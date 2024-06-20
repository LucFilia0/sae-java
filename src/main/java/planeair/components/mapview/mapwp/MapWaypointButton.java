package planeair.components.mapview.mapwp;

//#region IMPORTS

import javax.swing.BorderFactory;

import java.io.File;
import java.io.IOException;

import java.awt.Cursor;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import planeair.App;
import planeair.components.mapview.mapwp.flightwp.FlightWaypoint;
import planeair.components.menu.infos.NInfoPanel;
import planeair.graph.graphutil.PanelCreator;

//#endregion

/**
 * This class is the button of a {@link ihm.mapvisuals.mapwp.MapWaypoint MapWaypoint}, 
 * and allows the user to click and interact with the MapWaypoints on the Map.
 * 
 * @author Luc le Manifik
 */
public class MapWaypointButton extends javax.swing.JButton {

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
     * The Waypoint currently being clicked
     */
    public static MapWaypointButton waypointSelected ;

    /**
     * Tells whether or not this waypoint is selected
     */
    private boolean selected ;

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

        // Sets the background of the button unfilled, and makes the border disapear
        this.setContentAreaFilled(false);
        this.setBorder(null);

        this.setIcon(rotatedImage);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Makes a little funny hand when the button is hovered :)

        this.initEvents();
    }

    //#endregion

    //#region PRIVATE METHODS

    /**
     * This method initiates the performed action when the MapWaypointButton is clicked.
     * 
     * @author Luc le Manifik
     */
    private void initEvents() {

        this.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MapWaypointButton mwp = (MapWaypointButton) e.getSource();
                mwp.changeSelection(true) ;
            }
        });

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

    /**
     * Returns whether this waypoint is selected or not
     * @return true if it is selected, false if not
     */
    public boolean isSelected() {
        return this.selected ;
    }

    //#endregion

    //#region GETTERS

    /**
     * Change the selection of this button
     */
    public void setSelected(boolean selected) {
        this.selected = selected ;
    }

    //#endregion

    //#region PUBLIC METHODS

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
        NInfoPanel infoPanel = App.app.getMainScreen().getInfoPanel() ;
        if (isClicked) {
            // If it is selected, then remove the selection
            if (this.isSelected()) {
                selected = false ;
                this.setSelectionStyle() ;
                waypointSelected = null ;
                infoPanel.hideInfos() ;
                
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
                infoPanel.showInfos(mapWaypoint) ;
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
    
    //#endregion

}
