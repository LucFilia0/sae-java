package planeair.components;

// Import of SWING composants
import javax.swing.JPanel;
import javax.swing.JButton;

// Import of AWT composants
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;

// Import of Layout
import java.awt.FlowLayout;
import javax.swing.BoxLayout;

import planeair.App;

/**
 * Create a JPanel of importation for vols and aeroports
 */
public class NButtonImportPanelApp extends JPanel {

    /**
     * CONSTANT for know what type of file you have to import
     * This one is for airoport.txt
     */
    public static final int AIRPORT_FILE = 0;
    /**
     * CONSTANT for know what type of file you have to import
     * This one is for graph-test.txt
     */
    public static final int TEST_GRAPH_FILE = 1;
    /**
     * CONSTANT for know what type of file you have to import
     * This one is for vol-test.csv
     */
    public static final int FLIGHT_FILE = 12;

    /**
     * Button de choix
     * Vols/Aeroports
     * Location : first button in the center of the frame, left to graph one
     */
    JButton choiceFlight = new JButton("<html>Importer des fichiers <br> vols/aeroports</html>");
    /**
     * Button de choix
     * Graph
     * Location : second button in the center of the frame, right to vol/aeroprt one
     */
    JButton choiceGraph = new JButton("<html>Importer un fichier <br> graphes.csv</html>");
    
    /**
     * Button Importation
     * vol-test.csv
     * Location : after pushing the button buttonAirport
     */
    JButton buttonFlight = new JButton("vol-test.csv");
    /**
     * Button Importation for flight
     * aeroport.txt
     * Location : after pushing the button choiceFlight
     */
    JButton buttonAirport = new JButton("aeroport.csv");
    /**
     * Button Importation
     * graph-test.txt
     * Location : after pushing the button choiceGraph
     */
    JButton buttonGraph = new JButton("graph-test.csv");

    /**
     * The panel which their there is buttons 
     * Location : center of the frame 
     * What the panel contain depends of what you want to import
     */
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    /**
     * Button for valided aerorort
     * Location : below buttonAirport
     */
    JButton valideAr = new JButton("Valider");

    /**
     * Button for valide vols
     * Location : below buttonFlight
     */
    JButton valideFlight = new JButton("Valider");


    /**
     * Test pour rajouter un boutton valider au début
     * Location : below buttons choice
     */
    JButton valideStart = new JButton("Valider");

    /**
     * Import the Frame of the App
     */
    App app;
    
    /**
     * Create an Import panel
     * In the constructor you have to specify the number of button
     * 1 for aeroport
     * 2 for " + vols
     * @param framePrinc The object HomePage where the panel is put
     * @author GIRAUD Nila
     */
    public NButtonImportPanelApp(App app){
        this.app = app;

        this.setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));
        this.setBackground(Color.YELLOW);

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5,true));

        choiceFlight.setForeground(Color.WHITE);
        choiceFlight.setPreferredSize(new Dimension(200,125));
        choiceFlight.setBackground(Color.BLACK);

        buttonAirport.setForeground(Color.WHITE);
        buttonAirport.setPreferredSize(new Dimension(200,125));
        buttonAirport.setBackground(Color.BLACK);

        buttonFlight.setForeground(Color.WHITE);
        buttonFlight.setPreferredSize(new Dimension(200,125));
        buttonFlight.setBackground(Color.BLACK);


        choiceGraph.setForeground(Color.WHITE);
        choiceGraph.setPreferredSize(new Dimension(200,125));
        choiceGraph.setBackground(Color.BLACK);

        buttonsPanel.add(choiceFlight);
        buttonsPanel.add(choiceGraph);
        buttonsPanel.setBackground(Color.YELLOW);

        valideAr.setForeground(Color.WHITE);
        valideAr.setPreferredSize(new Dimension(50,40));
        valideAr.setBackground(Color.BLACK);

        valideFlight.setForeground(Color.WHITE);
        valideFlight.setPreferredSize(new Dimension(50,40));
        valideFlight.setBackground(Color.BLACK);

        valideStart.setForeground(Color.WHITE);
        valideStart.setPreferredSize(new Dimension(50,40));
        valideStart.setBackground(Color.BLACK);

        this.add(buttonsPanel);
        this.add(valideStart);
        valideStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0, 5)));

    }

    /**
     * Add events for the frame
     * Importation and return back
     */
    public void addEvents(){
        valideStart.addActionListener((ActionEvent e) -> {
        this.app.addBodyPanelPrinc();
            
        });

        choiceFlight.addActionListener((ActionEvent e) -> {
            buttonsPanel.removeAll();
            this.remove(valideStart);
            buttonsPanel.add(buttonAirport);
            this.add(valideAr);
            valideAr.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.add(Box.createRigidArea(new Dimension(0, 5)));
            this.app.setVisible(true);

        });

        choiceGraph.addActionListener((ActionEvent e) -> {
            NFileChooserForGraphApp fileChooser = new NFileChooserForGraphApp(TEST_GRAPH_FILE);
            if(fileChooser.getIsSelected()){
                this.app.addBodyPanelPrinc();
                this.setVisible(false);
            }
        });

        valideAr.addActionListener((ActionEvent e) -> {
            buttonsPanel.removeAll();
            this.remove(valideStart);
            this.remove(valideAr);
            buttonsPanel.add(buttonFlight);
            this.add(valideFlight);
            valideFlight.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.add(Box.createRigidArea(new Dimension(0, 5)));
            this.app.setVisible(true);
        });

        valideFlight.addActionListener((ActionEvent e) -> {
            this.resetPanel();
            this.app.addBodyPanelPrinc();
            
        });

        buttonAirport.addActionListener((ActionEvent e) -> {
        NFileChooserForGraphApp fileChosser = new NFileChooserForGraphApp(AIRPORT_FILE);
        if(fileChosser.getIsSelected()){
            this.setVisible(false);
        }

        } );

        buttonFlight.addActionListener((ActionEvent e) -> {
        NFileChooserForGraphApp fileChosser = new NFileChooserForGraphApp(FLIGHT_FILE);
        if(fileChosser.getIsSelected()){
            this.app.addBodyPanelPrinc();
            this.setVisible(false);
        } });
    } 
 
    /**
     * Restart the Panel for when we will open it again
     */
    public void resetPanel(){
        buttonsPanel.removeAll();
        this.remove(valideFlight);
        this.remove(buttonFlight);

        buttonsPanel.add(choiceFlight);
        buttonsPanel.add(choiceGraph);
        this.add(buttonsPanel);
        this.add(valideStart);
        valideStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
    }
}
