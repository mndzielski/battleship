package pl.edu.wat.wcy.isi.pz.battleship.components.button;

import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.components.button.base.BaseButton;
import pl.edu.wat.wcy.isi.pz.battleship.program.ProgramSettings;

@EqualsAndHashCode(callSuper = true)
public class BackButton extends BaseButton {
    private static final String FXML_BACK_BUTTON = "fxml.back.button";

    public BackButton() {
        super(ProgramSettings.getInstance().property(FXML_BACK_BUTTON));
    }
}
