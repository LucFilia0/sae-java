package planeair.graph.graphutil ;

//#region IMPORTS
import org.graphstream.graph.implementations.*;
import org.graphstream.graph.*;
//#endregion

/**
 * Class handling the creation of {@link Flight Flight objects}
 * through the addNode method of graphstream
 * 
 * @author Nathan LIEGEON
 */
public class FlightFactory implements NodeFactory<SingleNode> {
    
    /**
     * Overrides the way {@link org.graphstream.graph.Node Nodes} are 
     * created in a graph so that we can instantiate them as {@link Flight Flights}
     */
    @Override
    public Flight newInstance(String id, Graph graph) {
        return new Flight( (AbstractGraph) graph, id) ;
    }
}