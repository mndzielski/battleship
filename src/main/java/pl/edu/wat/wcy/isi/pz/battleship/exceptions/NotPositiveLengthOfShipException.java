package pl.edu.wat.wcy.isi.pz.battleship.exceptions;

/**
 * Created by Marcin Niedzielski.
 * Wojskowa Akademia Techniczna im. Jarosława Dąbrowskiego, Warszawa 2017r.
 */
public class NotPositiveLengthOfShipException extends RuntimeException {

    public NotPositiveLengthOfShipException(int length) {
        super(length + " is not positive");
    }
}
