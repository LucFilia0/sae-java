package planeair.graph;

import org.graphstream.graph.implementations.SingleGraph;

import planeair.exceptions.InvalidEntryException;
/**
 * Abstract class containing our default graph attributes
 * @see TestGraph
 * @see FlightsIntersectionGraph
 * 
 * @author Nathan LIEGEON
 */
public abstract class GraphSAE extends SingleGraph {

    /**
     * The String identifier that represents the max allowed number of Nodes (int)
     */
    public static final String NB_MAX_NODES = "nbMaxNodes";

    /**
     * The String identifier that represents the current number of Nodes (int)
     */
    public static final String NB_NODES = "nbNodes";

    /**
     * The String identifier that represents the current number of edges (int)
     */
    public static final String NB_EDGES = "nbEdges";

    /**
     * The String identifier that represents the current number of colors (int)
     */
    public static final String COLOR_ATTRIBUTE = "nbColors" ;

    /**
     * The String identifier that that represents the color assigned to a Node in a GraphSAE (int)
     */
    public static final String NODE_COLOR_ATTRIBUTE = "color" ;

    /**
     * Constructor setting our attributes to a default value of 0.
     * @param id
     */
    protected GraphSAE(String id) {
        super(id) ;
        this.setAttribute(GraphSAE.COLOR_ATTRIBUTE, 0) ;
    }

    /**
     * Returns the number of colors used to color the graph
     * 
     * @return
     */
    public int getNbColors() {
        return (int)this.getAttribute(GraphSAE.COLOR_ATTRIBUTE) ;
    }

    /**
     * Sets the total number of colors used to color the graph
     * @param nbConflicts new number of colors
     */
    public void setNbColors(int nbColors) {
        if (nbColors < 0) {
            throw new InvalidEntryException() ;
        }
        this.setAttribute(COLOR_ATTRIBUTE, nbColors) ;
    }
}
