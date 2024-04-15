package graph;

//-- Import Java
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//-- Import GraphStream
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

//-- Import Exceptions
import exceptions.InvalidFileFormatException;

/**
 * TestGraph is the basic class to handle the "graph-testX.txt" files.
 * @author Luc le Manifik
 */
public class TestGraph extends SingleGraph {

    private String id;
    private int kMax;
    private int nbNodes;

    public TestGraph(String id) {
        super(id);
    }
    // Mettre 

    public static void main(String args[]) {
        System.setProperty("org.graphstream.ui", "swing");

        Graph testGraph = new SingleGraph("testGraph");
        File testGraphFile = new File("sae/DataTest/graph-test6.txt");

        try {
            importTestGraph(testGraph, testGraphFile);
            testGraph.display();
        }catch(FileNotFoundException ex) {
            System.err.println(ex);
        }catch(NumberFormatException ex) {
            System.err.println(ex);
        }catch(InvalidFileFormatException ex) {
            System.err.println(ex);
        }
    }

    public static void importTestGraph(Graph graph, File file) throws FileNotFoundException, NumberFormatException, InvalidFileFormatException {

        TestGraphInfo testGraphInfo = new TestGraphInfo();
        Scanner lineScanner = null;
        try {
            lineScanner = new Scanner(file);
            setTestGraphInfos(lineScanner, testGraphInfo);
            System.out.println("K-max : " + testGraphInfo.getKMax() + " | nbNodes : " + testGraphInfo.getNbNodes());
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
                graph.addNode(id);
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
