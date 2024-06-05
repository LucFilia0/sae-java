package planeair.components;

// Import swing composants
import javax.swing.JPanel;

import planeair.App;

import javax.swing.JComponent;
import javax.swing.JLabel;

// Import Layout
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

/**
 * Class which create a panel of INFO GRAPH
 * The panel is situe in right bottom to the frame
 * 
 * @author GIRAUD Nila
 */
public class NInfoGraphPanelApp extends JPanel {


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

        this.setBackground(App.KINDAYELLOW);

        this.setLayout(new GridBagLayout());

        degreePanel.setBackground(App.KINDAYELLOW);
        titleDegreeAvg.setFont(new Font("Arial", Font.CENTER_BASELINE, 14));

        compPanel.setBackground(App.KINDAYELLOW);
        titleComp.setFont(new Font("Arial", Font.CENTER_BASELINE, 14));

        nodesPanel.setBackground(App.KINDAYELLOW);
        titleNodes.setFont(new Font("Arial", Font.CENTER_BASELINE, 14));

        edgesPanel.setBackground(App.KINDAYELLOW);
        titleEdges.setFont(new Font("Arial", Font.CENTER_BASELINE, 14));

        diameterPanel.setBackground(App.KINDAYELLOW);
        titleDiameter.setFont(new Font("Arial", Font.CENTER_BASELINE, 14));


        this.setMaximumSize(new Dimension(225,200));
       
        this.setVisible(true);

    }

    /**
     * Add Components in the panel
     */
    public void addComponents(){

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = -1; // colonne 1
        gbc.gridy = 0; // ligne 0
        gbc.anchor = GridBagConstraints.WEST;
        //LINE 1
        degreePanel.add(titleDegreeAvg);
        this.add(degreePanel,gbc);

        gbc.gridx = 0; // colonne 1
        gbc.gridy = 1; // ligne 0


        //Line 2
        compPanel.add(titleComp);
        this.add(compPanel,gbc);

        gbc.gridx = 0; // colonne 1
        gbc.gridy = 2; // ligne 0

        //Line 3
        nodesPanel.add(titleNodes);
        this.add(nodesPanel,gbc);

        gbc.gridx = 0; // colonne 1
        gbc.gridy = 3; // ligne 0

        //Line 4
        edgesPanel.add(titleEdges);
        this.add(edgesPanel,gbc);

        gbc.gridx = 0; // colonne 1
        gbc.gridy = 4; // ligne 0

        //Line 5
        diameterPanel.add(titleDiameter);
        this.add(diameterPanel,gbc);

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
