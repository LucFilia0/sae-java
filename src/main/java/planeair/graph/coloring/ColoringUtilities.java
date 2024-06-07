package planeair.graph.coloring;

import java.awt.Color;
//-- Import Java
import java.util.* ;
import java.util.stream.Collectors;

import org.graphstream.graph.Node;

import planeair.graph.graphtype.GraphSAE;
import planeair.graph.graphtype.TestGraph;

//-- Import Exceptions

/**
 * Class handling Coloring algorithms, mostly consists of static methods
 */
public class ColoringUtilities {
    /**
     * default size for nodes in the stylesheet 
     */
    public static final String DEFAULT_NODE_SIZE = "20px" ;

    /**
     * String Identifier for the DSATUR algorithm
     */
    public static final String DSATUR = "DSATUR" ;

    /**
     * String Identifier for the RLF algorithm
     */
    public static final String RLF = "RLF" ;

    /**
     * String Identifier for the Welsh & Powell algorithm
     */
    public static final String WELSH_POWELL = "WELSH POWELL" ;

    /**
     * Color for the default background color
     */
    public static final Color GRAPH_BACKGROUND_COLOR = new Color(250, 220, 87) ;
    
    /**
     * Checks if the coloring worked.
     * 
     * @param graph Graph that will be tested.
     * @param String key of the color attribute used
     * @return int number of nodes which are adjacent to another node with the same color.
     * 
     * @author Nathan LIEGEON
     */
    public static int computeNumberOfConflicts(GraphSAE graph, boolean showErrorMessages) {
        int nbProblems = 0 ;
        for (Node node : graph) {
            for (Node neighbor : node.neighborNodes().collect(Collectors.toSet())) {
                if (node.getAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE) == neighbor.getAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE)) {
                    nbProblems++ ;
                    if (showErrorMessages) {
                        System.out.println("Probleme entre " + node + " et " + neighbor) ;
                    }
                }
            }
        }   

        return nbProblems/2 ;
    }

    /**
     * Reads the color attribute of the nodes to give them an actual color on the display by editing its stylesheet
     * 
     * @param graph graph getting colored
     * @param nbColor number of colors the graph has
     */
    public static void setGraphStyle(GraphSAE graph, int nbColor) {
        StringBuffer stylesheet = new StringBuffer("node {size-mode : dyn-size ; size : " + DEFAULT_NODE_SIZE + " ; }\n") ;
        Integer color ;
        if (nbColor > 0) {
            for (Node coloringNode : graph) {
                color = (Integer)coloringNode.getAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE) ;
                coloringNode.setAttribute("ui.class", "color" + color) ;
            }
            
            // FFFFFF in decimal (i asked Google 👍)
            int maxHexValue = 16777215 ;
            
            // Hexadecimal value used for the color stored as an int
            int currentHexValue ;

            for (int i = 0 ; i < nbColor ; i++) {
                currentHexValue = (int)Math.round(maxHexValue*Math.random()) ;
                stylesheet.append("node.color" + (i+1) + "{fill-color : #" + toValidHex(Integer.toHexString(currentHexValue)) + " ; }\n") ;
            }
        }

        graph.setAttribute("ui.stylesheet", stylesheet.toString()) ;
    }

    /**
     * Fills the leading digits of a hex code with 0 until the number has 6 digits
     * example : str FF (reprensenting #FF) becomes 0000FF
     * @param str hex value of a number, HAS TO ONLY CONTAIN NUMBERS
     * @return the formatted hex string
     */
    public static String toValidHex(String str) {
        int nbZero = (6 - str.length()) ;
        StringBuffer res = new StringBuffer() ;
        for (int i = 0 ; i < nbZero ; i++) {
            res.append("0") ;
        }
        return res.append(str).toString() ;
    }

    /**
     * Removes all attributes linked to the coloring of this graph
     * @param graph
     * @param conflictAttribute
     */
    public static void removeCurrentColoring(GraphSAE graph) {
        graph.setAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE, 0) ;

        if (graph instanceof TestGraph) {
            graph.setAttribute(TestGraph.CONFLICT_ATTRIBUTE, 0) ;
            for (Node node : graph) {
                node.setAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE, 0) ;
                node.setAttribute(TestGraph.CONFLICT_ATTRIBUTE, 0) ;
            }
        }
        else {
            for (Node node : graph) {
                node.setAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE, 0) ;
            }
        }
    }

    /**
     * Calls the correct coloring method with the right algorithm
     * @param graph
     * @param algorithm
     * @return
     */
    public static void colorGraphWithChosenAlgorithm(GraphSAE graph, String algorithm) {
        switch (algorithm) {
                case DSATUR :
                    ColoringDSATUR.coloringDsatur(graph) ;
                    break ;
                case WELSH_POWELL :
                    ColoringWelshPowell.coloringWelshPowell(graph) ;
                    break ;
                case RLF :
                    ColoringRLF.coloringRLF(graph) ;  
                    break ;
                default :
        }
    }

    /**
     * Colors a node by minimizing conflicts (2 nodes with the same color touching each other)
     * 
     * @param graph graph you are trying to color
     * @param node node you are trying to color 
     * @return array consisting of 2 values, the color assigned to the node and the number of conflicts it generated
     * 
     * @author Nathan LIEGEON
     */
    public static int[] getLeastConflictingColor(GraphSAE graph, Node node) {
        int[] minConflict = {1, Integer.MAX_VALUE} ;
        int[] currentConflict  = new int[2];
        HashMap<Integer, Integer> conflictCount = new HashMap<>() ;

        node.neighborNodes().forEach(neighbor -> {
            if ((Integer) neighbor.getAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE) != 0) {
                conflictCount.merge((Integer)neighbor.getAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE), 1, Integer::sum) ;
            }
        }) ;

        for (Integer color : conflictCount.keySet()) {
            currentConflict[0] = color ;
            currentConflict[1] = conflictCount.get(color) ;
            if (minConflict[1] > currentConflict[1]) {
                minConflict = currentConflict ;
            }
        }

        if (minConflict[1] == Integer.MAX_VALUE) {
            minConflict[1] = 0 ;
        }

        return minConflict ;
    }
}
