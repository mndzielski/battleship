package pl.edu.wat.wcy.isi.pz.battleship.game.common;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import lombok.Data;

@Data
public class ObservablePosition {

    private final DoubleProperty x = new SimpleDoubleProperty();
    private final DoubleProperty y = new SimpleDoubleProperty();

    private double multiplyBy;

    public ObservablePosition(double multiplyBy) {
        this(multiplyBy, 0, 0);
    }

    public void set(int valueX, int valueY) {
        getX().set(valueX * multiplyBy);
        getY().set(valueY * multiplyBy);
    }

    public void set(Position position) {
        set(position.getX(), position.getY());
    }

    public Position get() {
        return new Position((int) (x.get()/multiplyBy), (int) (y.get()/multiplyBy));
    }

    public ObservablePosition(double multiplyBy, int x, int y) {
        this.multiplyBy = multiplyBy;

        this.x.set(x);
        this.y.set(y);
    }
}
