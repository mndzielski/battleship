package pl.edu.wat.wcy.isi.pz.battleship.components.button;

import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.components.button.base.BaseButton;
import pl.edu.wat.wcy.isi.pz.battleship.program.ProgramSettings;

@EqualsAndHashCode(callSuper = true)
public class SmallBackButton extends BaseButton {
    private static final String FXML_SMALL_BACK_BUTTON = "fxml.small.back.button";

    public SmallBackButton() {
        super(ProgramSettings.getInstance().property(FXML_SMALL_BACK_BUTTON));
    }
}
