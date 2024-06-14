package planeair.components.graphview;

//#region IMPORT
    //#region .SWING
    import javax.swing.JPanel;
    import javax.swing.Box;
    import javax.swing.JButton;
    //#endregion

    //#region .GRAPHSTREAM
    import org.graphstream.ui.swing_viewer.ViewPanel;
    //#endregion

    //#region PLANEAIR
    import planeair.App;
    import planeair.graph.graphutil.PanelCreator;
    //#endregion

    //#region .AWT
    import java.awt.Color;
    import java.awt.Dimension;
    import java.awt.Font;
    //#endregion

    //#region LAYOUT

import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
//#endregion
//#endregion

/**
 * This class create a Panel for see the graph 
 * Their is also a button expand for see the graph in a other JFrame (it's bigger)
 * 
 * @author GIRAUD Nila
 */
public class NMinGraphPanel extends JPanel {

    //#region  INSTANTIALISATION AND INITIALISATION
        //#region STRUCTURE
        /**
         * GridLayout
         * nb LINE : 2  (Graph + ButtonAgr)
         * nb COLUMN : 1
         * hgap : 0
         * vgap : 30
         */
        private JPanel gridPanelMinGraph = new JPanel();
        //#endregion

        //#region FIRST COMPONENT 
        /**
         * Panel of the graph representation
         * FlowLayout CENTER
         */
        private JPanel FlowPanelGraph = new JPanel(new BorderLayout());

        /**
         * Having acces to homePage (setVisible elements change)
         * the panel NPrincipalePanelApp is put in this frame
         */
        private App app;
        //#endregion

        //#region SECOND COMPONENT 
        /**
         * Expand Button for graph
         * Open a new Frame with the graph and this information
         * Location : in the panel Mingraph --> need here for Events
         */
        private JButton buttonAgr = new JButton("AGRANDIR");

        private JPanel buttonCenter = new JPanel(new GridLayout(1,1));
        //#endregion
    //#endregion
    
    //#region CONSTRUCTOR
    /**
     * Constructor of NMinGraphPanelApp
     */
    public NMinGraphPanel(App app, JButton buttonAgr) {
        this.app = app ;
        this.buttonAgr = buttonAgr;

        this.setBackground(App.KINDAYELLOW);  
        //STRUCT
        
        //GridPanel Background Color (YELLOW)
        gridPanelMinGraph.setBackground(App.KINDAYELLOW);
        gridPanelMinGraph.setLayout(new BoxLayout(gridPanelMinGraph, BoxLayout.Y_AXIS));

        /*FIRST COMPONENT */
        
        this.setMaximumSize(new Dimension(350,400));
        if (app.getGraphRenderer() == null) {
            FlowPanelGraph = new NSkullPanel() ;
            FlowPanelGraph.setLayout(new BorderLayout());
        }
        FlowPanelGraph.setPreferredSize(new Dimension(325,325));
         
        /*SECOND COMPONENT */

        buttonAgr.setBackground(Color.BLACK);
        buttonAgr.setForeground(Color.WHITE);
        buttonAgr.setFont(new Font("Arial", Font.BOLD, 20));
        buttonAgr.setBorderPainted(false);

        buttonCenter.setBackground(App.KINDAYELLOW);


        /*FIRST COMPONENT */
        gridPanelMinGraph.add(FlowPanelGraph);

        /*SECOND COMPONENT */
        gridPanelMinGraph.add(buttonAgr);

        this.add(gridPanelMinGraph);
    }
    //#endregion

    //#region ADD GRAPH
    /**
     * Puts the graph in the panel
     * @param graphRenderer Renderer which will take care of rendering the graph
     * contains the panel containing the view of the graph
     */
    public void addGraphToPanel(PanelCreator graphRenderer) {
        ViewPanel panel = graphRenderer.getViewPanel() ;
        this.FlowPanelGraph.removeAll() ;
        this.FlowPanelGraph.add(panel, BorderLayout.CENTER) ;
        buttonCenter.setBackground(App.KINDAYELLOW);
    }
    //#endregion

    //#region REMOVE GRAPH
    /**
     * Clears the MinGraph from the panel
     * 
     * @author Luc le Manifik
     */
    public void removeGraphFromPanel() {
        this.FlowPanelGraph.removeAll();
        this.app.setGraph(null);
    }
    //#endregion

    //#region ADD
    public void addComponents(){

        /*FIRST COMPONENT */
        gridPanelMinGraph.add(Box.createRigidArea(new Dimension(10,5)));
        gridPanelMinGraph.add(FlowPanelGraph);
        gridPanelMinGraph.add(Box.createRigidArea(new Dimension(10,10)));

        /*SECOND COMPONENT */
        buttonCenter.add(buttonAgr);
        gridPanelMinGraph.add(buttonCenter);
        gridPanelMinGraph.add(Box.createRigidArea(new Dimension(10,10)));

        this.add(gridPanelMinGraph);
   }
   //#endregion

}
