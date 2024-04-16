package exceptions;

import util.FlightTime ;

/**
 * Error managing a wrong time frame being entered in a {@link util.FlightTime FlightTime} object
 * 
 * @author Nathan LIEGEON
 */
public class InvalidTimeException extends Exception {
    
    public InvalidTimeException(FlightTime time) {
        super(time.getHour() + "h" + time.getMinute() + " est un temps invalide.") ;
    }
}
