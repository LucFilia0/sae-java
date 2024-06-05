package planeair.components.imports;

// Import of SWING composants
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JOptionPane;

// Import of AWT composants
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.Box;

// Import of Layout
import java.awt.FlowLayout;
import javax.swing.BoxLayout;

import planeair.App;
import planeair.components.buttons.NButtonImportFileApp;
import planeair.components.buttons.NFilledButton;
import planeair.exceptions.InvalidFileFormatException;
import planeair.graph.TestGraph;
import planeair.util.DataImportation;

/**
 * Creates a JPanel of importation for Flights and Airports4
 * @author GIRAUD Nila
 */
public class NButtonImportPanelApp extends JPanel {

    // CONSTANTS

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

    // ATTRIBUTES

    /**
     * Border Title for know what to do
     */
    TitledBorder border = new TitledBorder("Title");

    /**
     * Choice button
     * Graph
     * Location : second button in the center of the frame, right to Flight/Airport one
     */
    private NButtonImportFileApp choiceGraphImportation = new NButtonImportFileApp("<html>Importer un fichier <br> graph-test</html>");

    /**
     * Choice button
     * Vols/Aeroports
     * Location : first button in the center of the frame, left to graph one
     */
    private NButtonImportFileApp choiceFlightImportation = new NButtonImportFileApp("<html>Importer des fichiers <br> vols/aeroports</html>");
    
    /**
     * Importation button
     * vol-test.csv
     * Location : after pushing the button buttonAirport
     */
    private NButtonImportFileApp buttonFlightFileSelection = new NButtonImportFileApp("vol-test.csv");
    /**
     * Importation Button for Flights
     * aeroport.txt
     * Location : after pushing the button choiceFlight
     */
    private NButtonImportFileApp buttonAirportFileSelection = new NButtonImportFileApp("aeroport.csv");

    /**
     * The panel which their there is buttons 
     * Location : center of the frame 
     * What the panel contain depends of what you want to import
     */
    private JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    /**
     * Panel for returnBack + valideAirport
     * FlowLayout : CENTER
     */
    private JPanel panelReturnConfirmAir = new JPanel(new FlowLayout(FlowLayout.CENTER));
    /**
     * Button for valided aerorort
     * Location : below buttonAirport
     */
    private NFilledButton confirmAirports;
    /**
     * Button for return back to the step the user were
     */
    private NFilledButton returnBackAirports;

    /**
     * Panel for returnBack + valideFlight
     * FlowLayout : CENTER
     */
    private JPanel panelReturnConfirmFlight = new JPanel(new FlowLayout(FlowLayout.CENTER));
    /**
     * Button for valide vols
     * Location : below buttonFlight
     */
    private NFilledButton confirmFlights;
    /**
     * Button for annule return back to the step the user is
     */
    private NFilledButton returnBackFlights;

    /**
     * Test pour rajouter un boutton valider au début
     * Location : below buttons choice
     */
    private NFilledButton confirmStart;

    /**
     * Import the Frame of the App
     */
    private App app;
    
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

        this.initComponents();
        this.placeComponents();
        this.addComponents();
        this.addEvents();
    }

    /**
     * Initiates all the components of the NButtonImportPanelApp
     * 
     * @author Luc le Manifik
     */
    private void initComponents() {

        this.setBackground(App.KINDAYELLOW);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5,true));

        this.buttonsPanel.setBackground(App.KINDAYELLOW);
        this.panelReturnConfirmAir.setBackground(App.KINDAYELLOW);
        this.panelReturnConfirmFlight.setBackground(App.KINDAYELLOW);

        this.confirmStart.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     * Places all the components
     */
    private void placeComponents() {

        this.setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));
    }

    /**
     * Method adding components on the Panel
     */
    private void addComponents() {

        buttonsPanel.add(choiceFlightImportation);
        buttonsPanel.add(choiceGraphImportation);

        panelReturnConfirmAir.add(returnBackAirports);
        panelReturnConfirmAir.add(confirmAirports);

        panelReturnConfirmFlight.add(returnBackFlights);
        panelReturnConfirmFlight.add(confirmFlights);

        this.add(buttonsPanel);
        this.add(confirmStart);

        this.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    /**
     * Add events for the frame
     * Importation and return back
     */
    private void addEvents(){

        confirmStart.addActionListener((ActionEvent e) -> {
            this.app.switchToMainScreen(); 
        });
        
        choiceFlightImportation.addActionListener((ActionEvent e) -> {
            buttonsPanel.removeAll();
            this.remove(confirmStart);
            buttonsPanel.add(buttonAirportFileSelection);
            this.add(panelReturnConfirmAir);
            panelReturnConfirmAir.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.app.setVisible(true);

        });
    
        choiceGraphImportation.addActionListener((ActionEvent e) -> {

            NFileChooser fileChooser = new NFileChooser(this.app, NFileChooser.GRAPH_FILE);
            fileChooser.userImportFile();

            if(!fileChooser.getFile().equals(null)) {
                try {
                    this.app.setTestGraph(new TestGraph(fileChooser.getFile().getName())) ;
                    DataImportation.importTestGraphFromFile(this.app.getTestGraph(), fileChooser.getFile(), false);
                    initDefaultGraphImportation() ;
                    this.app.getPrincFrame().getMinGraphPanel().addGraphToPanel(this.app.getTestGraphRenderer()) ;
                    System.out.println(this.app.getTestGraph().getKMax());
                    this.app.getPrincFrame().getMenuGraphPanel().setAltitudeValues(this.app.getTestGraph().getKMax()) ;
                }catch(InvalidFileFormatException | FileNotFoundException error) {
                    JOptionPane.showMessageDialog(null, error.getMessage(),"Erreur d'importation", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    
        confirmAirports.addActionListener((ActionEvent e) -> {
            buttonsPanel.removeAll();
            this.remove(confirmStart);
            buttonsPanel.add(buttonFlightFileSelection);
            this.remove(panelReturnConfirmAir);
            this.add(panelReturnConfirmFlight);
            panelReturnConfirmFlight.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.app.setVisible(true);
        });

        returnBackAirports.addActionListener((ActionEvent e) -> {
            resetPanel(panelReturnConfirmAir, buttonAirportFileSelection);
            this.app.setVisible(true);
        });
    
        confirmFlights.addActionListener((ActionEvent e) -> {
            this.resetPanel(panelReturnConfirmFlight, buttonFlightFileSelection);
            this.app.switchToMainScreen();
            this.app.getPrincFrame().initMap();
        });
    
        returnBackFlights.addActionListener((ActionEvent e) -> {
            buttonsPanel.removeAll();
            this.remove(panelReturnConfirmFlight);
            this.remove(buttonFlightFileSelection);

            buttonsPanel.add(buttonAirportFileSelection);
            this.add(panelReturnConfirmAir);
            panelReturnConfirmAir.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.repaint();
        });
    
        buttonAirportFileSelection.addActionListener((ActionEvent e) -> {
            
            NFileChooser fileChooser = new NFileChooser(this.app, NFileChooser.AIRPORT_FILE);

            try {
                fileChooser.userImportFile();
                if(!fileChooser.getFile().equals(null)) {

                    DataImportation.importAirportsFromFile(this.app.getAirportSet(), this.app.getFig(), fileChooser.getFile());
                    System.out.println("Airport imported");
                }
            }catch(InvalidFileFormatException | FileNotFoundException error) {
                JOptionPane.showMessageDialog(null, error.getMessage(),"Erreur d'importation", JOptionPane.ERROR_MESSAGE);
            }   

        } );
    
        buttonFlightFileSelection.addActionListener((ActionEvent e) -> {
    
            NFileChooser fileChooser = new NFileChooser(this.app, NFileChooser.FLIGHT_FILE);
        
            try {
                fileChooser.userImportFile();
                if(!fileChooser.getFile().equals(null)) {

                    DataImportation.importFlightsFromFile(this.app.getAirportSet(), this.app.getFig(), fileChooser.getFile(), this.app.getTimeSecurity());
                    System.out.println("Flight imported");
                }
            }catch(InvalidFileFormatException | FileNotFoundException error) {
                JOptionPane.showMessageDialog(null, error.getMessage(),"Erreur d'importation", JOptionPane.ERROR_MESSAGE);
            }
    
        });
    } 
 
    /**
     * Restart the Panel for when we will open it again
     */
    public void resetPanel(JPanel panel, JButton button){
        buttonsPanel.removeAll();
        this.remove(panel);
        this.remove(button);

        buttonsPanel.add(choiceFlightImportation);
        buttonsPanel.add(choiceGraphImportation);
        this.add(buttonsPanel);
        this.add(confirmStart);
        confirmStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    public void initDefaultGraphImportation() {
        this.app.getPrincFrame().getMinGraphPanel().addGraphToPanel(this.app.getTestGraphRenderer()) ;
        this.app.getPrincFrame().getMenuGraphPanel().setAltitudeValues(this.app.getTestGraph().getKMax()) ;
    }
}
