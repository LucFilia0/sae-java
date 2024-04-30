//-- Import Swing

import javax.swing.*;

import org.jxmapviewer.viewer.GeoPosition;


import java.awt.BorderLayout;

//-- Import Java

import java.io.File;
import graph.FlightsIntersectionGraph;
import util.AirportSet;

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

        /*
         * Data Importation
         */
        //-- Definitions

        double timeSecurity = 15;

        //String testGraphFile = "data/graph-test1.txt";
        String airportsFile = "data/aeroports.csv";
        String flightsFile = "data/vol-test1.csv";

        System.setProperty("org.graphstream.ui", "swing");

        FlightsIntersectionGraph fig = new FlightsIntersectionGraph("Yep");
        AirportSet as = new AirportSet();

        Map map = new Map();
        
        try {
            as.importAirportsFromFile(new File(airportsFile), map);
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

        // fig.display();

        /*
         * IHM - Map
         */

        JPanel body = new JPanel();
        body.setLayout(new BorderLayout());

        map.getMap().setAddressLocation(new GeoPosition(45.7540653,4.8428123));
        map.getMap().setZoom(12);

        body.add(map, BorderLayout.CENTER);
/* 
        // Waypoints
        // =================================================================================================================================
        String airportName = "ANG";
        double airportLat = 0, airportLong = 0;

        try {
            airportLat = as.getAirport(airportName).getLatitude().getDecimalCoordinate();
            airportLong = as.getAirport(airportName).getLongitude().getDecimalCoordinate();
        }catch(ObjectNotFoundException onfe) {
            System.err.println(onfe);
        }

        GeoPosition gp = new GeoPosition(airportLat, airportLong);

        // A Waypoint needs to return its position (a Waypoint is juste a position)
        Waypoint wp = new Waypoint() {
            @Override
            public GeoPosition getPosition() {
                return gp;
            }
        };

        // A WaypointPainter is the style of the Waypoints (here the default one)
        WaypointPainter<Waypoint> wpp = new WaypointPainter<Waypoint>();
        wpp.setRenderer(new DefaultWaypointRenderer());

        Set<Waypoint> wps = new HashSet<Waypoint>();
        wps.add(wp);

        wpp.setWaypoints(wps); // To set the Waypoint he displays, a WaypointPainter needs to have a Set of Waypoints
        map.getMap().setOverlayPainter(wpp); // Set the WaypointPainter on Overlay

        // =================================================================================================================================

        map.getMap().setAddressLocation(gp);
 */
        //-- Frame
        JFrame _frame = new JFrame("Test Map");
        _frame.setContentPane(body);

        _frame.setSize(1024, 720);
        _frame.setLocationRelativeTo(null);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setVisible(true);
    }
}