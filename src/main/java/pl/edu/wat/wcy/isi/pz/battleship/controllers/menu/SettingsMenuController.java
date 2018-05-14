package pl.edu.wat.wcy.isi.pz.battleship.controllers.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.controllers.menu.base.AbstractMenuController;
import pl.edu.wat.wcy.isi.pz.battleship.program.ProgramSettings;

import java.net.URL;
import java.util.ResourceBundle;

@EqualsAndHashCode(callSuper = true)
@Data
public class SettingsMenuController extends AbstractMenuController {

    @Override
    protected void initializeNode(URL location, ResourceBundle resources) {
    }

    @FXML
    private void handleMainMenu(ActionEvent event) {
        menuController.loadMainMenu();
    }

    @FXML
    private void handleSoundSettings(ActionEvent event) {
        menuController.loadSoundSettingsMenu();
    }

    @FXML
    private void handleReset(ActionEvent event) {
        ProgramSettings.getInstance().reset();
        menuController.loadSettingsMenu();
    }
}