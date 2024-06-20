package planeair.components.comboboxes;


//#region IMPORTS
import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.Font;

import planeair.components.comboboxes.renders.NDefaultRenderer;

import planeair.components.menu.NGraphMenuPanel;
//#endregion


/**
 * This class create ComboBox appearance for Graph Menu
 * 
 * @author GIRAUD Nila
 */
public class NComboBoxGraph extends JComboBox<Integer> {

    //#region CONSTRUCTOR
    /**
     * Constructor of NComboBoxGraph
     * Renderer : NDefaultRenderer
     */
    public NComboBoxGraph(){
        
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Arial", Font.BOLD, 18));
        this.setBackground(Color.BLACK);

        this.setRenderer(new NDefaultRenderer<Integer>());
        this.setPreferredSize(NGraphMenuPanel.KINDACOMBOBOXDIMENSION);

    }
    //#endregion
    
}
