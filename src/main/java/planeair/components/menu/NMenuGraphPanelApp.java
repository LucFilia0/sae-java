package planeair.components.menu;


// Import swing composants
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

import planeair.App;
import planeair.graph.Coloration;
import planeair.graph.TestGraph;

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
    private JComboBox<Integer> altitudeComboBox = new JComboBox<>();

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
    JComboBox<String> algoChoice = new JComboBox<>();
    /**
     * Use the algo of the ComboBox (validate)
     * For help the PC to not automatally have to change of coloration all the time the user change of SelectedItem
     */
    private JButton okButton = new JButton("OK");

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
    public NMenuGraphPanelApp(App app, int kmax, JComboBox<Integer> altitudesMax){

        this.app = app ;
        this.altitudesMax = altitudesMax ;

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
        setAltitudeComboBox(kmax);
        this.setAltitudeValues(kmax) ;
        
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
        // Democraty doesn't work in times of war. DO NOT TOUCH (or i will put a pipe bomb in your mail).
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

        algoChoice.setForeground(Color.WHITE);
        algoChoice.setFont(new Font("Arial", Font.BOLD, 18));
        algoChoice.setBackground(Color.BLACK);

        okButton.setForeground(Color.WHITE);
        okButton.setFont(new Font("Arial", Font.BOLD, 14));
        okButton.setBackground(Color.BLACK);
        okButton.setPreferredSize(new Dimension(55,45));

        layoutAlgo.setLayout(new FlowLayout());
        layoutAlgo.setBackground(App.KINDAYELLOW);
        layoutAlgo.add(algoChoice);
        layoutAlgo.add(okButton);

        algoOption.add(algorithmes);
        algoOption.add(layoutAlgo);

        algoChoice.addItem(Coloration.DSATUR);
        algoChoice.addItem(Coloration.RLF);
        algoChoice.addItem(Coloration.WELSH_POWELL);

        

        //ADD
        this.add(titleMenu);
        //KMAX
        this.add(borderPanelKmax);
        //ALTITUDES
        borderPanelAlt.add(altitudeMaxOption);
        this.add(borderPanelAlt);

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
                cell.setForeground(Color.WHITE);
                cell.setFont(new Font("Arial", Font.BOLD, 18));
                cell.setBackground(Color.BLACK);
                cell.setPreferredSize(new Dimension(100,30));

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
                boolean colorationChanged = false ;
                TestGraph graph = app.getTestGraph() ;
                int oldKmax = graph.getKMax() ;
                int currentKMax = (int)altitudesMax.getSelectedItem() ;
                graph.setKMax(currentKMax) ;
                
                if (lastAlgoSelected != (String)algoChoice.getSelectedItem()) {
                    if (lastAlgoSelected != null) {
                        Coloration.removeCurrentColoring(graph) ;
                    }
                    lastAlgoSelected = (String)algoChoice.getSelectedItem() ;
                    colorationChanged = true ;
                }
                
                else {
                    if (oldKmax > currentKMax || oldKmax < currentKMax && graph.getNbConflicts() > 0 || graph.getNbColors() > currentKMax) {
                        colorationChanged = true ;
                    }
                }

                if (colorationChanged) {
                    if (lastAlgoSelected != null) {
                        Coloration.removeCurrentColoring(graph) ;
                    }
                    Coloration.colorGraphWithChosenAlgorithm(graph, (String)algoChoice.getSelectedItem()) ;
                    Coloration.setGraphStyle(graph, currentKMax) ;
                    NInfoGraphPanelApp panel = app.getPrincFrame().getInfoGraphPanel() ;
                    panel.setNbColorsUsed(graph.getNbColors()) ;
                    panel.setNbConflictsOccurred(graph.getNbConflicts()) ;
                }
            }
        });
    }
    
 
}
