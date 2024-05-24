//-- Import Swing

import javax.swing.JFrame;

import org.graphstream.graph.Node;

//-- Import AWT

import java.awt.BorderLayout;


//-- Import JxMapViewer



//-- Import Java

import java.io.File;
import java.util.LinkedList;
import graph.Coloration;

//-- Import Plane AIR

import graph.FlightsIntersectionGraph;
import graph.PanelCreator;
import graph.TestGraph;
import util.AirportSet;
import util.DataImportation;

//-- Import IHM

import ihm.Map;


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
        // DON'T TOUCH THAT IT'S VERY IMPORTANT
        System.setProperty("sun.java2d.uiScale", "100%") ;
        System.setProperty("org.graphstream.ui", "swing") ;

        /* App planeAIR = new App("Plane AIR"); // Such a great name, isn't it ?
        planeAIR.setVisible(true); */

        TestGraph tg = new TestGraph("graph") ;
        try {
            tg.importFromFile(new File("data/graph-test2.txt"), false) ;
        }

        catch (Exception e) {
            System.err.println(e) ;
        }

        for (Node n : tg) {
            n.setAttribute("color", 0) ;
        }
        int[] res = Coloration.ColorationDsatur(tg, "color", tg.getKMax()) ;
        Coloration.setGraphStyle(tg,res[0], "color") ;
        PanelCreator renderer = new PanelCreator(tg, true) ;
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
        String flightsFile = "data/vol-test8.csv";
        
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
            InvalidEntryException e) {
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
            DataImportation.importFlightsFromFile(as, fig, new File("data/vol-test8.csv"), 15);
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
    } 
    */
}