package pl.edu.wat.wcy.isi.pz.battleship.controllers.game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.components.button.SmallImageButton;
import pl.edu.wat.wcy.isi.pz.battleship.controllers.base.AbstractController;
import pl.edu.wat.wcy.isi.pz.battleship.controllers.root.RootController;
import pl.edu.wat.wcy.isi.pz.battleship.game.components.battlefield.DraggableBattlefield;
import pl.edu.wat.wcy.isi.pz.battleship.game.components.boards.Board;
import pl.edu.wat.wcy.isi.pz.battleship.game.components.ship.DraggableShip;
import pl.edu.wat.wcy.isi.pz.battleship.program.ProgramSettings;
import pl.edu.wat.wcy.isi.pz.battleship.xml.XmlShipsFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@EqualsAndHashCode(callSuper = true)
@Data
public class StartGameController extends AbstractController {

    @FXML
    private Label conflictLabel;

    @FXML
    private SmallImageButton nextButton;

    @FXML
    private GridPane battlefieldPane;

    private Board<DraggableBattlefield> battlefield;

    private static StartGameController instance;

    public static StartGameController getInstance() {
        return instance;
    }

    @Override
    protected void initializeNode(URL location, ResourceBundle resources) {
        instance = this;

        List<DraggableShip> ships = new XmlShipsFactory().getPlayerShips();
        double side = ProgramSettings.getInstance().getData().getSide();
        int size = ProgramSettings.getInstance().getData().getSize();

        this.battlefield = new Board<>(new DraggableBattlefield(ships), side, size);
        battlefieldPane.add(this.battlefield, 0, 0);

        conflictLabel.visibleProperty().bind(battlefield.getBattlefield().getConflict());
        nextButton.disableProperty().bind(battlefield.getBattlefield().getConflict());
    }

    @FXML
    private void handleBack(ActionEvent event) {
        RootController.getInstance().loadBaseMenu();
    }

    public void handleNext(ActionEvent event) {
        if (!battlefield.getBattlefield().getConflict().get()) {
            RootController.getInstance().loadMainGame();
            MainGameController controller = MainGameController.getInstance();

            controller.init(battlefield.getBattlefield().getShips());
        }
    }
}