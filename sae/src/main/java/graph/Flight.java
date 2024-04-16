package graph;

import org.graphstream.graph.implementations.*;
import util.* ;

/**
 * Class managing {@link org.graphstream.graph.implementations.SingleNode Nodes} representing the flights
 * 
 * @author Nathan LIEGEON
 */
public class Flight extends SingleNode {
    
    /**
     * Instantiates a {@link Flight} object, use a {@link FlightFactory} to create one
     * 
     * @param graph
     * @param id
     * 
     * @author Nathan LIEGEON
     */
    protected Flight(AbstractGraph graph, String id) {
        super(graph,id) ;
    }

    /**
     * Fills in all the attributes of a {@link Flight}
     * 
     * @param departureAirport Airport from which the flight departed
     * @param arrivalAirport Airport in which it will land
     * @param departTime Time at which it departed
     * @param length Length of the flight (in minutes)
     * 
     * @author Nathan LIEGEON
     */
    public void setFlightAttributes(String departureAirport, String arrivalAirport, FlightTime departTime, int length) {
        this.setAttribute("departure", departureAirport) ;
        this.setAttribute("arrival", arrivalAirport) ;
        this.setAttribute("departureTime", departTime) ;
        this.setAttribute("flightLength", length) ;
    }

    
}
