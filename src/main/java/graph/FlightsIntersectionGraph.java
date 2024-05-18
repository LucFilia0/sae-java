package graph;

//-- Import GraphStream

import org.graphstream.graph.implementations.SingleGraph;


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
    private final String NB_FLIGHTS = "nbFlights";

    /**
     * The String identifier that represents the number of collisions in the FIG (int)
     */
    private final String NB_COLLISIONS = "nbCollisions";

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
        this.setAttribute(this.NB_FLIGHTS, 0); // -> The number of flights in the FIG
        this.setAttribute(this.NB_COLLISIONS, 0); // -> The number of collisions in the FIG
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
        return (int)this.getAttribute(this.NB_FLIGHTS);
    }

    /**
     * Get the number of collisions in the FIG.
     * 
     * @return (int) - The number of collisions in the FIG.
     * 
     * @author Luc le Manifik
     */
    public int getNbCollisions()  {
        return (int)this.getAttribute(this.NB_COLLISIONS);
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
        this.setAttribute(this.NB_FLIGHTS, nbFlights);
    }

    /**
     * Set the number of collisions in the FIG.
     * 
     * @param nbCollisions (int) - The new number of collisions in the FIG.
     * 
     * @author Luc le Manifik
     */
    public void setNbCollisions(int nbCollisions) {
        this.setAttribute(this.NB_COLLISIONS, nbCollisions);
    }
}
