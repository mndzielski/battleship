package pl.edu.wat.wcy.isi.pz.battleship.game.events;

import javafx.event.Event;
import javafx.event.EventType;

public class PlayerTurnEndEvent extends Event {
    public static final EventType<PlayerTurnEndEvent> PLAYER_TURN_END =
            new EventType<>(Event.ANY, "PLAYER_TURN_END");

    public PlayerTurnEndEvent() {
        super(PLAYER_TURN_END);
    }
}