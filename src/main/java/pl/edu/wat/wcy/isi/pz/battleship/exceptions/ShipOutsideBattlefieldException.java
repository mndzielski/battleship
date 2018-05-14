package pl.edu.wat.wcy.isi.pz.battleship.exceptions;

public class ShipOutsideBattlefieldException extends RuntimeException {

    public ShipOutsideBattlefieldException(int x, int y) {
        super("Ship is outside battlefield: position (" + x + " " + y + ")");
    }
}
