package graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

// Exceptions
import exceptions.InvalidFileFormatException;

public class TestGraph {

    public static void main(String args[]) {
        System.setProperty("org.graphstream.ui", "swing");

        Graph testGraph = new SingleGraph("testGraph");
        File testGraphFile = new File("sae/DataTest/graph-test0.txt");

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

        for(int i=1; i<=nbNodes; ++i) {
            graph.addNode(String.valueOf(i));
        }
    }

    @SuppressWarnings("resource")
    private static void setTestGraphEdges(Graph graph, Scanner lineScanner, int nbNodes) throws FileNotFoundException, InvalidFileFormatException, NumberFormatException {
        String _line = "", _nodeA = "", _nodeB = "";
        int nodeA = 0, nodeB = 0;

        Scanner nodeScanner = null;

        while(lineScanner.hasNextLine()) {
            _line = lineScanner.nextLine();
            nodeScanner = new Scanner(_line);
            // Get node A
            if(nodeScanner.hasNext()) {
                _nodeA = nodeScanner.next();
            }else {
                nodeScanner.close();
                throw new InvalidFileFormatException();
            }
            // Get node B
            if(nodeScanner.hasNext()) {
                _nodeB = nodeScanner.next();
            }else {
                nodeScanner.close();
                throw new InvalidFileFormatException();
            }
            // Vérif nombre en plus sur la ligne
            if(nodeScanner.hasNext()) {
                nodeScanner.close();
                throw new InvalidFileFormatException();
            }

            try {
                nodeA = Integer.parseInt(_nodeA);
                nodeB = Integer.parseInt(_nodeB);
            }catch(NumberFormatException ex) {
                nodeScanner.close();
                throw ex;
            }

            if(nodeA <= nbNodes && nodeA > 0 && nodeB > 0 && nodeB <=nbNodes) {
                graph.addEdge(_nodeA + "-" + _nodeB, _nodeA, _nodeB);
            }else {
                nodeScanner.close();
                throw new InvalidFileFormatException();
            }
            nodeScanner.close();
        }
        nodeScanner.close();
    }
}
