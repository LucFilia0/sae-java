package planeair.graph.graphutil;

//#region IMPORTS
import java.io.File ;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.Comparator;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;

import planeair.graph.coloring.ColoringAlgorithm;
import planeair.graph.coloring.ColoringUtilities;
import planeair.graph.graphtype.TestGraph;
import planeair.importation.ImportationTestGraph;
//#endregion

/**
 * Automates every step of the Coloring Challenge where we 
 * have to import 20 graphs, color them and store the results.
 * 
 * @author Nathan LIEGEON
 */
public abstract class Automation {
    //#region ATTRIBUTES

    /**
     * Default folder where the solution will be stored
     */
    private static final String FOLDER_PATH = "/4/" ;

    //#endregion

    //#region MAIN
        
    /**
     * Starts the importation of all the files
     * 
     * @param path Folder from which the files will be imported
     * @param identifiers The file name structure(s)
     * @param placeholder Character that will be replaced by numbers
     * 
     * @author Nathan LIEGEON
     */
    public static void startAutomation(String path, String[] identifiers, char placeholder, int numberOfCores) {
        long start = System.nanoTime() ;
        ExecutorService threadPool = Executors.newFixedThreadPool(numberOfCores) ;
        TreeSet<TestGraph> graphSet = Automation.importDataFromFolder
            (path, identifiers, placeholder, threadPool) ;
        
        new File(path + FOLDER_PATH).mkdirs() ;

        for (TestGraph graph : graphSet) {
            TestGraph coloredGraph = Automation.useBestColoringAlgorithm(graph, threadPool) ;
            Automation.writeToFile(coloredGraph, path) ;
                
        }

        System.out.println("Done Writing") ;
        System.out.println("Time taken : " + (double)((System.nanoTime() - start)) /1000000000);
        threadPool.shutdown() ;
    }

    /**
     * Loads all graphs from the folder and stores them in a LinkedList which is returned
     * 
     * @param path the folder's path
     * @param identifiers the strings the name will be compared to
     * @param placeholder character that should be replaced by numbers for the check
     * @param threadPool current threadPool used to divide the tasks
     * @return the list of Graphs that were imported ordered by ID
     * 
     * @author Nathan LIEGEON
     */
    public static TreeSet<TestGraph> importDataFromFolder(String path, String[] identifiers, char placeholder, ExecutorService threadPool) {
        TreeSet<TestGraph> res = new TreeSet<>(new Comparator<TestGraph>() {
            @Override
            public int compare(TestGraph o1, TestGraph o2) {
                return Integer.compare(Integer.valueOf(Automation.isolateNumberInString(o1.getId())), 
                    Integer.valueOf(Automation.isolateNumberInString(o2.getId()))) ;
            }
        }) ;

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
                        TestGraph temp = new TestGraph(file.getName()) ;
                        try {
                            ImportationTestGraph.importTestGraphFromFile(temp, file, false);
                            res.add(temp) ;
                        }

                        catch (FileNotFoundException e) {
                            System.err.println(e) ;
                        }
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
        }

        return res ;
    }

    /**
     * Writes the relevant data to the graph files
     * 
     * @param graph Graph whose data is written down
     * @param path Parent folder where the importation took place
     * @param threadPool current threadPool used to divide the tasks
     * 
     * @author Nathan LIEGEON
     */
    public static void writeToFile(TestGraph graph, String path) {
        try {
            File resFile = new File(path + FOLDER_PATH + "colo-eval" + Automation.isolateNumberInString(graph.getId()) + ".txt") ;
            if (!resFile.createNewFile()) {
                System.out.println("File " + graph.getId() + " already exists\n") ;
                return ;
            }

            System.out.println("Writing file " + graph.getId() + "\n") ;
            FileWriter resWriter = new FileWriter(resFile) ;
            TreeSet<Node> set = new TreeSet<>(new Comparator<Node>() {
                @Override
                public int compare(Node arg0, Node arg1) {
                    return Integer.compare(Integer.valueOf(arg0.getId()), Integer.valueOf(arg1.getId())) ;
                }
            }) ;

            graph.nodes().forEach(n -> set.add(n)) ;
            for (Node node : set) {
                resWriter.write(node.getId() + " ; " + node.getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE) + '\n') ;
            }

            resWriter.close() ;

            File conflictFile = new File(path + FOLDER_PATH + "coloration-groupe1.4.csv") ;
            FileWriter conflictWriter = new FileWriter(conflictFile, true) ;
            
            conflictWriter.write(graph.getId() + ";" + Integer.toString(graph.getNbConflicts()) + '\n') ;
            conflictWriter.close() ;
        }

        catch (Exception e) {
            System.err.println(e) ;
        }
    }

    //#endregion

    //#region UTILITY

    /**
     * Recovers the first number in the String and returns it
     * 
     * @param input String containing the Integer
     * @return The String contraining the isolated Integer, null if there wasn't any
     * 
     * @author Nathan LIEGEON
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
     * @param threadPool List of threads currently used
     * @return
     * 
     * @author Nathan LIEGEON
     */
    public static TestGraph useBestColoringAlgorithm(TestGraph graph, ExecutorService threadPool) {
        final int ALGORITHM_COUNT = ColoringAlgorithm.algorithmList().length ;
        ArrayList<TestGraph> graphList = new ArrayList<>() ;
        for (int i = 0 ; i < ALGORITHM_COUNT ; i++) {
            graphList.add((TestGraph)Graphs.clone(graph)) ;
        }

        // Verifies all 3 threads are done before continuing
        CountDownLatch latch = new CountDownLatch(ALGORITHM_COUNT) ;

        // Creates the threads handling the colorings
        for (Integer algorithmId = 0 ; algorithmId < 
            ALGORITHM_COUNT ; algorithmId++) {

            ColoringAlgorithm currentAlgorithm = ColoringAlgorithm.algorithmList()[algorithmId] ;
            TestGraph graphColored = graphList.get(algorithmId);
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    ColoringUtilities.colorGraphWithChosenAlgorithm(
                        graphColored, currentAlgorithm) ;
                    latch.countDown() ;
                }
            }) ;
        }

        try {
            latch.await() ;
        } catch (Exception e) {
            System.out.println("Interrupted 🤓🤓🤓") ;
        }
        // Finds the best solution

        ColoringAlgorithm bestAlgorithm = findBestAlgorithmInList(graphList) ;
        System.out.println(bestAlgorithm) ;

        TestGraph temp  = null ;
        if (bestAlgorithm.getId() < graphList.size()) {
            temp = graphList.get(bestAlgorithm.getId()) ;
        }

        return temp ;
    }

    /**
     * Tells whether or not a file is of a similar form to the identifier
     * ex : identifier is "testGraphX.txt" filename is 
     * "testGraph12.txt", placeholder is 'X'
     * The function will return true for the example
     * 
     * @param filename name your are testing
     * @param identifier identifier the filename will be compared to
     * @param placeholder character which should be replaced by numbers
     * @return boolean telling whether the file was similar to the 
     * identifier or not
     * 
     * @author Nathan LIEGEON
     */
    public static boolean isFileLike(String filename, String identifier, 
                                    char placeholder) {
        // Initialisation
        boolean res = true ;
        int offset = 0, filenameLength = filename.length(), identifierLength = identifier.length();
        char filenameBuffer, identifierBuffer ;

        // If string is too short it can't be similar
        if (filenameLength < identifierLength) {
            return false ;
        }

        int i = 0 ;
        while (res && (i + offset) < filenameLength) {
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

            else if (filenameBuffer != identifierBuffer) {
                res = false ;
            }
            
            i++ ;
        }

        return res ;
    }

    /**
     * Tells whether or not a file is of a similar form to one of the identifiers
     * ex : identifier is "testGraphX.txt" filename is "testGraph12.txt", placeholder is 'X'
     * The function will return true for the example
     * This function is also case sensitive
     * 
     * @param filename name your are testing
     * @param identifier array containing different identifiers the filename will be compared to
     * @param placeholder character which should be replaced by numbers
     * @return boolean telling whether the file was similar to one of the identifiers or not
     * 
     * @author Nathan LIEGEON
     */
    public static boolean isFileLike(String filename, String[] identifiers, 
                                    char placeholder) {
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
     * 
     * @param list the list we are checking
     * @return index of the best algorithm or null if the list is empty
     * 
     * @author Nathan LIEGEON
     */
    public static ColoringAlgorithm findBestAlgorithmInList(ArrayList<TestGraph> list) {

        if (list.isEmpty()) {
            return null ;
        }
        
        Integer bestAlgorithm = 0 ;
        Integer currentAlgorithm = 1 ;
        boolean hasLessConflicts ;
        boolean hasLessColors ;

        while (currentAlgorithm < list.size()) {
            hasLessConflicts = list.get(bestAlgorithm).getNbConflicts() 
                > list.get(currentAlgorithm).getNbConflicts() ;

            hasLessColors = list.get(bestAlgorithm).getNbColors() 
                > list.get(currentAlgorithm).getNbColors() ;

            if (hasLessColors || hasLessConflicts) {
                bestAlgorithm = currentAlgorithm ;
            }
            currentAlgorithm++ ;
        }
        
        return ColoringAlgorithm.algorithmList()[bestAlgorithm] ;

    }

    //#endregion
} 
