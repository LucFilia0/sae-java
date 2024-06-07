package planeair.util;

// Import PlaneAIR
import planeair.exceptions.InvalidCoordinateException;

/**
 * This class extends {@link org.jxmapviewer.viewer.GeoPosition GeoPosition} but takes care of the direction char ('N', 'E'...) that are
 * stored in the source file.
 * 
 * @author Luc le Manifik
 */
public class Coordinate extends org.jxmapviewer.viewer.GeoPosition {
    
    public Coordinate(int latDegree, int latMinutes, int latSeconds, char latDirection, 
    int lonDegree, int lonMinutes, int lonSeconds, char lonDirection) throws InvalidCoordinateException {

        super(getDecimalCoordinate(latDegree, latMinutes, latSeconds, latDirection, 0), getDecimalCoordinate(lonDegree, lonMinutes, lonSeconds, lonDirection, 1));
    }

    private static double getDecimalCoordinate(int coordDegree, int coordMinutes, int coordSeconds, int coordDirection, int type) throws InvalidCoordinateException {
        double decimalCoordinate = coordDegree + (coordMinutes + coordSeconds/ 60.0)/60.0;

        switch(type) {
            case 0 : 
                switch(coordDirection) {
                    case 'N' :
                        break;
                    case 'S' :
                        decimalCoordinate *= -1;
                        break;
                    default :
                        throw new InvalidCoordinateException();
                }
                break;
            case 1 : 
                switch(coordDirection) {
                    case 'E' :
                        break;
                    case 'O' :
                        decimalCoordinate *= -1;
                        break;
                    default :
                        throw new InvalidCoordinateException();
                }
                break;
            default :
                System.err.println("Programmer's error : check Coordinate.java, line 35 :)");
        }

        return decimalCoordinate;
    }
}
