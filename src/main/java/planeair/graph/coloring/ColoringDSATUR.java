package planeair.graph.coloring;

//#region IMPORTS
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeMap;

import org.graphstream.graph.Node;

import planeair.graph.graphtype.GraphSAE;
import planeair.graph.graphtype.TestGraph;
//#endregion

public abstract class ColoringDSATUR {
    
    /**
     * Give a graph's coloring with less colision we can, use graph saturation principle (degree).
     * 
     * @param graph Graph that will be tested.
     * @return max array consisting of 2 values, 1 : number of colors used, 2 : number of conflicts
     * 
     * @autor GIRAUD Nila mod. Nathan LIEGEON
     */
    public static void coloringDsatur(GraphSAE graph) {
        int kMax = graph.getKMax() ;
        if (kMax < 2) {
            kMax = Integer.MAX_VALUE ;
        }
        LinkedList<Node> ListNodes = new LinkedList<Node>();
        graph.setAttribute(TestGraph.CONFLICT_ATTRIBUTE,0);

        //Put all Nodes in a LinkedList
        for (Node node : graph) {
            node.setAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE, 0);
            node.setAttribute("DSATUR", node.getDegree());
            
            insertSorted(ListNodes, node); // Descendent Sort
        }

        // Recursive call
        recursifDSATUR(ListNodes, graph, kMax);

        int[] res = {0,0};
        for(Node node : graph){
            if((int)node.getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE) > res[0]){
                res[0] = (int)node.getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE);
            }
        }
        graph.setAttribute(GraphSAE.COLOR_ATTRIBUTE, res[0]) ;
        res[1] = (int)graph.getAttribute(GraphSAE.CONFLICT_ATTRIBUTE) ;
    }

    /**
     * Recursif methode for ColoringDSATUR, take a node and find and finds an optimized color.
     * Stop when there are no more nodes.
     * @param ListNodes LinkedList of Graph's Nodes, with descendent sort (with one less node at each recursive call)
     * @param color Color tab wich give a resum of Adjacents Node's colors
     * 
     * @author GIRAUD Nila mod. Nathan LIEGEON
     */

    private static void recursifDSATUR(LinkedList<Node> ListNodes, GraphSAE graph, int kMax){

        if(!ListNodes.isEmpty()){
            //Step1
            Node nodeP = MaxNodeDSATUR(ListNodes);

            // Used to store the colors of each neighbors of this node
            // The Keys are the color, the values are the number of times a neighbor of this node has it.
            TreeMap<Integer, Integer> neighborColorMap = new TreeMap<>() ;
            int previousColor ;

            //Step2
            // We loop through all of the nodes of this neighbor and we store their color in the HashMap
            nodeP.neighborNodes().forEach(nodeAdj -> {
                int colorFound = (int)nodeAdj.getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE) ;
                if (colorFound != 0) {
                    // If color not in map, set value to 1, else add 1 to current value
                    neighborColorMap.merge(colorFound, 1, Integer::sum) ;
                }
            }) ;

            if (neighborColorMap.size() >= kMax) {
                nodeP.setAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE, 
                    Collections.min(neighborColorMap.keySet(), (int1, int2) -> 
                        Integer.compare(neighborColorMap.get(int1), neighborColorMap.get(int2)))) ;

                int nbConflict = (int)graph.getAttribute(GraphSAE.CONFLICT_ATTRIBUTE);
                graph.setAttribute(GraphSAE.CONFLICT_ATTRIBUTE, nbConflict + 
                    neighborColorMap.get((int)nodeP.getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)));
            }
            else{
                previousColor = 0 ;
                for (Integer key : neighborColorMap.keySet()) {
                    if (previousColor + 1 < key) {
                        break ;
                    }
                    previousColor = key ;
                }
                nodeP.setAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE, previousColor + 1) ;
            }

            //Step3
            nodeP.neighborNodes().forEach(nodeAdj -> {
                HashSet<Integer> buffer = new HashSet<>() ;
                nodeAdj.neighborNodes().forEach(nodeAdj2 -> {
                    if((int)nodeAdj2.getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE) != 0 && nodeAdj2 != nodeP){
                        buffer.add((int)nodeAdj2.getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE)) ;
                    }  
                });

                int nbColor = buffer.size() ;
                if( nbColor!= 0){
                    nodeAdj.setAttribute("DSATUR", nbColor); 
                }
            });
            ListNodes.remove(nodeP);
            

            //Step4
            recursifDSATUR(ListNodes, graph, kMax);
        }

    }



    /**
     * Find the node with the DSAT max
     * @param ListesNodes LinkedList of Graph's Nodes
     * @return max A node
     * 
     * @autor GIRAUD Nila
     */
    private static Node MaxNodeDSATUR( LinkedList<Node> ListesNodes){
        Node max = ListesNodes.getFirst();
        for (int i = 1; i < ListesNodes.size(); i++) {
            if ( (int)ListesNodes.get(i).getAttribute("DSATUR") > (int)max.getAttribute("DSATUR")) {
                max = ListesNodes.get(i) ;
            }
        }
        return max;

    }

    /**
     * Inserts a Node in the linkedList sorted in descending order
     * @param ListNodes LinkedList of Graph's Nodes
     * @param nodeP The Node that we want to insert
     * 
     * @autor GIRAUD Nila
     */

    public static void insertSorted(LinkedList<Node> ListNodes, Node nodeP) {
        int i = 0;
        if(!ListNodes.isEmpty()){
            for(Node node : ListNodes ) {
                if ((int)node.getAttribute("DSATUR") <= (int)nodeP.getAttribute("DSATUR")) {
                    ListNodes.add(i,nodeP);
                    return;
                }
                i++;
            }
        }
        ListNodes.addLast(nodeP);
    }
}
