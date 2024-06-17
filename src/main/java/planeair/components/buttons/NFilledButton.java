package planeair.components.buttons;

// IMPORT SWING
import javax.swing.JButton;

import planeair.App;

// IMPORT AWT
import java.awt.Color;
import java.awt.Dimension;

/**
 * A nice button, filled in black, with text written in white
 * 
 * @author Luc le Manifik
 */
public class NFilledButton extends JButton {

    /**
     * The default width of the NFilledButton
     */
    public static final int FILLED_BUTTON_WIDTH = 110;

    /**
     * The default height of the NFilledButton
     */
    public static final int FILLED_BUTTON_HEIGHT = 40;
    
    /**
     * Creates a new NFilledButton, with default size (width:90, height:40)
     * 
     * @param title (String) - The title of the button
     */
    public NFilledButton(String title) {
        super(title);
        this.setStyle();
        this.setPreferredSize(new Dimension(NFilledButton.FILLED_BUTTON_WIDTH, NFilledButton.FILLED_BUTTON_HEIGHT));
    }

    /**
     * Creates a new NFilledButton, with chosen dimensions
     * 
     * @param title (String) - The title of the button
     * @param width (int) - The width of the button
     * @param height (int) - The height of the button
     */
    public NFilledButton(String title, int width, int height) {
        super(title);
        this.setStyle();
        this.setPreferredSize(new Dimension(width, height));
    }

    /**
     * Sets the same style for all the constructors 
     */
    private void setStyle() {
        this.setFont(App.KINDANORMAL);
        this.setForeground(Color.WHITE);
        this.setBackground(Color.BLACK);
    }
}
