package megamek.client.ui.swing;

import junit.framework.TestCase;
import megamek.client.ui.swing.util.PlayerColour;
import megamek.common.*;
import megamek.client.*;


import megamek.common.options.GameOptions;
import megamek.common.options.OptionsConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.io.File;
import java.util.List;
import java.util.Vector;


@RunWith(JUnit4.class)
public class PlayerTableModelTest {
    @Test
    public void testGetColumnName() {
        ChatLounge chatLounge = Mockito.mock(ChatLounge.class);
        PlayerTableModel model = new PlayerTableModel(chatLounge);
        TestCase.assertEquals("Player", model.getColumnName(PlayerTableModel.COL_PLAYER));
        TestCase.assertEquals("Rating", model.getColumnName(PlayerTableModel.COL_RATING));
        TestCase.assertEquals("Start", model.getColumnName(PlayerTableModel.COL_START));
        TestCase.assertEquals("Team", model.getColumnName(PlayerTableModel.COL_TEAM));
        TestCase.assertEquals("BV", model.getColumnName(PlayerTableModel.COL_BV));
        TestCase.assertEquals("Tons", model.getColumnName(PlayerTableModel.COL_TON));
        TestCase.assertEquals("Cost", model.getColumnName(PlayerTableModel.COL_COST));
        TestCase.assertEquals("??", model.getColumnName(PlayerTableModel.N_COL));
    }

    @Test
    public void testAddPlayer() {
        ChatLounge chatLounge = Mockito.mock(ChatLounge.class);
        Player player = new Player(0, "Tester");
        ClientGUI gui = Mockito.mock(ClientGUI.class);
        Client client = Mockito.mock(Client.class);
        Entity entity = Mockito.mock(Entity.class);
        Mockito.when(entity.getOwner()).thenReturn(player);
        Mockito.when(entity.calculateBattleValue()).thenReturn(20);
        Mockito.when(entity.getCost(false)).thenReturn(100.0);
        Mockito.when(entity.getWeight()).thenReturn(50.0);
        Vector<Entity> entityVector = new Vector<>();
        entityVector.add(entity);
        Mockito.when(client.getEntitiesVector()).thenReturn(entityVector);
        Mockito.when(gui.getClient()).thenReturn(client);
        Mockito.when(chatLounge.getClientgui()).thenReturn(gui);
        PlayerTableModel model = new PlayerTableModel(chatLounge);
        model.addPlayer(player);
        TestCase.assertEquals(player, model.getPlayerAt(0));
        TestCase.assertEquals(20, model.getBvAt(0));
        TestCase.assertEquals(100, model.getCostAt(0));
        TestCase.assertEquals(50.0, model.getTonAt(0));
        TestCase.assertEquals(1, model.getRowCount());
        model.clearData();
        boolean failed;
        try {
            model.getPlayerAt(0);
            model.getBvAt(0);
            model.getCostAt(0);
            model.getTonAt(0);
            failed = false;
        }
        catch (IndexOutOfBoundsException e) {
            failed = true;
        }
        TestCase.assertTrue(failed);
    }

    @Test
    public void testGetValueAt() {
        ChatLounge chatLounge = Mockito.mock(ChatLounge.class);
        Player player = new Player(0, "Tester");
        ClientGUI gui = Mockito.mock(ClientGUI.class);
        Client client = Mockito.mock(Client.class);
        Entity entity = Mockito.mock(Entity.class);
        Mockito.when(entity.getOwner()).thenReturn(player);
        Mockito.when(entity.calculateBattleValue()).thenReturn(20);
        Mockito.when(entity.getCost(false)).thenReturn(100.0);
        Mockito.when(entity.getWeight()).thenReturn(50.0);
        Vector<Entity> entityVector = new Vector<>();
        entityVector.add(entity);
        Mockito.when(client.getEntitiesVector()).thenReturn(entityVector);
        Mockito.when(gui.getClient()).thenReturn(client);
        Mockito.when(chatLounge.getClientgui()).thenReturn(gui);
        PlayerTableModel model = new PlayerTableModel(chatLounge);
        model.addPlayer(player);
        Mockito.when(client.getLocalPlayer()).thenReturn(player);
        TestCase.assertEquals("Tester", model.getValueAt(0, PlayerTableModel.COL_PLAYER));
        TestCase.assertEquals(1000, model.getValueAt(0, PlayerTableModel.COL_RATING));
        TestCase.assertEquals("Any", model.getValueAt(0, PlayerTableModel.COL_START));
        TestCase.assertEquals(0, model.getValueAt(0, PlayerTableModel.COL_TEAM));
        TestCase.assertEquals(20, model.getValueAt(0, PlayerTableModel.COL_BV));
        TestCase.assertEquals(50.0, model.getValueAt(0, PlayerTableModel.COL_TON));
        TestCase.assertEquals(100, model.getValueAt(0, PlayerTableModel.COL_COST));
        Mockito.when(client.getLocalPlayer()).thenReturn(null);
        Game game = Mockito.mock(Game.class);
        GameOptions options = Mockito.mock(GameOptions.class);
        Mockito.when(options.booleanOption(OptionsConstants.BASE_REAL_BLIND_DROP)).thenReturn(true);
        Mockito.when(game.getOptions()).thenReturn(options);
        Mockito.when(client.getGame()).thenReturn(game);
        TestCase.assertEquals(9999, model.getValueAt(0, PlayerTableModel.COL_BV));
        TestCase.assertEquals(9999.0, model.getValueAt(0, PlayerTableModel.COL_TON));
        TestCase.assertEquals(9999, model.getValueAt(0, PlayerTableModel.COL_COST));
    }

    @Test
    public void testEditableAndColumnCount() {
        ChatLounge chatLounge = Mockito.mock(ChatLounge.class);
        PlayerTableModel model = new PlayerTableModel(chatLounge);
        TestCase.assertFalse(model.isCellEditable(0, 0));
        TestCase.assertEquals(PlayerTableModel.N_COL, model.getColumnCount());
    }
}
