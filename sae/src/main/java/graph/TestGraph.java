package graph;

//-- Import Java
import java.io.File;
import java.io.FileNotFoundException;
import java.util.* ; 
import java.util.stream.*;

//-- Import GraphStream
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.algorithm.*;
import org.graphstream.algorithm.ConnectedComponents.ConnectedComponent;

//-- Import Exceptions
import exceptions.InvalidFileFormatException;
import exceptions.InvalidEntryException;

/**
 * TestGraph is the basic class to handle the "graph-testX.txt" files.
 * This class extends the class SingleGraph, from GraphStream.
 * @author Luc le Manifik
 */
public class TestGraph extends SingleGraph {
    //-- TestGraph Attributes

    private int kMax; // -> Maximum amount of colors.
    private int nbMaxNodes; // -> Required amount of nodes. 
    private int nbNodes; // -> Current/real amount of nodes.
    private int nbEdges; // -> Amount of edges.

    //-- TestGraph Constructor

    /**
     * This constructor creates a new TestGraph.
     * 
     * @param id (String) - The identifier of the TestGraph
     * 
     * @author Luc le Manifik
     */
    TestGraph(String id) {
        super(id);
        this.kMax = 0;
        this.nbMaxNodes = 0;
        this.nbNodes = 0;
        this.nbEdges = 0;
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
        return "-- TestGraph\nIdentifier : " + super.id + "\nkMax : " + this.kMax + "\nNumber of nodes : " + this.nbNodes + "/" + this.nbMaxNodes + "\nNumber of edges : " + this.nbEdges;
    }

    //-- TestGraph Getters

    /**
     * Returns the identifier of the TestGraph.
     * 
     * @return identifier (String)
     */
    public String getId() {
        return super.getId();
    }

    /**
     * Returns the value of kMax, the maximum number of allowed colors.
     * 
     * @return kMax (int)
     */
    public int getKMax() {
        return this.kMax;
    }

    /**
     * Returns the value of the expected amount of nodes in the GraphTest.
     * 
     * @return nbMaxNodes (int)
     */
    public int getNbMaxNodes() {
        return this.nbMaxNodes;
    }

    /**
     * Returns the number of nodes implemented in the TestGraph.
     * 
     * @return nbNodes (int)
     */
    public int getNbNodes() {
        return this.nbNodes;
    }

    /**
     * Returns the number of edges implemented in the TestGraph.
     * 
     * @return nbEdges (int)
     */
    public int getNbEdges() {
        return this.nbEdges;
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
        this.kMax = kMax;
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
        this.nbMaxNodes = nbMaxNodes;
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
        this.nbNodes = nbNodes;
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
        if(nbNodes < 0) {
            throw new InvalidEntryException();
        }
        this.nbEdges = nbEdges;
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
    public void importDataFromFile(File file) throws FileNotFoundException, NumberFormatException, InvalidFileFormatException {
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
                ++this.nbNodes;
            }
            if(this.getNode(idNodeB) == null) {
                this.addNode(idNodeB);
                ++this.nbNodes;
            }

            // Checks if there is not too much nodes that have been added
            if(this.nbNodes <= this.nbMaxNodes) {
                try {
                    this.addEdge(idNodeA + "-" + idNodeB, idNodeA, idNodeB);
                    ++this.nbEdges;
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
        while(this.nbNodes < this.nbNodes) {
            ++this.nbNodes;
            this.addNode(String.valueOf(this.nbNodes));
        }

        nodeScanner.close();
    }
}