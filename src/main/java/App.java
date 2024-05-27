//-- Import Java

import java.awt.* ;
import java.io.File;
import javax.swing.* ;
import javax.swing.plaf.FontUIResource;

//-- Import GraphStream

import org.graphstream.graph.Node;
import org.graphstream.ui.swing_viewer.* ;



import org.graphstream.graph.implementations.* ;
import org.graphstream.graph.* ;

//-- Import Exceptions

import java.io.FileNotFoundException;
import exceptions.InvalidCoordinateException;
import exceptions.InvalidTimeException;
import exceptions.ObjectNotFoundException;
import exceptions.InvalidEntryException;

// Import packages

import util.* ;
import graph.* ;
import composants.*;

public class App {
    public static void main(String[] args) {
        /*System.setProperty("org.graphstream.ui", "swing") ;
        System.setProperty("sun.java2d.uiScale", "100%") ;

        FlightsIntersectionGraph fig = new FlightsIntersectionGraph("Yep");
        TestGraph testGraph = new TestGraph("hello") ;
        try {
            testGraph.importFromFile(new File("DataTest/graph-test19.txt"));

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

        Graph testGraph2 = Graphs.clone(testGraph) ;
        int[] c = Coloration.colorGraphRLF(testGraph, "color", testGraph.getKMax()) ;
        System.out.println("nb couleurs RLF : " + c[0] + "\n nb conflits RLF : " + c[1]) ;
        Coloration.setGraphStyle(testGraph, c[0], "color") ;
        
        c = Coloration.ColorationDsatur(testGraph2, testGraph.getKMax(), "color") ;
        System.out.println("nb couleurs DSAT : " + c[0] + "\n nb conflits DSAT : " + c[1]) ;
        Coloration.setGraphStyle(testGraph2, c[0], "color") ;
        
        // Use this instead of graph.display()
        PanelCreator r = new PanelCreator(testGraph) ;
        PanelCreator r2 = new PanelCreator(testGraph2) ;
        ViewPanel v = r.getViewPanel() ;
        ViewPanel v2 = r2.getViewPanel() ;

        JFrame frame = new JFrame("Bienvenue chez Plane air") ;
        frame.setVisible(true) ;
        frame.setSize(1200, 800) ;
        frame.setLayout(new GridLayout(1,2)) ;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;

        // p1
        JPanel p1 = new JPanel() ;
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS)) ;
        JLabel l1 = new JLabel("RLF") ;
        l1.setFont(new FontUIResource(l1.getFont().getName(), l1.getFont().getStyle(), 30)) ;
        l1.setAlignmentX(JLabel.CENTER_ALIGNMENT) ;
        p1.add(l1) ;
        p1.add(v) ;
        frame.add(p1) ;

        // p2
        JPanel p2 = new JPanel() ;
        p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS)) ;
        JLabel l2 = new JLabel("DSATUR") ;
        l2.setFont(new FontUIResource(l2.getFont().getName(), l2.getFont().getStyle(), 30)) ;
        l2.setAlignmentX(JLabel.CENTER_ALIGNMENT) ;
        p2.add(l2) ;
        p2.add(v2) ;
        frame.add(p2) ;
        
    }*/


    /*NPrincipaleFrameApp fenPrin = new NPrincipaleFrameApp();
    //fenPrin.addCardPanel();
    fenPrin.addComposants();
    fenPrin.addEvents();
    fenPrin.setVisible(true);*/

    try { 
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.Windows"); 
    } catch(Exception ignored){}

    NHomePage homePage = new NHomePage();
    homePage.setVisible(true);
    homePage.addComposants();

}

}