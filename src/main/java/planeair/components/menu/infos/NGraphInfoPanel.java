package planeair.components.menu.infos;

//#region
import javax.swing.JPanel;
import javax.swing.JLabel;

import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.algorithm.Toolkit;

import planeair.App;
import planeair.graph.graphtype.GraphSAE;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.math.RoundingMode;
import java.text.DecimalFormat;
//#endregion

/**
 * Class which create a panel of INFORMATION GRAPH
 * The panel is situe in right bottom to the frame
 * 
 * @author GIRAUD Nila
 */
public class NGraphInfoPanel extends JPanel {

    //#region INSTANTIALISATION AND INITIALISATION
        //#region STATIC TITLE
        /**
         * String identifier for the text related to the degree of the graph
         */
        public static final String DEGREE_TEXT = "Degré moyen: " ;

        /**
         * String identifier for the text related to the 
         * number of connected components of the graph
         */
        public static final String CONNECTED_COMP_TEXT = "Nb comp. connexes: " ;

        /**
         * String identifier for the text related to the 
         * number of nodes of the graph
         */
        public static final String NB_NODES_TEXT = "Nb noeuds: " ;

        /**
         * String identifier for the text related to the 
         * number of edges of the graph
         */
        public static final String NB_EDGES_TEXT = "Nb arretes: " ;

        /**
         * String identifier for the text related 
         * to the diameter of the graph
         */
        public static final String DIAMETER_TEXT = "Diametre: " ;

        /**
         * String identifier for the text related to the 
         * number of colors of the graph
         */
        public static final String NB_COLORS_TEXT = "Nb couleurs: " ;

        /**
         * String identifier for the text related to the 
         * number of edges of the graph
         */
        public static final String NB_CONFLICTS_TEXT = "Nb conflits: " ;
        //#endregion

        //#region LINE 1 DEGREE
        /**
         * Add an empty border for titleDegreeAvg
         */
        private JPanel degreePanel = new JPanel();
        /**
         * Title of the first LINE
         * Degree of the graph
         */
        private JLabel titleDegreeAvg = new JLabel(DEGREE_TEXT + "-");
        //#endregion

        //#region LINE 2 CONNECTED COMP
        /**
         * Add an empty border for titleComp
         */
        private JPanel compPanel = new JPanel();
        /**
         * Title of the second LINE
         * Nb related components
         */
        private JLabel titleComp = new JLabel(CONNECTED_COMP_TEXT + "-");
        //#endregion

        //#region LINE 3 NB NODES
        /**
         * Add an empty border for titleNodes
         */
        private JPanel nodesPanel = new JPanel();
        /**
         * Title of the third LINE
         * NbNodes
         */
        private JLabel titleNodes = new JLabel(NB_NODES_TEXT + "-");
        //#endregion

        //#region LINE 4 NB EDGES
        /**
         * Add an empty border for titleEdges
         */
        private JPanel edgesPanel = new JPanel();
        /**
         * Title of the LINE four
         *  Nb edges of the graph
         */
        private JLabel titleEdges = new JLabel(NB_EDGES_TEXT + "-");
        //#endregion

        //#region LINE 5 DIAMETER
        /**
         * Add an empty border for titleDiameter
         */
        private JPanel diameterPanel = new JPanel();

        /**
         * Title of the LINE five
         * Diameter of the graph
         */
        private JLabel titleDiameter = new JLabel(DIAMETER_TEXT + "-");
        //#endregion

        //#region LINE 6 NB COLOR
        /**
         * Add an empty border for titleNbColorsUsed
         */
        private JPanel nbColorPanel = new JPanel() ;
        /**
         * Title of the LINE 6
         * Nb of colors used to color the graph
         */
        private JLabel titleNbColorsUsed = new JLabel(NB_COLORS_TEXT + "-") ;
        //#endregion

        //#region LINE 7 NB CONLFIT
         /**
         * Add an empty border for titleNbConflictsOccurred
         */
        private JPanel nbConflictPanel = new JPanel() ;

        /**
         * Title of the LINE seven
         * Nb of conflicts that occurred while coloring the graph
         */
        private JLabel titleNbConflictsOccurred = new JLabel(NB_CONFLICTS_TEXT + "-") ;
        //#endregion

    //#endregion

    //#region CONSTRUCTOR
    /**
     * Contructor of NInfoGraphPanelApps
     */
    public NGraphInfoPanel() {
        
        this.initComponents();
        this.addComponents();
       
        this.setVisible(true);

    }
    //#endregion

    /**
     * Initializes all the components
     */
    private void initComponents() {
        this.setBackground(App.KINDAYELLOW);

        this.setLayout(new GridBagLayout());

        degreePanel.setBackground(App.KINDAYELLOW);
        titleDegreeAvg.setFont(App.KINDANORMAL);

        compPanel.setBackground(App.KINDAYELLOW);
        titleComp.setFont(App.KINDANORMAL);

        nodesPanel.setBackground(App.KINDAYELLOW);
        titleNodes.setFont(App.KINDANORMAL);

        edgesPanel.setBackground(App.KINDAYELLOW);
        titleEdges.setFont(App.KINDANORMAL);

        diameterPanel.setBackground(App.KINDAYELLOW);
        titleDiameter.setFont(App.KINDANORMAL);

        nbColorPanel.setBackground(App.KINDAYELLOW);
        titleNbColorsUsed.setFont(App.KINDANORMAL);

        nbConflictPanel.setBackground(App.KINDAYELLOW);
        titleNbConflictsOccurred.setFont(App.KINDANORMAL);

        this.setMaximumSize(new Dimension(225,230));
    }

    //#region ADD

    /**
     * Add Components in the panel
     */
    private void addComponents(){
        this.removeAll() ;
        GridBagConstraints gbc = new GridBagConstraints();
        if (App.app.getGraphRenderer() != null) {
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

            gbc.gridx = 0; // colonne 1
            gbc.gridy = 5; // ligne 0

            //Line 6
            nbColorPanel.add(titleNbColorsUsed) ;
            this.add(nbColorPanel, gbc) ;

            gbc.gridx = 0; // colonne 1
            gbc.gridy = 6; // ligne 0

            //Line 7
            nbConflictPanel.add(titleNbConflictsOccurred) ;
            this.add(nbConflictPanel, gbc) ;
        }

        else {
            gbc.gridx = 0 ;
            gbc.gridy = 0 ;
            JLabel problem = new JLabel("Veuillez importer un graph") ;
            problem.setFont(new Font("Arial", Font.BOLD, 16)) ;
            this.add(problem, gbc) ;
        }

    }
    //#endregion

    //#region GETTER AND GETTER

        //#region JLABEL 
        /**
         * get TitleDegreeAvg
         * @return TitleDegreeAvg
         */
        public JLabel getTitleDegreeAvg() {
            return this.titleDegreeAvg;
        }

        /**
         * set TitleDegreeAvg
         * @param titleDegreeAvg the new label
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
         * @param titleComp the new label
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
         * @param titleNodes the new label
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
         * @param titleEdges the new label
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
         * @param titleDiameter the new diameter
         */
        public void setTitleDiameter(JLabel titleDiameter) {
            this.titleDiameter = titleDiameter;
        }
        //#endregion

        //#region JLABEL DECIMAL FORMAT

        /**
         * Sets the text for the average degree field with a 2 digit precision
         * @param averageDegree The average degree being set
         */
        public void setAverageDegree(double averageDegree) {
            DecimalFormat format = new DecimalFormat("#.##") ;
            format.setRoundingMode(RoundingMode.HALF_UP) ;
            this.titleDegreeAvg.setText(DEGREE_TEXT + format.format(averageDegree)) ;
        }

        /**
         * changes the number of connected components
         * @param nbCC the number of connected components
         */
        public void setNbConnectedComponent(int nbCC) {
            this.titleComp.setText(CONNECTED_COMP_TEXT + nbCC) ;
        }

        /**
         * changes the number of diameter
         * @param diameter the number of diameters
         */
        public void setDiameter(int diameter) {
            this.titleDiameter.setText(DIAMETER_TEXT + diameter) ;
        }

        /**
         * changes the number of nodes
         * @param nbNodes the number of nodes
         */
        public void setNbNodes(int nbNodes) {
            this.titleNodes.setText(NB_NODES_TEXT + nbNodes) ;
        }

        /**
         * changes the number of edges
         * @param nbEdges the number of edges
         */
        public void setNbEdges(int nbEdges) {
            this.titleEdges.setText(NB_EDGES_TEXT + nbEdges) ;
        }

        /**
         * changes the number of colors
         * @param nbColors the number of colors
         */
        public void setNbColorsUsed(int nbColors) {
            this.titleNbColorsUsed.setText(NB_COLORS_TEXT + nbColors) ;
        }

        /**
         * changes the number of conflicts
         * @param nbConflicts the number of conflicts
         */
        public void setNbConflictsOccurred(int nbConflicts) {
            this.titleNbConflictsOccurred.setText(NB_CONFLICTS_TEXT + nbConflicts) ;
        } 
        //#endregion

        /**
         * Resets all of infoGraphs values to their default.
         */
        public void setDefaultValues() {
            titleNodes.setText(NB_NODES_TEXT + "-") ;
            titleEdges.setText(NB_EDGES_TEXT + "-") ;
            titleDegreeAvg.setText(DEGREE_TEXT + "-") ;
            titleDiameter.setText(DIAMETER_TEXT + "-") ;
            titleComp.setText(CONNECTED_COMP_TEXT + "-") ;
            titleNbColorsUsed.setText(NB_COLORS_TEXT + "-") ;
            titleNbConflictsOccurred.setText(NB_CONFLICTS_TEXT + "-") ;
        }

        /**
         * Computes the relevant graph statistics and updates the infoGraphPanel
         * If the graph has a Coloring, then inputs the number of colors and conflicts
         */
        public void computeGraphStats() {
            if (App.app.getGraphRenderer() != null) { 
                this.setDefaultValues() ;
                GraphSAE graph = App.app.getGraph() ;  
                ConnectedComponents cc = new ConnectedComponents(graph) ;
                cc.compute() ;

                this.setNbConnectedComponent(cc.getConnectedComponentsCount()) ;
                this.setAverageDegree((Toolkit.averageDegree(graph))) ;
                this.setNbEdges(graph.getEdgeCount()) ;
                this.setNbNodes(graph.getNodeCount()) ;
                this.setDiameter((int)Toolkit.diameter(graph)) ;

                if (graph.getNbColors() != 0) {
                    this.setNbColorsUsed(graph.getNbColors()) ;
                    this.setNbConflictsOccurred(graph.getNbConflicts());
                }
            }
        }

        /**
         * Change font size of all labels inside this panel
         * @param size The new size of the font
         */
        public void setFontSize(int size) {
            titleComp.setFont(new Font(getFont().getName(), Font.BOLD, size)) ;
            titleDegreeAvg.setFont(new Font(getFont().getName(), Font.BOLD, size)) ;
            titleDiameter.setFont(new Font(getFont().getName(), Font.BOLD, size)) ;
            titleEdges.setFont(new Font(getFont().getName(), Font.BOLD, size)) ;
            titleNbColorsUsed.setFont(new Font(getFont().getName(), Font.BOLD, size)) ;
            titleNbConflictsOccurred.setFont(new Font(getFont().getName(), Font.BOLD, size)) ;
            titleNodes.setFont(new Font(getFont().getName(), Font.BOLD, size)) ;
        }

        /**
         * Refreshes the panel
         */
        public void refresh() {
            this.addComponents() ; //#CryAboutIt #Kms #Brigand #CROUS'kie
            this.computeGraphStats() ;
        }
    //#endregion
}
