package planeair.components.graphview;

//-- Import Swing

import javax.swing.JPanel;
import javax.swing.JFrame;

//-- Import AWT

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

//-- Import PlaneAIR
import javax.swing.border.EmptyBorder;
import planeair.App;
import planeair.components.menu.NGraphInfoPanel;
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
public class NMaxGraphFrame extends JFrame{

    /**
     * Panel of the graph (Bigger than NInfoGraphPanelApp)
     * Location : all in the frame
     */
    private JPanel graph = new JPanel();

    /**
     * NInfoGraphPanelApp created, have parameter of the graph
     * Location : In the left at the bottom of the graph
     */
    private NGraphInfoPanel infoGraph ;

    /**
     * Homepage babababa no one cares
     */
    @SuppressWarnings("unused")
    private App app ;

    /**
     * Constructor of NMaxGraphPanelApp
     */
    public NMaxGraphFrame(App app, PanelCreator graphRenderer){
        this.app = app ;
        this.infoGraph = new NGraphInfoPanel(app) ;
        JPanel infoGraphPanel = new JPanel() ;
        if (graphRenderer == null) {
            // If no graph was imported
            graph = new NSkullPanel() ;
            this.setTitle("Vue sur rien du tout :(") ;
        }
        else {
            graph = graphRenderer.getViewPanel() ;
            graph.setLayout(new FlowLayout(FlowLayout.LEFT)) ;
            this.setTitle("Vue sur le Graph " + graphRenderer.getGraph().getId()) ; 

            int inset = 12 + 5; // 12 forever
            this.infoGraph.setBorder(new EmptyBorder(new Insets(inset, inset, inset, inset)));
            
            infoGraphPanel.add(this.infoGraph) ;
            infoGraphPanel.setBackground(graph.getBackground()) ;
            infoGraphPanel.setBorder(new EmptyBorder(new Insets(20, 20, 20, 20))) ;
            infoGraphPanel.setOpaque(false);

            graph.add(infoGraphPanel) ;
            infoGraph.setFontSize(18) ;
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(0,0,screenSize.width, screenSize.height);
        
        graph.setBackground(Color.BLACK);
        this.add(graph);
        this.setSize(new Dimension(1100,700));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);  
        // Handles properly closing the window by moving back all the panels it moved
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                removeAll() ;
                infoGraphPanel.removeAll() ;
                graph.removeAll() ;
                app.getMainScreen().getMinGraphPanel().getButtonAgr().setEnabled(true) ;
                if (graphRenderer != null) {
                    infoGraph.setFontSize(12);
                    app.getMainScreen().initGraphBottomPanel() ;
                    app.getMainScreen().getMinGraphPanel().addGraphToPanel(app.getGraphRenderer()) ;
                    app.repaint();
                    app.revalidate() ;
                }
            }
        });
    }
    
}
