package pl.edu.wat.wcy.isi.pz.battleship.program;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Data;

@Data
public class LanguageSettings {
    public enum Language {
        pl, en
    }

    private final BooleanProperty isPl = new SimpleBooleanProperty();
    private final BooleanProperty isEn = new SimpleBooleanProperty();

    private final ObjectProperty<Language> language = new SimpleObjectProperty<>();

    public LanguageSettings() {
        this.language.addListener((observable, oldValue, newValue) -> {
            isPl.setValue(newValue.equals(Language.pl));
            isEn.setValue(newValue.equals(Language.en));
        });
    }
}
