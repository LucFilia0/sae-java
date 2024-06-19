package planeair.components.menu.infos;

//#region IMPORT
    //#region .UTIL
    import java.util.List;
    import planeair.util.Airport;
    //#endregion

    //#region .SWING
    import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

//#endregion
    //#region .GRAPH
    import planeair.graph.graphutil.Flight;
    //#endregion
//#endregion

/**
 * Model for JTable
 * The table have 3 colummn {"ID", "Heure", "Etat"} where the user will shows link Flights informations 
 * to the selected airport
 * 
 * @author GIRAUD Nila
 */
public class NFlightTableInfoModel extends AbstractTableModel {

    /**
     * The airport button selected in the Map IHM component 
     */
    private Airport airport;

     /**
     * Title of each section of the table (columns)
     */
    private final String[] titles = {"ID", "Heure", "Etat"};

    /**
     *ArrayList which contain all Flight link witch the choosen Airport
     * In the oject Airport
     */
    private final List<Flight> listFlight; 

    //#region STATIC VARIABLE
    /**
     * The Flight leave the airport choose
     */
    private static final String DEPARTURE = "Départ";

    /**
     * The Flight arrive in the airport choose
     */
    private static final String ARRIVAL = "Arrive";
    //#endregion

    /**
     * Constructor of the class, Need for taken the choose Airport 
     * @param airport
     */
    public NFlightTableInfoModel(Airport airport){
        super();
        this.airport = airport;
        this.listFlight = airport.getFlightList(); 
    }

    @Override
    public int getRowCount() { return listFlight.size(); }

    @Override
    public String getColumnName(int column) { return titles[column]; }

    @Override
    public int getColumnCount() { return titles.length; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        Flight flight = listFlight.get(rowIndex);

        switch(columnIndex){
            case 0:
                return flight.getId();
            case 1:
                return flight.getDepartureTime();
            case 2:
                if (flight.getDepartureAirport() ==  airport) {
                    return NFlightTableInfoModel.DEPARTURE;
                } 
                else {
                    return NFlightTableInfoModel.ARRIVAL;
                }   
            default:
                return null;
        }
    }
    
}
