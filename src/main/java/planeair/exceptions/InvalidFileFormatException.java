package planeair.exceptions;

/**
 * Threw if the File which is currently read contains a format error. Like a missing information, for example.
 * You can indicate the line on which the error occurs if you want.
 * 
 * This Exception is often the only one threw by the importing functions, because it can 'catch' the 
 * error messages of the other errors, and then precisely indicate on which line is the error (check second constructor)
 * 
 * @author Luc le Manifik
 */
public class InvalidFileFormatException extends Error {
    
    /**
     * Creates a new InvalidFileFormatException.
     * Threw if the File which is currently read contains a format error. Like a missing information, for example.
     * 
     * Prompts a default message
     */
    public InvalidFileFormatException() {
        super("Fichier source corrompu");
    }

    /**
     * Creates a new InvalidFileFormatException.
     * Threw if the File which is currently read contains a format error. Like a missing information, for example.
     * 
     * @param currentLine The line on which the error occured
     * @param comment The comment you want to add, to describe the error
     */
    public InvalidFileFormatException(int currentLine, String comment) {
        super("Fichier source corrompu : Ligne " + currentLine + " : " + comment);
    }
}
