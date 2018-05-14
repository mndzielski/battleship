package pl.edu.wat.wcy.isi.pz.battleship.game.components.battlefield.ships;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.game.FieldStateEnum;
import pl.edu.wat.wcy.isi.pz.battleship.game.StackPaneField;
import pl.edu.wat.wcy.isi.pz.battleship.game.common.Orientation;
import pl.edu.wat.wcy.isi.pz.battleship.game.common.Position;
import pl.edu.wat.wcy.isi.pz.battleship.game.components.ship.BaseShip;
import pl.edu.wat.wcy.isi.pz.battleship.game.components.ship.DraggableShip;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShipFields extends BaseShip {

    private List<StackPaneField> fields = new ArrayList<>();

    private ShipFields(BaseShip ship, List<StackPaneField> fields) {
        super(ship.getPosition().get(), ship.getOrientation(), ship.getLength());
        this.fields = fields;

        getFields().forEach(f -> f.setState(FieldStateEnum.SHIP_PART));
    }

    public static <T extends BaseShip> List<ShipFields> getShips(List<T> ships, StackPaneField[][] stackPaneFields) {
        List<ShipFields> shipFields = new ArrayList<>();

        ships.forEach(ship -> {
            Orientation orientation = ship.getOrientation();
            Position p = ship.getPosition().get();
            List<StackPaneField> fields = new ArrayList<>();

            for (int i = 0; i < ship.getLength(); i++) {
                fields.add(stackPaneFields[p.getX()][p.getY()]);
                p = p.changeByOrientation(orientation);
            }

            shipFields.add(new ShipFields(ship, fields));
        });

        return shipFields;
    }
}
