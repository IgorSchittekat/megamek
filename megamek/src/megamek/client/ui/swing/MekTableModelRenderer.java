package megamek.client.ui.swing;

import megamek.common.Configuration;
import megamek.common.Entity;
import megamek.common.icons.Portrait;
import megamek.common.options.OptionsConstants;
import megamek.common.util.fileUtils.MegaMekFile;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class MekTableModelRenderer extends JPanel implements TableCellRenderer {
    private static final String FILENAME_UNKNOWN_UNIT = "unknown_unit.gif";
    private static final long serialVersionUID = -9154596036677641620L;

    private JLabel lblImage;
    private JLabel lblLoad;
    private final ChatLounge chatLounge;

    public MekTableModelRenderer(ChatLounge lounge) {
        chatLounge = lounge;
        lblImage = new JLabel();
        lblLoad = new JLabel();

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(gridbag);

        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(1, 1, 1, 1);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.CENTER;
        gridbag.setConstraints(lblLoad, c);
        add(lblLoad);

        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(1, 1, 1, 1);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        gridbag.setConstraints(lblImage, c);
        add(lblImage);

        lblImage.setBorder(BorderFactory.createEmptyBorder());
    }

    public void setText(String s, boolean isSelected) {
        lblImage.setText(String.format("<html>%s</html>", s));
    }

    public void clearImage() {
        lblImage.setIcon(null);
    }

    public void setImage(Image img) {
        lblImage.setIcon(new ImageIcon(img));
    }

    public JLabel getLabel() {
        return lblImage;
    }

    public void setLoad(boolean load) {
        // if this is a loaded unit then do something with lblLoad to make
        // it show up
        // otherwise clear lblLoad
        if (load) {
            lblLoad.setText(" +");
        } else {
            lblLoad.setText("");
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        Component c = this;
        setText(chatLounge.getMekModel().getValueAt(row, column).toString(), isSelected);
        Entity entity = chatLounge.getMekModel().getEntityAt(row);
        if (null == entity) {
            return null;
        }
        boolean isOwner = entity.getOwner().equals(chatLounge.getClientGUI().getClient().getLocalPlayer());
        boolean blindDrop = chatLounge.getClientGUI().getClient().getGame().getOptions()
                .booleanOption(OptionsConstants.BASE_BLIND_DROP);
        boolean compact = chatLounge.getButCompact().isSelected();
        if (!isOwner && blindDrop) {
            if (column == MekTableModel.COL_UNIT) {
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
            } else if (column == MekTableModel.COL_PILOT) {
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
            if (column == MekTableModel.COL_UNIT) {
                if (compact) {
                    clearImage();
                } else {
                    chatLounge.getClientGUI().loadPreviewImage(getLabel(), entity);
                }
                setToolTipText(ChatLounge.formatUnitTooltip(entity));
                setLoad(entity.getTransportId() != Entity.NONE);
            } else if (column == MekTableModel.COL_PILOT) {
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
