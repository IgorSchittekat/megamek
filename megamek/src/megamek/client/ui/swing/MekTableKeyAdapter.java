package megamek.client.ui.swing;

import megamek.common.Entity;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

public class MekTableKeyAdapter extends KeyAdapter {
    private final ChatLounge chatLounge;

    public MekTableKeyAdapter(ChatLounge lounge) {
        chatLounge = lounge;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (chatLounge.getTableEntities().getSelectedRowCount() == 0) {
            return;
        }
        int[] rows = chatLounge.getTableEntities().getSelectedRows();
        Vector<Entity> entities = new Vector<>();
        for (int row : rows) {
            entities.add(chatLounge.getMekModel().getEntityAt(row));
        }
        int code = e.getKeyCode();
        if ((code == KeyEvent.VK_DELETE) || (code == KeyEvent.VK_BACK_SPACE)) {
            e.consume();
            for (Entity entity : entities) {
                chatLounge.delete(entity);
            }
        } else if (code == KeyEvent.VK_SPACE) {
            e.consume();
            chatLounge.mechReadout(entities.get(0));
        } else if (code == KeyEvent.VK_ENTER) {
            e.consume();
            if (entities.size() == 1) {
                chatLounge.customizeMech(entities.get(0));
            } else if (chatLounge.canConfigureAll(entities)) {
                chatLounge.customizeMechs(entities);
            }
        }
    }
}
