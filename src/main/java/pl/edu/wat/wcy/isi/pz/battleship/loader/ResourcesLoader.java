package pl.edu.wat.wcy.isi.pz.battleship.loader;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import pl.edu.wat.wcy.isi.pz.battleship.program.ProgramSettings;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ResourcesLoader {
    private static final ClassLoader classLoader = ResourcesLoader.class.getClassLoader();

    public static ResourceBundle getResourceBundle(String filename) {
        return ResourceBundle.getBundle(filename, Locale.getDefault());
    }

    public static FXMLLoader getFXMLLoader(String urlName) {
        return new FXMLLoader(classLoader.getResource(urlName), ProgramSettings.getInstance().getResource());
    }

    public static Image getImage(String filename) {
        return new Image(classLoader.getResourceAsStream(filename));
    }

    public static String getFileURL(String filename) {
        URL url = classLoader.getResource(filename);
        return url != null  ? url.toString() : null;
    }

    public static InputStream getProperties(String filename) {
        return classLoader.getResourceAsStream(filename);
    }

    public static InputStream getXML(String filename) {
        return classLoader.getResourceAsStream(filename);
    }
}
