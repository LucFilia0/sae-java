package planeair.components.menu;

// import SWING components
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

// import AWT components
import java.awt.Dimension;
import java.awt.Font;

// import LAYOUT
import java.awt.GridLayout;

import planeair.App;
import planeair.components.mapview.Map;
/**
 * Create a panel Menu for Map
 * Help the user to modified appareance of the Map with JChekBox
 * 1) See lines for Flight
 * 2) See icon of used airport
 * 3) See icon of not used airport
 * 4) See icon of flight, the localization depends of the time
 * 
 * GirdLayout: 
 * LINE : 5
 * COLUMN : 1
 * HGap : 0
 * VGap : 15
 * 
 * @author GIRAUD Nila
 */
public class NMenuMapPanelApp extends JPanel {

    /**
     * JLabel for the title of the Panel NMenuPanelApp
     */
    private JLabel titleMenu = new JLabel("MENU MAP", SwingConstants.CENTER);

    // Flight Lines

    /**
     * The checkBox for setting if flight lines are visible in the Map
     * Represent flight's trajectory
     */
    private JCheckBox flightLines = new JCheckBox("Voir les lignes de vols");


    // Airoport Used

    /**
     * The checkBox for setting if Airport used are visible in the Map (red icon)
     * Their is 2 possibility : the airport is used by a flight, or no, so it's call airport not used
     */
    private JCheckBox airportUsed = new JCheckBox("Voir les aéroports utilisés");


    // Airoport NOT Used
    
    /**
     * The checkBox for setting if Airport not used are visible in the Map (grey icon)
     * Their is 2 possibility : the airport used by a flight, or no, so it's call airport not used
     */
    private JCheckBox airportNotUsed = new JCheckBox("Voir les aéroports utilisés");

    // Flight Icon

    private JCheckBox flightIcon = new JCheckBox("Voir les vols");


    /**
     * Constructor of NMenuPanelApp
     * @param map the used map of the App
     */
    public NMenuMapPanelApp(Map map){

        this.setBackground(App.KINDAYELLOW);
        this.setPreferredSize(new Dimension(225,300));
        this.setLayout( new GridLayout(5,1,0,15));

        // TITLE
        titleMenu.setFont(new Font("Arial", Font.BOLD, 26));
        titleMenu.setBackground(App.KINDAYELLOW);

        // LINE 1
        flightLines.setBackground(App.KINDAYELLOW);
        flightLines.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));

        // LINE 2
        airportUsed.setBackground(App.KINDAYELLOW);
        airportUsed.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));

        // LINE 3
        airportNotUsed.setBackground(App.KINDAYELLOW);
        airportNotUsed.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));

        // LINE 4
        flightIcon.setBackground(App.KINDAYELLOW);
        flightIcon.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));

       //Title Menu
       this.add(titleMenu);

       //Flight Lines
       this.add(flightLines);

       // Airoport Used
       this.add(airportUsed);


       // Airoport NOT Used
       this.add(airportNotUsed);

       //  Flight Icon
       this.add(flightIcon);
    }    
}
