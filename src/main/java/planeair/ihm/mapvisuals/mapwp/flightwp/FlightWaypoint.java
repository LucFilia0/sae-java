package planeair.ihm.mapvisuals.mapwp.flightwp;

//-- Import Java

import java.io.File;

//-- Import JxMapViewer

import org.jxmapviewer.viewer.GeoPosition;

import planeair.ihm.mapvisuals.mapwp.MapWaypoint;
import planeair.graph.Flight;

/**
 * This class is the MapWaypoint which is used to represent Flights on the Map.
 * 
 * @author Luc le Manifik
 */
public class FlightWaypoint extends MapWaypoint {
    
    //-- FlightWaypoint Constants

    /**
     * The file which contains the icon of the flights ({@link java.io.File})
     */
    public static final File FLIGHT_WAYPOINT_ICON_FILE = new File("sprint/plane.png");

    //-- FlightWaypoint Attributes

    private Flight flight;

    //-- FlightWaypoint Constructor

    /**
     * The FlightWaypoint class constructor. Creates a new FlightWaypoint.
     * 
     * @param name (String) - The name of the Flight
     * @param geoPosition ({@link org.jxmapviewer.viewer.GeoPosition}) - The (current) position of the Flight.
     * 
     * @author Luc le Manifik
     */
    public FlightWaypoint(Flight flight, GeoPosition geoPosition) {
        super(FlightWaypoint.FLIGHT_WAYPOINT_ICON_FILE, geoPosition);
        this.flight = flight;
    }

	//-- FlightWaypoint Getters
	
	/**
	 * Returns the Flight represented by the FlightWaypoint
	 *
	 */
	public Flight getFlight() {
		return this.flight;
	}

	//-- FlightWaypoint Setters
	
	/**
	 * Sets the new value of the flight represented by the FlightWaypoint
	 */
	public void setFlight(Flight flight) {
		if(flight != null) {
			this.flight = flight;
		}
	}

    public String toString() {
        return this.flight.toString();
    }
}
