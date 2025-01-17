package megamek.client.ui.swing;

import megamek.client.ui.Messages;
import megamek.common.Entity;
import megamek.common.IPlayer;
import megamek.common.IStartingPositions;
import megamek.common.options.OptionsConstants;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PlayerTableModel extends AbstractTableModel {
    private static final long serialVersionUID = -1372393680232901923L;

    public static final int COL_PLAYER = 0;
    public static final int COL_RATING = 1;
    public static final int COL_START = 2;
    public static final int COL_TEAM = 3;
    public static final int COL_BV = 4;
    public static final int COL_TON = 5;
    public static final int COL_COST = 6;
    public static final int N_COL = 7;

    private final ChatLounge chatLounge;
    private ArrayList<IPlayer> players;
    private ArrayList<Integer> bvs;
    private ArrayList<Integer> costs;
    private ArrayList<Double> tons;

    public PlayerTableModel(ChatLounge lounge) {
        chatLounge = lounge;
        players = new ArrayList<>();
        bvs = new ArrayList<>();
        costs = new ArrayList<>();
        tons = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return players.size();
    }

    public void clearData() {
        players = new ArrayList<>();
        bvs = new ArrayList<>();
        costs = new ArrayList<>();
        tons = new ArrayList<>();
    }

    @Override
    public int getColumnCount() {
        return N_COL;
    }

    public void addPlayer(IPlayer player) {
        players.add(player);
        int bv = 0;
        int cost = 0;
        double ton = 0;
        for (Entity entity : chatLounge.getClientgui().getClient().getEntitiesVector()) {
            if (entity.getOwner().equals(player)) {
                bv += entity.calculateBattleValue();
                cost += entity.getCost(false);
                ton += entity.getWeight();
            }
        }
        bvs.add(bv);
        costs.add(cost);
        tons.add(ton);
        fireTableDataChanged();
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case COL_PLAYER:
                return Messages.getString("ChatLounge.colPlayer");
            case COL_RATING:
                return "Rating";
            case COL_START:
                return "Start";
            case COL_TEAM:
                return "Team";
            case COL_TON:
                return Messages.getString("ChatLounge.colTon");
            case COL_BV:
                return Messages.getString("ChatLounge.colBV");
            case COL_COST:
                return Messages.getString("ChatLounge.colCost");
            default:
                return "??";
        }
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
        IPlayer player = getPlayerAt(row);
        boolean blindDrop = !player.equals(chatLounge.getClientgui().getClient().getLocalPlayer()) &&
                chatLounge.getClientgui().getClient().getGame().getOptions().booleanOption(OptionsConstants.BASE_REAL_BLIND_DROP);
        switch (col) {
            case COL_PLAYER:
                return player.getName();
            case COL_RATING:
                return player.getRating();
            case COL_START:
                return IStartingPositions.START_LOCATION_NAMES[player.getStartingPos()];
            case COL_TEAM:
                return player.getTeam();
            case COL_BV:
                int bv = bvs.get(row);
                if (blindDrop) {
                    bv = bv > 0 ? 9999 : 0;
                }
                return bv;
            case COL_TON:
                double ton = tons.get(row);
                if (blindDrop) {
                    ton = ton > 0 ? 9999 : 0;
                }
                return ton;
            case COL_COST:
                int cost = costs.get(row);
                if (blindDrop) {
                    cost = cost > 0 ? 9999 : 0;
                }
                return cost;
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    public IPlayer getPlayerAt(int row) {
        return players.get(row);
    }

    protected int getBvAt(int row) {
        return bvs.get(row);
    }

    protected int getCostAt(int row) {
        return costs.get(row);
    }

    protected Double getTonAt(int row) {
        return tons.get(row);
    }

    public JTable createPlayersTable() {
        return new JTable(this) {
            private static final long serialVersionUID = 6252953920509362407L;

            @Override
            public String getToolTipText(MouseEvent e) {
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);
                int realColIndex = convertColumnIndexToModel(colIndex);
                IPlayer player = getPlayerAt(rowIndex);
                if (player == null) {
                    return null;
                }
                int mines = player.getNbrMFConventional() + player.getNbrMFActive() + player.getNbrMFInferno()
                        + player.getNbrMFVibra();
                if (realColIndex == COL_PLAYER) {
                    return Messages.getString("ChatLounge.tipPlayer",
                            getValueAt(rowIndex, colIndex), player.getConstantInitBonus(), mines);
                } else if (realColIndex == COL_RATING) {
                    return getValueAt(rowIndex, colIndex).toString();
                } else if (realColIndex == COL_TON) {
                    return getValueAt(rowIndex, colIndex).toString();
                } else if (realColIndex == COL_COST) {
                    return Messages.getString("ChatLounge.tipCost",
                            getValueAt(rowIndex, colIndex));
                } else if (realColIndex == COL_START) {
                    return (String) getValueAt(rowIndex, colIndex);
                } else {
                    return Integer.toString((Integer) getValueAt(rowIndex, colIndex));
                }
            }
        };
    }

    public void setupColumnWidths(JTable playersTable) {
        TableColumn column;
        for (int i = 0; i < N_COL; i++) {
            column = playersTable.getColumnModel().getColumn(i);
            switch (i) {
                case COL_PLAYER:
                    column.setPreferredWidth(75);
                    break;
                case COL_RATING:
                case COL_START:
                    column.setPreferredWidth(50);
                    break;
                case COL_COST:
                    column.setPreferredWidth(55);
                    break;
                default:
                    column.setPreferredWidth(35);
            }
        }
    }
}
