package planeair.components.mapview.mapwp.airportwp;

//#region IMPORTS
import java.io.File;

import org.jxmapviewer.viewer.GeoPosition;

import planeair.util.Airport;
//#endregion

/**
 * This class is a Waypoint on the {@link planeair.components.mapview.Map Map} which represents an active Airport (in red).
 * Extends the {@link planeair.components.mapview.mapwp.airportwp.AirportWaypoint AirportWaypoint} class.
 * 
 * @author Luc le Manifik
 */
public class ActiveAirportWaypoint extends AirportWaypoint {
    
    //#region STATIC VARIABLES

    /**
     * The file which contains the icon of the active Airports ({@link java.io.File File})
     */
    public static final File ACTIVE_AIRPORT_WAYPOINT_ICON_FILE = new File("./icons/waypoints/active_airport.png");

    //#endregion

    //#region CONSTRUCTOR

    /**
     * Creates a new ActiveAirportWaypoint.
     * ActiveAirports are represented in red on the Map
     * 
     * @param airport ({@link planeair.util.Airport Airport}) - The Airport which is represented by the ActiveAirportWaypoint
     * @param geoPosition ({@link org.jxmapviewer.viewer.GeoPosition GeoPosition}) - The position of the Airport
     * 
     * @author Luc le Manifik
     */
    public ActiveAirportWaypoint(Airport airport, GeoPosition position) {
        super(ActiveAirportWaypoint.ACTIVE_AIRPORT_WAYPOINT_ICON_FILE, airport, position);
    }

    @Override
    public String toStringFirst() {
        throw new UnsupportedOperationException("Unimplemented method 'toStringFirst'");
    }

    @Override
    public String toStringSecond() {
        throw new UnsupportedOperationException("Unimplemented method 'toStringSecond'");
    }

    //#endregion
}
