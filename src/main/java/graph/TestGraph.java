package graph;

//-- Import Java
import java.io.File;
import java.io.FileNotFoundException;
import java.util.* ;

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

        String[] str = {"graph-textX.txt"} ;
        Automatisation.importDataFromFolder("DataTest", str, 'X') ;

        nodeScanner.close();
    }
    
}

   