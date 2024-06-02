package planeair.exceptions;

/**
 * This exception was meant to be thrown when the object searched  by the method getAttribute(key : String) returns "null".
 * 
 * @author Luc le Manifik
 */
public class ObjectNotFoundException extends Exception {

    /**
     * Creates a new ObjectNotFoundException.
     * Meant to be used when the method getAttribute(key : String) from GraphStream returns "null".
     * Means that the key is invalid, or that the object does not exist.
     * 
     * @author Luc le Manifik.
     */
    public ObjectNotFoundException() {
        super("The object can't be reached. Maybe he doesn't exist.");
    }
}
