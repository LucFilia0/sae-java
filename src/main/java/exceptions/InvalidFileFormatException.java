package exceptions;

public class InvalidFileFormatException extends Error {
    
    public InvalidFileFormatException() {
        super("Le fichier source est invalide, sori");
    }

    public InvalidFileFormatException(int line) {
        super("Le fichier source est invalide : ligne : " + line);
    }
}
