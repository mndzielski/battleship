package pl.edu.wat.wcy.isi.pz.battleship.game.events;

import javafx.event.Event;
import javafx.event.EventType;

public class OpponentTurnEndEvent extends Event {
    public static final EventType<OpponentTurnEndEvent> OPPONENT_TURN_END =
            new EventType<>(Event.ANY, "OPPONENT_TURN_END");

    public OpponentTurnEndEvent() {
        super(OPPONENT_TURN_END);
    }
}
