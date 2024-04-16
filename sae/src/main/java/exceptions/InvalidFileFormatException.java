package exceptions;

/**
 * Throwed if the file which is currently read contains a format error. Like a missing information, for example.
 */
public class InvalidFileFormatException extends Error {
    
    public InvalidFileFormatException() {
        super("Source file does not match the required format of data");
    }

    public InvalidFileFormatException(String comment) {
        super("Source file does not match the required format of data : " + comment);
    }
}
