package planeair.components.mapview.mapwp;

//#region IMPORTS

    //#region JAVA

    import java.io.File;

    //#endregion

    //#region AWT

    import java.awt.Cursor;

    //#endregion

    //#region EXCEPTIONS

    import java.io.IOException;

    //#endregion

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

    //#endregion

    //#region CONSTRUCTORS

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
}
