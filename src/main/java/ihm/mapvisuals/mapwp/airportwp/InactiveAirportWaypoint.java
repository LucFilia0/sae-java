package ihm.mapvisuals.mapwp.airportwp;

//-- Import Java

import java.io.File;

//-- Import JxMapViewer

import org.jxmapviewer.viewer.GeoPosition;

/**
 * This class is a Waypoint on the {@link ihm.Map} which represents an inactive Airport.
 * Extends the {@link ihm.mapvisuals.mapwp.airportwp.AirportWaypoint AirportWaypoint} abstract class.
 * 
 * @author Luc le Manifik
 */
public class InactiveAirportWaypoint extends AirportWaypoint {
    
    //-- InactiveAirportWaypoint Static Variables

    /**
     * The file which contains the icon of the inactive Airports ({@link java.io.File})
     */
    public static final File INACTIVE_AIRPORT_WAYPOINT_ICON_FILE = new File("sprint/inactive_airport.png");

    //-- InactiveAirportWaypoint Constructor

    /**
     * The InactiveAirportWaypoint class's constructor. Creates a new InactiveAirportWaypoint.
     * 
     * @param name (String) - The name of the Airport
     * @param position ({@link org.jxmapviewer.viewer.GeoPosition}) - The osition of the Airport
     * 
     * @author Luc le Manifik
     */
    public InactiveAirportWaypoint(String name, GeoPosition position) {
        super(InactiveAirportWaypoint.INACTIVE_AIRPORT_WAYPOINT_ICON_FILE, name, position);
    }
}
