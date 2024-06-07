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
import planeair.graph.graphtype.TestGraph;

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

        // Set containing all the nodes with the current color
        int nbConflicts = 0 ;
        int kMax = Integer.MAX_VALUE ;
        TestGraph testGraph ;
        if (graph instanceof TestGraph) {
            testGraph = (TestGraph)graph ;
            kMax = testGraph.getKMax() ;
        }

        GraphSAE newGraph = (GraphSAE)Graphs.clone(graph) ;

        // First recursive call
        int color = recursiveColoringRLF(graph, newGraph, 0, kMax) ;

        graph.setNbColors(color) ;
        if (graph instanceof TestGraph) {
            int res[] = {0,0} ;
            for (Node nodeLeft : newGraph) {
                res = ColoringUtilities.getLeastConflictingColor(graph, nodeLeft) ;
                nodeLeft.setAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE, res[0]) ;
                nbConflicts += res[1] ;
            }
            testGraph = (TestGraph)graph ;
            testGraph.setNbConflicts(nbConflicts) ;
        }

    }

    /**
     * Recursive function which will progressively eliminate colored nodes from a copy of the original graph until
     * there is none left or kMax has been surpassed
     * @param originalGraph
     * @param graph
     * @param color
     * @param kMax
     * @return
     */
    public static int recursiveColoringRLF(GraphSAE originalGraph, GraphSAE graph, int color, int kMax) {
        if (graph.getNodeCount() > 0 && color < kMax) {
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

            TreeSet<Node> colorSet = new TreeSet<>(new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {
                    return o2.getId().compareTo(o1.getId()) ;
                }
            }) ;
            Set<Node> addableNodesSet = graph.nodes().collect(Collectors.toSet()) ;

            colorSet.add(firstNode) ;
            addableNodesSet.remove(firstNode) ;
            addableNodesSet.removeAll(firstNode.neighborNodes().toList()) ;

            while (!addableNodesSet.isEmpty()) {
                nextNode = getNextNodeRLF(addableNodesSet, colorSet) ;
                colorSet.add(nextNode) ;
                addableNodesSet.remove(nextNode) ;
                addableNodesSet.removeAll(nextNode.neighborNodes().collect(Collectors.toSet())) ;
            }

            for (Node node : colorSet) {
                originalGraph.getNode(node.getId()).setAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE, color) ;
                graph.removeNode(node) ;
            }
        }

        else {
            return color ;
        }

        return recursiveColoringRLF(originalGraph, graph, color, kMax) ;
    }

    /**
     * Returns the node from addableNodesSet that has the most common neighbors with all nodes in colorSet
     * @param addableNodesSet
     * @param colorSet
     * @return
     */
    private static Node getNextNodeRLF(Set<Node> addableNodesSet, Set<Node> colorSet) {
        // Counts the number of common Neighbors between a node and all nodes in colorSet 
        HashMap<Node, Integer> countCommonNeighbors = new HashMap<>() ;
        Node res = null ;
        for (Node node : addableNodesSet) {
            countCommonNeighbors.put(node, 0) ;
            node.neighborNodes().forEach(neighbor -> {  
                if (colorSet.contains(neighbor)) {
                    countCommonNeighbors.merge(node, 1, Integer::sum) ;
                }
            });
        }

        // Recovers the one with the highest number of common neighbors
        if (!countCommonNeighbors.keySet().isEmpty()) {
            // Prevents randomness in the answer provided
            res = Collections.max(countCommonNeighbors.keySet(), (node1, node2) -> {
                int comp1 = countCommonNeighbors.get(node1) ;
                int comp2 = countCommonNeighbors.get(node1) ;
                if (comp1 == comp2) {
                    return node1.getId().compareTo(node2.getId()) ;
                }
                else {
                    return Integer.max(comp1, comp2) ;
                }
            }) ;
        }
        return res ;
    }
}
