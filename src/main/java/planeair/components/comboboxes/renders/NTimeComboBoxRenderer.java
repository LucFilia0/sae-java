package planeair.components.comboboxes.renders;

// import SWING components
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.Color ;

// import AWT components
import java.awt.Component;

/**
 * Render handling hours by putting 0s in front of all 1 digit numbers
 * 
 * @author Giraud Nila mod. Nathan LIEGEON
 */
public class NTimeComboBoxRenderer extends NDefaultRenderer<Integer>  {
    /**
     * Default text_color for this kind of combo boxes :
     * Black
     */
    public static final Color DEFAULT_TEXT_COLOR = Color.BLACK ;

    /**
     * Default background_color for this kind of combo boxes :
     * Yellow
     */
	public static final Color DEFAULT_BACKGROUND_COLOR = Color.YELLOW ;

    /**
	 * Overrides this Renderer's default text color and background colors
	 * with whatever you want
	 * 
	 * @param textColor
	 * @param backgroundColor
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

    public Component getListCellRendererComponent(
            JList<? extends Integer> list,
            Integer value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        
        JLabel cell = (JLabel)super.getListCellRendererComponent(
            list, value, index, isSelected, cellHasFocus) ;
        
        if (value < 10){
            cell.setText("0" + String.valueOf(value));
        }
        else{
            cell.setText(String.valueOf(value));
        }

        return cell;
    }

}
