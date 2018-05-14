package pl.edu.wat.wcy.isi.pz.battleship.game.components.ship;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PositionShip {
    List<DraggableShip> ships = new ArrayList<>();

    public static PositionShip[][] getArray(final int SIZE) {
        PositionShip[][] positionShips = new PositionShip[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                positionShips[i][j] = new PositionShip();
            }
        }
        return positionShips;
    }

    public static List<DraggableShip> conflictShips(PositionShip[][] positionShips) {

        List<DraggableShip> conflictShips = new ArrayList<>();

        for (PositionShip[] positionShip : positionShips) {
            for (PositionShip ship : positionShip) {
                if (ship.getShips().size() > 1) {
                    ship.getShips().forEach(s -> {
                        if(!conflictShips.contains(s)) {
                            conflictShips.add(s);
                        }
                    });
                }
            }
        }

        return conflictShips;
    }
}