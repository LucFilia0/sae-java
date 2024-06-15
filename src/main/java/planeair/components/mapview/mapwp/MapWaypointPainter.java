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
    import planeair.components.NMainScreen;
    import planeair.components.mapview.mapwp.airportwp.AirportWaypoint;
    import planeair.components.mapview.mapwp.flightwp.FlightWaypoint;
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
     * The Set which contains all the AirportWaypoints
     */
    private HashSet<AirportWaypoint> airportWaypointSet;

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

        this.airportWaypointSet = new HashSet<AirportWaypoint>();
        this.flightWaypointSet  = new HashSet<FlightWaypoint>();
        this.waypointButtonSet  = new HashSet<MapWaypointButton>();
    }

    //#endregion

    //#region GETTERS

    /**
     * Returns the Sets which contains all the AirportWaypoints
     * 
     * @return ({@link java.util.HashSet}) - The sets which contains all the AirportWaypoints
     * 
     * @author Luc le Manifik
     */
    public HashSet<AirportWaypoint> getAirportWaypoints() {
        return this.airportWaypointSet;
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
     * This method paints the Map's overlay, and diaplays all the different types of Waypoints on the Map.
     * There is 3 foreach loops, wandering the three Sets, and displaying each type of Waypoint accrding to it's own specs.
     * 
     * @author Luc le Manifik
     */
    @Override
    protected void doPaint(Graphics2D g, JXMapViewer map, int width, int height) {

        Rectangle screen = map.getViewportBounds();
        int mapZoom = map.getZoom();

        Double x = 0., y = 0.;

        /*
         * STEP 1 : We paint the Airports' Waypoints (Active AND Inactive) 
         */
        for(AirportWaypoint airportWp : this.airportWaypointSet) {

            // waypointLocationOnScreen is the transcription of the waypoint's GeoPosition on screen pixels
            Point2D airportWp_location = map.getTileFactory().geoToPixel(airportWp.getPosition(), mapZoom);

            MapWaypointButton waypointButton = airportWp.getWaypointButton();

            // In order to center the button on the whished position
            x = airportWp_location.getX() - screen.getX() - waypointButton.getWidth()/2;
            y = airportWp_location.getY() - screen.getY() - waypointButton.getHeight();
            NMainScreen main = app.getMainScreen() ;
            // Calculates whether the waypoint would be on top of one of the menus
            boolean graphMenuIntersects = main.isGraphMenuVisible() && main.getGraphMenuPanel().getBounds()
                .intersects(waypointButton.getBounds()) ;
            boolean mapMenuIntersects = main.isMapMenuVisible() && main.getMapMenuPanel().getBounds()
                .intersects(waypointButton.getBounds()) ;
            
            // If the waypoint is over once of the menus, don't paint it
            if (graphMenuIntersects || mapMenuIntersects) {
                    waypointButton.setVisible(false) ;
                    
            }
            else {
                waypointButton.setVisible(true) ;
            }

            waypointButton.setBounds((int) Math.round(x), (int) Math.round(y), MapWaypointButton.BUTTON_SIZE, MapWaypointButton.BUTTON_SIZE);
        }

        /*
         * STEP 2 : We paint the current Flights' Waypoints, and their routes
         */
        for(FlightWaypoint flightWp : this.flightWaypointSet) {

            /*
             * STEP 2.1 : Painting the Flights' waypoints
             */
            Point2D flightWp_location = map.getTileFactory().geoToPixel(flightWp.getPosition(), mapZoom);

            MapWaypointButton waypointButton = flightWp.getWaypointButton();

            x = flightWp_location.getX() - screen.getX() - MapWaypointButton.BUTTON_SIZE/2;
            y = flightWp_location.getY() - screen.getY() - MapWaypointButton.BUTTON_SIZE/2;
            
            waypointButton.setBounds((int) Math.round(x), (int) Math.round(y), MapWaypointButton.BUTTON_SIZE, MapWaypointButton.BUTTON_SIZE);

            /*
             * STEP 2.2 : Painting the Flights' routes
             */
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

            g.setStroke(new BasicStroke(3)) ; // Sets the stroke to 3, for better visibility on the Map
            g.drawLine(depX, depY, arrX, arrY);
        }
    }

    /**
     * This overrided method does not allow to get the Waypoints of the WaypointPainter<MapItem>
     */
    @Override
    public Set<MapWaypoint> getWaypoints() {
        return null;
    }

    //#endregion
}
