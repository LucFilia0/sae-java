package composants;

/**
 * Import swing composants
 */
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.Box;

/**
 * Import awt composants
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
/**
 * Import Layout
 */
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;




/**
 * Class which create a JPanel of MENU for the graph 
 * Change parameter of the graph
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
     * JPanel for put the slider (sliderKmax) next to the label (choixAltitudes)
     */
    //JPanel sliderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    
    /**
     * Empty Slider
     */
    //JPanel empty = new JPanel();

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

       /* this.setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));
        
        this.setPreferredSize(new Dimension(260,335));

        

        setAltitudeComboBox(kmax);

        //KMAX
        sliderKmax.setMinimum(2);
        sliderKmax.setMaximum(kmax + 30);
        sliderPanel.add(sliderKmax);

        for(int i = 2 ; i <= kmax + 30 ; i++  ){
            choixAltitudesMax.addItem(i);
        }
        choixAltitudesMax.setSelectedItem(kmax);

        choixAltitudesMax.setForeground(Color.WHITE);
        choixAltitudesMax.setFont(new Font("Arial", Font.BOLD, 18));
        choixAltitudesMax.setBackground(Color.BLACK);
        choixAltitudesMax.setOpaque(true);
        sliderPanel.add(choixAltitudesMax);
        empty.setBackground(Color.YELLOW);
        sliderPanel.add(empty);
        sliderKmax.setBackground(Color.YELLOW);
        sliderPanel.setBackground(Color.YELLOW);
        sliderKmax.setValue(kmax);


        //ALTITUDES
        altitudeComboBox.setBackground(Color.YELLOW);

        //CONFLITS
        nbConflits.setForeground(Color.WHITE);
        nbConflits.setFont(new Font("Arial", Font.BOLD, 25));
        nbConflits.setBackground(Color.BLACK);
        nbConflits.setOpaque(true);

        //ALGORITHMES
        buttonsLayout.add(dsatur);
        dsatur.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsLayout.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonsLayout.add(rlf);
        rlf.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonsLayout.setBackground(Color.YELLOW);

        // Color of the background (BLACK)
        dsatur.setBackground(Color.BLACK);
        rlf.setBackground(Color.BLACK);
        
        //  Size  width : 100  height : 125 
        dsatur.setPreferredSize(new Dimension(85,75));
        rlf.setPreferredSize(new Dimension(85,75));

        // Color of the text (WHITE)
        dsatur.setForeground(Color.WHITE);
        rlf.setForeground(Color.WHITE);

        //add

        //TITLE

        titleMenu.setFont(new Font("Arial", Font.BOLD, 20));
        this.add(titleMenu);
        titleMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        this.add(Box.createRigidArea(new Dimension(0, 8)));

        //KMAX
        changeKmax.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(changeKmax);
        changeKmax.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
        this.add(sliderPanel);
        sliderPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //ALTITUDES
        nbAltitudes.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(nbAltitudes);
        nbAltitudes.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
        altitudeComboBox.setPreferredSize(new Dimension(0,25));
        this.add(altitudeComboBox);
        altitudeComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(Box.createRigidArea(new Dimension(0, 6)));

        //CONFLITS
        nbConflitsTitle.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(nbConflitsTitle);
        nbConflitsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
        this.add(nbConflits);
        nbConflits.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(Box.createRigidArea(new Dimension(0, 8)));

        //ALGORITHMS
        algorithmes.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(algorithmes);
        algorithmes.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createRigidArea(new Dimension(0, 2)));
        this.add(buttonsLayout);
        buttonsLayout.setAlignmentX(Component.CENTER_ALIGNMENT);*/

        this.setLayout( new GridBagLayout());

        

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

       // kmaxOption.add(Box.createRigidArea(new Dimension(0, 5)));

        kmaxOption.add(altitudesMax);
        //altitudesMax.setAlignmentX(Component.CENTER_ALIGNMENT);

        kmaxOption.setBackground(Color.YELLOW);

        //ALTITUDES

        altitudeMaxOption.setLayout(new GridLayout(2,1));

        //nbAltitudes.setFont(new Font("Arial", Font.BOLD, 18));
        altitudeMaxOption.add(nbAltitudes);
        //nbAltitudes.setAlignmentX(Component.CENTER_ALIGNMENT);

        //altitudeMaxOption.add(Box.createRigidArea(new Dimension(0, 5)));

        altitudeComboBox.setPreferredSize(new Dimension(0,25));
        altitudeMaxOption.add(altitudeComboBox);
        //altitudeComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        altitudeMaxOption.setBackground(Color.YELLOW);

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



        //ADD

        GridBagConstraints GridBagC = new GridBagConstraints(); 
        GridBagC.insets = new Insets(10, 20, 10, 20);

        GridBagC.fill = GridBagConstraints.BOTH;

        //TITLE
        GridBagC.gridx = 1;
        GridBagC.gridy = 0;

        this.add(titleMenu, GridBagC);
        //titleMenu.setAlignmentX(Component.CENTER_ALIGNMENT);

        //KMAX

        GridBagC.gridy = 2;
        
        this.add(kmaxOption, GridBagC);
        //kmaxOption.setAlignmentX(Component.CENTER_ALIGNMENT);

        //ALTITUDES

        GridBagC.gridy = 3;
        this.add(altitudeMaxOption, GridBagC);

       
        //ALGO
        GridBagC.gridy = 4;
        this.add(algoOption,GridBagC);

        







    }
    
    public void setAltitudeComboBox(int kmax){
        altitudeComboBox.removeAllItems();
        altitudeComboBox.addItem("Toutes");
        for(int i = 1; i <= kmax; i++ ){
            altitudeComboBox.addItem(i);
        }
    }
}
