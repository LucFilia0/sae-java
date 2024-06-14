package planeair.components.mapview.mapwp.flightwp;

//-- Import Java

import java.io.File;

//-- Import JxMapViewer

import org.jxmapviewer.viewer.GeoPosition;

import planeair.components.mapview.mapwp.MapWaypoint;
import planeair.graph.graphutil.Flight;

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
    public static final File FLIGHT_WAYPOINT_ICON_FILE = new File("./icons/waypoints/plane.png");

    /**
     * The file which contains the icon of the flights ({@link java.io.File})
     */
    public static final File FLIGHT_CLICKED_WAYPOINT_ICON_FILE = new File("./icons/waypoints/planeClicked.png") ;

    //-- FlightWaypoint Attributes

    /**
     * The Flight which is represented by the FlightWaypoint
     */
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
        super(FlightWaypoint.FLIGHT_WAYPOINT_ICON_FILE, geoPosition, getFlightOrientation(flight));
        this.flight = flight;
        this.flight.setFlightWaypoint(this) ;
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

    /**
     * Returns the toString value of the represented Flight : Its informations and all...
     * 
     * @author Luc le Manifik
     */
    public String toString() {
        return this.flight.toString();
    }

    private static double getFlightOrientation(Flight flight) {

        double radian = 0.;
        int offset = 45;

        double latitudeDep = flight.getDepartureAirport().getCoordinate().getLatitude();
        double longitudeDep = flight.getDepartureAirport().getCoordinate().getLongitude();

        double latitudeArr = flight.getArrivalAirport().getCoordinate().getLatitude();
        double longitudeArr = flight.getArrivalAirport().getCoordinate().getLongitude();

        // TRIGO

        double adj = longitudeArr - longitudeDep;
        double op = latitudeArr - latitudeDep;
        double hyp = Math.sqrt(Math.pow(adj, 2) + Math.pow(op, 2));

        double sin = op / hyp;
        double cos = adj / hyp;

        if(sin > 0) {
            radian = -(Math.acos(cos)); // DONT touch or you dead :angry_skull:
        }else {
            radian = -(Math.toRadians(360) - Math.acos(cos));
        }

        return radian + Math.toRadians(offset);
    }

    


}
