package exceptions;

import util.* ;

public class InvalidCoordinatesException extends Exception {
    
    public InvalidCoordinatesException(Coordinate co) {
        super(co.getDegree() + "° " + co.getMinutes() + "' " + co.getSeconds() + "'' n'est pas une coordonnée valide") ;
    }
}
