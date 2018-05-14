package pl.edu.wat.wcy.isi.pz.battleship.controllers.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.controllers.menu.base.AbstractMenuController;
import pl.edu.wat.wcy.isi.pz.battleship.controllers.root.RootController;

import java.net.URL;
import java.util.ResourceBundle;

@EqualsAndHashCode(callSuper = true)
@Data
public class MainMenuController extends AbstractMenuController {

    @FXML
    private GridPane mainMenuContainer;

    @Override
    protected void initializeNode(URL location, ResourceBundle resources) {
    }

    @FXML
    private void handleStart(ActionEvent event) {
        RootController.getInstance().loadStartGame();
    }

    @FXML
    private void handleSettings(ActionEvent event) {
        menuController.loadSettingsMenu();
    }

    @FXML
    private void handleStatistics(ActionEvent event) {
        menuController.loadStatistics();
    }

    @FXML
    private void handleCreators(ActionEvent event) {
        menuController.loadCreators();
    }
    @FXML
    private void handleExit(ActionEvent event) {
        RootController rootController = RootController.getInstance();
        Stage stage = rootController.getStage();

        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
