package graph;

import org.graphstream.graph.* ;
import org.graphstream.graph.implementations.*;

public class AirportFactory implements NodeFactory<SingleNode> {
    @Override
    public SingleNode newInstance(String id, Graph graph) {
        return new Airport( (AbstractGraph) graph, id) ;
    }
}
