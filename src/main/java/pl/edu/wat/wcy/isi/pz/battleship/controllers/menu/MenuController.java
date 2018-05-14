package pl.edu.wat.wcy.isi.pz.battleship.controllers.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.controllers.base.AbstractController;
import pl.edu.wat.wcy.isi.pz.battleship.controllers.menu.base.AbstractMenuController;
import pl.edu.wat.wcy.isi.pz.battleship.loader.ResourcesLoader;
import pl.edu.wat.wcy.isi.pz.battleship.program.ProgramSettings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@EqualsAndHashCode(callSuper = true)
@Data
public class MenuController extends AbstractController {

    private static MenuController instance;

    public static MenuController getInstance() {
        return instance;
    }

    @FXML
    private AnchorPane menuContainer;

    private static final String MAIN_MENU_FXML = "fxml.main.menu";
    private static final String SETTINGS_MENU_FXML = "fxml.settings.menu";
    private static final String STATISTICS_MENU_FXML = "fxml.statistics.menu";
    private static final String CREATORS_FXML = "fxml.creators.menu";

    private static final String SOUND_SETTINGS_MENU_FXML = "fxml.sound.settings.menu";

    @Override
    protected void initializeNode(URL location, ResourceBundle resources) {
        instance = this;
        loadMainMenu();
    }

    private void loadView(String fileName) {
        if (menuContainer != null) {
            try {
                FXMLLoader loader = ResourcesLoader.getFXMLLoader(ProgramSettings.getInstance().property(fileName));
                Parent parent = loader.load();
                ((AbstractMenuController) loader.getController()).setMenuController(this);

                menuContainer.getChildren().clear();
                menuContainer.getChildren().add(parent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadMainMenu() {
        loadView(MAIN_MENU_FXML);
    }

    public void loadSettingsMenu() {
        loadView(SETTINGS_MENU_FXML);
    }

    public void loadStatistics() {
        loadView(STATISTICS_MENU_FXML);
    }

    public void loadSoundSettingsMenu() {
        loadView(SOUND_SETTINGS_MENU_FXML);
    }

    public void loadCreators() {
        loadView(CREATORS_FXML);
    }
}