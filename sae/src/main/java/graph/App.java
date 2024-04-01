package graph;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

import exceptions.* ;
import util.* ;

public class App {
    public static void main(String[] args) {
        // Type de rendu + Création du Graph
        System.setProperty("org.graphstream.ui", "swing");
        Graph flightMap = new SingleGraph("SAE") ;

        // Générateurs
        FlightFactory flights = new FlightFactory() ;
        AirportFactory airports = new AirportFactory() ;
        
        // Génération des vols + Importation (à faire)
        flightMap.setNodeFactory(flights) ;

        Flight f = (Flight) flightMap.addNode("Flight1") ;
        try {
            f.setFlightAttributes("Airport1", "Airport2", new Time(17,30), 320);
        }

        catch (InvalidTimeException ite) {
            System.err.println(ite) ;
        }

        //Génération des aéroports + Importation (à faire)
        flightMap.setNodeFactory(airports) ;
        Airport a = (Airport) flightMap.addNode("Airport1") ;
        try {
            a.setAirportAttributes("Paris", new Latitude(0, 44, 36, 'N'), new Longitude(10, 5, 3, 'E'));
        }

        catch(InvalidCoordinatesException ICE) {
            System.err.println(ICE) ;
        }

        catch(InvalidLatitudeDirectionException ILaDE) {
            System.err.println(ILaDE) ;
        }

        catch(InvalidLongitudeDirectionException ILoDE) {
            System.err.println(ILoDE) ;
        }
    }
}