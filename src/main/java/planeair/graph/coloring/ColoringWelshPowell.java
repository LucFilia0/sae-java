package planeair.graph.coloring;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;
import java.util.stream.Collectors;

import org.graphstream.graph.Node;

import planeair.graph.graphtype.GraphSAE;
import planeair.graph.graphtype.TestGraph;

public abstract class ColoringWelshPowell {
    
    /**
     * Colors a Graph using the Welsh & Powell algorithm with at most kMax colors if 
     * the graph is a testGraph. If it can't do so, it will try to minimize conflicts
     * 
     * @param graph Graph getting colored
     * 
     * @author Nathan LIEGEON
     */
    public static void coloringWelshPowell(GraphSAE graph) {
        int kMax = graph.getKMax() ;
        if (kMax < 2) {
            kMax = Integer.MAX_VALUE ;
        }

        int[] res = {0,0} ;
        LinkedList<Node> nodeList = new LinkedList<>() ;
        Set<Node> nodeSet ;
        graph.nodes().forEach(node -> nodeList.addFirst(node));

        // Descending order sort by degree
        nodeList.sort(new Comparator<Node>() {
            public int compare(Node arg0, Node arg1) {
                return Integer.compare(arg1.getDegree(), arg0.getDegree()) ;
            }
        }) ;

        // Implementation of the algorithm
        while (!nodeList.isEmpty() && res[0] < kMax) {
            nodeSet = new HashSet<>() ;
            res[0]++ ;
            ListIterator<Node> itr = nodeList.listIterator() ;
            while (itr.hasNext()) {
                Node currentNode = itr.next() ;
                if ((int)currentNode.getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE) == 0 
                    && !nodeSet.contains(currentNode)) {
                    itr.previous() ;
                    itr.remove() ;
                    currentNode.setAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE, res[0]) ;
                    nodeSet.addAll(currentNode.neighborNodes().collect(Collectors.toSet())) ;
                }
            }
        }

        // Conflicts management
        res[1] = ColoringUtilities.colorWithLeastConflicts(graph) ;
        graph.setAttribute(TestGraph.CONFLICT_ATTRIBUTE, res[1]) ;
        graph.setAttribute(GraphSAE.COLOR_ATTRIBUTE, res[0]) ;
    }
}
