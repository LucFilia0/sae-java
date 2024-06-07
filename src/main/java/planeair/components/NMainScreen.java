package planeair.components;

// Import of SWING composants
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Box;
// Import of AWT composants
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
     * The body of the NMainScreen, where the Map is located, and where the popup menus appears
     * 
     * @author Luc le Manifik
     */
    private JPanel body = new JPanel(new BorderLayout());

    /**
     * Panel situe in the CENTER of the Frame's borderLayout 
     * For view the map even if we have the different Panel
     */
    private Map map = new Map();

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

    // HEADER COMPOSANTS 
    
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
    private JPanel panelButton = new JPanel(new GridLayout(1,3));

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


    /*BODY COMPOSANTS*/

    // CENTER

    /**
     * The slider and comboBox for time
     * Localisation : Top of the center of the frame
     * The user can move time (because it's a simulation)
     */
    private NTimePanelApp timePanel = new NTimePanelApp();
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
    private NMenuGraphPanelApp menuGraph; 

    /**
     * Menu for changing graph composition
     * Appear after push the button with the icon menu.png
     * Location : left in the frame
     */
    private NMenuMapPanelApp menuMap;

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
    private NInfoGraphPanelApp infoGraph ;
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

        this.initComponents();
        this.addComponents();
        this.addEvents();

    }

    private void initComponents() {

        this.setLayout(new BorderLayout());

        this.body.setLayout(new BorderLayout());

        this.menuGraph = new NMenuGraphPanelApp(this.app, ABORT, choixAltitudesMax);

        map.setLayout(new BorderLayout());

        bodyCenter.setLayout(new BoxLayout(bodyCenter, BoxLayout.Y_AXIS));

        // HEADER COMPONENTS
        
        //Color of the header's panel
        panelNav.setBackground(App.KINDAYELLOW);
        panelButton.setBackground(App.KINDAYELLOW);

        // Set ButtonsMenu
        buttonMenuGraph.setBorderPainted(false);
        buttonMenuGraph.setContentAreaFilled(false);

        buttonMenuMap.setBorderPainted(false);
        buttonMenuMap.setContentAreaFilled(false);

        leaveButtonToImport.setBorderPainted(false);
        leaveButtonToImport.setContentAreaFilled(false);

        labelLogoName.setIcon(new ImageIcon("./icons/GraphFrance.png"));
        labelLogoName.setFont(App.KINDANOBLE);
        labelLogoName.setPreferredSize(new Dimension(WIDTH,70));

        // BODY COMPONENTS

        // CENTER
        timePanel.addComponents();
        timePanel.addEvents();

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

        //HEADER COMPONENTS
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
        map.add(timePanel, BorderLayout.CENTER);

        menuGraph = new NMenuGraphPanelApp(app, 0, choixAltitudesMax);
        menuMap = new NMenuMapPanelApp(map);
        
        // LEFT
        map.add(article,BorderLayout.WEST);

        //RIGHT
        initGraphBottomPanel();
        aside.add(graphLRightBottom);
        body.add(aside,BorderLayout.EAST);
        map.add(aside,BorderLayout.EAST);

        //ADD structure to BorderLayout
        body.add(header, BorderLayout.NORTH);
        body.add(map, BorderLayout.CENTER);

        this.add(body, BorderLayout.CENTER);
    };

    /**
     * Method adding events of the Panel
     */
    private void addEvents(){

        buttonMenuGraph.addActionListener((ActionEvent e) -> {

            if(buttonMenuGraph.getIcon().equals(iconMenuGraph)){

                if(buttonMenuMap.getIcon().equals(iconMenuMap)){

                GridBagConstraints GridBagC = new GridBagConstraints(); 
                GridBagC.insets = new Insets(0, 10, 10, 0);
                article.add(menuGraph, GridBagC);
                article.paintComponents(article.getGraphics());

                buttonMenuGraph.setIcon(iconCloseGraph);
                this.app.revalidate();
                }
                else{
                    map.remove(article);
                    this.app.setVisible(true);
                    article.removeAll();

                    GridBagConstraints GridBagC = new GridBagConstraints(); 
                    GridBagC.insets = new Insets(0, 10, 10, 0);
                    article.add(menuGraph, GridBagC);
                    article.paintComponents(article.getGraphics());

                    map.add(article,BorderLayout.WEST);

                    buttonMenuMap.setIcon(iconMenuMap);
                    buttonMenuGraph.setIcon(iconCloseGraph);

                    this.app.revalidate();
                }               
            }
            else{
                map.remove(article);
                this.app.setVisible(true);
                article.removeAll();
                map.add(article,BorderLayout.WEST); 
                this.app.setVisible(true);
                buttonMenuGraph.setIcon(iconMenuGraph);
            }

        });

        buttonMenuMap.addActionListener((ActionEvent e) -> {

            if(buttonMenuMap.getIcon().equals(iconMenuMap)){

                if(buttonMenuGraph.getIcon().equals(iconMenuGraph)){
                    article.add(menuMap);
                    article.paintComponents(article.getGraphics());
;
                    buttonMenuMap.setIcon(iconCloseMap);
                    this.app.revalidate();
                }
                else{
                    map.remove(article);
                    this.app.setVisible(true);
                    article.removeAll();

                    GridBagConstraints GridBagC = new GridBagConstraints(); 
                    GridBagC.insets = new Insets(0, 10, 10, 0);
                    article.add(menuMap, GridBagC);
                    article.paintComponents(article.getGraphics());

                    map.add(article,BorderLayout.WEST);

                    buttonMenuGraph.setIcon(iconMenuGraph);
                    buttonMenuMap.setIcon(iconCloseMap);
                    this.app.revalidate();

                }               
            }
            else{
                map.remove(article);
                this.app.setVisible(true);
                article.removeAll();
                map.add(article,BorderLayout.WEST); 
                this.app.setVisible(true);
                buttonMenuMap.setIcon(iconMenuMap);
            }

        });

        leaveButtonToImport.addActionListener((ActionEvent e) ->{
                this.app.switchToImportScreen();
                this.app.setVisible(true);
        });

        buttonAgr.addActionListener((ActionEvent e) -> {
            maxGraphPanel = new NMaxGraphFrameApp(app, app.getTestGraphRenderer(), infoGraph);
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
    public NMenuGraphPanelApp getMenuGraphPanel() {
        return this.menuGraph ;
    }

    /**
     * Returns this panel's InfoGraphPanel
     * @return
     */
    public NInfoGraphPanelApp getInfoGraphPanel() {
        return this.infoGraph ;
    }
    
    public void initMap() {
        this.app.getAirportSet().setActiveAirportsFrom(this.app.getFig());
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

        infoGraph = new NInfoGraphPanelApp(app);
        infoGraph.addComponents();
        infoGraph.computeGraphStats() ;
        graphLRightBottom.add(infoGraph);
        graphLRightBottom.add(Box.createRigidArea(new Dimension(0, 10)));
    }
}