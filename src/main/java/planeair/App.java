package planeair;

//#region IMPORTS
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import planeair.util.AirportSet;
import planeair.graph.graphtype.FlightsIntersectionGraph;
import planeair.graph.graphtype.GraphSAE;
import planeair.graph.graphtype.TestGraph;
import planeair.graph.graphutil.PanelCreator;
import planeair.components.NMainScreen;
import planeair.components.imports.NImportScreen;
//#endregion

//#endregion

/**
 * This class loads the application. It extends JFrame and loads itself. Beautiful isn't it ?
 * 
 * @author Luc le Manifik
 */
public class App extends javax.swing.JFrame {

    //#region LAUNCHER

    /**
     * The main app of this project
     */
    public static App app ; // ChokBar de Bzzzz

    /**
     * The function which is loaded when the program in lauched.
     * 
     * @param args IDK bro. Maybe something like argc and argv in C ??
     * 
     * @author Not me
     */
    public static void main(String[] args) {
        
        // DON'T TOUCH THAT IT'S VERY IMPORTANT
        System.setProperty("org.graphstream.ui", "swing") ;
        System.setProperty("sun.java2d.uiScale", "100%") ;

        String osName = System.getProperty("os.name").toLowerCase() ;

        // Racism section
        if (osName.startsWith("windows")) {
            System.setProperty("java -Dsun.java2d.directx", "True") ;
        }
        else if (osName.startsWith("linux")) {
            System.setProperty("java -Dsun.java2d.opengl", "True") ;
        }
        else {
            System.out.println("OS not Supported") ;
        }   
        
        // Launching the App
        App app = new App();
        app.setVisible(true);
    }

    //#endregion

    //#region STATIC VARIABLES

    /**
     * The minimal width of the application's screen
     */
    public static final int MIN_APP_SCREEN_WIDTH = 1200;

    /**
     * The minimal height of the application's screen
     */
    public static final int MIN_APP_SCREEN_HEIGHT = 900;

    /**
     * THE COLOR OF THE APP (not really yellow but quand même)
     */
    public static final Color KINDAYELLOW = new Color(242, 219, 7);

    /**
     * The default Bold font for the entire App
     */
    public static final Font KINDABOLD = new Font("Arial", Font.BOLD, 20);

    /**
     * The default Bold font for JCheckBox
     */
    public static final Font KINDACHEKBOX = new Font("Arial", Font.BOLD, 18);

    /**
     * The default Font for the entire App
     */
    public static final Font KINDANORMAL = new Font("Arial", Font.CENTER_BASELINE, 16);

    /**
     * The default italic font
     */
    public static final Font KINDANOBLE = new Font("Arial", Font.ITALIC, 25);

    /**
     * The default Title font for Menu
     */
    public static final Font KINDATITLE = new Font("Arial", Font.BOLD, 26);

    //#endregion

    //#region ATTRIBUTES

    // SCREENS

    /**
     * Logo of the Application
     */
    private Image logo = new ImageIcon("./icons/logoJaune.jpg").getImage();

    /**
     * Page of selection of import file
     * It's the first that you see when you open the app
     */
    private NImportScreen importScreen;

    /**
     * The principal panel of the App, where the map is located, the graph
     * and how to change them, thanks to the different menus
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
    @SuppressWarnings("unused")
    private FlightsIntersectionGraph fig;

    /**
     * the TestGraph that loads testGraphs files
     */
    private TestGraph testGraph ;

    /**
     * PanelCreator object used to render the graph
     */
    private PanelCreator graphRenderer ;

    //#endregion

    //#region CONSTRUCTORS

    /**
     * Creates a new App. It means one. Because there is only one App.
     * 
     * @author Luc le Manifik
     */
    App() {
        App.app = this;

        // Basic configuration
        this.setTitle("Plane AIR");
        this.setSize(MIN_APP_SCREEN_WIDTH, MIN_APP_SCREEN_HEIGHT);
        this.setMinimumSize(new Dimension(MIN_APP_SCREEN_WIDTH, MIN_APP_SCREEN_HEIGHT));
        logo.getScaledInstance(2, 32, Image.SCALE_SMOOTH) ;
        this.setIconImage(logo);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Setup of the App
        this.initAttributes();
        this.addComponents();
    }

    //#endregion

    //#region PRIVATE METHODS

    /**
     * This procedure initalize all the application's attributes.
     * 
     * @author Luc le Manifik
     */
    private void initAttributes() {

        this.airportSet = null; //new AirportSet() ;
        this.fig = null ;
        this.testGraph = null ;

        this.mainScreen = new NMainScreen();
        this.importScreen = new NImportScreen();
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

    //#endregion

    //#region SWITCH METHODS

    /**
     * Switch from importPanel to principalPanel
     * @author GIRAUD Nila - modified by Luc le Manifik
     */
    public void switchToMainScreen() {
        this.mainScreen.addTimePanel(); // Only add the slider if a FIG is charged
        this.mainScreen.getMinGraphPanel().confirmDisplay(graphRenderer);
        this.mainScreen.getGraphInfoPanel().refresh(); 
        this.mainScreen.getMapMenuPanel().refresh();
        this.mainScreen.getGraphMenuPanel().refresh() ;
        this.mainScreen.getInfoPanel().hideInfos();

        this.remove(this.importScreen);
        this.add(this.mainScreen, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
    }

    /**
     * Switch from principalePanel to importPanel
     * @author GIRAUD Nila - modified by Luc le Manifik
     */
    public void switchToImportScreen(){
        
        this.remove(this.mainScreen);
        this.add(this.importScreen, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
    }

    //#endregion

    //-- GETTERS AND SETTERS

    /**
     * Sets the value of the Graph, and instantiates the correct renderer
     * @param graph ({@link planeair.graph.GraphSAE TestGraph}) - 
     * The new Graph rendered on the App
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
     * Refreshes the panel creator with the new graph
     * @param graph the new graph
     */
    public void setGraphRendered(GraphSAE graph) {
        this.graphRenderer = new PanelCreator(graph) ;
    }

    /**
     * Sets the value of the AirportSet
     * 
     * @param as The new {@link planeair.util.AirportSet AirportSet} of the App
     * 
     * @author Luc le Manifik
     */
    public void setAirportSet(AirportSet as) {
        this.airportSet = as;
    }

    /**
     * @return The {@link planeair.graph.graphutil.PanelCreator PanelCreator} 
     * which renders the TestGraph
     */
    public PanelCreator getGraphRenderer() {
        return this.graphRenderer ;
    }

    /**
     * @return the graph currently being rendered 
     */
    public GraphSAE getGraph() {
        if (graphRenderer != null) {
            return graphRenderer.getGraph() ;
        }
        return null ;
    }

    /**
     * @return The {@link planeair.components.NMainScreen principal frame} of the App
     */
    public NMainScreen getMainScreen() {
        return this.mainScreen;
    }

    /**
     * Stars the rendering of the testGraph and doesn't
     * put it in a frame.
     */
    public void initTestGraphRenderer() {
        initTestGraphRenderer(false) ;
    }

    /**
     * Starts the rendering of the testGraph
     * @param inOwnFrame Whether or not the PanelCreator should create
     * a new frame
     */
    public void initTestGraphRenderer(boolean inOwnFrame) {
        this.graphRenderer = new PanelCreator(this.testGraph, inOwnFrame) ;
    }

    /**
     * Returns the importation panel of the App
     * @return The {@link planeair.components.imports.NImportScreen importation panel} of the App
     */
    public NImportScreen getImportScreen() {
        return this.importScreen;
    }

    /**
     * Returns the AirportSet of the App
     * @return The {@link planeair.util.AirportSet AirportSet} which contains all the imported Airports
     */
    public AirportSet getAirportSet() {
        return this.airportSet;
    }
}
