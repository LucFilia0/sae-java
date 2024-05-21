//-- Import Swing

import javax.management.JMX;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.Painter;

import java.awt.BasicStroke;

//-- Import AWT

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

import org.graphstream.ui.graphicGraph.stylesheet.Color;
import org.jxmapviewer.JXMapViewer;

//-- Import JxMapViewer

import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.painter.AbstractPainter;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;

import com.lowagie.text.Rectangle;

//-- Import Java

import java.util.HashSet;
import java.io.File;
import java.util.LinkedList;

//-- Import Plane AIR

import graph.Flight;
import graph.FlightsIntersectionGraph;
import graph.TestGraph;
import util.AirportSet;
import util.Airport;
import util.DataImportation;

//-- Import IHM

import ihm.Map;
import ihm.mapvisuals.mapwp.MapWaypoint;
import ihm.mapvisuals.mapwp.airportwp.ActiveAirportWaypoint;
import ihm.mapvisuals.mapwp.airportwp.AirportWaypoint;
import ihm.mapvisuals.mapwp.airportwp.InactiveAirportWaypoint;
import ihm.mapvisuals.mapwp.flightwp.FlightWaypoint;

//-- Import Exceptions

import java.io.FileNotFoundException;

import exceptions.InvalidCoordinateException;
import exceptions.InvalidCoordinatesException;
import exceptions.InvalidTimeException;
import exceptions.ObjectNotFoundException;
import exceptions.InvalidEntryException;

/**
 * This class loads the application.
 * 
 * @author Luc le Manifik
 */
public class App extends javax.swing.JFrame {

    public static void main(String[] args) {

        App planeAIR = new App("Plane AIR"); // Such a great name, isn't it ?
        planeAIR.setVisible(true);
    }

    /**
     * The width of the application's screen
     */
    public final static int APPLICATION_SCREEN_WIDTH = 1080;

    /**
     * The height of the application's screen
     */
    public final static int APPLICATION_SCREEN_HEIGHT = 720;

    private LinkedList<AirportSet> airportSets;

    private LinkedList<FlightsIntersectionGraph> figs;

    /**
     * The constructor of the App class. Creates a new App. Initiates all the differents steps before to launch the App.
     * 
     * @param name (String) - The name of the Application
     * 
     * @author Luc le Manifik
     */
    App(String name) {
        
        System.setProperty("org.graphstream.ui", "swing");
        
        this.setTitle(name);
        //this.initAttributes();

        //this.importData();
        this.setComponents();
        //this.placeComponents();
        //this.initEvents();

        this.test();
    }

    /**
     * This procedure initalize all the application's components.
     * 
     * @author Luc le Manifik
     */
    private void initAttributes() {
        this.airportSets = new LinkedList<AirportSet>();
        this.figs = new LinkedList<FlightsIntersectionGraph>();
    }

    private void importData() {

        // TEMP
        AirportSet as = new AirportSet();
        FlightsIntersectionGraph fig = new FlightsIntersectionGraph("Waffle");

        double timeSecurity = 15;

        //String testGraphFile = "data/graph-test1.txt";
        String airportsFile = "data/aeroports.csv";
        String flightsFile = "data/vol-test1.csv";
        
        try {
            DataImportation.importAirportsFromFile(as, fig, new File(airportsFile));
            DataImportation.importFlightsFromFile(as, fig, new File(flightsFile), timeSecurity);
            //as.showAllAirports();
        }catch(
            FileNotFoundException |
            NumberFormatException |
            InvalidTimeException |
            InvalidCoordinateException |
            ObjectNotFoundException |
            InvalidEntryException
            e) {
            System.err.println(e);
        }
    }

    /**
     * This procedure will set up all the components.
     * 
     * @author Luc le Manifik
     */
    private void setComponents() {

        // The App
        this.setSize(App.APPLICATION_SCREEN_WIDTH, App.APPLICATION_SCREEN_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The viewMapChooser
        
    }

    /**
     * This procedure places all the components in their right place.
     * Does the Layouts, etc...
     * 
     * @author Luc le Manifik
     */
    private void test()  {

        // Layout
        this.getContentPane().setLayout(new BorderLayout());

        // Adding elements


        // TEST
        AirportSet as = new AirportSet();
        FlightsIntersectionGraph fig = new FlightsIntersectionGraph("Waffle");

        try {
            DataImportation.importAirportsFromFile(as, fig, new File("data/aeroports.csv"));
            DataImportation.importFlightsFromFile(as, fig, new File("data/vol-test4.csv"), 15);
        }catch(FileNotFoundException | NumberFormatException | InvalidCoordinateException | ObjectNotFoundException | InvalidEntryException | InvalidTimeException e) {
            System.err.println(e);
        }
        DataImportation.setActiveAirports(as, fig);

        
        Map map = new Map();
        //map.paintWaypoints(as, fig);
        map.paintMapItems(as, fig);
        
        this.add(map, BorderLayout.CENTER);
    }
/* 
    /**
     * This procedure creates all the events.
     * 
     * @author Luc le Manifik
     *
    private void initEvents() {

        // Mouse listening events
        PanMouseInputListener _mouseListener = new PanMouseInputListener(map);
        this.map.addMouseListener(_mouseListener);
        this.map.addMouseMotionListener(_mouseListener);
        this.map.addMouseWheelListener(new ZoomMouseWheelListenerCursor(map));

        // ViewMapChooser
        this.viewMapChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                int index = viewMapChooser.getSelectedIndex();
                switch(index) {
                    case 0 :
                        map.setTileFactory(new DefaultTileFactory(new OSMTileFactoryInfo()));
                        break;
                    case 1 :
                        map.setTileFactory(new DefaultTileFactory(new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP)));
                        break;
                    case 2 :
                        map.setTileFactory(new DefaultTileFactory(new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID)));
                        break;
                    case 3 :
                        map.setTileFactory(new DefaultTileFactory(new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE)));
                        break;
                    default :
                        System.err.println("Omg what did you do ??");
                        break;
                }
            }
        });
    } */
}