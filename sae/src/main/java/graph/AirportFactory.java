package graph;

import org.graphstream.graph.* ;
import org.graphstream.graph.implementations.*;

/**
 * Class handling the creation of {@link Airport Airport objects} 
 * through the addNode method of graphstream
 * 
 * @author Nathan LIEGEON
 */
public class AirportFactory implements NodeFactory<SingleNode> {

    /**
     * Overrides the way nodes are created in a graph so that we can instantiate them as airports
     */
    @Override
    public Airport newInstance(String id, Graph graph) {
        return new Airport( (AbstractGraph) graph, id) ;
    }
}
