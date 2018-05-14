package pl.edu.wat.wcy.isi.pz.battleship.game.events;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChangedShipPositionEvent extends Event {
    public static final EventType<ChangedShipPositionEvent> CHANGED_SHIP_POSITION =
            new EventType<>(Event.ANY, "CHANGED_SHIP_POSITION");

    public ChangedShipPositionEvent() {
        super(CHANGED_SHIP_POSITION);
    }
}
