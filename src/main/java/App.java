//-- Import Java

import java.io.File;
import graph.FlightsIntersectionGraph;
import graph.TestGraph;
import util.AirportSet;

//-- Import GraphStream

import org.graphstream.graph.Node;

//-- Import Exceptions

import java.io.FileNotFoundException;

import org.graphstream.ui.graphicGraph.stylesheet.Color;

import exceptions.InvalidCoordinateException;
import exceptions.InvalidTimeException;
import exceptions.ObjectNotFoundException;
import exceptions.InvalidEntryException;

public class App {
    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");

        FlightsIntersectionGraph fig = new FlightsIntersectionGraph("Yep");
        TestGraph testGraph = new TestGraph("hello") ;
        try {
            testGraph.importFromFile(new File("DataTest/graph-test15.txt"));

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
            fig.importFlightsFromFile(new File("DataTest/vol-test2.csv"), as, timeSecurity);
            
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
        int[] color = TestGraph.colorGraphRLF(testGraph) ;
        StringBuffer stylesheet = new StringBuffer("node {size : 20px ;}") ;
        
        Color[] colorTab = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY
        , Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW} ;
        
        // Scuffed way to show colors on the graph
        for (int i = 0 ; i < color[0] ; i++) {
            Color currentColor = colorTab[i % colorTab.length] ;
            String str = "rgb(" + currentColor.getRed() + ',' + currentColor.getGreen() + ',' + currentColor.getBlue() + ')' ;
            stylesheet.append("node.color" + (i+1) + "{fill-color : " + str + " ; }\n") ;
        }
        
        testGraph.setAttribute("kMax", testGraph.getKMax());
        testGraph.setAttribute("ui.stylesheet", stylesheet.toString());
        System.out.println("nb couleurs : " + color[0] + "\nnb conflits : " + color[1]);
        System.out.println("conflits truc : " + TestGraph.testColorationGraph(testGraph)) ;
        testGraph.display() ;
    }
}