package planeair.components.comboboxes.renders;

//#region IMPORT
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Color ;
import java.awt.Component;
//#endregion

/**
 * Class of renderer for Time ComboBoxs
 * Add a 0 before 0 to 9 number 
 * 
 * @author GIRAUD Nila
 */
public class NTimeComboBoxRenderer extends NDefaultRenderer<Integer>  {

    //#region CONSTANTS
    
    /**
     * Default color for the text
     */
    public static final Color DEFAULT_TEXT_COLOR = Color.BLACK ;

    /**
     * Default color of the background
     */
	public static final Color DEFAULT_BACKGROUND_COLOR = Color.YELLOW ;
    //#endregion

    //#region CONSTRUCTORS

    /**
     * Constructor of NTimeComboBoxRenderer
     * @param textColor The color of the text
     * @param backgroundColor The color of the background
     */
    public NTimeComboBoxRenderer(Color textColor, Color backgroundColor) {
        super(textColor, backgroundColor) ;
    }

    /**
	 * Initializes the Renderer with its default background and text colors
	 * Gives a default font for all items in this comboBox
	 */
    public NTimeComboBoxRenderer() {
        super(DEFAULT_TEXT_COLOR, DEFAULT_BACKGROUND_COLOR) ;
    }

    //#endregion

    //#region GETTER

    @Override
    public Component getListCellRendererComponent(
            JList<? extends Integer> list,
            Integer value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        
        JLabel cell = (JLabel)super.getListCellRendererComponent
            (list, value, index, isSelected, cellHasFocus) ;
        
        if (value < 10){
            cell.setText("0" + String.valueOf(value));
        }
        else{
            cell.setText(String.valueOf(value));
        }

        return cell;
    }

    //#endregion

}
