package graph;

//-- Import Plane AIR

import util.FlightTime;

//-- Import GraphStream

import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.Node ;


/**
 * The FlightIntersectionGraph represents the collisions between Flights.
 * The nodes of the Graph are the Flights.
 * 
 * @implNote Uses the GraphStream attributes.
 * 
 * @author Luc le Manifik
 */
public class FlightsIntersectionGraph extends SingleGraph {

    //-- FIG Attributes

    /**
     * The String identifier that represents the number of Flights presents in the FIG (int)
     */
    public static final String NB_FLIGHTS = "nbFlights";

    /**
     * The String identifier that represents the number of collisions in the FIG (int)
     */
    public static final String NB_COLLISIONS = "nbCollisions";

    /**
     * The String identifier that represents the maximum number of Layers allowed.
     */
    public static final String KMAX = "kMax" ;

    //-- FIG Constructor

    /**
     * Constructor of the FlightsIntersectionGraph (FIG) class.
     * Creates a new FIG.
     * 
     * @param id (String) - The identifier of the FIG.
     * 
     * @author Luc le Manifik
     */
    public FlightsIntersectionGraph(String id) {
        super(id); // -> The identifier of the FIG, in the parent class (SingleGraph)
        this.setAttribute(FlightsIntersectionGraph.NB_FLIGHTS, 0); // -> The number of flights in the FIG
        this.setAttribute(FlightsIntersectionGraph.NB_COLLISIONS, 0); // -> The number of collisions in the FIG
        this.setAttribute(FlightsIntersectionGraph.KMAX, 0) ; // -> The maximum number of layers in the FIG
    }

    //-- FIG toString()

    /**
     * toString() FIG's method.
     */
    public String toString() {
        return "-- Flights Intersection Graph\nIdentifier : " + super.id + "\nNumber of Flights : " + this.getNbFlights() + "\nNumber of collisions : " + this.getNbCollisions();
    }

    //-- FIG Getters

    /**
     * Get the number of Flights in the FIG.
     * 
     * @return (int) - The number of Flights in the FIG.
     * 
     * @author Luc le Manifik
     */
    public int getNbFlights() {
        return (int)this.getAttribute(FlightsIntersectionGraph.NB_FLIGHTS);
    }

    /**
     * Get the number of collisions in the FIG.
     * 
     * @return (int) - The number of collisions in the FIG.
     * 
     * @author Luc le Manifik
     */
    public int getNbCollisions()  {
        return (int)this.getAttribute(FlightsIntersectionGraph.NB_COLLISIONS);
    }

    /**
     * Get the maximum number of layers in the FIG.
     * 
     * @return (int) - The maximum number of layers in the FIG.
     * 
     * @author Nathan LIEGEON
     */
    public int getKMax() {
        return (int)this.getAttribute(FlightsIntersectionGraph.KMAX) ;
    }

    //-- FIG Setters

    /**
     * Set the number of Flights in the FIG.
     * 
     * @param nbFlights (int) - The new number of Flights in the FIG.
     * 
     * @author Luc le Manifik
     */
    public void setNbFlights(int nbFlights) {
        this.setAttribute(FlightsIntersectionGraph.NB_FLIGHTS, nbFlights);
    }

    /**
     * Set the number of collisions in the FIG.
     * 
     * @param nbCollisions (int) - The new number of collisions in the FIG.
     * 
     * @author Luc le Manifik
     */
    public void setNbCollisions(int nbCollisions) {
        this.setAttribute(FlightsIntersectionGraph.NB_COLLISIONS, nbCollisions);
    }

    /**
     * Set the maximum numbers of layers in the FIG.
     * 
     * @param kMax (int) - The new maximum number of layers in the FIG.
     * 
     * @author Nathan LIEGEON
     */
    public void setKMax(int kMax) {
        this.setAttribute(FlightsIntersectionGraph.KMAX, kMax) ;
    }

    /**
     * Makes all flights visible
     */
    public void showAllFlights() {
        for (Node node : this) {
            node.removeAttribute("ui.hide") ;
            node.edges().forEach(edge -> edge.removeAttribute("ui.hide")) ;
        }
    }

    /**
     * Shows only the flights that are in the air at a given time
     * @param givenTime time used for the checks
     */
    public void showFlightsAtATime(FlightTime givenTime) {
        int givenTimeInMinutes = givenTime.getHourValueInMinutes() ;
        for (Node node : this) {
            Flight flightNode = (Flight) node ;
            int departure = flightNode.getDepartureTime().getHourValueInMinutes() ;
            int arrival = departure + flightNode.getFlightDuration() ;
            if (givenTimeInMinutes < departure || givenTimeInMinutes > arrival) {
                flightNode.setAttribute("ui.hide") ;
                flightNode.edges().forEach(edge -> edge.setAttribute("ui.hide")) ;
            }
        }
    }
    /**
     * Shows only the flights that are on the same layer
     * @param layer layer used for the checks
     */
    public void showSameLayerFlights(int layer) {
        this.showAllFlights() ;
        this.setAttribute("hidden") ;
        for (Node node : this) {
            Flight flightNode = (Flight)node ;
            if (flightNode.getLayer() != layer) {
                flightNode.setAttribute("ui.hide") ;
                flightNode.edges().forEach(edge -> edge.setAttribute("ui.hide")) ;
            }
        }
    }

}
