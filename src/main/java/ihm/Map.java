package ihm;

//-- Import Java

import java.util.HashSet;

//-- Import AWT

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//-- Import JxMapViewer

import org.jxmapviewer.OSMTileFactoryInfo; // For default parameters of the Map

import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointPainter;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory; // For default paramters of the Map

import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;

//-- Import Plane AIR

import graph.FlightsIntersectionGraph;
import graph.Flight;

import util.Airport;
import util.AirportSet;

import ihm.waypoint.ActiveAirportWaypoint;
import ihm.waypoint.FlightWaypoint;
import ihm.waypoint.InactiveAirportWaypoint;
import ihm.waypoint.MapWaypoint;
import ihm.waypoint.MapWaypointPainter;

/**
 * This class is the Map which appears on the Application.
 * The Map in itself extends the {@link org.jxmapviewer.JXMapViewer JXMapViewer} object.
 * 
 * @author Luc le Manifik
 */
public class Map extends org.jxmapviewer.JXMapViewer {
    
    //-- Map Attributes

    /**
     * The GeoPosition which is focused when the key "Space" is pressed
     */
    private GeoPosition center_geoPosition;

    /**
     * The zoom when the key "Space" is pressed
     */
    private int center_zoom;

    /**
     * The Set which contains all the Waypoint of the Map (Flights and Airports)
     */
    private HashSet<MapWaypoint> waypointSet;

    /**
     * The WaypointPainter which is used to draw all the Waypoints on the Map
     */
    private MapWaypointPainter waypointPainter;

    //-- Map Constructor

    /**
     * Map class's constructor. Creates a new Map.
     * Initiates the view of the Map at the center of the France.
     * 
     * @author Luc le Manifik
     */
    public Map() {

        this.initAttributes();
        this.initEvents();

        // Centering the Map to it's default position
        this.center();
    }

    /**
     * Initiates the different attributes of the Map
     * 
     * @author Luc le Manifik
     */
    private void initAttributes() {

        // Default center position
        this.center_geoPosition = new GeoPosition(47, 3);
        this.center_zoom = 13;

        // Default Waypoints stuff...
        this.waypointSet = new HashSet<MapWaypoint>();
        this.waypointPainter = new MapWaypointPainter();

        this.setOverlayPainter(this.waypointPainter);

        // Default JxMapViewer settings
        TileFactoryInfo _tileFactoryInfo = new OSMTileFactoryInfo(); // The parameters of the tiles of the Map
        DefaultTileFactory _defaultTileFactory = new DefaultTileFactory(_tileFactoryInfo); //Setting the default tile creation to the tiles we just defined      
        this.setTileFactory(_defaultTileFactory); // setting the map's 'tile factory'
    }

    /**
     * Initiates the different events, for the mouse, the space bar, etc...
     * 
     * @author Luc le Manifik
     */
    private void initEvents() {

        // Setting up the basic controls of the map (you touch you dead :skull:)
        PanMouseInputListener _mouseListener = new PanMouseInputListener(this);
        this.addMouseListener(_mouseListener); // The mouse is detected
        this.addMouseMotionListener(_mouseListener); // The map can move, when we slide the mouse
        this.addMouseWheelListener(new ZoomMouseWheelListenerCursor(this)); // The map can be zoomed, when we use the wheel of the mouse

        // Adding the command
        // -> When "Space" is pressed : Default view of the France
        this.setFocusable(true); // Allow the map to listen a "KeyListener"
        this.addKeyListener(new CenteringMapWhenSpacePressed());
    }

    //-- Map Getters

    /**
     * Get the default position when 'Space' is pressed
     * 
     * @return ({@link org.jxmapviewer.viewer.GeoPosition})
     * 
     * @author Luc le Manifik
     */
    public GeoPosition getCenterGeoPosition() {
        return this.center_geoPosition;
    }

    /**
     * Get the default Zoom when 'Space' is pressed
     * 
     * @return (int)
     * 
     * @author Luc le Manifik
     */
    public int getCenterZoom() {
        return this.center_zoom;
    }

    //-- Map Setters
    

    /* /**
     * This method sets the WaypointRenderer when importing Flights or Airports, so that it becomes possible to have
     * multiple Waypoint's design on the same overlay. It needs to change depending of what Object it's currently imported.
     * 
     * @param iconFile ({@link java.io.File}) - The image of the Waypoint currently imported
     * 
     * @see {@link #addAirports(AirportSet, File) addAirports} function
     * @see {@link #addFlights(FlightsIntersectionGraph, File) addFlights} function
     * 
     * @author Luc le Manifik
     *
    public void setWaypointPainter(File iconFile) {

        this.waypointPainter.setRenderer(new WaypointRenderer<Waypoint>() {
            @Override
            public void paintWaypoint(Graphics2D g, JXMapViewer map, Waypoint w)
            {
                BufferedImage img = null;

                try {
                    img = ImageIO.read(iconFile); // Setting up the visual of the Waypoints
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (img != null) {
                    Point2D point = map.getTileFactory().geoToPixel(w.getPosition(), map.getZoom());
                    
                    int x = (int)point.getX() -img.getWidth() / 2;
                    int y = (int)point.getY() -img.getHeight();
                    
                    g.drawImage(img, x, y, null);
                }
            }
        });
    } */

    //-- Map Methods

    /**
     * This method adds the Airports' icons on the Map.
     * 
     * @param airportSet ({@link util.AirportSet}) - The AirportSet which contains all the Airports, their position, etc...
     * 
     * @author Luc le Manifik
     */
    public void addAirports(AirportSet airportSet) {

        // Adding the active Airports
        for(Airport airport : airportSet.getActiveAirports()) {
            this.waypointSet.add(new ActiveAirportWaypoint(airport.getName(), airport.getGeoPosition()));
        }

        // Adding the inactive Airports
        for(Airport airport : airportSet.getInactiveAirports()) {
            this.waypointSet.add(new InactiveAirportWaypoint(airport.getName(), airport.getGeoPosition()));
        }
    }

    /**
     * This method adds all the Flights which are in the FIG.
     * 
     * @author Luc le Manifik
     */
    private void addFlights(FlightsIntersectionGraph fig) {

        fig.forEach(node -> {
            Flight flight = (Flight)node;
            GeoPosition currentFlightPosition = flight.getCurrentGeoPosition();
            // The function returns null is the Flight is not currently flying
            if(currentFlightPosition != null) {
                this.waypointSet.add(new FlightWaypoint(flight.getId(), currentFlightPosition));
            }
        });
    }

    /**
     * This procedure paints the MapWaypoints on the Map.
     * the Flights are only painted id they are currently flying.
     */
    public void paintWaypoints(AirportSet airportSet, FlightsIntersectionGraph fig) {

        // removing all the current MapWaypoints from the Map
        // this.map.removeAll();

        this.addAirports(airportSet);
        this.addFlights(fig);

        this.waypointPainter.setWaypoints(this.waypointSet);

        for(MapWaypoint waypoint : this.waypointSet) {
            /* Adds the WaypointButtons, which are the visual for Waypoint,
             * they need to be added manually, because JxMap is not made to have buttons, but Waypoints
             * So it will not show them automatically
             */
            this.add(waypoint.getWaypointButton());
        }
    }

    /**
     * This method centers the Map at the 'center_geoposition' location, with the 'center_zoom' zoom value.
     * 
     * @see setCenterGeoPosition() - in {@link ihm.Map}
     * @see setCenterZoom() - in {@link ihm.Map}
     * 
     * @author Luc le Manifik
     */
    public void center() {
        this.setAddressLocation(center_geoPosition);
        this.setZoom(center_zoom);
    }

    /**
     * Set the default zoom when 'Space' is pressed
     * 
     * @param center_zoom (int) - The new default zoom value
     * 
     * @author Luc le Manifik
     */
    public void setCenterZoom(int center_zoom) {
        if(center_zoom > 0)
            this.center_zoom = center_zoom;
    }

    /**
     * Set the default position when 'Space' is pressed
     * 
     * @param center_GeoPosition ({@link org.jxmapviewer.viewer.GeoPosition}) - The new GeoPosition where the focus will be made
     * 
     * @author Luc le Manifik
     */
    public void setCenterGeoPosition(GeoPosition center_GeoPosition) {
        if(!center_GeoPosition.equals(null))
            this.center_geoPosition = center_GeoPosition;
    }

    //-- Map KeyListener

    /**
     * This class implements the KeyListener interface, and allows to center the view of the Map when the key 'Space' is pressed
     * 
     * @author Luc le Manifik
     */
    private class CenteringMapWhenSpacePressed implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                center(); // Center the Map
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // Do nothing
        }

        @Override
        public void keyTyped(KeyEvent e) {
            // Do nothing
        }
    }
}
