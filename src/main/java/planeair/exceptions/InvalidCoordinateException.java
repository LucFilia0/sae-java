package planeair.exceptions;

/**
 * This exception is threw when an invalid format of Coordinate is found in the source file
 * 
 * @auhor Luc le Manifik
 */
public class InvalidCoordinateException extends Exception {
    
    /**
     * Creates a new Coordinate exception, threw when the Coordinate read by from
     * source File is not correct
     */
    public InvalidCoordinateException() {
        super("Wrong Coordinate format. Sori. Goofy me.");
    }
}
