package planeair.components;

/**
 * Import swing composants
 */
import javax.swing.JPanel;
import javax.swing.JFrame;

/**
 * Import awt composants
 */
import java.awt.Color;
import java.awt.Dimension;


/**
 * Create a Panel for MaxGraph
 */
public class NMaxGraphFrameApp extends JFrame{

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
     */
    NMaxGraphFrameApp(){

        graph.setPreferredSize(new Dimension(500,350));
        graph.setBackground(Color.GREEN);

        graph.add(infoGraph);
        this.add(graph);

        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);  
    }
    
}
