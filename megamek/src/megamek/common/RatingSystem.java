package megamek.common;

import megamek.common.event.GameEndEvent;
import megamek.common.event.GameListenerAdapter;
import megamek.common.event.GamePlayerChangeEvent;

import java.util.Enumeration;

public class RatingSystem {
    private RatingSystem() {

    }

    public static void calculate(IGame game) {
        // Example rating calculation
        // To be Implemented with a correct rating calculation
        Enumeration<IPlayer> iter = game.getPlayers();
        while (iter.hasMoreElements()) {
            IPlayer player = iter.nextElement();
            if (player.isObserver()) {
                continue;
            }
            if (player.getId() == game.getVictoryPlayerId() || player.getTeam() == game.getVictoryTeam()) {
                player.setRating(player.getRating() + 100);
            }
            else {
                player.setRating(player.getRating() - 100);
            }
            game.processGameEvent(new GamePlayerChangeEvent(game, player));
        }
    }
}
