package graph;

import org.graphstream.graph.implementations.*;
import util.Latitude ;
import util.Longitude ;

public class Airport extends SingleNode {
    protected Airport(AbstractGraph graph, String id) {
        super(graph, id) ;
    }

    public void setAirportAttributes(String location, Latitude latitude, Longitude longitude) {
        this.setAttribute("airportLocation", location);
        this.setAttribute("latitude", latitude);
        this.setAttribute("longitude", longitude) ;
    }   
}