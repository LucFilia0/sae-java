package planeair.graph.graphtype;

import planeair.graph.graphutil.Flight;

//-- Import Plane AIR

import planeair.util.NTime;

//-- Import GraphStream
import org.graphstream.graph.Node ;


/**
 * The FlightIntersectionGraph represents the collisions between Flights.
 * The nodes of the Graph are the Flights.
 * 
 * @implNote Uses the GraphStream attributes.
 * 
 * @author Luc le Manifik
 */
public class FlightsIntersectionGraph extends TestGraph {

    //-- FIG Attributes

    /**
     * The String identifier that represents the number of collisions in the FIG (int)
     */
    public static final String NB_COLLISIONS = "nbCollisions";

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
        this.setAttribute(FlightsIntersectionGraph.NB_COLLISIONS, 0); // -> The number of collisions in the FIG
    }

    //-- FIG toString()

    /**
     * toString() FIG's method.
     */
    public String toString() {
        return "-- Flights Intersection Graph\nIdentifier : " + super.id + "\nNumber of Flights : " + this.getNodeCount() + "\nNumber of collisions : " + this.getNbCollisions();
    }

    //-- FIG Getters

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

    //-- FIG Setters

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
    public void showFlightsAtATime(NTime givenTime) {
        int givenTimeInMinutes = givenTime.getValueInMinutes() ;
        for (Node node : this) {
            Flight flightNode = (Flight) node ;
            int departure = flightNode.getDepartureTime().getValueInMinutes() ;
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
