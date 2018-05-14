package pl.edu.wat.wcy.isi.pz.battleship.exceptions;

public class DifferentNumberOfShipsException extends RuntimeException {

    public DifferentNumberOfShipsException(int size1, int size2) {
        super("Different number of ships: " + size1 + " != " + size2);
    }
}
