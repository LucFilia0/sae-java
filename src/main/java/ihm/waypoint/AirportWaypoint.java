package ihm.waypoint;

//-- Import Java

import java.io.File;

//-- Import JxMapViewer

import org.jxmapviewer.viewer.GeoPosition;

/**
 * This class is the MapWaypoint which is used to represent Airports on the Map.
 * 
 * @author Luc le Manifik
 */
public class AirportWaypoint extends MapWaypoint {
    
    //-- AirportWaypoint Attributes

    /**
     * The file which contains the icon of the airports ({@link java.io.File})
     */
    public static final File AIRPORT_WAYPOINT_ICON_FILE = new File("sprint/red-airport.png");

    /**
     * The AirportWaypoint class constructor. Creates a new AirportWaypoint.
     * 
     * @param name (String) - The name of the Airport
     * @param geoPosition ({@link org.jxmapviewer.viewer.GeoPosition}) - The position of the Airport.
     * 
     * @author Luc le Manifik
     */
    public AirportWaypoint(String name, GeoPosition geoPosition) {
        super(AirportWaypoint.AIRPORT_WAYPOINT_ICON_FILE, name, geoPosition);
    }
}
