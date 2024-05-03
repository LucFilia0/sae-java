package graph;

//-- Import Java
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.algorithm.ConnectedComponents.ConnectedComponent;
//-- Import GraphStream
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.graphicGraph.stylesheet.Color;

import java.util.LinkedList;

import exceptions.InvalidEntryException;
//-- Import Exceptions
import exceptions.InvalidFileFormatException;

/**
 * TestGraph is the basic class to handle the "graph-testX.txt" files.
 * This class extends the class SingleGraph, from GraphStream.
 * 
 * @implNote Uses the GraphStream attributes.
 * 
 * @author Luc le Manifik
 */
public class TestGraph extends SingleGraph {

    //-- TestGraph Attributes

    /**
     * The String identifier that represents the max allowed number of colors (int)
     */
    private final String K_MAX = "kMax";

    /**
     * The String identifier that represents the max allowed number of Nodes (int)
     */
    private final String NB_MAX_NODES = "nbMaxNodes";

    /**
     * The String identifier that represents the current number of Nodes (int)
     */
    private final String NB_NODES = "nbNodes";

    /**
     * The String identifier that represents the current number of edges (int)
     */
    private final String NB_EDGES = "nbEdges";

    //-- TestGraph Constructor

    /**
     * Constructor of the TestGraph class.
     * Creates a new TestGraph.
     * 
     * @param id (String) - The identifier of the TestGraph
     * 
     * @author Luc le Manifik
     */
    public TestGraph(String id) {
        super(id);
        this.setAttribute(this.K_MAX, 0);
        this.setAttribute(this.NB_MAX_NODES, 0);
        this.setAttribute(this.NB_NODES, 0);
        this.setAttribute(this.NB_EDGES, 0);
    }

    //-- TestGraph toString()

    /**
     * This function prints the informations of its TestGraph.
     * It shows the graph's identifier, kMax and number of nodes.
     * 
     * @return the String which contains those informations.
     * 
     * @author Luc le Manifik
     */
    public String toString() {
        return "-- TestGraph\nIdentifier : " + super.id + "\nkMax : " + this.getKMax() + "\nNumber of nodes : " + this.getNbNodes() + "/" + this.getNbMaxNodes() + "\nNumber of edges : " + this.getNbEdges();
    }

    //-- TestGraph Getters

    /**
     * Returns the identifier of the TestGraph.
     * 
     * @return (String) - The identifier of the TestGraph
     * 
     * @author Luc le Manifik
     */
    public String getId() {
        return super.getId();
    }

    /**
     * Returns the value of kMax, the maximum number of allowed colors.
     * 
     * @return (int) - The maximum number of colors in the TestGraph
     * 
     * @author Luc le Manifik
     */
    public int getKMax() {
        return (int)this.getAttribute(this.K_MAX);
    }

    /**
     * Returns the value of the expected amount of nodes in the GraphTest.
     * 
     * @return (int) - The expected number of nodes in the TestGraph
     * 
     * @author Luc le Manifik
     */
    public int getNbMaxNodes() {
        return (int)this.getAttribute(this.NB_MAX_NODES);
    }

    /**
     * Returns the number of nodes implemented in the TestGraph.
     * 
     * @return (int) - The number of nodes in the TestGraph
     * 
     * @author Luc le Manifik
     */
    public int getNbNodes() {
        return (int)this.getAttribute(this.NB_NODES);
    }

    /**
     * Returns the number of edges implemented in the TestGraph.
     * 
     * @return nbEdges (int)
     * 
     * @author Luc le Manifik
     */
    public int getNbEdges() {
        return (int)this.getAttribute(this.NB_EDGES);
    }

    //-- TestGraph Setters

    /**
     * Sets the value of kMax, which is the maximum amount of colors of the TestGraph.
     * 
     * @param kMax (int) - The new value of kMax.
     * @throws InvalidEntryException Throwed if the wanted value of kMax is inferior to 0.
     * 
     * @author Luc le Manifik
     */
    public void setKMax(int kMax) throws InvalidEntryException {
        if(kMax < 0) {
            throw new InvalidEntryException();
        }
        this.setAttribute(this.K_MAX, kMax);;
    }

    /**
     * Sets the value of the expected amount of nodes in the TestGraph.
     * 
     * @param nbMaxNodes (int) - The new value of nbMaxNodes.
     * @throws InvalidEntryException Throwed if the wanted value of nbMaxValues is inferior to 0.
     * 
     * @author Luc le Manifik
     */
    public void setNbMaxNodes(int nbMaxNodes) throws InvalidEntryException {
        if(nbMaxNodes < 0) {
            throw new InvalidEntryException();
        }
        this.setAttribute(this.NB_MAX_NODES, nbMaxNodes);
    }

    /**
     * Sets the value the number of nodes currently implemented in the TestGraph.
     * 
     * @param nbNodes (int) - The new value of nbNodes.
     * @throws InvalidEntryException Throwed if the wanted value of nbNodes is inferior to 0.
     * 
     * @author Luc le Manifik
     */
    public void setNbNodes(int nbNodes) throws InvalidEntryException {
        if(nbNodes < 0) {
            throw new InvalidEntryException();
        }
        this.setAttribute(this.NB_NODES, nbNodes);
    }

    /**
     * Sets the value the number of edges currently implemented in the TestGraph.
     * 
     * @param nbEdges (int) - The new value of nbEdges.
     * @throws InvalidEntryException Throwed if the wanted value of nbEdges is inferior to 0.
     * 
     * @author Luc le Manifik
     */
    public void setNbEdges(int nbEdges) throws InvalidEntryException {
        if(nbEdges < 0) {
            throw new InvalidEntryException();
        }
        this.setAttribute(this.NB_EDGES, nbEdges);
    }

    //-- TestGraph Methods

    /**
     * This function sets the TestGraph with the datas imported from the file specified in parameter.
     * It creates the nodes, the edges, and sets the attributes of the TestGraph, like its maximum number of nodes, etc...
     * Also shows the informations of the TestGraph in the console.
     * 
     * @param file (java.io.File) - The file from where are imported all the informations of the TestGraph.
     * @throws FileNotFoundException Throwed if the file passed in parameter is not found, or does not exist.
     * @throws NumberFormatException Throwed if the cast from (String) to (int) occures with an error. It can means that the String is not in required format (presence of spaces or symbols).
     * @throws InvalidFileFormatException Throwed if the source file does not meet the required format. Like a missing information on a line.
     * 
     * @author Luc le Manifik
     */
    public void importFromFile(File file) throws FileNotFoundException, NumberFormatException, InvalidFileFormatException {
        Scanner lineScanner = null;
        try {
            lineScanner = new Scanner(file);

            this.setTestGraphFrom(lineScanner);

            System.out.println(this); // Print TestGraph infotmations
            lineScanner.close();
        }catch(FileNotFoundException fnfe) {
            throw fnfe;
        }catch(InvalidFileFormatException iffe) {
            throw iffe;
        }catch(NumberFormatException nfe) {
            throw nfe;
        }
    }

    /**
     * This method sets the maximum number of nodes, and the maximum amount of color of the TestGraph, by reading the Scanner which is passed in parameter.
     * 
     * @param lineScanner (java.util.scanner) - The scanner which is currently reading the source file, or any support that contains the informations, in the correct format.
     * @throws NumberFormatException Throwed if the cast from (String) to (int) occures with an error. It can means that the String is not in required format (presence of spaces or symbols).
     * @throws InvalidFileFormatException Throwed if the source file does not meet the required format. Like a missing information on a line.
     * @throws InvalidEntryException Throwed if the entered value of some functions is inferior to 0.
     * 
     * @author Luc le Manifik
     */
    private void setTestGraphInfosFrom(Scanner lineScanner) throws NumberFormatException, InvalidFileFormatException, InvalidEntryException {
        
        String line = "";
        Scanner dataScanner = null;

        // First Line : get kMax
        if(lineScanner.hasNextLine()) {
            line = lineScanner.nextLine(); // Store the first line of the file into line
            dataScanner = new Scanner(line);
            if(dataScanner.hasNext()) {
                try {
                    this.setKMax(Integer.parseInt(dataScanner.next()));
                }catch(NumberFormatException nfe) {
                    lineScanner.close();
                    dataScanner.close();
                    throw nfe;
                }catch(InvalidEntryException iee) {
                    lineScanner.close();
                    dataScanner.close();
                    throw iee;
                }

                if(dataScanner.hasNext()) { // Check if there is an other value on the line. Then prompts an error, but continue the execution of the program
                    lineScanner.close();
                    dataScanner.close();
                    System.err.println("Error at Line 1 : Too many informations on line");
                }
            }else { // If the first line is empty or unusable (only spaces, or just an "\n")
                lineScanner.close();
                dataScanner.close();
                throw new InvalidFileFormatException("First line of the source file is empty or unusable. Can't get k-max.");
            }
            dataScanner.close();
        }else {
            lineScanner.close();
            throw new InvalidFileFormatException("Source file is empty");
        }

        // Second Line : get NbMaxNodes
        if(lineScanner.hasNextLine()) {
            line = lineScanner.nextLine(); // Store the second line into "line"
            dataScanner = new Scanner(line);
            if(dataScanner.hasNext()) {
                try {
                    this.setNbMaxNodes(Integer.parseInt(dataScanner.next()));
                }catch(NumberFormatException nfe) {
                    lineScanner.close();
                    dataScanner.close();
                    throw nfe;
                }catch(InvalidEntryException iee) {
                    lineScanner.close();
                    dataScanner.close();
                    throw iee;
                }

                if(dataScanner.hasNext()) { // Check if there is another information on the line. Prompt a message error but continue the execution of the program 
                    lineScanner.close();
                    dataScanner.close();
                    System.err.println("Error at Line 2 : Too many informations on line");
                }
            }else { // If the second line is empty or unusable (only spaces, or just an "\n")
                lineScanner.close();
                dataScanner.close();
                throw new InvalidFileFormatException("Second line of the source file is empty or unusable. Can't get the number of nodes.");
            }
            dataScanner.close();
        }
        else {
            lineScanner.close();
            throw new InvalidFileFormatException("Second line of the source file is empty.");
        }
    }

    /**
     * This method creates the nodes and the edges of the TestGraph, throught the scanner passed in parameter.
     * The scanner is ideally reading the source file, or any other support where the required informations are correctly written.
     * 
     * @param lineScanner (java.util.scanner) - The scanner which is currently reading the source file, or any support that contains the informations, in the correct format.
     * @throws InvalidFileFormatException Throwed if the source file does not meet the required format. Like a missing information on a line.
     * @throws NumberFormatException Throwed if the cast from (String) to (int) occures with an error. It can means that the String is not in required format (presence of spaces or symbols).
     * @throws InvalidEntryException Throwed if the entered value of some functions is inferior to 0. Catched in setTestGraphInfosFrom().
     * 
     * @author Luc le Manifik
     */
    @SuppressWarnings("resource")
    private void setTestGraphFrom(Scanner lineScanner) throws InvalidFileFormatException, NumberFormatException, InvalidEntryException {
        
        // Gets the TestGraph informations, which are the number of node and k-max
        try {
            this.setTestGraphInfosFrom(lineScanner);
        }catch(NumberFormatException nfe) {
            throw nfe;
        }catch(InvalidFileFormatException iffe) {
            throw iffe;
        }catch(InvalidEntryException iee) {
            throw iee;
        }

        int lineCursor = 2; // Indicates which line of the file is currently read
        
        String line = "", idNodeA = "", idNodeB = "";

        Scanner nodeScanner = null;

        while(lineScanner.hasNextLine()) {
            ++lineCursor;
            line = lineScanner.nextLine(); // Store the line of the source file, with 2 nodes, into "line"
            nodeScanner = new Scanner(line);
            // Get node A
            if(nodeScanner.hasNext()) {
                idNodeA = nodeScanner.next();
            }else { // If there is no information on the line
                nodeScanner.close();
                throw new InvalidFileFormatException("line " + lineCursor);
            }
            // Get node B
            if(nodeScanner.hasNext()) {
                idNodeB = nodeScanner.next();
            }else { // If there is no information for the second node on the line
                nodeScanner.close();
                throw new InvalidFileFormatException("line " + lineCursor);
            }
            // Checks if there is too much informations on the line, then print a message error, but continue the execution
            if(nodeScanner.hasNext()) {
                System.err.println("Error at Line " + lineCursor + " : Too many informations on line");
            }

            // Adds the nodes if they do not already exist
            if(this.getNode(idNodeA) == null) {
                this.addNode(idNodeA);
                this.setNbNodes(this.getNbNodes() + 1);
            }
            if(this.getNode(idNodeB) == null) {
                this.addNode(idNodeB);
                this.setNbNodes(this.getNbNodes() + 1);
            }

            // Checks if there is not too much nodes that have been added
            if(this.getNbNodes() <= this.getNbNodes()) {
                try {
                    this.addEdge(idNodeA + "-" + idNodeB, idNodeA, idNodeB);
                    this.setNbEdges(this.getNbEdges() + 1);
                }
                // Errors are treated here, because they do not require to stop the program, and just need to prompt some informations
                catch(IdAlreadyInUseException iaiue) {
                    // -> If an edge with the same id already exists and strict checking is enabled
                    System.err.println("Error at Line " + lineCursor + " : Edge " + idNodeA + "-" + idNodeB + " already exists.");
                }catch(ElementNotFoundException enfe) {
                    // -> If strict checking is enabled, and 'node1' or 'node2' are not registered in the graph
                    System.err.println("Error at Line " + lineCursor + " : Node [" + idNodeA + "] or node [" + idNodeB + "] undeclared.");
                }catch(EdgeRejectedException ere) {
                    // -> If strict checking is enabled and the edge is not accepted
                    System.err.println("Error at Line " + lineCursor + " : Trying to add edge [" + idNodeA + "-" + idNodeB + "] but edge [" + idNodeB + "-" + idNodeA + "] seems to already exist");
                }
            }else {
                nodeScanner.close();
                throw new InvalidFileFormatException("The number of created nodes exceeds the required amount specified in the source file.");
            }
            nodeScanner.close();
        }

        // Add the missing nodes, those which are not bound to other nodes
        // Because nodes are created by reading the edges of the source file,
        // But if a node is not bound to any other node, then he is not created at this state of the program.
        while(this.getNbNodes() < this.getNbMaxNodes()) {
            this.setNbNodes(this.getNbNodes() + 1);
            this.addNode(String.valueOf(this.getNbNodes()));
        }

        nodeScanner.close();
    }

    //-- TestGraph Coloration

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

    public static void setGraphStyle(Graph graph, int nbColor, String colorAttribute) {
        StringBuffer stylesheet = new StringBuffer("node {size-mode : dyn-size ; size : 30px ; }") ;

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

     /**
     * Give a graph's coloration with less colision we can, use graph saturation principle (degree).
     * 
     * @param graph Graph that will be tested.
     * @param Kmax Max color we can use.
     * @param attributColor the name of the attribut have a graph's dependance
     * @return max Number of color we use for it.
     * 
     * @autor GIRAUD Nila
     */
    private static int ColorationDsatur(Graph graph, int Kmax, String attributColor){
        LinkedList<Node> ListNodes = new LinkedList<Node>();
        System.out.println("Salut");
        graph.setAttribute("nbConflit",0);

        //Put all Nodes in a LinkedList
        for (Node node : graph) {
            node.setAttribute(attributColor, 0);
            node.setAttribute("DSATUR", node.getDegree());
            
            insertSorted(ListNodes, node); // Descendent Sort
        }

        for (Node node : ListNodes){
            System.out.println(node.getAttribute("DSATUR"));
            System.out.println(node.getAttribute(attributColor));
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

        recursifDSATUR(ListNodes, color, graph, attributColor);

        for (Node node : graph){

            node.removeAttribute("DSATUR");
            node.setAttribute("ui.class", attributColor + node.getAttribute(attributColor));

            System.out.println(node.getAttribute("DSATUR"));
            System.out.println(node.getAttribute(attributColor));
            System.out.println(node.getAttribute("ui.class"));
            System.out.println("\n");
        }

        int max = 0;
        for(Node node : graph){
            if((int)node.getAttribute(attributColor) > max){
                max = (int)node.getAttribute(attributColor);
            }
        }

        System.out.println(graph.getAttribute("nbConflit"));

        return max;
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

            for (Node node : ListNodes){
                System.out.println("\n" + node.getAttribute("DSATUR"));
                System.out.println(node.getAttribute(attributColor));
                System.out.println(node);
                
                System.out.println("\n");
            }
            System.out.println("Salut");
            
            //Step1
            Node nodeP = MaxNodeDSATUR(ListNodes);
            System.out.println(nodeP + " | " + nodeP.getAttribute("DSATUR") + " | " + nodeP.getAttribute(attributColor));
 
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
             System.out.println("color :" + nodeP.getAttribute(attributColor));

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

            System.out.println(nodeP + " | " + nodeP.getAttribute("DSATUR") +  " | " + nodeP.getAttribute(attributColor));

            System.out.println(nodeP + " | " + nodeP.getAttribute("DSATUR") +  " | " + nodeP.getAttribute(attributColor));
            ListNodes.remove(nodeP);

            //Step4
            recursifDSATUR(ListNodes,color, graph,attributColor);
         }

    }

    /**
     * Give the less use color of NodeP's adjacents nodes
     * @param color Color tab wich give a resum of Adjacents Node's colors
     * @return min Less use color
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
    
}

   