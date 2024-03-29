package graph;

import org.graphstream.graph.implementations.*;
import util.* ;

public class Flight extends SingleNode {
    
    protected Flight(AbstractGraph graph, String id) {
        super(graph,id) ;
    }

    public void setFlightAttributes(String departureAirport, String arrivalAirport, Time departTime, int length) {
        this.setAttribute("departure", departureAirport) ;
        this.setAttribute("arrival", arrivalAirport) ;
        this.setAttribute("departureTime", departTime) ;
        this.setAttribute("flightLength", length) ;
    }

    
}
