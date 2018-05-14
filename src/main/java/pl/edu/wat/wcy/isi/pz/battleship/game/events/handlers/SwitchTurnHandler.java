package pl.edu.wat.wcy.isi.pz.battleship.game.events.handlers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import lombok.Data;

@Data
public abstract class SwitchTurnHandler implements EventHandler<MouseEvent> {

    public enum Turn {PLAYER, OPPONENT, NO_ONE}

    private static ObjectProperty<Turn> turn = new SimpleObjectProperty<>(null);

    public static Turn getTurn() {
        return SwitchTurnHandler.turn.get();
    }

    public static void setTurn(Turn turn) {
        SwitchTurnHandler.turn.set(turn);
    }

    protected abstract void performAction(Event event);

    @Override
    public void handle(MouseEvent event) {
        if (Turn.PLAYER.equals(turn.get())) {
            performAction(event);
        }
    }
}
