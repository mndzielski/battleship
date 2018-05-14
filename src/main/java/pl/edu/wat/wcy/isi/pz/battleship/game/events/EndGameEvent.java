package pl.edu.wat.wcy.isi.pz.battleship.game.events;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.game.events.handlers.SwitchTurnHandler;

@EqualsAndHashCode(callSuper = true)
@Data
public class EndGameEvent extends Event {
    public static final EventType<EndGameEvent> END_GAME_EVENT =
            new EventType<>(Event.ANY, "END_GAME_EVENT");

    private final SwitchTurnHandler.Turn turn;

    public EndGameEvent(SwitchTurnHandler.Turn turn) {
        super(END_GAME_EVENT);
        this.turn = turn;
    }
}