package planeairtest.importation;

//#region IMPORTS

import java.io.File;
	
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
	
import planeair.graph.graphtype.FlightsIntersectionGraph;
import planeair.importation.ImportationFIG;
import planeair.util.AirportSet;

import java.io.FileNotFoundException;
import planeair.exceptions.InvalidEntryException;
import planeair.exceptions.InvalidFileFormatException;
import planeair.exceptions.ObjectNotFoundException;

//#endregion

/**
 * Test for the importation of the AirportSet and the FIG
 * 
 * @author Luc le Manifik
 */
public class ImportationFIGTest {

	//#region ATRIBUTES

	/**
	 * The File which contains the Airports' informations
	 */
	private File airportFile;

	/**
	 * The File which contains the Flights' informations
	 */
	private File flightFile;

	/**
	 * The AirportSet which will contains the imported Airports
	 */
	private AirportSet airportSet;
	
	/**
	 * The FIG which will contains the imported Flights
	 */
	private FlightsIntersectionGraph fig;

	//#endregion

	/**
	 * This method is used to initiate the attributes used in the Tests
	 */
	@Before
	public void init() {
		this.airportFile = null;
		this.flightFile = null;
		try {
			airportFile = new File("src/test/java/planeairtest/testfiles/shitty-airports.csv");
			flightFile = new File("src/test/java/planeairtest/testfiles/vol-test3.csv");
		}catch(NullPointerException npe) {
			System.err.println(npe.getMessage());
			fail("mf");
		}
	}

	/**
	 * This method is used to test the Airports' importation
	 */
	@Test
	public void testImportAirportsFromFile() {

		this.airportSet = new AirportSet();

		try {
			ImportationFIG.importAirportsFromFile(airportSet, airportFile);
		}catch(InvalidFileFormatException | FileNotFoundException e) {
			System.err.println(e.getMessage());
			fail("File can't be read, mf");
		}

		String[] airportNames = new String[] {"AGF", "AJA", "ANG", "NCY", "AUR", "AUF", "AVN", "BAE", "BIA", "BVA", "EGC"};
		for(int apId = 0; apId < airportNames.length; ++apId) {
			try {
				this.airportSet.getAirport(airportNames[apId]);
			}catch(ObjectNotFoundException onfe) {
				System.err.println(onfe.getMessage());
				fail("Airport's name not found. Importation goes wrong");
			}
		}
	}

	/**
	 * This method is used to test the Flights' importation
	 */
	@Test
	public void testImportAirportFromFile() {

		this.fig = new FlightsIntersectionGraph("Test-FIG");

		// Import the Airports if the former test has not ran
		if(this.airportSet == null) {
			testImportAirportsFromFile();
		}

		try {
			ImportationFIG.importFlightsFromFile(airportSet, fig, flightFile, FlightsIntersectionGraph.DEFAULT_SECURITY_MARGIN);
		}catch(FileNotFoundException | InvalidEntryException e) {
			System.err.println(e.getMessage());
			fail("File can't be read you dumbass");
		}

		String[] flightIds = new String[] {
			"AF611091",
			"AF210361",
			"AF601038",
			"AF983822",
			"AF70051",
			"AF770600",
			"AF255917",
			"AF676971",
			"AF753319",
			"AF125314",
			"AF174393"
		};

		for(int fId = 0; fId < flightIds.length; ++fId) {
			assertFalse(fig.getNode(flightIds[fId]) == null);
		}
	}
}
