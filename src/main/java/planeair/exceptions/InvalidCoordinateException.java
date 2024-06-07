package planeair.exceptions;

/**
 * This exception is threw when an invalid format of Coordinate is found in the source file
 * 
 * @auhor Luc le Manifik
 */
public class InvalidCoordinateException extends Exception {
    
    public InvalidCoordinateException() {
        super("Wrong Coordinate format. Sori. Goofy me.");
    }
}
