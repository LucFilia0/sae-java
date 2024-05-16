//-- Import Java

import java.awt.* ;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.* ;
import javax.swing.plaf.FontUIResource;

import org.graphstream.ui.swing_viewer.* ;
import org.graphstream.ui.view.*;
import org.graphstream.graph.implementations.* ;

//-- Import GraphStream
import org.graphstream.graph.* ;

//-- Import Exceptions

// Import packages

import util.* ;
import graph.* ;

public class App {
    private static Graph fig;

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing") ;
        System.setProperty("sun.java2d.uiScale", "100%") ;

        FlightsIntersectionGraph fig = new FlightsIntersectionGraph("Yep");
        TestGraph tg = new TestGraph("Nope") ;
        try {
            tg.importFromFile(new File("DataTest/graph-test12.txt"));
        }
        catch (Exception e) {
            System.err.println(e) ;
        }
        
        AirportSet as = new AirportSet();

        double timeSecurity = 15;

        
        try {
            as.importAirportsFromFile(new File("DataTest/aeroports.csv"));
            fig.importFlightsFromFile(new File("DataTest/vol-test4.csv"), as, timeSecurity);
            
            //as.showAllAirports();
        }catch(Exception e) {
            System.err.println(e);
        }

        for (Node n : tg) {
            n.setAttribute("color", 0) ;
        }

        int[] c = Coloration.colorWelshPowell(tg, "color", tg.getKMax()) ;
        System.out.println("nb couleurs Welsh & Powell : " + c[0] + "\n nb conflits Welsh & Powell : " + c[1]) ;
        Coloration.setGraphStyle(tg, c[0], "color") ;
        
        // Use this instead of graph.display()
        PanelCreator r = new PanelCreator(tg) ;
        ViewPanel v = r.getViewPanel() ;

        JFrame frame = new JFrame("Bienvenue chez Plane air") ;
        frame.setVisible(true) ;
        frame.setSize(1200, 800) ;
        frame.setLayout(new BorderLayout()) ;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        frame.add(v) ;

        String[] str = {"graph-testX.txt"} ;
        Automatisation.importDataFromFolder("DataTest", str, 'X') ;
    }
}