package planeair.components.menu;

//#region IMPORT
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import planeair.App;
import planeair.components.mapview.Map;
//#endregion

/**
 * Creates a panel Menu for Map
 * Help the user to modified appareance of the Map with JChekBox
 * 1) See lines for Flight
 * 2) See icon of used airport
 * 3) See icon of not used airport
 * 4) See icon of flight, the localization depends of the time
 * 
 * GirdLayout: 
 * LINE : 5
 * COLUMN : 1
 * HGap : 0
 * VGap : 15
 * 
 * @author GIRAUD Nila
 */
public class NMapMenuPanel extends JPanel {

    //#region STATIC VARIABLES

    /**
     * The number of NMapCheckBoxes in the Panel
     */
    public static final int NB_CHECK_BOXES = 4;

    //#endregion

    //#region INSTANTIALISATION AND INITIALISATION

        //#region Title
        /**
         * JLabel for the title of the Panel NMenuPanelApp
         */
        private JLabel titleMenu = new JLabel("MENU MAP", SwingConstants.CENTER);
        //#endregion

        //#region CheckBoxes

        /**
         * The different NCheckBoxes of the panel
         */
        private NMapCheckBox[] checkBoxes = new NMapCheckBox[NB_CHECK_BOXES];

        /**
         * The labels linked to the different NCheckBoxes
         */
        private String[] parameterNames = new String[] {"Lignes de vol", "Aéroports utilisés", "Aéroports inutilisés", "Vols"};

        /**
         * the Map which the panel repaints 
         */
        private Map map;
        
        //#endregion

    //#endregion

    //#region CONSTRUCTOR
    /**
     * Constructor of NMenuPanelApp
     * @param map the used map of the App
     */
    public NMapMenuPanel(Map map){

        this.map = map;

        this.setBackground(App.KINDAYELLOW);
        this.setPreferredSize(new Dimension(225,300));
        this.setLayout( new GridLayout(5,1,0,15));

        //#region ADD
        //Title Menu
       titleMenu.setFont(App.KINDATITLE);
       this.add(titleMenu);

        initComponents();
       //#endregion

       //#region DEFAULT SETUP
        for(int i = 0; i < NB_CHECK_BOXES; ++i) {
            this.checkBoxes[i].setSelected(true);
        }
       //#endregion
    } 
    //#endregion   

    //#region PRIVATE METHODS

    /**
     * This method initiates the components of the panel
     */
    private void initComponents() {
        for(int i = 0; i < NB_CHECK_BOXES; ++i) {
            this.checkBoxes[i] = new NMapCheckBox(this.parameterNames[i]);
            initListeners(this.checkBoxes[i]);
            this.add(this.checkBoxes[i]);
        }
    }

    /**
     * adds the listeners
     * @param checkBox The checkbox on which the 
     * listener is added
     */
    private void initListeners(NMapCheckBox checkBox) {
        checkBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                map.repaint();
            }
            
        });
    }

    //#endregion

    //#region GETTERS

    /**
     * @return "True" if the lines of the Flights must be prompt, else "false"
     */
    public boolean mustShowFlightLines() {
        return this.checkBoxes[0].isSelected();
    }

    /**
     * @return "True" if the ActiveAirports must be prompt, else "false"
     */
    public boolean mustShowActiveAirports() {
        return this.checkBoxes[1].isSelected();
    }

    /**
     * @return "True" if the InactiveAirport must be prompt, else "false"
     */
    public boolean mustShowInactiveAirports() {
        return this.checkBoxes[2].isSelected();
    }

    /**
     * @return "True" if the Flights must be prompt, else "false"
     */
    public boolean mustShowFlights() {
        return this.checkBoxes[3].isSelected();
    }
    //#endregion
}
