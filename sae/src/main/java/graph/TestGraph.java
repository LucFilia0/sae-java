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

    public static void triTabNode(Node[] tab) {
        int max ;

        for (int i = 0 ; i < tab.length ; i++) {
            max = i ;
            for (int j = i + 1 ; j < tab.length ; j++) {
                if (tab[j].getDegree() > tab[max].getDegree()) {
                    max = j;
                }
            }

            Node temp = tab[i] ;
            tab[i] = tab[max] ;
            tab[max] = temp ;
        }

        for (int i = 0 ; i < tab.length ; i++) {
            System.out.println(tab[i]);
        }
    }

    public static void colorGraphRFL(Graph graph) {
        HashMap<Integer, HashSet<Node>> colorMap = new HashMap<>() ;
        Node[] tab = new Node[graph.getNodeCount()] ;
        int i = 0 ;

        // Tri par degré puis stock le résultat dans une liste chainée
        for (Node node : graph) {
            tab[i] = node ;
            node.setAttribute("color", 0);
            i++ ;
        }
        
        triTabNode(tab) ;
        LinkedList<Node> list = new LinkedList<>() ;
        Collections.addAll(list, tab) ;
        
        
        //Début de la Coloration ici
        int color = 1 ;

        //Servira à stocker les voisins des noeuds courrants
        Set<Node> buffer = new HashSet<>() ;

        //Servira à parcourir la liste des noeuds
        ListIterator<Node> iterator ;
        Node bufferNode ;
        colorMap.put(1, new HashSet<>()) ;

        //Traitement des noeuds
        while (!list.isEmpty()) {
            
            // On ajoute le premier noeud de la couleur et on ajoute ses voisins à buffer
            colorMap.get(color).add(list.getFirst()) ;
            buffer.addAll(list.getFirst().neighborNodes().collect(Collectors.toSet())) ;
            list.removeFirst() ;
            iterator = list.listIterator(0) ;
            
            // On colore progressivement les noeuds en ajoutant à chaque fois leurs voisins à buffer
            while (iterator.hasNext()) {
                bufferNode = iterator.next() ;
                if (!buffer.contains(bufferNode)) {
                    colorMap.get(color).add(bufferNode) ;
                    iterator.remove() ;
                    buffer.addAll(bufferNode.neighborNodes().collect(Collectors.toSet())) ;
                }
            }

            //Passage à la couleur suivante
            if (!list.isEmpty()) {

                //Affichage pour visualiser ici (test)
                Iterator<Node> itr = buffer.iterator() ;
                while (itr.hasNext()) {
                    System.out.print(itr.next() + "   ");
                }
                System.out.println("\n================\n");

                //Changement de couleur, remise à 0 des voisins
                color++ ;
                colorMap.put(color, new HashSet<>()) ;
                buffer = new HashSet<>() ;
            }

        }

        // Affichage de la coloration (test)
        for (Integer key : colorMap.keySet()) {
            Iterator<Node> itr = colorMap.get(key).iterator() ;
            System.out.print(key + " : ");
            while (itr.hasNext()) {
                Node node = (Node) itr.next() ;
                node.setAttribute("ui.class", "color" + key.toString());
                System.out.print(node + "   ") ;
            }
            System.out.println() ;
        }

    }

    public static void main(String args[]) {
        System.setProperty("org.graphstream.ui", "swing");

        Graph testGraph = new SingleGraph("testGraph");
        File testGraphFile = new File("sae/DataTest/graph-test4.txt");

        try {
            importTestGraph(testGraph, testGraphFile);
            TestGraph.colorGraphRFL(testGraph);
            testGraph.setAttribute("ui.stylesheet", "node {size : 25px ;} node.color1 {fill-color : red ;}" 
            + " node.color2 {fill-color : blue ;}" 
            + " node.color3 {fill-color : green ;}"
            + " node.color4 {fill-color : orange ;}"
            + " node.color5 {fill-color : pink ;}"
            + " node.color6 {fill-color : gray ;}"
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
