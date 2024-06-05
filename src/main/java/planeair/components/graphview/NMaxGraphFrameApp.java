package planeair.components.graphview;

//-- Import Swing

import javax.swing.JPanel;
import javax.swing.JFrame;

//-- Import AWT

import java.awt.Color;
import java.awt.Toolkit;

//-- Import PlaneAIR

import planeair.components.menu.NInfoGraphPanelApp;
import planeair.graph.PanelCreator;

/**
 * Create a Panel for MaxGraph, it's a frame for seeing the graph bigger (with informations)
 * 
 * @author GIRAUD Nila
 */
public class NMaxGraphFrameApp extends JFrame{

    /**
     * Panel of the graph (Bigger than NInfoGraphPanelApp)
     * Location : all in the frame
     */
    private JPanel graph = new JPanel();

    /**
     * NInfoGraphPanelApp created, have parameter of the graph
     * Location : In the left at the bottom of the graph
     */
    private NInfoGraphPanelApp infoGraph = new NInfoGraphPanelApp();

    /**
     * Constructor of NMaxGraphPanelApp
     */
    public NMaxGraphFrameApp(PanelCreator graphRenderer) {
        
        if (graphRenderer == null) {
            graph = new JPanel() ;
        }
        else {
            graph = graphRenderer.getViewPanel() ;
        }
        graph.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize()) ;
        graph.setBackground(Color.GREEN);

        infoGraph.addComponents();
        graph.add(infoGraph);
        this.add(graph);

        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);  
    }
    
}
