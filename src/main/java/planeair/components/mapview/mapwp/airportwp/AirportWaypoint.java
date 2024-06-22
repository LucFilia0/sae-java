package planeair.components.mapview.mapwp.airportwp;

//#region IMPORTS
import java.io.File;

import org.jxmapviewer.viewer.GeoPosition;

import planeair.util.Airport;
//#endregion

/**
 * This class is the MapWaypoint which is used to represent Airports on the Map.
 * The Airport class's Waypoints can be "active", in red, or "inactive", in gray.
 * 
 * The active/inactive visual is made by children classes :
 * {@link planeair.components.mapview.mapwp.airportwp.ActiveAirportWaypoint ActiveAirportWaypoint} and {@link planeair.components.mapview.mapwp.airportwp.InactiveAirportWaypoint InactiveAirportWaypoint}.
 * AirportWaypoint extends {@link planeair.components.mapview.mapwp.MapWaypoint MapWaypoint}
 * 
 * @author Luc le Manifik
 */
public abstract class AirportWaypoint extends planeair.components.mapview.mapwp.MapWaypoint {

	//#region ATTRIBUTES
	
	/**
	 * The Airport which is represented by the AirportWaypoint.
	 * Used to get the informations of the clicked MapWaypoint
	 */
	private Airport airport;

	//#endregion

    //#region CONSTRUCTORS

    /**
     * Creates a new AirportWaypoint.
	 * AirportWaypoints can be represented by two different visuals : The active ones (red), and the inactive ones (gray)
     * 
     * @param iconFile ({@link java.io.File File}) - The icon's File, which is used to prompt the visual
	 * @param airport ({@link planeair.util.Airport Airport}) - The Airport which is represented by the AirportWaypoint
     * @param geoPosition ({@link org.jxmapviewer.viewer.GeoPosition GeoPosition}) - The position of the Airport.
     * 
     * @author Luc le Manifik
     */
    AirportWaypoint(File iconFile, Airport airport, GeoPosition geoPosition) {
        super(iconFile, geoPosition, 0); // Degree = 0, because Airports are not spinning, bro...
		this.airport = airport;
		this.airport.setWaypoint(this);
    }

	//#endregion
	
	//#region GETTERS

	/**
	 * Returns the Airport represented by the AirportWaypoint
	 * 
	 * @return ({@link planeair.util.Airport Airport}) - The Airport which is represented by the AirportWaypoint
	 */
	public Airport getAirport() {
		return this.airport;
	}

	//#endregion

	//#region SETTERS

	/**
	 * Sets the Airport which is represented by the AirportWaypoint.
	 * Only if it's not null
	 */
	public void setAirport(Airport airport) {
		if(airport != null) {
			this.airport = airport;
		}
	}
	
	//#endregion

	//#region TOSTRING

	/**
	 * Returns the toString of the represented Airport
	 */
	public String toString() {
		return this.airport.toString();
	}

	//#endregion
}
