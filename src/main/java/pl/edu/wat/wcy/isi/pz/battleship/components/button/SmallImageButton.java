package pl.edu.wat.wcy.isi.pz.battleship.components.button;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.components.button.base.BaseButton;
import pl.edu.wat.wcy.isi.pz.battleship.program.ProgramSettings;

@EqualsAndHashCode(callSuper = true)
@Data
public class SmallImageButton extends BaseButton {
    private static final String FXML_SMALL_MENU_BUTTON = "fxml.small.menu.button";

    public SmallImageButton() {
        super(ProgramSettings.getInstance().property(FXML_SMALL_MENU_BUTTON));
    }
}