package composants;

// Import of SWING composants
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BorderLayout;
// Import of AWT composants
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

// Import of Layout
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;


/**
 * Create a JPanel of importation for vols and aeroports
 */
public class NButtonImportPanelApp extends JPanel {

    public static final int AIRPORT_FILE = 0;
    public static final int TEST_GRAPH_FILE = 1;
    public static final int FLIGHT_FILE = 12;
    
    

    /**
     * Button de choix
     * Vols/Aeroports
     */
    JButton choixVols = new JButton("<html>Importer des fichiers <br> vols/aeroports</html>");
    /**
     * Button de choix
     * Graph
     */
    JButton choixGraph = new JButton("<html>Importer un fichier <br> graphes.csv</html>");
    

    /**
     * Button Importation
     * vols.csv
     */
    JButton buttonVols = new JButton("vol-test.csv");
    /**
     * Button Importation
     * aeroport.csv
     */
    JButton buttonAeroport = new JButton("aeroport.csv");
    /**
     * Button Importation
     * graph.csv
     */
    JButton buttonGraph = new JButton("graph-test.csv");

    /**
     * Contain an Empty Panel and spaceBorderButtonImport
     * Forcing bottom
     */
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    /**
     * Button for valided aerorort
     */
    JButton valider = new JButton("Valider");

    /**
     * Button for valided vols
     */
    JButton valideVol = new JButton("Valider");


    NHomePage homePage;
    /**
     * Test pour rajouter un boutton valider au début
     * 
     */
    JButton valideStart = new JButton("Valider");

    


    /**
     * Create an Import panel
     * In the constructor you have to specify the number of button
     * 1 for aeroport
     * 2 for " + vols
     * @param framePrinc The object HomePage where the panel is put
     * @author GIRAUD Nila
     */
    public NButtonImportPanelApp(NHomePage framePrinc){
        this.homePage = framePrinc;

        this.setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));
        this.setBackground(Color.YELLOW);

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5,true));
        // // Set Panel's Background color (Yellow)
        // this.setBackground(Color.YELLOW);

        // // Color of the background (BLACK)
        // buttonVols.setBackground(Color.BLACK);
        // buttonAeroport.setBackground(Color.BLACK);
        
        // //  Size  width : 100  height : 125 
        // buttonVols.setPreferredSize(new Dimension(100,125));
        // buttonAeroport.setPreferredSize(new Dimension(100,125));

        // // Color of the text (WHITE)
        // buttonVols.setForeground(Color.WHITE);
        // buttonAeroport.setForeground(Color.WHITE);
        

        // buttonVols.setHorizontalTextPosition(SwingConstants.CENTER);
        // buttonAeroport.setHorizontalTextPosition(SwingConstants.CENTER);

        choixVols.setForeground(Color.WHITE);
        choixVols.setPreferredSize(new Dimension(200,125));
        choixVols.setBackground(Color.BLACK);

        buttonAeroport.setForeground(Color.WHITE);
        buttonAeroport.setPreferredSize(new Dimension(200,125));
        buttonAeroport.setBackground(Color.BLACK);

        buttonVols.setForeground(Color.WHITE);
        buttonVols.setPreferredSize(new Dimension(200,125));
        buttonVols.setBackground(Color.BLACK);


        choixGraph.setForeground(Color.WHITE);
        choixGraph.setPreferredSize(new Dimension(200,125));
        choixGraph.setBackground(Color.BLACK);

        buttonsPanel.add(choixVols);
        buttonsPanel.add(choixGraph);
        buttonsPanel.setBackground(Color.YELLOW);

        valider.setForeground(Color.WHITE);
        valider.setPreferredSize(new Dimension(50,40));
        valider.setBackground(Color.BLACK);

        valideVol.setForeground(Color.WHITE);
        valideVol.setPreferredSize(new Dimension(50,40));
        valideVol.setBackground(Color.BLACK);

        valideStart.setForeground(Color.WHITE);
        valideStart.setPreferredSize(new Dimension(50,40));
        valideStart.setBackground(Color.BLACK);

        this.add(buttonsPanel);
        this.add(valideStart);
        valideStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0, 5)));

    }

    public void addEvents(){
        valideStart.addActionListener((ActionEvent e) -> {
            homePage.addBodyPanelPrinc(this);
            this.setVisible(false);
        });

        choixVols.addActionListener((ActionEvent e) -> {
            buttonsPanel.removeAll();
            this.remove(valideStart);
            buttonsPanel.add(buttonAeroport);
            this.add(valider);
            valider.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.add(Box.createRigidArea(new Dimension(0, 5)));
            homePage.setVisible(true);

        });

        choixGraph.addActionListener((ActionEvent e) -> {
            NFileChooserForGraphApp fileChooser = new NFileChooserForGraphApp(TEST_GRAPH_FILE);
            if(fileChooser.getIsSelected()){
                homePage.addBodyPanelPrinc(this);
                this.setVisible(false);
            }
        });

        valider.addActionListener((ActionEvent e) -> {
            buttonsPanel.removeAll();
            this.remove(valideStart);
            this.remove(valider);
            buttonsPanel.add(buttonVols);
            this.add(valideVol);
            valideVol.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.add(Box.createRigidArea(new Dimension(0, 5)));
            homePage.setVisible(true);
            // homePage.addBodyPanelPrinc(this);
            // this.setVisible(false);
        });

        valideVol.addActionListener((ActionEvent e) -> {
            homePage.addBodyPanelPrinc(this);
            this.setVisible(false);
        });



        buttonAeroport.addActionListener((ActionEvent e) -> {
        NFileChooserForGraphApp fileChosser = new NFileChooserForGraphApp(AIRPORT_FILE);
        if(fileChosser.getIsSelected()){
            homePage.addBodyPanelPrinc(this);
            this.setVisible(false);
        }
            // if(this.getComponentCount() == 1){
            //     homePage.addBody(this);
            //     this.setVisible(false);
            // }
            // else{
            //     this.removeAll();
            //     buttonVols.setPreferredSize(new Dimension(300,175));
            //     this.add(buttonVols);
            //     homePage.setVisible(true);
            // }   

        

        } );

        buttonVols.addActionListener((ActionEvent e) -> {
        NFileChooserForGraphApp fileChosser = new NFileChooserForGraphApp(FLIGHT_FILE);
        if(fileChosser.getIsSelected()){
            homePage.addBodyPanelPrinc(this);
            this.setVisible(false);
        }

            // if(this.getComponentCount() == 1){
            //     homePage.addBody(this);
            //     this.setVisible(false);
            // }
            // else{
            //     this.removeAll();
            //     buttonAeroport.setPreferredSize(new Dimension(300,175));
            //     this.add(buttonAeroport);
            //     homePage.setVisible(true);
            // } 
        });
        }  
}
