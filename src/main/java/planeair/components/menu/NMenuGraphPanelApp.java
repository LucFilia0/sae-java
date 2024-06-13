package planeair.components.menu;


// Import swing components
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;


// Import awt components
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
//Import Layout
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

import planeair.App;
import planeair.components.time.NComboBoxTime;
import planeair.graph.coloring.ColoringUtilities;
import planeair.graph.graphtype.TestGraph;

/**
 * Class which create a JPanel of MENU for the graph 
 * Change parameter of the graph
 * Location : Left to the Frame
 * 
 * @author GIRAUD Nila
 */
public class NMenuGraphPanelApp extends JPanel{

    /**
     * 
     */
    public static final Dimension KINDACOMBOBOXDIMENSION = new Dimension(200,33);

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
    private JLabel colorChoice = new JLabel("Choix de la couleur", SwingConstants.CENTER);
    /**
     * JComboBox for choose the altitude (or everyone)
     * "Toutes" -> for everyone
     * A number between [first altitude; last altitude]
     */
    private NComboBoxGraphe altitudeComboBox = new NComboBoxGraphe();

    /**
     * Panel for create an empty border to the JComboBox altitude
     */
    private JPanel borderPanelAlt = new JPanel();

    //CHANGE SAFETY MARGIN 

    /**
     * Panel for Margin option
     */
    private JPanel marginOptionPanel = new JPanel();
    /**
     * Title of the choose ComboBox 
     */
    private JLabel safetyMargin = new JLabel("Changer marge sécurité", SwingConstants.CENTER);
    /**
     * JComboBox for choose the margin (default : 15min)
     * A number between [  ;  ]
     */
    private NComboBoxTime marginComboBox = new NComboBoxTime(59, NMenuGraphPanelApp.KINDACOMBOBOXDIMENSION);

    /**
     * Panel for create an empty border to the JComboBox safety margin
     */
    private JPanel borderPanelMargin = new JPanel();

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
     * JcomboBox that help too choose an algo for the coloration
     */
    private JComboBox<String> algoChoice = new JComboBox<>();
    /**
     * JPanel for put the JComboBox (choose algo) next to JButton (validate) 
     */
    private JPanel borderPanelAlgo = new JPanel(new FlowLayout(FlowLayout.CENTER));

    //CONFIRM

    /**
     * Panel For confirm button , CENTER alignement(BoxLayout)
     */
    private JPanel layoutConfirm = new JPanel(new GridBagLayout());

    /**
     * Use the algo of the ComboBox (validate)
     * For help the PC to not automatally have to change of coloration all the time the user change of SelectedItem
     */
    private JButton confirmButton = new JButton("Valider");

    /**
     * ComboBox containing kMax 
     */
    private NComboBoxGraphe kmaxComboBox ;

    /**
     * String containing the last algo selected
     */
    private String lastAlgoSelected = null ;

    /**
     * Last color selected for the displayed color
     */
    private int lastColorSelected = 0 ;

    /**
     * Homepage blablabla
     */
    private App app ;


    /**
     * Constructor of NMenuPanelApp
     * @param kmax
     * @param altitudesMax 
     */
    public NMenuGraphPanelApp(App app, int kmax, NComboBoxGraphe altitudesMax){

        this.app = app ;
        this.kmaxComboBox = altitudesMax ;

        this.setBackground(App.KINDAYELLOW);

        this.setLayout(new GridLayout(6,1));

        //TITLE
        titleMenu.setFont(App.KINDATITLE);

        //#region KMAX
        kmaxOption.setLayout( new GridLayout(2,1));

        // Title
        changeKmax.setFont(App.KINDANORMAL);
        kmaxOption.setBackground(App.KINDAYELLOW);

        
        //  ComboBox
        if (kmax > 1) {
            this.initKmaxValues(kmax) ;
        }
        else {
            altitudesMax.addItem(0) ;
            altitudesMax.setSelectedIndex(0) ;
        }
        initAltitudeComboBox(kmax);
        altitudesMax.setSelectedItem(kmax);
        

        kmaxOption.add(changeKmax);
        kmaxOption.add(altitudesMax);

        borderPanelKmax.setBackground(App.KINDAYELLOW);
        borderPanelKmax.setPreferredSize(new Dimension(250,30));

        borderPanelKmax.add(kmaxOption);
        //#endregion

        //#region ALTITUDES
        altitudeMaxOption.setLayout(new GridLayout(2,1));
        altitudeMaxOption.setBackground(App.KINDAYELLOW);

        // Titre
        colorChoice.setFont(App.KINDANORMAL);

        // ComboBox
        // Democraty doesn't work in times of war. DO NOT TOUCH (or i will put a pipe bomb in your mail).

        altitudeMaxOption.add(colorChoice);
        altitudeMaxOption.add(altitudeComboBox);

        borderPanelAlt.setBackground(App.KINDAYELLOW);
        //#endregion

        //#region SAFETY MARGIN
        // SAFETY MARGIN
        marginComboBox.setSelectedItem(15);
        marginOptionPanel.setLayout(new GridLayout(2,1));
        marginOptionPanel.setBackground(App.KINDAYELLOW);

        // Titre
        safetyMargin.setFont(App.KINDANORMAL);

        // JComboBox
        marginOptionPanel.add(safetyMargin);
        marginOptionPanel.add(marginComboBox);

        borderPanelMargin.setBackground(App.KINDAYELLOW);
        //#endregion SAFETY MARGIN

        //#region ALGO
        algoOption.setLayout(new GridLayout(2,1));
        algoOption.setBackground(App.KINDAYELLOW);

        // Titre
        algorithmes.setFont(App.KINDANORMAL);

        // JComboBox

        algoChoice.setForeground(Color.WHITE);
        algoChoice.setFont(new Font("Arial", Font.BOLD, 18));
        algoChoice.setBackground(Color.BLACK);
        algoChoice.setPreferredSize(NMenuGraphPanelApp.KINDACOMBOBOXDIMENSION);

        algoOption.add(algorithmes);
        algoOption.add(algoChoice);

        borderPanelAlgo.setBackground(App.KINDAYELLOW);
        //#endregion

        //#region CONFIRM
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFont(App.KINDABOLD);
        confirmButton.setBackground(Color.BLACK);
        confirmButton.setPreferredSize(NMenuGraphPanelApp.KINDACOMBOBOXDIMENSION);
        layoutConfirm.setBackground(App.KINDAYELLOW);
        //#endregion

        initAlgoComboBox((app.getTestGraph() != null)) ;

        //#region ADD
        //ADD
        this.add(titleMenu);
        //KMAX
        this.add(borderPanelKmax);
        //ALTITUDES
        borderPanelAlt.add(altitudeMaxOption);
        this.add(borderPanelAlt);

        // SAFETY MARGIN
        borderPanelMargin.add(marginOptionPanel);
        this.add(borderPanelMargin);

        //ALGO
        borderPanelAlgo.add(algoOption);
        this.add(borderPanelAlgo);


        //Ok button
        layoutConfirm.add(confirmButton);
        this.add(layoutConfirm);
        //#endregion

        //LISTENERS AND RENDERERS
        initListeners() ;
        initRenders() ;
    }

    /**
     * Fills the altitude comboBox with the correct values and defines its Render
     * @param kmax
     */
    public void initAltitudeComboBox(int kmax){
        altitudeComboBox.removeAllItems();
        if (kmax < 2) {
            altitudeComboBox.addItem(-1) ;
        }
        else {
            for(int i = 0; i <= kmax; i++ ){
                altitudeComboBox.addItem(i);
            }
        }
    }

    /**
     * Initializes algoComboBox with either the algorithms or
     * an error message depending of if the graph has been imported
     * @param graphIsImported
     */
    public void initAlgoComboBox(boolean graphIsImported) {
        algoChoice.removeAllItems() ;
        if (graphIsImported) {
            algoChoice.addItem(ColoringUtilities.DSATUR);
            algoChoice.addItem(ColoringUtilities.RLF);
            algoChoice.addItem(ColoringUtilities.WELSH_POWELL);
        }
        else {
            algoChoice.addItem("INDISPONIBLE") ;
        }
    }

    /**
     * Initializes all comboBoxes in the menu
     * @param kMax
     * @param graphIsImported
     */
    public void initAllComboBoxes(int kMax, boolean graphIsImported) {
        initAlgoComboBox(graphIsImported) ;
        initAltitudeComboBox(kMax) ;
    }

    /**
     * Initializes the different Renderers needed
     */
    private void initRenders() {
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

                else if (value == -1) {
                    cell.setText("/!\\") ;
                    cell.setHorizontalAlignment(JLabel.CENTER) ;
                }

                else {
                    cell.setText(Integer.toString(value)) ;
                }

                return cell ;
            }
        });

        kmaxComboBox.setRenderer(new ListCellRenderer<Integer>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends Integer> list, Integer value, int index,
                boolean isSelected, boolean cellHasFocus) {
                    
                    JLabel cell = new JLabel() ;
                    if (value == 0 ) {
                        cell.setText("/!\\") ;
                        cell.setHorizontalAlignment(JLabel.CENTER) ;
                    }
                    else {
                        cell.setText(Integer.toString(value)) ;
                    }
                        
                    cell.setFont(new Font("Arial", Font.BOLD, 18)) ;
                    cell.setForeground(Color.WHITE);
                    cell.setBackground(Color.BLACK);

                    return cell ;

            }
        });
    }

    /**
     * Getter for the comboBox containing the kMax options
     * @return
     */
    public JComboBox<Integer> getKmaxComboBox() {
        return this.kmaxComboBox ;
    }

    /**
     * Getter for the comboBox containing the algorithms
     * @return
     */
    public JComboBox<String> getAlgoChoice() {
        return this.algoChoice ;
    }

    /**
     * Sets the range of values and the default value of the kMax comboBox in the menu
     * @param kMax new KMax value
     */
    public void initKmaxValues(int kMax) {
        this.kmaxComboBox.removeAllItems() ;
        for (int i = 2; i < (int)kMax*1.5 + Math.sqrt(kMax) ; i++) {
            this.kmaxComboBox.addItem(i) ;
        }

        this.kmaxComboBox.setSelectedItem(kMax) ;
    }

    /**
     * Getter for the current kMax chosen
     * @return
     */
    public Integer getCurrentKMax() {
        return (Integer)this.kmaxComboBox.getSelectedItem() ;
    }

    /**
     * Getter for the current algorithm chosen
     * @return
     */
    public String getCurrentAlgorithm() {
        return (String)this.algoChoice.getSelectedItem() ;
    }

    /**
     * Adds listeners to components
     */
    private void initListeners() {
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean colorationChanged = false ;
                TestGraph graph = app.getTestGraph() ;
                if (graph == null) {
                    return ;
                }
                int oldKmax = graph.getKMax() ;
                int currentKMax = (int)kmaxComboBox.getSelectedItem() ;
                graph.setKMax(currentKMax) ;
                
                // The algorithm used changed so we need to update the coloring
                if (lastAlgoSelected != (String)algoChoice.getSelectedItem()) {
                    if (lastAlgoSelected != null) {
                        ColoringUtilities.removeCurrentColoring(graph) ;
                    }
                    lastAlgoSelected = (String)algoChoice.getSelectedItem() ;
                    colorationChanged = true ;
                }
                
                // In case it didn't we check if it needs to be changed
                else {
                    // If the new KMax is smaller than the old one, if the coloration can be improved 
                    //or if the coloring has more colors than the currentKmax, then we change the coloration
                    if (oldKmax > currentKMax || oldKmax < currentKMax && graph.getNbConflicts() > 0 || graph.getNbColors() > currentKMax) {
                        colorationChanged = true ;
                    }
                }

                // Treatment is done here because too much indentation is ugly
                if (colorationChanged) {
                    if (lastAlgoSelected != null) {
                        ColoringUtilities.removeCurrentColoring(graph) ;
                    }
                    ColoringUtilities.colorGraphWithChosenAlgorithm(graph, (String)algoChoice.getSelectedItem()) ;
                    ColoringUtilities.setGraphStyle(graph, currentKMax) ;
                    NInfoGraphPanelApp panel = app.getMainScreen().getInfoGraphPanel() ;
                    panel.setNbColorsUsed(graph.getNbColors()) ;
                    panel.setNbConflictsOccurred(graph.getNbConflicts()) ;
                }
            }
        });
    }
    
    /**
     * Setter for the lastAlgoSelected field
     * @param lastAlgoSelected
     */
    public void setLastAlgoSelected(String lastAlgoSelected) {
        this.lastAlgoSelected = lastAlgoSelected ;
    }
}
