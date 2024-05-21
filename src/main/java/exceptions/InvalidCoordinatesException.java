package exceptions;

import util.* ;

/**
 * Exception thrown when the fields in a {@link util.Coordinate Coordinate} child object
 */
public class InvalidCoordinatesException extends Exception {
    
    public InvalidCoordinatesException(Coordinate co) {
        super(co.getDegree() + "° " + co.getMinutes() + "' " + co.getSeconds() + "'' n'est pas une coordonnée valide") ;
    }
}
