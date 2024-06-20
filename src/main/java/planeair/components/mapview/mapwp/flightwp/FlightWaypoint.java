package planeair.components.mapview.mapwp.flightwp;

//#region IMPORTS
import java.io.File;

import org.jxmapviewer.viewer.GeoPosition;

import planeair.components.mapview.mapwp.MapWaypoint;
import planeair.graph.graphutil.Flight;
import planeair.graph.graphutil.PanelCreator;
//#endregion

/**
 * This class is the MapWaypoint which is used to represent Flights on the Map.
 * It is represented by little planes, flying across the Map.
 * 
 * @author Luc le Manifik
 */
public class FlightWaypoint extends MapWaypoint {
    
    //#region STATIC VARIABLES

    /**
     * The file which contains the icon of the flights ({@link java.io.File File})
     */
    public static final File FLIGHT_WAYPOINT_ICON_FILE = new File("./icons/waypoints/plane.png");

    //#endregion

    //#region ATTRIBUTES

    /**
     * The Flight which is represented by the FlightWaypoint.
     * Used to get the informations of the clicked Flight
     */
    private Flight flight;

    //#endregion

    //#region CONSTRUCTORS

    /**
     * Creates a new FlightWaypoint.
     * FlightWaypoints are represented by planes, flying across the Map.
     * 
     * @param flight ({@link planeair.graph.graphutil.Flight Flight}) - The Flight which is represented by the FlightWaypoint
     * @param geoPosition ({@link org.jxmapviewer.viewer.GeoPosition GeoPosition}) - The (current) position of the Flight
     * 
     * @author Luc le Manifik
     */
    public FlightWaypoint(Flight flight, GeoPosition geoPosition) {
        super(FlightWaypoint.FLIGHT_WAYPOINT_ICON_FILE, geoPosition, getFlightOrientation(flight));
        this.flight = flight;
        this.flight.setFlightWaypoint(this) ;
    }

    //#endregion

    //#region GETTERS
	
	/**
	 * Returns the Flight represented by the FlightWaypoint
     * 
     * @return ({@link planeair.graph.graphutil.Flight Flight}) - The Flight represented by the FlightWaypoint 
	 */
	public Flight getFlight() {
		return this.flight;
	}

    //#endregion

	//#region SETTERS
	
	/**
	 * Sets the new value of the flight represented by the FlightWaypoint
	 */
	public void setFlight(Flight flight) {
		if(flight != null) {
			this.flight = flight;
		}
	}

    //#endregion

    //#region PRIVATE FUNCTIONS

    /**
     * Returns the current orientation of the Flight, based on the departure and arrival Airports.
     * 
     * @param flight ({@link planeair.graph.graphutil.Flight Flight}) - The Flight we wish to represent, and from which we will calculate the orientation, based on the departure and arrival airports
     * 
     * @return (double) - The orientation, in radians, of the Flight
     * 
     * @author Luc le Manifik
     */
    private static double getFlightOrientation(Flight flight) {

        double radian = 0.;
        int offset = 45;

        // Gets the departure and arrival Airports coordinates
        double latitudeDep = flight.getDepartureAirport().getCoordinate().getLatitude();
        double longitudeDep = flight.getDepartureAirport().getCoordinate().getLongitude();

        double latitudeArr = flight.getArrivalAirport().getCoordinate().getLatitude();
        double longitudeArr = flight.getArrivalAirport().getCoordinate().getLongitude();

        // TRIGO 🤓

        double adj = longitudeArr - longitudeDep;
        double op = latitudeArr - latitudeDep;
        double hyp = Math.sqrt(Math.pow(adj, 2) + Math.pow(op, 2));

        double sin = op / hyp;
        double cos = adj / hyp;

        if(sin > 0) {
            radian = -(Math.acos(cos)); // DONT touch or you dead 🤬
        }else {
            radian = -(Math.toRadians(360) - Math.acos(cos));
        }

        return radian + Math.toRadians(offset);
    }

    //#endregion

    //#region TOSTRING

    /**
     * Returns the toString of the Represented Flight
     * 
     * @author Luc le Manifik
     */
    public String toString() {
        return this.flight.toString();
    }

    @Override
    public String toStringFirst() {
        return this.flight.toStringFirst();
    }

    @Override
    public String toStringSecond() {
        return this.flight.toStringSecond();
    }

    /**
     * Updates the style of this flight based on if its 
     * waypoint is selected or not
     * @param selected True if it is selected, else false
     */
    public void updateFlightStyle(boolean selected) {
        if (selected) {
            PanelCreator.setSelectedStyle(flight) ;
        }
        else {
            PanelCreator.removeSelectedStyle(flight) ;
        }
    }

    //#endregion
}
