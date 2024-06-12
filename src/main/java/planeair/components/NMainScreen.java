package planeair.components;

// Import of SWING components
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Box;
// Import of AWT components
import java.awt.Dimension;
import java.awt.event.ActionEvent;

// Import of LAYOUT
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;

import planeair.App;
import planeair.components.graphview.NMaxGraphFrameApp;
import planeair.components.graphview.NMinGraphPanelApp;
import planeair.components.mapview.Map;
import planeair.components.menu.NInfoGraphPanelApp;
import planeair.components.menu.NMenuGraphPanelApp;
import planeair.components.menu.NMenuMapPanelApp;
import planeair.components.time.NTimePanelApp;
import planeair.graph.graphtype.FlightsIntersectionGraph;
import planeair.graph.graphtype.TestGraph;


/**
 * This class creates the principal panel of the App where you can see the map and the graph
 * 
 * @author GIRAUD Nila
 */
public class NMainScreen extends JPanel{

    //STRUCT

    /**
     * Panel wish will situe in the North of the borderLayout of the frame
     * nb LINE : 3 (PanelNav + HourPanelCenter + HourSliderPanel)
     * nb COLUMN : 1
     * hgap : 0
     * vgap : 0
     */
    private JPanel header = new JPanel(new GridLayout(1,1,0,0));

    /**
     * Panel situe in the CENTER of the Frame's borderLayout 
     * For view the map even if we have the different Panel
     */
    private Map map ;

    /**
     * Layout for the BorderLayout CENTER of body
     */
    private JPanel bodyCenter = new JPanel();

    /**
     * Panel situe in the WEST of the body's borderLayout
     * Two MENU can appeair here
     * 1) NMenuGraphPanelApp
     * 2) NMenuMapPanelApp
     */
    private JPanel article = new JPanel();

    /**
     * Panel situe in the EAST of the body's borderLayout
     */
    private JPanel aside = new JPanel(new GridLayout());

    // HEADER COMPONENTS 
    
    /**
     * Layout's panel for Navigation bar (Button MENU  + LOGO and NAME of the APP)
     * nb LINE : 1
     * nb COLUMN : 3
     * hgap : 200
     * vgap : 0
     * Location : top of the frame (represent the header)
     */
    private JPanel panelNav = new JPanel(new GridLayout(1,3,0,0));

    /**
     * Panel for put all buttons in the nav
     * Grid
     * LINE : 1
     * COLUMN : 2 (menu + import)
     * Location : left ine the header
     */
    private JPanel panelButton = new JPanel();

    /**
     * Icon of the Button menu graph
     * Description : A cross
     * Source : ./src/main/java/close.png
     */
    private Icon iconCloseGraph = new ImageIcon("./icons/close.png");

     /**
     * Icon of the Button menu Graph
     * Description : three horizontal lines
     * Source : ./src/main/java/menu.png
     */
    private Icon iconMenuGraph = new ImageIcon("./icons/graph.png");
    /**
     * Button with their Icon
     * Location : first button in the nav
     */
    private JButton buttonMenuGraph = new JButton(iconMenuGraph);

    /**
     * Icon of the Button menu Map
     * Description :  A cross
     * Source : ./src/main/java/close.png
     */
    private Icon iconCloseMap = new ImageIcon("./icons/close.png");
     /**
     * Icon of the Button menu Map
     * Description : A mini Map
     * Source : ./src/main/java/map.png
     */
    private Icon iconMenuMap = new ImageIcon("./icons/map.png");
    /**
     * Button with their Icon
     * Location : second button in the nav
     */
    private JButton buttonMenuMap = new JButton(iconMenuMap);

    /**
     * Icon for import button
     */
    private Icon iconFolder = new ImageIcon("./icons/folder-input.png");
    /**
     * Button for return with importButtons
     * Location : third button in the nav
     */
    private JButton leaveButtonToImport = new JButton(iconFolder);

    /**
     * Label for App's name + Logo
     */
    private JLabel labelLogoName = new JLabel("Plane AIR",JLabel.CENTER);

    private boolean graphMenuIsVisible ;

    private boolean mapMenuIsVisible ;


    /*BODY COMPONENTS*/

    // CENTER

    /**
     * The slider and comboBox for time
     * Localisation : Top of the center of the frame
     * The user can move time (because it's a simulation)
     */
    private NTimePanelApp timePanel;
    /**
     * JLabel for see the number altitudes choose
     * Location : in the panel menu --> need here for Events
     */
    private JComboBox<Integer> choixAltitudesMax = new JComboBox<>(); 

     // LEFT 
    /**
     * Menu for changing graph composition
     * Appear after push the button with the icon menu.png
     * Location : left in the frame
     */
    private NMenuGraphPanelApp graphMenu; 

    /**
     * Menu for changing graph composition
     * Appear after push the button with the icon menu.png
     * Location : left in the frame
     */
    private NMenuMapPanelApp mapMenu;

    //RIGHT

    /**
     * Contain the panel of MinGraph + InfoGraph
     * Add Border
     */
    private JPanel spaceBorderGraph = new JPanel();
    /**
     * Contain an Empty Panel and spaceBorderButtonImport
     * Forcing bottom
     */
    private JPanel graphLRightBottom = new JPanel();
    /**
     * Panel for min graphPanel
     */
    private NMinGraphPanelApp minGraphPanel; 
    /**
     * Expand Button for graph
     * Open a new Frame with the graph and this information
     * Location : in the panel Mingraph --> need here for Events
     */
    private JButton buttonAgr = new JButton("AGRANDIR");
    /**
     * Info the graph show
     */
    private NInfoGraphPanelApp graphInfo ;
    /**
     * A frame for NMaxGraphPanelApp
     * Put directly in the frame with information
     */
    private NMaxGraphFrameApp maxGraphPanel; //maybe transform the class to JFrame directly

    /**
     * Having acces to homePage (setVisible elements change)
     * the panel NPrincipalePanelApp is put in this frame
     */
    private App app; 

    /**
     * Constructor of NPrincipalePanelApp
     * @param app the frame where this panel is put
     */
    public NMainScreen (App app){

        this.app = app;
        this.map = new Map(app) ;

        this.initComponents();
        this.addComponents();
        this.addEvents();

    }

    private void initComponents() {

        this.setLayout(new BorderLayout());

        map.setLayout(new BorderLayout());
        panelButton.setLayout(new BoxLayout(panelButton,BoxLayout.X_AXIS));

        bodyCenter.setLayout(new BoxLayout(bodyCenter, BoxLayout.Y_AXIS));

        // HEADER COMPONENTS
        
        //Color of the header's panel
        panelNav.setBackground(App.KINDAYELLOW);
        panelButton.setBackground(App.KINDAYELLOW);

        // Set ButtonsMenu
        buttonMenuGraph.setBorderPainted(false);
        buttonMenuGraph.setContentAreaFilled(false);
        graphMenuIsVisible = false ;

        buttonMenuMap.setBorderPainted(false);
        buttonMenuMap.setContentAreaFilled(false);
        mapMenuIsVisible = false ;

        leaveButtonToImport.setBorderPainted(false);
        leaveButtonToImport.setContentAreaFilled(false);

        labelLogoName.setIcon(new ImageIcon("./icons/GraphFrance.png"));
        labelLogoName.setFont(App.KINDANOBLE);
        labelLogoName.setPreferredSize(new Dimension(WIDTH,70));

        // BODY COMPONENTS

        //ARTICLE
        bodyCenter.setOpaque(false);
        article.setOpaque(false);
        article.setPreferredSize(new Dimension(385,100));

        //ASIDE
        minGraphPanel = new NMinGraphPanelApp(app, buttonAgr);
        minGraphPanel.addComponents();

        graphLRightBottom.setLayout(new BoxLayout(graphLRightBottom, BoxLayout.Y_AXIS));
        graphLRightBottom.setOpaque(false);

        spaceBorderGraph.setOpaque(false);

        aside.setPreferredSize(new Dimension(385,100));
        aside.setOpaque(false);
    }

    /**
     * Method adding components on the Panel
     */
    private void addComponents(){

        //HEADER COMPOSANTS
        panelButton.add(Box.createRigidArea(new Dimension(102, 0)));
        panelButton.add(buttonMenuGraph);
        panelButton.add(buttonMenuMap);
        panelButton.add(leaveButtonToImport);

       
        panelNav.add(panelButton);
        panelNav.add(labelLogoName);

        JPanel empty = new JPanel();
        empty.setBackground(App.KINDAYELLOW);
        panelNav.add(empty);

        header.add(panelNav);

        //BODY

        graphMenu = new NMenuGraphPanelApp(app, 0, choixAltitudesMax);
        mapMenu = new NMenuMapPanelApp(map);
        
        // LEFT
        map.add(article,BorderLayout.WEST);

        //RIGHT
        initGraphBottomPanel();
        aside.add(graphLRightBottom);
        map.add(aside,BorderLayout.EAST);

        //ADD structure to BorderLayout
        this.add(header, BorderLayout.NORTH);
        this.add(map, BorderLayout.CENTER);
    };

    /**
     * Adds the NTimePanel only if the FIG is not null
     * 
     * @author Luc le Manifik
     */
    public void addTimePanel() {
        if(this.app.getGraphRenderer() != null && this.timePanel == null && app.getGraph() instanceof FlightsIntersectionGraph) {
            this.timePanel = new NTimePanelApp(this.app);
            map.add(timePanel, BorderLayout.CENTER);
        }else if(this.app.getGraphRenderer() != null && this.timePanel != null && app.getGraph() instanceof TestGraph) {
            map.remove(this.timePanel);
            this.timePanel = null;
        }
    }

    /**
     * Method adding events of the Panel
     */
    private void addEvents(){

        buttonMenuGraph.addActionListener((ActionEvent e) -> {

            if(!graphMenuIsVisible){
                graphMenuIsVisible = true ;
                if(!mapMenuIsVisible){

                    GridBagConstraints GridBagC = new GridBagConstraints(); 
                    GridBagC.insets = new Insets(0, 10, 10, 0);
                    article.add(graphMenu, GridBagC);
                    article.paintComponents(article.getGraphics());

                    buttonMenuGraph.setIcon(iconCloseGraph);
                    this.app.revalidate();
                    this.repaint() ;
                }
                else{
                    mapMenuIsVisible = false ;
                    map.remove(article);
                    this.app.setVisible(true);
                    article.removeAll();

                    GridBagConstraints GridBagC = new GridBagConstraints(); 
                    GridBagC.insets = new Insets(0, 10, 10, 0);
                    article.add(graphMenu, GridBagC);
                    article.paintComponents(article.getGraphics());

                    map.add(article,BorderLayout.WEST);

                    buttonMenuMap.setIcon(iconMenuMap);
                    buttonMenuGraph.setIcon(iconCloseGraph);

                    this.app.revalidate();
                    this.repaint() ;
                }               
            }
            else{
                graphMenuIsVisible = false ;
                map.remove(article);
                this.app.setVisible(true);
                article.removeAll();
                map.add(article,BorderLayout.WEST); 
                this.app.setVisible(true);
                buttonMenuGraph.setIcon(iconMenuGraph);
                this.repaint() ;
            }

        });

        buttonMenuMap.addActionListener((ActionEvent e) -> {

            if(!mapMenuIsVisible){
                mapMenuIsVisible = true ;
                if(!graphMenuIsVisible){
                    article.add(mapMenu);
                    article.paintComponents(article.getGraphics());
;
                    buttonMenuMap.setIcon(iconCloseMap);
                    this.app.revalidate();
                    this.repaint() ;
                }
                else{
                    graphMenuIsVisible = false ;
                    map.remove(article);
                    this.app.setVisible(true);
                    article.removeAll();

                    GridBagConstraints GridBagC = new GridBagConstraints(); 
                    GridBagC.insets = new Insets(0, 10, 10, 0);
                    article.add(mapMenu, GridBagC);
                    article.paintComponents(article.getGraphics());

                    map.add(article,BorderLayout.WEST);

                    buttonMenuGraph.setIcon(iconMenuGraph);
                    buttonMenuMap.setIcon(iconCloseMap);
                    this.app.revalidate();
                    this.repaint() ;

                }               
            }
            else{
                mapMenuIsVisible = false ;
                map.remove(article);
                this.app.setVisible(true);
                article.removeAll();
                map.add(article,BorderLayout.WEST); 
                this.app.setVisible(true);
                buttonMenuMap.setIcon(iconMenuMap);
                this.repaint() ;
            }

        });

        leaveButtonToImport.addActionListener((ActionEvent e) ->{
            this.app.switchToImportScreen();
            this.app.setVisible(true);
            this.map.clearAll();
        });

        buttonAgr.addActionListener((ActionEvent e) -> {
            maxGraphPanel = new NMaxGraphFrameApp(app, app.getGraphRenderer(), graphInfo);
        });

    }

    /**
     * Returns this panel's MinGraphPanel
     * @return
     */
    public NMinGraphPanelApp getMinGraphPanel() {
        return this.minGraphPanel ;
    }

    /**
     * Returns this panel's MenuGraphPanel
     * @return
     */
    public NMenuGraphPanelApp getGraphMenuPanel() {
        return this.graphMenu ;
    }

    public NMenuMapPanelApp getMapMenuPanel() {
        return this.mapMenu ;
    }

    /**
     * Returns this panel's InfoGraphPanel
     * @return
     */
    public NInfoGraphPanelApp getGraphInfoPanel() {
        return this.graphInfo ;
    }

    /**
     * Returns the Map of the NMainScreen
     * 
     * @return The {@link planeair.components.mapview.Map Map} of the NMainScreen
     */
    public Map getMap() {
        return this.map;
    }

    public boolean isGraphMenuVisible() {
        return this.graphMenuIsVisible ;
    }

    public boolean isMapMenuVisible() {
        return this.mapMenuIsVisible ;
    }
    
    public void initMap() {
        this.app.getAirportSet().setActiveAirportsFrom((FlightsIntersectionGraph)this.app.getGraph()) ;
        this.map.paintAllAirports(this.app.getAirportSet());
    }

    /**
     * Fills the graphLRightBottom panel with all its components
     */
    public void initGraphBottomPanel() {
        graphLRightBottom.removeAll() ;
        graphLRightBottom.add(Box.createRigidArea(new Dimension(0, 10)));
        graphLRightBottom.add(minGraphPanel);
        graphLRightBottom.add(Box.createRigidArea(new Dimension(0, 10)));

        graphInfo = new NInfoGraphPanelApp(app);
        graphInfo.addComponents();
        graphInfo.computeGraphStats() ;
        graphLRightBottom.add(graphInfo);
        graphLRightBottom.add(Box.createRigidArea(new Dimension(0, 10)));
    }
}