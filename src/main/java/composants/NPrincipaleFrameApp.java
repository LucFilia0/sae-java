package composants;


// Import of SWING composants
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ihm.Map;

import javax.swing.GroupLayout.Alignment;
import javax.swing.Box;

// Import of AWT composants
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Import of LAYOUT
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BoxLayout;






public class NPrincipaleFrameApp extends JPanel{

    //STRUCT

    /**
     * Panel wish will situe in the North of the borderLayout of the frame
     * nb LINE : 3 (PanelNav + HourPanelCenter + HourSliderPanel)
     * nb COLUMN : 1
     * hgap : 0
     * vgap : 0
     */
    JPanel header = new JPanel(new GridLayout(3,1,0,0));

    /**
     * Panel situe in the CENTER of the Frame's borderLayout 
     * For view the map even if we have the different Panel
     */
    Map body = new Map();

    /**
     * Panel situe in the WEST of the Frame's borderLayout
     * CardLayout : 
     * 1) NButtonZoomPanleApp (ZoomPanelCardArticleApp)
     * 2) NButtonZoomPanleApp + NMenuPanelApp (MenuPanelCardArticleApp)
     */
    JPanel article = new JPanel(new GridBagLayout());

    JPanel aside = new JPanel();

    // HEADER COMPOSANTS 
    /**
     * Layout's panel for Navigation bar (Button MENU  + LOGO and NAME of the APP)
     * nb LINE : 1
     * nb COLUMN : 3
     * hgap : 200
     * vgap : 0
     */
    JPanel panelNav = new JPanel(new GridLayout(1,3,0,0));

    /**
     * Icon of the Button menu
     * Description : three horizontal lines
     * Source : ./src/main/java/menu.png
     */
    Icon iconMenu = new ImageIcon("./src/main/java/menu.png");

    /**
     * Icon of the Button menu
     * Description : three horizontal lines
     * Source : ./src/main/java/close.png
     */
    Icon iconClose = new ImageIcon("./src/main/java/close.png");

    /**
     * Panel for put the Button in the grid
     */
    JPanel panelButtonMenu = new JPanel();
    /**
     * Button with their Icon
     */
    JButton buttonMenu = new JButton(iconMenu);

    /**
     * Label for App's name
     * ADD logo in constructor
     */
    //JPanel PanelLabelLogoName = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel labelLogoName = new JLabel("Plane AIR",JLabel.CENTER);

    /**
     * 
     */
    Icon iconFolder = new ImageIcon("./src/main/java/folder-input.png");
    /**
     * Button for return with importButtons
     */
    JButton leaveButtonToImport = new JButton(iconFolder);


    /**
     * Layout Panel For hour's panel, link to the GridLayout of header
     */
    JPanel hourPanelCenterPanelLinkToHeader = new JPanel();

    /**
     * Panel that contain TextField with hour
     * FlowLayout at CENTER
     */
    JPanel hourPanelCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));

    /**
     * JComboBox for choose hour
     */
    JComboBox hourChoice = new JComboBox();
    /**
     * Label with " : " between hour ComboBox and minutes ComboBox
     */
    JLabel betweenTime = new JLabel(" : ");
    /**
     * JComboBox for choose minutes
     */
    JComboBox minChoice = new JComboBox();
    

    /**
     * Panel for the Hour's ScrollBar
     * FlowLayout at CENTER
     */
    JPanel hourSliderPanelCENTER = new JPanel(new FlowLayout(FlowLayout.CENTER));
    /**
     * Slider for Hour
     */
    JSlider sliderTime = new JSlider();
    /**
     * Icon for the playing button
     */
    Icon iconPlay = new ImageIcon("./src/main/java/play.png");
    /**
     * Button for playing the simulation 
     */
    JButton buttonPlay = new JButton(iconPlay);

    /*BODY COMPOSANTS*/

    //LEFT
    

    /**
     * Contain the panel of Zoom
     * Add Border
     */
    JPanel spaceBorderZoomPanel = new JPanel();

    /**
     * Contain an Empty Panel and spaceBorderZoomPanel
     * Forcing bottom
     */
    JPanel zoomButtonLeftBottom = new JPanel();

    /**
     * Create an Empty Panel for forcing the Buttonimport to go in bottom
     * When there is no MinGraph + InfoGraph
     */
    JPanel empty2 = new JPanel();
    /**
     * Slider that make access to change the number of altitudes max
     */
    JSlider sliderKmax = new JSlider();
    /**
     * JLabel for see the number altitudes choose
     */
    JComboBox choixAltitudesMax = new JComboBox(); 

    //RIGHT
    /**
     * Contain the panel of MinGraph + InfoGraph
     * Add Border
     */
    JPanel spaceBorderGraph = new JPanel();

    /**
     * Contain an Empty Panel and spaceBorderButtonImport
     * Forcing bottom
     */
    JPanel graphLRightBottom = new JPanel();
    /**
     * Expand Button
     */
    JButton buttonAgr = new JButton("AGRANDIR");
    /**
     * JButton of reduction of this panel
     */
    JButton reduireButtonGraph = new JButton("REDUIRE");
    /**
     * A panel for NMaxGraphPanelApp
     */
    NMaxGraphPanelApp maxGraphPanel;


    /**
     * Having acces to homePage (setVisible elements change)
     */
    NHomePage homePage;



    public NPrincipaleFrameApp (NHomePage homePage){

        this.homePage = homePage;

    
        /*this.setTitle("Plane AIR");
        this.setSize(900,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
        this.setLayout(new BorderLayout());
        body.setLayout(new BorderLayout());

        /*HEADER COMPOSANTS */

        
        //Color of the panel
        panelNav.setBackground(Color.YELLOW);
        panelButtonMenu.setBackground(Color.YELLOW);
        //PanelLabelLogoName.setBackground(Color.YELLOW);


        // Set ButtonsMenu
        buttonMenu.setBorderPainted(false);
        buttonMenu.setContentAreaFilled(false);

        leaveButtonToImport.setBorderPainted(false);
        leaveButtonToImport.setContentAreaFilled(false);
        
        // Set logo with Name of the App in PanelNav
        // Description : France representation with graph composants
        // Source : ./src/main/java/Graph France.png
        labelLogoName.setIcon(new ImageIcon("./src/main/java/Graph France.png"));
        //Size Label LOGO Name
        labelLogoName.setPreferredSize(new Dimension(WIDTH,70));

        // Color of The HourPanelCenter variable
        hourPanelCenter.setBackground(Color.YELLOW);
        
        for(int i = 0; i <= 23; i++){
            hourChoice.addItem(i);
        }
        hourChoice.setBackground(Color.YELLOW);

        for(int i = 0; i <= 59; i++){
            minChoice.addItem(i);
        }
        minChoice.setBackground(Color.YELLOW);

        //SLIDER
        sliderTime.setMinimum(0);
        sliderTime.setMaximum(2359);
        sliderTime.setPreferredSize(new Dimension(650,15));
        sliderTime.setValue(0000);
        //BUTTON PLAY
        buttonPlay.setBackground(Color.YELLOW);
        buttonPlay.setBorderPainted(false);
        buttonPlay.setPreferredSize(new Dimension(35,35));
        

        //ARTICLE
        zoomButtonLeftBottom.setLayout(new BoxLayout(zoomButtonLeftBottom, BoxLayout.Y_AXIS));
        
        //ASIDE
        graphLRightBottom.setLayout(new BoxLayout(graphLRightBottom, BoxLayout.Y_AXIS));


        //this.setLocationRelativeTo(null)
    }

    /**
     * Method adding compostans of the JFrame
     */
    public void addComposants(){

        //HEADER COMPOSANTS

        //First LINE
        panelButtonMenu.add(buttonMenu);
        panelNav.add(buttonMenu);
        //PanelLabelLogoName.add(LabelLogoName);
        panelNav.add(labelLogoName);

        
        leaveButtonToImport.setBackground(Color.YELLOW);
        panelNav.add(leaveButtonToImport);

        header.add(panelNav);

        //Second LINE

        hourPanelCenter.add(hourChoice);
        hourPanelCenter.add(betweenTime);
        hourPanelCenter.add(minChoice);
        hourPanelCenterPanelLinkToHeader.add(hourPanelCenter);
        header.add(hourPanelCenterPanelLinkToHeader);

        //Third LINE

        hourSliderPanelCENTER.add(sliderTime);
        hourSliderPanelCENTER.add(buttonPlay);
        header.add(hourSliderPanelCENTER);

        //BODY
        
        //LEFT
        empty2.setPreferredSize(new Dimension(130,200));
        zoomButtonLeftBottom.add(empty2);
        spaceBorderZoomPanel.add(new NButtonZoomPanelApp());
        zoomButtonLeftBottom.add(spaceBorderZoomPanel);
        
        body.add(zoomButtonLeftBottom, BorderLayout.WEST);

        //RIGHT
        graphLRightBottom.add(new NMinGraphPanelApp(buttonAgr));
        graphLRightBottom.add(Box.createRigidArea(new Dimension(0, 10)));
        graphLRightBottom.add(new NInfoGraphPanelApp());
        graphLRightBottom.add(spaceBorderGraph);
        body.add(graphLRightBottom,BorderLayout.EAST);
   

        //ADD structure to BorderLayout
        this.add(header, BorderLayout.NORTH);
        this.add(body, BorderLayout.CENTER);
        this.add(article, BorderLayout.WEST);
        this.add(aside, BorderLayout.EAST);


    };

    /**
     * Method adding events of the JFrame
     */
    public void addEvents(){

        buttonMenu.addActionListener((ActionEvent e) -> {

            if(buttonMenu.getIcon().equals(iconMenu)){

            GridBagConstraints GridBagC = new GridBagConstraints(); 
            GridBagC.insets = new Insets(0, 10, 10, 0);

            article.add(new NMenuPanelApp(10, choixAltitudesMax),GridBagC);
            buttonMenu.setIcon(iconClose);
            homePage.setVisible(true);
            }
            else{
                article.removeAll();
                buttonMenu.setIcon(iconMenu);
                homePage.setVisible(true);
            }

        });

        leaveButtonToImport.addActionListener((ActionEvent e) ->{
                
                homePage.removeBodyPanelPrinc(this);
                this.homePage.setVisible(true);


        });

        hourChoice.addActionListener((ActionEvent e) -> {
         
            int hour = (int)hourChoice.getSelectedItem();
            int newValSlid = (sliderTime.getValue()%100) + hour*100;
            sliderTime.setValue(newValSlid);
        });
   
        minChoice.addActionListener((ActionEvent e) -> {
            
            int min = (int)minChoice.getSelectedItem();
            int newValSlid = (sliderTime.getValue()/100)*100 + min;
            sliderTime.setValue(newValSlid);
        });

        sliderTime.addChangeListener((ChangeEvent e) -> {
            int time = sliderTime.getValue();
            int hour = time/100;
            int minutes = time%100;
            hourChoice.setSelectedItem(hour);
            minChoice.setSelectedItem(minutes);
            ;
         });
         

        buttonAgr.addActionListener((ActionEvent e) -> {
            // graphLRightBottom.removeAll();
            // spaceBorderGraph.removeAll();
            maxGraphPanel = new NMaxGraphPanelApp();
            // aside.add(maxGraphPanel);
            // this.setVisible(true);
        });

        reduireButtonGraph.addActionListener((ActionEvent e) -> {

            aside.removeAll();
            //create a Frame for MaxGraph
            homePage.setVisible(true);
        }); 

        sliderKmax.addChangeListener((ChangeEvent e) -> {
            choixAltitudesMax.setSelectedItem(sliderKmax.getValue());
        } );
       
        choixAltitudesMax.addActionListener((ActionEvent e) -> {
            sliderKmax.setValue((int)choixAltitudesMax.getSelectedItem());
        });

    }

    /**
     * Create the part in right of the app (Graph + Info + ImportButtons)
     */

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //CardLayout Solution (obscelete)
    
      /**
     * CardPanel with Import + (not all time) GraphInfos
     * Card Name : Min
     * GridLayout 
     * nb LINE : 3  (NMinGraphPanelApp + NInfoGraphPanel + NButtonImportPanelApp)
     * nb COLUMN : 1
     */
    
        //ARTICLE

    /**
     * CardPanel with Zoom
     * Card Name : Zoom
     * GridBagLayout
     */
    //JPanel  zoomPanelCardArticleApp = new JPanel(new GridBagLayout()) ;

    /** 
     * CardPanel with MENU + Zoom --> After push the MENU button
     * Card Name : Menu
     */
   // JPanel menuPanelCardArticleApp = new JPanel(new GridBagLayout());
    
    
    
    
    public void addCardPanel(){

        //ARTICLE COMPOSANTS

        //MENU CARD

        /**
         * Paramater Layout
         */
        /*GridBagConstraints GridBagC = new GridBagConstraints();*/

        /**
         * ZoomPanelCardArticleApp
         * When you open the menu this Panel is show
         */  
         /*GridBagC.gridx = 0;
         GridBagC.gridy = 0;

         menuPanelCardArticleApp.add(new NMenuPanelApp(), GridBagC);

         GridBagC.gridx = 1;
         GridBagC.gridy = 1;

         menuPanelCardArticleApp.add(new NButtonZoomPanelApp(), GridBagC);

         article.add(menuPanelCardArticleApp, "Menu");*/
         
        //ZOOM CARD

        /**
         * ZoomPanelCardArticleApp
         * When you open the widow or you close the MENU this Panel is show
         */   
        /*GridBagC.insets = new Insets(110, 0, 0, 0);   
        GridBagC.gridx = 0;
        GridBagC.gridy = 1;

        zoomPanelCardArticleApp.add(new JPanel(), GridBagC);

        GridBagC.gridx = 4;
        GridBagC.gridy = 2;
 
        zoomPanelCardArticleApp.add(new NButtonZoomPanelApp(), GridBagC);

        article.add(zoomPanelCardArticleApp,"Zoom");  */
        
        /**
         * Panel with just zoom is the one choose by default
         */
        /*cardArticleLayout.show(article, "Zoom");*/
        
        /*ASIDE COMPOSANTS*/

        //MAX CARD
        
        /*containMaxPanel.add(new NMaxGraphPanelApp());

        aside.add(containMaxPanel, "Max");

        // MIN CARD

         /**
          * Create an Empty Panel for force the NButtonImportPanel's Panel to bottom
          */
         /*JPanel Empty = new JPanel();
         Empty.setPreferredSize(new Dimension(200,200));
         zoomButtonLeftBottom.add(Empty);
         SpaceBorderZoomPanel.add(new NButtonImportPanelApp(1));
         zoomButtonLeftBottom.add(SpaceBorderZoomPanel);

         aside.add(zoomButtonLeftBottom, "Min");
        

         /**
         * Panel with just Import button(s) is the one choose by default
         */
        /*cardAsideLayout.show(aside, "Min");*/

    }


}