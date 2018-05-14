package pl.edu.wat.wcy.isi.pz.battleship.game.components.battlefield;

import javafx.scene.layout.Pane;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.game.FieldStateEnum;
import pl.edu.wat.wcy.isi.pz.battleship.game.StackPaneField;
import pl.edu.wat.wcy.isi.pz.battleship.game.common.Position;
import pl.edu.wat.wcy.isi.pz.battleship.program.ProgramSettings;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractBattlefield extends Pane {

    protected final double side;
    protected final int size;
    protected final StackPaneField[][] fields;

    protected AbstractBattlefield() {
        side = ProgramSettings.getInstance().getData().getSide();
        size = ProgramSettings.getInstance().getData().getSize();
        fields = new StackPaneField[size][size];
        init();
    }

    private void init() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                StackPaneField stackPane = new StackPaneField(side, FieldStateEnum.EMPTY, new Position(i, j));

                stackPane.setStyle(stackPane.getStyle() +
                        "-fx-text-fill: transparent; -fx-border-color: transparent;");
                stackPane.setLayoutX(i * (side + 1));
                stackPane.setLayoutY(j * (side + 1));
                getChildren().add(stackPane);
                fields[i][j] = stackPane;
            }
        }

        setStyle("-fx-background-color: transparent;");
    }
}