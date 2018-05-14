package pl.edu.wat.wcy.isi.pz.battleship.game;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.game.common.Position;
import pl.edu.wat.wcy.isi.pz.battleship.loader.ResourcesLoader;

@EqualsAndHashCode(callSuper = true)
@Data
public class StackPaneField extends StackPane {
    public boolean hasEditableState() {
        return state.equals(FieldStateEnum.EMPTY) || state.equals(FieldStateEnum.SHIP_PART);
    }

    private FieldStateEnum state;
    private Rectangle rectangle;
    private Position position;
    private ImageView imageView;

    public StackPaneField(double side, FieldStateEnum state, Position position) {
        super();

        this.state = state;
        this.position = position;
        this.rectangle = new Rectangle(side, side);
        this.rectangle.getStyleClass().addAll("rectangle-battlefield", "rectangle-base");

        this.getStyleClass().add("stack-pane-base");
        getChildren().addAll(this.rectangle);

        this.imageView = new ImageView(ResourcesLoader.getImage("images/Delete_50px.png"));
        this.imageView.setFitHeight(side - 2);
        this.imageView.setFitWidth(side - 2);
    }

    public StackPaneField(double side, String content) {
        super();
        this.state = FieldStateEnum.NOT_ACTIVE;
        this.rectangle = new Rectangle(side, side);
        this.rectangle.getStyleClass().addAll("rectangle-base");
        this.rectangle.setStyle("-fx-fill: #cecece");
        setStyle("-fx-background-color: transparent");

        getChildren().add(this.rectangle);

        if (content != null) {
            getChildren().add(new Text(content));
        }
    }

    public boolean shot() {
        switch (getState()) {
            case EMPTY:
                setState(FieldStateEnum.EMPTY_CHECKED);
                setStyle(getStyle() + "-fx-background-color: rgba(255,255,255,0)");
                this.rectangle.setStyle("-fx-fill: rgba(169,169,169,0.35)");
                toFront();
                return true;
            case SHIP_PART:
                setState(FieldStateEnum.SHIP_PART_CHECKED);
                setStyle(getStyle() + "-fx-background-color: rgba(255,255,255,0)");
                this.rectangle.setStyle("-fx-fill: rgba(189,5,0,0.35)");
                getChildren().add(this.imageView);
                toFront();
                return true;
            default:
                return false;
        }
    }
}