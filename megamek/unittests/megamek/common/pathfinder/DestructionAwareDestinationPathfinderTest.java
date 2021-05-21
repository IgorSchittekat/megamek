package megamek.common.pathfinder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashSet;

import megamek.common.Aero;
import megamek.common.BattleArmor;
import megamek.common.Coords;
import megamek.common.Game;
import org.junit.Test;

public class DestructionAwareDestinationPathfinderTest {
    @Test
    public void testFindPathToCoords() {
        DestructionAwareDestinationPathfinder destructionAwareDestinationPathfinder = new DestructionAwareDestinationPathfinder();

        Aero aero = new Aero();
        aero.setGame(new Game());
        HashSet<Coords> destinationCoords = new HashSet<Coords>();
        assertNull(
                destructionAwareDestinationPathfinder.findPathToCoords(aero, destinationCoords, new BoardClusterTracker()));
    }

    @Test
    public void testFindPathToCoords2() {
        DestructionAwareDestinationPathfinder destructionAwareDestinationPathfinder = new DestructionAwareDestinationPathfinder();

        BattleArmor battleArmor = new BattleArmor();
        battleArmor.setGame(new Game());
        HashSet<Coords> destinationCoords = new HashSet<Coords>();
        assertNull(destructionAwareDestinationPathfinder.findPathToCoords(battleArmor, destinationCoords,
                new BoardClusterTracker()));
        assertEquals(0, battleArmor.wigeBonus);
    }

    @Test
    public void testFindPathToCoords3() {
        DestructionAwareDestinationPathfinder destructionAwareDestinationPathfinder = new DestructionAwareDestinationPathfinder();
        Aero entity = new Aero();
        HashSet<Coords> destinationCoords = new HashSet<Coords>();
        assertNull(destructionAwareDestinationPathfinder.findPathToCoords(entity, destinationCoords, true,
                new BoardClusterTracker()));
    }

    @Test
    public void testGetClosestCoords() {
        HashSet<Coords> destinationRegion = new HashSet<Coords>();
        assertNull(DestructionAwareDestinationPathfinder.getClosestCoords(destinationRegion, new Aero()));
    }
}

