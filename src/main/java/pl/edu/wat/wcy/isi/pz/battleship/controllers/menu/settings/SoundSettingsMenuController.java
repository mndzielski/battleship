package pl.edu.wat.wcy.isi.pz.battleship.controllers.menu.settings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import pl.edu.wat.wcy.isi.pz.battleship.controllers.menu.base.AbstractMenuController;
import pl.edu.wat.wcy.isi.pz.battleship.program.ProgramSettings;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Marcin Niedzielski.
 * Wojskowa Akademia Techniczna im. Jarosława Dąbrowskiego, Warszawa 2017r.
 */
public class SoundSettingsMenuController extends AbstractMenuController {

    @FXML
    private Label volume;

    @FXML
    private Slider slider;

    private int toSliderValue(Double value) {
        value = value * 100;
        return value.intValue();
    }

    private Double toVolume(Double value) {
        return value / 100;

    }

    @Override
    protected void initializeNode(URL location, ResourceBundle resources) {
        ProgramSettings settings = ProgramSettings.getInstance();

        slider.setValue(toSliderValue(settings.getVolume().getValue()));
        volume.setText(toSliderValue(settings.getVolume().getValue()) + "%");

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            volume.setText(newValue.intValue() + "%");
            settings.setVolume(toVolume(newValue.doubleValue()));
        });

    }

    @FXML
    private void handleSettings(ActionEvent event) {
        menuController.loadSettingsMenu();
    }

}
