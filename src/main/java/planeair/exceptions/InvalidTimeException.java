package planeair.exceptions;

import planeair.util.NTime ;

/**
 * Error managing a wrong time frame being entered in a {@link NTime.FlightTime FlightTime} object
 * 
 * @author Nathan LIEGEON
 */
public class InvalidTimeException extends Exception {
   
    /**
     * Displays the error explaining the time is invalid
     * @param time the time
     */
    public InvalidTimeException(NTime time) {
        super(time.getHour() + "h" + time.getMinute() + " est une heure invalide") ;
    }
}
