package planeair;

//-- Import Swing

import javax.swing.JFrame;


//-- Import AWT

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import planeair.util.Airport;

//-- Import Plane AIR

import planeair.util.AirportSet;
import planeair.graph.graphtype.FlightsIntersectionGraph;
import planeair.graph.graphtype.GraphSAE;
import planeair.graph.graphtype.TestGraph;
import planeair.graph.graphutil.Flight;
import planeair.graph.graphutil.PanelCreator;
import planeair.components.NMainScreen;
import planeair.components.imports.NImportScreen;

/**
 * This class loads the application. It extends JFrame and loads itself. Beautiful isn't it ?
 * 
 * @author Luc le Manifik
 */
public class App extends javax.swing.JFrame {

    //-- LAUNCHING THE APP

    /**
     * The function which is loaded when the program in lauched.
     * 
     * @param args (String[]) - IDK bro
     */
    public static void main(String[] args) {
        
        // DON'T TOUCH THAT IT'S VERY IMPORTANT
        System.setProperty("org.graphstream.ui", "swing") ;
        System.setProperty("sun.java2d.uiScale", "100%") ;
        String osName = System.getProperty("os.name").toLowerCase() ;
        if (osName.startsWith("windows")) {
            System.setProperty("java -Dsun.java2d.directx", "True") ;
        }
        else if (osName.startsWith("linux")) {
            System.setProperty("java -Dsun.java2d.opengl", "True") ;
        }
        else {
            System.out.println("Sorry this is not supported for Mac, get a better OS 👍👍👍\n "+
                "If you're using anything else then just cry harder 🦈") ;
        }

        App app = new App();
        app.setVisible(true);
    }

    //-- APP ATTRIBUTES

    // BASE

    /**
     * The width of the application's screen
     */
    public final static int APPLICATION_SCREEN_WIDTH = 1080;

    /**
     * The height of the application's screen
     */
    public final static int APPLICATION_SCREEN_HEIGHT = 720;

    /**
     * THE COLOR OF THE APP (not really yellow but quand même)
     */
    public static final Color KINDAYELLOW = new Color(242, 219, 7);

    /**
     * The default Bold font for the entire App
     */
    public static final Font KINDABOLD = new Font("Arial", Font.BOLD, 20);

    /**
     * The default Font for the entire App
     */
    public static final Font KINDANORMAL = new Font("Arial", Font.CENTER_BASELINE, 14);

    /**
     * The default italic font
     */
    public static final Font KINDANOBLE = new Font("Arial", Font.ITALIC, 25);

    // FRAMES

    /**
     * Page of selection of import file
     * It's the first that you see when you open the app
     */
    private NImportScreen importScreen;

    /**
     * The principal panel of the App, where the map is located, the graph
     * and how to change them
     */
    private NMainScreen mainScreen;

    // DATA STRUCTURES

    /**
     * The AirportSet which contains all the Airports
     */
    private AirportSet airportSet;

    /**
     * The FIG which contains all the Flights
     */
    private FlightsIntersectionGraph fig;

    /**
     * the TestGraph that loads testGraphs files
     */
    private TestGraph testGraph ;

    /**
     * PanelCreator object used to render the graph
     */
    private PanelCreator graphRenderer ;

    /**
     * The time security
     */
    private double timeSecurity = 15;

    //-- APP CONSTRUCTOR

    /**
     * Creates a new App. It means one. Because there is only one App.
     * 
     * @author Luc le Manifik
     */
    App() {

        // Basic configuration
        this.setTitle("Plane AIR");
        this.setSize(App.APPLICATION_SCREEN_WIDTH, App.APPLICATION_SCREEN_HEIGHT);
        this.setMinimumSize(new Dimension(1200,800));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Setup of the App
        this.initAttributes();
        this.addComponents();
    }

    //-- SETUP METHODS

    /**
     * This procedure initalize all the application's attributes.
     * 
     * @author Luc le Manifik
     */
    private void initAttributes() {

        this.airportSet = null ;
        this.fig = null ;
        this.testGraph = null ;

        this.mainScreen = new NMainScreen(this);
        this.importScreen = new NImportScreen(this);
    }

    /**
     * This procedure places all the components of the App
     * 
     * @author Luc le Manifik
     */
    private void addComponents() {
        
        this.getContentPane().setLayout(new BorderLayout());
        this.add(this.importScreen, BorderLayout.CENTER); // Starts with import screen
    }

    //-- PANEL SWITCH METHODS

    /**
     * Switch from importPanel to principalPanel
     * @author GIRAUD Nila - modified by Luc le Manifik
     */
    public void switchToMainScreen() {

        this.mainScreen.addTimePanel();

        this.remove(this.importScreen);
        this.add(this.mainScreen);

        this.revalidate();
        this.repaint();
    }

    /**
     * Switch from principalePanel to importPanel
     * @author GIRAUD Nila - modified by Luc le Manifik
     */
    public void switchToImportScreen(){
        
        this.remove(this.mainScreen);
        this.add(this.importScreen);

        this.revalidate();
        this.repaint();
    }

    //-- GETTERS AND SETTERS

    /**
     * Sets the value of the Graph, and makes the correct renderer
     * @param graph ({@link planeair.graph.GraphSAE TestGraph}) - The new Graph rendered on the App
     */
    public void setGraph(GraphSAE graph) {
        if (graph == null) {
            this.testGraph = null ;
            this.fig = null ;
            this.graphRenderer = null ;
            return ;
        }
        if (graph instanceof TestGraph) {
            this.testGraph = (TestGraph)graph ;
            this.fig = null ;
        }
        else if (graph instanceof FlightsIntersectionGraph) {
            this.fig = (FlightsIntersectionGraph)graph ;
            this.testGraph = null ;
        }
        else {
            System.err.println("tf did you do ?????") ;
            return ;
        }

        this.graphRenderer = new PanelCreator(graph) ;
    }

    /**
     * Sets the value of the AirportSet
     * 
     * @param fig ({@link planeair.util.AirportSet AirportSet}) - The new AirportSet of the App
     * 
     * @author Luc le Manifik
     */
    public void setAirportSet(AirportSet as) {
        this.airportSet = as;
    }

    /**
     * Returns the PanelCreator which renders the TestGraph
     * @return ({@link planeair.graph.graphutil.PanelCreator PanelCreator}) - The PanelCreator which renders the TestGraph
     */
    public PanelCreator getGraphRenderer() {
        return this.graphRenderer ;
    }

    /**
     * Returns the graph currently being rendered 
     * @return
     */
    public GraphSAE getGraph() {
        return graphRenderer.getGraph() ;
    }

    /**
     * Returns the principal frame of the App
     * @return ({@link planeair.components.NMainScreen NPrincipalPanelApp}) - The principal frame of the App
     */
    public NMainScreen getMainScreen() {
        return this.mainScreen;
    }


    public void initTestGraphRenderer() {
        initTestGraphRenderer(false) ;
    }

    public void initTestGraphRenderer(boolean inOwnFrame) {
        this.graphRenderer = new PanelCreator(this.testGraph, inOwnFrame) ;
    }

    /**
     * Returns the importation panel of the App
     * @return ({@link planeair.components.imports.NImportScreen NImportPanelApp}) - The importation panel od the App
     */
    public NImportScreen getImportScreen() {
        return this.importScreen;
    }

    /**
     * Returns the AirportSet of the App
     * @return ({@link planeair.util.AirportSet AirportSet}) - The AirportSet which contains all the imported Airports
     */
    public AirportSet getAirportSet() {
        return this.airportSet;
    }

    /**
     * Returns the time security of the App
     * @return (double) - The time security under which the crossing Flights are considered in collision
     */
    public double getTimeSecurity() {
        return this.timeSecurity;
    }

}
