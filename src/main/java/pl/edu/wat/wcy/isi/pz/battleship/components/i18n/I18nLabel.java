package pl.edu.wat.wcy.isi.pz.battleship.components.i18n;

import javafx.scene.control.Label;
import lombok.Getter;

public class I18nLabel extends Label implements I18n {
    @Getter
    public String i18n;

    public void setI18n(String i18n) {
        I18n.super.setI18n(i18n);
    }
}
