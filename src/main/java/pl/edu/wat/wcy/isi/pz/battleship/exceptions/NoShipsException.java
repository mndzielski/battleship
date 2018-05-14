package pl.edu.wat.wcy.isi.pz.battleship.exceptions;

public class NoShipsException extends RuntimeException {

    public NoShipsException() {
        super("No ships in xml file");
    }
}
