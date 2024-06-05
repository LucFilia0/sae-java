package planeair.components;

// Import swing composants
import javax.swing.JPanel;

import planeair.App;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;

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
    // private JPanel gridPanelMinGraph = new JPanel(new GridLayout(2,1,0,30));
    private JPanel gridPanelMinGraph = new JPanel();


    /*FIRST COMPOSANT */

    /**
     * Panel of the graph representation
     * FlowLayout CENTER
     */
    private JPanel FlowPanelGraph = new JPanel(new FlowLayout(FlowLayout.CENTER));

    /*SECOND COMPOSANT */ 
    /**
     * Expand Button for graph
     * Open a new Frame with the graph and this information
     * Location : in the panel Mingraph --> need here for Events
     */
    private JButton buttonAgr = new JButton("AGRANDIR");

    private JPanel buttonCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));

    
    /**
     * Constructor of NMinGraphPanelApp
     */
    NMinGraphPanelApp(JButton buttonAgr){

        this.buttonAgr = buttonAgr;


        this.setBackground(App.KINDAYELLOW);



        //STRUCT
        
        //GridPanel Background Color (YELLOW)
        gridPanelMinGraph.setBackground(App.KINDAYELLOW);
        gridPanelMinGraph.setLayout(new BoxLayout(gridPanelMinGraph, BoxLayout.Y_AXIS));

        /*FIRST COMPOSANT */
        
        //Insert Graph representation in FlowPanelGraph pour Nathan liegounettt
        this.setMaximumSize(new Dimension(350,400));
        FlowPanelGraph.setPreferredSize(new Dimension(325,325));
         
        /*SECOND COMPOSANT */

        buttonAgr.setBackground(Color.BLACK);
        buttonAgr.setForeground(Color.WHITE);
        buttonAgr.setFont(new Font("Arial", Font.BOLD, 20));
        buttonAgr.setBorderPainted(false);

        buttonCenter.setBackground(App.KINDAYELLOW);
    }
    
    public void addComponents(){

        /*FIRST COMPOSANT */
        gridPanelMinGraph.add(Box.createRigidArea(new Dimension(10,5)));
        gridPanelMinGraph.add(FlowPanelGraph);
        gridPanelMinGraph.add(Box.createRigidArea(new Dimension(10,10)));

        /*SECOND COMPOSANT */
        buttonCenter.add(buttonAgr);
        gridPanelMinGraph.add(buttonCenter);
        gridPanelMinGraph.add(Box.createRigidArea(new Dimension(10,10)));

        this.add(gridPanelMinGraph);
   }

}
