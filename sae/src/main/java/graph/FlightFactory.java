package graph ;

import org.graphstream.graph.implementations.*;
import org.graphstream.graph.*;

public class FlightFactory implements NodeFactory<SingleNode> {
    @Override
    public SingleNode newInstance(String id, Graph graph) {
        return new Flight( (AbstractGraph) graph, id) ;
    }
}