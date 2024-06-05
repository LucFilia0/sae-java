package planeair.graph;

// Import java

import java.io.File ;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.List ;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList ;

// Import graphstream

import org.graphstream.graph.Node;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.Graphs;

import planeair.util.DataImportation;

/**
 * Does the importation of graphs from a folder automatically
 */
public class Automation {
    private static final int NUMBER_OF_ALGORITHMS = 3 ;
    private static final int WELSH_POWELL = 0 ;
    private static final int DSATUR = 1 ;
    private static final int RLF = 2 ;
    private static final int NB_COLORS = 0 ;
    private static final int NB_CONFLICTS = 1 ;
        
    /**
     * Starts the importation of all the files
     * 
     * @param path Folder from which the files will be imported
     * @param identifiers The file name structures
     * @param placeholder Character that will be replaced by numbers
     * @param colorAttribute Attribute used to color the graphs
     */
    public static void startAutomation(String path, String[] identifiers, char placeholder, String colorAttribute, int numberOfCores) {
        ExecutorService threadPool = Executors.newFixedThreadPool(numberOfCores) ;
        List<TestGraph> graphList = Automation.importDataFromFolder(path, identifiers, placeholder, threadPool) ;
        new File(path + "/4").mkdirs() ;
        for (TestGraph graph : graphList) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    Automation.writeToFile(graph, path, colorAttribute, threadPool) ;
                }
            }) ;
        }
        System.out.println("Done importing") ;
        threadPool.shutdown() ;
    }

    /**
     * Loads all graphs from the folder and stores them in a LinkedList which is returned
     * @param path the folder's path
     * @param identifiers the strings the name will be compared to
     * @param placeholder character that should be replaced by numbers for the check
     * @param threadPool current threadPool used to divide the tasks
     * @return the list of Graphs that were imported ordered by ID
     */
    public static List<TestGraph> importDataFromFolder(String path, String[] identifiers, char placeholder, ExecutorService threadPool) {
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
            
            // Counts the number of files that have been imported
            CountDownLatch latch = new CountDownLatch(files.length) ;
            for (File file : files) {
                threadPool.execute(new Runnable() {
                    public void run() {
                        TestGraph temp = new TestGraph(Automation.isolateNumberInString(file.getName())) ;
                        try {
                            DataImportation.importTestGraphFromFile(temp, file, false);
                            for (Node node : temp) {
                                node.setAttribute("color", 0) ;
                            }
                        }

                        catch (FileNotFoundException e) {
                            System.err.println(e) ;
                        }
        
                        res.addFirst(temp) ;
                        latch.countDown() ;
                    }
                }) ;
            }

            // Waits for all files to be imported
            try {
                latch.await() ;
            }
    
            catch (InterruptedException e) {
                System.out.println(e) ;
            }

            // Sorts the list by graph name
            res.sort(new Comparator<Graph>() {
                @Override
                public int compare(Graph arg0, Graph arg1) {
                    return Integer.compare(Integer.valueOf(arg0.getId()), Integer.valueOf(arg1.getId())) ;
                }
            }) ;
        }

        return res ;
    }

    /**
     * Writes the relevant data to the graph files
     * @param graph Graph whose data is written down
     * @param path Parent folder where the importation took place
     * @param colorAttribute Attributed used to color the graph
     * @param threadPool current threadPool used to divide the tasks
     */
    public static void writeToFile(TestGraph graphIn, String path, String colorAttribute, ExecutorService threadPool) {
        TestGraph graph = Automation.useBestColoringAlgorithm(graphIn, colorAttribute, threadPool) ;
        try {
            File resFile = new File(path + "/4/" + "color-eval" + graph.getId() + ".txt") ;
            if (!resFile.createNewFile()) {
                System.out.println("File #" + graph.getId() + " already exists") ;
            }
            else {
                System.out.println("Writing file #" + graph.getId()) ;
                FileWriter resWriter = new FileWriter(resFile) ;
                List<Node> list = graph.nodes().collect(Collectors.toList()) ;
                list.sort(new Comparator<Node>() {
                    @Override
                    public int compare(Node arg0, Node arg1) {
                        return Integer.compare(Integer.valueOf(arg0.getId()), Integer.valueOf(arg1.getId())) ;
                    }
                }) ;

                for (Node node : list) {
                    resWriter.write(node.getId() + " ; " + node.getAttribute(colorAttribute) + '\n') ;
                }

                resWriter.close() ;
            }

            File conflictFile = new File(path + "/4/coloration-groupe1.4.csv") ;
            FileWriter conflictWriter = new FileWriter(conflictFile, true) ;
            
            conflictWriter.write(resFile.getName() + ';' + Integer.toString(graph.getNbConflicts()) + '\n') ;
            conflictWriter.close() ;
        }

        catch (Exception e) {
            System.err.println(e) ;
        }
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

    /**
     * Finds the best coloring algorithm and returns its solution
     * @param graph Graph that was colored
     * @param colorAttribute Attribute used to color the graph
     * @param threadPool List of threads currently used
     * @return
     */
    public static TestGraph useBestColoringAlgorithm(TestGraph graph, String colorAttribute, ExecutorService threadPool) {

        ArrayList<ArrayList<Integer>> resList = new ArrayList<>() ;
        ArrayList<TestGraph> graphList = new ArrayList<>() ;
        for (int i = 0 ; i < NUMBER_OF_ALGORITHMS ; i++) {
            resList.add(new ArrayList<>()) ;
            graphList.add(new TestGraph(graph.getId())) ;
        }

        // Verifies all 3 threads are done before continuing
        CountDownLatch latch = new CountDownLatch(NUMBER_OF_ALGORITHMS) ;

        // Creates the threads handling the colorations
        Runnable welshPowell = new Runnable() {
            @Override
            public void run() {
                setValuesInLists(graph, graphList, resList, WELSH_POWELL, colorAttribute, latch) ;
            }
        } ;

        Runnable dsatur = new Runnable() {
            public void run() {
                setValuesInLists(graph, graphList, resList, DSATUR, colorAttribute, latch) ;
            };
        } ;

        Runnable rlf = new Runnable() {
            @Override
            public void run() {
                setValuesInLists(graph, graphList, resList, RLF, colorAttribute, latch) ;
            }
        } ;

        threadPool.execute(welshPowell) ;
        threadPool.execute(dsatur) ;
        threadPool.execute(rlf) ;

        try {
            latch.await() ;
        }
        catch (Exception e) {
            System.out.println("Interrupted 🤓🤓🤓") ;
        }

        // Finds the best solution

        int bestAlgorithm = findBestAlgorithmInList(resList) ;

        TestGraph temp  = null ;
        if (bestAlgorithm < graphList.size()) {
            temp = graphList.get(bestAlgorithm) ;
            temp.setAttribute("nbColors", resList.get(bestAlgorithm).get(0)) ;
            temp.setAttribute("nbConflicts", resList.get(bestAlgorithm).get(1)) ;
        }
        return temp ;
    }

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
     * Colors the graph with the selected algorithm, puts it in the graphList and stores the number of colors and 
     * the number of conflicts in resList
     * @param graph
     * @param graphList
     * @param resList
     * @param algorithm
     * @param colorAttribute
     * @param latch
     * 
     */
    public static void setValuesInLists(TestGraph graph, ArrayList<TestGraph> graphList, ArrayList<ArrayList<Integer>> resList, int algorithm, String colorAttribute, CountDownLatch latch) {
        graphList.set(algorithm, (TestGraph)Graphs.clone(graph)) ;
        Coloration.colorationDsatur(graphList.get(DSATUR), colorAttribute, graph.getKMax()) ;
        resList.get(algorithm).add(graph.getNbColors()) ;
        resList.get(algorithm).add(graph.getNbConflicts()) ;
        latch.countDown() ;
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
     * Returns which algorithm in the list has the best solution
     * @param arg0
     * @param arg1
     * @param arg2
     * @return index of the best algorithm
     */
    public static Integer findBestAlgorithmInList(ArrayList<ArrayList<Integer>> list) {

        if (list.isEmpty()) {
            return null ;
        }
        else {
            Integer bestAlgorithm = 0 ;
            Integer currentAlgorithm = 1 ;
            boolean hasLessConflicts ;
            boolean hasLessColors ;
            while (currentAlgorithm < list.size()) {
                hasLessConflicts = list.get(bestAlgorithm).get(NB_CONFLICTS) > list.get(currentAlgorithm).get(NB_CONFLICTS) ;
                hasLessColors = list.get(bestAlgorithm).get(NB_COLORS) > list.get(currentAlgorithm).get(NB_COLORS) ;
                if (hasLessColors || hasLessConflicts) {
                    bestAlgorithm = currentAlgorithm ;
                }
                currentAlgorithm++ ;
            }

            return bestAlgorithm ;
        }


    }
} 