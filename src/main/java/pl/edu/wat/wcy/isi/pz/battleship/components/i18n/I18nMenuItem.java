package pl.edu.wat.wcy.isi.pz.battleship.components.i18n;

import javafx.scene.control.MenuItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.edu.wat.wcy.isi.pz.battleship.program.ProgramSettings;

public class I18nMenuItem extends MenuItem implements I18n {
    @Getter
    public String i18n;

    public void setI18n(String i18n) {
        I18n.super.setI18n(i18n);
    }
}
