package graph;

import java.io.File ;
import java.io.FileFilter;
import java.io.FileWriter;
import java.util.List ;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList ;

import org.graphstream.graph.Node;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.Graphs;

public class Automatisation {
    
    /**
     * Tells whether or not a file is of a similar form to the identifier
     * ex : identifier is "testGraphX.txt" filename is "testGraph12.txt", placeholder is 'X'
     * The function will return true for the example
     * @param filename name your are testing
     * @param identifier identifier the filename will be compared to
     * @param placeholder character which should be replaced by numbers
     * @return boolean telling whether the file was similar to the identifier or not
     */
    public static boolean isFileLike(String filename, String identifier, char placeholder) {
        // Initialisation
        boolean res = true ;
        int offset = 0, filenameLength = filename.length(), identifierLength = identifier.length();
        char filenameBuffer, identifierBuffer ;

        // If string is too short it can't be similar
        if (filenameLength >= identifierLength) {
            int i = 0 ;
            while (res && (i+ offset) < filenameLength) {
                filenameBuffer = filename.charAt(i + offset) ;
                identifierBuffer = identifier.charAt(i) ;

                // Case when encountering placeholder
                if (identifierBuffer == placeholder) {
                    
                    // Checking for digits
                    while (Character.isDigit(filenameBuffer)) {
                        offset++ ;
                        filenameBuffer = filename.charAt(i + offset) ;
                    }

                    // Means there was no digit, so the placeholder was not filled
                    // example : testGraphX.txt and we get testGraph.txt
                    if (offset == 0) {
                        res = false ;
                    }
                    offset-- ;
                }
                else {
                    if (filenameBuffer != identifierBuffer) {
                        res = false ;
                    }
                }
                i++ ;
            }
        }
        else {
            res = false ;
        }

        return res ;
    }

    /**
     * Tells whether or not a file is of a similar form to one of the identifiers
     * ex : identifier is "testGraphX.txt" filename is "testGraph12.txt", placeholder is 'X'
     * The function will return true for the example
     * @param filename name your are testing
     * @param identifier array containing different identifiers the filename will be compared to
     * @param placeholder character which should be replaced by numbers
     * @return boolean telling whether the file was similar to one of the identifiers or not
     */
    public static boolean isFileLike(String filename, String[] identifiers, char placeholder) {
        int i = 0 ;
        boolean res = false ;
        while (!res && i < identifiers.length) {
            res = isFileLike(filename, identifiers[i], placeholder) ;
            i++ ;
        }
        
        return res ;
    }


    /**
     * Loads all graphs from the folder and stores them in a LinkedList which is returned
     * @param path the folder's path
     * @param identifiers the strings the name will be compared to
     * @param placeholder character that should be replaced by numbers for the check
     * @param threadPool current threadPool used to divide the tasks
     * @return the list of Graphs that were imported ordered by ID
     */
    public static List<TestGraph> importDataFromFolder(String path, String[] identifiers, char placeholder) {
        LinkedList<TestGraph> res = new LinkedList<>() ;

        // Filters the file that should be imported
        File folder = new File(path) ;
        File[] files = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File arg0) {
                return isFileLike(arg0.getName(), identifiers, placeholder) ;
            }
        }) ;

        // Imports the graphs from the filtered files
        if (files != null) {
            for (File file : files) {
                TestGraph temp = new TestGraph(Automatisation.isolateNumberInString(file.getName())) ;
                try {
                    temp.importFromFile(file) ;
                    for (Node node : temp) {
                        node.setAttribute("color", 0) ;
                    }
                }
                catch (Exception e) {
                    System.err.println(e) ;
                }

                res.addFirst(temp) ;
            }
        }

        res.sort(new Comparator<Graph>() {
            @Override
            public int compare(Graph arg0, Graph arg1) {
                return Integer.compare(Integer.valueOf(arg0.getId()), Integer.valueOf(arg1.getId())) ;
            }
        });

        return res ;
    }

    /**
     * Recovers the first number in the String and returns it
     * @param input String containing the Integer
     * @return The String contraining the isolated Integer, null if there wasn't any
     */
    public static String isolateNumberInString(String input) {
        int length = input.length() ;
        int i = 0 ;
        StringBuffer num = new StringBuffer() ;
        
        // Goes to the first digit in the String
        while (i < length && !Character.isDigit(input.charAt(i))) {
            i++ ;
        }

        // Stores the number in a StringBuffer
        if (i < length) {
            while (i < length && Character.isDigit(input.charAt(i))) {
                num.append(input.charAt(i)) ;
                i++ ;
            }
        }

        // Returns null if there is no number
        else {
            return null ;
        }
        
        return num.toString() ;
    }

    public static TestGraph useBestColoringAlgorithm(TestGraph graph, String colorAttribute) {
        ArrayList<ArrayList<Integer>> resList = new ArrayList<>() ;
        ArrayList<TestGraph> graphList = new ArrayList<>() ;

        int[] res ;
        graphList.add((TestGraph)Graphs.clone(graph)) ;
        resList.add(new ArrayList<>()) ;
        res = (Coloration.colorWelshPowell(graphList.get(0), colorAttribute, graph.getKMax())) ;
        for (int val : res) {
            resList.get(0).add(val) ;
        }

        graphList.add((TestGraph)Graphs.clone(graph)) ;
        resList.add(new ArrayList<>()) ;
        res = Coloration.ColorationDsatur(graphList.get(1), colorAttribute, graph.getKMax()) ;
        for (int val : res) {
            resList.get(1).add(val) ;
        }

        graphList.add((TestGraph)Graphs.clone(graph)) ;
        resList.add(new ArrayList<>()) ;
        res = Coloration.ColorationDsatur(graphList.get(2), colorAttribute, graph.getKMax()) ;
        for (int val : res) {
            resList.get(2).add(val) ;
        }

        int comp ;
        if (resList.get(0).get(1) + resList.get(1).get(1) + resList.get(2).get(1) > 0) {
            comp = Automatisation.smallestInThreeValues(resList.get(0).get(1), resList.get(1).get(1), resList.get(2).get(1)) ;
        }
        else {
            comp = Automatisation.smallestInThreeValues(resList.get(0).get(0), resList.get(1).get(0), resList.get(2).get(0)) ;
        }

        TestGraph temp ;
        switch(comp) {
            case 0 :
                temp = graphList.get(0) ;
                break ;
            case 1 :
                temp = graphList.get(1) ;
                break ;
            case 2 :
                temp = graphList.get(2) ;
                break ;
            default :
                // Shouldn't happen 👍
                temp = null ;
                break ;
        }

        if (temp != null) {
            temp.setAttribute("nbCouleurs", resList.get(comp).get(0)) ;
            temp.setAttribute("nbConflits", resList.get(comp).get(1)) ;
        }

        return temp ;
    }

    
    public static void startAutomatisation(String path, String[] identifiers, char placeholder, String colorAttribute) {
        List<TestGraph> graphList = Automatisation.importDataFromFolder(path, identifiers, placeholder) ;
        TestGraph temp ;
        new File(path + "/4").mkdirs() ;
        for (TestGraph graph : graphList) {
            temp = Automatisation.useBestColoringAlgorithm(graph, colorAttribute) ;
            try {
                File resFile = new File(path + "/4/" + "color-eval" + temp.getId() + ".txt") ;
                if (!resFile.createNewFile()) {
                    System.out.println("File already exists") ;
                }
                else {
                    FileWriter writer = new FileWriter(resFile) ;
                    List<Node> list = temp.nodes().collect(Collectors.toList()) ;
                    list.sort(new Comparator<Node>() {
                        @Override
                        public int compare(Node arg0, Node arg1) {
                            return Integer.compare(Integer.valueOf(arg0.getId()), Integer.valueOf(arg1.getId())) ;
                        }
                    }) ;

                    for (Node node : list) {
                        writer.write(node.getId() + " ; " + node.getAttribute(colorAttribute) + '\n') ;
                    }

                    writer.close() ;
                }
            }

            catch (Exception e) {
                System.err.println("IOException") ;
            }
        }
        
    }

    /**
     * Returns which value is the smallest
     * @param arg0
     * @param arg1
     * @param arg2
     * @return 0, 1 or 2 based on which one is the smallest
     */
    public static int smallestInThreeValues(int arg0, int arg1, int arg2) {
        if (arg0 < arg1 && arg0 < arg2) {
            return 0 ;
        }
        else if (arg1 < arg0 && arg1 < arg2) {
            return 1 ;
        }
        else {
            return 2 ;
        }
    }
    
} 
