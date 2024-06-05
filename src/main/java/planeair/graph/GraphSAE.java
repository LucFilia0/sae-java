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
        this.setAttribute(GraphSAE.NB_MAX_NODES, 0);
        this.setAttribute(GraphSAE.NB_NODES, 0);
        this.setAttribute(GraphSAE.NB_EDGES, 0);
    }

    /**
     * Returns the value of the expected amount of nodes in the GraphTest.
     * 
     * @return (int) - The expected number of nodes in the TestGraph
     * 
     * @author Luc le Manifik
     */
    public int getNbMaxNodes() {
        return (int)this.getAttribute(GraphSAE.NB_MAX_NODES);
    }

    /**
     * Returns the number of nodes implemented in the TestGraph.
     * 
     * @return (int) - The number of nodes in the TestGraph
     * 
     * @author Luc le Manifik
     */
    public int getNbNodes() {
        return (int)this.getAttribute(GraphSAE.NB_NODES);
    }

    /**
     * Returns the number of edges implemented in the TestGraph.
     * 
     * @return nbEdges (int)
     * 
     * @author Luc le Manifik
     */
    public int getNbEdges() {
        return (int)this.getAttribute(GraphSAE.NB_EDGES);
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
     * Sets the value of the expected amount of nodes in the TestGraph.
     * 
     * @param nbMaxNodes (int) - The new value of nbMaxNodes.
     * @throws InvalidEntryException Throwed if the wanted value of nbMaxValues is inferior to 0.
     * 
     * @author Luc le Manifik
     */
    public void setNbMaxNodes(int nbMaxNodes) throws InvalidEntryException {
        if(nbMaxNodes < 0) {
            throw new InvalidEntryException();
        }
        this.setAttribute(TestGraph.NB_MAX_NODES, nbMaxNodes);
    }

    /**
     * Sets the value the number of nodes currently implemented in the TestGraph.
     * 
     * @param nbNodes (int) - The new value of nbNodes.
     * @throws InvalidEntryException Throwed if the wanted value of nbNodes is inferior to 0.
     * 
     * @author Luc le Manifik
     */
    public void setNbNodes(int nbNodes) throws InvalidEntryException {
        if(nbNodes < 0) {
            throw new InvalidEntryException();
        }
        this.setAttribute(TestGraph.NB_NODES, nbNodes);
    }

    /**
     * Sets the value the number of edges currently implemented in the TestGraph.
     * 
     * @param nbEdges (int) - The new value of nbEdges.
     * @throws InvalidEntryException Throwed if the wanted value of nbEdges is inferior to 0.
     * 
     * @author Luc le Manifik
     */
    public void setNbEdges(int nbEdges) throws InvalidEntryException {
        if(nbEdges < 0) {
            throw new InvalidEntryException();
        }
        this.setAttribute(TestGraph.NB_EDGES, nbEdges);
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
