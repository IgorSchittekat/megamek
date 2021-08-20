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

    private static final int COL_PLAYER = 0;
    private static final int COL_RATING = 1;
    private static final int COL_START = 2;
    private static final int COL_TEAM = 3;
    private static final int COL_BV = 4;
    private static final int COL_TON = 5;
    private static final int COL_COST = 6;
    private static final int N_COL = 7;

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
        for (Entity entity : chatLounge.getClientGUI().getClient().getEntitiesVector()) {
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
        boolean blindDrop = !player.equals(chatLounge.getClientGUI().getClient().getLocalPlayer()) &&
                chatLounge.getClientGUI().getClient().getGame().getOptions().booleanOption(OptionsConstants.BASE_REAL_BLIND_DROP);
        if (col == COL_BV) {
            int bv = bvs.get(row);
            if (blindDrop) {
                bv = bv > 0 ? 9999 : 0;
            }
            return bv;
        } else if (col == COL_PLAYER) {
            return player.getName();
        } else if (col == COL_RATING) {
            return player.getRating();
        } else if (col == COL_START) {
            return IStartingPositions.START_LOCATION_NAMES[player.getStartingPos()];
        } else if (col == COL_TON) {
            double ton = tons.get(row);
            if (blindDrop) {
                ton = ton > 0 ? 9999 : 0;
            }
            return ton;
        } else if (col == COL_COST) {
            int cost = costs.get(row);
            if (blindDrop) {
                cost = cost > 0 ? 9999 : 0;
            }
            return cost;
        } else {
            return player.getTeam();
        }
    }

    public IPlayer getPlayerAt(int row) {
        return players.get(row);
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
            if (i == COL_PLAYER) {
                column.setPreferredWidth(90);
            } else if (i == COL_TEAM) {
                column.setPreferredWidth(5);
            } else if ((i == COL_COST)) {
                column.setPreferredWidth(55);
            } else if (i == COL_START) {
                column.setPreferredWidth(50);
            } else {
                column.setPreferredWidth(35);
            }
        }
    }
}
