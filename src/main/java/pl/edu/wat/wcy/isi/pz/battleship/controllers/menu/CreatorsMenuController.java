package pl.edu.wat.wcy.isi.pz.battleship.controllers.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.controllers.menu.base.AbstractMenuController;
import pl.edu.wat.wcy.isi.pz.battleship.rest.GithubClient;

import java.net.URL;
import java.util.ResourceBundle;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreatorsMenuController extends AbstractMenuController {

    @FXML
    private Label bio;

    @FXML
    private ImageView avatar;

    @FXML
    private Label login;

    @FXML
    private Hyperlink html;

    @Override
    protected void initializeNode(URL location, ResourceBundle resources) {
        new GithubClient(githubUser -> {
            login.setText(githubUser.getLogin());
            html.setText(githubUser.getHtml_url());
            bio.setText(githubUser.getBio());
        }, image -> avatar.setImage(image));
    }

    @FXML
    private void handleMainMenu(ActionEvent event) {
        menuController.loadMainMenu();
    }
}