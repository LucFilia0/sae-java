package planeair.components.mapview.mapwp;

//#region IMPORTS
import javax.swing.JToggleButton;
import javax.swing.border.StrokeBorder;

import java.io.File;
import java.io.IOException;

import java.awt.Cursor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.BasicStroke;
import java.awt.Color;

import planeair.components.mapview.mapwp.flightwp.FlightWaypoint;
import planeair.App;
//#endregion

/**
 * This class is the button of a {@link ihm.mapvisuals.mapwp.MapWaypoint MapWaypoint}, 
 * and allows the user to click and interact with the MapWaypoints on the Map.
 * 
 * @author Luc le Manifik
 */
public class MapWaypointButton extends JToggleButton {

    //#region ATTRIBUTES

    /**
     * The size of the button, and so, the size of the MapWaypoint (fixed size, even when you're zooming)
     */
    public static final int BUTTON_SIZE = 30;

    /**
     * The MapWaypoint which is linked to the button.
     * Used to get the informations of the MapWaypoints which is 'clicked'
     */
    private MapWaypoint mapWaypoint;
    
    /**
     * The Waypoint currently selected
     */
    public static MapWaypointButton waypointSelected ;

    //-- WaypointButton Constructor

    /**
     * The WaypointButton's constructor. Creates a new WaypointButton.
     * The icon is prompt from the "iconFile" parameter
     * 
     * @param iconFile ({@link java.io.File File}) - The File from which is prompted the button's icon.
     * 
     * @throws IOException Threw if the File does not exist or does not match the "image" requirements.
     */
    public MapWaypointButton(File iconFile, MapWaypoint mapWaypoint, double radian) throws IOException {

        this.mapWaypoint = mapWaypoint;

        RotatedImage rotatedImage = null;

        try {
            rotatedImage = new RotatedImage(iconFile, radian);
        }catch(IOException e) {
            throw e;
        }

        this.initListeners() ;

        // Sets the background of the button unfilled, and makes the border disapear
        this.setContentAreaFilled(false);
        this.setBorder(null);

        this.setIcon(rotatedImage);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Makes a little funny hand when the button is hovered :)
    }

    //#endregion

    //#region PRIVATE METHODS
    
    /**
     * Adds all the listeners linked to this component
     */
    private void initListeners() {
        this.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    MapWaypointButton.this.select() ;
                }
                else {
                    MapWaypointButton.this.deselect() ;
                }
            }
        }) ;
    }

    //#endregion

    //#region GETTERS

    /**
     * Returns the MapWaypoint which is linked to the MapWaypointButton.
     * Used to get the informations of the clicked MapWaypoint.
     * 
     * @return ({@link planeair.components.mapview.mapwp.MapWaypoint MapWaypoint})
     */
    public MapWaypoint getMapWaypoint() {
        return this.mapWaypoint;
    }

    //#endregion

    //#region SETTERS

     /**
     * Changes this {@code Waypoint}'s style based on whether or not it is Selected
     * If the waypoint is a FlightWaypoint, also update its Node
     * 
     * @param selected {@code true} if it is currently selected, 
     * {@code false} if not
     * 
     * @author Nathan LIEGEON
     */
    public void setSelectionStyle(boolean selected) {
        if (selected) {
            this.setForeground(Color.RED) ;
            this.setBorder(new StrokeBorder(new BasicStroke(3))) ;
        } else {
            this.setBorder(null) ;
        }

        if (this.getMapWaypoint() instanceof FlightWaypoint)
            ((FlightWaypoint)this.getMapWaypoint())
                .updateFlightStyle(selected) ;
    }

    //#region PUBLIC METHODS

    /**
     * <html>Undoes the selection on a waypoint by removing the border around 
     * and by hiding the info panel showing its information.<br>
     * If this waypoint is a {@link FlightWaypoint} then also highlight its
     * corresponding Flight on the graph.<br>
     * <br>
     * <strong>/!\ GETS CALLED BY select() ON THE PREVIOUSLY SELECTED
     * WAYPOINT /!\</strong>
     * </html>
     * 
     * @author Nathan LIEGEON
     * @see select
     */
    public synchronized void deselect() {
        setSelectionStyle(false) ;
        this.setSelected(false) ;
        waypointSelected = null ;
        App.app.getMainScreen().getInfoPanel().hideInfos() ;
    }

    /**
     * <html>
     * Highlights a {@code Waypoint} when the user clicks on it by 
     * putting a colored border around it,<br>
     * <strong>only one button can be selected at a time.</strong>
     * <br><br>
     * If the {@code Waypoint} corresponds to a flight, then also updates
     * its related {@link org.graphstream.graph.Node Node} 
     * style to also appear selected
     * </html>
     * 
     * @author Nathan LIEGEON
     * @see deselect
     */
    public synchronized void select() {
        if (waypointSelected != null) {
            waypointSelected.deselect() ;
        }
        
        setSelectionStyle(true) ;
        this.setSelected(true) ;
        waypointSelected = this ;
        App.app.getMainScreen().getInfoPanel().showInfos(mapWaypoint) ;
    }
    
    //#endregion

}
