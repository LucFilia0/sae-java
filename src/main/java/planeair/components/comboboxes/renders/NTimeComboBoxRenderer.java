package planeair.components.comboboxes.renders;

// import SWING components
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.Color ;

// import AWT components
import java.awt.Component;

public class NTimeComboBoxRenderer extends NDefaultRenderer<Integer>  {
    public static final Color DEFAULT_TEXT_COLOR = Color.BLACK ;
	public static final Color DEFAULT_BACKGROUND_COLOR = Color.YELLOW ;

    public NTimeComboBoxRenderer(Color textColor, Color backgroundColor) {
        super(textColor, backgroundColor) ;
    }

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
