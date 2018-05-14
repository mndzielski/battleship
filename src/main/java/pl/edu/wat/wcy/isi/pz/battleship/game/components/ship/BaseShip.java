package pl.edu.wat.wcy.isi.pz.battleship.game.components.ship;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.exceptions.NotPositiveLengthOfShipException;
import pl.edu.wat.wcy.isi.pz.battleship.game.common.ObservablePosition;
import pl.edu.wat.wcy.isi.pz.battleship.game.common.Orientation;
import pl.edu.wat.wcy.isi.pz.battleship.game.common.Position;
import pl.edu.wat.wcy.isi.pz.battleship.loader.ResourcesLoader;
import pl.edu.wat.wcy.isi.pz.battleship.program.ProgramSettings;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseShip extends StackPane {

    public final double side;

    protected final ObservablePosition position;
    protected Orientation orientation;
    protected final int length;

    public static BaseShip builder(String x, String y, String orientation, String length) {
        return new BaseShip(new Position(Integer.parseInt(x), Integer.parseInt(y)), Orientation.valueOf(orientation), Integer.parseInt(length));
    }

    public BaseShip(Position position, Orientation orientation, int length) {
        super();
        this.side = ProgramSettings.getInstance().getData().getSide() + 1;

        this.orientation = orientation;
        this.length = length;
        if(length <= 0) throw new NotPositiveLengthOfShipException(length);
        this.position = new ObservablePosition(this.side);
        layoutXProperty().bindBidirectional(this.position.getX());
        layoutYProperty().bindBidirectional(this.position.getY());
        this.position.set(position);

        double a = this.side, b = this.side;

        if (orientation.isVertical()) {
            a *= length;
        } else {
            b *= length;
        }

        setPrefSize(a + 1, b + 1);
        addInnerPane(a + 1, b + 1);

        setNormalColor();
    }

    public void setNormalColor() {
        setStyle("-fx-background-color: rgba(78, 105, 245, 0.3);");

    }

    public void setConflictColor() {
        setStyle("-fx-background-color: rgba(255, 0, 0, 0.3);");
    }

    private static final String SHIP_IMAGE = "image.ship";

    protected void addInnerPane(double width, double height) {
        ImageView iv = new ImageView(ResourcesLoader.getImage(ProgramSettings.getInstance().property(SHIP_IMAGE)));

        iv.setRotate(orientation.getRotate());
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        ImageView imageView = new ImageView(iv.snapshot(params, null));

        imageView.setFitWidth(width - 2);
        imageView.setFitHeight(height - 2);

        getChildren().clear();
        getChildren().add(imageView);
    }
}