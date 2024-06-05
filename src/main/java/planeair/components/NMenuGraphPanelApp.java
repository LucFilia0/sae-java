package planeair.components;


// Import swing composants
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import planeair.App;

// Import awt composants
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

//Import Layout
import java.awt.GridLayout;
import java.awt.FlowLayout;

/**
 * Class which create a JPanel of MENU for the graph 
 * Change parameter of the graph
 * Location : Left to the Frame
 * 
 * @author GIRAUD Nila
 */
public class NMenuGraphPanelApp extends JPanel{

    /**
     * JLabel for the title of the Panel NMenuPanelApp
     */
    private JLabel titleMenu = new JLabel("MENU GRAPHE", SwingConstants.CENTER);

    //KMAX

    /**
     * Panel for Kmax option
     */
    private JPanel kmaxOption = new JPanel();

    /**
     * JLabel change KMax
     */
    private JLabel changeKmax = new JLabel("Changer Kmax", SwingConstants.CENTER);

    /**
     * Panel for create an empty border to the JComboBox Kmax
     */
    private JPanel borderPanelKmax = new JPanel();

    //ALTITUDES

    /**
     * Panel for altitudeMax option
     */
    private JPanel altitudeMaxOption = new JPanel();
    /**
     * Title of the choose ComboBox 
     */
    private JLabel nbAltitudes = new JLabel("Choix des altitudes", SwingConstants.CENTER);
    /**
     * JComboBox for choose the altitude (or everyone)
     * "Toutes" -> for everyone
     * A number between [first altitude; last altitude]
     */
    private JComboBox altitudeComboBox = new JComboBox();

    /**
     * Panel for create an empty border to the JComboBox altitude
     */
    private JPanel borderPanelAlt = new JPanel();

    //ALGORITHMES

    /**
     * Panel for algo option
     */
    private JPanel algoOption = new JPanel();
    /**
     * JLabel title for algorithmes (DSATUR + RLF)
     */
    private JLabel algorithmes = new JLabel("Algorithmes", SwingConstants.CENTER );
    /**
     * JPanel for put the JComboBox (choose algo) next to JButton (validate) 
     */
    private JPanel layoutAlgo = new JPanel(new FlowLayout(FlowLayout.CENTER));

    /**
     * JcomboBox that help too choose an algo for the coloration
     */
    private JComboBox<String> choixAlgo = new JComboBox<>();
    /**
     * Use the algo of the ComboBox (validate)
     * For help the PC to not automatally have to change of coloration all the time the user change of SelectedItem
     */
    private JButton okButton = new JButton("OK");


    /**
     * Constructor of NMenuPanelApp
     * @param kmax
     */
    NMenuGraphPanelApp(int kmax, JComboBox altitudesMax){

        this.setBackground(App.KINDAYELLOW);

        this.setLayout(new GridLayout(4,1));

        //TITLE
        titleMenu.setFont(new Font("Arial", Font.BOLD, 26));

        //KMAX
        kmaxOption.setLayout( new GridLayout(2,1));

        // Title
        changeKmax.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));
        kmaxOption.setBackground(App.KINDAYELLOW);

        //  ComboBox
        for(int i = 2 ; i <= kmax + 30 ; i++  ){
            altitudesMax.addItem(i);
        }
        
        altitudesMax.setSelectedItem(kmax);
        altitudesMax.setForeground(Color.WHITE);
        altitudesMax.setFont(new Font("Arial", Font.BOLD, 18));
        altitudesMax.setBackground(Color.BLACK);
        altitudesMax.setPreferredSize(new Dimension(100,30));

        kmaxOption.add(changeKmax);
        kmaxOption.add(altitudesMax);

        borderPanelKmax.setBackground(App.KINDAYELLOW);
        borderPanelKmax.setPreferredSize(new Dimension(225,30));

        borderPanelKmax.add(kmaxOption);

        //ALTITUDES
        altitudeMaxOption.setLayout(new GridLayout(2,1));
        altitudeMaxOption.setBackground(App.KINDAYELLOW);

        // Titre
        nbAltitudes.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));

        // ComboBox
        altitudeComboBox.setForeground(Color.WHITE);
        altitudeComboBox.setFont(new Font("Arial", Font.BOLD, 18));
        altitudeComboBox.setBackground(Color.BLACK);
        altitudeComboBox.setPreferredSize(new Dimension(100,30));

        altitudeMaxOption.add(nbAltitudes);
        altitudeMaxOption.add(altitudeComboBox);

        borderPanelAlt.setBackground(App.KINDAYELLOW);
        borderPanelAlt.setPreferredSize(new Dimension(225,30));

        //CONFLITS

        //ALGO
        algoOption.setLayout(new GridLayout(2,1));
        algoOption.setBackground(App.KINDAYELLOW);

        // Titre
        algorithmes.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));

        // JComboBox + JButton

        choixAlgo.setForeground(Color.WHITE);
        choixAlgo.setFont(new Font("Arial", Font.BOLD, 18));
        choixAlgo.setBackground(Color.BLACK);

        okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Arial", Font.BOLD, 14));
        okButton.setBackground(Color.BLACK);
        okButton.setPreferredSize(new Dimension(55,45));

        layoutAlgo.setLayout(new FlowLayout());
        layoutAlgo.setBackground(App.KINDAYELLOW);
        layoutAlgo.add(choixAlgo);
        layoutAlgo.add(okButton);

        algoOption.add(algorithmes);
        algoOption.add(layoutAlgo);

        choixAlgo.addItem("DSATUR");
        choixAlgo.addItem("RLF");
        choixAlgo.addItem("WELSH POWEL");

        

        //ADD
        this.add(titleMenu);
        //KMAX
        this.add(borderPanelKmax);
        //ALTITUDES
        borderPanelAlt.add(altitudeMaxOption);
        this.add(borderPanelAlt);

        //ALGO
        this.add(algoOption);
    }
    
    public void setAltitudeComboBox(int kmax){
        altitudeComboBox.removeAllItems();
        altitudeComboBox.addItem("Toutes");
        for(int i = 1; i <= kmax; i++ ){
            altitudeComboBox.addItem(i);
        }
    }
}
