package planeair.components.comboboxes;




// import SWING components
import javax.swing.JComboBox;

import planeair.components.comboboxes.renders.NDefaultRenderer;
import planeair.components.menu.NGraphMenuPanel;

// import AWT components
import java.awt.Color;
import java.awt.Font;



/**
 * This class create ComboBox appearance for Graph Menu
 * 
 * @author GIRAUD Nila
 */
public class NComboBoxGraph extends JComboBox<Integer> {


    public NComboBoxGraph(){
        
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Arial", Font.BOLD, 18));
        this.setBackground(Color.BLACK);

        this.setRenderer(new NDefaultRenderer<Integer>());
        this.setPreferredSize(NGraphMenuPanel.KINDACOMBOBOXDIMENSION);

    }
    
}
