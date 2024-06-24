package planeairtest.graph.coloring;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Before;

import planeair.exceptions.InvalidFileFormatException;
import planeair.graph.graphtype.TestGraph;
import planeair.importation.TestGraphImportation;

public class TestColoring {
        
    /**
     * First graph we will test
     */
    protected TestGraph testGraph1 ;

    /**
     * Second graph we will test
     */
    protected TestGraph testGraph2 ;

    @Before
    public void setup() {
        testGraph1 = new TestGraph("test1") ;
        testGraph2 = new TestGraph("test2") ;
        try {
            TestGraphImportation.importTestGraphFromFile(testGraph1, new File
                ("src/test/java/planeairtest/testfiles/testgraph1.txt"), 
                false) ;
            TestGraphImportation.importTestGraphFromFile(testGraph2, new File
                ("src/test/java/planeairtest/testfiles/testgraph2.txt"), 
                false);
        }catch(FileNotFoundException fnfe) {
			System.err.println("File not found, you dummy");
		}catch(InvalidFileFormatException iffe) {
			fail("File is not well treated : " + iffe.getMessage());
		}
    }
}
