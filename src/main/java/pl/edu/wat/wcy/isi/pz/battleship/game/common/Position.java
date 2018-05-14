package pl.edu.wat.wcy.isi.pz.battleship.game.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Position {
    private int x;
    private int y;

    public Position add(Position position) {
        return new Position(this.x + position.x, this.y + position.y);
    }

    public Position changeByOrientation(Orientation orientation) {
        if(orientation.equals(Orientation.TOP) || orientation.equals(Orientation.BOTTOM)) {
            return add(new Position(0,1));
        }

        if(orientation.equals(Orientation.RIGHT) || orientation.equals(Orientation.LEFT)) {
            return add(new Position(1,0));
        }

        return null;
    }
}
