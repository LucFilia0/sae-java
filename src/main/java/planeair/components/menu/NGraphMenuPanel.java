package planeair.components.menu;


// Import swing components
import javax.swing.JPanel;
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
import planeair.components.comboboxes.NComboBoxGraph;
import planeair.components.comboboxes.NComboBoxTime;
import planeair.components.comboboxes.renders.NDefaultRenderer;
import planeair.components.comboboxes.renders.NTimeComboBoxRenderer;
import planeair.graph.coloring.ColoringUtilities;
import planeair.graph.graphtype.FlightsIntersectionGraph;
import planeair.graph.graphtype.GraphSAE;
import planeair.graph.graphutil.PanelCreator;

/**
 * Class which create a JPanel of MENU for the graph 
 * Change parameter of the graph
 * Location : Left to the Frame
 * 
 * @author GIRAUD Nila
 */
public class NGraphMenuPanel extends JPanel{

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
    private NComboBoxGraph altitudeComboBox = new NComboBoxGraph();

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
    private NComboBoxTime marginComboBox = new NComboBoxTime(59, NGraphMenuPanel.KINDACOMBOBOXDIMENSION);

    /**
     * Panel for create an empty border to the JComboBox safety margin
     */
    private JPanel borderPanelMargin = new JPanel();

    //ALGORITHMS

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
    private NComboBoxGraph kmaxComboBox ;

    /**
     * String containing the last algo selected
     */
    private String lastAlgoSelected = null ;

    /**
     * Homepage blablabla
     */
    private App app ;


    /**
     * Constructor of NMenuPanelApp
     * @param kmax
     * @param kmaxComboBox 
     */
    public NGraphMenuPanel(App app, int kmax, NComboBoxGraph kmaxComboBox){

        this.app = app ;
        this.kmaxComboBox = kmaxComboBox ;

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
            kmaxComboBox.addItem(0) ;
            kmaxComboBox.setSelectedIndex(0) ;
        }
        initAltitudeComboBox(kmax);
        kmaxComboBox.setSelectedItem(kmax);
        

        kmaxOption.add(changeKmax);
        kmaxOption.add(kmaxComboBox);

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
        algoChoice.setPreferredSize(NGraphMenuPanel.KINDACOMBOBOXDIMENSION);

        algoOption.add(algorithmes);
        algoOption.add(algoChoice);

        borderPanelAlgo.setBackground(App.KINDAYELLOW);
        //#endregion

        //#region CONFIRM
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFont(App.KINDABOLD);
        confirmButton.setBackground(Color.BLACK);
        confirmButton.setPreferredSize(NGraphMenuPanel.KINDACOMBOBOXDIMENSION);
        layoutConfirm.setBackground(App.KINDAYELLOW);
        //#endregion

        initAlgoComboBox((app.getGraphRenderer() != null)) ;

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
        initRenderers() ;
    }

    /**
     * Fills the altitude comboBox with the correct values and defines its Render
     * @param kmax
     */
    public void initAltitudeComboBox(int kmax){
        altitudeComboBox.removeAllItems();
        if (kmax <= 0) {
            altitudeComboBox.addItem(-1) ;
        }
        else if (kmax == 1) {
            altitudeComboBox.addItem(1) ;
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
     * 
     * @param kMax
     * @param graphIsImported
     * 
     * @author Nathan LIEGEON
     */
    public void initAllComboBoxes(int kMax, boolean graphIsImported) {
        initAlgoComboBox(graphIsImported) ;
        initAltitudeComboBox(kMax) ;
        initKmaxValues(kMax) ;
    }

    /**
     * Initializes the different Renderers needed
     * 
     * @author Nathan LIEGEON
     */
    private void initRenderers() {
        altitudeComboBox.setRenderer(new NDefaultRenderer<Integer>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends Integer> list, Integer value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                JLabel cell = (JLabel)super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus) ;
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

        kmaxComboBox.setRenderer(new NDefaultRenderer<Integer>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends Integer> list, Integer value, int index,
                boolean isSelected, boolean cellHasFocus) {
                    
                    JLabel cell = (JLabel)super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus) ;
                    if (value == 0 ) {
                        cell.setText("/!\\") ;
                        cell.setHorizontalAlignment(JLabel.CENTER) ;
                    }
                    else {
                        cell.setText(Integer.toString(value)) ;
                    }

                    return cell ;

            }
        });

        // Default for time comboBoxes
        marginComboBox.setRenderer(new NTimeComboBoxRenderer(Color.WHITE, Color.BLACK)) ;

        algoChoice.setRenderer(new NDefaultRenderer<String>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                JLabel cell = (JLabel)super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus) ;

                cell.setText(value) ;
                return cell ;
            }
        });
    }

    /**
     * Getter for the comboBox containing the kMax options
     * @return
     */
    public NComboBoxGraph getKmaxComboBox() {
        return this.kmaxComboBox ;
    }

    /**
     * Getter for the comboBox containing the algorithms
     * @return
     */
    public JComboBox<String> getAlgoChoice() {
        return this.algoChoice ;
    }

    public NComboBoxGraph getAltitudesComboBox() {
        return this.altitudeComboBox ;
    }

    private int lastColorSelected ;

    /**
     * Sets the range of values and the default value of the kMax comboBox in the menu
     * @param kMax new KMax value
     */
    public void initKmaxValues(int kMax) {
        this.kmaxComboBox.removeAllItems() ;
        if (kMax > 0) {
            for (int i = 1; i < (int)kMax*1.5 + Math.sqrt(kMax) ; i++) {
                this.kmaxComboBox.addItem(i) ;
            }

            this.kmaxComboBox.setSelectedItem(kMax) ;
        }

        else {
            kmaxComboBox.addItem(0) ;
        }
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

    public Integer getLastColorSelected() {
        return this.lastColorSelected ;
    }

    /**
     * Adds listeners to components
     */
    private void initListeners() {
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lastColorSelected = (int)altitudeComboBox.getSelectedItem() ;
                changeColoring(app.getGraph()) ;
                changeColorShown(app.getGraphRenderer()) ;
            }
        });

        kmaxComboBox.addActionListener(e -> {
            if (kmaxComboBox.getSelectedItem() != null) {
                initAltitudeComboBox((int)kmaxComboBox.getSelectedItem()) ;
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

    /**
     * Changes the coloring of this graph best on the kmax and algorithm selected
     * @param graph
     * 
     * @author Nathan LIEGEON
     */
    private void changeColoring(GraphSAE graph) {
        boolean coloringChanged = false ;
        if (graph == null) {
            return ;
        }
        int oldKMax = graph.getKMax() ;
        int currentKMax = (int)kmaxComboBox.getSelectedItem() ;
        graph.setKMax(currentKMax) ;
        
        // The algorithm used changed so we need to update the coloring
        if (lastAlgoSelected != (String)algoChoice.getSelectedItem()) {
            lastAlgoSelected = (String)algoChoice.getSelectedItem() ;
            coloringChanged = true ;
        }
        
        // In case we didn't check if it needs to be changed
        else {
            // If the new KMax is smaller than the old one, if the coloration can be improved 
            // or if the coloring has more colors than the currentKmax, then we change the coloration
            if (oldKMax > currentKMax || oldKMax < currentKMax && graph.getNbConflicts() > 0 || graph.getNbColors() > currentKMax) {
                coloringChanged = true ;
            }
        }

        // Treatment is done here because too much indentation is ugly
        if (coloringChanged) {
            if (lastAlgoSelected != null) {
                ColoringUtilities.removeCurrentColoring(graph) ;
            }
            ColoringUtilities.colorGraphWithChosenAlgorithm(graph, (String)algoChoice.getSelectedItem()) ;
            if (graph instanceof FlightsIntersectionGraph) {
                graph.setKMax(graph.getNbColors()) ;
                currentKMax = graph.getKMax() ;
            }
            ColoringUtilities.setGraphStyle(graph, currentKMax) ;
            NGraphInfoPanel panel = app.getMainScreen().getGraphInfoPanel() ;
            panel.setNbColorsUsed(graph.getNbColors()) ;
            panel.setNbConflictsOccurred(graph.getNbConflicts()) ;
        }
    }

    /**
     * Changes the color shown based on the value in the altitudeComboBox
     * @param graphRenderer
     * 
     * @author Nathan LIEGEON
     */
    private void changeColorShown(PanelCreator graphRenderer) {
        GraphSAE graph = graphRenderer.getGraph() ;
        graph.showAllNodes() ;
        if (lastColorSelected != 0) {
            // Do not question, blame graphstream instead
            graph.showNodesWithColor(lastColorSelected) ;
            graph.showNodesWithColor(lastColorSelected) ;
        }
    }
}
