/*
 * MegaMek -
 * Copyright (C) 2000,2001,2002,2003,2004,2005,2006 Ben Mazur (bmazur@sev.org)
 * Copyright © 2013 Edward Cullen (eddy@obsessedcomputers.co.uk)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 */
package megamek.client.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import megamek.MegaMek;
import megamek.client.Client;
import megamek.client.generator.RandomGenderGenerator;
import megamek.client.generator.RandomNameGenerator;
import megamek.client.bot.BotClient;
import megamek.client.bot.princess.Princess;
import megamek.client.bot.ui.swing.BotGUI;
import megamek.client.generator.RandomCallsignGenerator;
import megamek.client.ui.Messages;
import megamek.client.ui.swing.boardview.BoardView1;
import megamek.client.ui.swing.dialog.imageChooser.CamoChooserDialog;
import megamek.client.ui.swing.util.MenuScroller;
import megamek.client.ui.swing.widget.SkinSpecification;
import megamek.common.*;
import megamek.common.enums.Gender;
import megamek.common.event.GameCFREvent;
import megamek.common.event.GameEntityNewEvent;
import megamek.common.event.GameEntityRemoveEvent;
import megamek.common.event.GamePhaseChangeEvent;
import megamek.common.event.GamePlayerChangeEvent;
import megamek.common.event.GameSettingsChangeEvent;
import megamek.common.icons.Camouflage;
import megamek.common.icons.Portrait;
import megamek.common.options.GameOptions;
import megamek.common.options.IOption;
import megamek.common.options.IOptionGroup;
import megamek.common.options.OptionsConstants;
import megamek.common.options.PilotOptions;
import megamek.common.options.Quirks;
import megamek.common.util.BoardUtilities;
import megamek.common.util.fileUtils.MegaMekFile;

public class ChatLounge extends AbstractPhaseDisplay implements ActionListener, ItemListener,
        ListSelectionListener, MouseListener, IMapSettingsObserver {
    private static final long serialVersionUID = 1454736776730903786L;

    private JButton butOptions;
    private JLabel lblMapSummary;
    private JLabel lblGameYear;
    private JLabel lblTechLevel;

    private JTabbedPane panTabs;
    private JPanel panMain;
    private JPanel panMap;

    /* Unit Configuration Panel */
    private JPanel panUnitInfo;
    JButton butLoad;
    JButton butArmy;
    JButton butSkills;
    JButton butNames;
    private JButton butLoadList;
    private JButton butSaveList;
    private JButton butDeleteAll;

    /* Unit Table */
    JTable tableEntities;
    private JScrollPane scrEntities;
    private JToggleButton butCompact;

    private MekTableModel mekModel;

    /* Player Configuration Panel */
    private JPanel panPlayerInfo;
    private JComboBox<String> choTeam;
    private JButton butCamo;
    private JButton butAddBot;
    private JButton butRemoveBot;
    private JButton butChangeStart;
    private JTable tablePlayers;
    private JScrollPane scrPlayers;
    private PlayerTableModel playerModel;

    /* Map Settings Panel */
    private MapSettings mapSettings;
    private JButton butConditions;
    private JPanel panGroundMap;
    private JPanel panSpaceMap;
    private JComboBox<String> comboMapType;
    @SuppressWarnings("rawtypes")
    private JComboBox<Comparable> comboMapSizes;
    private JButton butMapSize;
    private JButton butRandomMap;
    private JButton buttonBoardPreview;
    private JScrollPane scrMapButtons;
    private JPanel panMapButtons;
    private JLabel labBoardsSelected;
    private JList<String> lisBoardsSelected;
    private JScrollPane scrBoardsSelected;
    private JButton butChange;
    private JLabel labBoardsAvailable;
    private JList<String> lisBoardsAvailable;
    private JScrollPane scrBoardsAvailable;
    private JCheckBox chkRotateBoard;
    private JCheckBox chkIncludeGround;
    private JCheckBox chkIncludeSpace;
    private JButton butSpaceSize;
    private Set<BoardDimensions> mapSizes = new TreeSet<>();

    boolean resetAvailBoardSelection = false;
    boolean resetSelectedBoards = true;

    JPanel mapPreviewPanel;
    MiniMap miniMap = null;
    ClientDialog boardPreviewW;
    private Game boardPreviewGame = new Game();

    private String cmdSelectedTab = null;

    private MechSummaryCache.Listener mechSummaryCacheListener = new MechSummaryCache.Listener() {
        @Override
        public void doneLoading() {
            butLoad.setEnabled(true);
            butArmy.setEnabled(true);
            butLoadList.setEnabled(true);
        }
    };

    //endregion Action Commands

    /**
     * Creates a new chat lounge for the clientgui.getClient().
     */
    public ChatLounge(ClientGUI clientgui) {
        super(clientgui, SkinSpecification.UIComponents.ChatLounge.getComp(),
                SkinSpecification.UIComponents.ChatLoungeDoneButton.getComp());

        // Create a tabbed panel to hold our components.
        panTabs = new JTabbedPane();
        Font tabPanelFont = new Font("Dialog", Font.BOLD, //$NON-NLS-1$
                GUIPreferences.getInstance().getInt("AdvancedChatLoungeTabFontSize")); //$NON-NLS-1$
        panTabs.setFont(tabPanelFont);

        clientgui.getClient().getGame().addGameListener(this);
        clientgui.getBoardView().addBoardViewListener(this);

        butOptions = new JButton(Messages.getString("ChatLounge.butOptions")); //$NON-NLS-1$
        butOptions.addActionListener(this);

        lblMapSummary = new JLabel("");
        lblGameYear = new JLabel("");
        lblGameYear.setToolTipText(Messages.getString("ChatLounge.GameYearLabelToolTip")); //$NON-NLS-1$

        lblTechLevel = new JLabel("");
        lblTechLevel.setToolTipText(Messages.getString("ChatLounge.TechLevelLabelToolTip")); //$NON-NLS-1$

        butCompact = new JToggleButton(Messages.getString("ChatLounge.butCompact")); //$NON-NLS-1$
        butCompact.addActionListener(this);

        butDone.setText(Messages.getString("ChatLounge.butDone"));
        Font font = null;
        try {
            font = new Font("sanserif", Font.BOLD, 12);
        } catch (Exception exp) {
            MegaMek.getLogger().error(exp);
        }
        if (font == null) {
            MegaMek.getLogger().error("Couldn't find the new font for the 'Done' button.");
        } else {
            butDone.setFont(font);
        }

        setupPlayerInfo();

        refreshGameSettings();

        setupEntities();
        setupUnitConfiguration();

        refreshEntities();

        setupMainPanel();

        // layout main thing
        setLayout(new BorderLayout());

        refreshMapSummaryLabel();
        refreshGameYearLabel();
        refreshTechLevelLabel();

        if (GUIPreferences.getInstance().getChatLoungeTabs()) {
            add(panTabs, BorderLayout.CENTER);
        } else {
            add(panMain, BorderLayout.CENTER);
        }
    }

    public ClientGUI getClientGUI() {
        return clientgui;
    }

    public PlayerTableModel getPlayerModel() {
        return playerModel;
    }

    public JTable getTablePlayers() {
        return tablePlayers;
    }

    public JToggleButton getButCompact() {
        return butCompact;
    }

    public JTable getTableEntities() {
        return tableEntities;
    }

    public MekTableModel getMekModel() {
        return mekModel;
    }

    /**
     * Sets up the entities table
     */
    private void setupEntities() {
        mekModel = new MekTableModel(this);
        tableEntities = new JTable();
        tableEntities.setModel(mekModel);
        tableEntities.setRowHeight(80);
        tableEntities.setIntercellSpacing(new Dimension(0, 0));
        tableEntities.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        TableColumn column = null;
        for (int i = 0; i < MekTableModel.N_COL; i++) {
            tableEntities.getColumnModel().getColumn(i).setCellRenderer(new MekTableModelRenderer(this));
            column = tableEntities.getColumnModel().getColumn(i);
            if ((i == MekTableModel.COL_UNIT) || (i == MekTableModel.COL_PILOT)) {
                column.setPreferredWidth(170);
            } else if (i == MekTableModel.COL_PLAYER) {
                column.setPreferredWidth(50);
            } else {
                column.setPreferredWidth(10);
            }
        }
        tableEntities.addMouseListener(new MekTableMouseAdapter(this));
        tableEntities.addKeyListener(new MekTableKeyAdapter(this));
        tableEntities.getSelectionModel().addListSelectionListener(this);
        scrEntities = new JScrollPane(tableEntities);
        scrEntities.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    }

    /**
     * Sets up the unit configuration panel
     */
    private void setupUnitConfiguration() {
        butLoadList = new JButton(Messages.getString("ChatLounge.butLoadList")); //$NON-NLS-1$
        butLoadList.setActionCommand("load_list"); //$NON-NLS-1$
        butLoadList.addActionListener(this);

        butSaveList = new JButton(Messages.getString("ChatLounge.butSaveList")); //$NON-NLS-1$
        butSaveList.setActionCommand("save_list"); //$NON-NLS-1$
        butSaveList.addActionListener(this);
        butSaveList.setEnabled(false);

        butLoad = new JButton(Messages.getString("ChatLounge.butLoad")); //$NON-NLS-1$
        butArmy = new JButton(Messages.getString("ChatLounge.butArmy")); //$NON-NLS-1$
        butSkills = new JButton(Messages.getString("ChatLounge.butSkills")); //$NON-NLS-1$
        butNames = new JButton(Messages.getString("ChatLounge.butNames")); //$NON-NLS-1$

        // Initialize the RandomNameGenerator and RandomCallsignGenerator
        RandomNameGenerator.getInstance();
        RandomCallsignGenerator.getInstance();

        MechSummaryCache mechSummaryCache = MechSummaryCache.getInstance();
        mechSummaryCache.addListener(mechSummaryCacheListener);
        boolean mscLoaded = mechSummaryCache.isInitialized();
        butLoad.setEnabled(mscLoaded);
        butArmy.setEnabled(mscLoaded);
        butLoadList.setEnabled(mscLoaded);
        butSkills.setEnabled(true);
        butNames.setEnabled(true);

        Font font = new Font("Sans Serif", Font.BOLD, 18); //$NON-NLS-1$
        butLoad.setFont(font);
        butLoad.setActionCommand("load_mech"); //$NON-NLS-1$
        butLoad.addActionListener(this);
        butArmy.addActionListener(this);
        butSkills.addActionListener(this);
        butNames.addActionListener(this);

        butDeleteAll = new JButton(Messages.getString("ChatLounge.butDeleteAll")); //$NON-NLS-1$
        butDeleteAll.setActionCommand("delete_all"); //$NON-NLS-1$
        butDeleteAll.addActionListener(this);
        butDeleteAll.setEnabled(false);

        panUnitInfo = new JPanel();
        panUnitInfo.setBorder(BorderFactory.createTitledBorder("Unit Setup"));

        // layout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        panUnitInfo.setLayout(gridbag);

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(1, 1, 1, 1);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 0.0;
        c.gridwidth = 2;
        c.gridheight = 1;
        gridbag.setConstraints(butLoad, c);
        panUnitInfo.add(butLoad);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        gridbag.setConstraints(butArmy, c);
        panUnitInfo.add(butArmy);

        c.gridx = 0;
        c.gridy = 2;
        gridbag.setConstraints(butSkills, c);
        panUnitInfo.add(butSkills);

        c.gridx = 0;
        c.gridy = 3;
        gridbag.setConstraints(butNames, c);
        panUnitInfo.add(butNames);

        c.gridx = 1;
        c.gridy = 1;
        gridbag.setConstraints(butLoadList, c);
        panUnitInfo.add(butLoadList);

        c.gridx = 1;
        c.gridy = 2;
        gridbag.setConstraints(butSaveList, c);
        panUnitInfo.add(butSaveList);

        c.gridx = 1;
        c.gridy = 3;
        gridbag.setConstraints(butDeleteAll, c);
        panUnitInfo.add(butDeleteAll);
    }

    /**
     * Sets up the player info (team, camo) panel
     */
    private void setupPlayerInfo() {

        playerModel = new PlayerTableModel(this);
        tablePlayers = playerModel.createPlayersTable();
        tablePlayers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePlayers.getSelectionModel().addListSelectionListener(this);

        tablePlayers.setModel(playerModel);
        playerModel.setupColumnWidths(tablePlayers);

        tablePlayers.addMouseListener(new PlayerTableMouseAdapter(this));

        scrPlayers = new JScrollPane(tablePlayers);
        scrPlayers.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panPlayerInfo = new JPanel();
        panPlayerInfo.setBorder(BorderFactory.createTitledBorder("Player Setup"));

        butAddBot = new JButton(Messages.getString("ChatLounge.butAddBot")); //$NON-NLS-1$
        butAddBot.setActionCommand("add_bot"); //$NON-NLS-1$
        butAddBot.addActionListener(this);

        butRemoveBot = new JButton(Messages.getString("ChatLounge.butRemoveBot")); //$NON-NLS-1$
        butRemoveBot.setEnabled(false);
        butRemoveBot.setActionCommand("remove_bot"); //$NON-NLS-1$
        butRemoveBot.addActionListener(this);

        choTeam = new JComboBox<String>();
        setupTeams();
        choTeam.addItemListener(this);

        butCamo = new JButton();
        butCamo.setPreferredSize(new Dimension(84, 72));
        butCamo.setActionCommand("camo");
        butCamo.addActionListener(e -> {
            // Show the CamoChooserDialog for the selected player
            IPlayer player = getPlayerSelected().getLocalPlayer();
            CamoChooserDialog ccd = new CamoChooserDialog(clientgui.getFrame(), player.getCamouflage());

            // If the dialog was canceled or nothing selected, do nothing
            if ((ccd.showDialog() == JOptionPane.CANCEL_OPTION) || (ccd.getSelectedItem() == null)) {
                return;
            }

            // Update the player from the camo selection
            player.setCamouflage(ccd.getSelectedItem());
            butCamo.setIcon(player.getCamouflage().getImageIcon());
            getPlayerSelected().sendPlayerInfo();
        });
        refreshCamos();

        butChangeStart = new JButton(Messages.getString("ChatLounge.butChangeStart"));
        butChangeStart.addActionListener(this);

        // layout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        panPlayerInfo.setLayout(gridbag);

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(1, 1, 1, 1);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 0.0;
        gridbag.setConstraints(choTeam, c);
        panPlayerInfo.add(choTeam);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 0.0;
        gridbag.setConstraints(butChangeStart, c);
        panPlayerInfo.add(butChangeStart);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 0.0;
        gridbag.setConstraints(butAddBot, c);
        panPlayerInfo.add(butAddBot);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 0.0;
        gridbag.setConstraints(butRemoveBot, c);
        panPlayerInfo.add(butRemoveBot);

        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 4;
        c.weightx = 1.0;
        c.weighty = 1.0;
        gridbag.setConstraints(butCamo, c);
        panPlayerInfo.add(butCamo);

        refreshPlayerInfo();
    }

    private void setupMainPanel() {
        setupMap();

        panMain = new JPanel();

        // layout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        panMain.setLayout(gridbag);

        c.fill = GridBagConstraints.VERTICAL;
        c.insets = new Insets(1, 1, 1, 1);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridwidth = 1;
        gridbag.setConstraints(butOptions, c);
        panMain.add(butOptions);

        JPanel panel1 = new JPanel(new GridBagLayout());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0.0;
        c.anchor = GridBagConstraints.WEST;
        panel1.add(lblMapSummary, c);
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 0, 0, 20);
        panel1.add(lblGameYear, c);
        c.gridx = 2;
        panel1.add(lblTechLevel, c);
        c.insets = new Insets(0, 0, 0, 0);
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 3;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0.0;
        c.anchor = GridBagConstraints.NORTHEAST;
        panel1.add(butCompact, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.NORTHEAST;
        panMain.add(panel1, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridwidth = 1;
        c.gridheight = 3;
        gridbag.setConstraints(scrEntities, c);
        panMain.add(scrEntities);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        gridbag.setConstraints(panUnitInfo, c);
        panMain.add(panUnitInfo);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridwidth = 1;
        c.gridheight = 1;
        gridbag.setConstraints(panPlayerInfo, c);
        panMain.add(panPlayerInfo);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 0.0;
        c.weighty = 1.0;
        c.gridwidth = 1;
        c.gridheight = 1;
        gridbag.setConstraints(scrPlayers, c);
        panMain.add(scrPlayers);

        panTabs.add("Select Units", panMain); //$NON-NLS-1$
        panTabs.add("Select Map", panMap); //$NON-NLS-1$
    }

    private void setupMap() {

        panMap = new JPanel();

        mapSettings = MapSettings.getInstance(clientgui.getClient().getMapSettings());

        butConditions = new JButton(Messages.getString("ChatLounge.butConditions")); //$NON-NLS-1$
        butConditions.addActionListener(this);

        butRandomMap = new JButton(Messages.getString("BoardSelectionDialog.GeneratedMapSettings")); //$NON-NLS-1$
        butRandomMap.addActionListener(this);

        chkIncludeGround = new JCheckBox(Messages.getString("ChatLounge.IncludeGround")); //$NON-NLS-1$
        chkIncludeGround.addActionListener(this);

        chkIncludeSpace = new JCheckBox(Messages.getString("ChatLounge.IncludeSpace")); //$NON-NLS-1$
        chkIncludeSpace.addActionListener(this);

        setupGroundMap();
        setupSpaceMap();
        refreshSpaceGround();

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        panMap.setLayout(gridbag);

        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(1, 1, 1, 1);
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.EAST;
        gridbag.setConstraints(butConditions, c);
        panMap.add(butConditions);

        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.WEST;
        gridbag.setConstraints(butRandomMap, c);
        panMap.add(butRandomMap);

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(4, 4, 4, 4);
        c.weightx = 1.0;
        c.weighty = 0.75;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        gridbag.setConstraints(panGroundMap, c);
        panMap.add(panGroundMap);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 0.25;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.gridheight = 1;
        gridbag.setConstraints(panSpaceMap, c);
        panMap.add(panSpaceMap);

    }

    /**
     * Sets up the ground map selection panel
     */
    @SuppressWarnings("rawtypes")
    private void setupGroundMap() {

        panGroundMap = new JPanel();
        panGroundMap.setBorder(BorderFactory.createTitledBorder("Planetary Map"));

        panMapButtons = new JPanel();

        comboMapType = new JComboBox<String>();
        setupMapChoice();

        butMapSize = new JButton(Messages.getString("ChatLounge.MapSize")); //$NON-NLS-1$
        butMapSize.addActionListener(this);

        comboMapSizes = new JComboBox<Comparable>();
        setupMapSizes();

        buttonBoardPreview = new JButton(Messages.getString("BoardSelectionDialog.ViewGameBoard")); //$NON-NLS-1$
        buttonBoardPreview.addActionListener(this);
        buttonBoardPreview.setToolTipText(Messages.getString("BoardSelectionDialog.ViewGameBoardTooltip"));//$NON-NLS-1$

        butChange = new JButton("<<"); //$NON-NLS-1$
        butChange.addActionListener(this);

        labBoardsSelected = new JLabel(Messages.getString("BoardSelectionDialog.MapsSelected"), SwingConstants.CENTER); //$NON-NLS-1$
        labBoardsAvailable = new JLabel(Messages.getString("BoardSelectionDialog.mapsAvailable"), //$NON-NLS-1$
                SwingConstants.CENTER);

        lisBoardsSelected = new JList<String>(new DefaultListModel<String>());
        lisBoardsSelected.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lisBoardsAvailable = new JList<String>(new DefaultListModel<String>());
        refreshBoardsSelected();
        refreshBoardsAvailable();
        lisBoardsAvailable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lisBoardsAvailable.addMouseListener(this);
        lisBoardsAvailable.addListSelectionListener(this);

        chkRotateBoard = new JCheckBox(Messages.getString("BoardSelectionDialog.RotateBoard")); //$NON-NLS-1$
        chkRotateBoard.addActionListener(this);

        // layout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        panGroundMap.setLayout(gridbag);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(1, 1, 1, 1);
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        gridbag.setConstraints(chkIncludeGround, c);
        panGroundMap.add(chkIncludeGround);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(1, 1, 1, 1);
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        gridbag.setConstraints(comboMapType, c);
        panGroundMap.add(comboMapType);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(1, 1, 1, 1);
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        gridbag.setConstraints(comboMapSizes, c);
        panGroundMap.add(comboMapSizes);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(1, 1, 1, 1);
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        gridbag.setConstraints(butMapSize, c);
        panGroundMap.add(butMapSize);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(1, 1, 1, 1);
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        gridbag.setConstraints(buttonBoardPreview, c);
        panGroundMap.add(buttonBoardPreview);

        scrMapButtons = new JScrollPane(panMapButtons);
        refreshMapButtons();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(1, 1, 1, 1);
        c.weightx = 0.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        gridbag.setConstraints(scrMapButtons, c);
        panGroundMap.add(scrMapButtons);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        gridbag.setConstraints(labBoardsSelected, c);
        panGroundMap.add(labBoardsSelected);

        scrBoardsSelected = new JScrollPane(lisBoardsSelected);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 4;
        c.anchor = GridBagConstraints.NORTHWEST;
        gridbag.setConstraints(scrBoardsSelected, c);
        panGroundMap.add(scrBoardsSelected);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.CENTER;
        gridbag.setConstraints(butChange, c);
        panGroundMap.add(butChange);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridx = 3;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        gridbag.setConstraints(labBoardsAvailable, c);
        panGroundMap.add(labBoardsAvailable);

        scrBoardsAvailable = new JScrollPane(lisBoardsAvailable);
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 3;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 4;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        gridbag.setConstraints(scrBoardsAvailable, c);
        panGroundMap.add(scrBoardsAvailable);

        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridx = 4;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.CENTER;
        gridbag.setConstraints(chkRotateBoard, c);
        panGroundMap.add(chkRotateBoard);

        mapPreviewPanel = new JPanel();

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 4;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 4;
        c.anchor = GridBagConstraints.NORTHWEST;
        gridbag.setConstraints(mapPreviewPanel, c);
        panGroundMap.add(mapPreviewPanel);

        try {
            miniMap = new MiniMap(mapPreviewPanel, null);
            // Set a default size for the minimap object to ensure it will have
            // space on the screen to be drawn.
            miniMap.setSize(160, 200);
            miniMap.setZoom(2);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, Messages.getString("BoardEditor.CouldNotInitialiseMinimap") + e,
                    Messages.getString("BoardEditor.FatalError"), JOptionPane.ERROR_MESSAGE); // $NON-NLS-1$
            // //$NON-NLS-2$
        }
        mapPreviewPanel.add(miniMap);

        // setup the board preview window.
        boardPreviewW = new ClientDialog(clientgui.frame, 
                Messages.getString("BoardSelectionDialog.ViewGameBoard"), //$NON-NLS-1$
                false);
        boardPreviewW.setLocationRelativeTo(clientgui.frame);
        boardPreviewW.setVisible(false);

        try {
            BoardView1 bv = new BoardView1(boardPreviewGame, null, null);
            bv.setDisplayInvalidHexInfo(false);
            bv.setUseLOSTool(false);
            boardPreviewW.add(bv.getComponent(true));
            boardPreviewW.setSize(clientgui.frame.getWidth()/2, clientgui.frame.getHeight()/2);
            bv.zoomOut();
            bv.zoomOut();
            bv.zoomOut();
            bv.zoomOut();
            boardPreviewW.center();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                            Messages.getString("BoardEditor.CouldntInitialize") + e,
                            Messages.getString("BoardEditor.FatalError"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
            //$NON-NLS-2$
        }

    }

    private void setupSpaceMap() {

        panSpaceMap = new JPanel();
        panSpaceMap.setBorder(BorderFactory.createTitledBorder("Space Map"));

        butSpaceSize = new JButton(Messages.getString("ChatLounge.MapSize"));
        butSpaceSize.addActionListener(this);

        // layout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        panSpaceMap.setLayout(gridbag);

        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(1, 1, 1, 1);
        c.weightx = 1.0;
        c.weighty = 0.0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        gridbag.setConstraints(chkIncludeSpace, c);
        panSpaceMap.add(chkIncludeSpace);

        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(1, 1, 1, 1);
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        gridbag.setConstraints(butSpaceSize, c);
        panSpaceMap.add(butSpaceSize);

    }

    /**
     * Set up the map chooser panel
     */
    private void setupMapChoice() {
        comboMapType.addItem(MapSettings.getMediumName(MapSettings.MEDIUM_GROUND));
        comboMapType.addItem(MapSettings.getMediumName(MapSettings.MEDIUM_ATMOSPHERE));
        comboMapType.addActionListener(this);
        refreshMapChoice();
    }

    private void setupMapSizes() {
        int oldSelection = comboMapSizes.getSelectedIndex();
        mapSizes = clientgui.getClient().getAvailableMapSizes();
        comboMapSizes.removeActionListener(this);
        comboMapSizes.removeAllItems();
        for (BoardDimensions size : mapSizes) {
            comboMapSizes.addItem(size);
        }
        comboMapSizes.addItem(Messages.getString("ChatLounge.CustomMapSize"));
        comboMapSizes.setSelectedIndex(oldSelection != -1 ? oldSelection : 0);
        comboMapSizes.addActionListener(this);
    }

    private void refreshMapChoice() {
        comboMapType.removeActionListener(this);
        if (mapSettings.getMedium() < MapSettings.MEDIUM_SPACE) {
            comboMapType.setSelectedIndex(mapSettings.getMedium());
        }
        comboMapType.addActionListener(this);
    }

    private void refreshSpaceGround() {
        chkIncludeGround.removeActionListener(this);
        chkIncludeSpace.removeActionListener(this);
        boolean inSpace = mapSettings.getMedium() == MapSettings.MEDIUM_SPACE;
        chkIncludeSpace.setSelected(inSpace);
        chkIncludeGround.setSelected(!inSpace);
        comboMapType.setEnabled(!inSpace);
        butMapSize.setEnabled(!inSpace);
        comboMapSizes.setEnabled(!inSpace);
        buttonBoardPreview.setEnabled(!inSpace);
        lisBoardsSelected.setEnabled(!inSpace);
        butChange.setEnabled(!inSpace);
        lisBoardsAvailable.setEnabled(!inSpace);
        chkRotateBoard.setEnabled(!inSpace);
        butSpaceSize.setEnabled(inSpace);
        chkIncludeGround.addActionListener(this);
        chkIncludeSpace.addActionListener(this);
    }

    private void refreshBoardsAvailable() {
        int selectedRow = lisBoardsAvailable.getSelectedIndex();
        ((DefaultListModel<String>) lisBoardsAvailable.getModel()).removeAllElements();
        for (String s : mapSettings.getBoardsAvailableVector()) {
            ((DefaultListModel<String>) lisBoardsAvailable.getModel()).addElement(s);
        }
        if (resetAvailBoardSelection) {
            lisBoardsAvailable.setSelectedIndex(0);
            resetAvailBoardSelection = false;
        } else {
            lisBoardsAvailable.setSelectedIndex(selectedRow);
        }
    }

    private void refreshBoardsSelected() {
        int selectedRow = lisBoardsSelected.getSelectedIndex();
        ((DefaultListModel<String>) lisBoardsSelected.getModel()).removeAllElements();
        int index = 0;
        for (Iterator<String> i = mapSettings.getBoardsSelected(); i.hasNext();) {
            ((DefaultListModel<String>) lisBoardsSelected.getModel()).addElement(index++ + ": " + i.next()); //$NON-NLS-1$
        }
        lisBoardsSelected.setSelectedIndex(selectedRow);
        if (resetSelectedBoards) {
            lisBoardsSelected.setSelectedIndex(0);
            resetSelectedBoards = false;
        } else {
            lisBoardsSelected.setSelectedIndex(selectedRow);
        }
    }

    /**
     * Fills the Map Buttons scroll pane with the appropriate amount of buttons
     * in the appropriate layout
     */
    private void refreshMapButtons() {
        panMapButtons.removeAll();

        panMapButtons.setLayout(new GridLayout(mapSettings.getMapHeight(), mapSettings.getMapWidth()));

        for (int i = 0; i < mapSettings.getMapHeight(); i++) {
            for (int j = 0; j < mapSettings.getMapWidth(); j++) {
                JButton button = new JButton(Integer.toString((i * mapSettings.getMapWidth()) + j));
                button.addActionListener(this);
                panMapButtons.add(button);
            }
        }

        scrMapButtons.validate();

        labBoardsAvailable.setText(mapSettings.getBoardWidth() + "x" + mapSettings.getBoardHeight() + " "
                + Messages.getString("BoardSelectionDialog.mapsAvailable"));
        comboMapSizes.removeActionListener(this);
        int items = comboMapSizes.getItemCount();

        boolean mapSizeSelected = false;
        for (int i = 0; i < (items - 1); i++) {
            BoardDimensions size = (BoardDimensions) comboMapSizes.getItemAt(i);

            if ((size.width() == mapSettings.getBoardWidth()) && (size.height() == mapSettings.getBoardHeight())) {
                comboMapSizes.setSelectedIndex(i);
                mapSizeSelected = true;
            }
        }
        // If we didn't select a size, select the last item: 'Custom Size'
        if (!mapSizeSelected) {
            comboMapSizes.setSelectedIndex(items - 1);
        }
        comboMapSizes.addActionListener(this);

    }

    public void previewMapsheet() {
        String boardName = lisBoardsAvailable.getSelectedValue();
        if (lisBoardsAvailable.getSelectedIndex() > 2) {
            IBoard board = new Board(16, 17);
            board.load(new MegaMekFile(Configuration.boardsDir(), boardName + ".board").getFile());
            if (chkRotateBoard.isSelected()) {
                BoardUtilities.flip(board, true, true);
            }
            if (board.isValid())
                miniMap.setBoard(board);
        }
    }

    public void previewGameBoard() {
        MapSettings temp = mapSettings;
        temp.replaceBoardWithRandom(MapSettings.BOARD_RANDOM);
        temp.replaceBoardWithRandom(MapSettings.BOARD_SURPRISE);
        IBoard[] sheetBoards = new IBoard[temp.getMapWidth() * temp.getMapHeight()];
        List<Boolean> rotateBoard = new ArrayList<>();
        for (int i = 0; i < (temp.getMapWidth() * temp.getMapHeight()); i++) {
            sheetBoards[i] = new Board();
            String name = temp.getBoardsSelectedVector().get(i);
            boolean isRotated = false;
            if (name.startsWith(Board.BOARD_REQUEST_ROTATION)) {
                // only rotate boards with an even width
                if ((temp.getBoardWidth() % 2) == 0) {
                    isRotated = true;
                }
                name = name.substring(Board.BOARD_REQUEST_ROTATION.length());
            }
            if (name.startsWith(MapSettings.BOARD_GENERATED) || (temp.getMedium() == MapSettings.MEDIUM_SPACE)) {
                sheetBoards[i] = BoardUtilities.generateRandom(temp);
            } else {
                sheetBoards[i].load(new MegaMekFile(Configuration.boardsDir(), name
                        + ".board").getFile());
                BoardUtilities.flip(sheetBoards[i], isRotated, isRotated);
            }
            rotateBoard.add(isRotated);
        }

        IBoard newBoard = BoardUtilities.combine(temp.getBoardWidth(), temp.getBoardHeight(), temp.getMapWidth(),
                temp.getMapHeight(), sheetBoards, rotateBoard, temp.getMedium());
        
        boardPreviewGame.setBoard(newBoard);
        boardPreviewW.setVisible(true);
    }

    /**
     * Refreshes the game settings with new info from the client
     */
    private void refreshGameSettings() {
        refreshTeams();
        refreshDoneButton();
    }

    /**
     * Refreshes the entities from the client
     */
    public void refreshEntities() {
        mekModel.clearData();
        boolean localUnits = false;

        /*
         * We will attempt to sort by the following criteria: My units first,
         * then my teamates units, then other teams units. We will also sort by
         * player name within the forementioned categories. Finally, a players
         * units will be sorted by the order they were "added" to the list.
         */
        ArrayList<Entity> allEntities = new ArrayList<Entity>();
        for (Entity ent : clientgui.getClient().getEntitiesVector()) {
            allEntities.add(ent);
        }

        Collections.sort(allEntities, new Comparator<Entity>() {
            @Override
            public int compare(final Entity a, final Entity b) {
                // entity.getOwner() does not work properly because teams are
                // not updated for
                // entities when the user switches teams
                final IPlayer p_a = clientgui.getClient().getGame().getPlayer(a.getOwnerId());// a.getOwner();
                final IPlayer p_b = clientgui.getClient().getGame().getPlayer(b.getOwnerId());// b.getOwner();
                final IPlayer localPlayer = clientgui.getClient().getLocalPlayer();
                final int t_a = p_a.getTeam();
                final int t_b = p_b.getTeam();
                final int tr_a = a.getTransportId();
                final int tr_b = b.getTransportId();
                if (p_a.equals(localPlayer) && !p_b.equals(localPlayer)) {
                    return -1;
                } else if (!p_a.equals(localPlayer) && p_b.equals(localPlayer)) {
                    return 1;
                } else if ((t_a == localPlayer.getTeam()) && (t_b != localPlayer.getTeam())) {
                    return -1;
                } else if ((t_b == localPlayer.getTeam()) && (t_a != localPlayer.getTeam())) {
                    return 1;
                } else if (t_a != t_b) {
                    return t_a - t_b;
                } else if (!p_a.equals(p_b)) {
                    return p_a.getName().compareTo(p_b.getName());
                } else {
                    int a_id = a.getId();
                    int b_id = b.getId();
                    // loaded units should be put immediately below their parent
                    // unit
                    // if a unit's transport ID is not none, then it should
                    // replace their actual id
                    if (tr_a == tr_b) {
                        // either they are both not being transported, or they
                        // are being transported
                        // by the same unit
                        return a_id - b_id;
                    }

                    if (tr_b != Entity.NONE) {
                        if (tr_b == a_id) {
                            // b is loaded on a
                            return -1;
                        }
                        b_id = tr_b;
                    }
                    if (tr_a != Entity.NONE) {
                        if (tr_a == b_id) {
                            // a is loaded on b
                            return 1;
                        }
                        a_id = tr_a;
                    }
                    return a_id - b_id;
                }
            }
        });

        for (Entity entity : allEntities) {
            // Remember if the local player has units.
            if (!localUnits && entity.getOwner().equals(clientgui.getClient().getLocalPlayer())) {
                localUnits = true;
            }

            if (!clientgui.getClient().getGame().getOptions().booleanOption(OptionsConstants.RPG_PILOT_ADVANTAGES)) { // $NON-NLS-1$
                entity.getCrew().clearOptions(PilotOptions.LVL3_ADVANTAGES);
            }

            if (!clientgui.getClient().getGame().getOptions().booleanOption(OptionsConstants.EDGE)) { // $NON-NLS-1$
                entity.getCrew().clearOptions(PilotOptions.EDGE_ADVANTAGES);
            }

            if (!clientgui.getClient().getGame().getOptions().booleanOption(OptionsConstants.RPG_MANEI_DOMINI)) { // $NON-NLS-1$
                entity.getCrew().clearOptions(PilotOptions.MD_ADVANTAGES);
            }

            if (!clientgui.getClient().getGame().getOptions()
                    .booleanOption(OptionsConstants.ADVANCED_STRATOPS_PARTIALREPAIRS)) { // $NON-NLS-1$
                entity.clearPartialRepairs();
            }
            // Handle the "Blind Drop" option.
            if (!entity.getOwner().equals(clientgui.getClient().getLocalPlayer())
                    && clientgui.getClient().getGame().getOptions().booleanOption(OptionsConstants.BASE_BLIND_DROP) // $NON-NLS-1$
                    && !clientgui.getClient().getGame().getOptions()
                    .booleanOption(OptionsConstants.BASE_REAL_BLIND_DROP)) { // $NON-NLS-1$

                mekModel.addUnit(entity);
            } else if (entity.getOwner().equals(clientgui.getClient().getLocalPlayer())
                    || (!clientgui.getClient().getGame().getOptions().booleanOption(OptionsConstants.BASE_BLIND_DROP) // $NON-NLS-1$
                    && !clientgui.getClient().getGame().getOptions()
                    .booleanOption(OptionsConstants.BASE_REAL_BLIND_DROP))) { // $NON-NLS-1$
                mekModel.addUnit(entity);
            }
        }

        // Enable the "Save Unit List..." and "Delete All"
        // buttons if the local player has units.
        butSaveList.setEnabled(localUnits);
        butDeleteAll.setEnabled(localUnits);
        clientgui.getMenuBar().setUnitList(localUnits);
    }

    public static String formatPilotTooltip(Crew pilot, boolean command, boolean init, boolean tough, boolean rpgSkills) {

        String value = "<html>";
        value += "<b>" + pilot.getDesc() + "</b><br>";
        if (pilot.getNickname().length() > 0) {
            value += "<i>" + pilot.getNickname() + "</i><br>";
        }
        if (pilot.getHits() > 0) {
            value += "<font color='red'>" + Messages.getString("ChatLounge.Hits") + pilot.getHits() + "</font><br>";
        }
        value += "" + pilot.getSkillsAsString(rpgSkills) + "<br>";
        if (tough) {
            value += Messages.getString("ChatLounge.Tough") + pilot.getToughness(0) + "<br>";
        }
        if (command) {
            value += Messages.getString("ChatLounge.Command") + pilot.getCommandBonus() + "<br>";
        }
        if (init) {
            value += Messages.getString("ChatLounge.Initiative") + pilot.getInitBonus() + "<br>";
        }
        value += "<br>";
        for (Enumeration<IOptionGroup> advGroups = pilot.getOptions().getGroups(); advGroups.hasMoreElements();) {
            IOptionGroup advGroup = advGroups.nextElement();
            if (pilot.countOptions(advGroup.getKey()) > 0) {
                value += "<b>" + advGroup.getDisplayableName() + "</b><br>";
                for (Enumeration<IOption> advs = advGroup.getOptions(); advs.hasMoreElements();) {
                    IOption adv = advs.nextElement();
                    if (adv.booleanValue()) {
                        value += "  " + adv.getDisplayableNameWithValue() + "<br>";
                    }
                }
            }
        }
        value += "</html>";
        return value;

    }

    private static StringBuffer tooltipString;
    private static final boolean BR = true;
    private static final boolean NOBR = false;

    /**
     * Adds a resource string to the entity tooltip
     *
     * @param ttSName
     *            The resource string name. "BoardView1.Tooltip." will be added
     *            in front, so "Pilot" will retrieve BoardView1.Tooltip.Pilot
     * @param startBR
     *            = true will start the string with a &lt;BR&gt;; The constants
     *            BR and NOBR can be used here.
     * @param ttO
     *            a list of Objects to insert into the {x} places in the
     *            resource.
     */
    private static void addToTT(String ttSName, boolean startBR, Object... ttO) {
        if (startBR == BR)
            tooltipString.append("<BR>");
        if (ttO != null) {
            tooltipString.append(Messages.getString("BoardView1.Tooltip." + ttSName, ttO));
        } else {
            tooltipString.append(Messages.getString("BoardView1.Tooltip." + ttSName));
        }
    }

    /**
     * Adds a resource string to the entity tooltip
     *
     * @param ttSName
     *            The resource string name. "BoardView1.Tooltip." will be added
     *            in front, so "Pilot" will retrieve BoardView1.Tooltip.Pilot
     * @param startBR
     *            = true will start the string with a &lt;BR&gt;; The constants
     *            BR and NOBR can be used here.
     */
    private static void addToTT(String ttSName, boolean startBR) {
        addToTT(ttSName, startBR, (Object[]) null);
    }

    public static String formatUnitTooltip(Entity entity) {

        GunEmplacement thisGunEmp = null;
        if (entity instanceof GunEmplacement)
            thisGunEmp = (GunEmplacement) entity;

        tooltipString = new StringBuffer();
        tooltipString.append("<HTML>");

        // Unit Chassis and Player
        addToTT("Unit", NOBR, entity.getOwner().getColour().getHexString(),
                entity.getChassis(), entity.getOwner().getName());

        // Pilot Info
        // Nickname > Name > "Pilot"
        for (int i = 0; i < entity.getCrew().getSlotCount(); i++) {
            if (entity.getCrew().isMissing(i)) {
                continue;
            }
            String pnameStr = entity.getCrew().getCrewType().getRoleName(i);

            if ((entity.getCrew().getName(i) != null) && !entity.getCrew().getName(i).equals(""))
                pnameStr = entity.getCrew().getName(i);

            if ((entity.getCrew().getNickname(i) != null) && !entity.getCrew().getNickname(i).equals(""))
                pnameStr = "'" + entity.getCrew().getNickname(i) + "'";

            if (entity.getCrew().getSlotCount() > 1) {
                pnameStr += " (" + entity.getCrew().getCrewType().getRoleName(i) + ")";
            }

            addToTT("Pilot", BR, pnameStr, entity.getCrew().getGunnery(i), entity.getCrew().getPiloting(i));

            // Pilot Status
            if (!entity.getCrew().getStatusDesc(i).equals(""))
                addToTT("PilotStatus", NOBR, entity.getCrew().getStatusDesc(i));
        }

        // Pilot Advantages
        int numAdv = entity.getCrew().countOptions(PilotOptions.LVL3_ADVANTAGES);
        if (numAdv == 1)
            addToTT("Adv1", NOBR, numAdv);
        else if (numAdv > 1)
            addToTT("Advs", NOBR, numAdv);

        // Pilot Manei Domini
        if ((entity.getCrew().countOptions(PilotOptions.MD_ADVANTAGES) > 0))
            addToTT("MD", NOBR);

        // Unit movement ability
        if (thisGunEmp == null) {
            addToTT("Movement", BR, entity.getWalkMP(), entity.getRunMPasString());
            if (entity.getJumpMP() > 0)
                tooltipString.append("/" + entity.getJumpMP());
        }

        // Armor and Internals
        addToTT("ArmorInternals", BR,
                entity.getTotalArmor()
                        + ((entity.getTotalArmor() != entity.getTotalOArmor()) ? "/" + entity.getTotalOArmor() : ""),
                entity.getTotalInternal() + ((entity.getTotalInternal() != entity.getTotalOInternal())
                        ? "/" + entity.getTotalOInternal() : ""));

        // Weapon List
        if (GUIPreferences.getInstance().getBoolean(GUIPreferences.SHOW_WPS_IN_TT)) {

            ArrayList<Mounted> weapons = entity.getWeaponList();
            HashMap<String, Integer> wpNames = new HashMap<String, Integer>();

            // Gather names, counts, Clan/IS
            // When clan then the number will be stored as negative
            for (Mounted curWp : weapons) {
                String weapDesc = curWp.getDesc();
                // Append ranges
                WeaponType wtype = (WeaponType) curWp.getType();
                int ranges[];
                if (entity.isAero()) {
                    ranges = wtype.getATRanges();
                } else {
                    ranges = wtype.getRanges(curWp);
                }
                String rangeString = "(";
                if ((ranges[RangeType.RANGE_MINIMUM] != WeaponType.WEAPON_NA)
                        && (ranges[RangeType.RANGE_MINIMUM] != 0)) {
                    rangeString += ranges[RangeType.RANGE_MINIMUM] + "/";
                } else {
                    rangeString += "-/";
                }
                int maxRange = RangeType.RANGE_LONG;

                if ((entity.getGame() != null)
                        && entity.getGame().getOptions().booleanOption(OptionsConstants.ADVCOMBAT_TACOPS_RANGE)) {
                    maxRange = RangeType.RANGE_EXTREME;
                }
                for (int i = RangeType.RANGE_SHORT; i <= maxRange; i++) {
                    rangeString += ranges[i];
                    if (i != maxRange) {
                        rangeString += "/";
                    }
                }

                weapDesc += rangeString + ")";
                if (wpNames.containsKey(weapDesc)) {
                    int number = wpNames.get(weapDesc);
                    if (number > 0)
                        wpNames.put(weapDesc, number + 1);
                    else
                        wpNames.put(weapDesc, number - 1);
                } else {
                    WeaponType wpT = ((WeaponType) curWp.getType());

                    if (entity.isClan() && TechConstants.isClan(wpT.getTechLevel(entity.getYear())))
                        wpNames.put(weapDesc, -1);
                    else
                        wpNames.put(weapDesc, 1);
                }
            }

            // Print to Tooltip
            tooltipString.append("<FONT SIZE=\"-2\">");

            for (Entry<String, Integer> entry : wpNames.entrySet()) {
                // Check if weapon is destroyed, text gray and strikethrough if
                // so, remove the "x "/"*"
                // Also remove "+", means currently selected for firing
                boolean wpDest = false;
                String nameStr = entry.getKey();
                if (entry.getKey().startsWith("x ")) {
                    nameStr = entry.getKey().substring(2, entry.getKey().length());
                    wpDest = true;
                }

                if (entry.getKey().startsWith("*")) {
                    nameStr = entry.getKey().substring(1, entry.getKey().length());
                    wpDest = true;
                }

                if (entry.getKey().startsWith("+")) {
                    nameStr = entry.getKey().substring(1, entry.getKey().length());
                    nameStr = nameStr.concat(" <I>(Firing)</I>");
                }

                // normal coloring
                tooltipString.append("<FONT COLOR=#8080FF>");
                // but: color gray and strikethrough when weapon destroyed
                if (wpDest)
                    tooltipString.append("<FONT COLOR=#a0a0a0><S>");

                String clanStr = "";
                if (entry.getValue() < 0)
                    clanStr = Messages.getString("BoardView1.Tooltip.Clan");

                // when more than 5 weapons are present, they will be grouped
                // and listed with a multiplier
                if (weapons.size() > 5) {
                    addToTT("WeaponN", BR, Math.abs(entry.getValue()), clanStr, nameStr);

                } else { // few weapons: list each weapon separately
                    for (int i = 0; i < Math.abs(entry.getValue()); i++) {
                        addToTT("Weapon", BR, Math.abs(entry.getValue()), clanStr, nameStr);
                    }
                }
                // Weapon destroyed? End strikethrough
                if (wpDest)
                    tooltipString.append("</S>");
                tooltipString.append("</FONT>");
            }
            tooltipString.append("</FONT>");
        }

        // Add StratOps quirks, if activated
        if ((entity.getGame() != null)
                && entity.getGame().getOptions().booleanOption(OptionsConstants.ADVANCED_STRATOPS_QUIRKS)) {
            for (Enumeration<IOptionGroup> advGroups = entity.getQuirks().getGroups(); advGroups.hasMoreElements();) {
                IOptionGroup advGroup = advGroups.nextElement();
                if (entity.countQuirks(advGroup.getKey()) > 0) {
                    tooltipString.append("<BR><i>" + advGroup.getDisplayableName() + ":</i>");
                    for (Enumeration<IOption> advs = advGroup.getOptions(); advs.hasMoreElements();) {
                        IOption adv = advs.nextElement();
                        if (adv.booleanValue()) {
                            tooltipString.append("<BR>&nbsp;" + adv.getDisplayableNameWithValue());
                        }
                    }
                }
            }
            for (Mounted weapon : entity.getWeaponList()) {
                for (Enumeration<IOptionGroup> advGroups = weapon.getQuirks().getGroups(); advGroups
                        .hasMoreElements();) {
                    IOptionGroup advGroup = advGroups.nextElement();
                    if (weapon.countQuirks() > 0) {
                        tooltipString.append("<BR><i>" + weapon.getDesc() + ":</i>");
                        for (Enumeration<IOption> advs = advGroup.getOptions(); advs.hasMoreElements();) {
                            IOption adv = advs.nextElement();
                            if (adv.booleanValue()) {
                                tooltipString.append("<BR>&nbsp;" + adv.getDisplayableNameWithValue());
                            }
                        }
                    }
                }
            }
        }

        // Add partial repairs, if activated
        for (Enumeration<IOptionGroup> advGroups = entity.getPartialRepairs().getGroups(); advGroups
                .hasMoreElements();) {
            IOptionGroup advGroup = advGroups.nextElement();
            if (entity.countPartialRepairs() > 0) {
                tooltipString.append("<BR><i>" + advGroup.getDisplayableName() + ":</i><br>");
                for (Enumeration<IOption> advs = advGroup.getOptions(); advs.hasMoreElements();) {
                    IOption adv = advs.nextElement();
                    if (adv.booleanValue()) {
                        tooltipString.append("&nbsp;" + adv.getDisplayableNameWithValue() + "<br>");
                    }
                }
            }
        }

        tooltipString.append("</html>");
        return tooltipString.toString();
    }

    /**
     * Refreshes the player info
     */
    public void refreshPlayerInfo() {
        playerModel.clearData();
        for (Enumeration<IPlayer> i = clientgui.getClient().getPlayers(); i.hasMoreElements();) {
            final IPlayer player = i.nextElement();
            if (player == null) {
                continue;
            }
            playerModel.addPlayer(player);
        }
    }

    private void refreshCamos() {
        butCamo.setIcon(getPlayerSelected().getLocalPlayer().getCamouflage().getImageIcon());
    }

    /**
     * Setup the team choice box
     */
    private void setupTeams() {
        choTeam.removeAllItems();
        for (int i = 0; i < IPlayer.MAX_TEAMS; i++) {
            choTeam.addItem(IPlayer.teamNames[i]);
        }
        if (clientgui.getClient().getLocalPlayer() != null) {
            choTeam.setSelectedIndex(clientgui.getClient().getLocalPlayer().getTeam());
        } else {
            choTeam.setSelectedIndex(0);
        }
    }

    /**
     * Highlight the team the player is playing on.
     */
    private void refreshTeams() {
        choTeam.setSelectedIndex(clientgui.getClient().getLocalPlayer().getTeam());
    }

    /**
     * Refreshes the done button. The label will say the opposite of the
     * player's "done" status, indicating that clicking it will reverse the
     * condition.
     */
    private void refreshDoneButton(boolean done) {
        butDone.setText(done ? Messages.getString("ChatLounge.notDone") : Messages.getString("ChatLounge.imDone"));
        // $NON-NLS-1$ //$NON-NLS-2$
    }

    private void refreshDoneButton() {
        refreshDoneButton(clientgui.getClient().getLocalPlayer().isDone());
    }

    /**
     * Change local player team.
     */
    private void changeTeam(int team) {
        Client c = getPlayerSelected();
        if ((c != null) && (c.getLocalPlayer().getTeam() != team)) {
            c.getLocalPlayer().setTeam(team);
            c.sendPlayerInfo();

            // WIP on getting entities to be able to be loaded by teammates
            for (Entity unit : c.getGame().getPlayerEntities(c.getLocalPlayer(), false)) {
                // If unit has empty bays it needs to be updated in order for
                // other entities to be able to load into it.
                if ((unit.getTransports().size() > 0) && (unit.getLoadedUnits().isEmpty())
                        && (unit.getTransportId() == Entity.NONE)) {
                    c.sendUpdateEntity(unit);
                }

                // Unload this unit if its being transported.
                if (unit.getTransportId() != Entity.NONE) {
                    unloader(unit);
                }
            }

            // Loop through everyone elses entities and if they no longer have a
            // legal loading, remove them.
            // I am aware this is an odd way to do it, however I couldn't get it
            // to work by looping through the
            // unit.getLoadedUnits() - it always returned with an empty list
            // even when there was loaded units.
            for (Entity unit : c.getGame().getEntitiesVector()) {
                if (unit.getOwner().equals(c.getLocalPlayer())) {
                    continue;
                }

                if ((unit.getTransportId() != Entity.NONE) && (c.getGame().getEntity(unit.getTransportId()).getOwner()
                        .getTeam() != unit.getOwner().getTeam())) {
                    unloader(unit);
                }
            }
        }
    }

    /**
     * Pop up the customize mech dialog
     */

    private void customizeMech() {
        if (tableEntities.getSelectedRow() == -1) {
            return;
        }
        customizeMech(mekModel.getEntityAt(tableEntities.getSelectedRow()));
    }

    /**
     * Load one unit into another in the chat lounge
     *
     * @param loadee
     *            - an Entity that should be loaded
     * @param loaderId
     *            - the id of the entity that will load
     */
    public void loader(Entity loadee, int loaderId, int bayNumber) {
        Client c = clientgui.getBots().get(loadee.getOwner().getName());
        if (c == null) {
            c = clientgui.getClient();
        }
        Entity loader = clientgui.getClient().getGame().getEntity(loaderId);
        if (loader == null) {
            return;
        }

        // We need to make sure our current bomb choices fit onto the new
        // fighter
        if (loader instanceof FighterSquadron) {
            FighterSquadron fSquad = (FighterSquadron) loader;
            // We can't use Aero.getBombPoints() because the bombs haven't been
            // loaded yet, only selected, so we have to count the choices
            int[] bombChoice = fSquad.getBombChoices();
            int numLoadedBombs = 0;
            for (int i = 0; i < bombChoice.length; i++) {
                numLoadedBombs += bombChoice[i];
            }
            // We can't load all of the squadrons bombs
            if (numLoadedBombs > ((IBomber)loadee).getMaxBombPoints()) {
                JOptionPane.showMessageDialog(clientgui.frame, Messages.getString("FighterSquadron.bomberror"),
                        Messages.getString("FighterSquadron.error"), JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        c.sendLoadEntity(loadee.getId(), loaderId, bayNumber);
        // TODO: it would probably be a good idea to reset deployment
        // info to equal that of the loader, and disable it in customMechDialog
        // I tried doing this but I cant quite figure out the client/server
        // interaction in CustomMechDialog.java
    }

    /**
     * Unload a unit in the chat lounge
     *
     * @param unloadee
     *            - the Entity to be unloaded
     */
    public void unloader(Entity unloadee) {
        Client c = clientgui.getBots().get(unloadee.getOwner().getName());
        if (c == null) {
            c = clientgui.getClient();
        }
        Entity unloader = clientgui.getClient().getGame().getEntity(unloadee.getTransportId());
        if (null == unloader) {
            return;
        }
        unloader.unload(unloadee);
        unloadee.setTransportId(Entity.NONE);
        c.sendUpdateEntity(unloadee);
        c.sendUpdateEntity(unloader);
    }

    /**
     * swap pilots from one entity to another
     *
     * @param swapee
     *            - an Entity that should be swapped from
     * @param swapperId
     *            - the id of the entity that should be swapped to
     */
    public void swapPilots(Entity swapee, int swapperId) {
        Client c = clientgui.getBots().get(swapee.getOwner().getName());
        if (c == null) {
            c = clientgui.getClient();
        }
        Entity swapper = clientgui.getClient().getGame().getEntity(swapperId);
        if (swapper == null) {
            return;
        }
        Crew temp = swapper.getCrew();
        swapper.setCrew(swapee.getCrew());
        swapee.setCrew(temp);
        c.sendUpdateEntity(swapee);
        c.sendUpdateEntity(swapper);
    }

    /**
     * Change the entities controller from one player to another
     *
     * @param e
     *            - an Entity that should that will have its owner changed
     * @param player_id
     *            - the id of the player that should now own the entity
     */
    public void changeEntityOwner(Entity e, int player_id) {
        Client c = clientgui.getBots().get(e.getOwner().getName());
        if (c == null) {
            c = clientgui.getClient();
        }
        IPlayer new_owner = c.getGame().getPlayer(player_id);
        // We if the unit is switching teams, we need to unload it
        if (e.getOwner().getTeam() != new_owner.getTeam()) {
            List<Entity> loadedUnits = e.getLoadedUnits();
            for (Entity loadee : loadedUnits) {
                unloader(loadee);
            }
        }
        e.setOwner(new_owner);
        c.sendUpdateEntity(e);
    }

    /**
     * Delete an entity from the lobby
     *
     * @param entity
     */
    public void delete(Entity entity) {
        Client c = clientgui.getBots().get(entity.getOwner().getName());
        if (c == null) {
            c = clientgui.getClient();
        }
        // first unload any units from this unit
        if (entity.getLoadedUnits().size() > 0) {
            for (Entity loaded : entity.getLoadedUnits()) {
                entity.unload(loaded);
                loaded.setTransportId(Entity.NONE);
                c.sendUpdateEntity(loaded);
            }
            c.sendUpdateEntity(entity);
        }
        // unload this unit from any other units it might be loaded onto
        if (entity.getTransportId() != Entity.NONE) {
            Entity loader = clientgui.getClient().getGame().getEntity(entity.getTransportId());
            if (null != loader) {
                loader.unload(entity);
                entity.setTransportId(Entity.NONE);
                c.sendUpdateEntity(loader);
                c.sendUpdateEntity(entity);
            }
        }
        c.sendDeleteEntity(entity.getId());
    }

    /**
     *
     * @param entities
     */
    public void customizeMechs(List<Entity> entities) {
        // Only call this for when selecting a valid list of entities
        if (entities.size() < 1) {
            return;
        }
        Set<String> owners = new HashSet<>();
        String ownerName = "";
        int ownerId = -1;
        for (Entity e : entities) {
            ownerName = e.getOwner().getName();
            ownerId = e.getOwner().getId();
            owners.add(ownerName);
        }

        // Error State
        if (owners.size() > 1) {
            return;
        }

        boolean editable = clientgui.getBots().get(ownerName) != null;
        Client client;
        if (editable) {
            client = clientgui.getBots().get(ownerName);
        } else {
            editable |= ownerId == clientgui.getClient().getLocalPlayer().getId();
            client = clientgui.getClient();
        }

        CustomMechDialog cmd = new CustomMechDialog(clientgui, client, entities, editable);
        cmd.setSize(new Dimension(GUIPreferences.getInstance().getCustomUnitWidth(),
                GUIPreferences.getInstance().getCustomUnitHeight()));
        cmd.setTitle(Messages.getString("ChatLounge.CustomizeUnits")); //$NON-NLS-1$
        cmd.setVisible(true);
        GUIPreferences.getInstance().setCustomUnitHeight(cmd.getSize().height);
        GUIPreferences.getInstance().setCustomUnitWidth(cmd.getSize().width);
        if (editable && cmd.isOkay()) {
            // send changes
            for (Entity entity : entities) {
                // If a LAM with mechanized BA was changed to non-mech mode, unload the BA.
                if ((entity instanceof LandAirMech)
                        && entity.getConversionMode() != LandAirMech.CONV_MODE_MECH) {
                    for (Entity loadee : entity.getLoadedUnits()) {
                        entity.unload(loadee);
                        loadee.setTransportId(Entity.NONE);
                        client.sendUpdateEntity(loadee);
                    }
                }

                client.sendUpdateEntity(entity);

                // Changing state to a transporting unit can update state of
                // transported units, so update those as well
                for (Transporter transport : entity.getTransports()) {
                    for (Entity loaded : transport.getLoadedUnits()) {
                        client.sendUpdateEntity(loaded);
                    }
                }

                // Customizations to a Squadron can effect the fighters
                if (entity instanceof FighterSquadron) {
                    entity.getSubEntities().ifPresent(ents -> ents.forEach(client::sendUpdateEntity));
                }
            }
        }
        if (cmd.isOkay() && (cmd.getStatus() != CustomMechDialog.DONE)) {
            Entity nextEnt = cmd.getNextEntity(cmd.getStatus() == CustomMechDialog.NEXT);
            customizeMech(nextEnt);
        }
    }

    public void setCMDSelectedTab(String tab) {
        cmdSelectedTab = tab;
    }

    /**
     *
     * @param entity
     */
    public void customizeMech(Entity entity) {
        boolean editable = clientgui.getBots().get(entity.getOwner().getName()) != null;
        Client c;
        if (editable) {
            c = clientgui.getBots().get(entity.getOwner().getName());
        } else {
            editable |= entity.getOwnerId() == clientgui.getClient().getLocalPlayer().getId();
            c = clientgui.getClient();
        }
        // When we customize a single entity's C3 network setting,
        // **ALL** members of the network may get changed.
        Entity c3master = entity.getC3Master();
        ArrayList<Entity> c3members = new ArrayList<>();
        Iterator<Entity> playerUnits = c.getGame().getPlayerEntities(c.getLocalPlayer(), false).iterator();
        while (playerUnits.hasNext()) {
            Entity unit = playerUnits.next();
            if (!entity.equals(unit) && entity.onSameC3NetworkAs(unit)) {
                c3members.add(unit);
            }
        }

        boolean doneCustomizing = false;
        while (!doneCustomizing) {
            // display dialog
            List<Entity> entities = new ArrayList<>();
            entities.add(entity);
            CustomMechDialog cmd = new CustomMechDialog(clientgui, c, entities, editable);
            cmd.setSize(new Dimension(GUIPreferences.getInstance().getCustomUnitWidth(),
                    GUIPreferences.getInstance().getCustomUnitHeight()));
            cmd.refreshOptions();
            cmd.refreshQuirks();
            cmd.refreshPartReps();
            cmd.setTitle(entity.getShortName());
            if (cmdSelectedTab != null) {
                cmd.setSelectedTab(cmdSelectedTab);
            }
            cmd.setVisible(true);
            GUIPreferences.getInstance().setCustomUnitHeight(cmd.getSize().height);
            GUIPreferences.getInstance().setCustomUnitWidth(cmd.getSize().width);
            cmdSelectedTab = cmd.getSelectedTab();
            if (editable && cmd.isOkay()) {
                // If a LAM with mechanized BA was changed to non-mech mode, unload the BA.
                if ((entity instanceof LandAirMech)
                        && entity.getConversionMode() != LandAirMech.CONV_MODE_MECH) {
                    for (Entity loadee : entity.getLoadedUnits()) {
                        entity.unload(loadee);
                        loadee.setTransportId(Entity.NONE);
                        c.sendUpdateEntity(loadee);
                    }
                }

                // send changes
                c.sendUpdateEntity(entity);

                // Changing state to a transporting unit can update state of
                // transported units, so update those as well
                for (Transporter transport : entity.getTransports()) {
                    for (Entity loaded : transport.getLoadedUnits()) {
                        c.sendUpdateEntity(loaded);
                    }
                }

                // Customizations to a Squadron can effect the fighters
                if (entity instanceof FighterSquadron) {
                    entity.getSubEntities().ifPresent(ents -> ents.forEach(c::sendUpdateEntity));
                }

                // Do we need to update the members of our C3 network?
                if (((c3master != null) && !c3master.equals(entity.getC3Master()))
                        || ((c3master == null) && (entity.getC3Master() != null))) {
                    for (Entity unit : c3members) {
                        c.sendUpdateEntity(unit);
                    }
                }
            }
            if (cmd.isOkay() && (cmd.getStatus() != CustomMechDialog.DONE)) {
                entity = cmd.getNextEntity(cmd.getStatus() == CustomMechDialog.NEXT);
            } else {
                doneCustomizing = true;
            }
        }
    }

    /** 
     * Displays a CamoChooserDialog to choose an individual camo for
     * the given vector of entities. The camo will only be applied
     * to units configurable by the local player, i.e. his own units
     * or those of his bots.
     */
    public void mechCamo(Vector<Entity> entities) {
        if (entities.size() < 1) {
            return;
        }

        final Entity entity = entities.get(0);
        // Display the CamoChooserDialog and await the result
        // The dialog is preset to the first selected unit's settings
        CamoChooserDialog ccd = new CamoChooserDialog(clientgui.getFrame(),
                entity.getCamouflageOrElse(entity.getOwner().getCamouflage()), true);

        // If the dialog was canceled or nothing was selected, do nothing
        if ((ccd.showDialog() == JOptionPane.CANCEL_OPTION) || (ccd.getSelectedItem() == null)) {
            return;
        }

        // Choosing the player camouflage resets the units to have no individual camouflage.
        final Camouflage selectedItem = ccd.getSelectedItem();
        final boolean noIndividualCamo = selectedItem.equals(entity.getOwner().getCamouflage());
        // Update all allowed entities with the camouflage
        for (Entity ent : entities) {
            if (isEditable(ent)) {
                ent.setCamouflage(noIndividualCamo ? new Camouflage() : selectedItem.clone());
                getLocalClient(ent).sendUpdateEntity(ent);
            }
        }
    }
    
    /** 
     * Returns true when the given entity may be configured
     * by the local player, i.e. if it is his own unit or one
     * of his bot's units.
     */
    private boolean isEditable(Entity entity) {
        return clientgui.getBots().containsKey(entity.getOwner().getName())
                || (entity.getOwnerId() == clientgui.getClient().getLocalPlayer().getId());
    }
    
    /** 
     * Returns the Client associated with a given entity that may be configured
     * by the local player (his own unit or one of his bot's units).
     */
    private Client getLocalClient(Entity entity) {
        if (clientgui.getBots().containsKey(entity.getOwner().getName())) {
            return clientgui.getBots().get(entity.getOwner().getName());
        } else {
            return clientgui.getClient();
        }
    }

    public void mechEdit(Entity entity) {
        boolean editable = clientgui.getBots().get(entity.getOwner().getName()) != null;
        Client c;
        if (editable) {
            c = clientgui.getBots().get(entity.getOwner().getName());
        } else {
            editable |= entity.getOwnerId() == clientgui.getClient().getLocalPlayer().getId();
            c = clientgui.getClient();
        }

        // display dialog
        UnitEditorDialog med = new UnitEditorDialog(clientgui.getFrame(), entity);
        // med.setPlayer(c.getLocalPlayer());
        med.setVisible(true);
        c.sendUpdateEntity(entity);
        /*
         * if (editable && med.isSelect()) { // send changes
         * c.sendUpdateEntity(entity); }
         */
    }

    public void customizePlayer() {
        Client c = getPlayerSelected();
        if (null != c) {
            PlayerSettingsDialog psd = new PlayerSettingsDialog(clientgui, c);
            psd.setVisible(true);
        }
    }

    /**
     * Pop up the view mech dialog
     */
    public void mechReadout(Entity entity) {
        final JDialog dialog = new JDialog(clientgui.frame, Messages.getString("ChatLounge.quickView"), false); //$NON-NLS-1$
        dialog.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_SPACE) {
                    e.consume();
                    dialog.setVisible(false);
                } else if (code == KeyEvent.VK_ENTER) {
                    e.consume();
                    dialog.setVisible(false);
                }
            }
        });
        // FIXME: this isn't working right, but is necessary for the key
        // listener to work right
        // dialog.setFocusable(true);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialog.setVisible(false);
            }
        });
        MechViewPanel mvp = new MechViewPanel();
        mvp.setMech(entity);
        JButton btn = new JButton(Messages.getString("Okay")); //$NON-NLS-1$
        btn.addActionListener(e -> dialog.setVisible(false));

        dialog.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints c;

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 1.0;
        c.weighty = 1.0;
        dialog.getContentPane().add(mvp, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.0;
        c.weighty = 0.0;
        dialog.getContentPane().add(btn, c);
        dialog.setSize(mvp.getBestWidth(), mvp.getBestHeight() + 75);
        dialog.validate();
        dialog.setVisible(true);
    }

    /**
     * @param entity the entity to display the BV Calculation for
     */
    public void mechBVDisplay(Entity entity) {
        final JDialog dialog = new JDialog(clientgui.frame, "BV Calculation Display", false);
        dialog.getContentPane().setLayout(new GridBagLayout());

        final int width = 500;
        final int height = 400;
        Dimension size = new Dimension(width, height);

        JEditorPane tEditorPane = new JEditorPane();
        tEditorPane.setContentType("text/html");
        tEditorPane.setEditable(false);
        tEditorPane.setBorder(null);
        entity.calculateBattleValue();
        tEditorPane.setText(entity.getBVText());
        tEditorPane.setCaretPosition(0);

        JScrollPane tScroll = new JScrollPane(tEditorPane,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tScroll.setBorder(null);
        tScroll.setPreferredSize(size);
        tScroll.setMinimumSize(size);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.weighty = 1.0;
        dialog.getContentPane().add(tScroll, gridBagConstraints);

        JButton button = new JButton(Messages.getString("Okay"));
        button.addActionListener(e -> dialog.setVisible(false));
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.weighty = 0.0;
        dialog.getContentPane().add(button, gridBagConstraints);

        dialog.setSize(new Dimension(width + 25, height + 75));
        dialog.validate();
        dialog.setVisible(true);
    }

    /**
     * Pop up the dialog to load a mech
     */
    private void loadMech() {
        clientgui.getMechSelectorDialog().updateOptionValues();
        clientgui.getMechSelectorDialog().setVisible(true);
    }

    public void loadFS(Vector<Integer> fighterIds) {
        String name = JOptionPane.showInputDialog(clientgui.frame, "Choose a squadron designation");
        if ((name == null) || (name.trim().length() == 0)) {
            name = "Flying Circus";
        }
        FighterSquadron fs = new FighterSquadron(name);
        fs.setOwner(clientgui.getClient().getGame().getEntity(fighterIds.firstElement()).getOwner());
        clientgui.getClient().sendAddSquadron(fs, fighterIds);
    }

    private void loadArmy() {
        clientgui.getRandomArmyDialog().setVisible(true);
    }

    public void loadRandomSkills() {
        clientgui.getRandomSkillDialog().showDialog(clientgui.getClient().getGame().getEntitiesVector());
    }

    public void loadRandomNames() {
        clientgui.getRandomNameDialog().showDialog(clientgui.getClient().getGame().getEntitiesVector());
    }

    /**
     * Changes all selected boards to be the specified board
     */
    private void changeMap(String board) {
        int[] selected = lisBoardsSelected.getSelectedIndices();
        for (final int newVar : selected) {
            String name = board;
            if (!MapSettings.BOARD_RANDOM.equals(name) && !MapSettings.BOARD_SURPRISE.equals(name)
                    && chkRotateBoard.isSelected()) {
                name = Board.BOARD_REQUEST_ROTATION + name;
            }

            // Validate the map
            IBoard b = new Board(16, 17);
            if (!MapSettings.BOARD_GENERATED.equals(board) && !MapSettings.BOARD_RANDOM.equals(board)
                    && !MapSettings.BOARD_SURPRISE.equals(board)) {
                b.load(new MegaMekFile(Configuration.boardsDir(), board + ".board").getFile());
                if (!b.isValid()) {
                    JOptionPane.showMessageDialog(this, "The Selected board is invalid, please select another.");
                    return;
                }
            }
            ((DefaultListModel<String>) lisBoardsSelected.getModel()).setElementAt(newVar + ": " + name, newVar); //$NON-NLS-1$
            mapSettings.getBoardsSelectedVector().set(newVar, name);
        }
        lisBoardsSelected.setSelectedIndices(selected);
        clientgui.getClient().sendMapSettings(mapSettings);
        if (boardPreviewW.isVisible()) {
            previewGameBoard();
        }
    }

    //
    // GameListener
    //
    @Override
    public void gamePlayerChange(GamePlayerChangeEvent e) {
        // Are we ignoring events?
        if (isIgnoringEvents()) {
            return;
        }
        refreshDoneButton();
        clientgui.getClient().getGame().setupTeams();
        refreshPlayerInfo();
        refreshEntities();
    }

    @Override
    public void gamePhaseChange(GamePhaseChangeEvent e) {
        // Are we ignoring events?
        if (isIgnoringEvents()) {
            return;
        }
        if (clientgui.getClient().getGame().getPhase() == IGame.Phase.PHASE_LOUNGE) {
            refreshDoneButton();
            refreshGameSettings();
            refreshPlayerInfo();
            refreshTeams();
            refreshCamos();
            refreshEntities();
        }
    }

    @Override
    public void gameEntityNew(GameEntityNewEvent e) {
        // Are we ignoring events?
        if (isIgnoringEvents()) {
            return;
        }
        refreshEntities();
        refreshPlayerInfo();
    }

    @Override
    public void gameEntityRemove(GameEntityRemoveEvent e) {
        // Are we ignoring events?
        if (isIgnoringEvents()) {
            return;
        }
        refreshEntities();
        refreshPlayerInfo();
    }

    @Override
    public void gameSettingsChange(GameSettingsChangeEvent e) {
        // Are we ignoring events?
        if (isIgnoringEvents()) {
            return;
        }
        refreshGameSettings();
        refreshEntities();
        refreshPlayerInfo();
        setupMapSizes();
    }

    @Override
    public void gameClientFeedbackRequest(GameCFREvent evt) {
        // Do nothing
    }

    /*
     * NOTE: On linux, this gets called even when programatically updating the
     * list box selected item. Do not let this go into an infinite loop. Do not
     * update the selected item (even indirectly, by sending player info) if it
     * is already selected.
     */
    @Override
    public void itemStateChanged(ItemEvent ev) {

        // Are we ignoring events?
        if (isIgnoringEvents()) {
            return;
        }

        if (ev.getSource().equals(choTeam)) {
            changeTeam(choTeam.getSelectedIndex());
        }
    }

    //
    // ActionListener
    //
    @Override
    public void actionPerformed(ActionEvent ev) {

        // Are we ignoring events?
        if (isIgnoringEvents()) {
            return;
        }

        if (ev.getSource().equals(butLoad)) {
            loadMech();
        } else if (ev.getSource().equals(butArmy)) {
            loadArmy();
        } else if (ev.getSource().equals(butSkills)) {
            loadRandomSkills();
        } else if (ev.getSource().equals(butNames)) {
            loadRandomNames();
        } else if (ev.getSource().equals(tableEntities)) {
            customizeMech();
        } else if (ev.getSource().equals(tablePlayers)) {
            customizePlayer();
        } else if (ev.getSource().equals(butDeleteAll)) {
            // Build a Vector of this player's entities.
            Client c = getPlayerSelected();
            if (c == null) {
                clientgui.doAlertDialog(Messages.getString("ChatLounge.ImproperCommand"),
                        Messages.getString("ChatLounge.SelectBotOrPlayer")); //$NON-NLS-1$ //$NON-NLS-2$
                return;
            }
            clientgui.deleteAllUnits(c);
        } else if (ev.getSource().equals(butOptions)) {
            // Make sure the game options dialog is editable.
            if (!clientgui.getGameOptionsDialog().isEditable()) {
                clientgui.getGameOptionsDialog().setEditable(true);
            }
            // Display the game options dialog.
            clientgui.getGameOptionsDialog().update(clientgui.getClient().getGame().getOptions());
            clientgui.getGameOptionsDialog().setVisible(true);
        } else if (ev.getSource().equals(butCompact)) {
            if (butCompact.isSelected()) {
                tableEntities.setRowHeight(15);
            } else {
                tableEntities.setRowHeight(80);
            }
            refreshEntities();
        } else if (ev.getSource().equals(butChangeStart)) {
            clientgui.getStartingPositionDialog().update();
            Client c = getPlayerSelected();
            if (c == null) {
                clientgui.doAlertDialog(Messages.getString("ChatLounge.ImproperCommand"),
                        Messages.getString("ChatLounge.SelectBotOrPlayer")); //$NON-NLS-1$ //$NON-NLS-2$
                return;
            }
            clientgui.getStartingPositionDialog().setClient(c);
            clientgui.getStartingPositionDialog().setVisible(true);
        } else if (ev.getSource().equals(butLoadList)) {
            // Allow the player to replace their current
            // list of entities with a list from a file.
            Client c = getPlayerSelected();
            if (c == null) {
                clientgui.doAlertDialog(Messages.getString("ChatLounge.ImproperCommand"),
                        Messages.getString("ChatLounge.SelectBotOrPlayer")); //$NON-NLS-1$ //$NON-NLS-2$
                return;
            }
            clientgui.loadListFile(c.getLocalPlayer());
        } else if (ev.getSource().equals(butSaveList)) {
            // Allow the player to save their current
            // list of entities to a file.
            Client c = getPlayerSelected();
            if (c == null) {
                clientgui.doAlertDialog(Messages.getString("ChatLounge.ImproperCommand"),
                        Messages.getString("ChatLounge.SelectBotOrPlayer")); //$NON-NLS-1$ //$NON-NLS-2$
                return;
            }
            clientgui.saveListFile(c.getGame().getPlayerEntities(c.getLocalPlayer(), false),
                    c.getLocalPlayer().getName());
        } else if (ev.getSource().equals(butAddBot)) {
            BotConfigDialog bcd = new BotConfigDialog(clientgui.frame);
            bcd.setVisible(true);
            if (bcd.dialogAborted) {
                return; // user didn't click 'ok', add no bot
            }
            if (clientgui.getBots().containsKey(bcd.getBotName())) {
                clientgui.doAlertDialog(Messages.getString("ChatLounge.AlertExistsBot.title"),
                        Messages.getString("ChatLounge.AlertExistsBot.message")); //$NON-NLS-1$ //$NON-NLS-2$
            } else {
                BotClient c = bcd.getSelectedBot(clientgui.getClient().getHost(), clientgui.getClient().getPort());
                c.setClientGUI(clientgui);
                c.getGame().addGameListener(new BotGUI(c));
                try {
                    c.connect();
                } catch (Exception e) {
                    clientgui.doAlertDialog(Messages.getString("ChatLounge.AlertBot.title"),
                            Messages.getString("ChatLounge.AlertBot.message")); //$NON-NLS-1$ //$NON-NLS-2$
                }
                clientgui.getBots().put(bcd.getBotName(), c);
            }
        } else if (ev.getSource().equals(butRemoveBot)) {
            Client c = getPlayerSelected();
            if ((c == null) || c.equals(clientgui.getClient())) {
                clientgui.doAlertDialog(Messages.getString("ChatLounge.ImproperCommand"),
                        Messages.getString("ChatLounge.SelectBo")); //$NON-NLS-1$ //$NON-NLS-2$
                return;
            }
            c.die();
            clientgui.getBots().remove(c.getName());
        } else if (ev.getSource() == butConditions) {
            clientgui.getPlanetaryConditionsDialog().update(clientgui.getClient().getGame().getPlanetaryConditions());
            clientgui.getPlanetaryConditionsDialog().setVisible(true);
        } else if (ev.getSource() == butRandomMap) {
            RandomMapDialog rmd = new RandomMapDialog(clientgui.frame, this, clientgui.getClient(), mapSettings);
            rmd.activateDialog(clientgui.getBoardView().getTilesetManager().getThemes());
        } else if (ev.getSource().equals(butChange)) {
            if (lisBoardsAvailable.getSelectedIndex() != -1) {
                changeMap(lisBoardsAvailable.getSelectedValue());
                lisBoardsSelected.setSelectedIndex(lisBoardsSelected.getSelectedIndex() + 1);
            }
        } else if (ev.getSource().equals(buttonBoardPreview)) {
            previewGameBoard();
        } else if (ev.getSource().equals(butMapSize) || ev.getSource().equals(butSpaceSize)) {
            MapDimensionsDialog mdd = new MapDimensionsDialog(clientgui, mapSettings);
            mdd.setVisible(true);
        } else if (ev.getSource().equals(comboMapSizes)) {
            if ((comboMapSizes.getSelectedItem() != null)
                    && !comboMapSizes.getSelectedItem().equals(Messages.getString("ChatLounge.CustomMapSize"))) {
                BoardDimensions size = (BoardDimensions) comboMapSizes.getSelectedItem();
                mapSettings.setBoardSize(size.width(), size.height());
                resetAvailBoardSelection = true;
                resetSelectedBoards = true;
                clientgui.getClient().sendMapSettings(mapSettings);
            }
        } else if (ev.getSource().equals(chkRotateBoard) && (lisBoardsAvailable.getSelectedIndex() != -1)) {
            previewMapsheet();
        } else if (ev.getSource().equals(comboMapType)) {
            mapSettings.setMedium(comboMapType.getSelectedIndex());
            clientgui.getClient().sendMapSettings(mapSettings);
        } else if (ev.getSource().equals(chkIncludeGround)) {
            if (chkIncludeGround.isSelected()) {
                mapSettings.setMedium(comboMapType.getSelectedIndex());
            } else {
                mapSettings.setMedium(MapSettings.MEDIUM_SPACE);
                // set default size for space maps
                mapSettings.setBoardSize(50, 50);
                mapSettings.setMapSize(1, 1);
            }
            clientgui.getClient().sendMapDimensions(mapSettings);
        } else if (ev.getSource().equals(chkIncludeSpace)) {
            if (chkIncludeSpace.isSelected()) {
                mapSettings.setMedium(MapSettings.MEDIUM_SPACE);
                // set default size for space maps
                mapSettings.setBoardSize(50, 50);
                mapSettings.setMapSize(1, 1);
            } else {
                mapSettings.setMedium(comboMapType.getSelectedIndex());
            }
            clientgui.getClient().sendMapDimensions(mapSettings);
        }
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        if ((arg0.getClickCount() == 1) && arg0.getSource().equals(lisBoardsAvailable)) {
            previewMapsheet();
        }
        if ((arg0.getClickCount() == 2) && arg0.getSource().equals(lisBoardsAvailable)) {
            if (lisBoardsAvailable.getSelectedIndex() != -1) {
                changeMap(lisBoardsAvailable.getSelectedValue());
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // ignore
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // ignore
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        // ignore
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // ignore
    }

    /**
     * Updates to show the map settings that have, presumably, just been sent by
     * the server.
     */
    @Override
    public void updateMapSettings(MapSettings newSettings) {
        mapSettings = MapSettings.getInstance(newSettings);
        refreshMapButtons();
        refreshMapChoice();
        refreshSpaceGround();
        refreshBoardsSelected();
        refreshBoardsAvailable();
        refreshMapSummaryLabel();
        refreshGameYearLabel();
        refreshTechLevelLabel();
    }

    public void refreshMapSummaryLabel() {
        String txt = Messages.getString("ChatLounge.MapSummary"); //$NON-NLS-1$
        txt = txt + " " //$NON-NLS-1$
                + (mapSettings.getBoardWidth() * mapSettings.getMapWidth()) + " x " //$NON-NLS-1$
                + (mapSettings.getBoardHeight() * mapSettings.getMapHeight());
        if (chkIncludeGround.isSelected()) {
            txt = txt + " " + comboMapType.getSelectedItem();
        } else {
            txt = txt + " " + "Space Map"; //$NON-NLS-1$
        }
        lblMapSummary.setText(txt);

        StringBuilder selectedMaps = new StringBuilder();
        selectedMaps.append("<html>"); //$NON-NLS-1$
        selectedMaps.append(Messages.getString("ChatLounge.MapSummarySelectedMaps"));
        selectedMaps.append("<br>"); //$NON-NLS-1$
        ListModel<String> model = lisBoardsSelected.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            String map = model.getElementAt(i);
            selectedMaps.append(map);
            if ((i + 1) < model.getSize()) {
                selectedMaps.append("<br>"); //$NON-NLS-1$
            }
        }
        lblMapSummary.setToolTipText(selectedMaps.toString());
    }

    public void refreshGameYearLabel() {
        String txt = Messages.getString("ChatLounge.GameYear"); //$NON-NLS-1$
        txt = txt + " " //$NON-NLS-1$
                + clientgui.getClient().getGame().getOptions().intOption(OptionsConstants.ALLOWED_YEAR); // $NON-NLS-1$
        lblGameYear.setText(txt);
    }

    public void refreshTechLevelLabel() {
        String tlString;
        IOption tlOpt = clientgui.getClient().getGame().getOptions().getOption("techlevel");
        if (tlOpt != null) {
            tlString = tlOpt.stringValue();

        } else {
            tlString = TechConstants.getLevelDisplayableName(TechConstants.T_TECH_UNKNOWN);
        }
        String txt = Messages.getString("ChatLounge.TechLevel"); //$NON-NLS-1$
        txt = txt + " " + tlString; //$NON-NLS-1$
        lblTechLevel.setText(txt);
    }

    @Override
    public void ready() {
        final Client client = clientgui.getClient();
        final IGame game = client.getGame();
        final GameOptions gOpts = game.getOptions();
        // enforce exclusive deployment zones in double blind
        if (gOpts.booleanOption(OptionsConstants.ADVANCED_DOUBLE_BLIND) // $NON-NLS-1$
                && gOpts.booleanOption(OptionsConstants.BASE_EXCLUSIVE_DB_DEPLOYMENT)) { // $NON-NLS-1$
            int i = client.getLocalPlayer().getStartingPos();
            if (i == 0) {
                clientgui.doAlertDialog(Messages.getString("ChatLounge.ExclusiveDeploy.title"), //$NON-NLS-1$
                        Messages.getString("ChatLounge.ExclusiveDeploy.msg")); //$NON-NLS-1$
                return;
            }
            for (Enumeration<IPlayer> e = client.getGame().getPlayers(); e.hasMoreElements();) {
                IPlayer player = e.nextElement();
                if (player.getStartingPos() == 0) {
                    continue;
                }
                // CTR and EDG don't overlap
                if (((player.getStartingPos() == 9) && (i == 10)) || ((player.getStartingPos() == 10) && (i == 9))) {
                    continue;
                }

                // check for overlapping starting directions
                if (((player.getStartingPos() == i) || ((player.getStartingPos() + 1) == i)
                        || ((player.getStartingPos() - 1) == i))
                        && (player.getId() != client.getLocalPlayer().getId())) {
                    clientgui.doAlertDialog(Messages.getString("ChatLounge.OverlapDeploy.title"), //$NON-NLS-1$
                            Messages.getString("ChatLounge.OverlapDeploy.msg")); //$NON-NLS-1$
                    return;
                }
            }
        }

        // Make sure player has a commander if Commander killed victory is on
        if (gOpts.booleanOption(OptionsConstants.VICTORY_COMMANDER_KILLED)) {
            List<String> players = new ArrayList<>();
            if ((game.getLiveCommandersOwnedBy(client.getLocalPlayer()) < 1)
                    && (game.getEntitiesOwnedBy(client.getLocalPlayer()) > 0)) {
                players.add(client.getLocalPlayer().getName());
            }
            for (Client bc : clientgui.getBots().values()) {
                if ((game.getLiveCommandersOwnedBy(bc.getLocalPlayer()) < 1)
                        && (game.getEntitiesOwnedBy(bc.getLocalPlayer()) > 0)) {
                    players.add(bc.getLocalPlayer().getName());
                }
            }
            if (players.size() > 0) {
                String title = Messages.getString("ChatLounge.noCmdr.title"); //$NON-NLS-1$
                String msg = Messages.getString("ChatLounge.noCmdr.msg"); //$NON-NLS-1$
                for (String player : players) {
                    msg += player + "\n";
                }
                clientgui.doAlertDialog(title, msg);
                return;
            }

        }

        boolean done = !client.getLocalPlayer().isDone();
        client.sendDone(done);
        refreshDoneButton(done);
        for (Client client2 : clientgui.getBots().values()) {
            client2.sendDone(done);
        }
    }

    Client getPlayerSelected() {
        if ((tablePlayers == null) || (tablePlayers.getSelectedRow() == -1)) {
            return clientgui.getClient();
        }
        String name = (String) tablePlayers.getValueAt(tablePlayers.getSelectedRow(), 0);
        BotClient c = (BotClient) clientgui.getBots().get(name);
        if ((c == null) && clientgui.getClient().getName().equals(name)) {
            return clientgui.getClient();
        }
        return c;
    }

    /**
     * Stop just ignoring events and actually stop listening to them.
     */
    @Override
    public void removeAllListeners() {
        clientgui.getClient().getGame().removeGameListener(this);
        clientgui.getBoardView().removeBoardViewListener(this);
    }

    /*
     * This is required because the ChatLounge adds the listener to the
     * MechSummaryCache that must be removed explicitly.
     */
    public void die() {
        MechSummaryCache.getInstance().removeListener(mechSummaryCacheListener);
    }

    /**
     * Returns true if the given list of entities can be configured as a group.
     * This requires that they all have the same owner, and that none of the
     * units are being transported.
     *
     * @param entities
     * @return
     */
    public boolean canConfigureAll(List<Entity> entities) {
        if (entities.size() == 1) {
            return true;
        }

        Set<Integer> owners = new HashSet<>();
        boolean containsTransportedUnit = false;
        for (Entity e : entities) {
            containsTransportedUnit |= e.getTransportId() != Entity.NONE;
            owners.add(e.getOwner().getId());
        }
        return (owners.size() == 1) && !containsTransportedUnit;
    }

    /**
     *
     */
    @Override
    public void valueChanged(ListSelectionEvent event) {
        if (event.getValueIsAdjusting()) {
            return;
        }
        if (event.getSource().equals(butRemoveBot)) {
            butRemoveBot.setEnabled(false);
            Client c = getPlayerSelected();
            if (c == null) {

                tablePlayers.removeRowSelectionInterval(tablePlayers.getSelectedRow(), tablePlayers.getSelectedRow());
                return;
            }
            if (c instanceof BotClient) {
                butRemoveBot.setEnabled(true);
            }
            choTeam.setSelectedIndex(c.getLocalPlayer().getTeam());
        } else if (event.getSource().equals(tablePlayers.getSelectionModel())) {
            butRemoveBot.setEnabled(false);
            Client c = getPlayerSelected();
            if (c == null) {
                tablePlayers.removeRowSelectionInterval(tablePlayers.getSelectedRow(), tablePlayers.getSelectedRow());
                return;
            }
            if (c instanceof BotClient) {
                butRemoveBot.setEnabled(true);
            }
            boolean tf = (!c.getGame().getPlayerEntities(c.getLocalPlayer(), false).isEmpty());
            butDeleteAll.setEnabled(tf);
            butSaveList.setEnabled(tf);
            refreshCamos();
            choTeam.setSelectedIndex(c.getLocalPlayer().getTeam());
        } else if (event.getSource().equals(lisBoardsAvailable)) {
            previewMapsheet();
        }
    }
}
