package exceptions;

import util.FlightTime ;

public class InvalidTimeException extends Exception {
    
    public InvalidTimeException(FlightTime time) {
        super(time.getHour() + "h" + time.getMinute() + " est un temps invalide.") ;
    }
}
