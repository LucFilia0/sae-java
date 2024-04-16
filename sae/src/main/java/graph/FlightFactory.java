package graph ;

import org.graphstream.graph.implementations.*;
import org.graphstream.graph.*;

public class FlightFactory implements NodeFactory<SingleNode> {
    @Override
    public Flight newInstance(String id, Graph graph) {
        return new Flight( (AbstractGraph) graph, id) ;
    }
}