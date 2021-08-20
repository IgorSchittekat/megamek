package megamek.client.ui.swing;

import megamek.client.ui.Messages;
import megamek.common.Configuration;
import megamek.common.Entity;
import megamek.common.icons.Portrait;
import megamek.common.options.OptionsConstants;
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
                return ChatLounge.formatPilotCompact(entity.getCrew(), blindDrop, rpgSkills);
            }
            return ChatLounge.formatPilotHTML(entity.getCrew(), blindDrop, rpgSkills);
        } else {
            if (compact) {
                return ChatLounge.formatUnitCompact(entity, blindDrop);
            }
            return ChatLounge.formatUnitHTML(entity, blindDrop);
        }
        return value;
    }

    public Entity getEntityAt(int row) {
        if (row < 0) {
            return null;
        }
        return data.get(row);
    }

    public MekTableModel.Renderer getRenderer() {
        return new MekTableModel.Renderer();
    }

    public class Renderer extends ChatLounge.MekInfo implements TableCellRenderer {
        private static final String FILENAME_UNKNOWN_UNIT = "unknown_unit.gif";
        private static final long serialVersionUID = -9154596036677641620L;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Component c = this;
            setText(getValueAt(row, column).toString(), isSelected);
            Entity entity = getEntityAt(row);
            if (null == entity) {
                return null;
            }
            boolean isOwner = entity.getOwner().equals(chatLounge.getClientGUI().getClient().getLocalPlayer());
            boolean blindDrop = chatLounge.getClientGUI().getClient().getGame().getOptions()
                    .booleanOption(OptionsConstants.BASE_BLIND_DROP);
            boolean compact = chatLounge.getButCompact().isSelected();
            if (!isOwner && blindDrop) {
                if (column == COL_UNIT) {
                    if (compact) {
                        clearImage();
                    } else {
                        Image image = getToolkit().getImage(
                                new MegaMekFile(Configuration.miscImagesDir(),
                                        FILENAME_UNKNOWN_UNIT).toString());
                        image = image.getScaledInstance(-1, 72,
                                Image.SCALE_DEFAULT);
                        setImage(image);
                    }
                } else if (column == COL_PILOT) {
                    if (compact) {
                        clearImage();
                    } else {
                        Image image = getToolkit().getImage(
                                new MegaMekFile(Configuration.portraitImagesDir(),
                                        Portrait.DEFAULT_PORTRAIT_FILENAME)
                                        .toString());
                        image = image.getScaledInstance(-1, 50,
                                Image.SCALE_DEFAULT);
                        setImage(image);
                    }
                }
            } else {
                if (column == COL_UNIT) {
                    if (compact) {
                        clearImage();
                    } else {
                        chatLounge.getClientGUI().loadPreviewImage(getLabel(), entity);
                    }
                    setToolTipText(ChatLounge.formatUnitTooltip(entity));
                    setLoad(entity.getTransportId() != Entity.NONE);
                } else if (column == COL_PILOT) {
                    if (compact) {
                        clearImage();
                    } else {
                        setImage(entity.getCrew().getPortrait(0).getImage(50));
                    }
                    setToolTipText(ChatLounge.formatPilotTooltip(entity.getCrew(),
                            chatLounge.getClientGUI().getClient().getGame().getOptions()
                                    .booleanOption(OptionsConstants.RPG_COMMAND_INIT),
                            chatLounge.getClientGUI().getClient().getGame().getOptions()
                                    .booleanOption(OptionsConstants.RPG_INDIVIDUAL_INITIATIVE),
                            chatLounge.getClientGUI().getClient().getGame().getOptions()
                                    .booleanOption(OptionsConstants.RPG_TOUGHNESS),
                            chatLounge.getClientGUI().getClient().getGame().getOptions()
                                    .booleanOption(OptionsConstants.RPG_RPG_GUNNERY)));
                }
            }

            if (isSelected) {
                c.setForeground(table.getSelectionForeground());
                c.setBackground(table.getSelectionBackground());
            } else {
                Color background = table.getBackground();
                if (row % 2 != 0) {
                    Color alternateColor = UIManager.getColor("Table.alternateRowColor");
                    if (alternateColor == null) {
                        // If we don't have an alternate row color, use 'controlHighlight'
                        // as it is pretty reasonable across the various themes.
                        alternateColor = UIManager.getColor("controlHighlight");
                    }
                    if (alternateColor != null) {
                        background = alternateColor;
                    }
                }
                c.setForeground(table.getForeground());
                c.setBackground(background);
            }

            if (hasFocus) {
                if (!isSelected ) {
                    Color col = UIManager.getColor("Table.focusCellForeground");
                    if (col != null) {
                        c.setForeground(col);
                    }
                    col = UIManager.getColor("Table.focusCellBackground");
                    if (col != null) {
                        c.setBackground(col);
                    }
                }
            }
            return c;
        }
    }
}
