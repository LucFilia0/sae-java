package graph;

import org.graphstream.graph.implementations.*;
import org.javatuples.Quartet ;

public class Airport extends SingleNode {
    protected Airport(AbstractGraph graph, String id) {
        super(graph, id) ;
    }

    public void setAirportAttributes(String location, Quartet<Integer,Integer,Integer,String> latitude, Quartet<Integer,Integer,Integer,String> longitude) {
        this.setAttribute("airportLocation", location);
        this.setAttribute("latitude", latitude);
        this.setAttribute("longitude", longitude) ;
    }   
}