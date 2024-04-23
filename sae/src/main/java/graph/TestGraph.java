package graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.* ; 
import java.util.stream.*;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.algorithm.*;
import org.graphstream.algorithm.ConnectedComponents.ConnectedComponent;

import java.util.ListIterator;

// Exceptions
import exceptions.InvalidFileFormatException;

public class TestGraph {

    public static void main(String args[]) {
        System.setProperty("org.graphstream.ui", "swing");

        Graph testGraph = new SingleGraph("testGraph");
        File testGraphFile = new File("DataTest/graph-test2.txt") ;
        
        try {
            importTestGraph(testGraph, testGraphFile) ;
            if ((int) testGraph.getAttribute("kMax") != 2) {
                System.out.println(" K-Max : " + testGraph.getAttribute("kMax") + " | Nombre de couleurs : " + ColorationDsatur(testGraph, (int)testGraph.getAttribute("kMax"))) ;
            }
            else {
                System.out.println("Worked ? : " + ColorationDsatur(testGraph, 2) ) ; //twoColorGraph(testGraph)
            }

            testGraph.setAttribute("ui.stylesheet", "node {size : 25px ; fill-color : gray ;} node.color1 {fill-color : red ;}" 
            + " node.color2 {fill-color : blue ;}" 
            + " node.color3 {fill-color : green ;}"
            + " node.color4 {fill-color : orange ;}"
            + " node.color5 {fill-color : pink ;}"
            + " node.color6 {fill-color : purple ;}"
            ) ;
            testGraph.display();
        }catch(FileNotFoundException ex) {
            System.err.println(ex);
        }catch(NumberFormatException ex) {
            System.err.println(ex);
        }catch(InvalidFileFormatException ex) {
            System.err.println(ex);
        }
    }

    /** 
     * Selects the next node to add to the set of colored Nodes by getting the one with the highest number of common
     * neighbors with the first node that was added to this color's set.
     * 
     * @param setOfNeighborsOfFirstNode Contains the neighbors of the first node added to the color currently worked on
     * @param setOfAddableNodes Contains all the nodes that could be colored with the color currently worked on
     * @return Node The node that has the highest number of common neighbors with the first node that was added and which is in setOfAddableNodes
     * 
     * @author Nathan LIEGEON
     */
    public static Node selectNextNodeToAdd(Set<Node> setOfNeighborsOfFirstNode, Set<Node> setOfAddableNodes) {
        HashMap<Node, Integer> nodeMap = new HashMap<>() ;

        for (Node nodeInList : setOfAddableNodes) {
            nodeMap.put(nodeInList, 0) ;
        }

        for (Node node : setOfNeighborsOfFirstNode) {
            for (Node neighborOfNode : node.neighborNodes().collect(Collectors.toSet())) {
                if (setOfAddableNodes.contains(neighborOfNode)) {
                    // If not in map => Sets value to 1, else adds one to the value
                    nodeMap.merge(neighborOfNode, 1, Integer::sum) ;
                }
            }
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
     * Adds nodes the current color's set until there is no node left which can be added
     *
     * @param graph Graph from which you will add the nodes
     * @param colorMap Hashmap storing for each color (Integer key) 
     * the set of all nodes that have that color (HashSet<Node> value)
     * @param Integer color The color currently being worked on
     * @param setOfNeighborsOfFirstNode Contains the neighbors of the first node added to the color currently worked on
     * @param setOfAddableNodes Contains all the nodes that could be colored with the color currently worked on
     * 
     * @author Nathan LIEGEON
     */
    public static void addNodesToSetOfAddableNodes(Graph graph, Map<Integer, HashSet<Node>> colorMap, 
    Integer color, Set<Node> setOfNeighborsOfFirstNode, Set<Node> setOfAddableNodes) {
        Node nextNode = null ;
        while (!setOfAddableNodes.isEmpty()) {
            nextNode = selectNextNodeToAdd(setOfNeighborsOfFirstNode, setOfAddableNodes) ;
            if (nextNode != null) {
                colorMap.get(color).add(graph.getNode(nextNode.getId())) ;
                setOfAddableNodes.remove(nextNode) ;
                setOfAddableNodes.removeAll(nextNode.neighborNodes().collect(Collectors.toSet())) ;
            }
        }
    }

    
    /** 
     * Loads every single node from the graph into setOfAddableNodes for future manipulations.
     * While adding them it also looks for the one with the highest degree then returns it at the end.
     * Can return null if no node is loaded into the list.
     *
     * @param graph Graph from which you will load the nodes
     * @param setOfAddableNodes The set in which you will load all of graph's nodes
     * @return Node returns the node with the highest degree, can be null.
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
     * 
     * @param graph Graph which will be colored
     * @return Number of colors used
     * 
     * @author Nathan LIEGEON
     */
    public static int colorGraphRLF(Graph graph) {

        Graph newGraph = Graphs.clone(graph) ;
        HashMap<Integer, HashSet<Node>> colorMap = new HashMap<>() ;
        HashSet<Node> setOfAddableNodes ;
        HashSet<Node> setOfNeighborsOfFirstNode ;
        Node firstNodeOfThisColor ;
        
        int color = 0 ;

        while (newGraph.getNodeCount() != 0) {
            color++ ;
            firstNodeOfThisColor = null ;
            colorMap.put(color, new HashSet<>()) ;
            setOfAddableNodes = new HashSet<>() ;

            firstNodeOfThisColor = loadGraphNodesIntoSetOfAddableNodes(newGraph, setOfAddableNodes) ;
            colorMap.get(color).add(graph.getNode(firstNodeOfThisColor.getId())) ;

            setOfNeighborsOfFirstNode = (HashSet<Node>) firstNodeOfThisColor.neighborNodes().collect(Collectors.toSet()) ;
            setOfAddableNodes.remove(firstNodeOfThisColor) ;
            setOfAddableNodes.removeAll(setOfNeighborsOfFirstNode) ;
            addNodesToSetOfAddableNodes(graph, colorMap, color, setOfNeighborsOfFirstNode, setOfAddableNodes) ;

            //Adds the colors to the real graph
            for (Node coloringNode : colorMap.get(color)) {
                coloringNode.setAttribute("color", color) ;
                coloringNode.setAttribute("ui.class", "color" + color) ;
                newGraph.removeNode(newGraph.getNode(coloringNode.getId())) ;
            }
            
        }


        return color ;
    }

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

    /**
     * Requires nodes to have the attribute "color".
     * Checks if the coloration worked.
     * 
     * @param graph Graph that will be tested.
     * @return int number of nodes which are adjacent to another node with the same color.
     * 
     * @author Nathan LIEGEON
     */
    public static int testColorationGraph(Graph graph) {
        int nbProblems = 0 ;
        for (Node node : graph) {
            for (Node neighbor : node.neighborNodes().collect(Collectors.toSet())) {
                if (node.getAttribute("color") == neighbor.getAttribute("color")) {
                    nbProblems++ ;
                    System.out.println("Probleme entre " + node + " et " + neighbor) ;
                }
            }
        }   

        return nbProblems ;
    }

    public static void importTestGraph(Graph graph, File file) throws FileNotFoundException, NumberFormatException, InvalidFileFormatException {

        TestGraphInfo testGraphInfo = new TestGraphInfo();
        Scanner lineScanner = null;
        try {
            lineScanner = new Scanner(file);
            setTestGraphInfos(lineScanner, testGraphInfo);
            System.out.println("K-max : " + testGraphInfo.getKMax() + " | nbNodes : " + testGraphInfo.getNbNodes());
            graph.setAttribute("kMax", testGraphInfo.getKMax()) ;
            setTestGraphNodes(graph, testGraphInfo.getNbNodes());
            System.out.println("Nodes importation OK");
            setTestGraphEdges(graph, lineScanner, testGraphInfo.getNbNodes());
            System.out.println("Edges importation OK");
        }catch(FileNotFoundException fnfe) {
            throw fnfe;
        }catch(NumberFormatException nfe) {
            throw nfe;
        }catch(InvalidFileFormatException iffe) {
            throw iffe;
        }
        lineScanner.close();
    }

    private static void setTestGraphInfos(Scanner lineScanner, TestGraphInfo testGraphInfo) throws FileNotFoundException, NumberFormatException, InvalidFileFormatException{
        String _kMax = "", _nbNodes = "";
        Scanner scan_kMax = null, scan_nbNodes = null;

        // Ligne 1 : k-max
        if(lineScanner.hasNextLine()) {
            _kMax = lineScanner.nextLine();
            scan_kMax = new Scanner(_kMax);
            if(scan_kMax.hasNext()) {
                try {
                    testGraphInfo.setKMax(Integer.parseInt(scan_kMax.next()));
                }catch(NumberFormatException ex) {
                    lineScanner.close();
                    scan_kMax.close();
                    throw ex;
                }
                if(scan_kMax.hasNext()) {
                    lineScanner.close();
                    scan_kMax.close();
                    throw new InvalidFileFormatException();
                }
            }else {
                lineScanner.close();
                scan_kMax.close();
                throw new InvalidFileFormatException();
            }
            scan_kMax.close();
        }else {
            lineScanner.close();
            throw new InvalidFileFormatException();
        }

        // Ligne 2 : Nombre de sommets
        if(lineScanner.hasNextLine()) {
            _nbNodes = lineScanner.nextLine();
            scan_nbNodes = new Scanner(_nbNodes);
            if(scan_nbNodes.hasNext()) {
                try {
                    testGraphInfo.setNbNodes(Integer.parseInt(scan_nbNodes.next()));
                }catch(NumberFormatException ex) {
                    lineScanner.close();
                    scan_nbNodes.close();
                    throw ex;
                }
                if(scan_nbNodes.hasNext()) {
                    lineScanner.close();
                    scan_nbNodes.close();
                    throw new InvalidFileFormatException();
                }
            }else {
                lineScanner.close();
                scan_nbNodes.close();
                throw new InvalidFileFormatException();
            }
            scan_nbNodes.close();
        }else {
            lineScanner.close();
            throw new InvalidFileFormatException();
        }
    }

    private static void setTestGraphNodes(Graph graph, int nbNodes) throws InvalidFileFormatException {
        if(nbNodes < 0) {
            throw new InvalidFileFormatException();
        }
        String id = "";

        for(int i=1; i<=nbNodes; ++i) {
            id = String.valueOf(i);
            try {
                Node n = graph.addNode(id);
                n.setAttribute("ui.label", id);
                n.setAttribute("color", 0) ;
            }catch(IdAlreadyInUseException iaiue) { // C'est pas censé arriver, mais on sait jamais, si on réutilise ailleurs...
                System.err.println(id + " est deja utilise");
            }
        }
    }

    @SuppressWarnings("resource")
    private static void setTestGraphEdges(Graph graph, Scanner lineScanner, int nbNodes) throws FileNotFoundException, InvalidFileFormatException, NumberFormatException {
        
        int lineId = 2;
        
        String _line = "", _nodeA = "", _nodeB = "";
        int nodeA = 0, nodeB = 0;

        Scanner nodeScanner = null;

        while(lineScanner.hasNextLine()) {
            ++lineId;
            _line = lineScanner.nextLine();
            nodeScanner = new Scanner(_line);
            // Get node A
            if(nodeScanner.hasNext()) {
                _nodeA = nodeScanner.next();
            }else {
                nodeScanner.close();
                throw new InvalidFileFormatException(lineId);
            }
            // Get node B
            if(nodeScanner.hasNext()) {
                _nodeB = nodeScanner.next();
            }else {
                nodeScanner.close();
                throw new InvalidFileFormatException(lineId);
            }
            // Vérif nombre en plus sur la ligne
            if(nodeScanner.hasNext()) {
                System.err.println("Ligne " + lineId + " du fichier source : Plus de deux sommets indiques");
            }

            try {
                nodeA = Integer.parseInt(_nodeA);
                nodeB = Integer.parseInt(_nodeB);
            }catch(NumberFormatException ex) {
                nodeScanner.close();
                throw ex;
            }

            if(nodeA <= nbNodes && nodeA > 0 && nodeB > 0 && nodeB <=nbNodes) {
                try {
                    graph.addEdge(_nodeA + "-" + _nodeB, _nodeA, _nodeB);
                }catch(IdAlreadyInUseException iaiue) {
                    System.err.println(_nodeB + "-" + _nodeA + " deja utilise : erreur ligne " + lineId);
                }catch(ElementNotFoundException enfe) {
                    System.err.println("Element introuvable : ligne " + lineId);
                }catch(EdgeRejectedException ere) {
                    System.err.println("Impossible de rajouter l'arete : ligne " + lineId);
                }
            }else {
                nodeScanner.close();
                throw new InvalidFileFormatException(lineId);
            }
            nodeScanner.close();
        }
        nodeScanner.close();
    }

    /**
     * Give a graph's coloration with less colision we can, use graph saturation principle (degree).
     * 
     * @param graph Graph that will be tested.
     * @param Kmax Max color we can use.
     * @return max Number of color we use for it.
     * 
     * @autor GIRAUD Nila
     */
    private static int ColorationDsatur(Graph graph, int Kmax){
        LinkedList<Node> ListNodes = new LinkedList<Node>();
        System.out.println("Salut");

        //Put all Nodes in a LinkedList
        for (Node node : graph) {
            node.setAttribute("color", 0);
            node.setAttribute("DSATUR", node.getDegree());
            
            insertSorted(ListNodes, node); // Descendent Sort
        }

        for (Node node : ListNodes){
            System.out.println(node.getAttribute("DSATUR"));
            System.out.println(node.getAttribute("color"));
            System.out.println("\n");
        }

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

        recursifDSATUR(ListNodes, color);

        for (Node node : graph){

            node.removeAttribute("DSATUR");
            node.setAttribute("ui.class", "color" + node.getAttribute("color"));

            System.out.println(node.getAttribute("DSATUR"));
            System.out.println(node.getAttribute("color"));
            System.out.println(node.getAttribute("ui.class"));
            System.out.println("\n");
        }

        int max = 0;
        for(Node node : graph){
            if((int)node.getAttribute("color") > max){
                max = (int)node.getAttribute("color");
            }
        }

        System.out.println(nbConflit);

        return max;
    }

    private static int nbConflit = 0;


    /**
     * Recursif methode for ColorationDSATUR, take a node and find and finds an optimized color.
     * Stop when there are no more nodes.
     * @param ListNodes LinkedList of Graph's Nodes, with descendent sort (with one less node at each recursive call)
     * @param color Color tab wich give a resum of Adjacents Node's colors
     * 
     * @author GIRAUD Nila
     */

    private static void recursifDSATUR(LinkedList<Node> ListNodes, int[] color){

         if(!ListNodes.isEmpty()){

            for (Node node : ListNodes){
                System.out.println("\n" + node.getAttribute("DSATUR"));
                System.out.println(node.getAttribute("color"));
                System.out.println(node);
                
                System.out.println("\n");
            }
            System.out.println("Salut");
            
            //Step1
            Node nodeP = MaxNodeDSATUR(ListNodes);
            System.out.println(nodeP + " | " + nodeP.getAttribute("DSATUR") + " | " + nodeP.getAttribute("color"));
 
             //Initialisation of color tab
            initColor(color);

            //Step2
            for(Node nodeAdj : nodeP.neighborNodes().collect(Collectors.toSet()) ){
 
                 if((int)nodeAdj.getAttribute("color") != 0){
                     color[(int)nodeAdj.getAttribute("color") - 1] ++;
                 }
             }

             int j = 0;

             while(j!= (color.length) && color[j] != 0 ){ j++; };

             if (j == (color.length) ){
                 nodeP.setAttribute("color", minGiveColorTab(color));
                 //il peu etre judicieux de rajouter un attribut conflit au avion qui risque de se percuter
                 nbConflit = nbConflit + 1;
             }
             else{ nodeP.setAttribute("color", j+1); };
             System.out.println("color :" + nodeP.getAttribute("color"));

            //Step3
            for(Node nodeAdj : nodeP.neighborNodes().collect(Collectors.toSet()) ){
                initColor(color);

                for(Node nodeAdj2 : nodeAdj.neighborNodes().collect(Collectors.toSet())){
                    if((int)nodeAdj2.getAttribute("color") != 0 && nodeAdj2 != nodeP){
                        color[(int)nodeAdj2.getAttribute("color") - 1] = 1;
                    }    
                }
                int nbColor = nbColorAdj(color);
                if( nbColor!= 0){
                    nodeAdj.setAttribute("DSATUR", nbColor); }
            }

            System.out.println(nodeP + " | " + nodeP.getAttribute("DSATUR") +  " | " + nodeP.getAttribute("color"));

            System.out.println(nodeP + " | " + nodeP.getAttribute("DSATUR") +  " | " + nodeP.getAttribute("color"));
            ListNodes.remove(nodeP);

            //Step4
            recursifDSATUR(ListNodes,color);
         }

    }

    /**
     * Give the less use color of NodeP's adjacents nodes
     * @param color Color tab wich give a resum of Adjacents Node's colors
     * @return
     */
    private static int minGiveColorTab(int color[] ){
        int min = color[0];
        for(int i = 1; i < color.length ; i++){
            if(color[i] < min ){
                min = color[i];
            }
        }

        return min;

    }

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

    private static Node MaxNodeDSATUR( LinkedList<Node> ListesNodes){
        Node max = ListesNodes.getFirst();
        for (int i = 1; i < ListesNodes.size(); i++) {
            if ( (int)ListesNodes.get(i).getAttribute("DSATUR") > (int)max.getAttribute("DSATUR")) {
                max = ListesNodes.get(i) ;
            }
        }
        return max;

    }

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