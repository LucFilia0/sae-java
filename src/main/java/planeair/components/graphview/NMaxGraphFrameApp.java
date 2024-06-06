package planeair.components.graphview;

/**
 * Import swing composants
 */
import javax.swing.JPanel;

import planeair.App;
import planeair.components.menu.NInfoGraphPanelApp;
import planeair.graph.PanelCreator;

import javax.swing.JFrame;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/**
 * Import awt composants
 */
import java.awt.Color;


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
    private NInfoGraphPanelApp infoGraph ;

    private App app ;


    /**
     * Constructor of NMaxGraphPanelApp
     */
    public NMaxGraphFrameApp(App app, PanelCreator graphRenderer, NInfoGraphPanelApp infoGraph){
        this.app = app ;
        this.infoGraph = infoGraph ;
        if (graphRenderer == null) {
            graph = new NSkullPanel() ;
            this.setTitle("Vue sur rien du tout :(") ;
        }
        else {
            graph = graphRenderer.getViewPanel() ;
            this.setTitle("Vue sur le Graph " + graphRenderer.getGraph().getId()) ;
            graph.add(this.infoGraph);
        }

        graph.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize()) ;
        graph.setBackground(Color.BLACK);
        this.add(graph);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);  
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                removeAll() ;
                if (graphRenderer != null) {
                    app.getPrincFrame().initGraphBottomPanel() ;
                    app.initTestGraphRenderer() ;
                    app.getPrincFrame().getMinGraphPanel().addGraphToPanel(app.getTestGraphRenderer()) ;
                    app.repaint();
                    app.revalidate() ;
                }
            }
        });
    }
    
}
