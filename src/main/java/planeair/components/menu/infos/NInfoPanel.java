package planeair.components.menu.infos;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;

//-- Import AWT

//-- Import Swing

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableColumnModel;

import planeair.App;
import planeair.components.mapview.Map;
import planeair.components.mapview.mapwp.MapWaypoint;
import planeair.components.mapview.mapwp.airportwp.AirportWaypoint;
import planeair.components.mapview.mapwp.flightwp.FlightWaypoint;
import planeair.util.Airport;

/**
 * The JPannel that is prompted when a MapItem is pressed, showing all the required informations
 *
 * @author Luc le Manifik
 */
public class NInfoPanel extends JPanel {

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
     * The first line of the prompt
     */
    private JLabel labelFlight;
    /**
     * Panel which contain labelFlight
     */
    private JPanel flightPanel;
    //#endregion
    

    /**
     * Creates a new NInfoPanel, which prompts the infos of the clicked elements
     *
     * @author Luc le Manifik
     */
    public NInfoPanel() {
        
        this.setOpaque(true);
        this.initComponents();
        this.addComponents();
    }

    /**
     * Creates all the components of the NInfoPanel
     */
    private void initComponents() {

        this.setBackground(App.KINDAYELLOW);
        this.setLayout(new FlowLayout());

        //#region AIRPORT
        this.labelAirport = new JLabel();
        this.airportPanel = new JPanel();
        this.scrollPane = new JScrollPane();
        this.panelTable = new JPanel(new FlowLayout(FlowLayout.CENTER));

        this.labelAirport.setOpaque(false);
        this.airportPanel.setBackground(App.KINDAYELLOW);

        this.scrollPane.setPreferredSize(new Dimension(300,90));
        this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.scrollPane.setBackground(App.KINDAYELLOW);

        panelTable.setBackground(App.KINDAYELLOW);
        
        
        //#endregion

        //#region FLIGHT
        this.labelFlight = new JLabel();
        this.flightPanel = new JPanel();

        this.labelFlight.setOpaque(false);
        this.flightPanel.setBackground(App.KINDAYELLOW);
        //#endregion
    }


    private void addComponents(){

        //#region AIRPORT
        airportPanel.add(labelAirport);
        //#endregion

        //#region FLight
        flightPanel.add(labelFlight);
        this.scrollPane.setViewportView(table);
        panelTable.add(scrollPane);
        //#endregion


    }

    /**
     * Makes the NInfoPanel visible, and prompts the infos of the MapItem that was clicked
     * 
     * @author Luc le Manifik
     */
    public void showInfos(MapWaypoint mapWaypoint) {

        this.removeAll();
        this.repaint();

        this.initComponents();
        this.addComponents();

        if(mapWaypoint instanceof FlightWaypoint){

            System.out.println("Salut");
            
            this.labelFlight.setText(mapWaypoint.toString());
            this.add(this.flightPanel);

        }
        else{

            System.out.println("Salut");
            

            this.airport = ((AirportWaypoint) mapWaypoint).getAirport();
            this.labelAirport.setText(mapWaypoint.toString());
    
            this.add(airportPanel);

            //#region TABLE
            this.table = new JTable();
            this.model = new NFlightTableInfoModel(this.airport);
            this.table.setModel(model);
            //#endregion
        
            this.add(panelTable);
            
        }
    }


    /**
     * Makes the NInfoPanel invisible, and clear all the written informations
     * Flight type
     * 
     * @author Luc le Manifik
     */
    public void hideInfosFlight() {
        this.setOpaque(true);
        this.labelFlight.setText("");
    }

    /**
     * Makes the NInfoPanel invisible, and clear all the written informations
     * Aiport type
     * 
     * @author Luc le Manifik
     */                 
    public void hideInfosAirport() {
        this.setOpaque(true);               
        this.labelAirport.setText("");
    }

    /**
     * Sets the NInfoPanel as the one of the App
     * 
     * @author Luc le Manifik
     */
    public void setPrincipal() {
	    Map.infoPanel = this;
    }
    
}
