//-- Import Java

import java.io.File;
import javax.swing.* ;
import java.awt.* ;

//-- Import GraphStream

import org.graphstream.graph.Node;
import org.graphstream.stream.thread.ThreadProxyPipe;
import org.graphstream.ui.swing_viewer.util.* ;
import org.graphstream.ui.view.* ;
import org.graphstream.ui.view.util.MouseManager;
import org.graphstream.ui.swing_viewer.* ;
import org.graphstream.algorithm.Toolkit ;

//-- Import Exceptions

import java.io.FileNotFoundException;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.graphicGraph.stylesheet.Color;
import org.graphstream.ui.swing.SwingGraphRenderer;

import exceptions.InvalidCoordinateException;
import exceptions.InvalidTimeException;
import exceptions.ObjectNotFoundException;
import exceptions.InvalidEntryException;

// Import packages

import util.* ;
import graph.* ;
import graph.Renderer;

public class App {
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing") ;
        System.setProperty("sun.java2d.uiScale", "100%") ;

        FlightsIntersectionGraph fig = new FlightsIntersectionGraph("Yep");
        TestGraph testGraph = new TestGraph("hello") ;
        try {
            testGraph.importFromFile(new File("DataTest/graph-test0.txt"));

        }catch(FileNotFoundException fnfe) {
            System.err.println(fnfe);
        }catch(NumberFormatException nfe) {
            System.err.println(nfe);
        }catch(InvalidEntryException iee) {
            System.err.println(iee);
        }
        
        AirportSet as = new AirportSet();

        double timeSecurity = 15;

        
        try {
            as.importAirportsFromFile(new File("DataTest/aeroports.csv"));
            fig.importFlightsFromFile(new File("DataTest/vol-test8.csv"), as, timeSecurity);
            
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
        
        for (Node node : testGraph) {
            node.setAttribute("color", 0) ;
        }
        int[] color = TestGraph.colorGraphRLF(testGraph, "color", testGraph.getKMax()) ;
        TestGraph.setGraphStyle(testGraph, color[0], "color") ;
        
        System.out.println("nb couleurs : " + color[0] + "\nnb conflits : " + color[1]);
        System.out.println("conflits truc : " + TestGraph.testColorationGraph(testGraph, "color")) ;

        color = TestGraph.colorGraphRLF(fig, Flight.LAYER, 100) ;
        TestGraph.setGraphStyle(fig, color[0], Flight.LAYER) ;
        System.out.println("nb couleurs : " + color[0] + "\nnb conflits : " + color[1]);
       
        int[] res = TestGraph.colorGraphRLF(fig, Flight.LAYER, 4) ;
        System.out.println("layers : " + res[0] + "\nconflicts : " + res[1]) ;
        
        // Use this instead of graph.display()
        Renderer r = new Renderer(testGraph) ;
        ViewPanel v = r.getViewPanel() ;

        JFrame frame = new JFrame("Bienvenue chez Plane air") ;
        frame.setVisible(true) ;
        frame.setSize(800, 800) ;
        frame.add(v) ;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        
    }
}