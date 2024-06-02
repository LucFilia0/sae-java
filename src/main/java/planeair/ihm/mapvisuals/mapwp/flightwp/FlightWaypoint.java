package planeair.ihm.mapvisuals.mapwp.flightwp;

//-- Import Java

import java.io.File;

//-- Import JxMapViewer

import org.jxmapviewer.viewer.GeoPosition;

import planeair.ihm.mapvisuals.mapwp.MapWaypoint;

/**
 * This class is the MapWaypoint which is used to represent Flights on the Map.
 * 
 * @author Luc le Manifik
 */
public class FlightWaypoint extends MapWaypoint {
    
    //-- FlightWaypoint Attributes

    /**
     * The file which contains the icon of the flights ({@link java.io.File})
     */
    public static final File FLIGHT_WAYPOINT_ICON_FILE = new File("sprint/plane.png");

    //-- FlightWaypoint Constructor

    /**
     * The FlightWaypoint class constructor. Creates a new FlightWaypoint.
     * 
     * @param name (String) - The name of the Flight
     * @param geoPosition ({@link org.jxmapviewer.viewer.GeoPosition}) - The (current) position of the Flight.
     * 
     * @author Luc le Manifik
     */
    public FlightWaypoint(String name, GeoPosition geoPosition) {
        super(FlightWaypoint.FLIGHT_WAYPOINT_ICON_FILE, name, geoPosition);
    }
}
