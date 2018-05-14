package pl.edu.wat.wcy.isi.pz.battleship.program.base;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import pl.edu.wat.wcy.isi.pz.battleship.loader.ResourcesLoader;
import pl.edu.wat.wcy.isi.pz.battleship.program.LanguageSettings;
import pl.edu.wat.wcy.isi.pz.battleship.program.LanguageSettings.Language;
import pl.edu.wat.wcy.isi.pz.battleship.program.StyleSettings;
import pl.edu.wat.wcy.isi.pz.battleship.program.StyleSettings.Style;
import pl.edu.wat.wcy.isi.pz.battleship.program.ProgramData;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

@Data
public class BaseProgramSettings {

    @Setter(AccessLevel.PROTECTED)
    private ResourceBundle resource;

    private ProgramData data;
    private Properties properties;

    LanguageSettings languageSettings = new LanguageSettings();
    StyleSettings styleSettings = new StyleSettings();


    private final DoubleProperty volume = new SimpleDoubleProperty();

    public void setVolume(Double volume) {
        this.volume.setValue(volume);
    }

    public void setStyle(Style style) {
        getStyle().setValue(style);
    }

    public ObjectProperty<Language> getLanguage() {
        return getLanguageSettings().getLanguage();
    }

    public ObjectProperty<Style> getStyle() {
        return getStyleSettings().getStyle();
    }

    public final void setLanguage(Language language) {
        Locale.setDefault(new Locale(language.name()));
        setResource(ResourcesLoader.getResourceBundle(getProperties().getProperty("settings.resource-bundle")));
        getLanguage().set(language);
    }

    protected BaseProgramSettings(Properties properties) {
        setProperties(properties);
        init();
    }

    protected void init() {
        Language language = Language.valueOf(properties.getProperty("settings.language"));
        Style style = Style.valueOf(properties.getProperty("settings.style"));
        Double volume = Double.valueOf(properties.getProperty("settings.volume"));
        data = new ProgramData(Integer.parseInt(properties.getProperty("battleship.size")),
                Double.parseDouble(properties.getProperty("battleship.side")));

        setLanguage(language);
        setStyle(style);
        setVolume(volume);
    }
}