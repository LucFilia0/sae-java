package exceptions;

/**
 * Error managing a wrong direction being entered in a {@link util.Latitude Latitude} object
 * 
 * @author Nathan LIEGEON
 */
public class InvalidLatitudeDirectionException extends Exception {
    
    public InvalidLatitudeDirectionException() {
        super("Les seules directions possibles sont 'N' et 'S'") ;
    }
}
