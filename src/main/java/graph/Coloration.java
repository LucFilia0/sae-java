package graph;

//-- Import Java
import java.io.File;
import java.io.FileNotFoundException;
import java.util.* ;
import java.util.stream.Collector;
import java.util.stream.Collectors;

//-- Import GraphStream
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.stylesheet.Color;
import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.algorithm.ConnectedComponents.ConnectedComponent;


//-- Import Exceptions

import exceptions.InvalidEntryException;
import exceptions.InvalidFileFormatException;

/**
 * Class handling Coloration algorithms, mostly consists of static methods
 */
public class Coloration {

    
    // RLF (Recursive Larget First)


    /** 
     * Selects the next node to add to the set of colored Nodes by getting the one with the highest number of common
     * neighbors with the first node that was added to this color's set.
     * 
     * @param setOfNeighborsOfFirstNode Contains the neighbors of the first node added to the color currently worked on
     * @param setOfAddableNodes Contains all the nodes that could be colored with the color currently worked on
     * @return Node which is : 
     * 1. the one with the highest number of common neighbors with the first node of the color 
     * 2. in setOfAddableNodes
     * 
     * @author Nathan LIEGEON
     */
    public static Node selectNextNodeToAdd(Set<Node> setOfNeighborsOfFirstNode, Set<Node> setOfAddableNodes) {
        HashMap<Node, Integer> nodeMap = new HashMap<>() ;

        for (Node nodeInSet : setOfAddableNodes) {
            nodeMap.put(nodeInSet, 0) ;
        }

        for (Node node : setOfNeighborsOfFirstNode) {
            node.neighborNodes().forEach(neighborOfNode -> {
                if (setOfAddableNodes.contains(neighborOfNode)) {
                    // If not in map => Sets value to 1, else adds one to the value
                    nodeMap.merge(neighborOfNode, 1, Integer::sum) ;
                }
            }) ;
        }

        Node max = null ;

        for (Node key : nodeMap.keySet()) {
            if (max == null || nodeMap.get(max) > nodeMap.get(key)) {
                max = key ;
            }
        }

        return max ;
    }

    /**
     * Colors all the nodes in setOfAddableNodes until it is empty
     *
     * @param graph graph you are trying to color
     * @param colorMap map storing for each color (Integer key) 
     * the set of all nodes that have that color (HashSet value)
     * @param color integer the color currently being worked on
     * @param setOfNeighborsOfFirstNode Contains the neighbors of the first node added to the color currently being worked on
     * @param setOfAddableNodes Contains all the nodes that could be colored with the color currently being worked on
     * 
     * @author Nathan LIEGEON
     */
    public static void colorNodesFromSetOfAddableNodes(Graph graph, Map<Integer, HashSet<Node>> colorMap, 
    Integer color, Set<Node> setOfNeighborsOfFirstNode, Set<Node> setOfAddableNodes) {
        Node nextNode = null ;
        Node tempNode ;
        while (!setOfAddableNodes.isEmpty()) {
            nextNode = selectNextNodeToAdd(setOfNeighborsOfFirstNode, setOfAddableNodes) ;
            if (nextNode != null) {
                tempNode = graph.getNode(nextNode.getId()) ;
                colorMap.get(color).add(tempNode) ;
                
                setOfAddableNodes.remove(nextNode) ;
                setOfAddableNodes.removeAll(nextNode.neighborNodes().collect(Collectors.toSet())) ;
            }
        }
    }
    
    /**
     * Colors a node by minimizing conflicts (2 nodes with the same color touching each other)
     * 
     * @param graph graph you are trying to color
     * @param node node you are trying to color 
     * @param colorAttribute key of the attribute used for coloring
     * @return array consisting of 2 values, the color assigned to the node and the number of conflicts it generated
     * 
     * @author Nathan LIEGEON
     */
    public static int[] getLeastConflictingColor(Graph graph, Node node, String colorAttribute) {
        int[] minConflict = {-1, -1} ;
        int[] currentConflict  = new int[2];
        HashMap<Integer, Integer> conflictCount = new HashMap<>() ;

        node.neighborNodes().forEach(neighbor -> {
            if ((Integer) graph.getNode(neighbor.getId()).getAttribute(colorAttribute) != 0) {
                conflictCount.merge((Integer)neighbor.getAttribute(colorAttribute), 1, Integer::sum) ;
            }
        }) ;

        for (Integer color : conflictCount.keySet()) {
            currentConflict[0] = color ;
            currentConflict[1] = conflictCount.get(color) ;
            if (minConflict[0] == -1 || minConflict[1] > currentConflict[1]) {
                minConflict = currentConflict ;
            }

        }

        return minConflict ;
    }

    /**
     * Applies the color returned by {@link getLeastConflictingColor} and returns the number of conflicts it generated
     * 
     * @param graph Graph we are trying to color
     * @param node Node currently being colored
     * @param colorAttribute key of the attribute used for coloring
     * @return number of conflicts caused by the node
     */
    public static int colorWithLeastConflicts(Graph graph, Node node, String colorAttribute) {
        int[] res = getLeastConflictingColor(graph, node, colorAttribute) ;
        node.setAttribute(colorAttribute, res[0]) ;
        node.setAttribute("ui.class", "color" + res[0]) ;
        return res[1] ;
    }

    /** 
     * Loads every single node from the graph into setOfAddableNodes for future manipulations.
     * While adding them it also looks for the one with the highest degree then returns it at the end.
     * Can return null if no node is loaded into the set.
     *
     * @param graph Graph from which you will load the nodes
     * @param setOfAddableNodes Set in which you will load the nodes
     * @return Node with the highest degree, can be null.
     * 
     * @author Nathan LIEGEON
     * 
     */
    public static Node loadGraphNodesIntoSetOfAddableNodes(Graph graph, Set<Node> setOfAddableNodes) {
        Node currentNode = null ;
        for (Node node : graph) {
            if (currentNode == null) {
                currentNode = node ;
            }

            else {
                if (currentNode.getDegree() < node.getDegree()) {
                    currentNode = node ;
                }
            }
            setOfAddableNodes.add(node) ;
        }

        return currentNode ;
    }
    
    /** 
     * Colors the graph using the RLF (Recursive Larget First) Algorithm
     * Implemented iteratively contrary to its name, could be modified later.
     * If the number of colors used reaches kMax, a different algorithm will be used to minimize conflicts.
     * 
     * @param graph Graph which will be colored
     * @param colorAttribute 
     * @return array consisting of 2 values : first one being the number of colors used, second being the number of conflicts
     * 
     * @author Nathan LIEGEON
     */
    public static int[] colorGraphRLF(Graph graph, String colorAttribute, int kMax) {

        Graph newGraph = Graphs.clone(graph) ;
        HashMap<Integer, HashSet<Node>> colorMap = new HashMap<>() ;
        HashSet<Node> setOfAddableNodes ;
        HashSet<Node> setOfNeighborsOfFirstNode ;
        Node firstNodeOfThisColor ;
        int color = 0 ;
        int[] infoTab = {0, 0} ;

        while (newGraph.getNodeCount() != 0 && color < kMax) {
            color++ ;
            infoTab[0]++ ;
            
            firstNodeOfThisColor = null ;
            colorMap.put(color, new HashSet<>()) ;
            setOfAddableNodes = new HashSet<>() ;

            firstNodeOfThisColor = loadGraphNodesIntoSetOfAddableNodes(newGraph, setOfAddableNodes) ;
            colorMap.get(color).add(graph.getNode(firstNodeOfThisColor.getId())) ;

            setOfNeighborsOfFirstNode = (HashSet<Node>) firstNodeOfThisColor.neighborNodes().collect(Collectors.toSet()) ;
            setOfAddableNodes.remove(firstNodeOfThisColor) ;
            setOfAddableNodes.removeAll(setOfNeighborsOfFirstNode) ;
            colorNodesFromSetOfAddableNodes(graph, colorMap, color, setOfNeighborsOfFirstNode, setOfAddableNodes) ;

            //Adds the colors to the real graph
            for (Node coloringNode : colorMap.get(color)) { 
                coloringNode.setAttribute(colorAttribute, color) ;
                newGraph.removeNode(newGraph.getNode(coloringNode.getId())) ;
            }
            
        }
        
        if (infoTab[0] > kMax) {
            infoTab[0] = kMax ;
        }

        if (newGraph.getNodeCount() != 0) {
            for (Node node : newGraph) {
                node = graph.getNode(node.getId()) ;
                infoTab[1] = infoTab[1] + colorWithLeastConflicts(graph, node, colorAttribute) ;
            }
        }

        return infoTab ;
    }

    
    // TWO-COLORING


    /**
     * Colors the graph with only 2 colors
     * The graph has to be 2-Colorable, else the algorithm will stop once two adjacent nodes have the same color.
     * 
     * @param node starting Node that will be colored
     * @param color color that will be used
     * @return boolean true if the coloration succeeded, false if it didn't
     * 
     * @author Nathan LIEGEON
     */
    public static boolean twoColorGraph(Graph graph) {
        ConnectedComponents graphComponents = new ConnectedComponents(graph) ;
        graphComponents.compute() ;
        boolean bool = true ;

        // Couldn't find a better way to isolate a random Node in every Connected Component
        //Might be highly unoptimized, need to look more into it
        for (ConnectedComponent thisComponent : graphComponents) {
            if (bool) {
                bool = recursiveTwoColoringNode(thisComponent.getNodeSet().iterator().next(), 1, true) ;
            }
        }
        
        return bool ;
    }

    /**
     * Recursively 2-colors startingNode's connected component by coloring startingNode with currentColor then calling the function on all of startingNodes' neighbors.
     * 
     * @param startingNode Node that will be colored
     * @param color Color that will be applied (1 or 2)
     * @param bool true if there is no Problem (Node having neighbor with same color), else false
     * @return boolean true if the coloration encountered no problem, else false
     * 
     * @author Nathan LIEGEON
     */
    public static boolean recursiveTwoColoringNode(Node startingNode, Integer initialColor, boolean bool) {
        if (bool) {
            
            int nodeColor = (int) startingNode.getAttribute("color") ;
            if (nodeColor == 0) {
                startingNode.setAttribute("color", initialColor) ;
                startingNode.setAttribute("ui.class", "color" + initialColor) ;
                
                for (Node neighbor : startingNode.neighborNodes().collect(Collectors.toSet())) {
                    if ((int) neighbor.getAttribute("color") == initialColor) {
                        bool = false ;
                    }
                    else {
                        bool = recursiveTwoColoringNode(neighbor, initialColor%2 + 1, bool) ;
                    }
                }
            }
        }

        return bool ;
    }
    
    // WELSH & POWELL

    /**
     * Colors a Graph using the Welsh & Powell algorithm with at most kMax colors
     * If it cannot do so, it will try to minimize conflicts
     * 
     * @param graph Graph getting colored
     * @param colorAttribute Attribute used to store colors in the graph
     * @param kMax Maximum number of colors allowed
     * @return array consisting of 2 values : first one being the number of colors used, second being the number of conflicts
     */
    public static int[] colorWelshPowell(Graph graph, String colorAttribute, int kMax) {
        int[] infoTab = {0,0} ;
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
        while (!nodeList.isEmpty() && infoTab[0] < kMax) {
            nodeSet = new HashSet<>() ;
            infoTab[0]++ ;
            ListIterator<Node> itr = nodeList.listIterator() ;
            while (itr.hasNext()) {
                Node currentNode = itr.next() ;
                if ((int)currentNode.getAttribute(colorAttribute) == 0 && !nodeSet.contains(currentNode)) {
                    itr.previous() ;
                    itr.remove() ;
                    currentNode.setAttribute(colorAttribute, infoTab[0]) ;
                    nodeSet.addAll(currentNode.neighborNodes().collect(Collectors.toSet())) ;
                }
            }
        }

        // Conflicts management
        if (!nodeList.isEmpty()) {
            for (Node node : nodeList) {
                infoTab[1] += Coloration.colorWithLeastConflicts(graph, node, colorAttribute) ;
            }
        }
        return infoTab ;
    }


    // DSATUR


    /**
     * Give a graph's coloration with less colision we can, use graph saturation principle (degree).
     * 
     * @param graph Graph that will be tested.
     * @param Kmax Max color we can use.
     * @param attributColor the name of the attribut have a graph's dependance
     * @return max array consisting of 2 values, 1 : number of colors used, 2 : number of conflicts
     * 
     * @autor GIRAUD Nila
     */
    public static int[] ColorationDsatur(Graph graph, int Kmax, String attributColor){
        LinkedList<Node> ListNodes = new LinkedList<Node>();
        //System.out.println("Salut");
        graph.setAttribute("nbConflit",0);

        //Put all Nodes in a LinkedList
        for (Node node : graph) {
            node.setAttribute(attributColor, 0);
            node.setAttribute("DSATUR", node.getDegree());
            
            insertSorted(ListNodes, node); // Descendent Sort
        }
        /*
        for (Node node : ListNodes){
            System.out.println(node.getAttribute("DSATUR"));
            System.out.println(node.getAttribute(attributColor));
            System.out.println("\n");
        }
        */

        //Color 
        int[] color = new int[Kmax];

        /*  Ascendent Sort
        Collections.sort(ListNodes, new Comparator<Node>() {
            
            @Override
            public int compare(Node n1, Node n2) {
                if (n1.getAttribute("DSATUR") != n2.getAttribute("DSATUR")) {
                    return Integer.compare((int)n1.getAttribute("DSATUR"),(int)n2.getAttribute("DSATUR"));
                }
                return Integer.compare((int)n1.getAttribute("id") ,(int)n2.getAttribute("id"));
            }
        });

        // Descendent Sort
        Collections.sort(ListNodes, Collections.reverseOrder());*/

        recursifDSATUR(ListNodes, color, graph, attributColor);

        for (Node node : graph){

            node.removeAttribute("DSATUR");
            node.setAttribute("ui.class", attributColor + node.getAttribute(attributColor));
            /* 
            System.out.println(node.getAttribute("DSATUR"));
            System.out.println(node.getAttribute(attributColor));
            System.out.println(node.getAttribute("ui.class"));
            System.out.println("\n");
            */
        }

        int[] res = {0,0};
        for(Node node : graph){
            if((int)node.getAttribute(attributColor) > res[0]){
                res[0] = (int)node.getAttribute(attributColor);
            }
        }

        res[1] = (int)graph.getAttribute("nbConflit");

        return res;
    }

    /**
     * Recursif methode for ColorationDSATUR, take a node and find and finds an optimized color.
     * Stop when there are no more nodes.
     * @param ListNodes LinkedList of Graph's Nodes, with descendent sort (with one less node at each recursive call)
     * @param attributColor the name of the attribut have a graph's dependance
     * @param color Color tab wich give a resum of Adjacents Node's colors
     * 
     * @author GIRAUD Nila
     */

    private static void recursifDSATUR(LinkedList<Node> ListNodes, int[] color, Graph graph, String attributColor){

         if(!ListNodes.isEmpty()){
            /*
            for (Node node : ListNodes){
                System.out.println("\n" + node.getAttribute("DSATUR"));
                System.out.println(node.getAttribute(attributColor));
                System.out.println(node);
                
                System.out.println("\n");
            }
            System.out.println("Salut");
            */
            
            //Step1
            Node nodeP = MaxNodeDSATUR(ListNodes);
            //System.out.println(nodeP + " | " + nodeP.getAttribute("DSATUR") + " | " + nodeP.getAttribute(attributColor));
 
             //Initialisation of color tab
            initColor(color);

            //Step2
            for(Node nodeAdj : nodeP.neighborNodes().collect(Collectors.toSet()) ){
 
                 if((int)nodeAdj.getAttribute(attributColor) != 0){
                     color[(int)nodeAdj.getAttribute(attributColor) - 1] ++;
                 }
             }

            int j = 0;

            while(j!= (color.length) && color[j] != 0 ){ j++; };

            if (j == (color.length) ){
                nodeP.setAttribute(attributColor, minGiveColorTab(color));
                //il peu etre judicieux de rajouter un attribut conflit au avion qui risque de se percuter
                int nbConflit = (int)graph.getAttribute("nbConflit");
                graph.setAttribute("nbConflit", nbConflit + color[(int)nodeP.getAttribute(attributColor)-1]);
            }
            else{ nodeP.setAttribute(attributColor, j+1); };
            //System.out.println("color :" + nodeP.getAttribute(attributColor));

            //Step3
            for(Node nodeAdj : nodeP.neighborNodes().collect(Collectors.toSet()) ){
                initColor(color);

                for(Node nodeAdj2 : nodeAdj.neighborNodes().collect(Collectors.toSet())){
                    if((int)nodeAdj2.getAttribute(attributColor) != 0 && nodeAdj2 != nodeP){
                        color[(int)nodeAdj2.getAttribute(attributColor) - 1] = 1;
                    }    
                }
                int nbColor = nbColorAdj(color);
                if( nbColor!= 0){
                    nodeAdj.setAttribute("DSATUR", nbColor); }
            }
            /*
            System.out.println(nodeP + " | " + nodeP.getAttribute("DSATUR") +  " | " + nodeP.getAttribute(attributColor));

            System.out.println(nodeP + " | " + nodeP.getAttribute("DSATUR") +  " | " + nodeP.getAttribute(attributColor));
            */
            ListNodes.remove(nodeP);
            

            //Step4
            recursifDSATUR(ListNodes,color, graph,attributColor);
         }

    }

    /**
     * Give the less use color of NodeP's adjacents nodes
     * @param color Color tab wich give a resum of Adjacents Node's colors
     * @return min Least used color
     * 
     * @autor GIRAUD Nila
     */
    private static int minGiveColorTab(int color[] ){
        int min = 0;
        for(int i = 1; i < color.length ; i++){
            if(color[i] < color[min] ){
                min = i;
            }
        }

        return min + 1;

    }

    /**
     * Initialise the Color tab at 0
     * @param color Color tab wich give a resum of Adjacents Node's colors
     * 
     * @autor GIRAUD Nila
     */
    private static void initColor(int[] color){
        for(int j = 0; j < color.length; j++){
            color [j] = 0;   
    }
}

    private static int nbColorAdj(int[] color){
        int nb = 0;
        for(int i = 0; i< color.length; i++){
            if(color[i] != 0){
                nb ++;
            }
        }

        return nb;

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
     * Insert a Node in a linkedList with in a descendent order
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

    
    // UTILITY

    
    /**
     * Checks if the coloration worked.
     * 
     * @param graph Graph that will be tested.
     * @param String key of the color attribute used
     * @return int number of nodes which are adjacent to another node with the same color.
     * 
     * @author Nathan LIEGEON
     */
    public static int testColorationGraph(Graph graph, String colorAttribute) {
        int nbProblems = 0 ;
        for (Node node : graph) {
            for (Node neighbor : node.neighborNodes().collect(Collectors.toSet())) {
                if (node.getAttribute(colorAttribute) == neighbor.getAttribute(colorAttribute)) {
                    nbProblems++ ;
                    System.out.println("Probleme entre " + node + " et " + neighbor) ;
                }
            }
        }   

        return nbProblems ;
    }

    /**
     * Reads the colorAttribute of the nodes to give them an actual color on the display by editing its stylesheet
     * 
     * @param graph graph getting colored
     * @param nbColor number of colors the graph has
     * @param colorAttribute key of the attribute handling colors
     */
    public static void setGraphStyle(Graph graph, int nbColor, String colorAttribute) {
        StringBuffer stylesheet = new StringBuffer("node {size-mode : dyn-size ; size : 20px ; }") ;

        Color[] colorTab = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY
            , Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW} ;

        for (Node coloringNode : graph) {
            Integer color = (Integer)coloringNode.getAttribute(colorAttribute) ;
            coloringNode.setAttribute("ui.class", "color" + color) ;
        }
        
        // Scuffed way to show colors on the graph
        for (int i = 0 ; i < nbColor ; i++) {
            Color currentColor = colorTab[i % colorTab.length] ;
            String str = "rgb(" + currentColor.getRed() + ',' + currentColor.getGreen() + ',' + currentColor.getBlue() + ')' ;
            stylesheet.append("node.color" + (i+1) + "{fill-color : " + str + " ; }\n") ;
        }

        graph.setAttribute("ui.stylesheet", stylesheet.toString()) ;
    }
}
