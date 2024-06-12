package planeair.components.menu;




// import SWING components
import javax.swing.JComboBox;

// import AWT components
import java.awt.Color;
import java.awt.Font;



/**
 * This class create ComboBox appearance for Graph Menu
 * 
 * @author GIRAUD Nila
 */
public class NComboBoxGraphe extends JComboBox<Integer> {


    public NComboBoxGraphe(){
        
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Arial", Font.BOLD, 18));
        this.setBackground(Color.BLACK);
        this.setPreferredSize(NMenuGraphPanelApp.KINDACOMBOBOXDIMENSION);

    }
    
}
