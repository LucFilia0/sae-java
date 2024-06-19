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
import java.io.FileNotFoundException;

import planeair.exceptions.InvalidFileFormatException;
//#region PLANEAIR
	import planeair.graph.graphtype.TestGraph;
	import planeair.importation.ImportationTestGraph;
	//#endregion

//#endregion

/**
 * This class test is made to test the importation of the TestGraphs
 * 
 * @author Luc le Manifik
 */
public class TestGraphImportationTest {

	/**
	 * The TestGraph we wish to test
	 */
	private TestGraph testGraph;
	
	/**
	 * This procedure is launched before the importation test
	 * 
	 * @author Luc le Manifik
	 */
	@Before
	public void init() {
		this.testGraph = new TestGraph("test");
	}
	
	@Test
	public void testImportTestGraphFromFile() {
		File file = null;
		try {
			file = new File("src/test/java/planeairtest/importation/testfiles/shitty-graph1.txt");
		}catch(NullPointerException e) {
			e.printStackTrace();
		}

		try {
			ImportationTestGraph.importTestGraphFromFile(testGraph, file, false);
		}catch(FileNotFoundException fnfe) {
			System.err.println("File not found, you dummy");
		}catch(InvalidFileFormatException iffe) {
			fail("File is not well treated : " + iffe.getMessage());
		}

		System.out.println("done fdp");
	}
}
