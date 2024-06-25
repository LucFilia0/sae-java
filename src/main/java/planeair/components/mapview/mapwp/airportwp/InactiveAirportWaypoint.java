package planeair.components.mapview.mapwp.airportwp;

//#region IMPORTS
import java.io.File;

import org.jxmapviewer.viewer.GeoPosition;

import planeair.util.Airport;
//#endregion

/**
 * This class is a Waypoint on the {@link planeair.components.mapview.Map} 
 * which represents an inactive Airport.
 * Extends the {@link AirportWaypoint AirportWaypoint} abstract class.
 * 
 * @author Luc le Manifik
 */
public class InactiveAirportWaypoint extends AirportWaypoint {
    
    //-- InactiveAirportWaypoint Static Variables

    /**
     * The file which contains the icon of the inactive Airports ({@link java.io.File})
     */
    public static final File INACTIVE_AIRPORT_WAYPOINT_ICON_FILE = new File("./icons/waypoints/inactive_airport.png");

    //-- InactiveAirportWaypoint Constructor

    /**
     * The InactiveAirportWaypoint class's constructor. Creates a new InactiveAirportWaypoint.
     * 
     * @param airport the Airport linked to this waypoint
     * @param position ({@link org.jxmapviewer.viewer.GeoPosition}) - The position of the Airport
     * 
     * @author Luc le Manifik
     */
    public InactiveAirportWaypoint(Airport airport, GeoPosition position) {
        super(InactiveAirportWaypoint.INACTIVE_AIRPORT_WAYPOINT_ICON_FILE, airport, position);
    }

    @Override
    public String toStringFirst() {
        throw new UnsupportedOperationException("Unimplemented method 'toStringFirst'");
    }

    @Override
    public String toStringSecond() {
        throw new UnsupportedOperationException("Unimplemented method 'toStringSecond'");
    }
}
