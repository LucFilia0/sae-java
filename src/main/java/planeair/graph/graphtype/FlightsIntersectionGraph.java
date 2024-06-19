package planeair.graph.graphtype;

//#region IMPORTS

    //#region EXCEPTIONS

    import planeair.exceptions.InvalidEntryException;

    //#endregion

//#endregion

/**
 * The FlightIntersectionGraph (aka. FIG) represents the collisions between Flights.
 * The nodes of the Graph are the Flights. Nodes (Flights) of the same color are placed
 * at the same altitude.
 * 
 * An edge between two Nodes(Flights) means that, if these Flights were on the same layer(altitude),
 * then they will collide.
 *  
 * To avoid that, coloring algorithms are used to spread Flights on different altitudes.
 * 
 * @implNote Uses the GraphStream attributes.
 * 
 * @author Luc le Manifik
 */
public class FlightsIntersectionGraph extends GraphSAE {

    //#region CONSTRUCTORS

    /**
     * Creates a new FlightsIntersectionGraph.
     * The Nodes of a FIG are Flights, and their color represents their altitude.
     * Flights of the same color are placed on the same altitude.
     * 
     * An edge between two Nodes(Flights) means that, if these Flights were on the same layer(altitude),
     * then they will collide.
     * 
     * To avoid that, coloring algorithms are used to spread Flights on different altitudes.
     * 
     * @param id The identifier of the FIG
     * 
     * @author Luc le Manifik
     */
    public FlightsIntersectionGraph(String id) {
        super(id); // -> The identifier of the FIG, in the parent class (SingleGraph)
    }

    //#endregion

    //#region TOSTRING

    /**
     * toString() FIG's method.
     * Prompts the informations of the FIG in a nice format
     * 
     * @return The informations of the FIG
     */
    public String toString() {
        return "<html><strong>FIG :</strong> " + 
            super.id + 
            "<br><strong>Number of Flights :</strong> " + 
            this.getNodeCount() + 
            "<br><strong>Number of collisions :</strong> " + 
            this.getNbConflicts() + 
            "</html>";
    }

    //#endregion

    /**
     * Sets the total number of colors used to color the graph
     * @param nbColors new number of colors
     */
    public void setNbColors(int nbColors) {
        if (nbColors < 0) {
            throw new InvalidEntryException() ;
        }
        this.setAttribute(COLOR_ATTRIBUTE, nbColors) ;
        this.setKMax(nbColors) ;
    }

}
