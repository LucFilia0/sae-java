package planeair.components;

//#region IMPORT
    //#region .SWING
    import javax.swing.Icon;
    import javax.swing.ImageIcon;
    import javax.swing.JButton;
    import javax.swing.JLabel;
    import javax.swing.JPanel;
    import javax.swing.Box;
    //#endregion

    //#region .AWT
    import java.awt.Dimension;
    import java.awt.event.ActionEvent;
    //#endregion

    //#region LAYOUT
    import java.awt.BorderLayout;
    import java.awt.GridLayout;
    import java.awt.Insets;
    import java.awt.GridBagConstraints;
    import javax.swing.BoxLayout;
    //#endregion

    //#region PLANEAIR
        import planeair.App;

        //#region COMPONENTS
        import planeair.components.comboboxes.NComboBoxGraph;
        import planeair.components.graphview.NMaxGraphFrame;
        import planeair.components.graphview.NMinGraphPanel;
        import planeair.components.mapview.Map;
        import planeair.components.menu.NMapMenuPanel;
        import planeair.components.menu.infos.NGraphInfoPanel;
        import planeair.components.menu.infos.NInfoPanel;
        import planeair.components.menu.NGraphMenuPanel;
        import planeair.components.time.NTimePanel;
        //#endregion
        
        //#region GRAPH
        import planeair.graph.graphtype.FlightsIntersectionGraph;
        import planeair.graph.graphtype.TestGraph;
        //#endregion
    //#endregion
//#endregion*

/**
 * This class creates the principal panel of the App where you can see the map and the graph
 * The class is one of the two Panel ADD in the JFrame (App)
 * 
 * @author GIRAUD Nila
 */
public class NMainScreen extends JPanel{

    //#region INSTANTIALISATION AND INITIALISATION

        //#region STRUCTURE
        /**
         * Panel wish will situe in the North of the borderLayout of the frame
         * nb LINE : 3 (PanelNav + HourPanelCenter)
         * nb COLUMN : 1
         * hgap : 0
         * vgap : 0
         */
        private JPanel header = new JPanel(new GridLayout(1,1,0,0));
        /**
         * Panel locate int the BODY of the Map borderLayout
         */
        // private JPanel body = new JPanel();
        /**
         * Panel locate int the ARTICLE of the Map borderLayout
         * Two MENU can appeair here
         * 1) NMenuGraphPanelApp
         * 2) NMenuMapPanelApp
         */
        private JPanel article = new JPanel();

        /**
         * Panel locate int the ASIDE of the Map borderLayout
         */
        private JPanel aside = new JPanel(new GridLayout());

        /**
         * Panel locate int th SOUTH of the Map borderLayout
         * Contain Info for mapWaipoint
         */
        private JPanel footer = new JPanel();

        /**
         * Panel situe in the CENTER of the Frame's borderLayout 
         * For view the map even if we have the different Panel
         */
        private Map map ;

        

        
        //#endregion

        //#region HEADER COMPONENTS

            //#region NAV PANEL
            /**
             * Layout's panel for Navigation bar (Button MENU  + LOGO and NAME of the APP)
             * nb LINE : 1
             * nb COLUMN : 3
             * hgap : 200
             * vgap : 0
             * Location : top of the frame (represent the header)
             */
            private JPanel panelNav = new JPanel(new GridLayout(1,3,0,0));
            //#endregion

            //#region NAV BUTTONS
                //#region PANEL
                /**
                 * Panel for put all buttons in the nav
                 * Grid
                 * LINE : 1
                 * COLUMN : 2 (menu + import)
                 * Location : left ine the header
                 */
                private JPanel panelButton = new JPanel();
                //#endregion

                //#region MENU GRAPH 
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
                //#endregion

                //#region MENU MAP
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
                //#endregion

                //#region IMPORTATION
                /**
                 * Icon for import button
                 */
                private Icon iconFolder = new ImageIcon("./icons/folder-input.png");
                /**
                 * Button for return with importButtons
                 * Location : third button in the nav
                 */
                private JButton leaveButtonToImport = new JButton(iconFolder);
                //#endregion
            //#endregion

            //#region NAME + LOGO
            /**
             * Label for App's name + Logo
             */
            private JLabel labelLogoName = new JLabel("Plane AIR",JLabel.CENTER);
            //#endregion

            //#region BOOLEAN
                /**
                 * A boolean 
                 * True if GraphMenu is Visible 
                 * Else false
                 */
                private boolean graphMenuIsVisible ;

                /**
                 * A boolean 
                 * True if MapMenu is Visible 
                 * Else false
                 */
                private boolean mapMenuIsVisible ;
                //#endregion
            
        //#endregion

        //#region BODY COMPONENTS

            //#region CENTER
            /**
             * The slider and comboBox for time
             * Localisation : Top of the center of the frame
             * The user can move time (because it's a simulation)
             */
            private NTimePanel timePanel;
            /**
             * JLabel for see the number altitudes choose
             * Location : in the panel menu --> need here for Events
             */
            private NComboBoxGraph kmaxComboBox = new NComboBoxGraph(); 
            //#endregion

            //#region LEFT
            /**
             * Menu for changing graph composition
             * Appear after push the button with the icon menu.png
             * Location : left in the frame
             */
            private NGraphMenuPanel graphMenu; 

            /**
             * Menu for changing graph composition
             * Appear after push the button with the icon menu.png
             * Location : left in the frame
             */
            private NMapMenuPanel mapMenu;
            //#endregion

            //#region RIGHT
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
        private NMinGraphPanel minGraphPanel;         
        /**
         * Info the graph show
         */
        private NGraphInfoPanel graphInfo ;
        /**
         * The panel which shows the informations of the clicked MapWaypoints
         */
        private NInfoPanel infoPanel;
        /**
         * A frame for NMaxGraphPanelApp
         * Put directly in the frame with information
         */
        @SuppressWarnings("unused")
        private NMaxGraphFrame maxGraphPanel; // DO NOT DELETE : Used in ActionListener
        //#endregion
    //#endregion

        //#region APP
        /**
         * Having acces to homePage (setVisible elements change)
         * the panel NPrincipalePanelApp is put in this frame
         */
        private App app; 
        //#endregion

    //#endregion

    //#region CONSTRUCTOR
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
    //#endregion

    //#region INIT
        //#region COMPONENTS
        private void initComponents() {

            // Make Info Panel principal
            this.infoPanel = new NInfoPanel();
            this.infoPanel.setPrincipal();

            //#region LAYOUT
            this.setLayout(new BorderLayout());
            map.setLayout(new BorderLayout());
            // footer.setLayout(new GridBagLayout());
            panelButton.setLayout(new BoxLayout(panelButton,BoxLayout.X_AXIS));
            //#endregion

            //#region HEADER 
            
                //#region COLOR
                panelNav.setBackground(App.KINDAYELLOW);
                panelButton.setBackground(App.KINDAYELLOW);
                //#endregion

                //#region BUTTONS MENU
                buttonMenuGraph.setBorderPainted(false);
                buttonMenuGraph.setContentAreaFilled(false);
                graphMenuIsVisible = false ;

                buttonMenuMap.setBorderPainted(false);
                buttonMenuMap.setContentAreaFilled(false);
                mapMenuIsVisible = false ;

                leaveButtonToImport.setBorderPainted(false);
                leaveButtonToImport.setContentAreaFilled(false);
                //#endregion

                //#region NAME + LOGO
                labelLogoName.setIcon(new ImageIcon("./icons/GraphFrance.png"));
                labelLogoName.setFont(App.KINDANOBLE);
                labelLogoName.setPreferredSize(new Dimension(WIDTH,70));
                //#endregion
            //#endregion

            //#region BODY 

                //#region ARTICLE
                // body.setOpaque(false);
                article.setOpaque(false);
                article.setPreferredSize(new Dimension(385,100));
                //#endregion

                //#region ASIDE
                minGraphPanel = new NMinGraphPanel(app);
                minGraphPanel.addComponents();

                graphLRightBottom.setLayout(new BoxLayout(graphLRightBottom, BoxLayout.Y_AXIS));
                graphLRightBottom.setOpaque(false);

                spaceBorderGraph.setOpaque(false);

                aside.setPreferredSize(new Dimension(385,100));
                aside.setOpaque(false);
                //#endregion

                //#region FOOTER
                footer.setOpaque(false);
                footer.setPreferredSize(new Dimension(this.getWidth(), 150));
                //#endregion
            //#endregion  
            
         }

        public void initMap() {
            this.app.getAirportSet().setActiveAirportsFrom((FlightsIntersectionGraph)this.app.getGraph()) ;
            this.map.paintAllAirports(this.app.getAirportSet());
        }
        //#endregion

        //#region GRAPH 
        /**
         * Fills the graphLRightBottom panel with all its components
         */
        public void initGraphBottomPanel() {
            graphLRightBottom.removeAll() ;
            graphLRightBottom.add(Box.createRigidArea(new Dimension(0, 10)));
            graphLRightBottom.add(minGraphPanel);
            graphLRightBottom.add(Box.createRigidArea(new Dimension(0, 10)));
            
            graphInfo = new NGraphInfoPanel(this.app);
            graphInfo.computeGraphStats() ;
            graphLRightBottom.add(this.graphInfo);

            graphLRightBottom.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        //#endregion

    //#endregion

    //#region ADD

        //#region COMPONENTS
        /**
         * Method adding components on the Panel
         */
        private void addComponents(){

            //#region HEADER COMPONENTS
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
            //#endregion

            //#region BODY
            graphMenu = new NGraphMenuPanel(app, 0, kmaxComboBox);
            mapMenu = new NMapMenuPanel(map);
            //#endregion
            
            //#region LEFT
            map.add(article,BorderLayout.WEST);
            //#endregion

            //#region RIGHT
            initGraphBottomPanel();
            aside.add(graphLRightBottom);
            //#endregion

            //#region FOOTER
            footer.add(infoPanel);
        

            //#endregion

            //#region ADD BORDER LAYOUT
            this.add(header, BorderLayout.NORTH);
            this.add(map, BorderLayout.CENTER);
            this.map.add(article,BorderLayout.WEST);
            this.map.add(aside,BorderLayout.EAST);
            this.map.add(footer, BorderLayout.SOUTH);
            //#endregion
        };
            
            
            
        //#endregion
    
        //#region TIME PANEL
        /**
         * Adds the NTimePanel only if the FIG is not null
         * 
         * @author Luc le Manifik
         */
        public void addTimePanel() {
            if(this.app.getGraphRenderer() != null && this.timePanel == null && app.getGraph() instanceof FlightsIntersectionGraph) {
                this.timePanel = new NTimePanel(this.app);
                map.add(timePanel, BorderLayout.CENTER);
            }else if(this.app.getGraphRenderer() != null && this.timePanel != null && app.getGraph() instanceof TestGraph) {
                map.remove(this.timePanel);
                this.timePanel = null;
            }
        }
        //#endregion

        //#region EVENTS
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

            //#endregion

    }
    //#endregion

    //#region GETTER / IS 

        //#region GETTER
        /**
         * Returns this panel's MinGraphPanel
         * @return
         */
        public NMinGraphPanel getMinGraphPanel() {
            return this.minGraphPanel ;
        }

        /**
         * Returns this panel's MenuGraphPanel
         * @return
         */
        public NGraphMenuPanel getGraphMenuPanel() {
            return this.graphMenu ;
        }

        public NMapMenuPanel getMapMenuPanel() {
            return this.mapMenu ;
        }

        /**
         * Returns this panel's InfoGraphPanel
         * @return
         */
        public NGraphInfoPanel getGraphInfoPanel() {
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
        //#endregion

        //#region IS
        public boolean isGraphMenuVisible() {
            return this.graphMenuIsVisible ;
        }

        public boolean isMapMenuVisible() {
            return this.mapMenuIsVisible ;
        }
        //#endregion
    
    //#endregion
}