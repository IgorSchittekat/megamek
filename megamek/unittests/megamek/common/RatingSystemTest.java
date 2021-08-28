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
public class RatingSystemTest {
    @Test
    public void testCalculate() {
        Game game = new Game();
        Player p1 = new Player(0, "Player 1");
        Player p2 = new Player(1, "Player 2");
        Player p3 = new Player(2, "Player 3");
        p3.setObserver(true);
        game.addPlayer(0, p1);
        game.addPlayer(1, p2);
        game.addPlayer(2, p3);
        p1.setTeam(1);
        p2.setTeam(2);
        p3.setTeam(3);
        game.setVictoryPlayerId(0);
        RatingSystem.calculate(game);
        TestCase.assertEquals(1100, p1.getRating());
        TestCase.assertEquals(900, p2.getRating());
        TestCase.assertEquals(1000, p3.getRating());
        game.setVictoryTeam(3);
        game.setVictoryPlayerId(4);
        p3.setObserver(false);
        RatingSystem.calculate(game);
        TestCase.assertEquals(1000, p1.getRating());
        TestCase.assertEquals(800, p2.getRating());
        TestCase.assertEquals(1100, p3.getRating());
    }
}
