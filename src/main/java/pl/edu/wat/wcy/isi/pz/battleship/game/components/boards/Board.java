package pl.edu.wat.wcy.isi.pz.battleship.game.components.boards;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.game.StackPaneField;
import pl.edu.wat.wcy.isi.pz.battleship.game.components.battlefield.AbstractBattlefield;

@EqualsAndHashCode(callSuper = true)
@Data
public class Board<T extends AbstractBattlefield> extends Pane {

    private T battlefield;
    private final int size;
    private final double side;

    public Board(T battlefield, double side, int size) {
        super();
        this.side = side;
        this.size = size;
        addStackPanes(battlefield);

        this.battlefield = battlefield;
    }

    private void addWithLayout(Node node, double layX, double layY) {
        getChildren().add(node);
        node.setLayoutX(layX);
        node.setLayoutY(layY);

    }

    private void addStackPanes(AbstractBattlefield battlefield) {
        StackPaneField emptyStackPane = new StackPaneField(side, null);

        addWithLayout(emptyStackPane, 0, 0);

        for (int i = 1; i <= size; i++) {
            StackPaneField stackPane = new StackPaneField(side, Integer.toString(i));
            addWithLayout(stackPane, 0, i * (side + 1) + 1);
        }

        for (int j = 1; j <= size; j++) {
            StackPaneField stackPane = new StackPaneField(side, Character.toString((char) ('A' + j - 1)));
            addWithLayout(stackPane, j * (side + 1) + 1, 0);
        }

        addWithLayout(battlefield, side + 1, side + 1);
    }
}
