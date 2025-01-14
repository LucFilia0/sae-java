package planeair.components.imports;

//#region IMPORT
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.Box;

import planeair.App;

import planeair.components.buttons.NImportFileButton;
import planeair.components.menu.infos.NGraphInfoPanel;
import planeair.components.buttons.NFilledButton;

import planeair.exceptions.InvalidFileFormatException;

import planeair.graph.coloring.ColoringDSATUR;
import planeair.graph.coloring.ColoringUtilities;
import planeair.graph.graphtype.FlightsIntersectionGraph;
import planeair.graph.graphtype.TestGraph;

import planeair.importation.FIGImportation;
import planeair.importation.TestGraphImportation;

import planeair.util.AirportSet;

import java.io.FileNotFoundException;
//#endregion

/**
 * Creates a JPanel of importation for Flights and Airport
 * @author GIRAUD Nila
 */
public class NImportButtonPanel extends JPanel {

    //#region STATIC

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
     * The default String which is prompted on the confirmation buttons
     */
    public static final String NEXT_MESSAGE = "Suivant";

    /**
     * The default message which is prompted on the rejection buttons
     */
    public static final String REJECT_MESSAGE = "Annuler";
    //#endregion

    //#region CHOOSING THE TYPE OF IMPORTATION
    /**
     * Choice button
     * Graph
     * Location : second button in the center of the frame, right to Flight/Airport one
     */
    private NImportFileButton choiceGraphImportation = 
        new NImportFileButton("<html>Importer<br>Graph-Test</html>");

    /**
     * Choice button
     * Vols/Aeroports
     * Location : first button in the center of the frame, left to graph one
     */
    private NImportFileButton choiceFlightImportation = 
        new NImportFileButton("<html>Importer<br>Vols / Aeroports</html>");
    
    /**
     * Importation button for Airports
     * aeroport.txt
     * Location : after pushing the button choiceFlight
     */
    private NImportFileButton buttonAirportFileSelection = 
        new NImportFileButton("Fichier des aéroports");
    /**
     * Importation button for Flights
     * vol-test.csv
     * Location : after pushing the button buttonAirport
     */
    private NImportFileButton buttonFlightFileSelection = 
        new NImportFileButton("Fichier des vols");
    //#endregion

    /**
     * The panel which their there is buttons 
     * Location : center of the frame 
     * What the panel contain depends of what you want to import
     */
    private JPanel buttonsPanel = 
        new JPanel(new FlowLayout(FlowLayout.CENTER));

    /**
     * Panel for returnBack + valideAirport
     * FlowLayout : CENTER
     */
    private JPanel panelReturnConfirmAir = 
        new JPanel(new FlowLayout(FlowLayout.CENTER));
    /**
     * Button for valided aerorort
     * Location : below buttonAirport
     */
    private NFilledButton NextAirports = 
        new NFilledButton(NImportButtonPanel.NEXT_MESSAGE);
    /**
     * Button for return back to the step the user were
     */
    private NFilledButton returnBackAirports = 
        new NFilledButton(NImportButtonPanel.REJECT_MESSAGE);

    /**
     * Panel for returnBack + valideFlight
     * FlowLayout : CENTER
     */
    private JPanel panelReturnConfirmFlight = 
        new JPanel(new FlowLayout(FlowLayout.CENTER));
    /**
     * Button for valide vols
     * Location : below buttonFlight
     */
    private NFilledButton NextFlights = 
        new NFilledButton(NImportButtonPanel.NEXT_MESSAGE);
    /**
     * Button for annule return back to the step the user is
     */
    private NFilledButton returnBackFlights = 
        new NFilledButton(NImportButtonPanel.REJECT_MESSAGE);

    /**
     * Test pour rajouter un boutton valider au début
     * Location : below buttons choice
     */
    private NFilledButton NextStart = 
        new NFilledButton(NImportButtonPanel.NEXT_MESSAGE);

    /**
     * Label for error message
     */
    private JLabel errorLabel = new JLabel(" ");

    //#region BOOLEANS

    /**
     * Checks if the Airports are imported
     */
    private boolean airportsImported;

    /**
     * Checks if the Flights are imported
     */
    private boolean flightsImported;

    //#endregion

    // VERIFICATIONS
    
    /**
     * Create an Import panel
     * In the constructor you have to specify the number of button
     * 1 for aeroport
     * 2 for " + vols
     *
     * @author GIRAUD Nila
     */
    public NImportButtonPanel(){

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

        this.airportsImported  = false;
        this.flightsImported = false;

        this.setBackground(App.KINDAYELLOW);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5,true));

        this.buttonsPanel.setBackground(App.KINDAYELLOW);
        this.panelReturnConfirmAir.setBackground(App.KINDAYELLOW);
        this.panelReturnConfirmFlight.setBackground(App.KINDAYELLOW);

        this.NextStart.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.errorLabel.setFont(App.KINDANORMAL);
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
        panelReturnConfirmAir.add(NextAirports);

        panelReturnConfirmFlight.add(returnBackFlights);
        panelReturnConfirmFlight.add(NextFlights);

        this.add(buttonsPanel);
        this.add(NextStart);

        this.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    /**
     * Add events for the frame
     * Importation and return back
     */
    private void addEvents(){

        // First button : Step directly in the NMainScreen
        NextStart.addActionListener((ActionEvent e) -> {
            if (App.app.getGraphRenderer() != null) {
                if(App.app.getMainScreen().getMap() != null && App.app.getGraph() instanceof TestGraph) {
                    App.app.getMainScreen().getMap().clearAll();
                }else if(App.app.getMainScreen().getMap() != null && App.app.getGraph() instanceof FlightsIntersectionGraph) {
                    App.app.getMainScreen().initMap();
                    App.app.getMainScreen().getTimePanel().getSliderTime().refreshTime();
                }
            }
            App.app.switchToMainScreen();
        });

        // Button to choose the TestGraph's File : Open JFileChooser
        choiceGraphImportation.addActionListener((ActionEvent e) -> {

            NFileChooser fileChooser = new NFileChooser(NFileChooser.GRAPH_FILE);
            fileChooser.userImportFile();

            if(fileChooser.getFile() != null) {
                try {
                    App.app.setGraph(new TestGraph(fileChooser.getFile().getName())) ;
                    TestGraphImportation.importTestGraphFromFile((TestGraph)App.app.getGraph(), 
                        fileChooser.getFile(), false);
                    App.app.setGraphRendered(App.app.getGraph());
                        
                    this.flightsImported = false;
                    this.airportsImported = false;
                    
                    App.app.getMainScreen().initGraphBottomPanel();
                        
                    initDefaultGraphImportation(App.app.getMainScreen().getGraphInfoPanel()) ;
                }catch(InvalidFileFormatException | FileNotFoundException error) {
                    this.errorLabel.setText(error.getMessage());
                    JOptionPane.showMessageDialog(null,errorLabel,"Erreur d'importation", JOptionPane.ERROR_MESSAGE);
                    fileChooser.setFile(null);
                }
            }
         });

        // Choose Flights and Airports importation
        choiceFlightImportation.addActionListener((ActionEvent e) -> {

            // In Garbage Collector we trust 🙌
            App.app.setAirportSet(new AirportSet());

            this.airportsImported = false;
            this.flightsImported = false;

            buttonsPanel.removeAll();
            this.remove(NextStart);
            buttonsPanel.add(buttonAirportFileSelection);
            this.add(panelReturnConfirmAir);
            panelReturnConfirmAir.setAlignmentX(Component.CENTER_ALIGNMENT);
            App.app.setVisible(true);

        });

        // Button to import Airports' File : Open JFileChooser
        buttonAirportFileSelection.addActionListener((ActionEvent e) -> {
            
            NFileChooser fileChooser = new NFileChooser(NFileChooser.AIRPORT_FILE);

            try {
                fileChooser.userImportFile();
                if(fileChooser.getFile() != null) {
                    FIGImportation.importAirportsFromFile(App.app.getAirportSet(), fileChooser.getFile());
                    this.airportsImported = true;
                }
            }catch(InvalidFileFormatException | FileNotFoundException error) {
                this.errorLabel.setText(error.getMessage());
                JOptionPane.showMessageDialog(App.app, errorLabel,
                    "Erreur d'importation", JOptionPane.ERROR_MESSAGE);
                fileChooser.setFile(null);
            }   

        });

        // Button to import Flights' File : Open JFileChooser
        buttonFlightFileSelection.addActionListener((ActionEvent e) -> {
    
            NFileChooser fileChooser = new NFileChooser(NFileChooser.FLIGHT_FILE);
        
            try {
                fileChooser.userImportFile();
                if(fileChooser.getFile() != null) {
                    App.app.setGraph(new FlightsIntersectionGraph(fileChooser.getFile().getName())) ;
                    FIGImportation.importFlightsFromFile(App.app.getAirportSet(), 
                        (FlightsIntersectionGraph)App.app.getGraph(), fileChooser.getFile());
                                        
                    ColoringDSATUR.coloringDsatur(App.app.getGraph()) ;
                    ColoringUtilities.setGraphStyle(App.app.getGraph(), App.app.getGraph().getNbColors()) ;
                    App.app.getGraph().setKMax(App.app.getGraph().getNbColors()) ;
                    App.app.setGraphRendered(App.app.getGraph());
                    
                    App.app.getMainScreen().initGraphBottomPanel() ;
                    this.initDefaultGraphImportation(App.app.getMainScreen().getGraphInfoPanel());
                    this.flightsImported = true;
                }
            }catch(InvalidFileFormatException | FileNotFoundException error) {
                this.errorLabel.setText(error.getMessage());
                JOptionPane.showMessageDialog(null,errorLabel,"Erreur d'importation", JOptionPane.ERROR_MESSAGE);
                fileChooser.setFile(null);
            }
    
        });
    
        NextAirports.addActionListener((ActionEvent e) -> {
            if(this.airportsImported) {
                buttonsPanel.removeAll();
                this.remove(NextStart);
                buttonsPanel.add(buttonFlightFileSelection);
                this.remove(panelReturnConfirmAir);
                this.add(panelReturnConfirmFlight);
                panelReturnConfirmFlight.setAlignmentX(Component.CENTER_ALIGNMENT);
                App.app.setVisible(true);
            }
        });

        returnBackAirports.addActionListener((ActionEvent e) -> {

            this.airportsImported = false;
            this.flightsImported = false;
            App.app.setGraph(null);

            resetPanel(panelReturnConfirmAir, buttonAirportFileSelection);
            App.app.setVisible(true);
        });
    
        NextFlights.addActionListener((ActionEvent e) -> {
            if(this.airportsImported && this.flightsImported) {
                this.resetPanel(panelReturnConfirmFlight, buttonFlightFileSelection);
                App.app.switchToMainScreen();
                App.app.getMainScreen().initMap();
                App.app.getMainScreen().getTimePanel().getSliderTime().resetSlider();
            }
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
    
    } 

    // METHODS
 
    /**
     * Restarts the Panel for when we will open it again
     * 
     * @param panel The panel contained
     * @param button The button contained
     */
    public void resetPanel(JPanel panel, JButton button){
        buttonsPanel.removeAll();
        this.remove(panel);
        this.remove(button);

        buttonsPanel.add(choiceFlightImportation);
        buttonsPanel.add(choiceGraphImportation);
        this.add(buttonsPanel);
        this.add(NextStart);
        NextStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    /**
     * Initializes the panel and statistics linked to this Graph
     * @param infoGraph Panel which will contain the statistics
     */
    public void initDefaultGraphImportation(NGraphInfoPanel infoGraph) {
        App.app.getMainScreen().getMinGraphPanel()
            .addGraphToPanel(App.app.getGraphRenderer()) ;
        if (App.app.getGraphRenderer() != null) {
            App.app.getMainScreen().getGraphMenuPanel()
                .initAllComboBoxes(App.app.getGraph().getKMax(), true) ;
            App.app.getMainScreen().getGraphMenuPanel().setLastAlgoSelected(null) ;
            infoGraph.computeGraphStats() ;
        }
    }
}
