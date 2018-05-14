package pl.edu.wat.wcy.isi.pz.battleship.game.events;

import javafx.event.Event;
import javafx.event.EventType;

public class PlayerTurnStartEvent extends Event {
    public static final EventType<PlayerTurnStartEvent> PLAYER_TURN_START =
            new EventType<>(Event.ANY, "PLAYER_TURN_START");

    public PlayerTurnStartEvent() {
        super(PLAYER_TURN_START);
    }
}
