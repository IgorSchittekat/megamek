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
public class PlayerTest {

    @Test
    public void testBasicGettersAndSetters() {
        Player player = new Player(0, "Test");
        TestCase.assertEquals(0, player.getId());
        TestCase.assertEquals(0, player.hashCode());
        TestCase.assertEquals("Test", player.getName());
        player.setTeam(1);
        TestCase.assertEquals(1, player.getTeam());
        player.setName("Tester");
        TestCase.assertEquals("Tester", player.getName());
        TestCase.assertEquals("Player 0 (Tester)", player.toString());
        player.setGhost(true);
        TestCase.assertTrue(player.isGhost());
        player.setSeeAll(true);
        TestCase.assertTrue(player.getSeeAll());
        TestCase.assertFalse(player.canSeeAll());
        player.setObserver(true);
        TestCase.assertTrue(player.isObserver());
        TestCase.assertTrue(player.canSeeAll());
        player.setObserver(false);
        TestCase.assertFalse(player.isObserver());
        TestCase.assertFalse(player.canSeeAll());
        player.setObserver(true);
        TestCase.assertFalse(player.canSeeAll());
        player.setColour(PlayerColour.CYAN);
        TestCase.assertEquals(PlayerColour.CYAN, player.getColour());
        player.setStartingPos(5);
        TestCase.assertEquals(5, player.getStartingPos());
        player.setAdmitsDefeat(true);
        TestCase.assertTrue(player.admitsDefeat());
        player.setAllowTeamChange(true);
        TestCase.assertTrue(player.isAllowingTeamChange());
        player.setInitialEntityCount(6);
        TestCase.assertEquals(6, player.getInitialEntityCount());
        player.changeInitialEntityCount(3);
        TestCase.assertEquals(9, player.getInitialEntityCount());
        player.setInitialBV(7);
        TestCase.assertEquals(7, player.getInitialBV());
        player.changeInitialBV(4);
        TestCase.assertEquals(11, player.getInitialBV());
        player.setInitCompensationBonus(8);
        TestCase.assertEquals(8, player.getInitCompensationBonus());
        player.setConstantInitBonus(9);
        TestCase.assertEquals(9, player.getConstantInitBonus());
        player.setNbrMFActive(10);
        TestCase.assertEquals(10, player.getNbrMFActive());
        player.setNbrMFCommand(11);
        TestCase.assertEquals(11, player.getNbrMFCommand());
        player.setNbrMFConventional(12);
        TestCase.assertEquals(12, player.getNbrMFConventional());
        player.setNbrMFInferno(13);
        TestCase.assertEquals(13, player.getNbrMFInferno());
        player.setNbrMFVibra(14);
        TestCase.assertEquals(14, player.getNbrMFVibra());
        TestCase.assertTrue(player.hasMinefields());
        player.setRating(100);
        TestCase.assertEquals(100, player.getRating());
        player.setRating(-100);
        TestCase.assertEquals(0, player.getRating());

    }

    @Test
    public void testAdvancedGettersAndSetters() {
        Player player = new Player(0, "Test");
        Game game = Mockito.mock(Game.class);
        player.setGame(game);
        player.setDone(true);
        TestCase.assertTrue(player.isDone());
        Team team = new Team(0);
        team.addPlayer(player);
        Mockito.when(game.getTeamForPlayer(player)).thenReturn(team);
        player.setObserver(true);
        TestCase.assertTrue(team.isObserverTeam());
    }

    @Test
    public void testMineFields() {
        Player player = new Player(0, "Test");
        Minefield mf1 = Minefield.createMinefield(new Coords(0, 0),0, 0, 0);
        Minefield mf2 = Minefield.createMinefield(new Coords(1, 2),0, 1, 3);
        Minefield mf3 = Minefield.createMinefield(new Coords(2, 5),0, 2, 4);
        Vector<Minefield> minefields1 = new Vector<>();
        minefields1.add(mf1);
        player.addMinefield(mf1);
        TestCase.assertTrue(player.containsMinefield(mf1));
        TestCase.assertEquals(minefields1, player.getMinefields());
        Vector<Minefield> minefields2 = new Vector<>();
        minefields2.add(mf2);
        minefields1.add(mf2);
        minefields2.add(mf3);
        minefields1.add(mf3);
        player.addMinefields(minefields2);
        TestCase.assertEquals(minefields1, player.getMinefields());
        player.removeMinefield(mf1);
        TestCase.assertEquals(minefields2, player.getMinefields());
        player.removeMinefields();
        TestCase.assertEquals(new Vector<>(), player.getMinefields());
    }

    @Test
    public void testGetTurnInitBonus() {
        Player player = new Player(0, "Test");
        TestCase.assertEquals(0, player.getTurnInitBonus());
        Game game = Mockito.mock(Game.class);
        player.setGame(game);
        Mockito.when(game.getEntitiesVector()).thenReturn(null);
        TestCase.assertEquals(0, player.getTurnInitBonus());
        Entity entity = Mockito.mock(Entity.class);
        Mockito.when(entity.getOwner()).thenReturn(null);
        TestCase.assertEquals(0, player.getTurnInitBonus());
        Mockito.when(entity.getOwner()).thenReturn(player);
        Mockito.when(entity.getHQIniBonus()).thenReturn(10);
        Mockito.when(entity.getQuirkIniBonus()).thenReturn(3);
        GameOptions gameOptions = Mockito.mock(GameOptions.class);
        Mockito.when(gameOptions.booleanOption(OptionsConstants.ADVANCED_TACOPS_MOBILE_HQS)).thenReturn(true);
        Mockito.when(game.getOptions()).thenReturn(gameOptions);
        Vector<Entity> entityVector = new Vector<>();
        entityVector.add(entity);
        Mockito.when(game.getEntitiesVector()).thenReturn(entityVector);
        TestCase.assertEquals(13, player.getTurnInitBonus());
    }

    @Test
    public void testGetCommandBonus() {
        Player player = new Player(0, "Test");
        TestCase.assertEquals(0, player.getCommandBonus());
        Game game = Mockito.mock(Game.class);
        player.setGame(game);
        Entity entity = Mockito.mock(Entity.class);
        Mockito.when(entity.getOwner()).thenReturn(player);
        Mockito.when(entity.isDestroyed()).thenReturn(false);
        Mockito.when(entity.isDeployed()).thenReturn(true);
        Mockito.when(entity.isOffBoard()).thenReturn(false);
        Mockito.when(entity.isCaptured()).thenReturn(false);
        Crew crew = Mockito.mock(Crew.class);
        Mockito.when(entity.getCrew()).thenReturn(crew);
        Mockito.when(crew.isActive()).thenReturn(true);
        GameOptions gameOptions = Mockito.mock(GameOptions.class);
        Mockito.when(gameOptions.booleanOption(OptionsConstants.RPG_COMMAND_INIT)).thenReturn(true);
        Mockito.when(game.getOptions()).thenReturn(gameOptions);
        Mockito.when(crew.getCommandBonus()).thenReturn(4);
        Mockito.when(entity.hasCommandConsoleBonus()).thenReturn(true);
        Vector<Entity> entityVector = new Vector<>();
        entityVector.add(entity);
        Mockito.when(game.getEntitiesVector()).thenReturn(entityVector);
        TestCase.assertEquals(6, player.getCommandBonus());
    }

    @Test
    public void testEquals() {
        Player player = new Player(0, "Test");
        Player player2 = new Player(1, "Test2");
        Player player3 = new Player(0, "Test");
        TestCase.assertTrue(player.equals(player3));
        TestCase.assertFalse(player.equals(player2));
    }

    @Test
    public void testEnemy() {
        Player player = new Player(0, "Test");
        Player player2 = new Player(1, "Test2");
        TestCase.assertTrue(player.isEnemyOf(null));
        TestCase.assertFalse(player.isEnemyOf(player));
        player.setTeam(0);
        player2.setTeam(1);
        TestCase.assertTrue(player.isEnemyOf(player2));
    }

    @Test
    public void testGetAirborneVTOL() {
        Player player = new Player(0, "Test");
        Game game = Mockito.mock(Game.class);
        player.setGame(game);
        Entity entity = Mockito.mock(Entity.class);
        Mockito.when(entity.getOwner()).thenReturn(player);
        Mockito.when(entity.getMovementMode()).thenReturn(EntityMovementMode.WIGE);
        Mockito.when(entity.isDestroyed()).thenReturn(false);
        Mockito.when(entity.getElevation()).thenReturn(2);
        Mockito.when(entity.getId()).thenReturn(5);
        Vector<Integer> units = new Vector<>();
        units.add(5);
        Vector<Entity> entityVector = new Vector<>();
        entityVector.add(entity);
        Mockito.when(game.getEntitiesVector()).thenReturn(entityVector);
        TestCase.assertEquals(units, player.getAirborneVTOL());
    }
}
