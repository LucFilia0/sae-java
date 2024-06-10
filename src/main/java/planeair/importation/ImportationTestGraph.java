package planeair.importation;

// Import Java
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Import GraphStream
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.graph.Node;

// Import Exceptions
import planeair.exceptions.InvalidEntryException;
import planeair.exceptions.InvalidFileFormatException;
import planeair.graph.graphtype.GraphSAE;
import planeair.graph.graphtype.TestGraph;

/**
 * <html>
 * This class contains all the functions/procedures linked to TestGraph importation.
 * <hr>
 * In this project, two types of files are mainly used : 
 *  <ol>
 *      <li>
 *          "aeroports.csv" contains all data of the Airports, which will be stored in an AirportSet.
 *      </li>
 *      <li>                 
 *          "vols-testX.csv" contains all data of the Flights we wish to simulate.
 *          <strong>The Flights NEEDS the Airports</strong>, and need to be imported after them.
 *      </li>
 *  </ol>
 * The files are formated due to certain rules, in order to find the right informations at the right place.
 * So, if a file can't be read because of some errors (mistakes or not), it may endanger the entire application.
 * <hr>
 * In order to prevent such a mess to happen, data will be checked before to be fully imported in the application's objects.
 * Messages will be prompt for the user, even if these errors are not blocking the programm to execute.
 * With that, the user will be informed of all the errors that are present in the files.
 * </html>
 * 
 * @author Luc le grand, que dis-je, le <strong>Manifik</strong>
 */
public class ImportationTestGraph {

    /**
     * The RegEx which is used to clean the TestGraph files' lines
     */
    public static final String REGEX_TEST_GRAPH = "[^ 0-9]";

    /**
     * Imports the TestGraph data specified in parameter
     * 
     * @param testGraph ({@link planeair.graph.graphtype.TestGraph TestGraph}) - The TestGraph we want to import
     * @param testGraphFile ({@link java.io.File File}) - The File which contains the TestGraph's data
     * @param showErrorMessages (boolean) - True if you want that the error messages are prompt
     * 
     * @throws FileNotFoundException Threw if the File does not exist, ot is not found
     * @throws InvalidFileFormatException Threw if the File format is incorrect
     * 
     * @author Luc le Manifik
     */
    public static void importTestGraphFromFile(TestGraph testGraph, File testGraphFile, boolean showErrorMessages) throws FileNotFoundException, InvalidFileFormatException {

        boolean kMaxImported = false;
        boolean nbNodesImported = false;

        Scanner lineScanner = null;

        int currentLine = 0;

        try {
            lineScanner = new Scanner(testGraphFile);
        }catch(FileNotFoundException fnfe) {
            throw fnfe;
        }

        try {
            // First Line : get kMax
            kMaxImported = importKMax(testGraph, lineScanner, currentLine, showErrorMessages);

            // Second Line : get NbMaxNodes
            nbNodesImported = importNbNodes(testGraph, lineScanner, currentLine, showErrorMessages);
        }catch(InvalidFileFormatException iffe) {
            throw iffe;
        }

        // Checks if the TestGraph Data
        if(!kMaxImported && !nbNodesImported && !lineScanner.hasNextLine()) {
            throw new InvalidFileFormatException(currentLine, "The maximum amount of colors, and/or the number of nodes could not be correctly imported at this point of the source file.");
        }

        try {
            createTestGraph(testGraph, lineScanner, currentLine, showErrorMessages);
        }catch(InvalidFileFormatException iffe) {
            throw iffe;
        }

    }

    // PRIVATE PROCEDURES

    /**
     * This methods is scanning the source file until it founds the k-max value.
     * 
     * @param testGraph ({@link graph.TestGraph TestGraph}) - The TestGraph we are currently importing
     * @param lineScanner ({@link java.util.Scanner Scanner}) - The Scanner which is currently reading the source File
     * @param currentLine (Integer) - The source File's current line 
     * @param showErrorMessages (boolean) - If "True", then some minor error messages will be prompt. Nothing will be prompt if you pu "False"
     * 
     * @throws InvalidFileFormatException
     * 
     * @return kMaxImported (boolean) - Return true if the kMax value is found in the file, else false
     * 
     * @author Luc le Manifik
     */
    @SuppressWarnings("resource")
    private static boolean importKMax(TestGraph testGraph, Scanner lineScanner, Integer currentLine, boolean showErrorMessages) throws InvalidFileFormatException {

        boolean kMaxImported = false;

        String line = null;
        Scanner dataScanner = null;

        while(!kMaxImported && lineScanner.hasNextLine()) {

            ++currentLine;
            line = lineScanner.nextLine().replaceAll(ImportationTestGraph.REGEX_TEST_GRAPH, "");
            dataScanner = new Scanner(line);

            if(dataScanner.hasNext()) {
                try {
                    testGraph.setKMax(Integer.parseInt(dataScanner.next()));
                    kMaxImported = true; // If the boolean is not turned to "True", then it means that the line is empty, and the Scanner will try to read the next line.
                }catch(NumberFormatException nfe) {
                    lineScanner.close();
                    dataScanner.close();
                    throw new InvalidFileFormatException(currentLine, "The maximum amount of colors can't be read. The TestGraph can not be correctly imported.");
                }catch(InvalidEntryException iee) {
                    lineScanner.close();
                    dataScanner.close();
                    throw new InvalidFileFormatException(currentLine, "The maximum amount of colors must be a positive number.");
                }

                if(showErrorMessages && dataScanner.hasNext()) { // Check if there is an other value on the line. Then prompts an error, but continue the execution of the program
                    System.err.println("Error at Line " + currentLine +  " : Too many informations on line");
                }
            }

            dataScanner.close();
        }

        return kMaxImported;
    }

    /**
     * This methods is scanning the source file until it founds the number of nodes value.
     * 
     * @param testGraph ({@link graph.TestGraph TestGraph}) - The TestGraph we are currently importing
     * @param lineScanner ({@link java.util.Scanner Scanner}) - The Scanner which is currently reading the source File
     * @param currentLine (Integer) - The source File's current line 
     * @param showErrorMessages (boolean) - If "True", then some minor error messages will be prompt. Nothing will be prompt if you pu "False"
     * 
     * @throws InvalidFileFormatException
     * 
     * @return nbNodesImported (boolean) - Return true if the number of nodes value is found in the file, else false
     * 
     * @author Luc le Manifik
     */
    @SuppressWarnings("resource")
    private static boolean importNbNodes(TestGraph testGraph, Scanner lineScanner, Integer currentLine, boolean showErrorMessages) throws InvalidFileFormatException {
        
        boolean nbNodesImported = false;

        String line = null;
        Scanner dataScanner = null;

        while(!nbNodesImported && lineScanner.hasNextLine()) {

            ++currentLine;
            line = lineScanner.nextLine().replaceAll(ImportationTestGraph.REGEX_TEST_GRAPH, "");
            dataScanner = new Scanner(line);

            if(dataScanner.hasNext()) {
                try {
                    testGraph.setNbMaxNodes(Integer.parseInt(dataScanner.next()));
                    nbNodesImported = true; // If the boolean is not turned to "True", then it means that the line is empty, and the Scanner will try to read the next line.
                }catch(NumberFormatException nfe) {
                    lineScanner.close();
                    dataScanner.close();
                    throw new InvalidFileFormatException(currentLine, "The number of nodes can't be read. The TestGraph can not be correctly imported.");
                }catch(InvalidEntryException iee) {
                    lineScanner.close();
                    dataScanner.close();
                    throw new InvalidFileFormatException(currentLine, "The number of nodes must be a positive number.");
                }

                if(showErrorMessages && dataScanner.hasNext()) { // Check if there is an other value on the line. Then prompts an error, but continue the execution of the program
                    System.err.println("Error at Line " + currentLine +  " : Too many informations on line");
                }
            }

            dataScanner.close();
        }

        return nbNodesImported;
    }

    @SuppressWarnings("resource")
    private static void createTestGraph(TestGraph testGraph, Scanner lineScanner, Integer currentLine, boolean showErrorMessages) throws InvalidFileFormatException {
        
        String line = null;
        Scanner nodeScanner = null;

        int nbMaxNodes = testGraph.getNbMaxNodes();
        int nbNodes = 0;

        String idNodeA = null;
        String idNodeB = null;

        Node nodeAdded ;

        while(lineScanner.hasNextLine()) {

            ++currentLine;
            line = lineScanner.nextLine();
            line = line.replaceAll(ImportationTestGraph.REGEX_TEST_GRAPH, "");
            nodeScanner = new Scanner(line);

            // Get node A
            if(nodeScanner.hasNext()) {
                idNodeA = nodeScanner.next();
                // Get node B
                if(nodeScanner.hasNext()) {
                    idNodeB = nodeScanner.next();
                }else { // If there is no information for the second node on the line
                    nodeScanner.close();
                    throw new InvalidFileFormatException(currentLine, "Not enough information to create edge. Missing second node.");
                }
            }
            
            // Checks if there is too much informations on the line, then print a message error, but continue the execution
            if(showErrorMessages && nodeScanner.hasNext()) {
                System.err.println("Error at Line " + currentLine + " : Too many informations on line");
            }

            // Adds the nodes and increment node number if they do not already exist
            if(testGraph.getNode(idNodeA) == null) {
                nodeAdded = testGraph.addNode(idNodeA);
                nodeAdded.setAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE, 0) ;
                nodeAdded.setAttribute(TestGraph.CONFLICT_ATTRIBUTE, 0) ;
                ++nbNodes;
            }
            if(testGraph.getNode(idNodeB) == null) {
                nodeAdded = testGraph.addNode(idNodeB);
                nodeAdded.setAttribute(GraphSAE.NODE_COLOR_ATTRIBUTE, 0) ;
                nodeAdded.setAttribute(TestGraph.CONFLICT_ATTRIBUTE, 0) ;
                ++nbNodes;
            }

            // Checks if there is not too much nodes that have been added
            if(nbNodes <= nbMaxNodes) {
                try {
                    testGraph.addEdge(idNodeA + "-" + idNodeB, idNodeA, idNodeB);
                }
                // Errors are treated here, because they do not require to stop the program, and just need to prompt some informations
                catch(IdAlreadyInUseException iaiue) {
                    // -> If an edge with the same id already exists and strict checking is enabled
                    if (showErrorMessages) {
                        System.err.println("Error at Line " + currentLine + " : Edge " + idNodeA + "-" + idNodeB + " already exists.");
                    }
                }catch(ElementNotFoundException enfe) {
                    // -> If strict checking is enabled, and 'node1' or 'node2' are not registered in the graph
                    if (showErrorMessages) {
                        System.err.println("Error at Line " + currentLine + " : Node [" + idNodeA + "] or node [" + idNodeB + "] undeclared.");
                    }
                }catch(EdgeRejectedException ere) {
                    // -> If strict checking is enabled and the edge is not accepted
                    if (showErrorMessages) {
                        System.err.println("Error at Line " + currentLine + " : Trying to add edge [" + idNodeA + "-" + idNodeB + "] but edge [" + idNodeB + "-" + idNodeA + "] seems to already exist");
                    }
                }
            }else {
                nodeScanner.close();
                throw new InvalidFileFormatException(currentLine, "The number of created nodes exceeds the maximum amount specified in the source file.");
            }
        }

        int nodeId = nbNodes;
        // What if there is not enough node ?
        while(nbNodes < nbMaxNodes) {
            if(testGraph.getNode(String.valueOf(nodeId)) != null) {
                testGraph.addNode(String.valueOf(nodeId));
                ++nbNodes;
            }
            ++nodeId;
        }
    }
}
