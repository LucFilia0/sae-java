package planeair.graph.coloring;

//#region IMPORTS
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.graphstream.graph.Node;

import planeair.graph.graphtype.GraphSAE;
//#endregion
/**
 * <html>
 * Class handling the {@code Welsh and Powell} algorithm
 * 
 * @author Nathan LIEGEON
 * @see <a href="https://en.wikipedia.org/wiki/Graph_coloring">
 *  for more detail
 * </a>
 * </html>
 */
public abstract class ColoringWelshPowell {
    
    /**
     * Colors a Graph using the Welsh and Powell algorithm with at most kMax 
     * colors if the graph is a testGraph. If it can't do so, 
     * it will try to minimize conflicts.
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
        TreeSet<Node> sortedSet = new TreeSet<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                int comp = Integer.compare(o2.getDegree(), o1.getDegree()) ;
                if (comp == 0) {
                   return o1.getId().compareTo(o2.getId()) ;
                }
                return comp ;
            }
        }) ;
        Set<Node> nodeSet ;
        graph.nodes().forEach(node -> sortedSet.add(node));

        // Implementation of the algorithm
        while (!sortedSet.isEmpty() && res[0] < kMax) {
            nodeSet = new HashSet<>() ;
            res[0]++ ;
            Iterator<Node> itr = sortedSet.iterator() ;
            while (itr.hasNext()) {
                Node currentNode = itr.next() ;
                if ((int)currentNode.getAttribute(ColoringUtilities
                .NODE_COLOR_ATTRIBUTE) == 0 
                && !nodeSet.contains(currentNode)) {
                    
                    itr.remove() ;
                    currentNode.setAttribute(ColoringUtilities
                    .NODE_COLOR_ATTRIBUTE, res[0]) ;
                    nodeSet.addAll(currentNode.neighborNodes()
                    .collect(Collectors.toSet())) ;
                }
            }
        }

        // Conflicts management
        res[1] = ColoringUtilities.colorWithLeastConflicts(graph) ;
        graph.setNbColors(res[0]) ;
        graph.setNbConflicts(res[1]) ;
    }
}
