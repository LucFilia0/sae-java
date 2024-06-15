package planeair.graph.graphtype;

//#region IMPORTS

    //#region EXCEPTIONS

    import planeair.exceptions.InvalidEntryException;

    //#endregion

//#endregion

/**
 * TestGraph is the basic class to handle the "graph-testX.txt" files.
 * This class extends the class SingleGraph, from GraphStream.
 * It's basically an empty Graph, with ne peculiar meaning, and just allows you to test your code
 * 
 * @implNote Uses the GraphStream attributes
 * 
 * @author Luc le Manifik
 */
public class TestGraph extends GraphSAE {

    //#region CONSTRUCTORS

    /**
     * Creates a new TestGraph.
     * A TestGraph is a basic graph for the SAE, and just allows you to test your algorithmes
     * 
     * @param id The identifier of the TestGraph
     * 
     * @author Luc le Manifik
     */
    public TestGraph(String id) {
        super(id);
    }

    //#endregion

    //#region TOSTRING

    /**
     * This function prints the informations of its TestGraph.
     * It shows the graph's identifier, kMax and number of nodes.
     * 
     * @return the String which contains those informations.
     * 
     * @author Luc le Manifik
     */
    public String toString() {
        return "<html><strong>TestGraph :</strong> " + super.id + "<br><strong>kMax :</strong> " + this.getKMax() + "<br><strong>Number of nodes :</strong> " + this.getNodeCount() + "/" + this.getNbMaxNodes() + "<br><strong>Number of edges :</strong> " + this.getEdgeCount() + "</html>";
    }

    //#endregion

    //#region GETTERS

    /**
     * Returns the identifier of the TestGraph.
     * 
     * @return The identifier of the TestGraph
     * 
     * @author Luc le Manifik
     */
    public String getId() {
        return super.getId();
    }

    /**
     * Returns the value of the expected amount of nodes in the GraphTest.
     * 
     * @return The expected number of nodes in the TestGraph
     * 
     * @author Luc le Manifik
     */
    public int getNbMaxNodes() {
        return (int)this.getAttribute(GraphSAE.NB_MAX_NODES);
    }

    //#endregion

    //#region SETTERS

    /**
     * Sets the value of the expected amount of nodes in the TestGraph
     * 
     * @param nbMaxNodes The new value of nbMaxNodes
     * 
     * @throws InvalidEntryException Threw if the wanted value of nbMaxValues is inferior to 0
     * 
     * @author Luc le Manifik
     */
    public void setNbMaxNodes(int nbMaxNodes) throws InvalidEntryException {
        if(nbMaxNodes < 0) {
            throw new InvalidEntryException();
        }
        this.setAttribute(TestGraph.NB_MAX_NODES, nbMaxNodes);
    }

    //#endregion
}

   