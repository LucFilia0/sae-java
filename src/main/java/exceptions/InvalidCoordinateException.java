package exceptions;

import util.Coordinate ;

/**
 * This exception is throwed if the values which define the Coordinates are not correct.
 * 
 * @author Nathan LIEGEON - Modified : Luc le Manifik
 */
public class InvalidCoordinateException extends Exception {
    
    public InvalidCoordinateException(Coordinate co) {
        super(co.getDegree() + "° " + co.getMinutes() + "' " + co.getSeconds() + "'' " + co.getDirection() + " n'est pas une coordonnée valide") ;
    }
}
