package megamek.client.ui.swing;

import megamek.client.bot.BotClient;
import megamek.client.bot.princess.Princess;
import megamek.common.IPlayer;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.StringTokenizer;

public class PlayerTableMouseAdapter extends MouseInputAdapter implements ActionListener {
    private final ChatLounge chatLounge;

    public PlayerTableMouseAdapter(ChatLounge lounge) {
        chatLounge = lounge;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            int row = chatLounge.getTablePlayers().rowAtPoint(e.getPoint());
            IPlayer player = chatLounge.getPlayerModel().getPlayerAt(row);
            if (player != null) {
                boolean isOwner = player.equals(chatLounge.getClientgui().getClient().getLocalPlayer());
                boolean isBot = chatLounge.getClientgui().getBots().get(player.getName()) != null;
                if ((isOwner || isBot)) {
                    chatLounge.customizePlayer();
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
        int row = chatLounge.getTablePlayers().rowAtPoint(e.getPoint());
        IPlayer player = chatLounge.getPlayerModel().getPlayerAt(row);
        boolean isOwner = player.equals(chatLounge.getClientgui().getClient().getLocalPlayer());
        boolean isBot = chatLounge.getClientgui().getBots().get(player.getName()) != null;
        if (e.isPopupTrigger()) {
            JMenuItem menuItem = null;
            menuItem = new JMenuItem("Configure ...");
            menuItem.setActionCommand("CONFIGURE|" + row);
            menuItem.addActionListener(this);
            menuItem.setEnabled(isOwner || isBot);
            popup.add(menuItem);

            if (isBot) {
                JMenuItem botConfig = new JMenuItem("Bot Settings ...");
                botConfig.setActionCommand("BOTCONFIG|" + row);
                botConfig.addActionListener(this);
                popup.add(botConfig);
            }

            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    @Override
    public void actionPerformed(ActionEvent action) {
        StringTokenizer st = new StringTokenizer(action.getActionCommand(), "|");
        String command = st.nextToken();
        if (command.equalsIgnoreCase("CONFIGURE")) {
            chatLounge.customizePlayer();
        } else if (command.equalsIgnoreCase("BOTCONFIG")) {
            int row = Integer.parseInt(st.nextToken());
            IPlayer player = chatLounge.getPlayerModel().getPlayerAt(row);
            BotClient bot = (BotClient) chatLounge.getClientgui().getBots().get(player.getName());
            BotConfigDialog bcd = new BotConfigDialog(chatLounge.getClientgui().frame, bot);
            bcd.setVisible(true);

            if (!bcd.dialogAborted && bot instanceof Princess) {
                ((Princess) bot).setBehaviorSettings(bcd.getBehaviorSettings());

                // bookkeeping:
                chatLounge.getClientgui().getBots().remove(player.getName());
                bot.setName(bcd.getBotName());
                chatLounge.getClientgui().getBots().put(bot.getName(), bot);
                player.setName(bcd.getBotName());
                chatLounge.getClientgui().chatlounge.refreshPlayerInfo();
            }
        }
    }
}
