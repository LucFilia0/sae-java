package planeair.graph.graphtype;

import planeair.graph.graphutil.Flight;

//-- Import Plane AIR

import planeair.util.FlightTime;

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

}
