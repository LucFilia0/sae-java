package planeairtest.importation;

//#region IMPORTS

import java.io.File;
	
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
	
import planeair.graph.graphtype.FlightsIntersectionGraph;
import planeair.importation.FIGImportation;
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
public class FIGImportationTest {

	//#region ATTRIBUTES

	/**
	 * The File which contains the Airports' informations
	 */
	private File airportFile;

	/**
	 * The File which contains the Flights' informations
	 */
	private File flightFile;

	/**
	 * File containing Flights' informations.
	 * This File contains missing informations on certain lines.
	 * Used to test if the exceptions are well catched.
	 */
	private File wrongFlightFile;

	/**
	 * File containing Airports' informations.
	 * This File contains missing informations on certain lines.
	 * Used to test if the exceptions are well catched.
	 */
	private File wrongAirportFile;

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
		
		this.airportFile 		= null;
		this.flightFile 		= null;

		this.wrongFlightFile	= null;
		this.wrongAirportFile	= null;

		try {
			this.airportFile = new File("src/test/java/planeairtest/testfiles/shitty-airports.csv");
			this.flightFile = new File("src/test/java/planeairtest/testfiles/shitty-flights.csv");

			this.wrongFlightFile = new File("src/test/java/planeairtest/testfiles/wrong-flights.csv");
			this.wrongAirportFile = new File("src/test/java/planeairtest/testfiles/wrong-airports.csv");
		}catch(NullPointerException npe) {
			System.err.println(npe.getMessage());
			fail("mf");
		}
	}

	/**
	 * This method is used to test if the Airports' importation
	 * runs correctly
	 * 
	 * @author Luc le Manifik
	 */
	@Test
	public void testImportAirportsFromFile() {

		this.airportSet = new AirportSet();

		try {
			FIGImportation.importAirportsFromFile(airportSet, airportFile);
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
	 * This method is used to test if the Flights' importation
	 * runs correctly
	 * 
	 * @author Luc le Manifik
	 */
	@Test
	public void testImportFlightsFromFile() {

		this.fig = new FlightsIntersectionGraph("FIG");

		// Import the Airports if the former test has not ran
		if(this.airportSet == null) {
			testImportAirportsFromFile();
		}

		try {
			FIGImportation.importFlightsFromFile(airportSet, fig, flightFile);
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
			assertFalse(fig.getNode(flightIds[fId]) == null); // Verifying if Flights exist
		}
	}

	/**
	 * This method verifies if the InvalidFileFormatException exception is correctly thrown
	 * when there are some missing informations on Airports' File's lines.
	 * 
	 * @author Luc le Manifik
	 */
	@Test
	public void testWrongAirportFile() {

		this.airportSet = new AirportSet();

		try {
			FIGImportation.importAirportsFromFile(airportSet, wrongAirportFile);
			fail("Exception not detected");
		}catch(FileNotFoundException fnfe) {
			System.err.println(fnfe.getMessage());
			fail("File not found you dummy");
		}catch(InvalidFileFormatException iffe) {
			System.out.println("Exception catched");
		}
	}

	/**
	 * This method verifies if the InvalidFileFormatException exception is correctly thrown
	 * when there are some missing informations on the Flights' File's lines.*
	 * 
	 * @author Luc le Manifik 
	 */
	@Test
	public void testWrongFlightFile() {

		if(this.airportSet == null) {
			testImportAirportsFromFile();
		}

		this.fig = new FlightsIntersectionGraph("wrong-FIG");

 		try {
			FIGImportation.importFlightsFromFile(airportSet, fig, wrongFlightFile); 
			fail("Exception not detected");
		}catch(FileNotFoundException fnfe) {
			System.err.println(fnfe.getMessage());
			fail("File not found you dummy");
		}catch(InvalidFileFormatException iffe) {
			System.out.println("Exception catched");
		}
	}
}
