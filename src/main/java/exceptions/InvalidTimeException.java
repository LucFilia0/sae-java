package exceptions;

import util.Time ;

public class InvalidTimeException extends Exception {
    
    public InvalidTimeException(Time time) {
        super(time.getHours() + "h" + time.getMinutes() + " est un temps invalide.") ;
    }
}
