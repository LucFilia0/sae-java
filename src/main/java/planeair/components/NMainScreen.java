package planeair.components;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Box;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;

import planeair.App;
import planeair.components.comboboxes.NComboBoxGraph;
import planeair.components.graphview.NMaxGraphFrame;
import planeair.components.graphview.NMinGraphPanel;
import planeair.components.mapview.Map;
import planeair.components.menu.NMapMenuPanel;
import planeair.components.menu.infos.NGraphInfoPanel;
import planeair.components.menu.infos.NInfoPanel;
import planeair.components.menu.NGraphMenuPanel;
import planeair.components.time.NTimePanel;

import planeair.graph.graphtype.FlightsIntersectionGraph;
import planeair.graph.graphtype.TestGraph;
//#endregion*

/**
 * This class creates the amin panel of the App where you can see the map and the graph of choosen files
 * The class is one of the two Panel ADD directely in the JFrame (App)
 * 
 * @author GIRAUD Nila
 */
public class NMainScreen extends JPanel {

    //#region INSTANTIALISATION AND INITIALISATION

        //#region STRUCTURE
        /**
         * Locate in the North of the borderLayout of the frame
         * nb LINE : 3 (PanelNav + HourPanelCenter)
         * nb COLUMN : 1
         * hgap : 0
         * vgap : 0
         */
        private JPanel header = new JPanel(new GridLayout(1,1,0,0));

        /**
         * Locate in the WEST of the body's borderLayout
         * Two MENU can appear here
         * 1) NMenuGraphPanel
         * 2) NMenuMapPanel
         */
        private JPanel article = new JPanel();

        /**
         * Locate int the EAST of the Map borderLayout
         * Two panel appear here
         * 1) NMinGraphPanel
         * 2) NGraphInfoPanel
         */
        private JPanel aside = new JPanel(new GridLayout());

        /**
         * Locate int th SOUTH of the Map borderLayout
         * Contain Info for mapWaipoint
         */
        private JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT));

        /**
         * Locate in the CENTER of the Frame's borderLayout 
         * For view the map even if we have the different Panel
         */
        private Map map ;

        //#endregion

        //#region HEADER COMPONENTS

            //#region NAV PANEL
            /**
             * Layout's panel for Navigation bar (Button MENU  + LOGO and NAME of the App)
             * nb LINE : 1
             * nb COLUMN : 3
             * hgap : 200
             * Location : top of the frame (represent the header)
             */
            private JPanel panelNav = new JPanel(new GridLayout(1,3));
            //#endregion

            //#region NAV BUTTONS
                //#region PANEL
                /**
                 * Put all buttons in the nav
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
             * App's name + Logo
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
             * Panel for timePanel
             * Override PaintComponent() --> Center it
             */
            private JPanel bodyCenter;
            /**
             * See the number altitudes choose
             * Location : in the panel menu --> need here for Events
             */
            private NComboBoxGraph kmaxComboBox = new NComboBoxGraph(); 
            //#endregion

            //#region LEFT
            /**
             * Menu for changing graph composition
             * Appear after push the button with the icon graph.png
             * Location : left in the frame
             */
            private NGraphMenuPanel graphMenu; 

            /**
             * Menu for changing map composition
             * Appear after push the button with the icon map.png
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

    //#endregion

    //#region CONSTRUCTOR
    /**
     * Constructor of NMainScreen
     */
    public NMainScreen (){

        this.map = new Map() ;

        this.initComponents();
        this.addComponents();
        this.addEvents();

    }
    //#endregion

    //#region INIT

        //#region COMPONENTS
        /**
         * 
         */
        private void initComponents() {

            // Make Info Panel principal
            this.infoPanel = new NInfoPanel();
            this.infoPanel.setPrincipal();

            //#region LAYOUT
            this.setLayout(new BorderLayout());
            map.setLayout(new BorderLayout());
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

                //#region CENTER

                //#endregion
                bodyCenter = new JPanel(){
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        bodyCenter.setBounds(NMainScreen.this.getWidth()/2-(bodyCenter.getWidth()/2) + 5,0,bodyCenter.getWidth(),bodyCenter.getHeight()); //butiful
                        bodyCenter.validate();
                    }
                };

                bodyCenter.setOpaque(false);
                
                //#region ARTICLE
                // body.setOpaque(false);
                article.setOpaque(false);
                article.setPreferredSize(new Dimension(385,100));
                //#endregion

                //#region ASIDE
                minGraphPanel = new NMinGraphPanel();
                minGraphPanel.addComponents();

                graphLRightBottom.setLayout(new BoxLayout(graphLRightBottom, BoxLayout.Y_AXIS));
                graphLRightBottom.setOpaque(false);

                spaceBorderGraph.setOpaque(false);

                aside.setPreferredSize(new Dimension(385,100));
                aside.setOpaque(false);
                //#endregion

                //#region FOOTER
                footer.setOpaque(false);
                footer.setPreferredSize(new Dimension(this.getWidth(), 140));
                //#endregion
            //#endregion  
            
         }

        /**
         * Initiates the Map located at the center of the NMainScreen
         * 
         * @author Luc le Manifik
         */
        public void initMap() {
            App.app.getAirportSet().setActiveAirportsFrom((FlightsIntersectionGraph)App.app.getGraph()) ;
            this.map.addAirports(App.app.getAirportSet());
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
            
            graphInfo = new NGraphInfoPanel();
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
            
            
            //#endregion
            
            //#region LEFT
            graphMenu = new NGraphMenuPanel(App.app, 0, kmaxComboBox);
            mapMenu = new NMapMenuPanel(map);
            map.add(article,BorderLayout.WEST);
            //#endregion

            //#region RIGHT
            initGraphBottomPanel();
            aside.add(graphLRightBottom);
            //#endregion

            //#region FOOTER
            JPanel emptyfooter = new JPanel();
            emptyfooter.setPreferredSize(new Dimension(5,footer.getHeight()));
            footer.add(emptyfooter);
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
            if(App.app.getGraphRenderer() != null && this.timePanel == null && App.app.getGraph() instanceof FlightsIntersectionGraph) {
                this.timePanel = new NTimePanel();
                bodyCenter.add(timePanel);
                map.add(this.bodyCenter, BorderLayout.CENTER);
            }else if(App.app.getGraphRenderer() != null && this.timePanel != null && App.app.getGraph() instanceof TestGraph) {
                bodyCenter.remove(timePanel);
                map.remove(this.bodyCenter);
                this.timePanel = null;
            }
        }

        public void refreshTime() {
            if(this.timePanel != null) {
                int value = this.timePanel.getSliderTime().getValue();
                this.timePanel.getSliderTime().setValue(12);
                this.timePanel.getSliderTime().setValue(value); // Briganderie
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
                        App.app.revalidate();
                        this.repaint() ;
                    }
                    else{
                        mapMenuIsVisible = false ;
                        map.remove(article);
                        App.app.setVisible(true);
                        article.removeAll();

                        GridBagConstraints GridBagC = new GridBagConstraints(); 
                        GridBagC.insets = new Insets(0, 10, 10, 0);
                        article.add(graphMenu, GridBagC);
                        article.paintComponents(article.getGraphics());

                        map.add(article,BorderLayout.WEST);

                        buttonMenuMap.setIcon(iconMenuMap);
                        buttonMenuGraph.setIcon(iconCloseGraph);

                        App.app.revalidate();
                        this.repaint() ;
                    }               
                }
                else{
                    graphMenuIsVisible = false ;
                    map.remove(article);
                    App.app.setVisible(true);
                    article.removeAll();
                    map.add(article,BorderLayout.WEST); 
                    App.app.setVisible(true);
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
                        App.app.revalidate();
                        this.repaint() ;
                    }
                    else{
                        graphMenuIsVisible = false ;
                        map.remove(article);
                        App.app.setVisible(true);
                        article.removeAll();

                        GridBagConstraints GridBagC = new GridBagConstraints(); 
                        GridBagC.insets = new Insets(0, 10, 10, 0);
                        article.add(mapMenu, GridBagC);
                        article.paintComponents(article.getGraphics());

                        map.add(article,BorderLayout.WEST);

                        buttonMenuGraph.setIcon(iconMenuGraph);
                        buttonMenuMap.setIcon(iconCloseMap);
                        App.app.revalidate();
                        this.repaint() ;

                    }               
                }
                else{
                    mapMenuIsVisible = false ;
                    map.remove(article);
                    App.app.setVisible(true);
                    article.removeAll();
                    map.add(article,BorderLayout.WEST); 
                    App.app.setVisible(true);
                    buttonMenuMap.setIcon(iconMenuMap);
                    this.repaint() ;
                }

            });

            leaveButtonToImport.addActionListener((ActionEvent e) ->{
                App.app.switchToImportScreen();
                App.app.setVisible(true);
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
         * The InfoPanel containing the information of the selected waypoint
         * @return the panel in question
         */
        public NInfoPanel getInfoPanel() {
            return this.infoPanel;
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
        /**
         * Returns whether or not the graph menu is visible to the user
         * @return True if it is, false if it isn't
         */
        public boolean isGraphMenuVisible() {
            return this.graphMenuIsVisible ;
        }

        /**
         * Returns whether or not the map menu is visible to the user
         * @return True if it is, false if it isn't
         */
        public boolean isMapMenuVisible() {
            return this.mapMenuIsVisible ;
        }
        //#endregion
    
    //#endregion
}