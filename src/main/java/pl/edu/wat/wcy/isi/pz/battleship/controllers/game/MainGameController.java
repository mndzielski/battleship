package pl.edu.wat.wcy.isi.pz.battleship.controllers.game;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.App;
import pl.edu.wat.wcy.isi.pz.battleship.controllers.base.AbstractController;
import pl.edu.wat.wcy.isi.pz.battleship.controllers.root.RootController;
import pl.edu.wat.wcy.isi.pz.battleship.exceptions.DifferentNumberOfShipsException;
import pl.edu.wat.wcy.isi.pz.battleship.game.components.battlefield.OpponentBattlefield;
import pl.edu.wat.wcy.isi.pz.battleship.game.components.battlefield.PlayerBattlefield;
import pl.edu.wat.wcy.isi.pz.battleship.game.components.boards.Board;
import pl.edu.wat.wcy.isi.pz.battleship.game.components.ship.BaseShip;
import pl.edu.wat.wcy.isi.pz.battleship.game.components.ship.DraggableShip;
import pl.edu.wat.wcy.isi.pz.battleship.game.events.EndGameEvent;
import pl.edu.wat.wcy.isi.pz.battleship.game.events.OpponentTurnStartEvent;
import pl.edu.wat.wcy.isi.pz.battleship.game.events.PlayerTurnStartEvent;
import pl.edu.wat.wcy.isi.pz.battleship.game.events.handlers.SwitchTurnHandler;
import pl.edu.wat.wcy.isi.pz.battleship.program.ProgramSettings;
import pl.edu.wat.wcy.isi.pz.battleship.xml.XmlShipsFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@EqualsAndHashCode(callSuper = true)
@Data
public class MainGameController extends AbstractController {

    private static MainGameController instance;

    public static MainGameController getInstance() {
        return instance;
    }

    @FXML
    private Label playerCheckedShipsSize;

    @FXML
    private Label opponentShipsSize;

    @FXML
    private Label playerShipsSize;

    @FXML
    private Label opponentCheckedShipsSize;

    @FXML
    private GridPane container;

    @FXML
    private GridPane mainContainer1;

    @FXML
    private GridPane mainContainer2;

    private boolean gameOver = false;

    @Override
    protected void initializeNode(URL location, ResourceBundle resources) {
        instance = this;
    }

    public void init(List<DraggableShip> ships) {
        final List<BaseShip> opponentShips = new XmlShipsFactory().getOpponentShips();
        if (ships.size() != opponentShips.size()) throw new DifferentNumberOfShipsException(ships.size(), opponentShips.size());
        OpponentBattlefield opponentBattlefield = new OpponentBattlefield(opponentShips);
        PlayerBattlefield playerBattlefield = new PlayerBattlefield(ships);
        double side = ProgramSettings.getInstance().getData().getSide();
        int size = ProgramSettings.getInstance().getData().getSize();
        Board<OpponentBattlefield> pane1 = new Board<>(opponentBattlefield, side, size);
        Board<PlayerBattlefield> pane2 = new Board<>(playerBattlefield, side, size);

        container.addEventHandler(PlayerTurnStartEvent.PLAYER_TURN_START, event -> {
            if (!gameOver) {
                App.logger.info("PLAYER_TURN_START :: Początek tury gracza");
                pane1.getBattlefield().fireEvent(new PlayerTurnStartEvent());
            }
        });

        container.addEventHandler(OpponentTurnStartEvent.OPPONENT_TURN_START, event -> {
            if (!gameOver) {
                App.logger.info("OPPONENT_TURN_START :: Początek tury przeciwnika");
                pane2.getBattlefield().fireEvent(new OpponentTurnStartEvent());
            }
        });

        container.addEventHandler(EndGameEvent.END_GAME_EVENT, event -> {
            gameOver = true;
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1500)));
            timeline.setOnFinished(event1 -> {
                RootController.getInstance().loadEndGame(event.getTurn());
                App.logger.info("END_GAME_EVENT :: Zakończenie gry :: " + event.getTurn());
            });
            timeline.play();
        });

        container.fireEvent(new PlayerTurnStartEvent());

        mainContainer1.add(pane1, 0, 0);
        mainContainer2.add(pane2, 0, 0);

        opponentShipsSize.textProperty().bind(Bindings.size(playerBattlefield.getShipFields()).asString());
        playerShipsSize.textProperty().bind(Bindings.size(opponentBattlefield.getShipFields()).asString());

        opponentCheckedShipsSize.textProperty().bind(Bindings.createStringBinding(() -> Integer.toString(playerBattlefield.getShipFieldsCheckedSize().get()).concat("/"), playerBattlefield.getShipFieldsCheckedSize()));
        playerCheckedShipsSize.textProperty().bind(Bindings.createStringBinding(() -> Integer.toString(opponentBattlefield.getShipFieldsCheckedSize().get()).concat("/"), opponentBattlefield.getShipFieldsCheckedSize()));
    }

    @FXML
    private void giveUp(ActionEvent event) {
        gameOver = true;
        RootController.getInstance().loadEndGame(SwitchTurnHandler.Turn.OPPONENT);
        App.logger.info("END_GAME_EVENT :: Zakończenie gry :: " + SwitchTurnHandler.Turn.OPPONENT);
    }
}