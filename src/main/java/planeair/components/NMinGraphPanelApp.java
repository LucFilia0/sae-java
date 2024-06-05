package planeair.components;

// Import swing composants
import javax.swing.JPanel;

import planeair.App;
import planeair.graph.Coloration;
import planeair.graph.PanelCreator;

import javax.swing.JButton;

// Import awt composants
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;


// Import Layout
import java.awt.GridLayout;
import java.awt.FlowLayout;

/**
 * This class create a Panel for see the graph 
 * Their is also a button expand for see the graph in a other JFrame (it's bigger)
 * 
 * @author GIRAUD Nila
 */
public class NMinGraphPanelApp extends JPanel {

    //STRUCT
    /**
     * GridLayout
     * nb LINE : 2  (Graph + ButtonAgr)
     * nb COLUMN : 1
     * hgap : 0
     * vgap : 30
     */
    JPanel gridPanelMinGraph = new JPanel(new GridLayout(2,1,0,30));


    /*FIRST COMPOSANT */

    /**
     * Panel of the graph representation
     * FlowLayout CENTER
     */
    JPanel FlowPanelGraph ;

    /**
     * Having acces to homePage (setVisible elements change)
     * the panel NPrincipalePanelApp is put in this frame
     */
    App app;

    /*SECOND COMPOSANT */

    
    /**
     * Constructor of NMinGraphPanelApp
     */
    NMinGraphPanelApp(App app, JButton buttonAgr) {
        this.app = app ;
        this.setBackground(Color.YELLOW);

        //STRUCT
        
        //GridPanel Background Color (YELLOW)
        gridPanelMinGraph.setBackground(Color.YELLOW);

        /*FIRST COMPOSANT */
        
        //Insert Graph representation in FlowPanelGraph pour Nathan liegounettt
         
        /*SECOND COMPOSANT */

        buttonAgr.setBackground(Color.BLACK);
        buttonAgr.setForeground(Color.WHITE);
        buttonAgr.setFont(new Font("Arial", Font.BOLD, 20));
        buttonAgr.setBorderPainted(false);


        /*FIRST COMPOSANT */
        FlowPanelGraph = new JPanel() ;
        FlowPanelGraph.setLayout(new FlowLayout(FlowLayout.CENTER)) ;
        gridPanelMinGraph.add(FlowPanelGraph);

        /*SECOND COMPOSANT */
        gridPanelMinGraph.add(buttonAgr);

        this.add(gridPanelMinGraph);
    }

    public void addGraphToPanel(PanelCreator graphRenderer) {
        this.FlowPanelGraph = graphRenderer.getViewPanel() ;  
    }
    
}
