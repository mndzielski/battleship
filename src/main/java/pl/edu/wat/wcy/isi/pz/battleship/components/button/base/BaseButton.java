package pl.edu.wat.wcy.isi.pz.battleship.components.button.base;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.App;
import pl.edu.wat.wcy.isi.pz.battleship.components.i18n.I18nButton;
import pl.edu.wat.wcy.isi.pz.battleship.loader.ResourcesLoader;
import pl.edu.wat.wcy.isi.pz.battleship.program.ProgramSettings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseButton extends I18nButton implements Initializable {

    @Override
    public void setI18n(String i18n) {
        ProgramSettings.getInstance().i18n(label.textProperty(), i18n);
    }

    @FXML
    public String url;

    @FXML
    private ImageView image;

    @FXML
    private Label label;

    public BaseButton(String componentPath) {
        FXMLLoader fxmlLoader = ResourcesLoader.getFXMLLoader(componentPath);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            App.logger.error(exception.getStackTrace());
        }
    }

    public void setUrl(String url) {
        if (image != null)
            image.setImage(new Image(url));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}