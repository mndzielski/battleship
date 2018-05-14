package pl.edu.wat.wcy.isi.pz.battleship;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import pl.edu.wat.wcy.isi.pz.battleship.controllers.root.RootController;
import pl.edu.wat.wcy.isi.pz.battleship.loader.ResourcesLoader;
import pl.edu.wat.wcy.isi.pz.battleship.program.ProgramSettings;

import java.io.IOException;

public class App extends Application {

    public final static Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("BattleShip - Start");
        launch(args);
    }

    private static final String FXML_ROOT = "fxml.root";
    private static final String APP_TITLE = "app.title";
    private static final String PROGRAM_ICON = "program.icon";
    private static final String PROGRAM_WIDTH = "program.width";
    private static final String PROGRAM_HEIGHT = "program.height";

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        ProgramSettings settings = ProgramSettings.getNewInstance();
        FXMLLoader loader = ResourcesLoader.getFXMLLoader(settings.property(FXML_ROOT));

        settings.i18n(stage.titleProperty(), APP_TITLE);
        stage.getIcons().add(ResourcesLoader.getImage(settings.property(PROGRAM_ICON)));

        Parent root = loader.load();
        ((RootController) loader.getController()).init(stage, getHostServices());
        stage.setScene(new Scene(root, Integer.parseInt(settings.property(PROGRAM_WIDTH)),
                Integer.parseInt(settings.property(PROGRAM_HEIGHT))));
        stage.show();
    }
}
