package planeair.components.graphview;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class NGraphNotHerePanel extends JPanel {

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
