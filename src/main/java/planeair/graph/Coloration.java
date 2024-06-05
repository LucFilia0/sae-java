package planeair.graph;

//-- Import Java
import java.util.* ;
import java.util.stream.Collectors;

//-- Import GraphStream
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import org.miv.mbox.Test;

//-- Import Exceptions

/**
 * Class handling Coloration algorithms, mostly consists of static methods
 */
public class Coloration {
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
    public static void colorNodesFromSetOfAddableNodes(GraphSAE graph, Map<Integer, HashSet<Node>> colorMap, 
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
     * @return array consisting of 2 values, the color assigned to the node and the number of conflicts it generated
     * 
     * @author Nathan LIEGEON
     */
    public static int[] getLeastConflictingColor(GraphSAE graph, Node node) {
        int[] minConflict = {-1, -1} ;
        int[] currentConflict  = new int[2];
        HashMap<Integer, Integer> conflictCount = new HashMap<>() ;

        node.neighborNodes().forEach(neighbor -> {
            if ((Integer) graph.getNode(neighbor.getId()).getAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE) != 0) {
                conflictCount.merge((Integer)neighbor.getAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE), 1, Integer::sum) ;
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
     * @return number of conflicts caused by the node
     */
    public static int colorWithLeastConflicts(GraphSAE graph, Node node) {
        int[] res = getLeastConflictingColor(graph, node) ;
        node.setAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE, res[0]) ;
        node.setAttribute("ui.class", "color" + res[0]) ;
        return res[1] ;
    }

    /** 
     * Loads every single node from the graph into setOfAddableNodes for future manipulations.
     * While adding them it also looks for the one with the highest degree then returns it at the end.
     * Can return null if no node is loaded into the set.
     *
     * @param graph GraphSAE from which you will load the nodes
     * @param setOfAddableNodes Set in which you will load the nodes
     * @return Node with the highest degree, can be null.
     * 
     * @author Nathan LIEGEON
     * 
     */
    public static Node loadGraphNodesIntoSetOfAddableNodes(GraphSAE graph, Set<Node> setOfAddableNodes) {
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
     * @return array consisting of 2 values : first one being the number of colors used, second being the number of conflicts
     * 
     * @author Nathan LIEGEON
     */
    public static int[] colorGraphRLF(GraphSAE graph) {
        int kMax = -1 ;
        if (graph instanceof TestGraph) {
            TestGraph testGraph = (TestGraph)graph ;
            kMax = testGraph.getKMax() ;
        }

        GraphSAE newGraph = (GraphSAE)Graphs.clone(graph) ;
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
                coloringNode.setAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE, color) ;
                newGraph.removeNode(newGraph.getNode(coloringNode.getId())) ;
            }
            
        }
        
        if (infoTab[0] > kMax) {
            infoTab[0] = kMax ;
        }

        if (newGraph.getNodeCount() != 0) {
            for (Node node : newGraph) {
                node = graph.getNode(node.getId()) ;
                infoTab[1] += colorWithLeastConflicts(graph, node) ;
            }
        }

        return infoTab ;
    }
    
    // WELSH & POWELL

    /**
     * Colors a Graph using the Welsh & Powell algorithm with at most kMax colors
     * If it cannot do so, it will try to minimize conflicts
     * 
     * @param graph Graph getting colored
     * @param kMax Maximum number of colors allowed
     * @return array consisting of 2 values : first one being the number of colors used, second being the number of conflicts
     */
    public static void colorWelshPowell(GraphSAE graph) {
        int kMax = -1 ;
        if (graph instanceof TestGraph) {
            TestGraph testGraph = (TestGraph)graph ;
            kMax = testGraph.getKMax() ;
        }
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
        while (!nodeList.isEmpty() && infoTab[0] < kMax || kMax < 0) {
            nodeSet = new HashSet<>() ;
            infoTab[0]++ ;
            ListIterator<Node> itr = nodeList.listIterator() ;
            while (itr.hasNext()) {
                Node currentNode = itr.next() ;
                if ((int)currentNode.getAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE) == 0 && !nodeSet.contains(currentNode)) {
                    itr.previous() ;
                    itr.remove() ;
                    currentNode.setAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE, infoTab[0]) ;
                    nodeSet.addAll(currentNode.neighborNodes().collect(Collectors.toSet())) ;
                }
            }
        }

        // Conflicts management
        if (graph instanceof TestGraph && !nodeList.isEmpty()) {
            for (Node node : nodeList) {
                infoTab[1] += Coloration.colorWithLeastConflicts(graph, node) ;
            }
            graph.setAttribute(TestGraph.CONFLICT_ATTRIBUTE, infoTab[1]) ;
        }
        graph.setAttribute(GraphSAE.COLOR_ATTRIBUTE, infoTab[0]) ;
    }


    // DSATUR
    /**
     * Give a graph's coloration with less colision we can, use graph saturation principle (degree).
     * 
     * @param graph Graph that will be tested.
     * @return max array consisting of 2 values, 1 : number of colors used, 2 : number of conflicts
     * 
     * @autor GIRAUD Nila
     */
    public static void colorationDsatur(GraphSAE graph) {
        LinkedList<Node> ListNodes = new LinkedList<Node>();
        int kMax = -1 ;
        graph.setAttribute(TestGraph.CONFLICT_ATTRIBUTE,0);
        if (graph instanceof TestGraph) {
            TestGraph testGraph = (TestGraph)graph ;
            kMax = testGraph.getKMax() ;
        }

        //Put all Nodes in a LinkedList
        for (Node node : graph) {
            node.setAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE, 0);
            node.setAttribute("DSATUR", node.getDegree());
            
            insertSorted(ListNodes, node); // Descendent Sort
        }

        // Recursive call
        recursifDSATUR(ListNodes, graph);

        for (Node node : graph){
            node.removeAttribute("DSATUR");
            node.setAttribute("ui.class", "color" + node.getAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE));
        }

        int[] res = {0,0};
        for(Node node : graph){
            if((int)node.getAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE) > res[0]){
                res[0] = (int)node.getAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE);
            }
        }
        graph.setAttribute(TestGraph.COLOR_ATTRIBUTE, res[0]) ;
        res[1] = (int)graph.getAttribute(TestGraph.CONFLICT_ATTRIBUTE) ;
    }

    /**
     * Recursif methode for ColorationDSATUR, take a node and find and finds an optimized color.
     * Stop when there are no more nodes.
     * @param ListNodes LinkedList of Graph's Nodes, with descendent sort (with one less node at each recursive call)
     * @param color Color tab wich give a resum of Adjacents Node's colors
     * 
     * @author GIRAUD Nila
     */

    private static void recursifDSATUR(LinkedList<Node> ListNodes, GraphSAE graph){

        if(!ListNodes.isEmpty()){
            int kMax = -1 ;
            if (graph instanceof TestGraph) {
                TestGraph testGraph = (TestGraph)graph ;
                kMax = testGraph.getKMax() ;
            }
            //Step1
            Node nodeP = MaxNodeDSATUR(ListNodes);

            HashMap<Integer, Integer> color = new HashMap<>() ;
            int minColor = 0 ;
            int currentColor ;

            //Step2
            nodeP.neighborNodes().forEach(nodeAdj -> {
                int colorFound = (int)nodeAdj.getAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE) ;
                if (colorFound != 0) {
                    color.merge(colorFound, 1, Integer::sum) ;
                }
            }) ;

            if (!(graph instanceof TestGraph) && color.keySet().size() != kMax) {
                nodeP.setAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE, 
                    Collections.min(color.keySet(), (int1, int2) -> Integer.compare(color.get(int1), color.get(int2)))) ;
                if (graph instanceof TestGraph) {
                    int nbConflict = (int)graph.getAttribute(TestGraph.CONFLICT_ATTRIBUTE);
                    graph.setAttribute(TestGraph.CONFLICT_ATTRIBUTE, nbConflict + color.get((int)nodeP.getAttribute(GraphSAE.COLOR_ATTRIBUTE)));
                }
            }
            else{
                for (Integer key : color.keySet()) {
                    currentColor = color.get(key) ;
                    if (currentColor == 0) {
                        if (!color.containsKey(minColor) || minColor > currentColor) {
                            minColor = currentColor ;
                        }
                    }
                }
                nodeP.setAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE, minColor) ;
            }

            //Step3
            nodeP.neighborNodes().forEach(nodeAdj -> {
                HashMap<Integer, Integer> buffer = new HashMap<>() ;

                for(Node nodeAdj2 : nodeAdj.neighborNodes().collect(Collectors.toSet())){
                    if((int)nodeAdj2.getAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE) != 0 && nodeAdj2 != nodeP){
                        buffer.put((int)nodeAdj2.getAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE), 1) ;
                    }    
                }
                int nbColor = buffer.values().size() ;
                if( nbColor!= 0){
                    nodeAdj.setAttribute("DSATUR", nbColor); 
                }
            });
            ListNodes.remove(nodeP);
            

            //Step4
            recursifDSATUR(ListNodes, graph);
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
    public static int testColorationGraph(GraphSAE graph) {
        int nbProblems = 0 ;
        for (Node node : graph) {
            for (Node neighbor : node.neighborNodes().collect(Collectors.toSet())) {
                if (node.getAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE) == neighbor.getAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE)) {
                    nbProblems++ ;
                    System.out.println("Probleme entre " + node + " et " + neighbor) ;
                }
            }
        }   

        return nbProblems ;
    }

    /**
     * Reads the color attribute of the nodes to give them an actual color on the display by editing its stylesheet
     * 
     * @param graph graph getting colored
     * @param nbColor number of colors the graph has
     */
    public static void setGraphStyle(GraphSAE graph, int nbColor) {
        StringBuffer stylesheet = new StringBuffer("node {size-mode : dyn-size ; size : " + DEFAULT_NODE_SIZE + " ; }\n") ;

        if (nbColor > 0) {
            for (Node coloringNode : graph) {
                Integer color = (Integer)coloringNode.getAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE) ;
                coloringNode.setAttribute("ui.class", "color" + color) ;
            }
            
            // FFFFFF in decimal (i asked Google 👍)
            int maxHexValue = 16777215 ;
            
            // Hexadecimal value used for the color stored as an int
            int currentHexValue ;

            for (int i = 0 ; i < nbColor ; i++) {
                currentHexValue = (maxHexValue/(nbColor))*i ;
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
     * Removes all attributes linked to the coloration of this graph
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
                    colorationDsatur(graph) ;
                    break ;
                case WELSH_POWELL :
                    colorWelshPowell(graph) ;
                    break ;
                case RLF :
                    colorGraphRLF(graph) ;
                    break ;
                default :
        }
    }
}
