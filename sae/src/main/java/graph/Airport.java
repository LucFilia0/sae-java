package graph;

import org.graphstream.graph.implementations.*;
import util.Latitude ;
import util.Longitude ;

/**
 * Class managing {@link org.graphstream.graph.Node Nodes} representing the airports
 * 
 * @author Nathan LIEGEON
 */
public class Airport extends SingleNode {

    /**
     * Instantiates an {@link Airport} object, use a {@link AirportFactory} to create one
     * @see AirportFactory
     * @param graph
     * @param id
     * 
     * @author Nathan LIEGEON
     */
    protected Airport(AbstractGraph graph, String id) {
        super(graph, id) ;
    }

    /**
     * Fills in all the attributes of an {@link Airport}
     * 
     * @param location Which city the airport is located in
     * @param latitude Latitude at which the airport is located
     * @param longitude Longitude at which the airport is located
     * 
     * @author Nathan LIEGEON
     */
    public void setAirportAttributes(String location, Latitude latitude, Longitude longitude) {
        this.setAttribute("airportLocation", location);
        this.setAttribute("latitude", latitude);
        this.setAttribute("longitude", longitude) ;
    }   
}