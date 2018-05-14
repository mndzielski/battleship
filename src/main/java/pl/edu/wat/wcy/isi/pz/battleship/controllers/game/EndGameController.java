package pl.edu.wat.wcy.isi.pz.battleship.controllers.game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pl.edu.wat.wcy.isi.pz.battleship.controllers.base.AbstractController;
import pl.edu.wat.wcy.isi.pz.battleship.controllers.root.RootController;

import java.net.URL;
import java.util.ResourceBundle;

public class EndGameController extends AbstractController {

    @Override
    protected void initializeNode(URL location, ResourceBundle resources) {
    }

    @FXML
    public void loadMenu(ActionEvent event) {
        RootController.getInstance().loadBaseMenu();
    }
}
