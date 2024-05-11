//-- Import Swing

import javax.swing.JComboBox;
import javax.swing.JFrame;

//-- Import AWT

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jxmapviewer.JXMapViewer;

//-- Import JxMapViewer

import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;


//-- Import Java

import java.util.HashSet;
import java.io.File;

import graph.Flight;
import graph.FlightsIntersectionGraph;
import graph.TestGraph;
import util.AirportSet;
import util.Airport;

//-- Import IHM

import ihm.Map;
import ihm.waypoint.MapWaypoint;
import ihm.waypoint.FlightWaypoint;
import ihm.waypoint.AirportWaypoint;
import ihm.waypoint.MapWaypointPainter;

//-- Import Exceptions

import java.io.FileNotFoundException;

import exceptions.InvalidCoordinateException;
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
    private final static int APPLICATION_SCREEN_WIDTH = 1080;

    /**
     * The height of the application's screen
     */
    private final static int APPLICATION_SCREEN_HEIGHT = 720;

    /**
     * The TestGraph, xhich is used (sometimes)
     */
    private TestGraph testGraph;

    /**
     * The graph which sets all the Flights on their Layer, and avoid collisions
     */
    private FlightsIntersectionGraph fig;

    /**
     * The Set which contains all the Airports
     */
    private AirportSet airportSet;

    /**
     * The Set which contains all the MapWaypoints currently on the Map
     */
    private HashSet<MapWaypoint> waypointSet;

    /**
     * The WaypointPainter which paints all the Waypoints on the Map.
     */
    private MapWaypointPainter mapWaypointPainter;

    /**
     * The Map. No... THE Map. That's better
     */
    private JXMapViewer map;

    /**
     * The JComboBox xhich allows to switch betwenn the different modes of the Map (no THE... ok I'm stopping...)
     */
    private JComboBox<String> viewMapChooser;

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
        this.initAttributes();

        this.importData();
        this.setComponents();
        this.placeComponents();
        this.initEvents();

        this.paintWaypoints();
    }

    /**
     * This procedure initalize all the application's components.
     * 
     * @author Luc le Manifik
     */
    private void initAttributes() {

        // Data
        this.testGraph = new TestGraph("TestGraph");
        this.fig = new FlightsIntersectionGraph("FlightIntersectionGraph");
        this.airportSet = new AirportSet();
        
        // IHM
        this.map = new JXMapViewer();
        String[] viewMapOptions = new String[]{"Open Stree", "Virtual Earth", "Hybrid", "Satellites"};
        this.viewMapChooser = new JComboBox<String>(viewMapOptions);

        // Waypoints (you touch you dead)
        this.waypointSet = new HashSet<MapWaypoint>();
        this.mapWaypointPainter = new MapWaypointPainter();
        this.map.setOverlayPainter(this.mapWaypointPainter);
    }

    private void importData() {

        double timeSecurity = 15;

        //String testGraphFile = "data/graph-test1.txt";
        String airportsFile = "data/aeroports.csv";
        String flightsFile = "data/vol-test1.csv";
        
        try {
            this.airportSet.importAirportsFromFile(new File(airportsFile));
            this.fig.importFlightsFromFile(new File(flightsFile), this.airportSet, timeSecurity);
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

        // The Map
        DefaultTileFactory _defaultTileFactory = new DefaultTileFactory(new OSMTileFactoryInfo()); // Default look when the App is launched
        this.map.setTileFactory(_defaultTileFactory);
        this.map.setAddressLocation(new GeoPosition(45.7701836,4.8834086));
        this.map.setZoom(6);

        // The viewMapChooser
        
    }

    /**
     * This procedure places all the components in their right place.
     * Does the Layouts, etc...
     * 
     * @author Luc le Manifik
     */
    private void placeComponents() {

        // Layout
        this.getContentPane().setLayout(new BorderLayout());

        // Adding elements
        this.add(this.map, BorderLayout.CENTER);
        this.add(this.viewMapChooser, BorderLayout.NORTH);
    }

    /**
     * This procedure creates all the events.
     * 
     * @author Luc le Manifik
     */
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
    }

    /**
     * This procedure paints the MapWaypoints on the Map.
     * the Flights are only painted id they are currently flying.
     */
    private void paintWaypoints() {

        // removing all the current MapWaypoints from the Map
        // this.map.removeAll();

        addAirports();
        addFlights();

        this.mapWaypointPainter.setWaypoints(this.waypointSet);

        for(MapWaypoint waypoint : this.waypointSet) {
            this.map.add(waypoint.getWaypointButton()); // Adds the WaypointButtons, which are the visual for Waypoint
        }
    }

    /**
     * This procedure adds all the Airports which are in the airportSet.
     * 
     * @author Luc le Manifik
     */
    private void addAirports() {

        for(Airport airport : this.airportSet.getAirportSet()) {
            GeoPosition airportPosition = new GeoPosition(airport.getLatitude().getDecimalCoordinate(), airport.getLongitude().getDecimalCoordinate());
            // Adding the airport to the waypointSet
            this.waypointSet.add(new AirportWaypoint(airport.getName(), airportPosition));
        }
    }

    /**
     * This procedure adds all the Flights which are in the FIG.
     * 
     * @author Luc le Manifik
     */
    private void addFlights() {

        this.fig.forEach(node -> {
            Flight flight = (Flight)node;
            GeoPosition currentFlightPosition = flight.getCurrentGeoPosition();
            // The function returns null is the Flight is not currently flying
            if(currentFlightPosition != null) {
                this.waypointSet.add(new FlightWaypoint(flight.getId(), currentFlightPosition));
                System.out.println("Flight added");
            }
        });
    }
}