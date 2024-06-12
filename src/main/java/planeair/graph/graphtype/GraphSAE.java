package planeair.graph.graphtype;

import java.awt.Color;

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
     * The String identifier that represents the max allowed number of colors (int)
     */
    public static final String K_MAX = "kMax";
    /**
     * The String identifier that represents the current number of conflicts (int)
     */
    public static final String CONFLICT_ATTRIBUTE = "nbConflicts" ; 

    private Color[] colorTab ;

    /**
     * Constructor setting our attributes to a default value of 0.
     * @param id
     */
    protected GraphSAE(String id) {
        super(id) ;
        this.setAttribute(GraphSAE.COLOR_ATTRIBUTE, 0) ;
        this.setAttribute(GraphSAE.K_MAX, 0);
        this.setAttribute(TestGraph.CONFLICT_ATTRIBUTE, 0);
        this.setAttribute(GraphSAE.NB_MAX_NODES, 0);
        ColoringUtilities.setGraphStyle(this, 0) ;
    }

    /**
     * Returns the value of kMax, the maximum number of allowed colors.
     * 
     * @return (int) - The maximum number of colors in the TestGraph
     * 
     * @author Luc le Manifik
     */
    public int getKMax() {
        return (int)this.getAttribute(GraphSAE.K_MAX);
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
     * Returns the number of conflicts that occurred while coloring the graph
     * 
     * @return
     */
    public int getNbConflicts() {
        return (int)this.getAttribute(TestGraph.CONFLICT_ATTRIBUTE) ;
    }

    public Color[] getColorTab() {
        return this.colorTab ;
    }

    /**
     * Sets the value of kMax, which is the maximum amount of colors of the TestGraph.
     * 
     * @param kMax (int) - The new value of kMax.
     * @throws InvalidEntryException Throwed if the wanted value of kMax is inferior to 0.
     * 
     * @author Luc le Manifik
     */
    public void setKMax(int kMax) throws InvalidEntryException {
        if(kMax < 0) {
            throw new InvalidEntryException();
        }
        this.setAttribute(GraphSAE.K_MAX, kMax);;
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
     * Sets the total number of conflicts the graph has
     * An edge counts as a conflict is both of its nodes have the same color.
     * @param nbConflicts new number of conflicts
     */
    public void setNbConflicts(int nbConflicts) {
        if (nbConflicts < 0) {
            throw new InvalidEntryException() ;
        }
        this.setAttribute(CONFLICT_ATTRIBUTE, nbConflicts) ;
    }

    public void setColorTab(Color[] colorTab) {
        this.colorTab = colorTab ;
    }

    /**
     * Removes the "ui.hide" attribute from every node of this graph
     */
    public void showAllNodes() {
        this.nodes().forEach(n -> {
            n.edges().forEach(e -> e.removeAttribute("ui.hide"));
            n.removeAttribute("ui.hide") ;
        });
    }

    /**
     * Shows only nodes with their colorAttribute set to color
     * Gives the attribute "ui.hide" to all the ones that don't
     * @param color
     */
    public void showNodesWithColor(int color) {
        this.nodes().forEach(n -> {
            if ((Integer)n.getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE) != color) {
                n.edges().forEach(e -> {
                    e.setAttribute("ui.hide") ;
                }) ;
                n.setAttribute("ui.hide") ;
            }
        }) ;
    }

}
