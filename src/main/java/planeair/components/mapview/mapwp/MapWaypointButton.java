package planeair.components.mapview.mapwp;

//-- Import Java

import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;

import planeair.components.mapview.mapwp.flightwp.FlightWaypoint;
import planeair.graph.graphutil.PanelCreator;

import java.awt.BasicStroke;
import java.awt.Color;

//-- Import AWT

import java.awt.Cursor;
import java.awt.Image;

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

    /**
     * The MapWaypoint which is linked to the button
     */
    private MapWaypoint mapWaypoint;

    /**
     * The Waypoint currently being clicked
     */
    public static MapWaypointButton waypointSelected ;

    private boolean selected ;

    //-- WaypointButton Constructor

    /**
     * The WaypointButton's constructor. Creates a new WaypointButton.
     * The icon is prompt from the "iconFile" parameter
     * 
     * @param iconFile ({@link java.io.File}) - The File of the button's icon.
     * 
     * @throws IOException Throwed if the File does not exist or does not match the "image" requirements.
     */
    public MapWaypointButton(File iconFile, MapWaypoint mapWaypoint, double radian) throws IOException {

        this.mapWaypoint = mapWaypoint;

        Image scaledImage = null;

        try {
            // The scaled image is the "resized" version of the "iconFile" image. To fit the wanted size on the map (BUTTON_SIZE)
            scaledImage = ImageIO.read(iconFile).getScaledInstance(MapWaypointButton.BUTTON_SIZE, MapWaypointButton.BUTTON_SIZE, Image.SCALE_SMOOTH);
        }catch(IOException e) {
            throw e; // What the heck is IOException ?? Seriously XD
        }

        // Sets the background of the button unfilled, and makes the border disapear
        this.setContentAreaFilled(true);
        this.setBorder(null);
        this.setContentAreaFilled(false);

        this.setIcon(new RotatedImage(scaledImage, radian));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Makes a little funny hand when the button is hovered :)
    }

    //-- MapWaypointButton Getters

    /**
     * Returns the MapWaypoint which is linked to the MapWaypointButton
     * 
     * @return ({@link planeair.components.mapview.mapwp.MapWaypoint MapWaypoint})
     */
    public MapWaypoint getMapWaypoint() {
        return this.mapWaypoint;
    }

    /**
     * Highlights a waypoint when clicked on by putting a
     * colored border around it, only one button
     * can have be selected at a time
     * 
     * If the waypoint corresponds to a flight, then also updates
     * its related graph node's style to also appear selected
     * 
     * @author Nathan LIEGEON
     */
    public void changeSelection(boolean isClicked) {
        // Case when the button is first clicked
        if (isClicked) {
            // If it is selected, then remove the selection
            if (this.isSelected()) {
                selected = false ;
                this.setSelectionStyle() ;
                waypointSelected = null ;
            }
            else {
                // Change the selection from the previous one to this one
                if (waypointSelected != null) {
                    waypointSelected.setSelected(false);
                    waypointSelected.setSelectionStyle() ;
                }
                selected = true ;
                this.setSelectionStyle() ;
                waypointSelected = this ;
            }
        }

        // Case if its style has to be updated
        else if (!this.isSelected()) {
            this.setSelectionStyle() ;
        }

        this.repaint() ;
    }

    /**
     * Changes this waypoint's style based on whether or not it is Selected
     * If the waypoint is a FlightWaypoint, also update its Node
     * 
     * @param isSelected True if it is currently selected, false if not
     * 
     * @author Nathan LIEGEON
     */
    public void setSelectionStyle() {
        if (this.isSelected()) {
            this.setForeground(Color.RED) ;
            this.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3))) ;
            if (this.getMapWaypoint() instanceof FlightWaypoint) {
                FlightWaypoint fwp = (FlightWaypoint)this.getMapWaypoint() ;
                PanelCreator.setSelectedStyle(fwp.getFlight()) ;
            }
        }

        else {
            this.setBorder(null) ;
            if (this.getMapWaypoint() instanceof FlightWaypoint) {
                FlightWaypoint fwp = (FlightWaypoint)this.getMapWaypoint() ;
                PanelCreator.removeSelectedStyle(fwp.getFlight()) ;
            }
        }

        this.repaint() ;
    }

    /**
     * Returns whether this waypoint is selected or not
     * @return true if it is selected, false if not
     */
    public boolean isSelected() {
        return this.selected ;
    }

    /**
     * Change the selection of this button
     */
    public void setSelected(boolean selected) {
        this.selected = selected ;
    } 

}
