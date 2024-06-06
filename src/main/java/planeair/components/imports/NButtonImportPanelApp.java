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
import planeair.components.imports.buttons.NButtonConfirmOrReturnImportApp;
import planeair.components.imports.buttons.NButtonImportFileApp;
import planeair.components.menu.NInfoGraphPanelApp;
import planeair.exceptions.InvalidFileFormatException;
import planeair.graph.TestGraph;
import planeair.util.DataImportation;

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
     * Border Title for know what to do
     */
    TitledBorder  border = new TitledBorder("Title");

    /**
     * Button de choix
     * Vols/Aeroports
     * Location : first button in the center of the frame, left to graph one
     */
    private NButtonImportFileApp choiceFlight = new NButtonImportFileApp("<html>Importer des fichiers <br> vols/aeroports</html>");
    /**
     * Button de choix
     * Graph
     * Location : second button in the center of the frame, right to vol/aeroprt one
     */
    private NButtonImportFileApp choiceGraph = new NButtonImportFileApp("<html>Importer un fichier <br> graphes.csv</html>");
    
    /**
     * Button Importation
     * vol-test.csv
     * Location : after pushing the button buttonAirport
     */
    private NButtonImportFileApp buttonFlight = new NButtonImportFileApp("vol-test.csv");
    /**
     * Button Importation for flight
     * aeroport.txt
     * Location : after pushing the button choiceFlight
     */
    private NButtonImportFileApp buttonAirport = new NButtonImportFileApp("aeroport.csv");

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
    private NButtonConfirmOrReturnImportApp confirmAir = new NButtonConfirmOrReturnImportApp("Valider");
    /**
     * Button for annule return back to the step the user is
     */
    private NButtonConfirmOrReturnImportApp returnBackAir = new NButtonConfirmOrReturnImportApp("Annuler");

    /**
     * Panel for returnBack + valideFlight
     * FlowLayout : CENTER
     */
    private JPanel panelReturnConfirmFlight = new JPanel(new FlowLayout(FlowLayout.CENTER));
    /**
     * Button for valide vols
     * Location : below buttonFlight
     */
    private NButtonConfirmOrReturnImportApp confirmFlight = new NButtonConfirmOrReturnImportApp("Valider");
    /**
     * Button for annule return back to the step the user is
     */
    private NButtonConfirmOrReturnImportApp returnBackFlight = new NButtonConfirmOrReturnImportApp("Annuler");


    /**
     * Test pour rajouter un boutton valider au début
     * Location : below buttons choice
     */
    private NButtonConfirmOrReturnImportApp confirmStart = new NButtonConfirmOrReturnImportApp("Valider");

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

        this.setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));
        this.setBackground(App.KINDAYELLOW);

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5,true));

    }

    /**
     * Method adding components on the Panel
     */
    public void addComponents(){

        buttonsPanel.add(choiceFlight);
        buttonsPanel.add(choiceGraph);
        buttonsPanel.setBackground(App.KINDAYELLOW);

        panelReturnConfirmAir.setBackground(App.KINDAYELLOW);
        panelReturnConfirmAir.add(returnBackAir);
        panelReturnConfirmAir.add(confirmAir);

        panelReturnConfirmFlight.setBackground(App.KINDAYELLOW);
        panelReturnConfirmFlight.add(returnBackFlight);
        panelReturnConfirmFlight.add(confirmFlight);

        this.add(buttonsPanel);
        this.add(confirmStart);
        confirmStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    /**
     * Add events for the frame
     * Importation and return back
     */
    public void addEvents(){

        confirmStart.addActionListener((ActionEvent e) -> {
            this.app.addBodyPanelPrinc(); 
        });
        
        choiceFlight.addActionListener((ActionEvent e) -> {
            buttonsPanel.removeAll();
            this.remove(confirmStart);
            buttonsPanel.add(buttonAirport);
            this.add(panelReturnConfirmAir);
            panelReturnConfirmAir.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.app.setVisible(true);

        });
    
        choiceGraph.addActionListener((ActionEvent e) -> {

            NFileChooser fileChooser = new NFileChooser(this.app, NFileChooser.GRAPH_FILE);
            fileChooser.userImportFile();

            if(!fileChooser.getFile().equals(null)) {
                try {
                    this.app.setTestGraph(new TestGraph(fileChooser.getFile().getName())) ;
                    DataImportation.importTestGraphFromFile(this.app.getTestGraph(), fileChooser.getFile(), false);
                    initDefaultGraphImportation(app.getPrincFrame().getInfoGraphPanel()) ;  
                }catch(InvalidFileFormatException | FileNotFoundException error) {
                    this.app.setTestGraph(null) ;
                    JOptionPane.showMessageDialog(null, error.getMessage(),"Erreur d'importation", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    
        confirmAir.addActionListener((ActionEvent e) -> {
            buttonsPanel.removeAll();
            this.remove(confirmStart);
            buttonsPanel.add(buttonFlight);
            this.remove(panelReturnConfirmAir);
            this.add(panelReturnConfirmFlight);
            panelReturnConfirmFlight.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.app.setVisible(true);
        });

        returnBackAir.addActionListener((ActionEvent e) -> {
            resetPanel(panelReturnConfirmAir, buttonAirport);
            this.app.setVisible(true);
        });
    
        confirmFlight.addActionListener((ActionEvent e) -> {
            this.resetPanel(panelReturnConfirmFlight, buttonFlight);
            this.app.addBodyPanelPrinc();
            this.app.getPrincFrame().initMap();
        });
    
        returnBackFlight.addActionListener((ActionEvent e) -> {
            buttonsPanel.removeAll();
            this.remove(panelReturnConfirmFlight);
            this.remove(buttonFlight);

            buttonsPanel.add(buttonAirport);
            this.add(panelReturnConfirmAir);
            panelReturnConfirmAir.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.repaint();
        });
    
        buttonAirport.addActionListener((ActionEvent e) -> {
            
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
    
        buttonFlight.addActionListener((ActionEvent e) -> {
    
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

        buttonsPanel.add(choiceFlight);
        buttonsPanel.add(choiceGraph);
        this.add(buttonsPanel);
        this.add(confirmStart);
        confirmStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    /**
     * Initializes the panel and statistics linked to this Graph
     * @param infoGraph Panel which will contain the statistics
     */
    public void initDefaultGraphImportation(NInfoGraphPanelApp infoGraph) {
        this.app.getPrincFrame().getMinGraphPanel().addGraphToPanel(this.app.getTestGraphRenderer()) ;
        this.app.getPrincFrame().getMenuGraphPanel().setAltitudeValues(this.app.getTestGraph().getKMax()) ;
        this.app.getPrincFrame().getMenuGraphPanel().setLastAlgoSelected(null) ;
        infoGraph.addComponents() ;
        infoGraph.computeGraphStats() ;
    }
}
