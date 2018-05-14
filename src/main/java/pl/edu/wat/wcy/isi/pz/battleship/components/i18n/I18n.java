package pl.edu.wat.wcy.isi.pz.battleship.components.i18n;

import javafx.beans.property.StringProperty;
import pl.edu.wat.wcy.isi.pz.battleship.program.ProgramSettings;

public interface I18n {
    default void setI18n(String i18n) {
        ProgramSettings.getInstance().i18n(textProperty(), i18n);
    }

    StringProperty textProperty();
}
