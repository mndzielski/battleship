package pl.edu.wat.wcy.isi.pz.battleship.program;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Data;

@Data
public class StyleSettings {

    public enum Style {
        first, second, third
    }

    private final BooleanProperty isFirst = new SimpleBooleanProperty();
    private final BooleanProperty isSecond = new SimpleBooleanProperty();
    private final BooleanProperty isThird = new SimpleBooleanProperty();

    private final ObjectProperty<Style> style = new SimpleObjectProperty<>();

    public StyleSettings() {
        this.style.addListener((observable, oldValue, newValue) -> {
            isFirst.setValue(newValue.equals(Style.first));
            isSecond.setValue(newValue.equals(Style.second));
            isThird.setValue(newValue.equals(Style.third));
        });
    }

}
