package planeair.util;

//#region IMPORTS

    //#region PLANEAIR

    import planeair.exceptions.InvalidCoordinateException;

    //#endregion

//#endregion

/**
 * This class extends {@link org.jxmapviewer.viewer.GeoPosition GeoPosition}, from JxMapViewer, but takes care of the direction char ('N', 'E'...) that are
 * stored in the source file. The final values of the Coordinate are stored as doubles.
 * 
 * @author Luc le Manifik
 */
public class Coordinate extends org.jxmapviewer.viewer.GeoPosition {

    //#region ATTRIBUTES

    // Latitude

    /**
     * Latitude's degree
     */
    private int latDegree;

    /**
     * Latitude's minutes
     */
    private int latMinutes;

    /**
     * Latitude's seconds
     */
    private int latSeconds;

    /**
     * Latitude's direction
     */
    private char latDirection;

    // Longitude

    /**
     * Longitude's degree
     */
    private int lonDegree;

    /**
     * Longitude's minutes
     */
    private int lonMinutes;

    /**
     * Longitude's seconds
     */
    private int lonSeconds;

    /**
     * Longitude's direction
     */
    private char lonDirection;

    //#endregion
    
    //#region CONSTRUCTORS

    /**
     * Creates a new Coordinate. Extends {@link org.jxmapviewer.viewer.GeoPosition GeoPosition}, so it can be used with JXMapViewer
     * and contains both Latitude and Longitude values.
     * 
     * @param latDegree The latitude degree
     * @param latMinutes The latitude minutes
     * @param latSeconds The latitude seconds
     * @param latDirection The latitude direction ('N' or 'S')
     * 
     * @param lonDegree The longitude degree
     * @param lonMinutes The longitude minutes
     * @param lonSeconds The longitude seconds
     * @param lonDirection The longitude direction ('E' or 'O')
     * 
     * @throws InvalidCoordinateException Threw if the Coordinate read in the source File is not correct
     * 
     * @author Luc le Manifik
     */
    public Coordinate(int latDegree, int latMinutes, int latSeconds, char latDirection, 
    int lonDegree, int lonMinutes, int lonSeconds, char lonDirection) throws InvalidCoordinateException {

        super(getDecimalCoordinate(latDegree, latMinutes, latSeconds, latDirection, 0), getDecimalCoordinate(lonDegree, lonMinutes, lonSeconds, lonDirection, 1));

        if((latDirection != 'N' && latDirection != 'S') || (lonDirection != 'E' && lonDirection != 'O')) {
            throw new InvalidCoordinateException();
        }

        // Latitude
        this.latDegree = latDegree;
        this.latMinutes = latMinutes;
        this.latSeconds = latSeconds;
        this.latDirection = latDirection;

        // Longitude
        this.lonDegree = lonDegree;
        this.lonMinutes = lonMinutes;
        this.lonSeconds = lonSeconds;
        this.lonDirection = lonDirection;
    }

    //#endregion

    //#region TOSTRING

    /**
     * Returns the Coordinate in a great format (Ex: 12° 13' 14'' | 12° 12' 12'')
     * 
     * @return The String which represents the Coordinate
     * 
     * @author Luc le Manifik
     */
    @Override
    public String toString() {

        return this.latDegree + "° " + this.latMinutes + "' " + this.latSeconds + "'' " + this.latDirection
         + " | " +
         this.lonDegree + "° " + this.lonMinutes + "' " + this.lonSeconds + "'' " + this.lonDirection;
    }

    //#endregion

    //#region PRIVATE FUNCTIONS

    /**
     * Creates a new Coordinate, but putting a negative value if there is 'S' or 'O'
     * 
     * @param coordDegree The coordinate degree
     * @param coordMinutes The coordinate minutes
     * @param coordSeconds The coordinate seconds
     * @param coordDirection The coordinate direction
     * @param type 0 to import a latitude ('N' or 'S') and 1 to import a longitude ('E' or 'O')
     * 
     * @return The decimal value of the coordinate axis (positive OR negative)
     * 
     * @author Luc le Manifik
     */
    private static double getDecimalCoordinate(int coordDegree, int coordMinutes, int coordSeconds, int coordDirection, int type) {
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
                }
                break;
            default :
                System.err.println("Programmer's error : check Coordinate.java, line 35 :)");
        }

        return decimalCoordinate;
    }

    //#endregion
}
