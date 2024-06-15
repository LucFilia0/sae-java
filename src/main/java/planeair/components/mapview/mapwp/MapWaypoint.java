package planeair.components.mapview.mapwp;

//#region IMPORTS

    //#region JAVA

    import java.io.File;
    import java.io.IOException;

    //#endregion

    //#region AWT

    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;

    //#endregion

    //#region JXMAPVIEWER

    import org.jxmapviewer.viewer.GeoPosition;

    //#endregion

    //#region PLANEAIR

    import planeair.components.mapview.Map;

    //#endregion

//#endregion

/**
 * This class is made to put interactive Waypoints on the Application's map.
 * The MapWaypoints are Waypoints with a JButton and an icon, which make them clickable 🤯.
 * 
 * This class is abstract, and can only be instaced throught one of its children (AirportWaypoint, FlightWaypoint...)
 * 
 * @author Luc le Manifik
 */
public abstract class MapWaypoint extends org.jxmapviewer.viewer.DefaultWaypoint {

    //#region ATTRIBUTES

    /**
     * The button with which we can interact.
     * This button will be placed at the same position that the MapWaypoint on the Map, 
     * and will be constantly repainted.
     */
    protected MapWaypointButton waypointButton;

    //#endregion

    //#region CONSTRUCTORS

    /**
     * Creates a new MapWaypoint.
     * 
     * @param iconFile ({@link java.io.File File}) - The File of the MapWaypoint's icon (in fact the MapWaypoint's button's icon 🤯)
     * @param geoPosition ({@link org.jxmapviewer.viewer.GeoPosition}) - The position of the MapWaypoint on the Map
     * @param degree (double) - The orientation degree of the MapWaypoint's image
     * 
     * @throws IOException Threw if the "iconFile" is not found, or does not match the "image" requirements.
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

        this.waypointButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MapWaypointButton mwp = (MapWaypointButton) e.getSource();
                if(Map.infoPanel != null)
                    Map.infoPanel.showInfos(mwp.getMapWaypoint());
            }
        });

    }

    //#endregion

    //#region GETTERS

    /**
     * Gets the MapWaypointButton of the MapWaypoint. 
     * The MapWaypointButton actually contains the visual of the MapWaypoint.
     * 
     * @return ({@link ihm.mapvisuals.mapwp.MapWaypointButton MapWaypointButton}) - The MapWaypointButton linked to the MapWaypoint
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
     * @param iconFile ({@link java.io.File File}) - The new icon's File
     * 
     * @throws IOException Threw if the new File is not found or does not match the "image" requirements.
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
     */
    public abstract String toString();

    //#endregionw
}
