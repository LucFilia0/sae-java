package exceptions;

/**
 * Error managing a wrong direction being entered in a {@link util.Longitude Longitude} object
 * 
 * @author Nathan LIEGEON
 */
public class InvalidLongitudeDirectionException extends Exception {
    
    public InvalidLongitudeDirectionException() {
        super("Les seules directions possibles sont 'E' et 'W'") ;
    }
}