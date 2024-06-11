package planeair.graph.graphtype;

import org.graphstream.graph.implementations.SingleGraph;

import planeair.exceptions.InvalidEntryException;
import planeair.graph.coloring.ColoringUtilities;
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
     * The String identifier that represents the current number of colors (int)
     */
    public static final String COLOR_ATTRIBUTE = "nbColors" ;

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

    /**
     * Removes the "ui.hide" attribute from every node of this graph
     */
    public void showAllNodes() {
        this.nodes().forEach(n -> {
            n.removeAttribute("ui.hide") ;
            n.edges().forEach(e -> e.removeAttribute("ui.hide"));
        });
    }

    /**
     * Shows only nodes with their colorAttribute set to color
     * Gives the attribute "ui.hide" to all the ones which don't
     * @param color
     */
    public void showNodesWithColor(int color) {
        this.nodes().forEach(n -> {
            if ((Integer)n.getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE) == color) {
                n.setAttribute("ui.hide") ;
                n.edges().forEach(e -> e.setAttribute("ui.hide")) ;
            }
        }) ;
    }
}
