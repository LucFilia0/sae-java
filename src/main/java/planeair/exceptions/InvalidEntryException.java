package planeair.exceptions;

/**
 * Exception threw when a wrong value is passed in a mehod. For example, a negative value, where you
 * only expect positive ones
 * 
 * @author Luc le Manifik
 */
public class InvalidEntryException extends Error {

    /**
     * Creates a new InvalidEntryException, threw when a wrong value is passed in a mehod. For example, a negative value, where you
     * only expect positive ones
     */
    public InvalidEntryException()
    {
        super("Invalid entry. The read value does not matches the requirement.");
    }
}
