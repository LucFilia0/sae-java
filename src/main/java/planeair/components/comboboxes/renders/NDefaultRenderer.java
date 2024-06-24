package planeair.components.comboboxes.renders;

//#region IMPORTS
import java.awt.Component;
import java.awt.Font;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
//#endregion

/**
 * The default renderer for the {@code Combo boxes} of this app.
 * Makes the text white, the background black, and sets a default font. 
 * 
 * @author Nathan LIEGEON
 */
public class NDefaultRenderer<T> implements ListCellRenderer<T> {

	/**
     * Default text_color for this kind of combo boxes :
	 * White
     */
	public static final Color DEFAULT_TEXT_COLOR = Color.WHITE ;

	/**
     * Default background_color for this kind of combo boxes : 
	 * Black
     */
	public static final Color DEFAULT_BACKGROUND_COLOR = Color.BLACK ;

	/**
	 * The text color input by the user
	 */
	protected Color textColor ;

	/**
	 * The text color input by the user
	 */
	protected Color backgroundColor ;

	/**
	 * Overrides this Renderer's default text color and background colors
	 * with whatever you want
	 * 
	 * @param textColor
	 * @param backgroundColor
	 */
	public NDefaultRenderer(Color textColor, Color backgroundColor) {
		this.textColor = textColor ;
		this.backgroundColor = backgroundColor ;
	}

	/**
	 * Initializes the Renderer with its default background and text colors
	 * Gives a default font for all items in this comboBox
	 */
	public NDefaultRenderer() {
		this.textColor = DEFAULT_TEXT_COLOR ;
		this.backgroundColor = DEFAULT_BACKGROUND_COLOR ;
	}

	@Override
	public Component getListCellRendererComponent(
			JList<? extends T> list,
			T value,
			int index,
			boolean isSelected,
			boolean cellHasFocus) {
		
		JLabel cell = new JLabel() ;
		
		cell.setFont(new Font("Arial", Font.BOLD, 18)) ;
		cell.setForeground(textColor);
		cell.setBackground(backgroundColor);
		return cell ;
	}
}
