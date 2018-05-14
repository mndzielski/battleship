package pl.edu.wat.wcy.isi.pz.battleship.game.components.battlefield;

import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.App;
import pl.edu.wat.wcy.isi.pz.battleship.game.FieldStateEnum;
import pl.edu.wat.wcy.isi.pz.battleship.game.StackPaneField;
import pl.edu.wat.wcy.isi.pz.battleship.game.common.Position;
import pl.edu.wat.wcy.isi.pz.battleship.game.components.battlefield.ships.ShipFields;
import pl.edu.wat.wcy.isi.pz.battleship.game.components.ship.BaseShip;
import pl.edu.wat.wcy.isi.pz.battleship.game.events.OpponentTurnStartEvent;
import pl.edu.wat.wcy.isi.pz.battleship.game.events.PlayerTurnEndEvent;
import pl.edu.wat.wcy.isi.pz.battleship.game.events.PlayerTurnStartEvent;
import pl.edu.wat.wcy.isi.pz.battleship.game.events.handlers.SwitchTurnHandler;
import pl.edu.wat.wcy.isi.pz.battleship.program.ProgramSettings;

import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class OpponentBattlefield extends AbstractShootableBattlefield {
    public OpponentBattlefield(List<BaseShip> ships) {
        super();

        addEventHandler(PlayerTurnStartEvent.PLAYER_TURN_START, event -> {
            App.logger.info("PLAYER_TURN_START :: Początek tury gracza");
            SwitchTurnHandler.setTurn(SwitchTurnHandler.Turn.PLAYER);

            event.consume();
        });

        addEventHandler(PlayerTurnEndEvent.PLAYER_TURN_END, event -> {
            App.logger.info("PLAYER_TURN_END :: Koniec tury gracza");
            SwitchTurnHandler.setTurn(SwitchTurnHandler.Turn.NO_ONE);

            event.consume();
        });

        Arrays.stream(getFields()).forEach(a -> {
            Arrays.stream(a).forEach((StackPaneField stackPaneField) -> {
                stackPaneField.hoverProperty().addListener((observable, oldValue, newValue) -> {
                    Position pos = stackPaneField.getPosition();

                    final int size =ProgramSettings.getInstance().getData().getSize();

                    for (int x = 0; x < size; x++) {
                        for (int y = 0; y < size; y++) {
                            StackPaneField arrPart = this.fields[x][y];

                            if (arrPart.hasEditableState())
                                arrPart.getRectangle()
                                        .setStyle((x != pos.getX() && y != pos.getY() || !observable.getValue() || !SwitchTurnHandler.Turn.PLAYER.equals(SwitchTurnHandler.getTurn())) ? "-fx-fill: #ffffff" : "-fx-fill: rgba(242,242,242,0.81)");
                        }
                    }
                });

                stackPaneField.onMouseClickedProperty().set(new SwitchTurnHandler() {
                    @Override
                    protected void performAction(Event event) {
                        if (stackPaneField.getState().equals(FieldStateEnum.SHIP_PART) || stackPaneField.getState().equals(FieldStateEnum.EMPTY)) {
                            shot(stackPaneField);
                        }
                    }
                });
            });
        });

        shipFields = FXCollections.observableArrayList(ShipFields.getShips(ships, getFields()));
        shipFields.forEach(s -> {
            s.setMouseTransparent(true);
            s.setVisible(false);
        });
        getChildren().addAll(shipFields);
    }

    private void shot(StackPaneField stackPaneField) {
        fireEvent(new PlayerTurnEndEvent());
        Timeline timeline = getTimeline(stackPaneField);

        App.logger.info("Gracz strzela w " + stackPaneField.getPosition());

        timeline.onFinishedProperty().set(event -> {
            boolean result = stackPaneField.shot();
            checkShips(SwitchTurnHandler.Turn.PLAYER);
            if (result) {
                if (stackPaneField.getState().equals(FieldStateEnum.SHIP_PART_CHECKED)) {
                    App.logger.info("Gracz trafił nowa tura dla niego");
                    getParent().fireEvent(new PlayerTurnStartEvent());
                } else {
                    App.logger.info("Gracz nie trafił nowa tura dla przeciwnika");
                    getParent().fireEvent(new OpponentTurnStartEvent());
                }
            }
        });
        timeline.play();
    }
}
