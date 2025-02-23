package planeair.components.graphview;

//#region IMPORTS
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;
//#endregion

/**
 * Placeholder panel for when the graph has been moved to another frame
 */
public class NGraphNotHerePanel extends JPanel {
    
    /**
     * Panel telling the user that the graph has been moved to another frame
     */
    public NGraphNotHerePanel() {
        super() ;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK) ;
        NMinGraphPanel.centerString(g, getBounds(), "Le Graph est dans une\n autre fenêtre",
            new Font("Arial", Font.BOLD, getWidth()/18)) ;
    }
}  
