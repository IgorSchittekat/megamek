package megamek.common;

import junit.framework.TestCase;
import megamek.client.ui.swing.util.PlayerColour;
import megamek.common.*;

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
public class GameTest {
    @Test
    public void testGettersAndSetters() {
        Game game = new Game();
        game.setExternalGameId(20);
        TestCase.assertEquals(20, game.getExternalGameId());

    }

    @Test
    public void testPlayers() {
        Game game = new Game();
        Player p1 = new Player(0, "Tester");
        Player p2 = new Player(1, "Tester 2");
        game.addPlayer(0, p1);
        game.addPlayer(1, p2);
        TestCase.assertEquals(p1, game.getPlayer(0));
        TestCase.assertNull(game.getPlayer(-1));
        Vector<Player> players = new Vector<>();
        players.add(p1);
        players.add(p2);
        TestCase.assertEquals(players, game.getPlayersVector());
        TestCase.assertEquals(2, game.getNoOfPlayers());
        Player p3 = new Player(0, "Tester 3");
        game.setPlayer(0, p3);
        TestCase.assertEquals(p3, game.getPlayer(0));
        game.removePlayer(0);
        players.remove(p1);
        TestCase.assertEquals(players, game.getPlayersVector());
    }
}
