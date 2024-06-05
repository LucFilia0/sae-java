package planeair.components.mapview;

//-- Import JxMapViewer

import org.jxmapviewer.viewer.GeoPosition;

/**
 * This class represents all the different items that could be drawn on a Map (FlightWaypoints, AirportWaypoints, FlightRoutes, etc...)
 * 
 * @author Luc le Manifik
 */
public abstract class MapItem extends org.jxmapviewer.viewer.DefaultWaypoint {
    
    /**
     * Creates a new MapItem.
     * 
     * @param geoPosition ({@link org.jxmapviewer.viewer.GeoPosition}) - The GeoPosition of the MapItem. Its position on Earth.
     * 
     * @author Luc le Manifik
     */
    protected MapItem(GeoPosition geoPosition) {
        super(geoPosition);
    }
}
