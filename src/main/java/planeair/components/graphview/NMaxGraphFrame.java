package planeair.components.graphview;

//#region IMPORT
    //#region .SWING
    import javax.swing.JPanel;
    import javax.swing.JFrame;
    //#endregion

    //#region .AWT
    import java.awt.Color;
    import java.awt.Dimension;
    import java.awt.Toolkit;
    //#endregion

    //#region PLANEAIR
    import javax.swing.border.EmptyBorder;
    import planeair.App;
    import planeair.components.menu.infos.NGraphInfoPanel;
    import planeair.graph.graphutil.PanelCreator;
    //#endregion

    //#region .AWT
    import java.awt.event.WindowAdapter;
    import java.awt.event.WindowEvent;
    import java.awt.FlowLayout;
    import java.awt.Insets;
    //#endregion
//#endregion

/**
 * Create a Panel for MaxGraph, it's a frame for seeing the graph bigger (with informations)
 * 
 * @author GIRAUD Nila
 */
public class NMaxGraphFrame extends JFrame{

    //#region INSTANTIALISATION AND INITIALISATION
    /**
     * Panel of the graph (Bigger than NInfoGraphPanelApp)
     * Location : all in the frame
     */
    private JPanel graph = new JPanel();

    /**
     * NInfoGraphPanelApp created, have parameter of the graph
     * Location : In the left at the bottom of the graph
     */
    private NGraphInfoPanel graphInfo ;

    /**
     * Homepage babababa no one cares
     */
    @SuppressWarnings("unused")
    private App app ;
    //#endregion

    //#region CONSTRUCTOR
    /**
     * Constructor of NMaxGraphPanelApp
     */
    public NMaxGraphFrame(App app, PanelCreator graphRenderer){
        this.app = app ;
        this.graphInfo = new NGraphInfoPanel(app) ;
        JPanel graphInfoPanel = new JPanel() ;
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
            this.graphInfo.setBorder(new EmptyBorder(new Insets(inset, inset, inset, inset)));
            
            graphInfoPanel.add(this.graphInfo) ;
            graphInfo.computeGraphStats() ;
            graphInfoPanel.setBackground(graph.getBackground()) ;
            graphInfoPanel.setBorder(new EmptyBorder(new Insets(20, 20, 20, 20))) ;
            graphInfoPanel.setOpaque(false);

            graph.add(graphInfoPanel) ;
            graphInfo.setFontSize(18) ;
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
                graphInfoPanel.removeAll() ;
                graph.removeAll() ;
                app.getMainScreen().getMinGraphPanel().getButtonAgr().setEnabled(true) ;
                if (graphRenderer != null) {
                    graphInfo.setFontSize(12);
                    app.getMainScreen().initGraphBottomPanel() ;
                    app.getMainScreen().getMinGraphPanel().addGraphToPanel(app.getGraphRenderer()) ;
                    app.repaint();
                    app.revalidate() ;
                }
            }
        });
    }
    //#endregion
    
}
