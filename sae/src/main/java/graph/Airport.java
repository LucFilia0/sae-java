package graph;

import org.graphstream.graph.implementations.*;
import util.Latitude ;
import util.Longitude ;

/**
 * Class managing Nodes representing the airports
 */
public class Airport extends SingleNode {

    /**
     * Instantiates an Airport, use AirportFactory to create one
     * @see AirportFactory
     * @param graph
     * @param id
     */
    protected Airport(AbstractGraph graph, String id) {
        super(graph, id) ;
    }

    public void setAirportAttributes(String location, Latitude latitude, Longitude longitude) {
        this.setAttribute("airportLocation", location);
        this.setAttribute("latitude", latitude);
        this.setAttribute("longitude", longitude) ;
    }   
}