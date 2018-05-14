package pl.edu.wat.wcy.isi.pz.battleship.exceptions;

public class NoPropertyException extends RuntimeException {

    public NoPropertyException(String key) {
        super("Couldn't find property for key: " + key);
    }
}
