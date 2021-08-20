package megamek.client.ui.swing;

import megamek.client.Client;
import megamek.client.generator.RandomCallsignGenerator;
import megamek.client.generator.RandomGenderGenerator;
import megamek.client.generator.RandomNameGenerator;
import megamek.client.ui.Messages;
import megamek.client.ui.swing.util.MenuScroller;
import megamek.common.*;
import megamek.common.enums.Gender;
import megamek.common.options.OptionsConstants;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

public class MekTableMouseAdapter extends MouseInputAdapter implements ActionListener {

    //region Action Commands
    private static final String NAME_COMMAND = "NAME";
    private static final String CALLSIGN_COMMAND = "CALLSIGN";

    private final ChatLounge chatLounge;

    public MekTableMouseAdapter(ChatLounge lounge) {
        chatLounge = lounge;
    }

    @Override
    public void actionPerformed(ActionEvent action) {
        StringTokenizer st = new StringTokenizer(action.getActionCommand(), "|");
        String command = st.nextToken();
        int[] rows = chatLounge.getTableEntities().getSelectedRows();
        int row = chatLounge.getTableEntities().getSelectedRow();
        Entity entity = chatLounge.getMekModel().getEntityAt(row);
        Vector<Entity> entities = new Vector<>();
        for (int value : rows) {
            entities.add(chatLounge.getMekModel().getEntityAt(value));
        }
        if (null == entity) {
            return;
        }
        if (command.equalsIgnoreCase("VIEW")) {
            chatLounge.mechReadout(entity);
        } else if (command.equalsIgnoreCase("BV")) {
            chatLounge.mechBVDisplay(entity);
        } else if (command.equalsIgnoreCase("DAMAGE")) {
            chatLounge.mechEdit(entity);
        } else if (command.equalsIgnoreCase("INDI_CAMO")) {
            chatLounge.mechCamo(entities);
        } else if (command.equalsIgnoreCase("CONFIGURE")) {
            chatLounge.customizeMech(entity);
        } else if (command.equalsIgnoreCase("CONFIGURE_ALL")) {
            chatLounge.customizeMechs(entities);
        } else if (command.equalsIgnoreCase("DELETE")) {
            Client c = chatLounge.getClientGUI().getBots().get(entity.getOwner().getName());
            if (c == null) {
                c = chatLounge.getClientGUI().getClient();
            }
            for (Entity e : entities) {
                // first unload any units from this unit
                if (e.getLoadedUnits().size() > 0) {
                    for (Entity loaded : e.getLoadedUnits()) {
                        e.unload(loaded);
                        loaded.setTransportId(Entity.NONE);
                        c.sendUpdateEntity(loaded);
                    }
                    c.sendUpdateEntity(e);
                }
                // unload this unit from any other units it might be loaded
                // onto
                if (entity.getTransportId() != Entity.NONE) {
                    Entity loader = chatLounge.getClientGUI().getClient().getGame().getEntity(entity.getTransportId());
                    if (null != loader) {
                        loader.unload(entity);
                        entity.setTransportId(Entity.NONE);
                        c.sendUpdateEntity(loader);
                        c.sendUpdateEntity(entity);
                    }
                }
                c.sendDeleteEntity(e.getId());
            }
        } else if (command.equalsIgnoreCase("SKILLS")) {
            Client c = chatLounge.getClientGUI().getBots().get(entity.getOwner().getName());
            if (c == null) {
                c = chatLounge.getClientGUI().getClient();
            }
            for (Entity e : entities) {
                for (int i = 0; i < e.getCrew().getSlotCount(); i++) {
                    int[] skills = c.getRandomSkillsGenerator().getRandomSkills(e, true);
                    e.getCrew().setGunnery(skills[0], i);
                    e.getCrew().setPiloting(skills[1], i);
                    if (e.getCrew() instanceof LAMPilot) {
                        skills = c.getRandomSkillsGenerator().getRandomSkills(e, true);
                        ((LAMPilot) e.getCrew()).setGunneryAero(skills[0]);
                        ((LAMPilot) e.getCrew()).setPilotingAero(skills[1]);
                    }
                }
                e.getCrew().sortRandomSkills();
                c.sendUpdateEntity(e);
            }
        } else if (command.equalsIgnoreCase(NAME_COMMAND)) {
            Client c = chatLounge.getClientGUI().getBots().get(entity.getOwner().getName());
            if (c == null) {
                c = chatLounge.getClientGUI().getClient();
            }
            for (Entity e : entities) {
                for (int i = 0; i < e.getCrew().getSlotCount(); i++) {
                    Gender gender = RandomGenderGenerator.generate();
                    e.getCrew().setGender(gender, i);
                    e.getCrew().setName(RandomNameGenerator.getInstance().generate(gender, e.getOwner().getName()), i);
                }
                c.sendUpdateEntity(e);
            }
        } else if (command.equals(CALLSIGN_COMMAND)) {
            Client c = chatLounge.getClientGUI().getBots().get(entity.getOwner().getName());
            if (c == null) {
                c = chatLounge.getClientGUI().getClient();
            }
            for (Entity e : entities) {
                for (int i = 0; i < e.getCrew().getSlotCount(); i++) {
                    e.getCrew().setNickname(RandomCallsignGenerator.getInstance().generate(), i);
                }
                c.sendUpdateEntity(e);
            }
        } else if (command.equalsIgnoreCase("LOAD")) {
            StringTokenizer stLoad = new StringTokenizer(st.nextToken(), ":");
            int id = Integer.parseInt(stLoad.nextToken());
            int bayNumber = Integer.parseInt(stLoad.nextToken());
            Entity loadingEntity = chatLounge.getClientGUI().getClient().getEntity(id);
            boolean loadRear = false;
            if (stLoad.hasMoreTokens()) {
                loadRear = Boolean.parseBoolean(stLoad.nextToken());
            }

            double capacity;
            boolean hasEnoughCargoCapacity;
            String errorMessage = "";
            if (bayNumber != -1) {
                Bay bay = loadingEntity.getBayById(bayNumber);
                if (null != bay) {
                    double loadSize = entities.stream().mapToDouble(bay::spaceForUnit).sum();
                    capacity = bay.getUnused();
                    hasEnoughCargoCapacity = loadSize <= capacity;
                    errorMessage = Messages.getString("LoadingBay.baytoomany") + // $NON-NLS-2$
                            " " + (int) bay.getUnusedSlots()
                            + bay.getDefaultSlotDescription() + ".";
                    // We're also using bay number to distinguish between front and rear locations
                    // for protomech mag clamp systems
                } else if (loadingEntity.hasETypeFlag(Entity.ETYPE_MECH)
                        && entities.get(0).hasETypeFlag(Entity.ETYPE_PROTOMECH)) {
                    capacity = 1;
                    hasEnoughCargoCapacity = entities.size() == 1;
                    errorMessage = Messages.getString("LoadingBay.protostoomany");
                } else {
                    hasEnoughCargoCapacity = false;
                    errorMessage = Messages.getString("LoadingBay.bayNumberNotFound", bayNumber);
                }
            } else {
                HashMap<Long, Double> capacities, counts;
                capacities = new HashMap<>();
                counts = new HashMap<>();
                HashMap<Transporter, Double> potentialLoad = new HashMap<>();
                // Get the counts and capacities for all present types
                for (Entity e : entities) {
                    long entityType = e.getEntityType();
                    long loaderType = loadingEntity.getEntityType();
                    double unitSize;
                    if ((entityType & Entity.ETYPE_MECH) != 0) {
                        entityType = Entity.ETYPE_MECH;
                        unitSize = 1;
                    } else if ((entityType & Entity.ETYPE_INFANTRY) != 0) {
                        entityType = Entity.ETYPE_INFANTRY;
                        boolean useCount = true;
                        if ((loaderType & Entity.ETYPE_TANK) != 0) {
                            // This is a super hack... When getting
                            // capacities, troopspace gives unused space in
                            // terms of tons, and BattleArmorHandles gives
                            // it in terms of unit count. If I call
                            // getUnused, it sums these together, and is
                            // meaningless, so we'll go through all
                            // transporters....
                            boolean hasTroopSpace = false;
                            for (Transporter t : loadingEntity.getTransports()) {
                                if (t instanceof TankTrailerHitch) {
                                    continue;
                                }
                                double loadWeight = e.getWeight();
                                if (potentialLoad.containsKey(t)) {
                                    loadWeight += potentialLoad.get(t);
                                }
                                if (!(t instanceof BattleArmorHandlesTank) && t.canLoad(e)
                                        && (loadWeight <= t.getUnused())) {
                                    hasTroopSpace = true;
                                    potentialLoad.put(t, loadWeight);
                                    break;
                                }
                            }
                            if (hasTroopSpace) {
                                useCount = false;
                            }
                        }
                        // TroopSpace uses tonnage
                        // bays and BA handlebars use a count
                        if (useCount) {
                            unitSize = 1;
                        } else {
                            unitSize = e.getWeight();
                        }
                    } else if ((entityType & Entity.ETYPE_PROTOMECH) != 0) {
                        entityType = Entity.ETYPE_PROTOMECH;
                        unitSize = 1;
                        // Loading using mag clamps; user can specify front or rear.
                        // Make use of bayNumber field
                        if ((loaderType & Entity.ETYPE_MECH) != 0) {
                            bayNumber = loadRear? 1 : 0;
                        }
                    } else if ((entityType & Entity.ETYPE_DROPSHIP) != 0) {
                        entityType = Entity.ETYPE_DROPSHIP;
                        unitSize = 1;
                    } else if ((entityType & Entity.ETYPE_JUMPSHIP) != 0) {
                        entityType = Entity.ETYPE_JUMPSHIP;
                        unitSize = 1;
                    } else if ((entityType & Entity.ETYPE_AERO) != 0) {
                        entityType = Entity.ETYPE_AERO;
                        unitSize = 1;
                    } else if ((entityType & Entity.ETYPE_TANK) != 0) {
                        entityType = Entity.ETYPE_TANK;
                        unitSize = 1;
                    } else {
                        unitSize = 1;
                    }

                    Double count = counts.get(entityType);
                    if (count == null) {
                        count = 0.0;
                    }
                    count = count + unitSize;
                    counts.put(entityType, count);

                    Double cap = capacities.get(entityType);
                    if (cap == null) {
                        cap = loadingEntity.getUnused(e);
                        capacities.put(entityType, cap);
                    }
                }
                hasEnoughCargoCapacity = true;
                capacity = 0;
                for (Long typeId : counts.keySet()) {
                    double currCount = counts.get(typeId);
                    double currCapacity = capacities.get(typeId);
                    if (currCount > currCapacity) {
                        hasEnoughCargoCapacity = false;
                        capacity = currCapacity;
                        String messageName;
                        if (typeId == Entity.ETYPE_INFANTRY) {
                            messageName = "LoadingBay.nonbaytoomanyInf";
                        } else {
                            messageName = "LoadingBay.nonbaytoomany";
                        }
                        errorMessage = Messages.getString(messageName, currCount,
                                Entity.getEntityTypeName(typeId), currCapacity);
                    }
                }
            }
            if (hasEnoughCargoCapacity) {
                for (Entity e : entities) {
                    chatLounge.loader(e, id, bayNumber);
                }
            } else {
                JOptionPane.showMessageDialog(chatLounge.getClientGUI().frame, errorMessage, Messages.getString("LoadingBay.error"), // $NON-NLS-2$
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (command.equalsIgnoreCase("UNLOAD")) {
            for (Entity e : entities) {
                chatLounge.unloader(e);
            }
        } else if (command.equalsIgnoreCase("UNLOADALL")) {
            for (Entity loadee : entity.getLoadedUnits()) {
                chatLounge.unloader(loadee);
            }
        } else if (command.equalsIgnoreCase("UNLOADALLFROMBAY")) {
            int id = Integer.parseInt(st.nextToken());
            Bay bay = entity.getBayById(id);
            for (Entity loadee : bay.getLoadedUnits()) {
                chatLounge.unloader(loadee);
            }
        } else if (command.equalsIgnoreCase("SQUADRON")) {
            Vector<Integer> fighters = new Vector<Integer>();
            for (Entity e : entities) {
                fighters.add(e.getId());
            }
            if ((!chatLounge.getClientGUI().getClient().getGame().getOptions()
                    .booleanOption(OptionsConstants.ADVAERORULES_ALLOW_LARGE_SQUADRONS)
                    && (fighters.size() > FighterSquadron.MAX_SIZE))
                    || (chatLounge.getClientGUI().getClient().getGame().getOptions()
                    .booleanOption(OptionsConstants.ADVAERORULES_ALLOW_LARGE_SQUADRONS)
                    && (fighters.size() > FighterSquadron.ALTERNATE_MAX_SIZE))) {
                JOptionPane.showMessageDialog(chatLounge.getClientGUI().frame, Messages.getString("FighterSquadron.toomany"),
                        Messages.getString("FighterSquadron.error"), JOptionPane.ERROR_MESSAGE); // $NON-NLS-1$
                // //$NON-NLS-2$
            } else {
                chatLounge.loadFS(fighters);
            }
        } else if (command.equalsIgnoreCase("SWAP")) {
            int id = Integer.parseInt(st.nextToken());
            chatLounge.swapPilots(entity, id);
        } else if (command.equalsIgnoreCase("CHANGE_OWNER")) {
            // Code to swap entities to a player.
            int id = Integer.parseInt(st.nextToken());
            for (Entity e : entities) {
                chatLounge.changeEntityOwner(e, id);
            }
        } else if (command.equalsIgnoreCase("SAVE_QUIRKS_ALL")) {
            for (Entity e : entities) {
                QuirksHandler.addCustomQuirk(e, false);
            }
        } else if (command.equalsIgnoreCase("SAVE_QUIRKS_MODEL")) {
            for (Entity e : entities) {
                QuirksHandler.addCustomQuirk(e, true);
            }
        } else if (command.equalsIgnoreCase("RAPIDFIREMG_OFF") || command.equalsIgnoreCase("RAPIDFIREMG_ON")) {
            boolean rapidFire = command.equalsIgnoreCase("RAPIDFIREMG_ON");
            for (Entity e : entities) {
                boolean dirty = false;
                for (Mounted m : e.getWeaponList()) {
                    WeaponType wtype = (WeaponType) m.getType();
                    if (!wtype.hasFlag(WeaponType.F_MG)) {
                        continue;
                    }
                    m.setRapidfire(rapidFire);
                    dirty = true;
                }
                if (dirty) {
                    chatLounge.getClientGUI().getClient().sendUpdateEntity(e);
                }
            }
        } else if (command.equalsIgnoreCase("HOTLOAD_OFF") || command.equalsIgnoreCase("HOTLOAD_ON")) {
            boolean hotLoad = command.equalsIgnoreCase("HOTLOAD_ON");
            for (Entity e : entities) {
                boolean dirty = false;
                for (Mounted m : e.getWeaponList()) {
                    WeaponType wtype = (WeaponType) m.getType();
                    if (!wtype.hasFlag(WeaponType.F_MISSILE)
                            || (wtype.getAmmoType() != AmmoType.T_LRM)) {
                        continue;
                    }
                    m.setHotLoad(hotLoad);
                    dirty = true;
                }
                for (Mounted m : e.getAmmo()) {
                    AmmoType atype = (AmmoType) m.getType();
                    if (atype.getAmmoType() != AmmoType.T_LRM) {
                        continue;
                    }
                    m.setHotLoad(hotLoad);
                    // Set the mode too, so vehicles can switch back
                    int numModes = m.getType().getModesCount();
                    for (int i = 0; i < numModes; i++) {
                        if (m.getType().getMode(i).getName().equals("HotLoad")) {
                            m.setMode(i);
                        }
                    }
                    dirty = true;
                }
                if (dirty) {
                    chatLounge.getClientGUI().getClient().sendUpdateEntity(e);
                }
            }
        } else if (command.equalsIgnoreCase("SEARCHLIGHT_OFF") || command.equalsIgnoreCase("SEARCHLIGHT_ON")) {
            boolean searchLight = command.equalsIgnoreCase("SEARCHLIGHT_ON");
            for (Entity e : entities) {
                boolean dirty = false;
                if (!e.hasQuirk(OptionsConstants.QUIRK_POS_SEARCHLIGHT)) {
                    e.setExternalSpotlight(searchLight);
                    e.setSpotlightState(searchLight);
                    dirty = true;
                }
                if (dirty) {
                    chatLounge.getClientGUI().getClient().sendUpdateEntity(e);
                }
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            int row = chatLounge.getTableEntities().rowAtPoint(e.getPoint());
            Entity entity = chatLounge.getMekModel().getEntityAt(row);
            if (entity != null) {
                boolean isOwner = entity.getOwner().equals(chatLounge.getClientGUI().getClient().getLocalPlayer());
                boolean isBot = chatLounge.getClientGUI().getBots().get(entity.getOwner().getName()) != null;
                if ((isOwner || isBot)) {
                    chatLounge.customizeMech(entity);
                }
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e) {
        JPopupMenu popup = new JPopupMenu();
        JTable tableEntities = chatLounge.getTableEntities();
        MekTableModel mekModel = chatLounge.getMekModel();
        ClientGUI clientgui = chatLounge.getClientGUI();
        if (chatLounge.getTableEntities().getSelectedRowCount() == 0) {
            return;
        }
        int[] rows = tableEntities.getSelectedRows();
        int row = tableEntities.getSelectedRow();
        boolean oneSelected = tableEntities.getSelectedRowCount() == 1;
        Entity entity = mekModel.getEntityAt(row);
        Vector<Entity> entities = new Vector<>();
        for (int i = 0; i < rows.length; i++) {
            entities.add(mekModel.getEntityAt(rows[i]));
        }
        if (null == entity) {
            return;
        }
        boolean isOwner = entity.getOwner().equals(clientgui.getClient().getLocalPlayer());
        boolean isBot = clientgui.getBots().get(entity.getOwner().getName()) != null;
        boolean blindDrop = clientgui.getClient().getGame().getOptions()
                .booleanOption(OptionsConstants.BASE_BLIND_DROP);
        boolean isQuirksEnabled = clientgui.getClient().getGame().getOptions()
                .booleanOption(OptionsConstants.ADVANCED_STRATOPS_QUIRKS);
        boolean isRapidFireMG = clientgui.getClient().getGame().getOptions()
                .booleanOption(OptionsConstants.ADVCOMBAT_TACOPS_BURST);
        boolean isHotLoad = clientgui.getClient().getGame().getOptions()
                .booleanOption(OptionsConstants.ADVCOMBAT_TACOPS_HOTLOAD);
        boolean isSearchlight = clientgui.getClient().getGame().getPlanetaryConditions()
                .getLight() > PlanetaryConditions.L_DUSK;
        boolean allLoaded = true;
        boolean allUnloaded = true;
        boolean allCapFighter = true;
        boolean allDropships = true;
        boolean allInfantry = true;
        boolean allBattleArmor = true;
        boolean allProtomechs = true;
        boolean allSameEntityType = true;
        boolean hasMGs = false;
        boolean hasSearchlight = false;
        boolean hasLRMS = false;
        boolean hasHotLoad = false;
        boolean hasRapidFireMG = false;
        boolean sameSide = true;
        Entity prevEntity = null;
        int prevOwnerId = -1;
        for (Entity en : entities) {
            if (en.getTransportId() == Entity.NONE) {
                allLoaded = false;
            } else {
                allUnloaded = false;
            }
            if (!en.isCapitalFighter(true) || (en instanceof FighterSquadron)) {
                allCapFighter = false;
            }
            if ((prevOwnerId != -1) && (en.getOwnerId() != prevOwnerId)) {
                sameSide = false;
            }
            prevOwnerId = en.getOwnerId();
            allDropships &= en.hasETypeFlag(Entity.ETYPE_DROPSHIP);
            allInfantry &= en.hasETypeFlag(Entity.ETYPE_INFANTRY);
            allBattleArmor &= en.hasETypeFlag(Entity.ETYPE_BATTLEARMOR);
            allProtomechs &= en.hasETypeFlag(Entity.ETYPE_PROTOMECH);
            if ((prevEntity != null) && !en.getClass().equals(prevEntity.getClass()) && !allInfantry) {
                allSameEntityType = false;
            }
            if (isRapidFireMG || isHotLoad) {
                for (Mounted m : en.getWeaponList()) {
                    EquipmentType etype = m.getType();
                    if (etype.hasFlag(WeaponType.F_MG)) {
                        hasMGs = true;
                        hasRapidFireMG |= m.isRapidfire();
                    }
                    if (etype.hasFlag(WeaponType.F_MISSILE)) {
                        hasLRMS |= ((WeaponType) etype).getAmmoType() == AmmoType.T_LRM;
                        hasHotLoad |= m.isHotLoaded();
                    }
                    if (etype.hasFlag(WeaponType.F_MISSILE)) {
                        hasLRMS |= ((WeaponType) etype).getAmmoType() == AmmoType.T_LRM_IMP;
                        hasHotLoad |= m.isHotLoaded();
                    }
                }
            }
            boolean hasSearchlightQuirk = isQuirksEnabled && en.hasQuirk(OptionsConstants.QUIRK_POS_SEARCHLIGHT);
            if (!hasSearchlightQuirk) {
                hasSearchlight |= en.hasExternaSpotlight();
            }
            prevEntity = en;
        }
        if (e.isPopupTrigger()) {
            // This menu uses the following Mnemonics:
            // B, C, D, E, I, O, R, V
            JMenuItem menuItem;
            if (oneSelected) {
                menuItem = new JMenuItem("View...");
                menuItem.setActionCommand("VIEW");
                menuItem.addActionListener(this);
                menuItem.setEnabled(isOwner || !blindDrop);
                menuItem.setMnemonic(KeyEvent.VK_V);
                popup.add(menuItem);
            }

            if (oneSelected) {
                menuItem = new JMenuItem("Configure...");
                menuItem.setActionCommand("CONFIGURE");
                menuItem.addActionListener(this);
                menuItem.setEnabled(isOwner || isBot);
            } else {
                menuItem = new JMenuItem("Configure all");
                menuItem.setActionCommand("CONFIGURE_ALL");
                menuItem.addActionListener(this);
                menuItem.setEnabled(chatLounge.canConfigureAll(entities));
            }
            menuItem.setMnemonic(KeyEvent.VK_C);
            popup.add(menuItem);

            if (oneSelected) {
                menuItem = new JMenuItem("Edit Damage...");
                menuItem.setActionCommand("DAMAGE");
                menuItem.addActionListener(this);
                menuItem.setEnabled(isOwner || isBot);
                menuItem.setMnemonic(KeyEvent.VK_E);
                popup.add(menuItem);
            }


            menuItem = new JMenuItem("Set individual camo");
            menuItem.setActionCommand("INDI_CAMO");
            menuItem.addActionListener(this);
            menuItem.setEnabled(isOwner || isBot);
            menuItem.setMnemonic(KeyEvent.VK_I);
            popup.add(menuItem);

            if (oneSelected) {
                menuItem = new JMenuItem("View BV Calculation...");
                menuItem.setActionCommand("BV");
                menuItem.addActionListener(this);
                menuItem.setMnemonic(KeyEvent.VK_B);
                popup.add(menuItem);
            }

            menuItem = new JMenuItem("Delete...");
            menuItem.setActionCommand("DELETE");
            menuItem.addActionListener(this);
            menuItem.setEnabled(isOwner || isBot);
            menuItem.setMnemonic(KeyEvent.VK_D);
            popup.add(menuItem);

            //region Randomize Submenu
            // This menu uses the following Mnemonic Keys:
            // C, N, S
            JMenu menu = new JMenu("Randomize");
            menu.setMnemonic(KeyEvent.VK_R);

            menuItem = new JMenuItem("Name");
            menuItem.setActionCommand(NAME_COMMAND);
            menuItem.addActionListener(this);
            menuItem.setEnabled(isOwner || isBot);
            menuItem.setMnemonic(KeyEvent.VK_N);
            menu.add(menuItem);

            menuItem = new JMenuItem("Callsign");
            menuItem.setActionCommand(CALLSIGN_COMMAND);
            menuItem.addActionListener(this);
            menuItem.setEnabled(isOwner || isBot);
            menuItem.setMnemonic(KeyEvent.VK_C);
            menu.add(menuItem);

            menuItem = new JMenuItem("Skills");
            menuItem.setActionCommand("SKILLS");
            menuItem.addActionListener(this);
            menuItem.setEnabled(isOwner || isBot);
            menuItem.setMnemonic(KeyEvent.VK_S);
            menu.add(menuItem);
            popup.add(menu);
            //endregion Randomize Submenu

            // Change Owner Menu Item
            menu = new JMenu(Messages.getString("ChatLounge.ChangeOwner"));
            menu.setEnabled(isOwner || isBot);
            menu.setMnemonic(KeyEvent.VK_O);
            Enumeration<IPlayer> players = clientgui.getClient().getPlayers();
            while (players.hasMoreElements() && (isOwner || isBot)) {
                IPlayer p = players.nextElement();
                //
                if (!entity.getOwner().equals(p)) {
                    menuItem = new JMenuItem(p.getName());
                    menuItem.setActionCommand("CHANGE_OWNER|" + p.getId());
                    menuItem.addActionListener(this);
                    menuItem.setEnabled(true);
                    menu.add(menuItem);
                }
            }
            popup.add(menu);

            if (allUnloaded) {
                menu = new JMenu("Load...");
                JMenu menuDocking = new JMenu("Dock With...");
                JMenu menuSquadrons = new JMenu("Join...");
                JMenu menuMounting = new JMenu("Mount...");
                JMenu menuClamp = new JMenu("Mag Clamp...");
                JMenu menuLoadAll = new JMenu("Load All Into");
                boolean canLoad = false;
                boolean allHaveMagClamp = true;
                for (Entity b : entities) {
                    if (b.hasETypeFlag(Entity.ETYPE_BATTLEARMOR)
                            || b.hasETypeFlag(Entity.ETYPE_PROTOMECH)) {
                        allHaveMagClamp &= b.hasWorkingMisc(MiscType.F_MAGNETIC_CLAMP);
                    }
                }
                for (Entity loader : clientgui.getClient().getGame().getEntitiesVector()) {
                    // TODO don't allow capital fighters to load one another
                    // at the moment
                    if (loader.isCapitalFighter() && !(loader instanceof FighterSquadron)) {
                        continue;
                    }
                    boolean loadable = true;
                    for (Entity en : entities) {
                        if (!loader.canLoad(en, false)
                                || (loader.getId() == en.getId())
                                //TODO: support edge case where a support vee with an internal vehicle bay can load trailer internally
                                || (loader.canTow(en.getId()))) {
                            loadable = false;
                            break;
                        }
                    }
                    if (loadable) {
                        canLoad = true;
                        menuItem = new JMenuItem(loader.getShortName());
                        menuItem.setActionCommand("LOAD|" + loader.getId() + ":-1");
                        menuItem.addActionListener(this);
                        menuItem.setEnabled(isOwner || isBot);
                        menuLoadAll.add(menuItem);
                        JMenu subMenu = new JMenu(loader.getShortName());
                        if ((loader instanceof FighterSquadron) && allCapFighter) {
                            menuItem = new JMenuItem("Join " + loader.getShortName());
                            menuItem.setActionCommand("LOAD|" + loader.getId() + ":-1");
                            menuItem.addActionListener(this);
                            menuItem.setEnabled(isOwner || isBot);
                            menuSquadrons.add(menuItem);
                        } else if ((loader instanceof Jumpship) && allDropships) {
                            int freeCollars = 0;
                            for (Transporter t : loader.getTransports()) {
                                if (t instanceof DockingCollar) {
                                    freeCollars += t.getUnused();
                                }
                            }
                            menuItem = new JMenuItem(
                                    loader.getShortName() + " (Free Collars: " + freeCollars + ")");
                            menuItem.setActionCommand("LOAD|" + loader.getId() + ":-1");
                            menuItem.addActionListener(this);
                            menuItem.setEnabled(isOwner || isBot);
                            menuDocking.add(menuItem);
                        } else if (allBattleArmor && allHaveMagClamp && !loader.isOmni()
                                // Only load magclamps if applicable
                                && loader.hasUnloadedClampMount()
                                // Only choose MagClamps as last option
                                && (loader.getUnused(entities.get(0)) < 2)) {
                            for (Transporter t : loader.getTransports()) {
                                if ((t instanceof ClampMountMech) || (t instanceof ClampMountTank)) {
                                    menuItem = new JMenuItem("Onto " + loader.getShortName());
                                    menuItem.setActionCommand("LOAD|" + loader.getId() + ":-1");
                                    menuItem.addActionListener(this);
                                    menuItem.setEnabled(isOwner || isBot);
                                    menuClamp.add(menuItem);
                                }
                            }
                        } else if (allProtomechs && allHaveMagClamp
                                && loader.hasETypeFlag(Entity.ETYPE_MECH)) {
                            Transporter front = null;
                            Transporter rear = null;
                            for (Transporter t : loader.getTransports()) {
                                if (t instanceof ProtomechClampMount) {
                                    if (((ProtomechClampMount) t).isRear()) {
                                        rear = t;
                                    } else {
                                        front = t;
                                    }
                                }
                            }
                            Entity en = entities.firstElement();
                            if ((front != null) && front.canLoad(en)
                                    && ((en.getWeightClass() < EntityWeightClass.WEIGHT_SUPER_HEAVY)
                                    || (rear == null) || rear.getLoadedUnits().isEmpty())) {
                                menuItem = new JMenuItem("Onto Front");
                                menuItem.setActionCommand("LOAD|" + loader.getId() + ":0");
                                menuItem.addActionListener(this);
                                menuItem.setEnabled(isOwner || isBot);
                                subMenu.add(menuItem);
                            }
                            boolean frontUltra = (front != null)
                                    && front.getLoadedUnits().stream()
                                    .anyMatch(l -> l.getWeightClass() == EntityWeightClass.WEIGHT_SUPER_HEAVY);
                            if ((rear != null) && rear.canLoad(en) && !frontUltra) {
                                menuItem = new JMenuItem("Onto Rear");
                                menuItem.setActionCommand("LOAD|" + loader.getId() + ":1");
                                menuItem.addActionListener(this);
                                menuItem.setEnabled(isOwner || isBot);
                                subMenu.add(menuItem);
                            }
                            if (subMenu.getItemCount() > 0) {
                                menuClamp.add(subMenu);
                            }
                        } else if (allInfantry) {
                            menuItem = new JMenuItem(loader.getShortName());
                            menuItem.setActionCommand("LOAD|" + loader.getId() + ":-1");
                            menuItem.addActionListener(this);
                            menuItem.setEnabled(isOwner || isBot);
                            menuMounting.add(menuItem);
                        }
                        Entity en = entities.firstElement();
                        if (allSameEntityType && !allDropships) {
                            for (Transporter t : loader.getTransports()) {
                                if (t.canLoad(en)) {
                                    if (t instanceof Bay) {
                                        Bay bay = (Bay) t;
                                        menuItem = new JMenuItem("Into Bay #" + bay.getBayNumber() + " (Free "
                                                + "Slots: "
                                                + (int) loader.getBayById(bay.getBayNumber()).getUnusedSlots()
                                                + loader.getBayById(bay.getBayNumber()).getDefaultSlotDescription()
                                                + ")");
                                        menuItem.setActionCommand(
                                                "LOAD|" + loader.getId() + ":" + bay.getBayNumber());
                                        /*
                                         * } else { menuItem = new
                                         * JMenuItem(
                                         * t.getClass().getName()+
                                         * "Transporter" );
                                         * menuItem.setActionCommand("LOAD|"
                                         * + loader.getId() + ":-1"); }
                                         */
                                        menuItem.addActionListener(this);
                                        menuItem.setEnabled(isOwner || isBot);
                                        subMenu.add(menuItem);
                                    }
                                }
                            }
                            if (subMenu.getMenuComponentCount() > 0) {
                                menu.add(subMenu);
                            }
                        }
                    }
                }
                if (canLoad) {
                    if (menu.getMenuComponentCount() > 0) {
                        menu.setEnabled(isOwner || isBot);
                        MenuScroller.createScrollBarsOnMenus(menu);
                        popup.add(menu);
                    }
                    if (menuDocking.getMenuComponentCount() > 0) {
                        menuDocking.setEnabled(isOwner || isBot);
                        MenuScroller.createScrollBarsOnMenus(menuDocking);
                        popup.add(menuDocking);
                    }
                    if (menuSquadrons.getMenuComponentCount() > 0) {
                        menuSquadrons.setEnabled(isOwner || isBot);
                        MenuScroller.createScrollBarsOnMenus(menuSquadrons);
                        popup.add(menuSquadrons);
                    }
                    if (menuMounting.getMenuComponentCount() > 0) {
                        menuMounting.setEnabled(isOwner || isBot);
                        MenuScroller.createScrollBarsOnMenus(menuMounting);
                        popup.add(menuMounting);
                    }
                    if (menuClamp.getMenuComponentCount() > 0) {
                        menuClamp.setEnabled(isOwner || isBot);
                        MenuScroller.createScrollBarsOnMenus(menuClamp);
                        popup.add(menuClamp);
                    }
                    boolean hasMounting = menuMounting.getMenuComponentCount() > 0;
                    boolean hasSquadrons = menuSquadrons.getMenuComponentCount() > 0;
                    boolean hasDocking = menuDocking.getMenuComponentCount() > 0;
                    boolean hasLoad = menu.getMenuComponentCount() > 0;
                    boolean hasClamp = menuClamp.getMenuComponentCount() > 0;
                    if ((menuLoadAll.getMenuComponentCount() > 0)
                            && !(hasMounting || hasSquadrons || hasDocking || hasLoad || hasClamp)) {
                        menuLoadAll.setEnabled(isOwner || isBot);
                        MenuScroller.createScrollBarsOnMenus(menuLoadAll);
                        popup.add(menuLoadAll);
                    }
                }
            } else if (allLoaded) {
                menuItem = new JMenuItem("Unload");
                menuItem.setActionCommand("UNLOAD");
                menuItem.addActionListener(this);
                menuItem.setEnabled(isOwner || isBot);
                popup.add(menuItem);
            }
            if (oneSelected && (entity.getLoadedUnits().size() > 0)) {
                menuItem = new JMenuItem("Unload All Carried Units");
                menuItem.setActionCommand("UNLOADALL");
                menuItem.addActionListener(this);
                menuItem.setEnabled((isOwner || isBot));
                popup.add(menuItem);
                JMenu subMenu = new JMenu("Unload All From...");
                for (Bay bay : entity.getTransportBays()) {
                    if (bay.getLoadedUnits().size() > 0) {
                        menuItem = new JMenuItem(
                                "Bay # " + bay.getBayNumber() + " (" + bay.getLoadedUnits().size() + " units)");
                        menuItem.setActionCommand("UNLOADALLFROMBAY|" + bay.getBayNumber());
                        menuItem.addActionListener(this);
                        menuItem.setEnabled((isOwner || isBot));
                        subMenu.add(menuItem);
                    }
                }
                if (subMenu.getItemCount() > 0) {
                    subMenu.setEnabled((isOwner || isBot));
                    popup.add(subMenu);
                }
            }
            if (allCapFighter && allUnloaded && sameSide) {
                menuItem = new JMenuItem("Start Fighter Squadron");
                menuItem.setActionCommand("SQUADRON");
                menuItem.addActionListener(this);
                menuItem.setEnabled(isOwner || isBot);
                popup.add(menuItem);
            }
            if (oneSelected) {
                menu = new JMenu("Swap pilots with");
                boolean canSwap = false;
                for (Entity swapper : clientgui.getClient().getGame().getEntitiesVector()) {
                    if (swapper.isCapitalFighter()) {
                        continue;
                    }
                    // only swap your own pilots and with the same unit and crew type
                    if ((swapper.getOwnerId() == entity.getOwnerId()) && (swapper.getId() != entity.getId())
                            && (swapper.getUnitType() == entity.getUnitType())
                            && swapper.getCrew().getCrewType() == entity.getCrew().getCrewType()) {
                        canSwap = true;
                        menuItem = new JMenuItem(swapper.getShortName());
                        menuItem.setActionCommand("SWAP|" + swapper.getId());
                        menuItem.addActionListener(this);
                        menuItem.setEnabled((isOwner || isBot));
                        menu.add(menuItem);
                    }
                }
                if (canSwap) {
                    menu.setEnabled(isOwner || isBot);
                    popup.add(menu);
                }
            }

            // Set Rapid Fire MGs
            if (isRapidFireMG || isHotLoad || isSearchlight) {
                menu = new JMenu(Messages.getString("ChatLounge.Equipment"));
                if (isRapidFireMG && hasMGs) {
                    if (hasRapidFireMG) {
                        menuItem = new JMenuItem(Messages.getString("ChatLounge.RapidFireToggleOff"));
                        menuItem.setActionCommand("RAPIDFIREMG_OFF");
                    } else {
                        menuItem = new JMenuItem(Messages.getString("ChatLounge.RapidFireToggleOn"));
                        menuItem.setActionCommand("RAPIDFIREMG_ON");
                    }
                    menuItem.addActionListener(this);
                    menuItem.setEnabled(isOwner || isBot);
                    menu.add(menuItem);
                }
                if (isHotLoad && hasLRMS) {
                    if (hasHotLoad) {
                        menuItem = new JMenuItem(Messages.getString("ChatLounge.HotLoadToggleOff"));
                        menuItem.setActionCommand("HOTLOAD_OFF");
                    } else {
                        menuItem = new JMenuItem(Messages.getString("ChatLounge.HotLoadToggleOn"));
                        menuItem.setActionCommand("HOTLOAD_ON");
                    }
                    menuItem.addActionListener(this);
                    menuItem.setEnabled(isOwner || isBot);
                    menu.add(menuItem);
                }
                if (isSearchlight) {
                    if (hasSearchlight) {
                        menuItem = new JMenuItem(Messages.getString("ChatLounge.SearchlightToggleOff"));
                        menuItem.setActionCommand("SEARCHLIGHT_OFF");
                    } else {
                        menuItem = new JMenuItem(Messages.getString("ChatLounge.SearchlightToggleOn"));
                        menuItem.setActionCommand("SEARCHLIGHT_ON");
                    }
                    menuItem.addActionListener(this);
                    boolean loneEntityWithQuirk = oneSelected && isQuirksEnabled
                            && entity.hasQuirk(OptionsConstants.QUIRK_POS_SEARCHLIGHT);
                    menuItem.setEnabled((isOwner || isBot) && !loneEntityWithQuirk);
                    menu.add(menuItem);
                }
                if (menu.getMenuComponentCount() > 0) {
                    popup.add(menu);
                }
            }

            if (isQuirksEnabled) {
                menuItem = new JMenuItem("Save Quirks for Chassis");
                menuItem.setActionCommand("SAVE_QUIRKS_ALL");
                menuItem.addActionListener(this);
                popup.add(menuItem);
                menuItem = new JMenuItem("Save Quirks for Chassis/Model");
                menuItem.setActionCommand("SAVE_QUIRKS_MODEL");
                menuItem.addActionListener(this);
                popup.add(menuItem);
            }

            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }

}
