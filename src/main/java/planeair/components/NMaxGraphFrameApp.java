package planeair.components;

/**
 * Import swing composants
 */
import javax.swing.JPanel;

import planeair.graph.PanelCreator;

import javax.swing.JFrame;

import java.awt.Toolkit;

/**
 * Import awt composants
 */
import java.awt.Color;
import java.awt.Dimension;


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
    JPanel graph ;
    /**
     * NInfoGraphPanelApp created, have parameter of the graph
     * Location : In the left at the bottom of the graph
     */
    NInfoGraphPanelApp infoGraph = new NInfoGraphPanelApp();


    /**
     * Constructor of NMaxGraphPanelApp
     */
    NMaxGraphFrameApp(PanelCreator graphRenderer){
        if (graphRenderer == null) {
            graph = new JPanel() ;
        }
        else {
            graph = graphRenderer.getViewPanel() ;
        }
        graph.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize()) ;
        graph.setBackground(Color.GREEN);

        graph.add(infoGraph);
        this.add(graph);

        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);  
    }
    
}
