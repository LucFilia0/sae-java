package planeair.components.imports;

//#region IMPORT
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
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

import planeair.importation.ImportationFIG;
import planeair.importation.ImportationTestGraph;

import planeair.util.AirportSet;

import java.io.FileNotFoundException;
//#endregion

/**
 * Creates a JPanel of importation for Flights and Airports4
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
    public static final String CONFIRM_MESSAGE = "Valider";

    /**
     * The default message which is prompted on the rejection buttons
     */
    public static final String REJECT_MESSAGE = "Annuler";
    //#endregion

    //#region ATTRIBUTS

    /**
     * Border Title for know what to do
     */
    TitledBorder border = new TitledBorder("Title");
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
     * Importation Button for Flights
     * aeroport.txt
     * Location : after pushing the button choiceFlight
     */
    private NImportFileButton buttonAirportFileSelection = 
        new NImportFileButton("Fichier des aéroports");
    /**
     * Importation button
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
    private NFilledButton confirmAirports = 
        new NFilledButton(NImportButtonPanel.CONFIRM_MESSAGE);
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
    private NFilledButton confirmFlights = 
        new NFilledButton(NImportButtonPanel.CONFIRM_MESSAGE);
    /**
     * Button for annule return back to the step the user is
     */
    private NFilledButton returnBackFlights = 
        new NFilledButton(NImportButtonPanel.REJECT_MESSAGE);

    /**
     * Test pour rajouter un boutton valider au début
     * Location : below buttons choice
     */
    private NFilledButton confirmStart = 
        new NFilledButton(NImportButtonPanel.CONFIRM_MESSAGE);

    /**
     * Import the Frame of the App
     */
    private App app;

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
     * @param framePrinc The object HomePage where the panel is put
     * @author GIRAUD Nila
     */
    public NImportButtonPanel(App app){

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

        this.airportsImported  = false;
        this.flightsImported = false;

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

        // First button : Step directly in the NMainScreen
        confirmStart.addActionListener((ActionEvent e) -> {
            if (App.app.getGraphRenderer() != null) {
                if(app.getMainScreen().getMap() != null && this.app.getGraph() instanceof TestGraph) {
                    app.getMainScreen().getMap().clearAll();
                }else if(app.getMainScreen().getMap() != null && this.app.getGraph() instanceof FlightsIntersectionGraph) {
                    app.getMainScreen().initMap();
                    app.getMainScreen().refreshTime();
                }
            }
            this.app.switchToMainScreen();
        });

        // Button to choose the TestGraph's File : Open JFileChooser
        choiceGraphImportation.addActionListener((ActionEvent e) -> {

            NFileChooser fileChooser = new NFileChooser(this.app, NFileChooser.GRAPH_FILE);
            fileChooser.userImportFile();

            if(fileChooser.getFile() != null) {
                try {
                    this.app.setGraph(new TestGraph(fileChooser.getFile().getName())) ;
                    ImportationTestGraph.importTestGraphFromFile((TestGraph)this.app.getGraph(), 
                        fileChooser.getFile(), false);

                    this.flightsImported = false;
                    this.airportsImported = false;
                    
                    app.getMainScreen().initGraphBottomPanel();
                    if (app.getMainScreen().getInfoPanel() != null)
                        app.getMainScreen().getInfoPanel().hideInfos();
                        
                    initDefaultGraphImportation(this.app.getMainScreen().getGraphInfoPanel()) ;
                }catch(InvalidFileFormatException | FileNotFoundException error) {
                    JOptionPane.showMessageDialog(null, error.getMessage(),"Erreur d'importation", JOptionPane.ERROR_MESSAGE);
                    fileChooser.setFile(null);
                }
            }
        });

        // Choose Flights and Airports importation
        choiceFlightImportation.addActionListener((ActionEvent e) -> {

            // In Garbage Collector we trust 🙌
            this.app.setAirportSet(new AirportSet());

            buttonsPanel.removeAll();
            this.remove(confirmStart);
            buttonsPanel.add(buttonAirportFileSelection);
            this.add(panelReturnConfirmAir);
            panelReturnConfirmAir.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.app.setVisible(true);

        });

        // Button to import Airports' File : Open JFileChooser
        buttonAirportFileSelection.addActionListener((ActionEvent e) -> {
            
            NFileChooser fileChooser = new NFileChooser(this.app, NFileChooser.AIRPORT_FILE);

            try {
                fileChooser.userImportFile();
                if(fileChooser.getFile() != null) {
                    ImportationFIG.importAirportsFromFile(this.app.getAirportSet(), fileChooser.getFile());
                    this.airportsImported = true;
                }
            }catch(InvalidFileFormatException | FileNotFoundException error) {
                JOptionPane.showMessageDialog(this.app, error.getMessage(),
                    "Erreur d'importation", JOptionPane.ERROR_MESSAGE);
                fileChooser.setFile(null);
            }   

        });

        // Button to import Flights' File : Open JFileChooser
        buttonFlightFileSelection.addActionListener((ActionEvent e) -> {
    
            NFileChooser fileChooser = new NFileChooser(this.app, NFileChooser.FLIGHT_FILE);
        
            try {
                fileChooser.userImportFile();
                if(fileChooser.getFile() != null) {
                    app.setGraph(new FlightsIntersectionGraph(fileChooser.getFile().getName())) ;
                    ImportationFIG.importFlightsFromFile(this.app.getAirportSet(), 
                        (FlightsIntersectionGraph)this.app.getGraph(), fileChooser.getFile(), FlightsIntersectionGraph.DEFAULT_SECURITY_MARGIN);
                    
                    ColoringDSATUR.coloringDsatur(app.getGraph()) ;
                    ColoringUtilities.setGraphStyle(app.getGraph(), app.getGraph().getNbColors()) ;
                    app.getGraph().setKMax(app.getGraph().getNbColors()) ;
                    app.getMainScreen().initGraphBottomPanel() ;
                    this.initDefaultGraphImportation(this.app.getMainScreen().getGraphInfoPanel());
                    this.flightsImported = true;
                }
            }catch(InvalidFileFormatException | FileNotFoundException error) {
                JOptionPane.showMessageDialog(null, error.getMessage(),"Erreur d'importation", JOptionPane.ERROR_MESSAGE);
                fileChooser.setFile(null);
            }
    
        });
    
        confirmAirports.addActionListener((ActionEvent e) -> {
            if(this.airportsImported) {
                buttonsPanel.removeAll();
                this.remove(confirmStart);
                buttonsPanel.add(buttonFlightFileSelection);
                this.remove(panelReturnConfirmAir);
                this.add(panelReturnConfirmFlight);
                panelReturnConfirmFlight.setAlignmentX(Component.CENTER_ALIGNMENT);
                this.app.setVisible(true);
            }
        });

        returnBackAirports.addActionListener((ActionEvent e) -> {
            resetPanel(panelReturnConfirmAir, buttonAirportFileSelection);
            this.app.setVisible(true);
        });
    
        confirmFlights.addActionListener((ActionEvent e) -> {
            if(this.airportsImported && this.flightsImported) {
                this.resetPanel(panelReturnConfirmFlight, buttonFlightFileSelection);
                this.app.switchToMainScreen();
                this.app.getMainScreen().initMap();
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

    /**
     * Initializes the panel and statistics linked to this Graph
     * @param infoGraph Panel which will contain the statistics
     */
    public void initDefaultGraphImportation(NGraphInfoPanel infoGraph) {
        this.app.getMainScreen().getMinGraphPanel().addGraphToPanel(this.app.getGraphRenderer()) ;
        this.app.getMainScreen().getGraphMenuPanel()
            .initAllComboBoxes(app.getGraph().getKMax(), true) ;
        this.app.getMainScreen().getGraphMenuPanel().setLastAlgoSelected(null) ;
        infoGraph.computeGraphStats() ;
    }
}
