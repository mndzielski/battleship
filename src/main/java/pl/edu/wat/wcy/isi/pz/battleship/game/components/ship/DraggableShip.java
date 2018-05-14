package pl.edu.wat.wcy.isi.pz.battleship.game.components.ship;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.wat.wcy.isi.pz.battleship.game.common.Orientation;
import pl.edu.wat.wcy.isi.pz.battleship.game.common.Position;
import pl.edu.wat.wcy.isi.pz.battleship.game.events.ChangedShipPositionEvent;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class DraggableShip extends BaseShip {
    private double x = 0;
    private double y = 0;

    private double mouseX = 0;
    private double mouseY = 0;

    private boolean dragging = false;
    private boolean moveToFront = true;

    public DraggableShip(Position position, Orientation orientation, int length) {
        super(position, orientation, length);
        init();
    }

    private double normalize(double value, double max) {
        value = value < 0 ? 0 : value;
        value = value > (max - 1) ? (max - 1) : value;

        return value;
    }

    private int getShorterLengthPart() {
        return length - (length / 2 + 1);
    }

    private void init() {

        onMousePressedProperty().set(event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();

            x = getLayoutX();
            y = getLayoutY();

            if (isMoveToFront()) toFront();


            event.consume();
        });

        onMouseDraggedProperty().set(event -> {
            x += event.getSceneX() - mouseX;
            y += event.getSceneY() - mouseY;

            setLayoutX(normalize(x, ((Pane) getParent()).getWidth() - getPrefWidth()));
            setLayoutY(normalize(y, ((Pane) getParent()).getHeight() - getPrefHeight()));

            dragging = true;

            mouseX = event.getSceneX();
            mouseY = event.getSceneY();

            event.consume();
        });

        onMouseClickedProperty().set(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                double height = getHeight();
                double width = getWidth();

                switch (orientation) {
                    case TOP:
                        position.set(position.get().add(new Position(-getShorterLengthPart(), length / 2)));
                        break;
                    case RIGHT:
                        position.set(position.get().add(new Position(getShorterLengthPart(), -getShorterLengthPart())));
                        break;
                    case BOTTOM:
                        position.set(position.get().add(new Position(-(length / 2), getShorterLengthPart())));
                        break;
                    case LEFT:
                        position.set(position.get().add(new Position((length / 2), -(length / 2))));
                        break;
                }

                orientation = orientation.getNext();

                double parentWidth = ((Pane) getParent()).getWidth();
                double parentHeight = ((Pane) getParent()).getHeight();

                setPrefSize(height, width);

                setLayoutX(normalize(getLayoutX(), parentWidth - getPrefWidth()));
                setLayoutY(normalize(getLayoutY(), parentHeight - getPrefHeight()));

                addInnerPane(height, width);

                fireEvent(new ChangedShipPositionEvent());
            }

            event.consume();
        });

        onMouseReleasedProperty().set(event -> {
            dragging = false;
            position.set((int) Math.round(getLayoutX() / side), (int) Math.round(getLayoutY() / side));

            fireEvent(new ChangedShipPositionEvent());

            event.consume();
        });
    }
}