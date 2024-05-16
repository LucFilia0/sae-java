package graph;

import java.io.File ;
import java.io.FileFilter;
import java.util.List ;

import org.graphstream.graph.Graph;

import java.util.Comparator;
import java.util.LinkedList ;

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
        TestGraph temp ;
        if (files != null) {
            for (File file : files) {
                temp = new TestGraph(Automatisation.isolateNumberInString(file.getName())) ;
                try {
                    temp.importFromFile(file) ;
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
        while (i < length && input.charAt(i) < '0' || input.charAt(i) > '9') {
            i++ ;
        }

        if (i < length) {
            while (i < length && input.charAt(i) >= '0' && input.charAt(i) <= '9') {
                num.append(input.charAt(i)) ;
                i++ ;
            }
        }

        else {
            return null ;
        }

        return num.toString() ;
    }
    
} 
