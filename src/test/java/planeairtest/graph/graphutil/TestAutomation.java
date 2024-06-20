package planeairtest.graph.graphutil;

import org.junit.Test;

import static org.junit.Assert.* ;
import planeair.graph.graphutil.Automation;

/**
 * Test Class for the utility methods in Automation
 */
public class TestAutomation {

    private static final char PLACEHOLDER = 'X' ;

    private static final String[] IDENTIFIERS1 = 
        {"salutX.txt", "byeX.txt", "goodY.txt"} ;

    private static final String[] IDENTIFIERS2 = {} ;

    private static final String filename1 = "salut12.txt" ;

    private static final String filename2 = "SALUT15.txt" ;

    private static final String filename3 = 
        "bye283817672672638719326137919612371.txt" ;

    private static final String filename4 = "rienAVoir.txt" ;

    private static final String filename5 = "good5.txt" ;

    private static final String filename6 = "SALUT.txt" ;

    @Test
    public void testIsFileLike() {

        // Test with normal string
        assertTrue(Automation.isFileLike(filename1, IDENTIFIERS1, PLACEHOLDER)) ;

        // Test with upper case string  
        assertFalse(Automation.isFileLike(filename2, IDENTIFIERS1, PLACEHOLDER)) ;

        // Test with a lot of numbers (maybe Integer overflow ?)
        assertTrue(Automation.isFileLike(filename3, IDENTIFIERS1, PLACEHOLDER)) ;

        // Test with completely wrong string
        assertFalse(Automation.isFileLike(filename4, IDENTIFIERS1, PLACEHOLDER)) ;

        // Test with empty identifiers
        assertFalse(Automation.isFileLike(filename1, IDENTIFIERS2, PLACEHOLDER)) ;

        // Test with wrong placeholder
        assertFalse(Automation.isFileLike(filename5, IDENTIFIERS1, PLACEHOLDER)) ;

        // Test with no numbers
        assertFalse(Automation.isFileLike(filename6, IDENTIFIERS1, PLACEHOLDER)) ;

    }

    @Test 
    public void testIsolateNumberInString() {

        // Test for normal behavior
        assertEquals("12", Automation.isolateNumberInString(filename1)) ;

        // Test for no number in string
        assertNull(Automation.isolateNumberInString(filename6)) ;

        // Test for big number
        assertEquals("283817672672638719326137919612371", 
            Automation.isolateNumberInString(filename3));
    }
}
