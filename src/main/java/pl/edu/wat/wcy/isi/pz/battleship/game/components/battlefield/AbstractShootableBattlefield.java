package pl.edu.wat.wcy.isi.pz.battleship.game.components.battlefield;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.game.FieldStateEnum;
import pl.edu.wat.wcy.isi.pz.battleship.game.StackPaneField;
import pl.edu.wat.wcy.isi.pz.battleship.game.components.battlefield.ships.ShipFields;
import pl.edu.wat.wcy.isi.pz.battleship.game.events.EndGameEvent;
import pl.edu.wat.wcy.isi.pz.battleship.game.events.handlers.SwitchTurnHandler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractShootableBattlefield extends AbstractBattlefield {
    protected IntegerProperty shipFieldsCheckedSize = new SimpleIntegerProperty(0);
    protected ObservableList<ShipFields> shipFields;

    protected void checkShips(SwitchTurnHandler.Turn turn) {

        List<ShipFields> shipFieldsList = getShipFields().stream().filter(s -> {
            final boolean[] isAllChecked = {true};
            s.getFields().forEach(f -> {
                if (f.getState().equals(FieldStateEnum.SHIP_PART)) {
                    isAllChecked[0] = false;
                }
            });
            return isAllChecked[0];
        }).collect(Collectors.toList());

        shipFieldsList.forEach(s -> {
            s.setVisible(true);
            s.getFields().forEach(f -> {
                f.setStyle("");
            });
        });
        shipFieldsCheckedSize.set(shipFieldsList.size());

        if (shipFieldsList.size() == getShipFields().size()) {
            getParent().fireEvent(new EndGameEvent(turn));
        }
    }

    protected Timeline getTimeline(StackPaneField field) {
        return new Timeline(
                new KeyFrame(Duration.ZERO),
                new KeyFrame(new Duration(200), new KeyValue(field.opacityProperty(), 0.3)),
                new KeyFrame(new Duration(500), new KeyValue(field.opacityProperty(), 1.0)),
                new KeyFrame(new Duration(770), new KeyValue(field.backgroundProperty(), new Background(new BackgroundFill(Paint.valueOf("#5249FF"), CornerRadii.EMPTY, new Insets(0))))),
                new KeyFrame(new Duration(800), new KeyValue(field.backgroundProperty(), new Background(new BackgroundFill(Paint.valueOf("#5249FF"), CornerRadii.EMPTY, new Insets(0))))));

    }

    protected List<StackPaneField> getFreeFields() {
        return Arrays.stream(getFields())
                .flatMap(Arrays::stream)
                .filter(s -> s.getState().equals(FieldStateEnum.SHIP_PART) || s.getState().equals(FieldStateEnum.EMPTY))
                .collect(Collectors.toList());
    }
}

