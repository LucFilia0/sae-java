package ihm.mapvisuals;

//-- Import Java

import java.util.HashSet;
import java.util.Set;

//-- Import AWT

import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

//-- Import JxMapViewer

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.WaypointPainter;

//-- Import Plane AIR

import graph.Flight;

import ihm.mapvisuals.mapwp.MapWaypointButton;
import ihm.mapvisuals.mapwp.airportwp.AirportWaypoint;
import ihm.mapvisuals.mapwp.flightwp.FlightWaypoint;

/**
 * This class paints the different Waypoints on a Map object.
 * It paints Airports, which are putted a little above, to allow their pic to point towards the exact location of the Airports.
 * Flights, which have their image centered.
 * FlightRoutes, which are made by reading the FIG.
 * 
 * @author Luc le Manifik
 */
public class MapItemPainter extends WaypointPainter<MapItem> {
    
    /**
     * The Set which contains all the AirportWaypoints
     */
    private HashSet<AirportWaypoint> airportWaypointSet;

    /**
     * The Set which contains all the FlightWaypoints
     */
    private HashSet<FlightWaypoint> flightWaypointSet;

    /**
     * The set which contains all the Flights currently flying, in order to trace the FlightRoutes
     */
    private HashSet<Flight> currentFlightSet;

    /**
     * The MapItemPainter class's constructor. Creates a new MapItemPainter.
     * 
     * @author Luc le Manifik
     */
    public MapItemPainter() {
        this.airportWaypointSet = new HashSet<AirportWaypoint>();
        this.flightWaypointSet = new HashSet<FlightWaypoint>();
        this.currentFlightSet = new HashSet<Flight>();
    }

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

            waypointButton.setLocation(x.intValue(), y.intValue());
        }

        /*
         * STEP 2 : We paint the current Flights' Waypoints
         */
        for(FlightWaypoint flightWp : this.flightWaypointSet) {

            Point2D flightWp_location = map.getTileFactory().geoToPixel(flightWp.getPosition(), mapZoom);

            MapWaypointButton waypointButton = flightWp.getWaypointButton();

            x = flightWp_location.getX() - screen.getY() - waypointButton.getWidth()/2;
            y = flightWp_location.getY() - screen.getY() - waypointButton.getHeight()/2;

            waypointButton.setLocation(x.intValue(), y.intValue());
        }

        /*
         * STEP 3 : We paint the Flights' routes
         */
        for(Flight flight : this.currentFlightSet) {

            Point2D departureAirport_location = map.getTileFactory().geoToPixel(flight.getDepartureAirport().getGeoPosition(), map.getZoom());
            Point2D arrivalAirport_location = map.getTileFactory().geoToPixel(flight.getArrivalAirport().getGeoPosition(), map.getZoom());

            int depX = (int) (departureAirport_location.getX() - screen.getX());
            int depY = (int) (departureAirport_location.getY() - screen.getY());

            int arrX = (int) (arrivalAirport_location.getX() - screen.getX());
            int arrY = (int) (arrivalAirport_location.getY() - screen.getY());

            g.drawLine(depX, depY, arrX, arrY);
        }
    }

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
     * Returns the Set which contains all the Flights currently flying
     * 
     * @return ({@link java.util.HashSet}) - The Set which contains all the Flights currently flying
     * 
     * @author Luc le Manifik
     */
    public HashSet<Flight> getCurrentFlights() {
        return this.currentFlightSet;
    }

    /**
     * This overrided method does not allow to get the Waypoints of the WaypointPainter<MapItem>
     */
    @Override
    public Set<MapItem> getWaypoints() {
        return null;
    }
}