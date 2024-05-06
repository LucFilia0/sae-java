//-- Import Java

import java.awt.* ;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.* ;
import javax.swing.plaf.FontUIResource;

import org.graphstream.ui.swing_viewer.* ;
import org.graphstream.graph.implementations.* ;

//-- Import GraphStream
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

public class App {
    private static Graph fig;

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing") ;
        System.setProperty("sun.java2d.uiScale", "100%") ;

        FlightsIntersectionGraph fig = new FlightsIntersectionGraph("Yep");
        
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

        int[] c = Coloration.colorWelshPowell(fig, Flight.LAYER, 4) ;
        System.out.println("nb couleurs Welsh & Powell : " + c[0] + "\n nb conflits Welsh & Powell : " + c[1]) ;
        Coloration.setGraphStyle(fig, c[0], Flight.LAYER) ;
        
        // Use this instead of graph.display()
        PanelCreator r = new PanelCreator(fig) ;
        ViewPanel v = r.getViewPanel() ;

        JFrame frame = new JFrame("Bienvenue chez Plane air") ;
        frame.setVisible(true) ;
        frame.setSize(1200, 800) ;
        frame.setLayout(new BorderLayout()) ;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        JPanel p = new JPanel() ;
        p.setLayout(new GridLayout(1, 2)) ;
        frame.add(p, BorderLayout.CENTER) ;

        // p1
        JPanel p1 = new JPanel() ;
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS)) ;
        JLabel l1 = new JLabel("Welsh & Powell") ;
        l1.setFont(new FontUIResource(l1.getFont().getName(), l1.getFont().getStyle(), 30)) ;
        l1.setAlignmentX(JLabel.CENTER_ALIGNMENT) ;
        p1.add(l1) ;
        p1.add(v) ;

        // p2
        JPanel p2 = new JPanel() ;
        p2.setLayout(new GridLayout(3, 1)) ;
        JButton button1 = new JButton("Show Flights currently in the air") ;
        button1.addActionListener(e -> fig.showFlightsAtATime(FlightTime.getCurrentTime()));
        p2.add(button1) ;
        
        JButton button2 = new JButton("Show all flights on the first layer") ;
        button2.addActionListener(e -> fig.showSameLayerFlights(1));
        p2.add(button2) ;

        JButton button3 = new JButton("Show all flights") ;
        button3.addActionListener(e -> fig.showAllFlights()) ;
        p2.add(button3) ;

        p.add(p1) ;
        p.add(p2) ;
    }
}