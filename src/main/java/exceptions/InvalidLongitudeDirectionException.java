package exceptions;

public class InvalidLongitudeDirectionException extends Exception {
    
    public InvalidLongitudeDirectionException() {
        super("Les seules directions possibles sont 'E' et 'W'") ;
    }
}