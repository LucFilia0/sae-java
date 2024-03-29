package graph;

import org.graphstream.graph.implementations.*;
import java.sql.Time ;

public class Flight extends SingleNode {
    
    protected Flight(AbstractGraph graph, String id) {
        super(graph,id) ;
    }

    public void setFlightAttributes(String aeroportDepart, String aeroportArrivee, Time depart, int length) {
        this.setAttribute("departure", aeroportDepart) ;
        this.setAttribute("arrival", aeroportArrivee) ;
        this.setAttribute("departureTime", depart) ;
        this.setAttribute("flightLength", length) ;
    }

    
}
