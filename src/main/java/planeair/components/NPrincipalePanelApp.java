package planeair.components;

// Import of SWING composants
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.Box;

// Import of AWT composants
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;

// Import of LAYOUT
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BoxLayout;

//-- Import PlaneAIR

import planeair.ihm.Map;
import planeair.App;
import planeair.ihm.infopanel.NInfoPanel;

/**
 * This class create the principale panel of the App where you can see the map and the graph
 * 
 * @author GIRAUD Nila
 */
public class NPrincipalePanelApp extends JPanel{

    //STRUCT

    /**
     * Panel wish will situe in the North of the borderLayout of the frame
     * nb LINE : 3 (PanelNav + HourPanelCenter + HourSliderPanel)
     * nb COLUMN : 1
     * hgap : 0
     * vgap : 0
     */
    JPanel header = new JPanel(new GridLayout(1,1,0,0));

    /**
     * Panel situe in the CENTER of the Frame's borderLayout 
     * For view the map even if we have the different Panel
     */
    Map body;

    /**
     * Layout for the BorderLayout CENTER of body
     */
    JPanel bodyCenter = new JPanel();

    /**
     * Panel situe in the WEST of the Frame's borderLayout
     * CardLayout : 
     * 1) NButtonZoomPanleApp (ZoomPanelCardArticleApp)
     * 2) NButtonZoomPanleApp + NMenuPanelApp (MenuPanelCardArticleApp)
     */
    JPanel article = new JPanel(new GridBagLayout());


    // HEADER COMPOSANTS 
    
    /**
     * Layout's panel for Navigation bar (Button MENU  + LOGO and NAME of the APP)
     * nb LINE : 1
     * nb COLUMN : 3
     * hgap : 200
     * vgap : 0
     * Location : top of the frame (represent the header)
     */
    JPanel panelNav = new JPanel(new GridLayout(1,3,0,0));

    /**
     * Icon of the Button menu
     * Description : three horizontal lines
     * Source : ./src/main/java/menu.png
     */
    Icon iconMenu = new ImageIcon("./src/main/java/planeair/icons/menu.png");

    /**
     * Icon of the Button menu
     * Description : three horizontal lines
     * Source : ./src/main/java/close.png
     */
    Icon iconClose = new ImageIcon("./src/main/java/planeair/icons/close.png");

    /**
     * Panel for put all buttons in the nav
     * Grid
     * LINE : 1
     * COLUMN : 2 (menu + import)
     * Location : left ine the header
     */
    JPanel panelButton = new JPanel(new GridLayout(1,2));
    /**
     * Button with their Icon
     * Location : first button in the nav
     */
    JButton buttonMenu = new JButton(iconMenu);

    /**
     * Icon for import button
     */
    Icon iconFolder = new ImageIcon("./src/main/java/planeair/icons/folder-input.png");
    /**
     * Button for return with importButtons
     * Location : second button in the nav
     */
    JButton leaveButtonToImport = new JButton(iconFolder);
    /**
     * Label for App's name + Logo
     */
    JLabel labelLogoName = new JLabel("Plane AIR",JLabel.CENTER);

    /**
     * Layout Panel For hour's panel, link to the GridLayout of header
     */
    JPanel hourPanelComboBox = new JPanel();
    
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
     * Panel that contain ComboBox with hour
     * FlowLayout at CENTER
     * Location : Top of the center of the frame
     */
    JPanel hourPanelCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));

    /**
     * Panel for the time's Slider
     * FlowLayout at CENTER
     */
    JPanel hourSliderPanelCENTER = new JPanel(new FlowLayout(FlowLayout.CENTER));

    /**
     * Slider for Hour 
     * Location : Top of the center of the frame, below the comboBox
     */
    JSlider sliderTime = new JSlider();

    /**
     * Icon for the playing button
     */
    Icon iconPlay = new ImageIcon("./src/main/java/planeair/icons/play.png");

    /**
     * Button for playing the simulation 
     * Location : right to the slider
     */
    JButton buttonPlay = new JButton(iconPlay);

    /*BODY COMPOSANTS*/

    // CENTER

    /**
     * Slider that make access to change the number of altitudes max
     * Location : in the panel menu --> need here for Events
     */
    JSlider sliderKmax = new JSlider();
    /**
     * JLabel for see the number altitudes choose
     * Location : in the panel menu --> need here for Events
     */
    JComboBox choixAltitudesMax = new JComboBox(); 
    /**
     * A pael for forcing all their is in the center to be in top
     */
    JPanel topEmptyPanel = new JPanel();

    //RIGHT
    /**
     * Contain the panel of MinGraph + InfoGraph
     * Add Border
     */
    JPanel spaceBorderGraph = new JPanel();
    /**
     * Panel in the bodyCenter Layout for the combo box and slider for TIME
     * Location : center of the Frame
     * LINE : 3
     * CULUMN : 1
     */
    JPanel panelCenterWithTimeComboAndSlider = new JPanel(new GridLayout(3,1));

    // LEFT 
    /**
     * Menu for changing graph composition
     * Appear after push the button with the icon menu.png
     * Location : left in the frame
     */
    NMenuPanelApp menu; 

    // RIGHT
    /**
     * Contain an Empty Panel and spaceBorderButtonImport
     * Forcing bottom
     */
    JPanel graphLRightBottom = new JPanel();
    /**
     * Expand Button for graph
     * Open a new Frame with the graph and this information
     * Location : in the panel Mingraph --> need here for Events
     */
    JButton buttonAgr = new JButton("AGRANDIR");
    /**
     * A frame for NMaxGraphPanelApp
     * Put directly in the frame with information
     */
    NMaxGraphFrameApp maxGraphPanel; //maybe transform the class to JFramr directly

    /**
     * Having acces to homePage (setVisible elements change)
     * the panel NPrincipalePanelApp is put in this frame
     */
    App app;

    /**
     *
     */
    private NInfoPanel infoPanel;

    /**
     * Constructor of NPrincipalePanelApp
     * @param app the frame where this panel is put
     */
    public NPrincipalePanelApp (App app){
	
	this.infoPanel = new NInfoPanel();
	this.infoPanel.setPrincipal();

        this.app = app;
        this.body = new Map();

        this.setLayout(new BorderLayout());

        body.setLayout(new BorderLayout());

        bodyCenter.setLayout(new BoxLayout(bodyCenter, BoxLayout.Y_AXIS));

        /*HEADER COMPOSANTS */
        
        //Color of the header's panel
        panelNav.setBackground(Color.YELLOW);
        panelButton.setBackground(Color.YELLOW);


        // Set ButtonsMenu
        buttonMenu.setBorderPainted(false);
        buttonMenu.setContentAreaFilled(false);

        leaveButtonToImport.setBorderPainted(false);
        leaveButtonToImport.setContentAreaFilled(false);

        
        // Set logo with Name of the App in PanelNav
        // Description : France representation with graph composants
        // Source : ./src/main/java/Graph France.png
        labelLogoName.setIcon(new ImageIcon("./src/main/java/planeair/icons/GraphFrance.png"));
        //Size Label LOGO Name
        labelLogoName.setPreferredSize(new Dimension(WIDTH,70));

        // Color of The HourPanelCenter variable
        hourPanelCenter.setBackground(Color.YELLOW);
        
        menu = new NMenuPanelApp(10, choixAltitudesMax);

        for(int i = 0; i <= 23; i++){
            hourChoice.addItem(i);
        }
        hourChoice.setBackground(Color.YELLOW);

        for(int i = 0; i <= 59; i++){
            minChoice.addItem(i);
        }
        minChoice.setBackground(Color.YELLOW);

        sliderTime.setOpaque(false);
        hourSliderPanelCENTER.setOpaque(false);
        hourPanelComboBox.setOpaque(false);
        panelCenterWithTimeComboAndSlider.setOpaque(false);
        article.setOpaque(false);

        //SLIDER
        sliderTime.setMinimum(0);
        sliderTime.setMaximum(2359);
        sliderTime.setPreferredSize(new Dimension(500,15));
        sliderTime.setValue(0000);
        //BUTTON PLAY
        buttonPlay.setBackground(Color.YELLOW);
        buttonPlay.setBorderPainted(false);
        buttonPlay.setPreferredSize(new Dimension(35,35));

        hourPanelComboBox.setPreferredSize(new Dimension(0,0));
        hourSliderPanelCENTER.setPreferredSize(new Dimension(0,0));

        topEmptyPanel.setPreferredSize(new Dimension(900,100));
        topEmptyPanel.setOpaque(false);

        //ARTICLE

        // zoomButtonLeftBottom.setLayout(new BoxLayout(zoomButtonLeftBottom, BoxLayout.Y_AXIS));
        // spaceBorderZoomPanel.setOpaque(false);
        // zoomButtonLeftBottom.setOpaque(false);
        // empty2.setOpaque(false);
        
        bodyCenter.setOpaque(false);

        //ASIDE
        graphLRightBottom.setLayout(new BoxLayout(graphLRightBottom, BoxLayout.Y_AXIS));
        graphLRightBottom.setOpaque(false);
        spaceBorderGraph.setOpaque(false);
    }

    /**
     * Method adding compostans of the Panel
     */
    public void addComposants(){

        //HEADER COMPOSANTS
        panelButton.add(buttonMenu);
        panelButton.add(leaveButtonToImport);
       
        panelNav.add(panelButton);
        panelNav.add(labelLogoName);

        JPanel empty = new JPanel();
        empty.setBackground(Color.YELLOW);
        panelNav.add(empty);

        header.add(panelNav);

        //BODY
        
        //Hour COMBOBOX
        hourPanelCenter.add(hourChoice);
        hourPanelCenter.add(betweenTime);
        hourPanelCenter.add(minChoice);
        hourPanelComboBox.add(hourPanelCenter);


        //TIME SLIDER
        hourSliderPanelCENTER.add(sliderTime);
        hourSliderPanelCENTER.add(buttonPlay);

        //BODY CENTER
        bodyCenter.add(hourPanelComboBox);
        bodyCenter.add(hourSliderPanelCENTER);

        bodyCenter.add(topEmptyPanel);

        body.add(bodyCenter);

        //RIGHT
        graphLRightBottom.add(Box.createRigidArea(new Dimension(0, 10)));
        graphLRightBottom.add(new NMinGraphPanelApp(buttonAgr));
        graphLRightBottom.add(Box.createRigidArea(new Dimension(0, 10)));
        graphLRightBottom.add(new NInfoGraphPanelApp());
        graphLRightBottom.add(spaceBorderGraph);
        body.add(graphLRightBottom,BorderLayout.EAST);
   
        article.setPreferredSize(new Dimension(160,100));

	//INFOPANEL
	this.body.add(this.infoPanel, BorderLayout.SOUTH);

        //ADD structure to BorderLayout
        this.add(header, BorderLayout.NORTH);
        this.add(body, BorderLayout.CENTER);
        body.add(article,BorderLayout.WEST);

    };

    public void setMap() {
        this.app.getAirportSet().setActiveAirportsFrom(this.app.getFig());
        this.body.paintMapItems(this.app.getAirportSet(), this.app.getFig());
    }

    /**
     * Method adding events of the JFrame
     */
    public void addEvents(){

        buttonMenu.addActionListener((ActionEvent e) -> {

            if(buttonMenu.getIcon().equals(iconMenu)){
                GridBagConstraints GridBagC = new GridBagConstraints(); 
                GridBagC.insets = new Insets(0, 10, 10, 0);
                article.add(menu ,GridBagC);
                buttonMenu.setIcon(iconClose);
                this.app.revalidate();

            }
            else{
                body.remove(article);
                this.app.setVisible(true);
                article.removeAll();
                body.add(article,BorderLayout.WEST); 
                this.app.setVisible(true);
                buttonMenu.setIcon(iconMenu);
            }

        });

        leaveButtonToImport.addActionListener((ActionEvent e) ->{
                this.app.removeBodyPanelPrinc(this);
                this.app.setVisible(true);
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
            maxGraphPanel = new NMaxGraphFrameApp();
        });

        sliderKmax.addChangeListener((ChangeEvent e) -> {
            choixAltitudesMax.setSelectedItem(sliderKmax.getValue());
        } );
       
        choixAltitudesMax.addActionListener((ActionEvent e) -> {
            sliderKmax.setValue((int)choixAltitudesMax.getSelectedItem());
        });

    }
}
