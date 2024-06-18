package planeair.components.mapview;

//#region IMPORTS

    //#region AWT

    import java.awt.event.KeyEvent;
    import java.awt.event.KeyListener;
    import java.util.Iterator;

    //#endregion

    //#region JXMAPVIEWER

    import org.jxmapviewer.OSMTileFactoryInfo; // For default parameters of the Map

    import org.jxmapviewer.viewer.GeoPosition;
    import org.jxmapviewer.viewer.TileFactoryInfo;
    import org.jxmapviewer.viewer.DefaultTileFactory; // For default paramters of the Map

    import org.jxmapviewer.input.PanMouseInputListener;
    import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;

    //#endregion

    //#region PLANEAIR

    import planeair.util.Airport;
    import planeair.util.AirportSet;
    import planeair.util.NTime;
    import planeair.graph.graphtype.FlightsIntersectionGraph;
    import planeair.graph.graphutil.Flight;
    import planeair.App;
    import planeair.components.mapview.mapwp.MapWaypointButton;
    import planeair.components.mapview.mapwp.MapWaypointPainter;
    import planeair.components.mapview.mapwp.airportwp.ActiveAirportWaypoint;
    import planeair.components.mapview.mapwp.airportwp.InactiveAirportWaypoint;
    import planeair.components.mapview.mapwp.flightwp.FlightWaypoint;
    import planeair.components.menu.infos.NInfoPanel;

    //#endregion

//#endregion

/**
 * This class is the Map which appears on the Application.
 * The Map in itself extends the {@link org.jxmapviewer.JXMapViewer JXMapViewer} object.
 * 
 * @author Luc le Manifik
 */
public class Map extends org.jxmapviewer.JXMapViewer {

    //#region STATIC VARIABLES

    /**
     * The NInfoPanel which will prompt all the infos of the MapWaypoints*
     */
    public static NInfoPanel infoPanel = null;

    //#endregion

    //#region ATTRIBUTES
    
    /**
     * The GeoPosition which is focused when the key "Space" is pressed.
     */
    private GeoPosition DefaultGeoPosition;

    /**
     * The zoom when the key "Space" is pressed
     */
    private int defaultZoom;

    /**
     * The WaypointPainter which is used to draw all the Waypoints on the Map
     */
    private MapWaypointPainter itemPainter;

    /**
     * Homepage blablabla
     */
    private App app ;

    //#endregion

    //#region CONSTRUCTORS

    /**
     * Map class's constructor. Creates a new Map.
     * Initiates the view of the Map at the France's center.
     * 
     * @author Luc le Manifik
     */
    public Map(App app) {
        this.app = app ;

        this.initAttributes();
        this.initEvents();

        // Centering the Map to it's default position (France's center)
        this.center();
    }

    //#endregion

    //#region GETTERS

    /**
     * Gets the default position center when 'Space' is pressed
     * 
     * @return ({@link org.jxmapviewer.viewer.GeoPosition GeoPosition}) - The default position, when 'space' is pressed
     * 
     * @author Luc le Manifik
     */
    public GeoPosition getDefaultGeoPosition() {
        return this.DefaultGeoPosition;
    }

    /**
     * Gets the default Zoom when 'Space' is pressed
     * 
     * @return (int)
     * 
     * @author Luc le Manifik
     */
    public int getDefaultZoom() {
        return this.defaultZoom;
    }

    //#endregion

    //#region SETTERS
    
    /**
     * Sets the default zoom when 'Space' is pressed
     * 
     * @param defaultZoom (int) - The new default zoom value
     * 
     * @author Luc le Manifik
     */
    public void setDefaultZoom(int defaultZoom) {
        if(defaultZoom > 0)
            this.defaultZoom = defaultZoom;
    }

    /**
     * Set the default position when 'Space' is pressed
     * 
     * @param defaultGeoPosition ({@link org.jxmapviewer.viewer.GeoPosition}) - The new GeoPosition where the focus will be made
     * 
     * @author Luc le Manifik
     */
    public void setCenterGeoPosition(GeoPosition defaultGeoPosition) {
        if(!defaultGeoPosition.equals(null))
            this.DefaultGeoPosition = defaultGeoPosition;
    }

    //#endregion

    //#region PRIVATE METHODS

    /**
     * Initiates the different attributes of the Map
     * 
     * @author Luc le Manifik
     */
    private void initAttributes() {

        // Default center position (Here, France's center)
        this.DefaultGeoPosition = new GeoPosition(47, 3);
        this.defaultZoom = 13; // Not 12 T_T

        // Waypoints stuff...
        this.itemPainter = new MapWaypointPainter(this.app);
        this.setOverlayPainter(this.itemPainter);

        // Default JxMapViewer settings
        TileFactoryInfo _tileFactoryInfo = new OSMTileFactoryInfo(); // The parameters of the tiles of the Map
        DefaultTileFactory _defaultTileFactory = new DefaultTileFactory(_tileFactoryInfo); //Setting the default tile creation to the tiles we just defined      
        this.setTileFactory(_defaultTileFactory); // setting the map's 'tile factory'
    }

    /**
     * Initiates the different events, for the mouse, the space bar, etc...
     * Allows you to  interract with the Map
     * 
     * @author Luc le Manifik
     */
    private void initEvents() {

        // Setting up the basic controls of the map (you touch you dead :skull:)
        PanMouseInputListener mouseListener = new PanMouseInputListener(this);
        this.addMouseListener(mouseListener); // The mouse is detected
        this.addMouseMotionListener(mouseListener); // The map can move, when we slide the mouse
        this.addMouseWheelListener(new ZoomMouseWheelListenerCursor(this)); // The map can be zoomed, when we use the wheel of the mouse

        // Adding the command
        // -> When "Space" is pressed : Default view of the France
        this.setFocusable(true); // Allow the map to listen a "KeyListener"
        this.addKeyListener(new CenteringMapWhenSpacePressed());
    }

    //#endregion

    //#region PUBLIC METHODS

        //#region CENTERING MAP

        /**
         * This method centers the Map at the 'center_geoposition' location, with the 'center_zoom' zoom value.
         * 
         * @see setCenterGeoPosition() - in {@link ihm.Map}
         * @see setCenterZoom() - in {@link ihm.Map}
         * 
         * @author Luc le Manifik
         */
        public void center() {
            this.setAddressLocation(DefaultGeoPosition);
            this.setZoom(defaultZoom);
        }

        //#endregion

        //#region WAYPOINTS

            //#region ADD AIRPORTS

            /**
             * This method adds the Airports' icons on the Map.
             * 
             * @param airportSet ({@link util.AirportSet}) - The AirportSet which contains all the Airports, their position, etc...
             * 
             * @author Luc le Manifik
             */
            public void addAirports(AirportSet airportSet) {

                addActiveAirports(airportSet);
                addInactiveAirports(airportSet);
            }

            /**
             * This method adds the ActiveAirports to the AirportSet
             * 
             * @param airportSet The {@link planeair.util.AirportSet AirportSet} which contains the Airports
             */
            private void addActiveAirports(AirportSet airportSet) {
                for(Airport airport : airportSet.getActiveAirports()) {
                    this.itemPainter.getActiveAirportWaypoints().add(new ActiveAirportWaypoint(airport, airport.getCoordinate()));
                }
            }

            /**
             * This method adds the InactiveAirports to the AirportSet
             * 
             * @param airportSet The {@link planeair.util.AirportSet AirportSet} which contains the Airports
             */
            private void addInactiveAirports(AirportSet airportSet) {
                for(Airport airport : airportSet.getInactiveAirports()) {
                    this.itemPainter.getInactiveAirportWaypoints().add(new InactiveAirportWaypoint(airport, airport.getCoordinate()));
                }
            }

            //#endregion

            //#region REMOVE AIRPORTS

            /**
             * This method removes and repaints the InactiveAirports from the {@link planeair.components.mapview.mapwp.MapWaypointPainter MapWaypointPainter}
             * 
             * @param airportSet ({@link util.AirportSet}) - The AirportSet which contains all the Airports, their position, etc...
             */
            public void removeAirports(AirportSet airportSet) {
                removeActiveAirports(airportSet);
                removeInactiveAirports(airportSet);
            }
            
            /**
             * This method removes the InactiveAirports from the {@link planeair.components.mapview.mapwp.MapWaypointPainter MapWaypointPainter}
             * 
             * @param airportSet ({@link util.AirportSet}) - The AirportSet which contains all the Airports, their position, etc...
             */
            private void removeActiveAirports(AirportSet airportSet) {
                for(Airport airport : airportSet.getActiveAirports()) {
                    this.itemPainter.getActiveAirportWaypoints().remove(airport.getWaypoint());
                }
            }

            /**
             * This method repaints the InactiveAirports from the {@link planeair.components.mapview.mapwp.MapWaypointPainter MapWaypointPainter}
             * 
             * @param airportSet ({@link util.AirportSet}) - The AirportSet which contains all the Airports, their position, etc...
             */
            private void removeInactiveAirports(AirportSet airportSet) {
                for(Airport airport : airportSet.getInactiveAirports()) {
                    this.itemPainter.getInactiveAirportWaypoints().remove(airport.getWaypoint());
                }
            }

            //#endregion

        //#region PAINT FLIGHTS

        /**
         * Paints all the Flights at the {@link planeair.util.NTime time} passed in parameter
         * 
         * @param time The time when the Flights are painted
         * @param fig The {@link planeair.graph.graphtype.FlightsIntersectionGraph FIG} from which are painted the Flights
         * 
         * @author Luc le Manifik
         */
        public void paintFlightsAtTime(NTime time, FlightsIntersectionGraph fig) {

            clearAllFlights();
            addAllFlightsAtTime(time, fig);
            
            this.repaint();
        }

        /**
         * Removes all the {@link planeair.graph.graphutil.Flight Flight} Flights from the {@link planeair.components.mapview.mapwp.MapWaypointPainter MapItemPainter}
         * before to repaint them on the Map, making the Fligths' movment
         * 
         * @author Luc le Manifik
         */
        private void clearAllFlights() {

            MapWaypointButton mwb = null;

            Iterator<FlightWaypoint> itWp = this.itemPainter.getFlightWaypoints().iterator();
            FlightWaypoint wp = null;

            while(itWp.hasNext()) { 
                wp = itWp.next();
                mwb = wp.getWaypointButton();
                this.remove(mwb);
                itWp.remove();
            }
        }

        /**
         * Adds all the Flights, after calculating their new position, and replace them on the Map
         * 
         * @param time The {@link planeair.util.NTime NTime} when we want to paint the Fligths
         * @param fig The {@link planeair.graph.graphtype.FlightsIntersectionGraph FIG} from which are painted the Flights
         * 
         * @author Luc le Manifik
         */
        public void addAllFlightsAtTime(NTime time, FlightsIntersectionGraph fig) {

            MapWaypointButton mwb = null;

            fig.forEach(node -> {
                Flight flight = (Flight)node;

                GeoPosition flightPositionAtTime = flight.getGeoPositionAtTime(time);
                // The function returns null is the Flight is not currently flying
                if(flightPositionAtTime != null) {
                    this.itemPainter.getFlightWaypoints().add(new FlightWaypoint(flight, flightPositionAtTime));
                }
            });

            for(FlightWaypoint waypoint : this.itemPainter.getFlightWaypoints()) {
                mwb = waypoint.getWaypointButton();
                this.add(mwb);
                this.itemPainter.getWpButtons().add(mwb);
            }
        }

        //#endregion

        /**
         * Clears all the MapWaypoints on the Map, by reseting the MapItemPainter
         * 
         * @author Luc le Manifik
         */
        public void clearAll() {
        
            for(MapWaypointButton mwb : this.itemPainter.getWpButtons()) {
                this.remove(mwb);
            }

            this.itemPainter.clearAll();

            this.repaint();
            this.revalidate();
        }

        //#endregion

    //#endregion

    //#region CENTERING MAP LISTENER

    /**
     * This class implements the KeyListener interface, 
     * and allows to center the view of the Map when the key 'Space' is pressed
     * 
     * @author Luc le Manifik
     */
    private class CenteringMapWhenSpacePressed implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                center(); // Centers the Map
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // Does nothing
        }

        @Override
        public void keyTyped(KeyEvent e) {
            // Does nothing
        }
    }

    //#endregion
}
