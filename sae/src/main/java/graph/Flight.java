package graph;

import org.graphstream.graph.implementations.*;
import util.* ;

/**
 * Class managing Nodes representing the flights
 * 
 * @author Nathan LIEGEON
 */
public class Flight extends SingleNode {
    
    /**
     * Instantiates a Flight object, use a FlightFactory to create one
     * @see FlightFactory
     * 
     * @param graph
     * @param id
     */
    protected Flight(AbstractGraph graph, String id) {
        super(graph,id) ;
    }

    /**
     * Fills in all the attributes of a Flight
     * 
     * @param departureAirport Airport from which the flight departed
     * @param arrivalAirport Airport in which it will land
     * @param departTime Time at which it departed
     * @param length Length of the flight (in minutes)
     */
    public void setFlightAttributes(String departureAirport, String arrivalAirport, FlightTime departTime, int length) {
        this.setAttribute("departure", departureAirport) ;
        this.setAttribute("arrival", arrivalAirport) ;
        this.setAttribute("departureTime", departTime) ;
        this.setAttribute("flightLength", length) ;
    }

    
}
