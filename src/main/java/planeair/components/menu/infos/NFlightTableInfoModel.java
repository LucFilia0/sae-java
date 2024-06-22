package planeair.components.menu.infos;

//#region IMPORT
import java.awt.Font;
import java.util.List;

import planeair.util.Airport;
import planeair.util.NTime;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;

import planeair.components.menu.infos.renders.NFlightTableRenderer;
import planeair.graph.coloring.ColoringUtilities;
import planeair.graph.graphutil.Flight;
//#endregion

/**
 * Model for JTable
 * The table's columns are defined  by the enum {@code Titles} and the
 * values contained will depend on the Airport's {@code FlightList}
 * 
 * @author GIRAUD Nila mod. Nathan LIEGEON
 */
public class NFlightTableInfoModel extends AbstractTableModel {

    /**
     * The airport button selected in the Map IHM component 
     */
    private Airport airport;

    /**
     *ArrayList which contain all Flight link witch the choosen Airport
     * In the oject Airport
     */
    private List<Flight> listFlight; 

    //#region STATIC VARIABLE
    /**
     * String shown if the flight leaves from this airport
     */
    private static final String DEPARTURE = "Départ";

    /**
     * String shown if the flight arrives to this airport
     */
    private static final String ARRIVAL = "Arrive";
    //#endregion

    //#region CONSTRUCTORS

    /**
     * Constructor of the class, Need for taken the choose Airport 
     * @param airport
     */
    public NFlightTableInfoModel(JTable table, Airport airport) {
        initRenders(table) ;
        this.airport = airport;
        this.listFlight = airport.getFlightList(); 
    }
    //#endregion

    //#region OVERRIDEN METHODS

    @Override
    public int getRowCount() { 
        return listFlight.size(); 
    }

    @Override
    public String getColumnName(int column) { 
        return Titles.values()[column].columnName ; 
    }

    @Override
    public int getColumnCount() { 
        return Titles.values().length ;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Titles.values()[columnIndex].objectClass ;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Flight flight = listFlight.get(rowIndex);
        Titles title = Titles.values()[columnIndex] ;

        switch(title){
            case ID:
                return flight.getId();
            case HOUR:
                return flight.getDepartureTime();
            case STATE:
                if (flight.getDepartureAirport() ==  airport) {
                    return NFlightTableInfoModel.DEPARTURE;
                } 
                else {
                    return NFlightTableInfoModel.ARRIVAL;
                }   
            case ALTITUDE:
                return flight.getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE);
            default:
                return null;
        }
    }

    //#endregion
    //#region ENUMS

    /**
     * Enum containing for each column its title and type
     */
    public enum Titles {
        ID("ID", String.class),
        HOUR("Heure", NTime.class),
        STATE("Etat", String.class),
        ALTITUDE("Altitude", Integer.class) ;

        /**
         * Text of the column header
         */
        private String columnName ;

        /**
         * Type of the data stored in that column
         */
        private Class<? extends Object> objectClass ;

        /**
         * Constructor 
         * @param columnName the header
         * @param objectClass the type
         */
        private Titles(String columnName, Class<? extends Object> objectClass) {
            this.columnName = columnName ;
            this.objectClass = objectClass ;
        }
    }
    //#endregion

    //#region PRIVATE METHODS

    /**
     * Initializes the renders for each column and the general header style
     * @param table The table on which the renders get set
     */
    private void initRenders(JTable table) {
        for (int i = 0 ; i < Titles.values().length ; i++) {
            table.setDefaultRenderer(Titles.values()[i].objectClass, 
            new NFlightTableRenderer());
        }

        JTableHeader header = table.getTableHeader() ;
        header.setFont(new Font("Arial", Font.BOLD, 14)) ;
    }
    //#endregion
    
}
