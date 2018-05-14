package pl.edu.wat.wcy.isi.pz.battleship.components.button;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.components.button.base.BaseButton;
import pl.edu.wat.wcy.isi.pz.battleship.program.ProgramSettings;

@EqualsAndHashCode(callSuper = true)
@Data
public class ImageButton extends BaseButton {
    private static final String FXML_MENU_BUTTON = "fxml.menu.button";

    public ImageButton() {
        super(ProgramSettings.getInstance().property(FXML_MENU_BUTTON));
    }
}
