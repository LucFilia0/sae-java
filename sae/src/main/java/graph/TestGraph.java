package graph;

import java.io.File;
import java.io.FileNotFoundException;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import java.util.* ;
import java.util.stream.*;

// Exceptions
import exceptions.InvalidFileFormatException;

public class TestGraph {

    public static void main(String args[]) {
        System.setProperty("org.graphstream.ui", "swing");

        Graph testGraph = new SingleGraph("testGraph");
        File testGraphFile = new File("sae/DataTest/graph-test19.txt");
        try {
            importTestGraph(testGraph, testGraphFile) ;
            System.out.println("Nombre de couleurs : " + colorGraphRLF(testGraph) + " K-Max : " + testGraph.getAttribute("kMax")) ;
            System.out.println("Nb conflits : " + testColorationGraph(testGraph)) ;
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
    public static Node selectNextNodeToAdd(HashSet<Node> setOfNeighborsOfFirstNode, HashSet<Node> setOfAddableNodes) {
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
    public static void addNodesToSetOfAddableNodes(Graph graph, HashMap<Integer, HashSet<Node>> colorMap, 
    Integer color, HashSet<Node> setOfNeighborsOfFirstNode, HashSet<Node> setOfAddableNodes) {
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
    public static Node loadGraphNodesIntoSetOfAddableNodes(Graph graph, HashSet<Node> setOfAddableNodes) {
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
     * Applique l'algorithme RLF (Recursive Largest First) pour colorier graph
     * (Malgré son nom l'implémentation est ici itérative)
     * 
     * @param graph graph à colorier
     * @return int nombre de couleurs utilisées pour colorier graph
     * 
     * @author Nathan LIEGEON
     */
    public static int colorGraphRLF(Graph graph) {

        Graph newGraph = Graphs.clone(graph) ;
        HashMap<Integer, HashSet<Node>> colorMap = new HashMap<>() ;
        HashSet<Node> setOfAddableNodes ;
        HashSet<Node> setOfNeighborsOfFirstNode ;
        Node firstNodeOfThisColor ;
        
        int color = 1 ;

        while (newGraph.getNodeCount() != 0) {
            firstNodeOfThisColor = null ;
            colorMap.put(color, new HashSet<>()) ;
            setOfAddableNodes = new HashSet<>() ;

            firstNodeOfThisColor = loadGraphNodesIntoSetOfAddableNodes(newGraph, setOfAddableNodes) ;
            colorMap.get(color).add(graph.getNode(firstNodeOfThisColor.getId())) ;

            setOfNeighborsOfFirstNode = (HashSet<Node>) firstNodeOfThisColor.neighborNodes().collect(Collectors.toSet()) ;
            setOfAddableNodes.remove(firstNodeOfThisColor) ;
            setOfAddableNodes.removeAll(setOfNeighborsOfFirstNode) ;
            addNodesToSetOfAddableNodes(graph, colorMap, color, setOfNeighborsOfFirstNode, setOfAddableNodes) ;

            //Ajout des couleurs dans le vrai graphe 
            for (Node coloringNode : colorMap.get(color)) {
                coloringNode.setAttribute("color", color) ;
                coloringNode.setAttribute("ui.class", "color" + color) ;
                newGraph.removeNode(newGraph.getNode(coloringNode.getId())) ;
            }
            color++ ;
            
        }


        return color - 1 ;
    }

    /**
     * Requires nodes to have the attribute "color".
     * Checks if the coloration worked.
     * 
     * @param graph Graph that will be tested.
     * @return int number of nodes which are adjacent to another node with the same color.
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
            }catch(IdAlreadyInUseException iaiue) { // C'est pas censé arrivé, mais on sait jamais, si on réutilise ailleurs...
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
}