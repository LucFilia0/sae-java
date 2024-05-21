package ihm.mapvisuals.mapwp;

//-- Import Java

import java.io.File;

import javax.imageio.ImageIO;

//-- Import AWT

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;

//-- Import Swing

import javax.swing.ImageIcon;

//-- Import Exceptions

import java.io.IOException;

/**
 * This class is the button of a MapWaypoint ({@link ihm.mapvisuals.mapwp.MapWaypoint}), and allows the user to click and interact with the
 * Waypoints on the Map.
 * 
 * @author Luc le Manifik
 */
public class MapWaypointButton extends javax.swing.JButton {

    //-- WaypointButton Attributes

    /**
     * The size of the button, and so, the size of the MapWaypoint (fixed size, even when you zoom)
     */
    public static final int BUTTON_SIZE = 30;

    //-- WaypointButton Constructor

    /**
     * The WaypointButton's constructor. Creates a new WaypointButton.
     * The icon is prompt from the "iconFile" parameter
     * 
     * @param iconFile ({@link java.io.File}) - The File of the button's icon.
     * 
     * @throws IOException Throwed if the File does not exist or does not match the "image" requirements.
     */
    public MapWaypointButton(File iconFile) throws IOException {

        Image scaledImage = null;

        try {
            // The scaled image is the "resized" version of the "iconFile" image. To fit the wanted size on the map (BUTTON_SIZE)
            scaledImage = ImageIO.read(iconFile).getScaledInstance(MapWaypointButton.BUTTON_SIZE, MapWaypointButton.BUTTON_SIZE, Image.SCALE_SMOOTH);
        }catch(IOException e) {
            throw e; // What the heck is IOException ?? Seriously XD
        }

        // Sets the background of the button unfilled, and makes the border disapear
        this.setContentAreaFilled(false);
        this.setBorder(null);

        this.setIcon(new ImageIcon(scaledImage));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Makes a little funny hand when the button is hovered :)
        this.setPreferredSize(new Dimension(MapWaypointButton.BUTTON_SIZE, MapWaypointButton.BUTTON_SIZE)); // Don't ask why, but "setSize" didn't work...
    }
}