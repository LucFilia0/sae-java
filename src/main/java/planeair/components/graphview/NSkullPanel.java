package planeair.components.graphview;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Panel shown if no graph has been imported, it contains a single giant skull image 
 * (or a placeholder text if you dare delete it)
 * 
 * @author Knight of the Holy CROUS'kie
 */
public class NSkullPanel extends JPanel {

    /**
     * The GOOFY panel in question
     */
    public NSkullPanel() {
        super() ;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x ;
        int y ;
        try {
            this.setBackground(Color.BLACK) ;
            Image skullEmoji = ImageIO.read(new File("./icons/SkullEmoji.png"))
                .getScaledInstance(-1, (int)(this.getHeight()*0.8), Image.SCALE_DEFAULT) ;
            x = (this.getWidth() - skullEmoji.getWidth(null)) / 2;
            y = (this.getHeight() - skullEmoji.getHeight(null)) / 2;
            g.drawImage(skullEmoji, x, y, null) ;
        }

        catch (Exception e) {
            x = this.getWidth() / (this.getWidth()/200) ;
            y = this.getHeight() / 2 ;
            Rectangle rect = new Rectangle(this.getWidth()/4, this.getHeight()/4, this.getWidth()/2, this.getHeight()/2) ;
            g.setFont(new Font(g.getFont().getName(), g.getFont().getSize(), this.getWidth()/16)) ;
            g.setColor(Color.WHITE) ;
            this.centerString(g, rect, "WHERE IS THE SKULL :(", g.getFont());
        }
    }

    /**
     * Draws a centered String in a Rectangle
     * I didn't make this, I yonked it from StackOverflow
     * Here's the original question 
     * https://stackoverflow.com/questions/27706197/how-can-i-center-graphics-drawstring-in-java
     * @author Gilbert Le Blanc
     * @param g
     * @param r
     * @param s
     * @param font
     */
    public void centerString(Graphics g, Rectangle r, String s, 
        Font font) {
        FontRenderContext frc = new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(s, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;

        g.setFont(font);
        g.drawString(s, r.x + a, r.y + b);
    }
}
