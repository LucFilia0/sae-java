package planeair.components.mapview.mapwp;

//-- Import Java

import java.io.File;
import java.io.IOException;

//-- Import AWT

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//-- Import JxMapViewer

import org.jxmapviewer.viewer.GeoPosition;

import planeair.components.mapview.Map;


/**
 * This class is made to put interactive Waypoints on the Application's map.
 * The MapWaypoints are Waypoints with a JButton and an icon, which make it possible to iteract with.
 * 
 * @author Luc le Manifik
 */
public abstract class MapWaypoint extends planeair.components.mapview.MapItem {

    //-- MapWaypoint attributes

    /**
     * The button with which we can interact
     */
    protected MapWaypointButton waypointButton;

    //-- MapWaypoint Constructor

    /**
     * The constructor of the MapWaypoint class. Creates a new MapWaypoint.
     * 
     * @param iconFile ({@link java.io.File}) - The File of the MapWaypoint's icon (in fact the MapWaypoint's button's icon :exploding_head:)
     * @param name (String) - The name of the MapWaypoint
     * @param geoPosition ({@link org.jxmapviewer.viewer.GeoPosition}) - The position of the MapWaypoint on the Map
     * 
     * @throws IOException Throwed if the "iconFile" is not found, or does not match the "image" requirements.
     * 
     * @author Luc le Manifik
     */
    public MapWaypoint(File iconFile, GeoPosition geoPosition) {
        super(geoPosition);
        this.waypointButton = null;

        try {
            this.waypointButton = new MapWaypointButton(iconFile, this);
        }catch(IOException e) {
            e.printStackTrace();
        }

        this.initEvents();
    }

    /**
     * This function initiates the performed action when the WaypointButton is clicked.
     * 
     * @author Luc le Manifik
     */
    private void initEvents() {

        /* this.waypointButton.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent arg0) {
                switch(arg0.getStateChange()) {
                    case ItemEvent.SELECTED : 
                        System.out.println("SELECTED");
                        infoPanel.showInfos(((MapWaypointButton)arg0.getSource()).getMapWaypoint());
                        break;
                    case ItemEvent.DESELECTED : 
                        System.out.println("DESELECTED");
                        infoPanel.hideInfos();
                        break;
                    default : break;
                }
            }
            
        }); */

        this.waypointButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MapWaypointButton mwp = (MapWaypointButton) e.getSource();
                Map.infoPanel.showInfos(mwp.getMapWaypoint());
            }
            
        });

    }

    //-- MapWaypoint Getters

    /**
     * Returns the WaypointButton of the MapWaypoint. The WaypointButton actually contains the visual of the MapWaypoint
     * 
     * @return ({@link ihm.mapvisuals.mapwp.MapWaypointButton}) - The WaypointButton of the MapWaypoint
     * 
     * @author Luc le Manifik
     */
    public MapWaypointButton getWaypointButton() {
        return this.waypointButton;
    }

    //-- MapWaypoint Setters

    /**
     * Sets the icon of the MapWaypoint's WaypointButton (which is the visual of the MapWaypoint).
     * 
     * @param iconFile ({@link java.io.File}) - The new icon's File
     * 
     * @throws IOException Throwed if the new File is not found or does not match the "image" requirements.
     * 
     * @author Luc le Manifik
     */
    public void setButtonIcon(File iconFile) throws IOException {
        if(iconFile != null) {
            try {
                this.waypointButton = new MapWaypointButton(iconFile, this);
            }catch(IOException e) {
                throw e;
            }
        }
    }

    public abstract String toString();
}
