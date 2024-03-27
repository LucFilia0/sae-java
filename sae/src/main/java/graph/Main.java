package graph;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.*;

public class Main {
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");
        Graph flightMap = new SingleGraph("SAE") ;
        
        
        FlightFactory test = new FlightFactory() ;
        flightMap.setNodeFactory(test);
        
        Flight f = (Flight)flightMap.addNode("1") ;
        f.setFlightAttributes("a","b", 8, 49, 47) ;

    }
}