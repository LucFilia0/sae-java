package ihm;

//-- Import Java

import graph.Flight;

import util.Airport;
import util.AirportSet;

import java.util.HashSet;
import java.util.Set;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

//-- Import Swing

import javax.swing.*;

//-- Import JxMapViewer

import org.jxmapviewer.viewer.*;

import graph.FlightsIntersectionGraph;

import org.jxmapviewer.*;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;

//-- Import Exceptions

import java.io.IOException;


/**
 * This class is the Map which appears on the Application.
 * The Map in itself is a "JPanel", and it is needed to do ".getMap()" to get the real JxMapViewer object, and work with it.
 * 
 * @author Luc le Manifik
 */
public class Map extends JPanel {
    
    //-- Map Attributes

    /**
     * The JxMapViewer's map ({@link org.jxmapviewer.JXMapViewer}) which actually the REAL map.
     */
    private JXMapViewer map;

    /**
     * The GeoPosition which is focused when the key "Space" is pressed
     */
    private GeoPosition center_geoPosition;

    /**
     * The zoom when the key "Space" is pressed
     */
    private int center_zoom;

    /**
     * The Set which contains all the GeoPositions of the Airports.
     */
    private Set<Waypoint> airports_waypointSet;

    /**
     * The WaypointPainter which prompt the Airport's icon.
     */
    private WaypointPainter<Waypoint> airports_waypointPainter;

    /**
     * The Set which contains all the GeoPositions of the Flights
     */
    private Set<Waypoint> flights_waypointSet;

    /**
     * The WaypointPainter which prompt the Flight's icon.
     */
    private WaypointPainter<Waypoint> flights_waypointPainter;

    //-- Map Constructor

    public Map() {

        this.map = new JXMapViewer();
        
        // Values for the KeyEvent "Space" -> Centering the screen
        this.center_geoPosition = new GeoPosition(47, 3);
        this.center_zoom = 13;



        this.airports_waypointSet = new HashSet<Waypoint>();
        this.flights_waypointSet = new HashSet<Waypoint>();
        /* The WaypointPainter is set in the "setAirportsWaypointPainter()" function
         * because it needs the Icon's File (same for the Flights)
         */
        this.airports_waypointPainter = null;
        this.flights_waypointPainter = null;

        

        // The parameters of the tiles of the Map
        TileFactoryInfo _tileFactoryInfo = new OSMTileFactoryInfo();
        //Setting the default tile creation to the tiles we just defined
        DefaultTileFactory _defaultTileFactory = new DefaultTileFactory(_tileFactoryInfo);

        // setting the map's 'tile factory'
        this.map.setTileFactory(_defaultTileFactory);

        // Setting up the basic controls of the map (you touch you dead :skull:)
        PanMouseInputListener _mouseListener = new PanMouseInputListener(map);
        this.map.addMouseListener(_mouseListener); // The mouse is detected
        this.map.addMouseMotionListener(_mouseListener); // The map can move, when we slide the mouse
        this.map.addMouseWheelListener(new ZoomMouseWheelListenerCursor(map)); // The map can be zoomed, when we use the wheel of the mouse

        // Adding the JXMapViewer to the Map object
        this.setLayout(new BorderLayout());
        this.add(this.map, BorderLayout.CENTER); // In order to have the map filling the area...

        // Adding the command
        // -> When "Space" is pressed : Default view of the France
        this.map.setFocusable(true); // Allow the map to listen a "KeyListener"
        this.map.addKeyListener(new CenteringMapWhenSpacePressed());
    }

    //-- Map Getters

    /**
     * Get the JxMapViewer, the REAL map inside the Map object.
     * 
     * @return ({@link org.jxmapviewer.JXMapViewer})
     * 
     * @author Luc le Manifik
     */
    public JXMapViewer getMap() {
        return this.map;
    }

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
     * Set the visual of the WayointPainter put in parameter from the iconFile (png or else).
     * This function is here to set up the 'airports_waypointPainter' and the 'flights_waypointPainter' with the same function.
     * 
     * @param iconFile ({@link java.io.File}) - The icon File. The image that will be displayed by the WaypointPainter
     * @param waypointPainter ({@link org.jxmapviewer.viewer.WaypointPainter}) - The WaypointPainter that will display the Waypoints
     * 
     * @author Luc le Manifik
     */
    private void setWaypointPainterRenderer(File iconFile, WaypointPainter<Waypoint> waypointPainter) {
        waypointPainter.setRenderer(new WaypointRenderer<Waypoint>() {
            @Override
            public void paintWaypoint(Graphics2D g, JXMapViewer map, Waypoint w)
            {
                BufferedImage img = null;

                try {
                    img = ImageIO.read(iconFile); // The icon of the Waypoint
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
    }

    /**
     * Set the Airport's WaypointPainter, which will draw all the Airport's Waypoints.
     * 
     * @param airportIconFile ({@link java.io.File}) - The icon that will be used to draw the Airports on the Map
     * 
     * @author Luc le Manifik
     */
    public void setAirportsWaypointPainter(File airportIconFile) {
        this.airports_waypointPainter = new WaypointPainter<Waypoint>();

        // Setting up the visual of the Waypoints
        this.setWaypointPainterRenderer(airportIconFile, airports_waypointPainter);
    }

    /**
     * Set the Flight's WaypointPainter, which will draw all the Flight's Waypoints.
     * 
     * @param flightIconFile ({@link java.io.File}) - The icon that will be used to draw the Flights on the Map
     * 
     * @author Luc le Manifik
     */
    public void setFlightsWaypointPainter(File flightIconFile) {
        this.flights_waypointPainter = new WaypointPainter<Waypoint>();

        // Setting up the visual of the Waypoints
        this.setWaypointPainterRenderer(flightIconFile, flights_waypointPainter);
    }

    //-- Map Methods

    /**
     * This function centers the Map at the 'center_geoposition' location, with the 'center_zoom' zoom value.
     * 
     * @see setCenterGeoPosition() - in {@link ihm.Map}
     * @see setCenterZoom() - in {@link ihm.Map}
     * 
     * @author Luc le Manifik
     */
    public void center() {
        this.map.setAddressLocation(center_geoPosition);
        this.map.setZoom(center_zoom);
    }

    /**
     * This function displays the Airport's icons on the Map.
     * 
     * @param airportSet ({@link util.AirportSet}) - The AirportSet which contains all the Airports, their position, etc...
     * @param airportIconFile ({@link java.io.File}) - The Icon of the Airport's waypoint
     * 
     * @author Luc le Manifik
     */
    public void paintAirports(AirportSet airportSet, File airportIconFile) {

        this.setAirportsWaypointPainter(airportIconFile);

        Set<Airport> airports = airportSet.getAirportSet();

        for(Airport airport : airports) {

            // Add a new Waypoint which contains the actual airport's coordinates to "airports_waypointSet"
            this.airports_waypointSet.add(new Waypoint() {
                @Override
                public GeoPosition getPosition() {
                    return new GeoPosition(airport.getLatitude().getDecimalCoordinate(), airport.getLongitude().getDecimalCoordinate());
                }
            });
        }

        // Add all the Airport's Waypoints to the Airport's WaypointPainter
        this.airports_waypointPainter.setWaypoints(this.airports_waypointSet);
        // Set up the waypointPainter as the JxMapViewer's overlay  
        this.map.setOverlayPainter(airports_waypointPainter);
    }

    public void paintFlights(FlightsIntersectionGraph fig, File flightIconFile) {

        this.setFlightsWaypointPainter(flightIconFile);

        fig.forEach(node -> {
            Flight flight = (Flight)node;
            this.flights_waypointSet.add(new Waypoint() {
                @Override
                public GeoPosition getPosition() {
                    return flight.getCurrentGeoPosition();
                }
            });
        });

        // Add all the Flight's Waypoints to the Waypoint's WaypointPainter
        this.flights_waypointPainter.setWaypoints(this.flights_waypointSet);
        // Set up the JxMapViewer Overlay
        this.map.getOverlayPainter().
        this.map.setOverlayPainter(flights_waypointPainter);
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
