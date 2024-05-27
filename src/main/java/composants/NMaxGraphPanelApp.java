package composants;

/**
 * Import swing composants
 */
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import java.awt.BorderLayout;
/**
 * Import awt composants
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;


/**
 * Import Layout
 */
import java.awt.GridLayout;
import java.awt.FlowLayout;


/**
 * Create a Panel for MaxGraph
 */
public class NMaxGraphPanelApp extends JFrame{

    //LEFT
    
    /**
     * Create an Empty Panel for forcing the ButtonZoom to go in bottom
     */
    JPanel empty2 = new JPanel();

    /**
     * Contain the panel of Zoom
     * Add Border
     */
    JPanel spaceBorderZoomPanel = new JPanel();

    /**
     * Contain an Empty Panel and spaceBorderZoomPanel
     * Forcing bottom
     */
    JPanel zoomButtonLeftBottom = new JPanel();

     /**
     * Create an Empty Panel for forcing the Buttonimport to go in bottom
     * When there is no MinGraph + InfoGraph
     */
    JPanel empty = new JPanel();

    /**
     * Panel of the graph (Bigger than NInfoGraphPanelApp)
     */
    JPanel graph = new JPanel();
    /**
     * NInfoGraphPanelApp created, have parameter of the graph
     */
    NInfoGraphPanelApp infoGraph = new NInfoGraphPanelApp();


    /**
     * Constructor of NMaxGraphPanelApp
     * @param reduireButtonGraph JButton for reduce that Panel
     */
    NMaxGraphPanelApp(){

        graph.setPreferredSize(new Dimension(500,350));
        graph.setBackground(Color.GREEN);

        // // reduireButtonGraph.setBackground(Color.BLACK);
        // // reduireButtonGraph.setForeground(Color.WHITE);
        // // reduireButtonGraph.setFont(new Font("Arial", Font.BOLD, 20));
        // // reduireButtonGraph.setBorderPainted(false);    
        graph.add(infoGraph);
        this.add(graph);

        

        // this.add(Box.createRigidArea(new Dimension(0, 10)));

        
        // infoGraph.setAlignmentX(Component.CENTER_ALIGNMENT);

        // this.add(Box.createRigidArea(new Dimension(0, 10)));

        // // this.add(reduireButtonGraph);
        // // reduireButtonGraph.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
    }
    
}
