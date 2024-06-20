package planeair.components.menu.infos;

//#region IMPORT
import java.awt.Dimension;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import planeair.App;
import planeair.components.mapview.Map;
import planeair.components.mapview.mapwp.MapWaypoint;
import planeair.components.mapview.mapwp.airportwp.AirportWaypoint;
import planeair.components.mapview.mapwp.flightwp.FlightWaypoint;
import planeair.util.Airport;
//#endregion

/**
 * The JPannel that is prompted when a MapItem is pressed, showing all the required informations
 *
 * @author Luc le Manifik
 */
public class NInfoPanel extends JPanel {

    //#region INSTANTIALISATION
        //#region AIRPORT
        /**
         * The first line of the prompt
         */
        private JLabel labelAirport;
        /**
         * Panel that contain labelAirport
         */
        private JPanel airportPanel;

        /**
         * Panel for the Table and this Scroll pane
         */
        JPanel panelTable;
        /**
         * JScrollBar for the Table
         */
        JScrollPane scrollPane;


        /**
         *  JTable for departure/arrival flight of the airport
         * Row Informations : 
         * ID
         * Time
         * Arrival or Dpearture
         */
        JTable table;

        /**
         * Model for the JTable
         */
        NFlightTableInfoModel model;


        /**
         * The airport selected by the user
         */
        Airport airport;
        //#endregion
        
        //#region FLIGHT
        /**
         * The first COLUMN of the prompt
         */
        private JLabel labelFlightLeft;
        /**
         * The Second COLULMN of the prompt
         */
        private JLabel labelFlightRight;
        /**
         * Panel which contain labelFlight
         */
        private JPanel flightPanel;
        //#endregion

    //#endregion

    //#region CONSTRUCTOR
    /**
     * Creates a new NInfoPanel, which prompts the infos of the clicked elements
     *
     * @author Luc le Manifik
     */
    public NInfoPanel() {
        
        this.initComponents();
        this.setOpaque(false);
        this.addComponents();
    }
    //#endregion

    //#region INITISALISATION
    /**
     * Creates all the components of the NInfoPanel
     */
    private void initComponents() {

        this.setBackground(App.KINDAYELLOW);
        this.setLayout(new FlowLayout());
        this.setOpaque(true);

        //#region AIRPORT
        this.labelAirport = new JLabel();
        this.airportPanel = new JPanel();
        this.scrollPane = new JScrollPane();
        this.panelTable = new JPanel(new FlowLayout(FlowLayout.CENTER));

        this.labelAirport.setOpaque(false);
        this.airportPanel.setBackground(App.KINDAYELLOW);

        this.scrollPane.setPreferredSize(new Dimension(400,100));
        this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.scrollPane.setBackground(App.KINDAYELLOW);

        panelTable.setBackground(App.KINDAYELLOW);
        //#endregion

        //#region FLIGHT
        this.labelFlightLeft = new JLabel();
        this.labelFlightRight = new JLabel();
        this.flightPanel = new JPanel(new GridLayout(1,2));

        this.labelFlightLeft.setOpaque(false);
        this.labelFlightRight.setOpaque(false);
        this.flightPanel.setBackground(App.KINDAYELLOW);
        //#endregion
    }
    //#endregion

    //#region ADD
    /**
     * Adding components in the class panel
     */
    private void addComponents(){

        //#region AIRPORT
        airportPanel.add(labelAirport);
        //#endregion

        //#region FLight
        flightPanel.add(labelFlightLeft);
        flightPanel.add(labelFlightRight);
        this.scrollPane.setViewportView(table);
        panelTable.add(scrollPane);
        //#endregion

    }
    //#endregion

    //#region SET INFORMATIONS --> SHOW AND HIDE
    /**
     * Makes the NInfoPanel visible, and prompts the infos of the MapItem that was clicked
     * 
     * @author GIRAUD Nila 
     */
    public void showInfos(MapWaypoint mapWaypoint) {

        hideInfos();

        this.initComponents();
        this.addComponents();

        if(mapWaypoint instanceof FlightWaypoint){
            
            this.labelFlightLeft.setText(mapWaypoint.toStringFirst());
            this.labelFlightRight.setText(mapWaypoint.toStringSecond());
            this.add(this.flightPanel);
        }
        else{
            this.labelAirport.setText(mapWaypoint.toString());
            this.add(airportPanel);

            //#region TABLE
            this.airport = ((AirportWaypoint) mapWaypoint).getAirport();
            if(!this.airport.getFlightList().isEmpty()){
                this.table = new JTable();
                this.model = new NFlightTableInfoModel(this.airport);
                this.table.setModel(model);
                scrollPane.setViewportView(table);
                this.add(panelTable);
            }
            //#endregion 
        }
    }


    /** Makes the NInfoPanel invisible, and clear all the written informations
     * 
     * @author Luc le Manifik
     */
    public void hideInfos() {

        this.removeAll();
        this.setOpaque(false);
        
    }

    /**
     * Sets the NInfoPanel as the one of the App
     * 
     * @author Luc le Manifik
     */
    public void setPrincipal() {
        Map.infoPanel = this;
    }
    //#endregion
    
}
