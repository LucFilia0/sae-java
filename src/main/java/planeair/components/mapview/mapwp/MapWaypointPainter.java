package planeair.components.mapview.mapwp;

//#region IMPORTS

    //#region JAVA

    import java.util.HashSet;
    import java.util.Set;
    import java.lang.Math;

    //#endregion

    //#region AWT

    import java.awt.Rectangle;
    import java.awt.BasicStroke;
    import java.awt.Graphics2D;
    import java.awt.geom.Point2D;

    //#endregion

    //#region JXMAPVIEWER

    import org.jxmapviewer.JXMapViewer;
    import org.jxmapviewer.viewer.WaypointPainter;

    //#endregion

    //#region PLANEAIR

    import planeair.App;
    import planeair.components.mapview.Map;
    import planeair.components.NMainScreen;
    import planeair.components.mapview.mapwp.airportwp.ActiveAirportWaypoint;
    import planeair.components.mapview.mapwp.airportwp.InactiveAirportWaypoint;
    import planeair.components.mapview.mapwp.flightwp.FlightWaypoint;
    import planeair.components.menu.NMapMenuPanel;
    import planeair.graph.coloring.ColoringUtilities;
    import planeair.graph.graphtype.FlightsIntersectionGraph;

    //#endregion

//#endregion

/**
 * This class paints the different Waypoints on a Map object.
 * It paints : Airports, which are putted a little above, to allow their pic to point towards the exact location of the coordinate.
 * Flights, which have their image centered.
 * FlightRoutes, which are made by reading the FIG, and represents the routes of the Flights
 * 
 * @author Luc le Manifik
 */
public class MapWaypointPainter extends WaypointPainter<MapWaypoint> {
    
    //#region ATTRIBUTES
    
    /**
     * The Set which contains all the ActiveAirports waypoints
     */
    private HashSet<ActiveAirportWaypoint> activeAirportWaypointSet;

    /**
     * The Set which contains all the InactiveAirports' waypoints
     */
    private HashSet<InactiveAirportWaypoint> inactiveAirportWaypointSet;


    /**
     * The Set which contains all the FlightWaypoints
     */
    private HashSet<FlightWaypoint> flightWaypointSet;

    /**
     * The Set which stores all the MapWaypointButtons.
     * This Set is needed to remove all the MapWaypointButtons from the Map, when we want to repaint it, for example.
     */
    private HashSet<MapWaypointButton> waypointButtonSet;

    /**
     * Homepage blablabla
     */
    private App app ;

    //#endregion

    //#region CONSTRUCTORS

    /**
     * Creates a new MapWaypointPainter.
     * 
     * @author Luc le Manifik
     */
    public MapWaypointPainter(App app) {

        this.app = app ;

        this.activeAirportWaypointSet = new HashSet<ActiveAirportWaypoint>();
        this.inactiveAirportWaypointSet = new HashSet<InactiveAirportWaypoint>();
        this.flightWaypointSet  = new HashSet<FlightWaypoint>();
        this.waypointButtonSet  = new HashSet<MapWaypointButton>();
    }

    //#endregion

    //#region GETTERS

    /**
     * Returns the Sets which contains all the ActiveAirports' Waypoints
     * 
     * @return ({@link java.util.HashSet}) - The sets which contains all the ActiveAirports' Waypoints
     * 
     * @author Luc le Manifik
     */
    public HashSet<ActiveAirportWaypoint> getActiveAirportWaypoints() {
        return this.activeAirportWaypointSet;
    }

    /**
     * Returns the Sets which contains all the InactiveAirports' Waypoints
     * 
     * @return ({@link java.util.HashSet}) - The sets which contains all the InactiveAirports' Waypoints
     * 
     * @author Luc le Manifik
     */
    public HashSet<InactiveAirportWaypoint> getInactiveAirportWaypoints() {
        return this.inactiveAirportWaypointSet;
    }

    /**
     * Returns the Set which contains all the FlightWaypoints
     * 
     * @return ({@link java.util.HashSet}) - The Sets which contains all the FlightWaypoints
     * 
     * @author Luc le Manifik
     */
    public HashSet<FlightWaypoint> getFlightWaypoints() {
        return this.flightWaypointSet;
    }

    /**
     * Returns the Set which contains all the MapWaypointButtons
     * 
     * @return ({@link java.util.HashSet HashSet}) - The Sets which contains all the MapWaypointButtons
     * 
     * @author Luc le Manifik
     */
    public HashSet<MapWaypointButton> getWpButtons() {
        return this.waypointButtonSet;
    }

    //#endregion

    //#region OVERRIDED METHODS

    /**
     * This method paints the Map's overlay, and displays all the different types of Waypoints on the Map.
     * There are 3 foreach loops, wandering the three Sets, and displaying each type of Waypoint according to its own specs.
     * 
     * @author Luc le Manifik
     */
    @Override
    protected void doPaint(Graphics2D g, JXMapViewer map, int width, int height) {

        Rectangle screen = map.getViewportBounds();
        int mapZoom = map.getZoom();

        Double x = 0., y = 0.;

        NMapMenuPanel mapMenu = this.app.getMainScreen().getMapMenuPanel();

        /*
         * STEP 1 : We paint the Airports' Waypoints
         */

        if(mapMenu.mustShowActiveAirports()) {
            paintActiveAirports(g, map, width, height, mapZoom, screen);
        }

        if(mapMenu.mustShowInactiveAirports()) {
            paintInactiveAirports(g, map, width, height, mapZoom, screen);
        }

        /*
         * STEP 2 : We paint the current Flights' Waypoints, and their routes
         */
        for(FlightWaypoint flightWp : this.flightWaypointSet) {
            
            Integer drawnColor = app.getMainScreen().getGraphMenuPanel().getLastColorSelected() ;
                
            if (drawnColor == 0 || flightWp.getFlight().getAttribute(
                    ColoringUtilities.NODE_COLOR_ATTRIBUTE) == drawnColor) {
                
                // Painting the Flight Icons
                if(mapMenu.mustShowFlights()) {

                    Point2D flightWp_location = map.getTileFactory().geoToPixel(flightWp.getPosition(), mapZoom);
                    flightWp.getFlight().setFlightWaypoint(flightWp) ;
        
                    MapWaypointButton waypointButton = flightWp.getWaypointButton();
        
                    x = flightWp_location.getX() - screen.getX() - MapWaypointButton.BUTTON_SIZE/2;
                    y = flightWp_location.getY() - screen.getY() - MapWaypointButton.BUTTON_SIZE/2;
                    
                    waypointButton.setBounds((int) Math.round(x), (int) Math.round(y), MapWaypointButton.BUTTON_SIZE, MapWaypointButton.BUTTON_SIZE);        
                }

                // Painting the Flight lines
                if(mapMenu.mustShowFlightLines()) {

                    Point2D departureAirport_location = map.getTileFactory().geoToPixel(flightWp.getFlight().getDepartureAirport().getCoordinate(), map.getZoom());
                    Point2D arrivalAirport_location = map.getTileFactory().geoToPixel(flightWp.getFlight().getArrivalAirport().getCoordinate(), map.getZoom());
        
                    int depX = (int) (departureAirport_location.getX() - screen.getX());
                    int depY = (int) (departureAirport_location.getY() - screen.getY());
        
                    int arrX = (int) (arrivalAirport_location.getX() - screen.getX());
                    int arrY = (int) (arrivalAirport_location.getY() - screen.getY());
        
                    int color = (int)flightWp.getFlight().getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE) ;
                    if (color != 0) {
                        FlightsIntersectionGraph graph = (FlightsIntersectionGraph)flightWp.getFlight().getGraph() ;
                        g.setColor(graph.getColorTab()[color - 1]) ;
                    }
                    g.setStroke(new BasicStroke(3)) ;
                    g.drawLine(depX, depY, arrX, arrY);
                }
            }
            else {
                flightWp.getFlight().setFlightWaypoint(null) ;
            }
        }
    }

    /**
     * Paints all the ActiveAirports
     * @param g
     * @param map
     * @param width
     * @param height
     * @param mapZoom
     * @param screen
     */
    private void paintActiveAirports(Graphics2D g, JXMapViewer map, int width, int height, int mapZoom, Rectangle screen) {

        double x, y;

        for(ActiveAirportWaypoint airportWp : this.activeAirportWaypointSet) {

            // waypointLocationOnScreen is the transcription of the waypoint's GeoPosition on screen pixels
            Point2D airportWp_location = map.getTileFactory().geoToPixel(airportWp.getPosition(), mapZoom);

            MapWaypointButton waypointButton = airportWp.getWaypointButton();

            // In order to center the button on the wished position
            x = airportWp_location.getX() - screen.getX() - waypointButton.getWidth()/2;
            y = airportWp_location.getY() - screen.getY() - waypointButton.getHeight();
            NMainScreen main = app.getMainScreen() ;
            // Calculates whether the waypoint would be on top of one of the menus
            boolean graphMenuIntersects = main.isGraphMenuVisible() && main.getGraphMenuPanel().getBounds()
                .intersects(waypointButton.getBounds()) ;
            boolean mapMenuIntersects = main.isMapMenuVisible() && main.getMapMenuPanel().getBounds()
                .intersects(waypointButton.getBounds()) ;
            
            // If the waypoint is over one of the menus, don't paint it
            if (graphMenuIntersects || mapMenuIntersects) {
                waypointButton.setVisible(false) ;   
            }
            else {
                waypointButton.setVisible(true) ;
            }

            waypointButton.setBounds((int) Math.round(x), (int) Math.round(y), MapWaypointButton.BUTTON_SIZE, MapWaypointButton.BUTTON_SIZE);
        }

        ((Map)map).paintActiveAirports();
    }

    /**
     * Paint all the InactiveAirports
     * @param g
     * @param map
     * @param width
     * @param height
     * @param mapZoom
     * @param screen
     */
    private void paintInactiveAirports(Graphics2D g, JXMapViewer map, int width, int height, int mapZoom, Rectangle screen) {
        
        double x, y;

        for(InactiveAirportWaypoint airportWp : this.inactiveAirportWaypointSet) {

            // waypointLocationOnScreen is the transcription of the waypoint's GeoPosition on screen pixels
            Point2D airportWp_location = map.getTileFactory().geoToPixel(airportWp.getPosition(), mapZoom);

            MapWaypointButton waypointButton = airportWp.getWaypointButton();

            // In order to center the button on the wished position
            x = airportWp_location.getX() - screen.getX() - waypointButton.getWidth()/2;
            y = airportWp_location.getY() - screen.getY() - waypointButton.getHeight();
            NMainScreen main = app.getMainScreen() ;
            // Calculates whether the waypoint would be on top of one of the menus
            boolean graphMenuIntersects = main.isGraphMenuVisible() && main.getGraphMenuPanel().getBounds()
                .intersects(waypointButton.getBounds()) ;
            boolean mapMenuIntersects = main.isMapMenuVisible() && main.getMapMenuPanel().getBounds()
                .intersects(waypointButton.getBounds()) ;
            
            // If the waypoint is over one of the menus, don't paint it
            if (graphMenuIntersects || mapMenuIntersects) {
                waypointButton.setVisible(false) ;   
            }
            else {
                waypointButton.setVisible(true) ;
            }

            waypointButton.setBounds((int) Math.round(x), (int) Math.round(y), MapWaypointButton.BUTTON_SIZE, MapWaypointButton.BUTTON_SIZE);
        }

        ((Map)map).paintInactiveAirports();
    }

    /**
     * This overrided method does not allow to get the Waypoints of the WaypointPainter<MapItem>
     */
    @Override
    public Set<MapWaypoint> getWaypoints() {
        return null;
    }

    public void clearAll() {
        this.activeAirportWaypointSet.clear();
        this.inactiveAirportWaypointSet.clear();
        this.flightWaypointSet.clear();
        this.waypointButtonSet.clear();
    }

    //#endregion
}
