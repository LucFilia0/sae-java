package planeair.graph.graphtype;

//-- Import GraphStream

//-- Import Exceptions

import planeair.exceptions.InvalidEntryException;
import planeair.graph.coloring.ColoringUtilities;

/**
 * TestGraph is the basic class to handle the "graph-testX.txt" files.
 * This class extends the class SingleGraph, from GraphStream.
 * 
 * @implNote Uses the GraphStream attributes.
 * 
 * @author Luc le Manifik
 */
public class TestGraph extends GraphSAE {

    //-- TestGraph Attributes

    /**
     * The String identifier that represents the max allowed number of colors (int)
     */
    public static final String K_MAX = "kMax";
    /**
     * The String identifier that represents the current number of conflicts (int)
     */
    public static final String CONFLICT_ATTRIBUTE = "nbConflicts" ; 

    //-- TestGraph Constructor

    /**
     * Constructor of the TestGraph class.
     * Creates a new TestGraph.
     * 
     * @param id (String) - The identifier of the TestGraph
     * 
     * @author Luc le Manifik
     */
    public TestGraph(String id) {
        super(id);
        this.setAttribute(TestGraph.K_MAX, 0);
        this.setAttribute(TestGraph.CONFLICT_ATTRIBUTE, 0);
        this.setAttribute(GraphSAE.NB_MAX_NODES, 0);
        ColoringUtilities.setGraphStyle(this, 0) ;
    }

    //-- TestGraph toString()

    /**
     * This function prints the informations of its TestGraph.
     * It shows the graph's identifier, kMax and number of nodes.
     * 
     * @return the String which contains those informations.
     * 
     * @author Luc le Manifik
     */
    public String toString() {
        return "-- TestGraph\nIdentifier : " + super.id + "\nkMax : " + this.getKMax() + "\nNumber of nodes : " + this.getNodeCount() + "/" + this.getNbMaxNodes() + "\nNumber of edges : " + this.getEdgeCount();
    }

    //-- TestGraph Getters

    /**
     * Returns the identifier of the TestGraph.
     * 
     * @return (String) - The identifier of the TestGraph
     * 
     * @author Luc le Manifik
     */
    public String getId() {
        return super.getId();
    }

    /**
     * Returns the value of kMax, the maximum number of allowed colors.
     * 
     * @return (int) - The maximum number of colors in the TestGraph
     * 
     * @author Luc le Manifik
     */
    public int getKMax() {
        return (int)this.getAttribute(TestGraph.K_MAX);
    }

    /**
     * Returns the number of conflicts that occurred while coloring the graph
     * 
     * @return
     */
    public int getNbConflicts() {
        return (int)this.getAttribute(TestGraph.CONFLICT_ATTRIBUTE) ;
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

    //-- TestGraph Setters

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
        this.setAttribute(TestGraph.K_MAX, kMax);;
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
}

   