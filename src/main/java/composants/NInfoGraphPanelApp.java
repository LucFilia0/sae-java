package composants;

// Import swing composants
import javax.swing.JPanel;
import javax.swing.JLabel;

// Import awt composants
import java.awt.Color;
import java.awt.Dimension;
// Import Layout
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

/**
 * Class which create a panel of INFO GRAPH
 */
public class NInfoGraphPanelApp extends JPanel{

    //Define attribut

    /**
     * Layout's Panel for INFO
     * Grid Layout
     * nb LINE : 5
     * nb COLUMN : 1 (Name Info + Info)
     */
    JPanel infoGraphGridLayout = new JPanel(new GridLayout(5, 1));

    // LINE 1

    /**
     * Add a padding in left of titleDegreeAvg
     */
    JPanel degreePanel = new JPanel();
    /**
     * Title of the first LINE
     * Degree of the graph
     */
    JLabel titleDegreeAvg = new JLabel("Degré moyen: " + "-");
    /**
     * Degree in the graph
     */
    // JLabel degreeAvg = new JLabel("test ");

    // LINE 2

    /**
     * Add a padding in left of titleComp
     */
    JPanel compPanel = new JPanel();
    /**
     * Title of the second LINE
     * Nb related composants
     */
    JLabel titleComp = new JLabel("Nb comp. connexes: " + "-");
    /**
     * Degree in the graph
     */
    // JLabel relatedComp = new JLabel("test ");

    // LINE 3

    /**
     * Add a padding in left of titleNodes
     */
    JPanel nodesPanel = new JPanel();
    /**
     * Title of the third LINE
     * NbNodes
     */
    JLabel titleNodes = new JLabel("Nb noeuds: " + "-");
    /**
     * Degree in the graph
     */
    // JLabel nbNodes = new JLabel("test ");

    // LINE 4

    /**
     * Add a padding in left of titleEdges
     */
    JPanel edgesPanel = new JPanel();
    /**
     * Title of the LINE four
     *  Nb edges of the graph
     */
    JLabel titleEdges = new JLabel("Nb arretes: " + "-");
    /**
     * Degree in the graph
     */
    // JLabel nbEdges = new JLabel("test ");

    // LINE 5

    /**
     * Add a padding in left of titleDiameter
     */
    JPanel diameterPanel = new JPanel();
    /**
     * Title of the LINE five
     * Diameter of the graph
     */
    JLabel titleDiameter = new JLabel("Diametre: " + "-");
    /**
     * Degree in the graph
     */
    // JLabel diameter = new JLabel("test ");

    /**
     * Empty Panel, the padding
     */
    JPanel padding = new JPanel();

    /**
     * Contructor of NInfoGraphPanelApp
     */
    public NInfoGraphPanelApp(){

        this.setBackground(Color.YELLOW);

        this.setLayout(new BorderLayout());

        //this.add(new JButton());

        degreePanel.setBackground(Color.YELLOW);
        compPanel.setBackground(Color.YELLOW);
        nodesPanel.setBackground(Color.YELLOW);
        edgesPanel.setBackground(Color.YELLOW);
        diameterPanel.setBackground(Color.YELLOW);

        //LINE 1
        // degreePanel.add(padding);
        degreePanel.add(titleDegreeAvg);
        infoGraphGridLayout.add(degreePanel);
        // infoGraphGridLayout.add(degreeAvg);

        //Line 2
        // compPanel.add(padding);
        compPanel.add(titleComp);
        infoGraphGridLayout.add(compPanel);
        // infoGraphGridLayout.add(relatedComp);

        //Line 3
        // nodesPanel.add(padding);
        nodesPanel.add(titleNodes);
        infoGraphGridLayout.add(nodesPanel);
        // infoGraphGridLayout.add(nbNodes);

        //Line 4
        // edgesPanel.add(padding);
        edgesPanel.add(titleEdges);
        infoGraphGridLayout.add(edgesPanel);
        // infoGraphGridLayout.add(nbEdges);

        //Line 5
        // diameterPanel.add(padding);
        diameterPanel.add(titleDiameter);
        infoGraphGridLayout.add(diameterPanel);
        // infoGraphGridLayout.add(diameter);

        infoGraphGridLayout.setBackground(Color.YELLOW);
        this.add(infoGraphGridLayout,BorderLayout.CENTER);

        this.setVisible(true);
    }

    
}
