package pl.edu.wat.wcy.isi.pz.battleship.controllers.root;

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.App;
import pl.edu.wat.wcy.isi.pz.battleship.components.i18n.I18nRadioMenuItem;
import pl.edu.wat.wcy.isi.pz.battleship.controllers.base.AbstractController;
import pl.edu.wat.wcy.isi.pz.battleship.game.events.handlers.SwitchTurnHandler;
import pl.edu.wat.wcy.isi.pz.battleship.h2.Game;
import pl.edu.wat.wcy.isi.pz.battleship.h2.GameResult;
import pl.edu.wat.wcy.isi.pz.battleship.loader.ResourcesLoader;
import pl.edu.wat.wcy.isi.pz.battleship.program.LanguageSettings.Language;
import pl.edu.wat.wcy.isi.pz.battleship.program.ProgramSettings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static pl.edu.wat.wcy.isi.pz.battleship.program.StyleSettings.Style;

@EqualsAndHashCode(callSuper = true)
@Data
public class RootController extends AbstractController {
    private static RootController instance;

    @FXML
    private I18nRadioMenuItem styleSt;

    @FXML
    private I18nRadioMenuItem styleNd;

    @FXML
    private I18nRadioMenuItem styleRd;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public static RootController getInstance() {
        return instance;
    }

    private MediaPlayer mediaPlayer;
    private HostServices hostServices;
    private Stage stage;

    private static final String BASE_MENU_FXML = "fxml.base.menu";
    private static final String START_GAME_FXML = "fxml.start.game";
    private static final String END_GAME_WIN_FXML = "fxml.end.game.win";
    private static final String END_GAME_LOSE_FXML = "fxml.end.game.lose";
    private static final String MAIN_GAME_FXML = "fxml.main.game";

    @FXML
    private GridPane root;

    @FXML
    private I18nRadioMenuItem languagePl;

    @FXML
    private I18nRadioMenuItem languageEn;

    private static final String FIRST_STYLE = "css.style.1";
    private static final String SECOND_STYLE = "css.style.2";
    private static final String THIRD_STYLE = "css.style.3";

    private void loadStyle() {
        loadStyle(ProgramSettings.getInstance().getStyle().get());
    }

    private String getProperty(String property) {
        return ProgramSettings.getInstance().property(property);
    }

    private void loadStyle(Style style) {
        root.getStylesheets().clear();

        switch (style) {
            case first:
                root.getStylesheets().add(getProperty(FIRST_STYLE));
                break;
            case second:
                root.getStylesheets().add(getProperty(SECOND_STYLE));
                break;
            case third:
                root.getStylesheets().add(getProperty(THIRD_STYLE));
                break;
        }
    }

    @Override
    protected void initializeNode(URL location, ResourceBundle resources) {
        loadStyle();

        ProgramSettings.getInstance().getStyle().addListener((observable, oldValue, newValue) -> loadStyle(newValue));

        languagePl.selectedProperty().bindBidirectional(ProgramSettings.getInstance().getLanguageSettings().getIsPl());
        languageEn.selectedProperty().bindBidirectional(ProgramSettings.getInstance().getLanguageSettings().getIsEn());
        languagePl.setOnAction(event -> ProgramSettings.getInstance().setLanguage(Language.pl));
        languageEn.setOnAction(event -> ProgramSettings.getInstance().setLanguage(Language.en));

        styleSt.selectedProperty().bindBidirectional(ProgramSettings.getInstance().getStyleSettings().getIsFirst());
        styleNd.selectedProperty().bindBidirectional(ProgramSettings.getInstance().getStyleSettings().getIsSecond());
        styleRd.selectedProperty().bindBidirectional(ProgramSettings.getInstance().getStyleSettings().getIsThird());

        styleSt.setOnAction(event -> ProgramSettings.getInstance().setStyle(Style.first));
        styleNd.setOnAction(event -> ProgramSettings.getInstance().setStyle(Style.second));
        styleRd.setOnAction(event -> ProgramSettings.getInstance().setStyle(Style.third));

        instance = this;
        loadBaseMenu();

        playMusic();
    }

    private static final String MUSIC = "music.theme";

    private void playMusic() {
        String musicFile = ResourcesLoader.getFileURL(getProperty(MUSIC));

        if (musicFile != null) {
            Media sound = new Media(musicFile);
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.volumeProperty().bind(ProgramSettings.getInstance().getVolume());
            mediaPlayer.play();
        }
    }

    public void init(Stage stage, HostServices hostServices) throws IOException {
        setStage(stage);
        setHostServices(hostServices);
        onClose();
    }

    private void onClose() {
        if (stage != null) {
            stage.setOnCloseRequest(event -> {
                App.logger.info("Zamykanie");
                executorService.shutdown();
            });
        }
    }

    private void loadView(String view) {
        try {
            Parent parent = ResourcesLoader.getFXMLLoader(getProperty(view)).load();
            root.getChildren().clear();
            root.add(parent, 0, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadBaseMenu() {
        loadView(BASE_MENU_FXML);
    }

    public void loadStartGame() {
        loadView(START_GAME_FXML);
    }

    public void loadEndGame(SwitchTurnHandler.Turn turn) {
        Game game = new Game();
        switch (turn) {
            case PLAYER:
                game.setWin(true);
                new GameResult().save(game);
                loadView(END_GAME_WIN_FXML);
                break;
            case OPPONENT:
                game.setWin(false);
                new GameResult().save(game);
                loadView(END_GAME_LOSE_FXML);
                break;
        }
    }

    public void loadMainGame() {
        loadView(MAIN_GAME_FXML);
    }

    @FXML
    private void handleClose(ActionEvent event) {
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));

    }
}
