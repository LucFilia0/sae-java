package planeair.components.menu;

import java.awt.event.ActionListener;

import javax.swing.BoxLayout;

//-- Import AWT

//-- Import Swing

import javax.swing.JLabel;

import planeair.components.mapview.Map;
import planeair.components.mapview.mapwp.MapWaypoint;
import planeair.components.mapview.mapwp.MapWaypointButton;

/**
 * The JPannel that is prompted when a MapItem is pressed, showing all the required informations
 *
 * @author Luc le Manifik
 */
public class NInfoPanel extends javax.swing.JPanel {
    
    /**
     * The first line of the prompt
     */
    private JLabel label;

    /**
     * Creates a new NInfoPanel, which prompts the infos of the clicked elements
     *
     * @author Luc le Manifik
     */
    public NInfoPanel() {
        this.setOpaque(true);
        this.initComponents();
        this.placeComponents();
    }

    /**
     * Creates all the components of the NInfoPanel
     */
    private void initComponents() {
        this.label = new JLabel();
    }

    /**
     * Places all the components of the NInfoPanel
     */
    private void placeComponents() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(this.label);
    }

    /**
     * Makes the NInfoPanel visible, and prompts the infos of the MapItem that was clicked
     * 
     * @author Luc le Manifik
     */
    public void showInfos(MapWaypoint mapWaypoint) {
        this.label.setText(mapWaypoint.toString());

        this.setOpaque(false);
    }

    /**
     * Makes the NInfoPanel invisible, and clear all the written informations
     * 
     * @author Luc le Manifik
     */
    public void hideInfos() {
        this.setOpaque(true);
        this.label.setText("");
    }

    /**
     * Sets the NInfoPanel as the one of the App
     * 
     * @author Luc le Manifik
     */
    public void setPrincipal() {
	    Map.infoPanel = this;
    }
    
}
