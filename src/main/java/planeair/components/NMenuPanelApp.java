package planeair.components;


// Import swing composants
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;


// Import awt composants

import java.awt.Color;
import java.awt.Component;
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
public class NMenuPanelApp extends JPanel{

    /**
     * JLabel for the title of the Panel NMenuPanelApp
     */
    JLabel titleMenu = new JLabel("MENU", SwingConstants.CENTER);

    //KMAX

    /**
     * Panel for Kmax option
     */
    JPanel kmaxOption = new JPanel();

    /**
     * JLabel change KMax
     */
    JLabel changeKmax = new JLabel("Changer Kmax", SwingConstants.CENTER);

    /**
     * Panel for create an empty border to the JComboBox Kmax
     */
    JPanel borderPanelKmax = new JPanel();

    //ALTITUDES

    /**
     * Panel for altitudeMax option
     */
    JPanel altitudeMaxOption = new JPanel();
    /**
     * Title of the choose slider
     */
    JLabel nbAltitudes = new JLabel("Choix des altitudes", SwingConstants.CENTER);
    /**
     * JComboBox for choose the altitude (or everyone)
     */
    JComboBox altitudeComboBox = new JComboBox();

    /**
     * Panel for create an empty border to the JComboBox altitude
     */
    JPanel borderPanelAlt = new JPanel();

    //CONFLITS
    
     /**
     * Panel for conflict option
     */
    //JPanel conflictOption = new JPanel();
    /**
     * title of conflict numbers
     */
    //JLabel nbConflitsTitle = new JLabel("Nombres de conflits");
    /**
     * JLabel of the number of conflicts
     */
    //JLabel nbConflits = new JLabel("  12  ", SwingConstants.CENTER);

    //ALGORITHMES

    /**
     * Panel for algo option
     */
    JPanel algoOption = new JPanel();
    /**
     * JLabel title for algorithmes (DSATUR + RLF)
     */
    JLabel algorithmes = new JLabel("Algorithmes", SwingConstants.CENTER );
    /**
     * JPanel for put the button (DSTAUR) next to the button (RLF)
     */
    JPanel buttonLayout = new JPanel(new FlowLayout(FlowLayout.CENTER));

    /**
     * JcomboBox that help too choose an algo for the coloation
     */
    JComboBox choixAlgo = new JComboBox();
    /**
     * Use the algo of the ComboBox
     */
    JButton okButton = new JButton("OK");


    /**
     * Constructor of NMenuPanelApp
     * @param kmax
     */
    NMenuPanelApp(int kmax, JComboBox altitudesMax){

        this.setBackground(Color.YELLOW);

        this.setLayout( new GridLayout(5,1));

        //TITLE
        titleMenu.setFont(new Font("Arial", Font.BOLD, 20));

        //KMAX
        kmaxOption.setLayout( new GridLayout(2,1));

        for(int i = 2 ; i <= kmax + 30 ; i++  ){
            altitudesMax.addItem(i);
        }
        
        altitudesMax.setSelectedItem(kmax);
        altitudesMax.setForeground(Color.WHITE);
        altitudesMax.setFont(new Font("Arial", Font.BOLD, 18));
        altitudesMax.setBackground(Color.BLACK);
        altitudesMax.setOpaque(true);

        kmaxOption.add(changeKmax);
        changeKmax.setAlignmentX(Component.CENTER_ALIGNMENT);

        kmaxOption.add(altitudesMax);

        kmaxOption.setBackground(Color.YELLOW);
        borderPanelKmax.setBackground(Color.YELLOW);

        //ALTITUDES

        altitudeMaxOption.setLayout(new GridLayout(2,1));
        altitudeMaxOption.add(nbAltitudes);

        altitudeComboBox.setPreferredSize(new Dimension(0,25));
        altitudeMaxOption.add(altitudeComboBox);

        altitudeMaxOption.setBackground(Color.YELLOW);

        borderPanelAlt.setBackground(Color.YELLOW);

        //CONFLITS

        //ALGO

        algoOption.setLayout(new GridLayout(2,1));
        algoOption.setBackground(Color.YELLOW);

        buttonLayout.setLayout(new FlowLayout());
        buttonLayout.setBackground(Color.YELLOW);

        buttonLayout.add(choixAlgo);
        buttonLayout.add(okButton);

        algoOption.add(algorithmes);
        algoOption.add(buttonLayout);

        choixAlgo.addItem((Object)"DSATUR");
        choixAlgo.addItem((Object)"RLF");

        //ADD

        this.add(titleMenu);

        //KMAX
        
        borderPanelKmax.add(kmaxOption);

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
