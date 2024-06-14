package planeair.components.comboboxes.renders;

//#region IMPORT
    //#region .SWING
    import javax.swing.JLabel;
    import javax.swing.JList;
    import java.awt.Color ;
    //#endregion

    //#region AWT
    import java.awt.Component;
    //#endregion
//#endregion

/**
 * Class of renderer for Time ComboBoxs
 * 
 * @author GIRAUD Nila
 */
public class NTimeComboBoxRenderer extends NDefaultRenderer<Integer>  {

    //#region INSTANTIALISATION AND INITIALISATION
    /**
     * Default color for text
     */
    public static final Color DEFAULT_TEXT_COLOR = Color.BLACK ;

    /**
     * Default background Color
     */
	public static final Color DEFAULT_BACKGROUND_COLOR = Color.YELLOW ;
    //#endregion

    //#region CONSTRUCTOR CHOOSE COLOR
    /**
     * Constructor of NTimeComboBoxRenderer
     * @param textColor 
     * @param backgroundColor
     */
    public NTimeComboBoxRenderer(Color textColor, Color backgroundColor) {
        super(textColor, backgroundColor) ;
    }
    //#endregion

    //#region DEFAULT CONSTRUCTOR
    public NTimeComboBoxRenderer() {
        super(DEFAULT_TEXT_COLOR, DEFAULT_BACKGROUND_COLOR) ;
    }
    //#endregion

    //#region GETTER
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
    //#endregion

}
