package planeair.components.comboboxes;


//#region IMPORT
    //#region .SWING
    // import SWING components
    import javax.swing.JComboBox;
    //#endregion

    //#region .AWT
    import java.awt.Color;
    import java.awt.Font;
    //#endregion

    //#region COMPONENTS.COMBOBOXS.RENDERS
    import planeair.components.comboboxes.renders.NDefaultRenderer;
    //#endregion

    //#region COMPONENTS.MENU
    import planeair.components.menu.NGraphMenuPanel;
    //#endregion
//#endregion


/**
 * This class create custom ComboBox for Graph Menu with a Default renderer
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
