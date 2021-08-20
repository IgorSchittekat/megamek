package megamek.client.ui.swing;

import megamek.client.ui.Messages;
import megamek.common.*;
import megamek.common.icons.Portrait;
import megamek.common.options.OptionsConstants;
import megamek.common.options.PilotOptions;
import megamek.common.options.Quirks;
import megamek.common.util.fileUtils.MegaMekFile;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class MekTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 4819661751806908535L;

    public static final int COL_UNIT = 0;
    public static final int COL_PILOT = 1;
    public static final int COL_PLAYER = 2;
    public static final int COL_BV = 3;
    public static final int N_COL = 4;

    private final ChatLounge chatLounge;
    private ArrayList<Entity> data;

    public MekTableModel(ChatLounge lounge) {
        chatLounge = lounge;
        data = new ArrayList<Entity>();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    public void clearData() {
        data = new ArrayList<Entity>();
        fireTableDataChanged();
    }

    @Override
    public int getColumnCount() {
        return N_COL;
    }

    public void addUnit(Entity en) {
        data.add(en);
        fireTableDataChanged();
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case (COL_PILOT):
                return Messages.getString("ChatLounge.colPilot");
            case (COL_UNIT):
                return Messages.getString("ChatLounge.colUnit");
            case (COL_PLAYER):
                return Messages.getString("ChatLounge.colPlayer");
            case (COL_BV):
                return Messages.getString("ChatLounge.colBV");
        }
        return "??";
    }

    @Override
    public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    @Override
    public Object getValueAt(int row, int col) {
        boolean compact = chatLounge.getButCompact().isSelected();
        Entity entity = getEntityAt(row);
        boolean blindDrop = !entity.getOwner().equals(chatLounge.getClientGUI().getClient().getLocalPlayer())
                && chatLounge.getClientGUI().getClient().getGame().getOptions().booleanOption(OptionsConstants.BASE_BLIND_DROP);
        String value = "";
        if (col == COL_BV) {
            value += entity.calculateBattleValue();
        } else if (col == COL_PLAYER) {
            if (compact) {
                value += entity.getOwner().getName();
            } else {
                value += entity.getOwner().getName() + "<br>Team "
                        + chatLounge.getClientGUI().getClient().getGame().getPlayer(entity.getOwnerId()).getTeam();
            }
        } else if (col == COL_PILOT) {
            final boolean rpgSkills = chatLounge.getClientGUI().getClient().getGame().getOptions().booleanOption(OptionsConstants.RPG_RPG_GUNNERY);
            if (compact) {
                return formatPilotCompact(entity.getCrew(), blindDrop, rpgSkills);
            }
            return formatPilotHTML(entity.getCrew(), blindDrop, rpgSkills);
        } else {
            if (compact) {
                return formatUnitCompact(entity, blindDrop);
            }
            return formatUnitHTML(entity, blindDrop);
        }
        return value;
    }

    public Entity getEntityAt(int row) {
        if (row < 0) {
            return null;
        }
        return data.get(row);
    }

    public String formatPilotCompact(Crew pilot, boolean blindDrop, boolean rpgSkills) {
        String value = "";
        if (blindDrop) {
            value += Messages.getString("ChatLounge.Unknown");
        } else {
            value += pilot.getDesc();
        }

        value += " (" + pilot.getSkillsAsString(rpgSkills) + ")";
        if (pilot.countOptions() > 0) {
            value += " (" + pilot.countOptions() + Messages.getString("ChatLounge.abilities") + ")";
        }
        return value;
    }

    public String formatPilotHTML(Crew pilot, boolean blindDrop, boolean rpgSkill) {

        int crewAdvCount = pilot.countOptions(PilotOptions.LVL3_ADVANTAGES);
        int implants = pilot.countOptions(PilotOptions.MD_ADVANTAGES);

        String value = "";
        if (!blindDrop && pilot.getSlotCount() > 1) {
            for (int i = 0; i < pilot.getSlotCount(); i++) {
                if (pilot.isMissing(i)) {
                    value += "<b>No " + pilot.getCrewType().getRoleName(i) + "</b>";
                } else {
                    value += "<b>" + pilot.getDesc(i) + "</b> (" + pilot.getCrewType().getRoleName(i) + "): ";
                    value += pilot.getSkillsAsString(i, rpgSkill);
                }
                value += "<br/>";
            }
        } else {
            if (blindDrop) {
                value += "<b>" + Messages.getString("ChatLounge.Unknown") + "</b><br/>";
            } else {
                value += "<b>" + pilot.getDesc() + "</b><br/>";
            }
            value += "" + pilot.getSkillsAsString(rpgSkill);
        }
        if (crewAdvCount > 0) {
            value += ", " + crewAdvCount + Messages.getString("ChatLounge.advs");
        }
        value += "<br>";
        if (implants > 0) {
            value += "<i>" + Messages.getString("ChatLounge.md") + "</i>, " + implants
                    + Messages.getString("ChatLounge.implants") + "<br>";
        }
        return value;
    }

    public String formatUnitCompact(Entity entity, boolean blindDrop) {

        String value = "";
        // Reset the tree strings.
        String strTreeSet = ""; //$NON-NLS-1$
        String strTreeView = ""; //$NON-NLS-1$

        if (blindDrop) {
            return formatUtilBlindDrop(entity);
        }

        // Set the tree strings based on C3 settings for the unit.
        if (entity.hasC3i() || entity.hasNavalC3()) {
            if (entity.calculateFreeC3Nodes() == 5) {
                strTreeSet = "**"; //$NON-NLS-1$
            }
            strTreeView = " (" + entity.getC3NetId() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
        } else if (entity.hasC3()) {
            if (entity.getC3Master() == null) {
                if (entity.hasC3S()) {
                    strTreeSet = "***"; //$NON-NLS-1$
                } else {
                    strTreeSet = "*"; //$NON-NLS-1$
                }
            } else if (!entity.C3MasterIs(entity)) {
                strTreeSet = ">"; //$NON-NLS-1$
                if ((entity.getC3Master().getC3Master() != null)
                        && !entity.getC3Master().C3MasterIs(entity.getC3Master())) {
                    strTreeSet = ">>"; //$NON-NLS-1$
                }
                strTreeView = " -> " + entity.getC3Master().getDisplayName(); //$NON-NLS-1$
            }
        }

        value += strTreeSet + entity.getShortName() + strTreeView;

        if (entity.getTransportId() != Entity.NONE) {
            Entity loader = entity.getGame().getEntity(entity.getTransportId());
            value += ", aboard " + loader.getShortName() + "";
        }

        if (entity.isHidden()) {
            value += " (" + Messages.getString("ChatLounge.hidden") + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }

        if (entity.isOffBoard()) {
            value += " (" + Messages.getString("ChatLounge.deploysOffBoard") + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        } else if (entity.getDeployRound() > 0) {
            value += " (" + Messages.getString("ChatLounge.deploysAfterRound") //$NON-NLS-1$ //$NON-NLS-2$
                    + entity.getDeployRound();
            if (entity.getStartingPos(false) != Board.START_NONE) {
                value += Messages.getString("ChatLounge.deploysAfterZone") //$NON-NLS-1$
                        + IStartingPositions.START_LOCATION_NAMES[entity.getStartingPos(false)];
            }
            // $NON-NLS-2$
            value += ")"; //$NON-NLS-1$
        }
        return value;
    }

    public String formatUnitHTML(Entity entity, boolean blindDrop) {
        String value = "";
        if (blindDrop) {
            value = formatUtilBlindDrop(entity);
            value += "<br>";
        } else {
            String c3network = "";
            if (entity.hasC3i()) {
                if (entity.calculateFreeC3Nodes() >= 5) {
                    c3network += Messages.getString("ChatLounge.C3iNone");
                } else {
                    c3network += Messages.getString("ChatLounge.C3iNetwork") + entity.getC3NetId();
                    if (entity.calculateFreeC3Nodes() > 0) {
                        c3network += Messages.getString("ChatLounge.C3iNodes",
                                entity.calculateFreeC3Nodes());
                    }
                }
            } else if (entity.hasNavalC3()) {
                if (entity.calculateFreeC3Nodes() >= 5) {
                    c3network += Messages.getString("ChatLounge.NC3None");
                } else {
                    c3network += Messages.getString("ChatLounge.NC3Network") + entity.getC3NetId();
                    if (entity.calculateFreeC3Nodes() > 0) {
                        c3network += Messages.getString("ChatLounge.NC3Nodes",
                                entity.calculateFreeC3Nodes());
                    }
                }
            } else if (entity.hasC3()) {
                if (entity.C3MasterIs(entity)) {
                    c3network += Messages.getString("ChatLounge.C3Master");
                    c3network += Messages.getString("ChatLounge.C3MNodes",
                            entity.calculateFreeC3MNodes());
                    if (entity.hasC3MM()) {
                        c3network += Messages.getString("ChatLounge.C3SNodes",
                                entity.calculateFreeC3Nodes());
                    }
                } else if (!entity.hasC3S()) {
                    c3network += Messages.getString("ChatLounge.C3Master");
                    c3network += Messages.getString("ChatLounge.C3SNodes",
                            entity.calculateFreeC3Nodes());
                    // an independent master might also be a slave to a company
                    // master
                    if (entity.getC3Master() != null) {
                        c3network += "<br>" + Messages.getString("ChatLounge.C3Slave")
                                + entity.getC3Master().getDisplayName();
                        // $NON-NLS-1$
                    }
                } else if (entity.getC3Master() != null) {
                    c3network += Messages.getString("ChatLounge.C3Slave") + entity.getC3Master().getDisplayName();
                    // $NON-NLS-1$
                } else {
                    c3network += Messages.getString("ChatLounge.C3None");
                }
            }

            int posQuirkCount = entity.countQuirks(Quirks.POS_QUIRKS);
            int negQuirkCount = entity.countQuirks(Quirks.NEG_QUIRKS);
            int partRepCount = entity.countPartialRepairs();

            value += "<b>" + entity.getShortName() + "</b><br>";
            value += "" + Math.round(entity.getWeight()) + Messages.getString("ChatLounge.Tons") + "<br>";
            if (entity.getTransportId() != Entity.NONE) {
                Entity loader = entity.getGame().getEntity(entity.getTransportId());
                value += "<i>Carried by " + loader.getShortName() + "</i><br>";
            }
            if (c3network.length() > 0) {
                value += c3network + "<br>";
            }
            if ((posQuirkCount > 0) | (negQuirkCount > 0)) {
                value += Messages.getString("ChatLounge.Quirks") + "+" + posQuirkCount + "/" + "-" + negQuirkCount
                        + "<br>";
            }
            if ((partRepCount > 0)) {
                value += Messages.getString("ChatLounge.PartialRepairs") + " + " + partRepCount + "<br>";
            }

        }

        if (entity.isHidden()) {
            value += Messages.getString("ChatLounge.hidden") + "<br>"; // ; //$NON-NLS-1$
        }

        if (entity.isOffBoard()) {
            value += Messages.getString("ChatLounge.deploysOffBoard"); //$NON-NLS-1$
        } else if (entity.getDeployRound() > 0) {
            value += Messages.getString("ChatLounge.deploysAfterRound") + entity.getDeployRound(); //$NON-NLS-1$
            if (entity.getStartingPos(false) != Board.START_NONE) {
                value += Messages.getString("ChatLounge.deploysAfterZone") //$NON-NLS-1$
                        + IStartingPositions.START_LOCATION_NAMES[entity.getStartingPos(false)];
            }
        }
        if (!entity.isDesignValid()) {
            value += Messages.getString("ChatLounge.invalidDesign");
        }
        return value;
    }

    private String formatUtilBlindDrop(Entity entity) {
        String value = "";
        if (entity instanceof Infantry) {
            value += Messages.getString("ChatLounge.0"); //$NON-NLS-1$
        } else if (entity instanceof Protomech) {
            value += Messages.getString("ChatLounge.1"); //$NON-NLS-1$
        } else if (entity instanceof GunEmplacement) {
            value += Messages.getString("ChatLounge.2"); //$NON-NLS-1$
        } else {
            value += entity.getWeightClassName();
            if (entity instanceof Tank) {
                value += Messages.getString("ChatLounge.6"); //$NON-NLS-1$
            }
        }
        return value;
    }
}
