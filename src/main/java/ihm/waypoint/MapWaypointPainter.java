package ihm.waypoint;

//-- Import AWT

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

//-- Import JxMapViewer

import org.jxmapviewer.JXMapViewer;

/**
 * This class paint's the MapWaypoints over the Map. It extends {@link org.jxmapviewer.viewer.WaypointPainter WaypointPainter}
 * from JxMapViewer.
 * 
 * @author Luc le Manifik
 */
public class MapWaypointPainter extends org.jxmapviewer.viewer.WaypointPainter<MapWaypoint> {
    
    @Override
    protected void doPaint(Graphics2D g, JXMapViewer map, int width, int height) {

        for(MapWaypoint waypoint : this.getWaypoints()) {
            // waypointLocationOnScreen is the transcription of the waypoint's GeoPosition on screen pixels
            Point2D waypointLocationOnScreen = map.getTileFactory().geoToPixel(waypoint.getPosition(), map.getZoom());
            Rectangle screen = map.getViewportBounds();

            WaypointButton waypointButton = waypoint.getWaypointButton();

            // In order to center the button on the whished position
            int x = (int) (waypointLocationOnScreen.getX() - screen.getX() - waypointButton.getWidth()/2);
            int y = (int) (waypointLocationOnScreen.getY() - screen.getY() - waypointButton.getHeight()/2);

            waypointButton.setLocation(x, y);
        }
    }
}
