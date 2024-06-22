package planeair.components.menu.infos.renders;

//#region IMPORTS
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
//#endregion

/**
 * Renderer used for the flight table shown when 
 * {@code AirportWaypoints} are clicked
 */
public class NFlightTableRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasOutput, int row,
            int column) {
        JLabel cell = new JLabel(value.toString()) ;
        cell.setFont(new Font("Arial", Font.BOLD, 14)) ;
        if (isSelected)
            cell.setBackground(table.getSelectionBackground()) ;
        else 
            cell.setBackground(Color.WHITE) ;

        cell.setOpaque(true) ;

        return cell ;

    }
}
