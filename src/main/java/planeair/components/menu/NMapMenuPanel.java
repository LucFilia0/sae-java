package planeair.components.menu;

//#region IMPORT
    //#region .SWING
    import javax.swing.JLabel;
    import javax.swing.JPanel;
    import javax.swing.SwingConstants;
    //#endregion

    //#region .AWT
    import java.awt.Dimension;
    //#endregion

    //#region LAYOUT
    import java.awt.GridLayout;
    //#endregion

    //#region PLANEAIR
    import planeair.App;
    import planeair.components.mapview.Map;
    //#endregion
//#endregion

/**
 * Create a panel Menu for Map
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

    //#region INSTANTIALISATION AND INITIALISATION

        //#region Title
        /**
         * JLabel for the title of the Panel NMenuPanelApp
         */
        private JLabel titleMenu = new JLabel("MENU MAP", SwingConstants.CENTER);
        //#endregion

        //#region Flight Lines
        
        /**
         * The checkBox for setting if flight lines are visible in the Map
         * Represent flight's trajectory
         */
        private NMapCheckBox flightLines = new NMapCheckBox("Lignes de vols");
        //#endregion


        //#region Airoport Used

        /**
         * The checkBox for setting if Airport used are visible in the Map (red icon)
         * Their is 2 possibility : the airport is used by a flight, or no, so it's call airport not used
         */
        private NMapCheckBox airportUsed = new NMapCheckBox("Aéroports utilisés");
        //#endregion


        //#region Airoport NOT Used
        
        /**
         * The checkBox for setting if Airport not used are visible in the Map (grey icon)
         * Their is 2 possibility : the airport used by a flight, or no, so it's call airport not used
         */
        private NMapCheckBox airportNotUsed = new NMapCheckBox("Aéroports non utilisés");
        //#endregion

        //#region Flight Icon

        private NMapCheckBox flightIcon = new NMapCheckBox("Vols");
        //#endregion
    //#endregion

    //#region CONSTRUCTOR
    /**
     * Constructor of NMenuPanelApp
     * @param map the used map of the App
     */
    public NMapMenuPanel(Map map){

        this.setBackground(App.KINDAYELLOW);
        this.setPreferredSize(new Dimension(225,300));
        this.setLayout( new GridLayout(5,1,0,15));

       //#region ADD
       //Title Menu
       titleMenu.setFont(App.KINDATITLE);
       this.add(titleMenu);

       //Flight Lines
       this.add(flightLines);

       // Airoport Used
       this.add(airportUsed);


       // Airoport NOT Used
       this.add(airportNotUsed);

       //  Flight Icon
       this.add(flightIcon);
       //#endregion
    } 
    //#endregion   
}
