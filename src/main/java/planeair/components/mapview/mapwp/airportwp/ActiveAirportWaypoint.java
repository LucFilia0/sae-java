package planeair.components.mapview.mapwp.airportwp;

//-- Import Java

import java.io.File;

//-- Import JxMapViewer

import org.jxmapviewer.viewer.GeoPosition;

import planeair.util.Airport;

/**
 * This class is a Waypoint on the {@link ihm.Map} which represents an active Airport.
 * Extends the {@link ihm.mapvisuals.mapwp.airportwp.AirportWaypoint AirportWaypoint} class.
 * 
 * @author Luc le Manifik
 */
public class ActiveAirportWaypoint extends AirportWaypoint {
    
    //-- ActiveAirportWaypoint Static Variables

    /**
     * The file which contains the icon of the active Airports ({@link java.io.File})
     */
    public static final File ACTIVE_AIRPORT_WAYPOINT_ICON_FILE = new File("sprint/active_airport.png");

    //-- ActiveAirportWaypoint Constructor

    /**
     * The ActiveAirportWaypoint class's constructor. Creates a new ActiveAirportWaypoint.
     * 
     * @param name (String) - The name of the Airport
     * @param geoPosition ({@link org.jxmapviewer.viewer.GeoPosition}) - The position of the Airport.
     * 
     * @author Luc le Manifik
     */
    public ActiveAirportWaypoint(Airport airport, GeoPosition position) {
        super(ActiveAirportWaypoint.ACTIVE_AIRPORT_WAYPOINT_ICON_FILE, airport, position);
    }
}
