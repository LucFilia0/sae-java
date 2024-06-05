package planeair.components;


// Import swing composants
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import planeair.App;
import planeair.graph.Coloration;
import planeair.graph.TestGraph;

// Import awt composants

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

//Import Layout
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
     * JComboBox to choose the altitude (or show all of them)
     */
    JComboBox<Integer> altitudeComboBox = new JComboBox<>();

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
     * JcomboBox that help too choose an algo for the coloration
     */
    JComboBox<String> algoChoice = new JComboBox<>();
    /**
     * Use the algo of the ComboBox
     */
    JButton okButton = new JButton("OK");
    /**
     * ComboBox containing kMax 
     */
    JComboBox<Integer> altitudesMax ;

    private String lastAlgoSelected = null ;

    private App app ;


    /**
     * Constructor of NMenuPanelApp
     * @param kmax
     */
    NMenuPanelApp(App app, int kmax, JComboBox<Integer> altitudesMax){
        this.app = app ;
        this.altitudesMax = altitudesMax ;
        this.setBackground(Color.YELLOW);

        this.setLayout( new GridLayout(5,1));

        //TITLE
        titleMenu.setFont(new Font("Arial", Font.BOLD, 20));

        //KMAX
        kmaxOption.setLayout( new GridLayout(2,1));

        this.setAltitudeValues(kmax) ;
        
        this.altitudesMax.setSelectedItem(kmax);
        this.altitudesMax.setForeground(Color.WHITE);
        this.altitudesMax.setFont(new Font("Arial", Font.BOLD, 18));
        this.altitudesMax.setBackground(Color.BLACK);
        this.altitudesMax.setOpaque(true);

        kmaxOption.add(changeKmax);
        changeKmax.setAlignmentX(Component.CENTER_ALIGNMENT);

        kmaxOption.add(this.altitudesMax);

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
        
        buttonLayout.setBackground(Color.YELLOW);

        buttonLayout.add(algoChoice);
        buttonLayout.add(okButton);

        algoOption.add(algorithmes);
        algoOption.add(buttonLayout);

        algoChoice.addItem(Coloration.DSATUR);
        algoChoice.addItem(Coloration.RLF);
        //algoChoice.addItem(Coloration.WELSH_POWELL) ;
        algoChoice.setSelectedItem(null) ;

        //ADD

        this.add(titleMenu);

        //KMAX
        
        borderPanelKmax.add(kmaxOption);

        this.add(borderPanelKmax);


        //ALTITUDES

        borderPanelAlt.add(altitudeMaxOption);
        this.add(borderPanelAlt);
        this.setAltitudeComboBox(kmax);

        //ALGO
        this.add(algoOption);

        //LISTENERS
        initListeners() ;
    }
    
    public void setAltitudeComboBox(int kmax){
        altitudeComboBox.removeAllItems();
        altitudeComboBox.setRenderer(new ListCellRenderer<Integer>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends Integer> list, Integer value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                JLabel cell = new JLabel() ;
                if (value == 0) {
                    cell.setText("Toutes") ;
                }

                else {
                    cell.setText(Integer.toString(value)) ;
                }

                return cell ;
            }
        });

        for(int i = 0; i <= kmax; i++ ){
            altitudeComboBox.addItem(i);
        }
    }

    public JComboBox<Integer> getAltitudeMax() {
        return this.altitudesMax ;
    }

    public JComboBox<String> getAlgoChoice() {
        return this.algoChoice ;
    }

    /**
     * Sets the range of values and the default value of the kMax comboBox in the menu
     * @param kMax new KMax value
     */
    public void setAltitudeValues(int kMax) {
        this.altitudesMax.removeAllItems() ;
        for (int i = 2; i < (int)kMax*2 ; i++) {
            this.altitudesMax.addItem(i) ;
        }

        this.altitudesMax.setSelectedItem(kMax) ;
    }

    public Integer getCurrentKMax() {
        return (Integer)this.altitudesMax.getSelectedItem() ;
    }

    public String getCurrentAlgorithm() {
        return (String)this.algoChoice.getSelectedItem() ;
    }

    private void initListeners() {
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TestGraph graph = app.getTestGraph() ;
                int oldKmax = graph.getKMax() ;
                int currentKMax = (int)altitudesMax.getSelectedItem() ;
                graph.setKMax(currentKMax) ;
                
                if (lastAlgoSelected != (String)algoChoice.getSelectedItem()) {
                    if (lastAlgoSelected != null) {
                        Coloration.removeCurrentColoring(graph, TestGraph.COLOR_ATTRIBUTE, TestGraph.CONFLICT_ATTRIBUTE) ;
                    }
                    lastAlgoSelected = (String)algoChoice.getSelectedItem() ;
                    Coloration.colorGraphWithChosenAlgorithm(graph, (String)algoChoice.getSelectedItem(), TestGraph.COLOR_ATTRIBUTE) ;
                    Coloration.setGraphStyle(graph, currentKMax, TestGraph.COLOR_ATTRIBUTE) ;
                }
                
                else {
                    if (oldKmax < currentKMax || graph.getNbColors() > currentKMax) {
                        Coloration.removeCurrentColoring(graph, TestGraph.COLOR_ATTRIBUTE, TestGraph.CONFLICT_ATTRIBUTE) ;
                        Coloration.colorGraphWithChosenAlgorithm(graph, (String)algoChoice.getSelectedItem(), TestGraph.COLOR_ATTRIBUTE) ;
                        Coloration.setGraphStyle(graph, currentKMax, TestGraph.COLOR_ATTRIBUTE) ;
                    }
                }
            }
        });
    }
}
