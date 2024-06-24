package planeairtest.importation;

//#region IMPORTS

	//#region JUNIT
	import org.junit.Before;
	import org.junit.Test;
	import static org.junit.Assert.*;
	//#endregion

	//#region JAVA
	import java.io.File;
	//#endregion

	//#region PLANEAIR
	import planeair.graph.graphtype.TestGraph;
	import planeair.importation.TestGraphImportation;
	//#endregion

	//#region EXCEPTIONS
	import planeair.exceptions.InvalidFileFormatException;
	import java.io.FileNotFoundException;
	//#endregion

//#endregion

/**
 * Test for the importation of the TestGraphs
 * 
 * @author Luc le Manifik
 */
public class ImportationTestGraphTest {

	//#region ATTRIBUTES

	/**
	 * The TestGraph we wish to test
	 */
	private TestGraph testGraph;

	//#endregion
	
	/**
	 * This procedure is launched before the importation test
	 * 
	 * @author Luc le Manifik
	 */
	@Before
	public void init() {
		this.testGraph = new TestGraph("Test-TestGraph");
	}
	
	/**
	 * This method tests the importation of the TestGraph when no error must be thrown.
	 * In RegEx we trust
	 * 
	 * @author Luc le Manifik
	 */
	@Test
	public void testImportTestGraphFromFile() {
		File file = null;
		try {
			file = new File("src/test/java/planeairtest/testfiles/shitty-graph.txt");
		}catch(NullPointerException e) {
			e.printStackTrace();
		}

		try {
			TestGraphImportation.importTestGraphFromFile(testGraph, file, false);
		}catch(FileNotFoundException fnfe) {
			System.err.println("File not found, you dummy");
		}catch(InvalidFileFormatException iffe) {
			fail("File is not well treated : " + iffe.getMessage());
		}

		assertTrue(this.testGraph.getNbMaxNodes() == 11);
		assertTrue(this.testGraph.getNodeCount() == 11);
		assertTrue(this.testGraph.getEdgeCount() == 20);
		assertTrue(this.testGraph.getKMax() == 2);
	}

	/**
	 * Tests if the importation thrown errors when supposed to. Meaning when there is not
	 * enough informations on a line.
	 * I'm too lazy to make the program searching for the missing information
	 * on the next line. Maybe next time.
	 * Not sure it is a good idea, though...
	 * 
	 * @author Luc le Manifik
	 */
	@Test
	public void testWrongTestGraphImportation() {

		File wrongFile = null; // Not enough information on a line
		
		try {
			wrongFile = new File("src/test/java/planeairtest/testfiles/wrong-graph.txt");
		}catch(NullPointerException npe) {
			System.err.println("Null pointer passed you moron");
		}

		try {
			TestGraphImportation.importTestGraphFromFile(testGraph, wrongFile, false);
			fail("Exception not found");
		}catch(FileNotFoundException fnfe) {
			System.err.println(fnfe.getMessage());
			fail("File not found you little piece of... paper");
		}catch(InvalidFileFormatException iffe) {
			System.out.println("Exception catched");
		}
	}
}
