package exceptions;

public class InvalidEntryException extends Error {
    public InvalidEntryException()
    {
        super("Invlid entry");
    }
}
