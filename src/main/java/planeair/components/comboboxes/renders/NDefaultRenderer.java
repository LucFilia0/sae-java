package planeair.components.comboboxes.renders;

import java.awt.Component;
import java.awt.Font;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Haha figure it out by yourself loser 😂😂😂😂😂😂😂
 */
public class NDefaultRenderer<T> implements ListCellRenderer<T> {
	public static final Color DEFAULT_TEXT_COLOR = Color.WHITE ;
	public static final Color DEFAULT_BACKGROUND_COLOR = Color.BLACK ;

	protected Color textColor ;
	protected Color backgroundColor ;

	public NDefaultRenderer(Color textColor, Color backgroundColor) {
		this.textColor = textColor ;
		this.backgroundColor = backgroundColor ;
	}

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
