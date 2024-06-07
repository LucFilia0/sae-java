package planeair.components.mapview;

//-- Import AWT

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//-- Import JxMapViewer

import org.jxmapviewer.OSMTileFactoryInfo; // For default parameters of the Map

import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.DefaultTileFactory; // For default paramters of the Map

import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;

//-- Import Plane AIR

import planeair.util.Airport;
import planeair.util.AirportSet;
import planeair.graph.graphtype.FlightsIntersectionGraph;
import planeair.graph.graphutil.Flight;
import planeair.components.mapview.mapwp.MapWaypointPainter;
import planeair.components.mapview.mapwp.airportwp.ActiveAirportWaypoint;
import planeair.components.mapview.mapwp.airportwp.AirportWaypoint;
import planeair.components.mapview.mapwp.airportwp.InactiveAirportWaypoint;
import planeair.components.mapview.mapwp.flightwp.FlightWaypoint;
import planeair.components.menu.NInfoPanel;

/**
 * This class is the Map which appears on the Application.
 * The Map in itself extends the {@link org.jxmapviewer.JXMapViewer JXMapViewer} object.
 * 
 * @author Luc le Manifik
 */
public class Map extends org.jxmapviewer.JXMapViewer {

    /**
     * The NInfoPanel which will prompt all the infos of the MapWaypoints*
     */
    public static NInfoPanel infoPanel = null;
    
    //-- Map Attributes

    /**
     * The GeoPosition which is focused when the key "Space" is pressed
     */
    private GeoPosition center_geoPosition;

    /**
     * The zoom when the key "Space" is pressed
     */
    private int center_zoom;

    /* /**
     * The Set which contains all the Waypoint of the Map (Flights and Airports)
     *
    private HashSet<MapItem> itemSet; */

    /**
     * The WaypointPainter which is used to draw all the Waypoints on the Map
     */
    private MapWaypointPainter itemPainter;

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
        /*
        this.itemSet = new HashSet<MapItem>();
        */
        this.itemPainter = new MapWaypointPainter();

        this.setOverlayPainter(this.itemPainter);

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
            this.itemPainter.getAirportWaypoints().add(new ActiveAirportWaypoint(airport, airport.getPosition()));
        }

        // Adding the inactive Airports
        for(Airport airport : airportSet.getInactiveAirports()) {
            this.itemPainter.getAirportWaypoints().add(new InactiveAirportWaypoint(airport, airport.getPosition()));
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

            // Adding the Flight in the "currentFlights" set, in order to prompt the Flight's route, even if he's not currently flying
            this.itemPainter.getCurrentFlights().add(flight);

            GeoPosition currentFlightPosition = flight.getCurrentGeoPosition();
            // The function returns null is the Flight is not currently flying
            if(currentFlightPosition != null) {
                this.itemPainter.getFlightWaypoints().add(new FlightWaypoint(flight, currentFlightPosition));
            }
        });
    }

    /**
     * This procedure paints the MapWaypoints on the Map.
     * the Flights are only painted id they are currently flying.
     * 
     * @author Luc le Manifik
     */
    public void paintMapItems(AirportSet airportSet, FlightsIntersectionGraph fig) {

        this.addAirports(airportSet);
        this.addFlights(fig);

        /* Adds the WaypointButtons, which are the visual for Waypoint,
        * they need to be added manually, because JxMap is not made to have buttons, but Waypoints
        * So it will not show them automatically
        */
        for(AirportWaypoint waypoint : this.itemPainter.getAirportWaypoints()) {
            
            this.add(waypoint.getWaypointButton());
        }

        for(FlightWaypoint   waypoint : this.itemPainter.getFlightWaypoints()) {

            this.add(waypoint.getWaypointButton());
        }
    }

    //-- Map Methods --> Center default

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
