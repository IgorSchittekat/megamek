package megamek.client.ui.swing.boardview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Vector;

import megamek.client.Client;
import megamek.client.ui.swing.ClientGUI;
import megamek.client.ui.swing.util.MegaMekController;
import megamek.common.Board;
import megamek.common.Entity;
import megamek.common.Flare;
import megamek.common.Game;
import megamek.common.IBoard;
import megamek.common.IGame;
import megamek.common.IPlayer;
import megamek.common.Report;
import megamek.common.TagInfo;
import megamek.common.weapons.AttackHandler;
import org.junit.Test;

public class BoardView1Test {
    @Test
    public void testConstructor() throws IOException {
        Game game = new Game();
        MegaMekController megaMekController = new MegaMekController();
        Client client = new Client("Name", "localhost", 8080);
        ClientGUI clientGUI = new ClientGUI(client, new MegaMekController());
        new BoardView1(game, megaMekController, clientGUI);
        assertEquals("0.49.0-SNAPSHOT", game.mmVersion);
        assertNull(game.getTurn());
        Vector<Vector<Report>> allReports = game.getAllReports();
        Vector<TagInfo> tagInfo = game.getTagInfo();
        assertEquals(allReports, tagInfo);
        assertTrue(game.getSmokeCloudList() instanceof java.util.concurrent.CopyOnWriteArrayList);
        assertEquals(0, game.getRoundCount());
        Vector<IPlayer> playersVector = game.getPlayersVector();
        assertEquals(allReports, playersVector);
        assertEquals(tagInfo, playersVector);
        assertEquals(IGame.Phase.PHASE_UNKNOWN, game.getPhase());
        assertEquals(0, game.getArtillerySize());
        Vector<Entity> outOfGameEntitiesVector = game.getOutOfGameEntitiesVector();
        assertEquals(allReports, outOfGameEntitiesVector);
        assertEquals(playersVector, outOfGameEntitiesVector);
        assertEquals(tagInfo, outOfGameEntitiesVector);
        IBoard iBoard = game.board;
        IBoard board = game.getBoard();
        assertSame(iBoard, board);
        assertTrue(board instanceof Board);
        assertEquals(0, game.getNoOfTeams());
        assertSame(iBoard, board);
        assertEquals(0, game.getNoOfPlayers());
        assertEquals(0, game.getNoOfInitiativeRerollRequests());
        assertEquals(0, game.getNoOfEntities());
        assertEquals(1, game.getNextEntityId());
        assertEquals(IGame.Phase.PHASE_UNKNOWN, game.getLastPhase());
        Vector<Flare> flares = game.getFlares();
        assertEquals(allReports, flares);
        assertEquals(outOfGameEntitiesVector, flares);
        assertEquals(playersVector, flares);
        assertEquals(tagInfo, flares);
        assertEquals(-1, game.getFirstEntityNum());
        assertNull(game.getFirstEntity());
        assertEquals(0, game.getExternalGameId());
        Vector<AttackHandler> attacksVector = game.getAttacksVector();
        assertEquals(allReports, attacksVector);
        assertEquals(flares, attacksVector);
        assertEquals(outOfGameEntitiesVector, attacksVector);
        assertEquals(playersVector, attacksVector);
        assertEquals(tagInfo, attacksVector);
        assertNull(megaMekController.boardEditor);
        assertNull(megaMekController.clientgui);
        assertNull(clientGUI.cb2);
        assertNull(clientGUI.minimapW);
        assertNull(clientGUI.minimap);
        assertNull(clientGUI.mechW);
        assertNull(clientGUI.mechD);
        assertNull(clientGUI.chatlounge);
    }
}

