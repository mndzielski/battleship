package pl.edu.wat.wcy.isi.pz.battleship.game.components.battlefield;

import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.App;
import pl.edu.wat.wcy.isi.pz.battleship.game.FieldStateEnum;
import pl.edu.wat.wcy.isi.pz.battleship.game.StackPaneField;
import pl.edu.wat.wcy.isi.pz.battleship.game.components.battlefield.ships.ShipFields;
import pl.edu.wat.wcy.isi.pz.battleship.game.components.ship.DraggableShip;
import pl.edu.wat.wcy.isi.pz.battleship.game.events.OpponentTurnEndEvent;
import pl.edu.wat.wcy.isi.pz.battleship.game.events.OpponentTurnStartEvent;
import pl.edu.wat.wcy.isi.pz.battleship.game.events.PlayerTurnStartEvent;
import pl.edu.wat.wcy.isi.pz.battleship.game.events.handlers.SwitchTurnHandler;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
public class PlayerBattlefield extends AbstractShootableBattlefield {

    public PlayerBattlefield(List<DraggableShip> ships) {
        super();

        addEventHandler(OpponentTurnStartEvent.OPPONENT_TURN_START, event -> {
            App.logger.info("OPPONENT_TURN_START :: Początek tury przeciwnika");
            SwitchTurnHandler.setTurn(SwitchTurnHandler.Turn.OPPONENT);
            shot();
            event.consume();
        });

        addEventHandler(OpponentTurnEndEvent.OPPONENT_TURN_END, event -> {
            App.logger.info("OPPONENT_TURN_END :: Koniec tury przeciwnika");
            SwitchTurnHandler.setTurn(SwitchTurnHandler.Turn.NO_ONE);
            event.consume();
        });

        shipFields = FXCollections.observableArrayList(ShipFields.getShips(ships, getFields()));
        getChildren().addAll(shipFields);
    }


    public void shot() {
        fireEvent(new OpponentTurnEndEvent());

        List<StackPaneField> freeFields = getFreeFields();
        if (new Random().nextInt(10) < 3) {
            freeFields = freeFields.stream().filter(s -> s.getState().equals(FieldStateEnum.SHIP_PART)).collect(Collectors.toList());
        }

        StackPaneField field = freeFields.get(new Random().nextInt(freeFields.size()));
        App.logger.info("Przeciwnik strzela w " + field.getPosition());

        Timeline timeline = getTimeline(field);
        timeline.onFinishedProperty().set(event -> {
            boolean result = field.shot();
            checkShips(SwitchTurnHandler.Turn.OPPONENT);
            if (result) {
                if (field.getState().equals(FieldStateEnum.SHIP_PART_CHECKED)) {
                    App.logger.info("Przeciwnik trafił nowa tura dla niego");
                    getParent().fireEvent(new OpponentTurnStartEvent());
                } else {
                    App.logger.info("Przeciwnik nie trafił tura dla ciebie");
                    getParent().fireEvent(new PlayerTurnStartEvent());
                }
            }
        });
        timeline.play();

    }
}
