package exceptions;

public class InvalidLatitudeDirectionException extends Exception {
    
    public InvalidLatitudeDirectionException() {
        super("Les seules directions possibles sont 'N' et 'S'") ;
    }
}
