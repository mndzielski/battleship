package pl.edu.wat.wcy.isi.pz.battleship.controllers.base;

import javafx.fxml.Initializable;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;

@Data
public abstract class AbstractController implements Initializable {

    @Override
    public final void initialize(URL location, ResourceBundle resources) {
        initializeNode(location, resources);
    }

    protected abstract void initializeNode(URL location, ResourceBundle resources);
}
