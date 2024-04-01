package exceptions;

public class InvalidFileFormatException extends Error {
    
    public InvalidFileFormatException() {
        super("Le fichier txt comporte des erreurs");
    }
}
