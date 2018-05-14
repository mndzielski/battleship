package pl.edu.wat.wcy.isi.pz.battleship.game.events;

import javafx.event.Event;
import javafx.event.EventType;

public class OpponentTurnStartEvent extends Event {
    public static final EventType<OpponentTurnStartEvent> OPPONENT_TURN_START =
            new EventType<>(Event.ANY, "OPPONENT_TURN_START");

    public OpponentTurnStartEvent() {
        super(OPPONENT_TURN_START);
    }}
