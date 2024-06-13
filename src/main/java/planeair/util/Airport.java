package planeair.util;

import java.util.ArrayList;

//-- Import java

import java.util.Iterator;

//-- Import GraphStream

import org.graphstream.graph.Node;

//-- Import PlaneAIR

import planeair.graph.graphtype.FlightsIntersectionGraph;
import planeair.graph.graphutil.Flight;

/**
 * Airport represents the different France's airports.
 * 
 * @author Luc le Manifik
 */
public class Airport {
    
    //-- Airport Attributes

    /**
     * The name of the Airport (String)
     */
    private String name;

    /**
     * The city where the Airort is (String)
     */
    private String location;

    /**
     * The GeoPosition of the Airport 
     */
    private Coordinate coordinate;

    /**
     * Contains all the flights arriving to or leaving from this airport
     */
    private ArrayList<Flight> flightList ;

    //-- Airport Constructor

    /**
     * Constructor of the Airport class.
     * Creates a new Airport.
     * 
     * @param name (String) - The name of the Airport.
     * @param location (String) - The location/city of the Airport.
     * @param longitude ({@link util.Longitude util.Longitude}) - The Longitude (extends {@link util.Coordinate util.Coordinate}) of the Airport.
     * @param latitude ({@link util.Latitude util.Latitude}) - The Latitude (extends {@link util.Coordinate util.Coordinate}) of the Airport.
     * 
     * @throws NullPointerException Threw if the Object passed in parameter is Null / not declared
     * 
     * @author Luc le Manifik
     */
    public Airport(String name, String location, Coordinate coordinate) throws NullPointerException {
        try {
            this.setName(name);
            this.setLocation(location);
            this.setCoordinate(coordinate);
            this.flightList = new ArrayList<>() ;
        }catch(NullPointerException e) {
            throw e;
        }
    }

    //-- Airport toString()

    /**
     * Returns the informations of the Airport in a (String).
     * 
     * @author Luc le Manifik
     */
    public String toString() {
        return "<html><strong>-- Airport :</strong> " + this.name + "<br><strong>Location :</strong> " + this.location + "<br><strong>Latitude :</strong> " + this.coordinate.getLatitude() + "<br><strong>Longitude :</strong> " + this.coordinate.getLongitude() + "</html>";
    }

    //-- Airport Getters

    /**
     * Get the name of the Airport.
     * @return name (String)
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the location of the Airport.
     * @return location (String)
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Returns the GeoPosition of the Airport.
     * 
     * @return ({@link org.jxmapviewer.viewer.GeoPosition}) - The GeoPosition of the Airport
     * 
     * @author Luc le Maifik
     */
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    public ArrayList<Flight> getFlightList() {
        return this.flightList ;
    }

    //-- Airport Setters

    /**
     * Set the name of the Airport.
     * 
     * @param name (String) - The new name of the Airport.
     * @throws NullPointerException Throwed if the (String) passed  in parameter is null.
     * 
     * @author Luc le Manifik
     */
    public void setName(String name) throws NullPointerException {
        if(name == null) {
            throw new NullPointerException();
        }
        this.name = name;
    }

    /**
     * Set the location of the Airport.
     * 
     * @param location (String) - The new location of the Airport.
     * @throws NullPointerException Throwed if the (String) passed  in parameter is null.
     * 
     * @author Luc le Manifik
     */
    public void setLocation(String location) throws NullPointerException {
        if(location == null) {
            throw new NullPointerException();
        }
        this.location = location;
    }

    /**
     * Set the longitude of the Airport.
     * 
     * @param longitude ({@link util.Longitude util.Longitude}) - The new Longitude of the Airport.
     * @throws NullPointerException Throwed if the ({@link util.Longitude util.Longitude}) passed  in parameter is null.
     * 
     * @author Luc le Manifik
     */
    public void setCoordinate(Coordinate coordinate) throws NullPointerException {
        if(coordinate == null) {
            throw new NullPointerException();
        }
        this.coordinate = coordinate;
    }

    //-- Airport Methods

    /**
     * This method returns if the Airport must be marked active (in red) on the Map, because one of the Flights
     * in the FIG is using this Airport.
     * 
     * @param fig ({@link graph.FlightsIntersectionGraph}) - The FIG, which contains the Flights to check
     *
     * @return (boolean) - True if the Flight must be marked as an active one
     * 
     * @author Luc le Manifik
     */
    public boolean mustBeActive(FlightsIntersectionGraph fig) {

        boolean sort = false;

        Iterator<Node> figIterator = fig.nodes().toList().iterator();
        Flight currentFlight = null;
        
        while(figIterator.hasNext() && !sort) {

            currentFlight = (Flight)figIterator.next();

            if(this.equals(currentFlight.getDepartureAirport()) || this.equals(currentFlight.getArrivalAirport())) {
                sort = true;
            }
        }

        return sort;
    }
}
