package ihm;

import java.awt.BorderLayout;

//-- Import Swing

import javax.swing.*;

//-- Import JxMapViewer

import org.jxmapviewer.viewer.*;
import org.jxmapviewer.*;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;

/**
 * This class is the Map which appears on the Application.
 * 
 * @author Luc le Manifik
 */
public class Map extends JPanel {
    
    //-- Map Attributes
    private JXMapViewer map;

    //-- Map Constructor

    public Map() {
        // The parameters of the tiles of the Map
        TileFactoryInfo _tileFactoryInfo = new OSMTileFactoryInfo();
        //Setting the default tile creation to the tiles we just defined
        DefaultTileFactory _defaultTileFactory = new DefaultTileFactory(_tileFactoryInfo);

        // Creating the map and setting its 'tile factory'
        this.map = new JXMapViewer();
        this.map.setTileFactory(_defaultTileFactory);

        // Setting up the basic controls of the map (you touch you dead :skull:)
        PanMouseInputListener _mouseListener = new PanMouseInputListener(map);
        this.map.addMouseListener(_mouseListener); // The mouse is detected
        this.map.addMouseMotionListener(_mouseListener); // The map can move, when we slide the mouse
        this.map.addMouseWheelListener(new ZoomMouseWheelListenerCursor(map)); // The map can be zoomed, when we use the wheel of the mouse

        // Adding the JXMapViewer to the Map object
        this.setLayout(new BorderLayout());
        this.add(this.map, BorderLayout.CENTER); // In order to have the map filling the area...
    }

    //-- Map Getters
    public JXMapViewer getMap() {
        return this.map;
    }

    //-- Map Setters
}
