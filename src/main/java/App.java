//-- Import Swing

import javax.swing.*;

import java.awt.BorderLayout;

//-- Import Java

import java.io.File;

import graph.Flight;
import graph.FlightsIntersectionGraph;
import util.AirportSet;
import util.Airport;

//-- Import IHM

import ihm.Map;


//-- Import Exceptions

import java.io.FileNotFoundException;

import exceptions.InvalidCoordinateException;
import exceptions.InvalidTimeException;
import exceptions.ObjectNotFoundException;
import exceptions.InvalidEntryException;

public class App {
    public static void main(String[] args) {

        // == Data Importation
        double timeSecurity = 15;

        //String testGraphFile = "data/graph-test1.txt";
        String airportsFile = "data/aeroports.csv";
        String flightsFile = "data/vol-test1.csv";

        System.setProperty("org.graphstream.ui", "swing");

        FlightsIntersectionGraph fig = new FlightsIntersectionGraph("FIG");
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

        JFrame appFrame = new JFrame("Plane Air");
        int appWidth = 1024;
        int appHeight = 720;

        JPanel body = new JPanel();
        body.setLayout(new BorderLayout());

        Map map = new Map();
        map.center();
        //map.addAirports(as, Airport.AIRPORT_ICON_FILE);
        map.addFlights(fig, Flight.FLIGHT_ICON);
        map.print();

        body.add(map, BorderLayout.CENTER);
        appFrame.setContentPane(body);

        appFrame.setSize(appWidth, appHeight);
        appFrame.setLocationRelativeTo(null);
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appFrame.setVisible(true);
    }
}