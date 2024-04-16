package graph;

import org.graphstream.graph.* ;
import org.graphstream.graph.implementations.*;

/**
 * Class handling the creation of Airports through the 
 * addNode method of graph stream
 */
public class AirportFactory implements NodeFactory<SingleNode> {

    /**
     * Overrides 
     */
    @Override
    public Airport newInstance(String id, Graph graph) {
        return new Airport( (AbstractGraph) graph, id) ;
    }
}
