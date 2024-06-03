package planeair;

//-- Import Swing

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import org.graphstream.graph.Node;

//-- Import AWT

import java.awt.BorderLayout;


//-- Import JxMapViewer



//-- Import Java

import java.io.File;
import java.util.LinkedList;
import planeair.graph.Coloration;

//-- Import Plane AIR

import planeair.graph.FlightsIntersectionGraph;
import planeair.graph.PanelCreator;
import planeair.graph.TestGraph;
import planeair.util.AirportSet;
import planeair.util.DataImportation;

//-- Import IHM

import planeair.ihm.Map;

import org.graphstream.ui.swing_viewer.* ;
import org.miv.mbox.Test;

import planeair.components.*;

import org.graphstream.graph.implementations.* ;
import org.graphstream.graph.* ;

//-- Import Exceptions

import java.io.FileNotFoundException;

import planeair.exceptions.InvalidCoordinateException;
import planeair.exceptions.InvalidTimeException;
import planeair.exceptions.ObjectNotFoundException;
import planeair.exceptions.InvalidEntryException;
import planeair.exceptions.InvalidFileFormatException;
import planeair.util.* ;
import planeair.graph.* ;

import planeair.util.* ;
import planeair.graph.* ;


/**
 * This class loads the application.
 * 
 * @author Luc le Manifik
 */
public class App extends javax.swing.JFrame {

    /**
     * The width of the application's screen
     */
    public final static int APPLICATION_SCREEN_WIDTH = 1080;

    /**
     * The height of the application's screen
     */
    public final static int APPLICATION_SCREEN_HEIGHT = 720;

    /**
     * Page of selection of import file
     * It's the first that you see when you open the map
     */
    NImportPanelApp importPanel;

    /**
     * The panel principale of the App, where there is the map, the graph
     * and how change them
     */
    NPrincipalePanelApp framePrinc = new NPrincipalePanelApp(this);

    /**
     * the TestGraph that loads testGraphs files
     */
    private TestGraph testGraph;

    /**
     * The AirportSet which contains all the Airports
     */
    private AirportSet airportSet;

    /**
     * The FIG which contains all the Flights
     */
    private FlightsIntersectionGraph fig;

    /**
     * The time security
     */
    private double timeSecurity = 15;


    App(){

        this.setTitle("Plane AIR | PAGE D'IMPORTATION");
        this.setSize(900,600);

        this.initAttributes();
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        framePrinc.addComposants();
        framePrinc.addEvents();
        
        
        this.add(importPanel);

        this.setVisible(true);
        
        this.setLocationRelativeTo(null);        

    }

    public static void main(String[] args) {
        
        // DON'T TOUCH THAT IT'S VERY IMPORTANT
        System.setProperty("org.graphstream.ui", "swing") ;
        System.setProperty("sun.java2d.uiScale", "100%") ;
        System.setProperty("org.graphstream.ui", "swing") ;

        /* App planeAIR = new App("Plane AIR"); // Such a great name, isn't it ?
        planeAIR.setVisible(true); */

        /* TestGraph tg = new TestGraph("graph") ;
        try {
            tg.importFromFile(new File("data/graph-test2.txt"), false) ;
        }

        catch (Exception e) {
            System.err.println(e) ;
        } */

        /* for (Node n : tg) {
            n.setAttribute("color", 0) ;
        }
        int[] res = Coloration.ColorationDsatur(tg, "color", tg.getKMax()) ;
        Coloration.setGraphStyle(tg,res[0], "color") ;
        PanelCreator renderer = new PanelCreator(tg, true) ;*/

        App app = new App();
        
    }

    /**
     * Switch from importPanel to principalePanel
     */
    public void addBodyPanelPrinc(){
        this.setTitle("Plane AIR");
        this.remove(importPanel);
        this.add(framePrinc);

        this.revalidate();
        this.repaint();
    }

    /**
     * Switch from principalePanel to importPanel
     */
    public void removeBodyPanelPrinc(NPrincipalePanelApp framePrinc){
        
        this.setTitle("Plane AIR | PAGE D'IMPORTATION");
        this.remove(framePrinc);
        this.add(importPanel);

        this.revalidate();
        this.repaint();
    }

    /**
     * This procedure initalize all the application's components.
     * 
     * @author Luc le Manifik
     */
    private void initAttributes() {
        this.airportSet = new AirportSet();
        this.fig = new FlightsIntersectionGraph("FIG");
        this.testGraph = new TestGraph("TestGraph");

        this.framePrinc = new NPrincipalePanelApp(this);
        this.importPanel = new NImportPanelApp(this);

    }

    private void importData() {

        // TEMP
        AirportSet as = new AirportSet();
        FlightsIntersectionGraph fig = new FlightsIntersectionGraph("Waffle");

        double timeSecurity = 15;

        //String testGraphFile = "data/graph-test1.txt";
        String airportsFile = "data/aeroports.txt";
        String flightsFile = "data/vol-test8.csv";
        
        try {
            DataImportation.importAirportsFromFile(as, fig, new File(airportsFile));
            DataImportation.importFlightsFromFile(as, fig, new File(flightsFile), timeSecurity);
            //as.showAllAirports();
        }catch(
            FileNotFoundException |
            NumberFormatException |
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

        System.setProperty("org.graphstream.ui", "swing");
        TestGraph graph = new TestGraph("Hello");

        try {
            DataImportation.importTestGraphFromFile(graph, new File("data/graph-test0.txt"), true);
        }catch(FileNotFoundException | InvalidFileFormatException e) {
            System.err.println(e);
        }

        //System.out.println(graph);

        //graph.display();

        // ============================================================

        FlightsIntersectionGraph fig = new FlightsIntersectionGraph("ok");
        AirportSet as = new AirportSet();

        Map map = new Map();

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(map, BorderLayout.CENTER);

        try {
            DataImportation.importAirportsFromFile(as, fig, new File("data/aeroports.csv"));
            DataImportation.importFlightsFromFile(as, fig, new File("data/vol-test3.csv"), 15);
        }catch(Exception e) {
            System.err.println(e);
        }

        System.out.println(fig);

        

        this.setVisible(true);
    }
    
    public TestGraph getTestGraph() {
        return this.testGraph;
    }

    public AirportSet getAirportSet() {
        return this.airportSet;
    }

    public FlightsIntersectionGraph getFig() {
        return this.fig;
    }

    public double getTimeSecurity() {
        return this.timeSecurity;
    }

    public NPrincipalePanelApp getPrincFrame() {
        return this.framePrinc;
    }

}