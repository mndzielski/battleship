package pl.edu.wat.wcy.isi.pz.battleship.game.components.battlefield;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.exceptions.ShipOutsideBattlefieldException;
import pl.edu.wat.wcy.isi.pz.battleship.game.common.Orientation;
import pl.edu.wat.wcy.isi.pz.battleship.game.common.Position;
import pl.edu.wat.wcy.isi.pz.battleship.game.components.ship.DraggableShip;
import pl.edu.wat.wcy.isi.pz.battleship.game.components.ship.PositionShip;
import pl.edu.wat.wcy.isi.pz.battleship.game.events.ChangedShipPositionEvent;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class DraggableBattlefield extends AbstractBattlefield {

    List<DraggableShip> ships;
    private BooleanProperty conflict = new SimpleBooleanProperty();

    public DraggableBattlefield(List<DraggableShip> ships) {
        super();
        this.ships = ships;
        getChildren().addAll(ships);

        checkConflicts();

        ships.forEach(s -> s.addEventHandler(ChangedShipPositionEvent.CHANGED_SHIP_POSITION, event -> {
            checkConflicts();
        }));
    }

    private void checkConflicts() {
        PositionShip[][] positionShips = PositionShip.getArray(size);

        getShips().forEach(s -> {
            Position p = s.getPosition().get();
            Orientation orientation = s.getOrientation();

            for (int i = 0; i < s.getLength(); i++) {
                if(p.getY() >= size || p.getX()>= size) throw new ShipOutsideBattlefieldException(p.getY(), p.getX());
                positionShips[p.getY()][p.getX()].getShips().add(s);
                p = p.changeByOrientation(orientation);
            }
        });

        List<DraggableShip> conflictShips = PositionShip.conflictShips(positionShips);

        getShips().forEach(s -> {
            if (conflictShips.contains(s)) {
                s.setConflictColor();
            } else {
                s.setNormalColor();
            }
        });

        this.conflict.setValue(!conflictShips.isEmpty());
    }
}
