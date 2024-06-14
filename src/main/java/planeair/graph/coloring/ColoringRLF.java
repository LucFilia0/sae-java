package planeair.graph.coloring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;

import planeair.graph.graphtype.GraphSAE;

/**
 * Class handling all the methods for the RLF graph Algorithm
 * The algorithm was based on this source 
 * {@link <a href = "https://en.wikipedia.org/wiki/Recursive_largest_first_algorithm">Source</a>}
 * 
 * @author Nathan LIEGEON
 */
public abstract class ColoringRLF {
    
    /** 
     * Colors the graph using the RLF (Recursive Larget First) Algorithm
     * If the number of colors used reaches kMax, a different algorithm will be used to minimize conflicts.
     * 
     * @param graph Graph which will be colored
     * 
     * @author Nathan LIEGEON
     */
    public static void coloringRLF(GraphSAE graph) {
        // INITIALISATION
        int nbConflicts = 0 ;
        int kMax = graph.getKMax() ;
        if (kMax < 2) {
            kMax = Integer.MAX_VALUE ;
        }

        // Copy of the graph which will be progressively emptied
        GraphSAE newGraph = (GraphSAE)Graphs.clone(graph) ;

        // First recursive call
        int color = recursiveColoringRLF(graph, newGraph, 0, kMax) ;
        
        // Conflict management 
        int res[] = {0,0} ;
        for (Node nodeLeft : newGraph) {
            res = ColoringUtilities.getLeastConflictingColor(graph, nodeLeft) ;
            nodeLeft.setAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE, res[0]) ;
            nbConflicts += res[1] ;
        }

        graph.setNbColors(color) ;
        graph.setNbConflicts(nbConflicts) ;

    }

    /**
     * Recursive function which will progressively eliminate colored nodes from a copy of the original graph until
     * there is none left or kMax has been surpassed
     * 
     * @param originalGraph
     * @param graph
     * @param color
     * @return
     * 
     * @author Nathan LIEGEON
     */
    public static int recursiveColoringRLF(GraphSAE originalGraph, GraphSAE graph, int color, int kMax) {
        if (graph.getNodeCount() == 0 || color >= kMax) {
            return color ;
        }
        else {
            color++ ;
            // Doing this prevents randomness in the node selected, we will always select the node with biggest Id
            // Choosing the biggest Id is arbitrary and only for consistency
            ArrayList<Node> degreeMap = Toolkit.degreeMap(graph) ;
            int i = 1 ;
            int maxI = 0 ;
            while (i < degreeMap.size() && degreeMap.get(i) == degreeMap.get(i-1)) {
                if (degreeMap.get(i).getId().compareTo(degreeMap.get(maxI).getId()) > 0) {
                    maxI = i ;
                }
            }

            Node firstNode = degreeMap.get(maxI) ;
            Node nextNode ;

            // Orders the nodes by id
            TreeSet<Node> colorSet = new TreeSet<>(new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {
                    return o2.getId().compareTo(o1.getId()) ;
                }
            }) ;
            Set<Node> addableNodesSet = graph.nodes().collect(Collectors.toSet()) ;

            // We add the first node and remove all its neighbors from the set
            colorSet.add(firstNode) ;
            addableNodesSet.remove(firstNode) ;
            addableNodesSet.removeAll(firstNode.neighborNodes().toList()) ;

            // We add the node with the most common neighbors
            // with nodes in the color set, then remove all of 
            //its neighbors and itself from the set of addable nodes
            while (!addableNodesSet.isEmpty()) {
                nextNode = getNextNodeRLF(addableNodesSet, colorSet) ;
                colorSet.add(nextNode) ;
                addableNodesSet.remove(nextNode) ;
                addableNodesSet.removeAll(nextNode.neighborNodes().collect(Collectors.toSet())) ;
            }

            // Apply the coloration to the original graph and remove the node from the copy
            for (Node node : colorSet) {
                // We are working on a copy of the graph so we have to get the real node from the id of node in the copy
                originalGraph.getNode(node.getId()).setAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE, color) ;
                graph.removeNode(node) ;
            }
        }

        return recursiveColoringRLF(originalGraph, graph, color, kMax) ;
    }

    /**
     * Returns the node from addableNodesSet that has the most common neighbors with all nodes in colorSet
     * @param addableNodesSet
     * @param colorSet
     * @return
     * 
     * @author Nathan LIEGEON
     */
    private static Node getNextNodeRLF(Set<Node> addableNodesSet, Set<Node> colorSet) {
        // Counts the number of common Neighbors between a node and all nodes in colorSet 
        HashMap<Node, Integer> countNeighborsInSet = new HashMap<>() ;
        HashMap<Node, Integer> countNeighborsNotInSet = new HashMap<>() ;
        Node res = null ;
        for (Node node : addableNodesSet) {
            countNeighborsInSet.put(node, 0) ;
            countNeighborsNotInSet.put(node, 0) ;
            node.neighborNodes().forEach(neighbor -> {  
                if (colorSet.contains(neighbor)) {
                    countNeighborsInSet.merge(node, 1, Integer::sum) ;
                }
                else {
                    countNeighborsNotInSet.merge(node, 1, Integer::sum) ;
                }
            });
        }

        // Recovers the one with the highest number of common neighbors
        if (!countNeighborsInSet.isEmpty()) {
            // Prevents randomness in the answer provided
            res = Collections.max(countNeighborsInSet.keySet(), (node1, node2) -> {
                int comp1 = countNeighborsInSet.get(node1) ;
                int comp2 = countNeighborsInSet.get(node2) ;
                if (comp1 == comp2) {
                    comp2 = countNeighborsNotInSet.get(node1) ;
                    comp1 = countNeighborsNotInSet.get(node2) ;
                    if (comp1 == comp2) {
                        return node1.getId().compareTo(node2.getId()) ;
                    }
                }
                return Integer.compare(comp1, comp2) ;
            }) ;
        }
        return res ;
    }
}
