package exceptions;

import util.Time ;

public class InvalidTimeException extends Exception {
    
    public InvalidTimeException(Time time) {
        super(time.getHour() + "h" + time.getMinute() + " est un temps invalide.") ;
    }
}
