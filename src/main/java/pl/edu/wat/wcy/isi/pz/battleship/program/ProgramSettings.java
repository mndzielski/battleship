package pl.edu.wat.wcy.isi.pz.battleship.program;

import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.exceptions.NoPropertyException;
import pl.edu.wat.wcy.isi.pz.battleship.loader.ResourcesLoader;
import pl.edu.wat.wcy.isi.pz.battleship.program.base.BaseProgramSettings;

import java.io.IOException;
import java.util.Properties;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProgramSettings extends BaseProgramSettings {

    private static ProgramSettings instance;

    public static ProgramSettings getInstance() {
        return instance;
    }

    public static ProgramSettings getNewInstance() throws IOException {

        Properties properties = new Properties();
        properties.load(ResourcesLoader.getProperties("config/config.properties"));

        instance = new ProgramSettings(properties);
        return instance;
    }

    public void i18n(StringProperty stringProperty, String key) {
        stringProperty.bind(Bindings.createStringBinding(() -> getResource().getString(key), getLanguage()));
    }

    public void i18n(StringProperty stringProperty, String key, String val) {
        stringProperty.bind(Bindings.createStringBinding(() -> getResource().getString(key).concat(val), getLanguage()));
    }

    public String property(String key) throws NoPropertyException {
        final String property = getProperties().getProperty(key);
        if (property == null) throw new NoPropertyException(key);
        return property;
    }

    public void reset() {
        super.init();
    }

    private ProgramSettings(Properties properties) {
        super(properties);
    }
}