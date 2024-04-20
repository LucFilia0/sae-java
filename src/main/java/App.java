//-- Import Swing

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

//-- Import JxMapViewer

import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

import org.jxmapviewer.JXMapViewer;

public class App {
    public static void main(String[] args) {

        // Frame
        JFrame frame = new JFrame("Test Map");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 720);
        frame.setLocationRelativeTo(null);

        // BorderLayout
        JPanel _borderLayout = new JPanel();
        _borderLayout.setLayout(new BorderLayout());

        // Title
        JLabel _label = new JLabel("Plane Air", SwingConstants.CENTER);
        _label.setFont(new Font("Arial", Font.BOLD, 20));

        _borderLayout.add(_label, BorderLayout.NORTH);

        // Left Menu
        JPanel _container_leftMenu = new JPanel();
        _container_leftMenu.setLayout(new FlowLayout(FlowLayout.CENTER));
        _container_leftMenu.setPreferredSize(new Dimension(250, 0));

        _borderLayout.add(_container_leftMenu, BorderLayout.WEST);

        JPanel _leftMenu = new JPanel();
        _leftMenu.setLayout(new BoxLayout(_leftMenu, BoxLayout.Y_AXIS));

        _container_leftMenu.add(_leftMenu);

        // -- Left Menu -> Title
        JPanel _container_LeftMenu_container_leftMenuTitle = new JPanel();
        _container_LeftMenu_container_leftMenuTitle.setLayout(new FlowLayout());

        JLabel _leftMenuTitle = new JLabel("Left Menu Youhou!");
        _container_LeftMenu_container_leftMenuTitle.add(_leftMenuTitle);
        //_container_LeftMenu_container_leftMenuTitle


        _leftMenu.add(_container_LeftMenu_container_leftMenuTitle);

        // -- Left Menu -> FlowLayout

        /* JPanel _leftMenu_flowLayout1 = new JPanel();
        JPanel _leftMenu_flowLayout2 = new JPanel();
        JPanel _leftMenu_flowLayout3 = new JPanel();

        _leftMenu_flowLayout1.setLayout(new FlowLayout());
        _leftMenu_flowLayout2.setLayout(new FlowLayout());
        _leftMenu_flowLayout3.setLayout(new FlowLayout());

        _leftMenu.add(_leftMenu_flowLayout1);
        _leftMenu.add(_leftMenu_flowLayout2);
        _leftMenu.add(_leftMenu_flowLayout3); */

        // -- Left Menu -> Button
        JButton _leftMenu_button1 = new JButton("Button 1");
        JButton _leftMenu_button2 = new JButton("Button 2");
        JButton _leftMenu_button3 = new JButton("Button 3");

        /* _leftMenu_flowLayout1.add(_leftMenu_button1);
        _leftMenu_flowLayout2.add(_leftMenu_button2);
        _leftMenu_flowLayout3.add(_leftMenu_button3); */

        _leftMenu.add(_leftMenu_button1);
        _leftMenu.add(_leftMenu_button2);
        _leftMenu.add(_leftMenu_button3);
        

        // Map
        TileFactoryInfo _tileFactoryInfo = new OSMTileFactoryInfo(); // The informations on the tiles
        DefaultTileFactory _defaultTileFactory = new DefaultTileFactory(_tileFactoryInfo); // The tile maker
        JXMapViewer _JxMapViewer = new JXMapViewer();
        _JxMapViewer.setTileFactory(_defaultTileFactory); // Set the tile maker, to make tiles based on the _tileFactoryInfo

        GeoPosition _position = new GeoPosition(45.7592157,4.896261);
        _JxMapViewer.setCenterPosition(_position);

        // _JxMapViewer.setBackground(Color.RED);
        // _JxMapViewer.setDrawTileBorders(true);
        // _JxMapViewer.setForeground(Color.BLUE);
        // _JxMapViewer.setLoadingImage();

        _JxMapViewer.setZoom(5);

        MouseInputAdapter mm = new PanMouseInputListener(_JxMapViewer);
        _JxMapViewer.addMouseListener(mm);
        _JxMapViewer.addMouseMotionListener(mm);
        _JxMapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(_JxMapViewer));

        
        _borderLayout.add(_JxMapViewer, BorderLayout.CENTER);

        frame.setContentPane(_borderLayout);
        frame.setVisible(true);
    }
}