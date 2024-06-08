package planeair.components.mapview.mapwp.airportwp;

//-- Import Java

import java.io.File;

//-- Import JxMapViewer

import org.jxmapviewer.viewer.GeoPosition;

import planeair.util.Airport;

/**
 * This class is the MapWaypoint which is used to represent Airports on the Map.
 * The Airport class's Waypoints can be "active", in red, or "inactive", in gray.
 * The active/inactive visual is made by children classes :
 * {@link ihm.mapvisuals.mapwp.airportwp.ActiveAirportWaypoint ActiveAirportWaypoint} and {@link ihm.mapvisuals.mapwp.airportwp.InactiveAirportWaypoint InactiveAirportWaypoint}.
 * Extends {@link ihm.mapvisuals.mapwp.MapWaypoint MapWaypoint}
 * 
 * @author Luc le Manifik
 */
public abstract class AirportWaypoint extends planeair.components.mapview.mapwp.MapWaypoint {

	//-- Airport Waypoint Attributes
	
	private Airport airport;

    //-- AirportWaypoint Consructor

    /**
     * The AirportWaypoint class's constructor. Creates a new AirportWaypoint.
     * 
     * @param iconFile ({@link java.io.File}) - The icon's File
     * @param name (String) - The name of the Airport
     * @param geoPosition ({@link org.jxmapviewer.viewer.GeoPosition}) - The position of the Airport.
     * 
     * @author Luc le Manifik
     */
    AirportWaypoint(File iconFile, Airport airport, GeoPosition geoPosition) {
        super(iconFile, geoPosition, 0);
		this.airport = airport;
    }

	//-- AirportWaypoint Getters
	
	/**
	 * Returns the Airport represented by the AirportWaypoint
	 */
	public Airport getAirport() {
		return this.airport;
	}

	/**
	 * Sets the Aiprort which is represented by the AirportWaypoint
	 */
	public void setAirport(Airport airport) {
		if(airport != null) {
			this.airport = airport;
		}
	}

	/**
	 * Returns the prompt of the infos Airports
	 */
	public String toString() {
		return this.airport.toString();
	}

}
