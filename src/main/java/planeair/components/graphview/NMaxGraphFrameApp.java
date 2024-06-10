package planeair.components.graphview;

//-- Import Swing

import javax.swing.JPanel;
import javax.swing.JFrame;

//-- Import AWT

import java.awt.Color;
import java.awt.Toolkit;

//-- Import PlaneAIR
import javax.swing.border.EmptyBorder;
import planeair.App;
import planeair.components.menu.NInfoGraphPanelApp;
import planeair.graph.graphutil.PanelCreator;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/**
 * Import awt components
 */
import java.awt.FlowLayout;
import java.awt.Insets;


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
        JPanel infoGraphPanel = new JPanel() ;
        if (graphRenderer == null) {
            graph = new NSkullPanel() ;
            this.setTitle("Vue sur rien du tout :(") ;
        }
        else {
            graph = graphRenderer.getViewPanel() ;
            graph.setLayout(new FlowLayout(FlowLayout.LEFT)) ;
            this.setTitle("Vue sur le Graph " + graphRenderer.getGraph().getId()) ;
            
            infoGraphPanel.add(this.infoGraph) ;
            infoGraphPanel.setBackground(graph.getBackground()) ;
            infoGraphPanel.setBorder(new EmptyBorder(new Insets(20, 20, 20, 20))) ;
            infoGraphPanel.setBackground(Color.WHITE) ;

            graph.add(infoGraphPanel) ;
            infoGraph.setFontSize(18) ;
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
                infoGraphPanel.removeAll() ;
                graph.removeAll() ;
                if (graphRenderer != null) {
                    infoGraph.setFontSize(12);
                    app.getMainScreen().initGraphBottomPanel() ;
                    app.initTestGraphRenderer() ;
                    app.getMainScreen().getMinGraphPanel().addGraphToPanel(app.getTestGraphRenderer()) ;
                    app.repaint();
                    app.revalidate() ;
                }
            }
        });
    }
    
}
