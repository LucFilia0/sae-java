package graph;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

import util.* ;

public class App {
    public static void main(String[] args) {
        // Type de rendu + Création du Graph
        System.setProperty("org.graphstream.ui", "swing");
        Graph flightMap = new SingleGraph("SAE") ;
        Time test1 = new Time(-12, 10) ;
        System.out.println(test1);
        Coordinate test2 = new Latitude(-12, -5, 18, 'N') ;
        System.out.println(test2) ;

        // Générateurs
        FlightFactory flights = new FlightFactory() ;
        AirportFactory airports = new AirportFactory() ;
        
        // Génération des vols + Importation (à faire)
        flightMap.setNodeFactory(flights) ;

        Flight f = (Flight) flightMap.addNode("Flight1") ;
        f.setFlightAttributes("Airport1", "Airport2", new Time(17,30), 320);

        //Génération des aéroports + Importation (à faire)
        flightMap.setNodeFactory(airports) ;
        Airport a = (Airport) flightMap.addNode("Airport1") ;
        
        a.setAirportAttributes("Paris", new Latitude(0, 44, 36, 'N'), new Longitude(10, 5, 3, 'E'));
    }
}