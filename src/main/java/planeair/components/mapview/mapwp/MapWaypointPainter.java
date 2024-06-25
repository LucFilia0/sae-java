package planeair.components.mapview.mapwp;

//#region IMPORTS
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.Math;

import java.awt.Rectangle;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.WaypointPainter;

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
    private Set<ActiveAirportWaypoint> activeAirportWaypointSet;

    /**
     * The Set which contains all the InactiveAirports' waypoints
     */
    private Set<InactiveAirportWaypoint> inactiveAirportWaypointSet;


    /**
     * The Set which contains all the FlightWaypoints
     */
    private Set<FlightWaypoint> flightWaypointSet;

    /**
     * The Set which stores all the MapWaypointButtons.
     * This Set is needed to remove all the MapWaypointButtons from the Map, when we want to repaint it, for example.
     */
    private Set<MapWaypointButton> waypointButtonSet;

    //#endregion

    //#region CONSTRUCTORS

    /**
     * Creates a new MapWaypointPainter.
     * 
     * @author Luc le Manifik
     */
    public MapWaypointPainter() {
        this.activeAirportWaypointSet = ConcurrentHashMap.newKeySet() ;
        this.inactiveAirportWaypointSet = ConcurrentHashMap.newKeySet() ;
        this.flightWaypointSet  = ConcurrentHashMap.newKeySet() ;
        this.waypointButtonSet  = ConcurrentHashMap.newKeySet() ;
    }

    //#endregion

    //#region GETTERS

    /**
     * Returns the Sets which contains all the ActiveAirports' Waypoints
     * 
     * @return ({@link java.util.Set}) - The sets which contains all the ActiveAirports' Waypoints
     * 
     * @author Luc le Manifik
     */
    public Set<ActiveAirportWaypoint> getActiveAirportWaypoints() {
        return this.activeAirportWaypointSet;
    }

    /**
     * Returns the Sets which contains all the InactiveAirports' Waypoints
     * 
     * @return ({@link java.util.Set}) - The sets which contains all the InactiveAirports' Waypoints
     * 
     * @author Luc le Manifik
     */
    public Set<InactiveAirportWaypoint> getInactiveAirportWaypoints() {
        return this.inactiveAirportWaypointSet;
    }

    /**
     * Returns the Set which contains all the FlightWaypoints
     * 
     * @return ({@link java.util.Set}) - The Sets which contains all the FlightWaypoints
     * 
     * @author Luc le Manifik
     */
    public Set<FlightWaypoint> getFlightWaypoints() {
        return this.flightWaypointSet;
    }

    /**
     * Returns the Set which contains all the MapWaypointButtons
     * 
     * @return ({@link java.util.Set}) - The Sets which contains all the MapWaypointButtons
     * 
     * @author Luc le Manifik
     */
    public Set<MapWaypointButton> getWpButtons() {
        return this.waypointButtonSet;
    }

    //#endregion

    //#region OVERRIDEN METHODS

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

        NMapMenuPanel mapMenu = App.app.getMainScreen().getMapMenuPanel();

        /*
         * STEP 1 : We paint the Airports' Waypoints
         */
        if(mapMenu.mustShowActiveAirports()) {
            paintActiveAirports(g, map, mapZoom, screen);
        }else {
            eraseActiveAirports();
        }

        if(mapMenu.mustShowInactiveAirports()) {
            paintInactiveAirports(g, map, mapZoom, screen);
        }else {
            eraseInactiveAirports();
        }

        /*
         * STEP 2 : We paint the current Flights' Waypoints, and their routes
         */
        for(FlightWaypoint flightWp : this.flightWaypointSet) {
            
            Integer drawnColor = App.app.getMainScreen()
                .getGraphMenuPanel().getLastColorSelected() ;
                
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
                    showWaypoint(waypointButton) ;
                }else {
                    map.remove(flightWp.getWaypointButton());
                    flightWp.getFlight().setFlightWaypoint(null) ;
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
                        g.setColor(graph.getColorMap().get(color - 1)) ;
                    }
                    g.setStroke(new BasicStroke(3)) ;
                    g.drawLine(depX, depY, arrX, arrY);
                }
            }else {
                map.remove(flightWp.getWaypointButton());
                flightWp.getFlight().setFlightWaypoint(null) ;
            }
        }
    }

    //#region ACTIVE AIRPORTS

    /**
     * Paints all the ActiveAirports
     * @param g idk
     * @param map Ze Map
     * @param mapZoom The zoom of Ze Map
     * @param screen the screen dimension
     * 
     * @author Luc le Manifik
     */
    private void paintActiveAirports(Graphics2D g, JXMapViewer map, int mapZoom, Rectangle screen) {

        double x, y;

        for(ActiveAirportWaypoint airportWp : this.activeAirportWaypointSet) {

            // waypointLocationOnScreen is the transcription of the waypoint's GeoPosition on screen pixels
            Point2D airportWp_location = map.getTileFactory().geoToPixel(airportWp.getPosition(), mapZoom);

            MapWaypointButton waypointButton = airportWp.getWaypointButton();

            // In order to center the button on the wished position
            x = airportWp_location.getX() - screen.getX() - waypointButton.getWidth()/2;
            y = airportWp_location.getY() - screen.getY() - waypointButton.getHeight();


            waypointButton.setBounds((int) Math.round(x), (int) Math.round(y), MapWaypointButton.BUTTON_SIZE, MapWaypointButton.BUTTON_SIZE);
            ((Map)map).add(waypointButton);
        }
    }

    /**
     * Erases all the ActiveAirports
     */
    private void eraseActiveAirports() {
        for(ActiveAirportWaypoint airportWaypoint : this.activeAirportWaypointSet) {
            airportWaypoint.getWaypointButton().setBounds(0, 0, 0, 0);
        }
    }

    //#endregion

    //#region INACTIVE AIRPORTS

    /**
     * Paint all the InactiveAirports
     * 
     * @param g idk
     * @param map Ze Map (again)
     * @param mapZoom The zoom of the Map
     * @param screen The screen dimensions
     * 
     * @author Luc le Manifik
     */
    private void paintInactiveAirports(Graphics2D g, JXMapViewer map, int mapZoom, Rectangle screen) {
        
        double x, y;

        for(InactiveAirportWaypoint airportWp : this.inactiveAirportWaypointSet) {

            // waypointLocationOnScreen is the transcription of the waypoint's GeoPosition on screen pixels
            Point2D airportWp_location = map.getTileFactory().geoToPixel(airportWp.getPosition(), mapZoom);

            MapWaypointButton waypointButton = airportWp.getWaypointButton();

            // In order to center the button on the wished position
            x = airportWp_location.getX() - screen.getX() - waypointButton.getWidth()/2;
            y = airportWp_location.getY() - screen.getY() - waypointButton.getHeight();
            
            waypointButton.setBounds((int) Math.round(x), (int) Math.round(y), MapWaypointButton.BUTTON_SIZE, MapWaypointButton.BUTTON_SIZE);
            ((Map)map).add(waypointButton);
            showWaypoint(waypointButton) ; 
        }
    }

    /**
     * Erases all the ActiveAirports
     */
    private void eraseInactiveAirports() {
        for(InactiveAirportWaypoint airportWaypoint : this.inactiveAirportWaypointSet) {
            airportWaypoint.getWaypointButton().setBounds(0, 0, 0, 0);
        }
    }

    //#endregion

    /**
     * This method is not supported for a MapWaypointPainter
     * 
     * @throws UnsupportedOperationException this method has been replaced by
     * more specific methods
     */
    @Override
    public HashSet<MapWaypoint> getWaypoints() throws UnsupportedOperationException {
        throw new UnsupportedOperationException() ;
    }

    /**
     * Empties all the waypoint sets
     */
    public void clearAll() {
        this.activeAirportWaypointSet.clear();
        this.inactiveAirportWaypointSet.clear();
        this.flightWaypointSet.clear();
        this.waypointButtonSet.clear();
    }

    /**
     * Shows the {@code Waypoint} if it is not intersecting with 
     * one of the {@code Menus}
     * 
     * @param waypointButton The waypoint whose position will be tested
     * 
     * @author Nathan LIEGEON
     */
    private void showWaypoint(MapWaypointButton waypointButton) {
        NMainScreen main = App.app.getMainScreen() ;

        boolean graphMenuIntersects = main.isGraphMenuVisible() 
            && main.getGraphMenuPanel().getBounds()
            .intersects(waypointButton.getBounds()) ;
        boolean mapMenuIntersects = main.isMapMenuVisible() 
            && main.getMapMenuPanel().getBounds()
            .intersects(waypointButton.getBounds()) ;
        
        // If the waypoint is over one of the menus, it doesn't get painted
        if (graphMenuIntersects || mapMenuIntersects)
            waypointButton.setVisible(false) ;   
        else
            waypointButton.setVisible(true) ; 
    }

    //#endregion
}
