package planeair.util;

//#region IMPORTS

    //#region JAVA

    import java.util.ArrayList;
    import java.util.Iterator;

    //#endregion

    //#region GRAPHSTREAM

    import org.graphstream.graph.Node;

import planeair.components.mapview.mapwp.airportwp.AirportWaypoint;

//#endregion

    //#region PLANEAIR

    import planeair.graph.graphtype.FlightsIntersectionGraph;
    import planeair.graph.graphutil.Flight;

    //#endregion

//#endregion

/**
 * the Airport class represents the different airports which are imported (in this case, France's airports).
 * 
 * @author Luc le Manifik
 */
public class Airport {
    
    //#region ATTRIBUTES

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

    /**
     * The MapWaypoint which represents the Airport
     */
    private AirportWaypoint waypoint;

    //#endregion
    
    //#region CONSTRUCTORS

    /**
     * Creates a new Airport.
     * Airports have Flights, going in and out of them, and needs to be imported BEFORE the Flights.
     * 
     * @param name (String) - The name of the Airport.
     * @param location (String) - The location/city of the Airport.
     * @param coordinate ({@link planeair.util.Coordinate Coordinate}) - The Coordinate/location of the Airport. Extends {@link org.jxmapviewer.viewer.GeoPosition GeoPosition}
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

    //#endregion

    //#region TOSTRING

    /**
     * Returns the informations of the Airport in a correctly formated String.
     * 
     * @author Luc le Manifik
     */
    public String toString() {
        return "<html><h1>Aéroport</h1><strong>Nom :</strong> " + this.name + "<br><strong>Ville :</strong> " + this.location + "<br><strong>Coordonnées :</strong> " + this.coordinate + "</html>";
    }

    //#endregion

    //#region GETTERS

    /**
     * Gets the name of the Airport.
     * The unique which is used to identifiate the Airport
     * 
     * @return The name of the Airport
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the location of the Airport.
     * 
     * @return The name of the city in which the Airport is
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Gets the Coordinate of the Airport.
     * 
     * @return The {@link planeair.util.Coordinate Coordinate} of the Airport
     * 
     * @author Luc le Maifik
     */
    public Coordinate getCoordinate() {
        return this.coordinate;
    }
    
    /**
     * Returns all the fligts coming to and going from this airport
     * 
     * @return
     * 
     */
    public ArrayList<Flight> getFlightList() {
        return this.flightList ;
    }

    /**
     * 
     * @return
     */
    public AirportWaypoint getWaypoint() {
        return this.waypoint;
    }

    //#endregion

    //#region SETTERS

    /**
     * Sets the name of the Airport.
     * 
     * @param name (String) - The new name of the Airport.
     * 
     * @throws NullPointerException Threw if the String passed in parameter is null.
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
     * Sets the location of the Airport.
     * 
     * @param location (String) - The new location of the Airport.
     * 
     * @throws NullPointerException Threw if the (String) passed in parameter is null.
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
     * Sets the longitude of the Airport.
     * 
     * @param coordinate ({@link planeair.util.Coordinate Coordinate}) - The new Coordinate of the Airport
     * 
     * @throws NullPointerException Threw if the {@link planeair.util.Coordinate Coordinate} passed  in parameter is null
     * 
     * @author Luc le Manifik
     */
    public void setCoordinate(Coordinate coordinate) throws NullPointerException {

        if(coordinate == null) {
            throw new NullPointerException();
        }
        this.coordinate = coordinate;
    }

    /**
     * 
     * @param waypoint
     */
    public void setWaypoint(AirportWaypoint waypoint) {
        this.waypoint = waypoint;
    }

    //#endregion

    //#region PUBLIC METHODS

    /**
     * This method returns if the Airport must be marked active (in red) on the Map, because one of the Flights
     * in the FIG is using this Airport.
     * 
     * @param fig ({@link graph.FlightsIntersectionGraph}) - The FIG, which contains the Flights to check
     * 
     * @return "True" if the Flight must be marked as an active one, else "false"
     * 
     * @author Luc le Manifik
     */
    public boolean mustBeActive(FlightsIntersectionGraph fig) {

        boolean mustBeActive = false;

        Iterator<Node> figIterator = fig.nodes().toList().iterator();
        Flight currentFlight = null;
        
        while(figIterator.hasNext() && !mustBeActive) {

            currentFlight = (Flight)figIterator.next();

            if(this.equals(currentFlight.getDepartureAirport()) || this.equals(currentFlight.getArrivalAirport())) {
                mustBeActive = true;
            }
        }

        return mustBeActive;
    }

    //#endregion
}
