package planeair.components;

// Import swing composants
import javax.swing.JPanel;
import javax.swing.JButton;

// Import awt composants
import java.awt.Color;
import java.awt.Font;


// Import Layout
import java.awt.GridLayout;
import java.awt.FlowLayout;


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
    JPanel FlowPanelGraph = new JPanel(new FlowLayout(FlowLayout.CENTER));

    /*SECOND COMPOSANT */

    
    /**
     * Constructor of NMinGraphPanelApp
     */
    NMinGraphPanelApp(JButton buttonAgr){

        this.setBackground(Color.YELLOW);

        //STRUCT
        
        //GridPanel Background Color (YELLOW)
        gridPanelMinGraph.setBackground(Color.YELLOW);

        /*FIRST COMPOSANT */
        
        //Insert Graph representation in FlowPanelGraph
         
        /*SECOND COMPOSANT */

        buttonAgr.setBackground(Color.BLACK);
        buttonAgr.setForeground(Color.WHITE);
        buttonAgr.setFont(new Font("Arial", Font.BOLD, 20));
        buttonAgr.setBorderPainted(false);


         /*FIRST COMPOSANT */
         gridPanelMinGraph.add(FlowPanelGraph);

         /*SECOND COMPOSANT */
         gridPanelMinGraph.add(buttonAgr);
 
         this.add(gridPanelMinGraph);
    }
    
}
