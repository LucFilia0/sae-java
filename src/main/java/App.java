//-- Import Swing

import javax.imageio.ImageIO;
import javax.swing.*;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;
import org.jxmapviewer.viewer.WaypointRenderer;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import ihm.icon.*;

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
import java.io.IOException;
import java.util.HashSet;

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
        String flightsFile = "data/vol-test3.csv";

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

        //fig.display();

        JFrame appFrame = new JFrame("Plane Air");
        int appWidth = 1024;
        int appHeight = 720;

        JPanel body = new JPanel();
        body.setLayout(new BorderLayout());

        Map map = new Map();
        map.addAirports(as, Airport.AIRPORT_ICON_FILE);
        //map.addFlights(fig, Flight.FLIGHT_ICON);
        map.print();

        /*
         * Test Overlay
         */
        HashSet<Waypoint> waypointSet = new HashSet<Waypoint>();

        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<Waypoint>();

        /*
        waypointPainter.setRenderer(new WaypointRenderer<Waypoint>() {
            @Override
            public void paintWaypoint(Graphics2D g, JXMapViewer map, Waypoint w)
            {
                BufferedImage img = null;

                try {
                    img = ImageIO.read(Airport.AIRPORT_ICON_FILE); // Setting up the visual of the Waypoints
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (img != null) {
                    Point2D point = map.getTileFactory().geoToPixel(w.getPosition(), map.getZoom());
                    
                    int x = (int)point.getX() -img.getWidth() / 2;
                    int y = (int)point.getY() -img.getHeight();
                    
                    g.drawImage(img, x, y, null);
                }
            }
        });
        */

        /* BufferedImage img1 = null;
        BufferedImage img2 = null;

        try {
            img1 = ImageIO.read(Airport.AIRPORT_ICON_FILE);
            img2 = ImageIO.read(Flight.FLIGHT_ICON_FILE);
        }catch(IOException e) {
            e.printStackTrace();
        }

        Graphics2D graphic_img1 = img1.createGraphics();
        System.out.println(img1.getGraphics());
        System.out.println(graphic_img1);

        waypointPainter.paint(graphic_img1, map.getMap(), 10, 10);

        waypointSet.add(new Waypoint() {
            @Override
            public GeoPosition getPosition() {
                return new GeoPosition(0, 0);
            }
        });

        waypointPainter.setWaypoints(waypointSet);

        map.getMap().setOverlayPainter(waypointPainter  );

         */

        body.add(map, BorderLayout.CENTER);
        appFrame.setContentPane(body);

        appFrame.setSize(appWidth, appHeight);
        appFrame.setLocationRelativeTo(null);
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appFrame.setVisible(true);
    }
}