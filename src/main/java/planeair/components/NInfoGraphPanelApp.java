package planeair.components;

// Import swing composants
import javax.swing.JPanel;
import javax.swing.JLabel;

// Import awt composants
import java.awt.Color;
// Import Layout
import java.awt.GridLayout;
import java.awt.BorderLayout;

/**
 * Class which create a panel of INFO GRAPH
 * The panel is situe in right bottom to the frame
 * 
 * @author GIRAUD Nila
 */
public class NInfoGraphPanelApp extends JPanel{

    //STRUCT
    /**
     * Layout's Panel for INFO
     * Grid Layout
     * nb LINE : 5
     * nb COLUMN : 1 (Name Info + Info)
     */
    private JPanel infoGraphGridLayout = new JPanel(new GridLayout(5, 1));

    // LINE 1

    /**
     * Add an empty border for titleDegreeAvg
     */
    private JPanel degreePanel = new JPanel();
    /**
     * Title of the first LINE
     * Degree of the graph
     */
    private JLabel titleDegreeAvg = new JLabel("Degré moyen: " + "-");
    /**
     * Degree in the graph
     */
    // JLabel degreeAvg = new JLabel("test ");

    // LINE 2

    /**
     * Add an empty border for titleComp
     */
    private JPanel compPanel = new JPanel();
    /**
     * Title of the second LINE
     * Nb related composants
     */
    private JLabel titleComp = new JLabel("Nb comp. connexes: " + "-");
    /**
     * Degree in the graph
     */
    // JLabel relatedComp = new JLabel("test ");

    // LINE 3

    /**
     * Add an empty border for titleNodes
     */
    private JPanel nodesPanel = new JPanel();
    /**
     * Title of the third LINE
     * NbNodes
     */
    private JLabel titleNodes = new JLabel("Nb noeuds: " + "-");
    /**
     * Degree in the graph
     */
    // JLabel nbNodes = new JLabel("test ");

    // LINE 4

    /**
     * Add an empty border for titleEdges
     */
    private JPanel edgesPanel = new JPanel();
    /**
     * Title of the LINE four
     *  Nb edges of the graph
     */
    private JLabel titleEdges = new JLabel("Nb arretes: " + "-");
    /**
     * Degree in the graph
     */
    // JLabel nbEdges = new JLabel("test ");

    // LINE 5

    /**
     * Add an empty border for titleDiameter
     */
    private JPanel diameterPanel = new JPanel();
    /**
     * Title of the LINE five
     * Diameter of the graph
     */
    private JLabel titleDiameter = new JLabel("Diametre: " + "-");

    /**
     * Contructor of NInfoGraphPanelApp
     */
    public NInfoGraphPanelApp(){

        this.setBackground(Color.YELLOW);

        this.setLayout(new BorderLayout());
        this.setAlignmentX(LEFT_ALIGNMENT) ;

        degreePanel.setBackground(Color.YELLOW);
        compPanel.setBackground(Color.YELLOW);
        nodesPanel.setBackground(Color.YELLOW);
        edgesPanel.setBackground(Color.YELLOW);
        diameterPanel.setBackground(Color.YELLOW);

        //LINE 1
        degreePanel.add(titleDegreeAvg);
        infoGraphGridLayout.add(degreePanel);


        //Line 2
        compPanel.add(titleComp);
        infoGraphGridLayout.add(compPanel);

        //Line 3
        nodesPanel.add(titleNodes);
        infoGraphGridLayout.add(nodesPanel);

        //Line 4
        edgesPanel.add(titleEdges);
        infoGraphGridLayout.add(edgesPanel);

        //Line 5
        diameterPanel.add(titleDiameter);
        infoGraphGridLayout.add(diameterPanel);

        infoGraphGridLayout.setBackground(Color.YELLOW);
        this.add(infoGraphGridLayout,BorderLayout.CENTER);

        this.setVisible(true);
    }

     /**
     * get TitleDegreeAvg
     * @return TitleDegreeAvg
     */
    public JLabel getTitleDegreeAvg() {
        return this.titleDegreeAvg;
    }

    /**
     * set TitleDegreeAvg
     */
    public void setTitleDegreeAvg(JLabel titleDegreeAvg) {
        this.titleDegreeAvg = titleDegreeAvg;
    }


    /**
     * get TitleComp
     * @return TitleComp
     */
    public JLabel getTitleComp() {
        return this.titleComp;
    }

    /**
     * set TitleComp
     */
    public void setTitleComp(JLabel titleComp) {
        this.titleComp = titleComp;
    }

    /**
     * get TitleNodes
     * @return TitleNodes
     */
    public JLabel getTitleNodes() {
        return this.titleNodes;
    }

     /**
     * set TitleNodes
     */
    public void setTitleNodes(JLabel titleNodes) {
        this.titleNodes = titleNodes;
    }

     /**
     * get TitleEdges
     * @return TitleEdges
     */
    public JLabel getTitleEdges() {
        return this.titleEdges;
    }

    /**
     * set TitleEdges
     */
    public void setTitleEdges(JLabel titleEdges) {
        this.titleEdges = titleEdges;
    }

     /**
     * get TitleDiameter
     * @return TitleDiameter
     */
    public JLabel getTitleDiameter() {
        return this.titleDiameter;
    }

    /**
     * set TitleDiameter
     */
    public void setTitleDiameter(JLabel titleDiameter) {
        this.titleDiameter = titleDiameter;
    }
}
