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

    @Test
    public void testEntities() {
        Game game = new Game();
        Entity e1 = Mockito.mock(Entity.class);
        Entity e2 = Mockito.mock(Entity.class);
        Vector<Entity> entities = new Vector<>();
        entities.add(e1);
        entities.add(e2);
        game.setEntitiesVector(entities);
        TestCase.assertEquals(entities, game.getEntitiesVector());
    }

    @Test
    public void testGetEntitiesLeft() {
        Game game = new Game();
        Player player = new Player(0, "Test");
        Player player2 = new Player(1, "Test2");
        game.addPlayer(0, player);
        game.addPlayer(1, player2);
        Infantry i1 = Mockito.mock(Infantry.class);
        Infantry i2 = Mockito.mock(Infantry.class);
        Infantry i3 = Mockito.mock(Infantry.class);
        Infantry i4 = Mockito.mock(Infantry.class);
        Protomech p1 = Mockito.mock(Protomech.class);
        Protomech p2 = Mockito.mock(Protomech.class);
        Protomech p3 = Mockito.mock(Protomech.class);
        Protomech p4 = Mockito.mock(Protomech.class);
        Protomech p5 = Mockito.mock(Protomech.class);
        Tank t1 = Mockito.mock(Tank.class);
        Tank t2 = Mockito.mock(Tank.class);
        Tank t3 = Mockito.mock(Tank.class);
        Mech m1 = Mockito.mock(Mech.class);
        Mech m2 = Mockito.mock(Mech.class);
        Mech m3 = Mockito.mock(Mech.class);
        Mech m4 = Mockito.mock(Mech.class);
        Mech m5 = Mockito.mock(Mech.class);
        Mech m6 = Mockito.mock(Mech.class);
        Mockito.when(i1.getOwner()).thenReturn(player);
        Mockito.when(i2.getOwner()).thenReturn(player);
        Mockito.when(i3.getOwner()).thenReturn(player);
        Mockito.when(i4.getOwner()).thenReturn(player2);
        Mockito.when(p1.getOwner()).thenReturn(player);
        Mockito.when(p2.getOwner()).thenReturn(player);
        Mockito.when(p3.getOwner()).thenReturn(player);
        Mockito.when(p4.getOwner()).thenReturn(player);
        Mockito.when(p5.getOwner()).thenReturn(player2);
        Mockito.when(t1.getOwner()).thenReturn(player);
        Mockito.when(t2.getOwner()).thenReturn(player);
        Mockito.when(t3.getOwner()).thenReturn(player2);
        Mockito.when(m1.getOwner()).thenReturn(player);
        Mockito.when(m2.getOwner()).thenReturn(player);
        Mockito.when(m3.getOwner()).thenReturn(player);
        Mockito.when(m4.getOwner()).thenReturn(player);
        Mockito.when(m5.getOwner()).thenReturn(player);
        Mockito.when(m6.getOwner()).thenReturn(player2);
        Mockito.when(i1.isSelectableThisTurn()).thenReturn(true);
        Mockito.when(i2.isSelectableThisTurn()).thenReturn(true);
        Mockito.when(i3.isSelectableThisTurn()).thenReturn(false);
        Mockito.when(p1.isSelectableThisTurn()).thenReturn(true);
        Mockito.when(p2.isSelectableThisTurn()).thenReturn(true);
        Mockito.when(p3.isSelectableThisTurn()).thenReturn(true);
        Mockito.when(p4.isSelectableThisTurn()).thenReturn(false);
        Mockito.when(t1.isSelectableThisTurn()).thenReturn(true);
        Mockito.when(t2.isSelectableThisTurn()).thenReturn(false);
        Mockito.when(m1.isSelectableThisTurn()).thenReturn(true);
        Mockito.when(m2.isSelectableThisTurn()).thenReturn(true);
        Mockito.when(m3.isSelectableThisTurn()).thenReturn(true);
        Mockito.when(m4.isSelectableThisTurn()).thenReturn(false);
        Mockito.when(m5.isSelectableThisTurn()).thenReturn(false);
        Vector<Entity> entities = new Vector<>();
        entities.add(i1);
        entities.add(i2);
        entities.add(i3);
        entities.add(i4);
        entities.add(p1);
        entities.add(p2);
        entities.add(p3);
        entities.add(p4);
        entities.add(p5);
        entities.add(t1);
        entities.add(t2);
        entities.add(t3);
        entities.add(m1);
        entities.add(m2);
        entities.add(m3);
        entities.add(m4);
        entities.add(m5);
        entities.add(m6);
        game.setEntitiesVector(entities);
        TestCase.assertEquals(2, game.getInfantryLeft(0));
        TestCase.assertEquals(3, game.getProtomechsLeft(0));
        TestCase.assertEquals(1, game.getVehiclesLeft(0));
        TestCase.assertEquals(3, game.getMechsLeft(0));
    }

    @Test
    public void testRemoveTurnFor() {
        Game game = new Game();
        game.resetTurnIndex();
        Player player = new Player(0, "Test");
        game.addPlayer(0, player);
        GameTurn.EntityClassTurn turn = Mockito.mock(GameTurn.EntityClassTurn.class);
        Vector<GameTurn> turnVector = new Vector<>();
        turnVector.add(turn);
        GameOptions options = Mockito.mock(GameOptions.class);
        Mockito.when(options.booleanOption(OptionsConstants.INIT_INF_MOVE_MULTI)).thenReturn(true);
        Mockito.when(options.booleanOption(OptionsConstants.INIT_PROTOS_MOVE_MULTI)).thenReturn(true);
        Mockito.when(options.booleanOption(OptionsConstants.ADVGRNDMOV_VEHICLE_LANCE_MOVEMENT)).thenReturn(true);
        Mockito.when(options.booleanOption(OptionsConstants.ADVGRNDMOV_MEK_LANCE_MOVEMENT)).thenReturn(true);
        Mockito.when(options.intOption(OptionsConstants.INIT_INF_PROTO_MOVE_MULTI)).thenReturn(1);
        Mockito.when(options.intOption(OptionsConstants.ADVGRNDMOV_VEHICLE_LANCE_MOVEMENT_NUMBER)).thenReturn(1);
        Mockito.when(options.intOption(OptionsConstants.ADVGRNDMOV_MEK_LANCE_MOVEMENT_NUMBER)).thenReturn(1);
        game.setOptions(options);
        Infantry e1 = Mockito.mock(Infantry.class);
        Protomech e2 = Mockito.mock(Protomech.class);
        Tank e3 = Mockito.mock(Tank.class);
        Mech e4 = Mockito.mock(Mech.class);
        Mockito.when(e1.getOwnerId()).thenReturn(0);
        Mockito.when(e2.getOwnerId()).thenReturn(0);
        Mockito.when(e3.getOwnerId()).thenReturn(0);
        Mockito.when(e4.getOwnerId()).thenReturn(0);
        game.setPhase(IGame.Phase.PHASE_MOVEMENT);
        Mockito.when(turn.isValidClass(GameTurn.CLASS_INFANTRY)).thenReturn(true);
        Mockito.when(turn.isValidClass(~GameTurn.CLASS_INFANTRY)).thenReturn(false);
        Mockito.when(turn.isValidClass(GameTurn.CLASS_PROTOMECH)).thenReturn(true);
        Mockito.when(turn.isValidClass(~GameTurn.CLASS_PROTOMECH)).thenReturn(false);
        Mockito.when(turn.isValidClass(GameTurn.CLASS_TANK)).thenReturn(true);
        Mockito.when(turn.isValidClass(~GameTurn.CLASS_TANK)).thenReturn(false);
        Mockito.when(turn.isValidClass(GameTurn.CLASS_MECH)).thenReturn(true);
        Mockito.when(turn.isValidClass(~GameTurn.CLASS_MECH)).thenReturn(false);
        game.setTurnVector(turnVector);
        game.removeTurnFor(e1);
        TestCase.assertEquals(new Vector<>(), game.getTurnVector());
        game.setTurnVector(turnVector);
        game.removeTurnFor(e2);
        TestCase.assertEquals(new Vector<>(), game.getTurnVector());
        game.setTurnVector(turnVector);
        game.removeTurnFor(e3);
        TestCase.assertEquals(new Vector<>(), game.getTurnVector());
        game.setTurnVector(turnVector);
        game.removeTurnFor(e4);
        TestCase.assertEquals(new Vector<>(), game.getTurnVector());

        Mockito.when(options.booleanOption(OptionsConstants.INIT_INF_MOVE_MULTI)).thenReturn(false);
        Mockito.when(options.booleanOption(OptionsConstants.INIT_INF_MOVE_LATER)).thenReturn(true);
        Mockito.when(turn.isValidEntity(e1, game, false)).thenReturn(true);
        game.setTurnVector(turnVector);
        game.removeTurnFor(e1);
        TestCase.assertEquals(new Vector<>(), game.getTurnVector());

        Mockito.when(options.booleanOption(OptionsConstants.INIT_PROTOS_MOVE_MULTI)).thenReturn(false);
        Mockito.when(options.booleanOption(OptionsConstants.INIT_PROTOS_MOVE_LATER)).thenReturn(true);
        Mockito.when(turn.isValidEntity(e2, game, false)).thenReturn(true);
        game.setTurnVector(turnVector);
        game.removeTurnFor(e2);
        TestCase.assertEquals(new Vector<>(), game.getTurnVector());
    }
}
