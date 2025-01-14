package planeair.graph.coloring;

//#region IMPORTS
import java.awt.Color;

import java.util.* ;
import java.util.stream.Collectors;

import org.graphstream.graph.Node;

import planeair.graph.graphtype.GraphSAE;
//#endregion

/**
 * Class handling Coloring algorithms, mostly consists of static methods
 */
public abstract class ColoringUtilities {

    //#region ATTRIBUTES

    /**
     * default size for nodes in the stylesheet 
     */
    public static final Integer DEFAULT_NODE_SIZE = 20 ;

    /**
     * The String identifier that that represents the color assigned to a Node in a GraphSAE (int)
     */
    public static final String NODE_COLOR_ATTRIBUTE = "color" ;

    //#endregion
    
    /**
     * Checks if the coloring worked.
     * 
     * @param graph Graph that will be tested.
     * @return int number of nodes which are adjacent to another node with the same color.
     * 
     * @author Nathan LIEGEON
     */
    public static int computeNumberOfConflicts(GraphSAE graph) {
        return (int)graph.edges().filter(e -> 
            e.getNode0().getAttribute(NODE_COLOR_ATTRIBUTE) ==
            e.getNode1().getAttribute(NODE_COLOR_ATTRIBUTE))
            .count() ;
    }

    /**
     * Reads the color attribute of the nodes to give them an actual color on the display by editing its stylesheet
     * 
     * @param graph graph getting colored
     * @param nbColor number of colors the graph has, if 0 or less, initializes a default style
     * coloring
     * 
     * @author Nathan LIEGEON
     */
    public static void setGraphStyle(GraphSAE graph, int nbColor) {
        StringBuffer stylesheet = new StringBuffer("node {size-mode : dyn-size;"
                                                    + " \n size : " 
                                                    + DEFAULT_NODE_SIZE 
                                                    + " ; }\n") ;
        Integer color ;
        HashMap<Integer, Color> colorMap = graph.getColorMap() ;
        colorMap.clear() ;
        
        if (nbColor <= 0) {
            graph.setAttribute("ui.stylesheet", stylesheet.toString()) ;
            return ;
        }
        
        for (Node coloringNode : graph) {
            color = (Integer)coloringNode.getAttribute
                (ColoringUtilities.NODE_COLOR_ATTRIBUTE) ;
            coloringNode.setAttribute("ui.class", "color" + color) ;
        }
        
        // FFFFFF in decimal (i asked Google) - 15 
        //to get to FFFFF0 to avoid white nodes
        int maxHexValue = 16777200 ;
        
        // Hexadecimal value used for the color stored as an int
        int currentHexValue ;
        Color addedColor ;
        StringBuilder buffer = new StringBuilder() ;

        for (int i = 0 ; i < nbColor ; i++) {

            // Ensures all colors are different
            do {
                buffer.delete(0, buffer.length()) ;
                currentHexValue = (int)Math.round(maxHexValue*Math.random()) ; 
                
                buffer.append("node.color" + (i+1) + "{fill-color : #" + 
                    toValidHex(Integer.toHexString(currentHexValue)) + " ; }\n") ;
                
                addedColor = new Color(currentHexValue) ;
            } while (colorMap.values().contains(addedColor)) ;

            stylesheet.append(buffer) ;
            colorMap.put(i, addedColor) ;
        }
        
        graph.setAttribute("ui.stylesheet", stylesheet.toString()) ;
    }

    /**
     * Fills the leading digits of a hex code with 0 until the number has 6 digits
     * example : str FF (reprensenting #FF) becomes 0000FF
     * 
     * @param str hex value of a number, HAS TO ONLY CONTAIN NUMBERS
     * @return the formatted hex string
     * 
     * @author Nathan LIEGEON
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
     * 
     * @param graph The graph in question
     * 
     * @author Nathan LIEGEON
     */
    public static void removeCurrentColoring(GraphSAE graph) {
        graph.setAttribute(GraphSAE.COLOR_ATTRIBUTE, 0) ;
        for (Node node : graph) {
            node.setAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE, 0) ;
        }
    }

    /**
     * Colors a node by minimizing conflicts
     * 
     * @param node node you are trying to color 
     * @param kMax the kMax of the graph the node originates from
     * @return array consisting of 2 values, the color assigned to the node and 
     * the number of conflicts it generated
     * 
     * @author Nathan LIEGEON
     */
    public static int[] getLeastConflictingColor(Node node, int kMax) {
        int[] minConflict = {1, Integer.MAX_VALUE} ;
        int[] currentConflict  = new int[2];
        HashMap<Integer, Integer> conflictCount = new HashMap<>() ;

        node.neighborNodes().forEach(neighbor -> {
            if ((Integer) neighbor.getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE) != 0) {
                conflictCount.merge((Integer)neighbor.getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE), 1, Integer::sum) ;
            }
        }) ;

        if (conflictCount.keySet().size() < kMax) {
            // Means one color is available
            TreeSet<Integer> set = new TreeSet<>() ;
            set.addAll(conflictCount.keySet()) ;
            Iterator<Integer> itr = set.iterator() ;

            Integer currentColor = 0 ;
            Integer previousColor = 0;

            if (itr.hasNext())
                currentColor = itr.next() ;

            while ((previousColor + 1) == currentColor && itr.hasNext()) {
                previousColor = currentColor ;
                currentColor = itr.next() ;
            }

            minConflict[0] = previousColor + 1 ;
        }
        else {
            // No color was available
            for (Integer color : conflictCount.keySet()) {
                currentConflict[0] = color ;
                currentConflict[1] = conflictCount.get(color) ;
                
                if (minConflict[1] > currentConflict[1]) {
                    minConflict[0] = currentConflict[0] ;
                    minConflict[1] = currentConflict[1] ;
                }
            }
        }

        if (minConflict[1] == Integer.MAX_VALUE) {
            minConflict[1] = 0 ;
        }

        return minConflict ;
    }

    /**
     * 
     * <html>
     * Colors all {@code Nodes} that don't yet have a 
     * {@code color} with the least conflicting color.
     * <br><br>
     * A conflict is an {@code edge} whose extremeties have the same color
     * @param graphSet {@code Set} containing {@code Nodes} we are 
     * trying to color. If one of the nodes has a color, it gets skipped
     * @param kMax Maximum color that can be assigned to a node
     * </html>
     * 
     * @return The number of conflicts that occurred
     * 
     * @author Nathan LIEGEON
     */
    public static int colorWithLeastConflicts(Set<Node> graphSet, int kMax) {
        
        TreeSet<Node> set = new TreeSet<>((node1, node2) -> {
            if (node1.getDegree() == node2.getDegree()) {
                return node1.getId().compareTo(node2.getId()) ;
            }
            return Integer.compare(node2.getDegree(), node1.getDegree()) ;
        }) ;

        int nbConflicts = 0 ;
        int[] res = {0,0} ;

        for (Node node : graphSet) {
            if ((int)node.getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE) == 0) {
                set.add(node) ;
            }
        }

        for (Node node : set) {
            res = getLeastConflictingColor(node, kMax) ;
            node.setAttribute(NODE_COLOR_ATTRIBUTE, res[0]) ;
            nbConflicts += res[1] ;
        }

        return nbConflicts ;
    }

    /**
     * Colors all {@code Nodes} that don't yet have a color with the least
     * conflicting color
     * 
     * a conflict is an edge whose extremeties have the same color
     * @param graph the graph from which all the nodes will be taken
     * 
     * calls the default implementation of this method (the one with a set)
     * with the graph's kmax and all its nodes that don't yet have a color
     * 
     * @return The number of conflicts that occurred
     * 
     * @author Nathan LIEGEON
     */
    public static int colorWithLeastConflicts(GraphSAE graph) {
        return colorWithLeastConflicts(graph.nodes()
            .filter(n -> (int)n.getAttribute(NODE_COLOR_ATTRIBUTE) == 0)
            .collect(Collectors.toSet())
            , graph.getKMax()) ;
    }

    /**
     * 
     * <html>
     * Colors the graph with the chosen algorithm if the algorithm
     * has a defined method for it.<br><br>
     * Its arguments should be a {@link GraphSAE} and it should be {@code 
     * static}
     * 
     * </html>
     * @param graph The graph we want to color
     * @param algorithm The algorithm used
     * @return {@code true} if the method was successfully invoked, 
     * {@code false} else
     */
    public static boolean colorGraphWithChosenAlgorithm(GraphSAE graph, 
                        ColoringAlgorithms algorithm) {
        try {
            algorithm.getMethod().invoke(null, graph) ;
        } catch (Exception e) {
            return false ;
        }

        return true ;
    }
}
