package planeair.components.mapview.mapwp;

//#region IMPORTS
import java.io.File;
import java.io.IOException;

import org.jxmapviewer.viewer.GeoPosition;
//#endregion

/**
 * This class puts interactive Waypoints on the Application's map.
 * The MapWaypoints are Waypoints with a JButton and an icon, which makes them clickable .
 * 
 * This class is abstract, and can only be instanced throught one of its children (AirportWaypoint, FlightWaypoint...)
 * 
 * @author Luc le Manifik
 */
public abstract class MapWaypoint extends org.jxmapviewer.viewer.DefaultWaypoint {

    //#region ATTRIBUTES

    /**
     * The button with which we can interact.
     * This button will be placed at the same position that the MapWaypoint's Coordinate on the Map, 
     * and will be constantly repainted.
     */
    protected MapWaypointButton waypointButton;

    //#endregion

    //#region CONSTRUCTORS

    /**
     * Creates a new MapWaypoint.
     * 
     * @param iconFile The {@link java.io.File File} of the MapWaypoint's icon (in fact the MapWaypoint's button's icon 🤯)
     * @param geoPosition The {@link org.jxmapviewer.viewer.GeoPosition position} of the MapWaypoint on the Map
     * @param degree The orientation degree of the MapWaypoint's image
     * 
     * @throws IOException Thrown if the "iconFile" is not found, or does not match the "image" requirements.
     * 
     * @author Luc le Manifik
     */
    public MapWaypoint(File iconFile, GeoPosition geoPosition, double degree) {

        super(geoPosition);
        this.waypointButton = null;

        try {
            this.waypointButton = new MapWaypointButton(iconFile, this, degree);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    //#endregion

    //#region GETTERS

    /**
     * Gets the MapWaypointButton of the MapWaypoint. 
     * The MapWaypointButton actually contains the visual of the MapWaypoint.
     * 
     * @return ({@link ihm.mapvisuals.mapwp.MapWaypointButton MapWaypointButton}) 
     * - The MapWaypointButton linked to the MapWaypoint
     * 
     * @author Luc le Manifik
     */
    public MapWaypointButton getWaypointButton() {
        return this.waypointButton;
    }

    //#endregion

    //#region SETTERS

    /**
     * Sets the icon of the MapWaypoint's MapWaypointButton (which is the visual of the MapWaypoint).
     * 
     * @param iconFile The new icon's {@link java.io.File File}
     * 
     * @throws IOException Thrown if the new File is not found or does not match the "image" requirements.
     * 
     * @author Luc le Manifik
     */
    public void setButtonIcon(File iconFile, double degree) throws IOException {
        
        if(iconFile != null) {
            try {
                this.waypointButton = new MapWaypointButton(iconFile, this, degree);
            }catch(IOException e) {
                throw e;
            }
        }
    }

    //#endregion

    //#region TOSTRING

    /**
     * Returns the correctly formated informations of the MapWaypointButton
     * First part
     */
    public abstract String toStringFirst();

    /**
     * Returns the correctly formated informations of the MapWaypointButton
     * First part
     */
    public abstract String toStringSecond();


    /**
     * Returns the correctly formated informations of the MapWaypointButton
     */
    public abstract String toString();

    //#endregionw
}
