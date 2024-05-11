package ihm.waypoint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//-- Import Java

import java.io.File;
import java.io.IOException;

//-- Import JxMapViewer

import org.jxmapviewer.viewer.GeoPosition;

/**
 * This class is made to put interactive Waypoints on the Application's map.
 * The MapWaypoints are Waypoints with a JButton and an icon, which make it possible to iteract with.
 * 
 * @author Luc le Manifik
 */
public class MapWaypoint extends org.jxmapviewer.viewer.DefaultWaypoint {
    
    //-- MapWaypoint attributes

    /**
     * The name of the MapWaypoint
     */
    protected String name;

    /**
     * The button with which we can interact
     */
    protected WaypointButton waypointButton;

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
    public MapWaypoint(File iconFile, String name, GeoPosition geoPosition) {
        super(geoPosition);
        this.setName(name);
        this.waypointButton = null;

        try {
            this.waypointButton = new WaypointButton(iconFile);
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

        this.waypointButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("MapWaypoint : " + name);
            }
        });
    }

    //-- MapWaypoint Getters

    /**
     * Returns the name of the MapWaypoint
     * 
     * @return (String) - The name of the MapWaypoint
     * 
     * @author Luc le Manifik
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the WaypointButton of the MapWaypoint. The WaypointButton actually contains the visual of the MapWaypoint
     * 
     * @return ({@link ihm.waypoint.WaypointButton}) - The WaypointButton of the MapWaypoint
     * 
     * @author Luc le Manifik
     */
    public WaypointButton getWaypointButton() {
        return this.waypointButton;
    }

    //-- MapWaypoint Setters

    /**
     * Sets the name of the MapWaypoint
     * 
     * @param name (String) - The new name of the MapWaypoint
     * 
     * @author Luc le Manifik
     */
    public void setName(String name) {
        if(name != null) this.name = name;
    }

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
                this.waypointButton = new WaypointButton(iconFile);
            }catch(IOException e) {
                throw e;
            }
        }
    }
}
