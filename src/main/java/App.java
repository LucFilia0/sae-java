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

public class App {
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");

        FlightsIntersectionGraph fig = new FlightsIntersectionGraph("Yep");
        AirportSet as = new AirportSet();

        double timeSecurity = 15;

        
        try {
            as.importAirportsFromFile(new File("DataTest/aeroports.csv"));
            fig.importFlightsFromFile(new File("DataTest/vol-test4.csv"), as, timeSecurity);
            
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
        }


        fig.display();
    }
}