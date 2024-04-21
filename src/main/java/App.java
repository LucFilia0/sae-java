//-- Import Java

import java.io.File;
import graph.FlightsIntersectionGraph;
import util.AirportSet;

//-- Import GraphStream


//-- Import Exceptions

import java.io.FileNotFoundException;

import exceptions.InvalidCoordinateException;
import exceptions.InvalidTimeException;
import exceptions.ObjectNotFoundException;
import exceptions.InvalidEntryException;

public class App {
    public static void main(String[] args) {

        //-- Definitions

        double timeSecurity = 15;

        //String testGraphFile = "data/graph-test1.txt";
        String airportsFile = "data/aeroports.csv";
        String flightsFile = "data/vol-test2.csv";

        System.setProperty("org.graphstream.ui", "swing");

        FlightsIntersectionGraph fig = new FlightsIntersectionGraph("Yep");
        AirportSet as = new AirportSet();
        
        try {
            as.importAirportsFromFile(new File(airportsFile));
            fig.importFlightsFromFile(new File(flightsFile), as, timeSecurity);
            
            //as.showAllAirports();
        }catch(FileNotFoundException fnfe) {
            System.err.println(fnfe);
        }catch(NumberFormatException nfe) {
            System.err.println(nfe);
        }catch(InvalidTimeException ite) {
            System.err.println(ite);
        }catch(InvalidCoordinateException ice) {
            System.err.println(ice);
        }catch(ObjectNotFoundException onfe) {
            System.err.println(onfe);
        }catch(InvalidEntryException iee) {
            System.err.println(iee);
        }

        fig.display();
    }
}