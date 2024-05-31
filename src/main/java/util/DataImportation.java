package util;

//-- Import Java

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//-- Import GraphStream

import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.IdAlreadyInUseException;

//-- Import Plane AIR

import graph.Flight;
import graph.FlightFactory;
import graph.FlightsIntersectionGraph;
import graph.TestGraph;

//-- Import Exceptions

import exceptions.InvalidCoordinateException;
import exceptions.InvalidEntryException;
import exceptions.InvalidFileFormatException;
import exceptions.ObjectNotFoundException;

/**
 * <html>
 * This class contains all the functions/procedures linked to data importation.
 * <hr>
 * In this project, three types of files are mainly used : 
 *      <ul>
 *          <li>
 *              <h4>For the TestGraphs : "graph-testX.txt"</h4>
 *              With "X" being a number. These files are in the "txt" format, and contains all the required informations
 *              to build TestGraphs (with GraphStream)
 *          </li>
 * 
 *          <li>
 *              <h4>For the Map : "aeroports.csv" and "vols-testX.csv"</h4>
 *              <ol>
 *                  <li>
 *                      "aeroports.csv" contains all data of the Airports, which will be stored in an AirportSet.
 *                  </li>
 *                  <li>                 
 *                      "vols-testX.csv" contains all data of the Flights we wish to simulate.
 *                      <strong>The Flights NEEDS the Airports</strong>, and need to be imported after them.
 *                  </li>
 *              </ol>
 *          </li>
 *      </ul>
 * The files are formated due to certain rules, in order to find the right informations at the right place.
 * So, if a file can't be read because of some errors (mistakes or not), it may endanger the entire application.
 * <hr>
 * In order to prevent such a mess to happen, data will be checked before to be fully imported in the application's objects,
 * and messages will be prompt for the attention of the user, even if these errors are not blocking the programm to execute.
 * With that, the user will be informed of all the errors that are present in the files.
 * </html>
 * 
 * @author Luc le grand, que dis-je, le <strong>Manifik</strong>
 */
public abstract class DataImportation {

    /**
     * The RegEx which is used to clean the lines of the files. It only accepts ";", numbers and letters.
     * All the other caracters are ignored.
     * 
     * @author Luc le Manifik
     */
    public static final String REGEX_FLIGHTS_AIRPORTS = "[^;0-9a-zA-Z]";

    /**
     * Adds the spaces for the importation of the Nodes, in the TestGraphs
     */
    public static final String REGEX_TEST_GRAPH = "[^ 0-9]";

    /*
     * ===============================================
     * TestGGraph's Importation
     * ===============================================
     */

    public static void importTestGraphFromFile(TestGraph testGraph, File testGraphFile, boolean showErrorMessages) throws FileNotFoundException {

        boolean kMaxImported = false;
        boolean nbNodesImported = false;

        Scanner lineScanner = null;

        int currentLine = 0;

        try {
            lineScanner = new Scanner(testGraphFile);
        }catch(FileNotFoundException fnfe) {
            throw fnfe;
        }

        try {
            // First Line : get kMax
            kMaxImported = importKMax(testGraph, lineScanner, currentLine, showErrorMessages);

            // Second Line : get NbMaxNodes
            nbNodesImported = importNbNodes(testGraph, lineScanner, currentLine, showErrorMessages);
        }catch(InvalidFileFormatException iffe) {
            throw iffe;
        }

        // Checks if the TestGraph Data
        if(!kMaxImported && !nbNodesImported && !lineScanner.hasNextLine()) {
            throw new InvalidFileFormatException(currentLine, "The maximum amount of colors, and/or the number of nodes could not be correctly imported at this point of the source file.");
        }

        try {
            createTestGraph(testGraph, lineScanner, currentLine, showErrorMessages);
        }catch(InvalidFileFormatException iffe) {
            throw iffe;
        }


    }

    /**
     * This methods is scanning the source file until it founds the k-max value.
     * 
     * @param testGraph ({@link graph.TestGraph TestGraph}) - The TestGraph we are currently importing
     * @param lineScanner ({@link java.util.Scanner Scanner}) - The Scanner which is currently reading the source File
     * @param currentLine (Integer) - The source File's current line 
     * @param showErrorMessages (boolean) - If "True", then some minor error messages will be prompt. Nothing will be prompt if you pu "False"
     * 
     * @throws InvalidFileFormatException
     * 
     * @return kMaxImported (boolean) - Return true if the kMax value is found in the file, else false
     * 
     * @author Luc le Manifik
     */
    @SuppressWarnings("resource")
    private static boolean importKMax(TestGraph testGraph, Scanner lineScanner, Integer currentLine, boolean showErrorMessages) throws InvalidFileFormatException {

        boolean kMaxImported = false;

        String line = null;
        Scanner dataScanner = null;

        while(!kMaxImported && lineScanner.hasNextLine()) {

            ++currentLine;
            line = lineScanner.nextLine().replaceAll(DataImportation.REGEX_TEST_GRAPH, "");
            dataScanner = new Scanner(line);

            if(dataScanner.hasNext()) {
                try {
                    testGraph.setKMax(Integer.parseInt(dataScanner.next()));
                    kMaxImported = true; // If the boolean is not turned to "True", then it means that the line is empty, and the Scanner will try to read the next line.
                }catch(NumberFormatException nfe) {
                    lineScanner.close();
                    dataScanner.close();
                    throw new InvalidFileFormatException(currentLine, "The maximum amount of colors can't be read. The TestGraph can not be correctly imported.");
                }catch(InvalidEntryException iee) {
                    lineScanner.close();
                    dataScanner.close();
                    throw new InvalidFileFormatException(currentLine, "The maximum amount of colors must be a positive number.");
                }

                if(showErrorMessages && dataScanner.hasNext()) { // Check if there is an other value on the line. Then prompts an error, but continue the execution of the program
                    System.err.println("Error at Line " + currentLine +  " : Too many informations on line");
                }
            }

            dataScanner.close();
        }

        return kMaxImported;
    }

    /**
     * This methods is scanning the source file until it founds the number of nodes value.
     * 
     * @param testGraph ({@link graph.TestGraph TestGraph}) - The TestGraph we are currently importing
     * @param lineScanner ({@link java.util.Scanner Scanner}) - The Scanner which is currently reading the source File
     * @param currentLine (Integer) - The source File's current line 
     * @param showErrorMessages (boolean) - If "True", then some minor error messages will be prompt. Nothing will be prompt if you pu "False"
     * 
     * @throws InvalidFileFormatException
     * 
     * @return nbNodesImported (boolean) - Return true if the number of nodes value is found in the file, else false
     * 
     * @author Luc le Manifik
     */
    @SuppressWarnings("resource")
    private static boolean importNbNodes(TestGraph testGraph, Scanner lineScanner, Integer currentLine, boolean showErrorMessages) throws InvalidFileFormatException {
        
        boolean nbNodesImported = false;

        String line = null;
        Scanner dataScanner = null;

        while(!nbNodesImported && lineScanner.hasNextLine()) {

            ++currentLine;
            line = lineScanner.nextLine().replaceAll(DataImportation.REGEX_TEST_GRAPH, "");
            dataScanner = new Scanner(line);

            if(dataScanner.hasNext()) {
                try {
                    testGraph.setNbMaxNodes(Integer.parseInt(dataScanner.next()));
                    nbNodesImported = true; // If the boolean is not turned to "True", then it means that the line is empty, and the Scanner will try to read the next line.
                }catch(NumberFormatException nfe) {
                    lineScanner.close();
                    dataScanner.close();
                    throw new InvalidFileFormatException(currentLine, "The number of nodes can't be read. The TestGraph can not be correctly imported.");
                }catch(InvalidEntryException iee) {
                    lineScanner.close();
                    dataScanner.close();
                    throw new InvalidFileFormatException(currentLine, "The number of nodes must be a positive number.");
                }

                if(showErrorMessages && dataScanner.hasNext()) { // Check if there is an other value on the line. Then prompts an error, but continue the execution of the program
                    System.err.println("Error at Line " + currentLine +  " : Too many informations on line");
                }
            }

            dataScanner.close();
        }

        return nbNodesImported;
    }

    @SuppressWarnings("resource")
    private static void createTestGraph(TestGraph testGraph, Scanner lineScanner, Integer currentLine, boolean showErrorMessages) throws InvalidFileFormatException {
        
        String line = null;
        Scanner nodeScanner = null;

        int nbMaxNodes = testGraph.getNbMaxNodes();
        int nbNodes = 0;
        int nbEdges = 0;

        String idNodeA = null;
        String idNodeB = null;

        while(lineScanner.hasNextLine()) {

            ++currentLine;
            line = lineScanner.nextLine();
            line = line.replaceAll(DataImportation.REGEX_TEST_GRAPH, "");
            nodeScanner = new Scanner(line);

            // Get node A
            if(nodeScanner.hasNext()) {
                idNodeA = nodeScanner.next();
                // Get node B
                if(nodeScanner.hasNext()) {
                    idNodeB = nodeScanner.next();
                }else { // If there is no information for the second node on the line
                    nodeScanner.close();
                    throw new InvalidFileFormatException(currentLine, "Not enough information to create edge. Missing second node.");
                }
            }
            
            // Checks if there is too much informations on the line, then print a message error, but continue the execution
            if(showErrorMessages && nodeScanner.hasNext()) {
                System.err.println("Error at Line " + currentLine + " : Too many informations on line");
            }

            // Adds the nodes and increment node number if they do not already exist
            if(testGraph.getNode(idNodeA) == null) {
                testGraph.addNode(idNodeA);
                ++nbNodes;
            }
            if(testGraph.getNode(idNodeB) == null) {
                testGraph.addNode(idNodeB);
                ++nbNodes;
            }

            // Checks if there is not too much nodes that have been added
            if(nbNodes <= nbMaxNodes) {
                try {
                    testGraph.addEdge(idNodeA + "-" + idNodeB, idNodeA, idNodeB);
                    ++nbEdges;
                }
                // Errors are treated here, because they do not require to stop the program, and just need to prompt some informations
                catch(IdAlreadyInUseException iaiue) {
                    // -> If an edge with the same id already exists and strict checking is enabled
                    if (showErrorMessages) {
                        System.err.println("Error at Line " + currentLine + " : Edge " + idNodeA + "-" + idNodeB + " already exists.");
                    }
                }catch(ElementNotFoundException enfe) {
                    // -> If strict checking is enabled, and 'node1' or 'node2' are not registered in the graph
                    if (showErrorMessages) {
                        System.err.println("Error at Line " + currentLine + " : Node [" + idNodeA + "] or node [" + idNodeB + "] undeclared.");
                    }
                }catch(EdgeRejectedException ere) {
                    // -> If strict checking is enabled and the edge is not accepted
                    if (showErrorMessages) {
                        System.err.println("Error at Line " + currentLine + " : Trying to add edge [" + idNodeA + "-" + idNodeB + "] but edge [" + idNodeB + "-" + idNodeA + "] seems to already exist");
                    }
                }
            }else {
                nodeScanner.close();
                throw new InvalidFileFormatException(currentLine, "The number of created nodes exceeds the maximum amount specified in the source file.");
            }
        }

        // What if there is not enough node ?
        while(nbNodes < nbMaxNodes) {
            testGraph.addNode(String.valueOf(nbNodes));
            ++nbNodes;
        }

        testGraph.setNbNodes(nbNodes);
        testGraph.setNbEdges(nbEdges);
    }

    /*
     * ===============================================
     * Airports' Importation
     * ===============================================
     */

    /**
     * Imports the Airports from a File passed in parameter. They are automatically added to the AirportSet.
     * 
     * @param airportsFile ({@link java.io.File File}) - The File read.
     *
     * @throws FileNotFoundException Threw if the source File is not found or does not exist
     * @throws InvalidFileFormatException Threw if the File is not found or does not exist 
     * 
     * @author Luc le Manifik
     */
    public static void importAirportsFromFile(AirportSet airportSet, FlightsIntersectionGraph fig, File airportsFile) throws FileNotFoundException, InvalidFileFormatException {
        
        Scanner scanLine = null;

        try {
            scanLine = new Scanner(airportsFile);
        }catch(FileNotFoundException fnfe) {
            throw fnfe;
        }

        String line;
        int currentLine = 0; // The line currently read in the source file. To report errors to the user.

        while(scanLine.hasNextLine()) {
            ++currentLine;
            line = scanLine.nextLine();
            if(line.charAt(0) != '\n') { // Check if the line is not just a blank line
                try {
                    DataImportation.createAirportFrom(airportSet, fig, line, currentLine); // Creates an Airport with the informations of the line.
                }catch(InvalidFileFormatException errorInFile) {
                    scanLine.close();
                    // All errors are threw as InvalidFileFormatException, because the File is not in the correct format, non-dependant of which error precisely
                    throw new InvalidFileFormatException(currentLine, errorInFile.getMessage());
                }
            }
        }

        scanLine.close();
    }

    /**
     * Creates and add an Airport to the AirportSet, by reading the line (String) passed in parameter. 
     * 
     * @param airportSet ({@link util.AirportSet AirportSet}) - The AirportSet in which are imported the Airports
     * @param fig ({@link graph.FlightsIntersectionGraph FlightIntersectionGraph}) - The FIG in which are imported the Flights
     * @param line (String) - The String that is read and contains the informations about the Airport.
     * @param currentLine (int) - The current line in the source file. Used to report errors.
     * 
     * @throws InvalidFileFormatException Threw if the source file does not match the required format. So if there is an error on the line.
     * 
     * @author Luc le Manifik
     */
    private static void createAirportFrom(AirportSet airportSet, FlightsIntersectionGraph fig, String line, int currentLine) throws InvalidFileFormatException {

        String okLine = line.replaceAll(DataImportation.REGEX_FLIGHTS_AIRPORTS, ""); // Suppress all the useless spaces

        Scanner scanAirport = new Scanner(okLine);
        scanAirport.useDelimiter("[;\0]");

        String string_attribute; // Store the different attributes of the Airport
        int currentAttribute = 0; // Counter, incremented to pass the attributes in the while loop (with the switch).

        // All the Strings that will contain the informations of the Airport, by reading the source file.
        String s_name = "", s_location = "", s_latitudeDegree = "", s_latitudeMinutes = "", s_latitudeSeconds = "", s_longitudeDegree = "", s_longitudeMinutes = "", s_longitudeSeconds = "";
        char s_latitudeDirection = ' ', s_longitudeDirection = ' ';

        int latitudeDegree = 0, latitudeMinutes = 0, latitudeSeconds = 0;
        int longitudeDegree = 0, longitudeMinutes = 0, longitudeSeconds = 0;
        
        while(scanAirport.hasNext()) {
            string_attribute = scanAirport.next();
            switch(currentAttribute) {
                case 0 :
                    s_name = string_attribute; // We suppose the code of the Airport is not obligatory written capital letters 
                    break;
                case 1 :
                    s_location = string_attribute;
                    break;
                case 2 :
                    s_latitudeDegree = string_attribute;
                    break;
                case 3 :
                    s_latitudeMinutes = string_attribute;
                    break;
                case 4 :
                    s_latitudeSeconds = string_attribute;
                    break;
                case 5 :
                    s_latitudeDirection = string_attribute.toUpperCase().charAt(0);
                    break;
                case 6 :
                    s_longitudeDegree = string_attribute;
                    break;
                case 7 :
                    s_longitudeMinutes = string_attribute;
                    break;
                case 8 :
                    s_longitudeSeconds = string_attribute;
                    break;
                case 9 :
                    s_longitudeDirection = string_attribute.toUpperCase().charAt(0);
                    break;
                default :
                    System.err.println("Error at Line " + currentLine + " : More informations than required.");
                    break;
            }
            ++currentAttribute;
        }
        scanAirport.close();

        // Ensure we have all the required informations
        if(currentAttribute < 9) {
            throw new InvalidFileFormatException(currentLine, "Missing informations to correctly create the Airport");
        }

        // Cast the coordinates String informations to int
        try {
            latitudeDegree = Integer.parseInt(s_latitudeDegree);
            latitudeMinutes = Integer.parseInt(s_latitudeMinutes);
            latitudeSeconds = Integer.parseInt(s_latitudeSeconds);

            longitudeDegree = Integer.parseInt(s_longitudeDegree);
            longitudeMinutes = Integer.parseInt(s_longitudeMinutes);
            longitudeSeconds = Integer.parseInt(s_longitudeSeconds);
        }catch(NumberFormatException nfe) {
            throw new InvalidFileFormatException(currentLine, "There was an error during the cast from String to int.");
        }

        // Creates the two coordinates, need to be declared here before to be used in the Airport's Constructor below

        Longitude longitude = null;
        Latitude latitude = null;

        try {
            latitude = new Latitude(latitudeDegree, latitudeMinutes, latitudeSeconds, s_latitudeDirection);
            longitude = new Longitude(longitudeDegree, longitudeMinutes, longitudeSeconds, s_longitudeDirection);
        }catch(InvalidCoordinateException ice) {
            throw new InvalidFileFormatException(currentLine, ice.getMessage());
        }

        // Adding the Airport to the AirportSet        
        Airport airport = new Airport(s_name, s_location, latitude, longitude);
        airportSet.add(airport);
    }

    /**
     * This procedure puts the active and inactive Airports in their right Set.
     * It must be called after the Flights' importation, because it needs the Flights to know
     * if it's used or not.
     * 
     * @param airportSet ({@link util.AirportSet}) - The AirportSet which contains all the Airports
     * @param fig ({@link graph.FlightsIntersectionGraph}) - The FIG which contains all the Airports
     * 
     * @author Luc le Manifik
     */
    public static void setActiveAirports(AirportSet airportSet, FlightsIntersectionGraph fig) {
        
        for(Airport airport : airportSet) {

            // Adding the airport in the right Set, used later to print Waypoints on the Map (the red ones and the grey ones)
            if(airport.mustBeActive(fig)) {
                airportSet.getActiveAirports().add(airport);
            }else {
                airportSet.getInactiveAirports().add(airport);
            }
        }
    }


    /*
     * ===============================================
     * Flights' Importation
     * ===============================================
     */

    /**
     * Imports and creates Flights from the source file passed in parameter. The Airports which are needed to create the Flights
     * are in the AirportSet, passed in parameter. It is made this way so that we can create multiple FIG without having to re-import the Airports each time.
     * 
     * @param airportSet ({@link util.AirportSet util.AirportSet}) - The Set that contains all the Airports.
     * @param fig ({@link graph.FlightsIntersectionGraph}) - The FIG which contains all the Flights
     * @param flightsFile ({@link java.io.File java.io.File}) - The source file where the informations  on the Flights are stored.
     * @param timeSecurity (double) - The time Gap below which the Flights are considered like in collision (in MINUTES).
     * 
     * @throws FileNotFoundException Threw if the file is not found or does not exist.
     * @throws InvalidEntryException Threw if the values passed in the Flights's constructor are not correct.
     * 
     * @author Luc le Manifik
     */
    public static void importFlightsFromFile(AirportSet airportSet, FlightsIntersectionGraph fig, File flightsFile, double timeSecurity) throws FileNotFoundException, InvalidEntryException {
        
        Scanner scanLine = null;

        try {
            scanLine = new Scanner(flightsFile);
        }catch(FileNotFoundException fnfe) {
            throw fnfe;
        }

        String line;
        int currentLine = 0;
        int nbFlight = 0;

        Flight flight = null;

        while(scanLine.hasNext()) {
            ++currentLine;
            line = scanLine.nextLine();
            if(line.charAt(0) != '\n') { // Pass if the line is just a blank line
                try {
                    flight = DataImportation.createFlightFrom(airportSet, fig, line, currentLine);
                    ++nbFlight;

                    DataImportation.createCollisions(fig, flight, timeSecurity); // Creates all the collisions from this new Flight
                }catch(InvalidFileFormatException iffe) {
                    scanLine.close();
                    throw iffe;
                }catch(InvalidEntryException iee) {
                    scanLine.close();
                    throw iee;
                }
            }
        }
        fig.setNbFlights(nbFlight); // Sets the number of Flights

        scanLine.close();
    }

    /**
     * Set the attributes of a Flight from a String, with informations separated by semi-colons ";".
     * 
     * @param airportSet ({@link util.AirportSet util.AirportSet}) - The AirportSet on which are contains all the airports.
     * @param fig ({@link graph.FlightsIntersectionGraph}) - The FIG, which contains the Flights
     * @param line (String) - The line on which the relatives informations of the Flight are registered.
     * @param currentLine (int) - The current line of the source file, used to report the errors.
     * 
     * @throws InvalidFileFormatException Threw if the line does not meet the requirement. Like missing informations, etc...
     * 
     * @author Luc le Manifik
     */
    private static Flight createFlightFrom(AirportSet airportSet, FlightsIntersectionGraph fig, String line, int currentLine) throws InvalidFileFormatException {

        String okLine = line.replaceAll(" ", "");

        Scanner scanData = new Scanner(okLine);
        scanData.useDelimiter("[;\0]");

        int currentAttribute = 0;
        String string_attribute = "";

        // Variables that will store the Flight's informations
        String s_name = "", s_departure = "", s_arrival = "";
        int departureTime_h = 0, departureTime_m = 0, duration = 0;
        FlightTime departureTime = null;

        while(scanData.hasNext()) {
            string_attribute = scanData.next(); // store the current Flight's data (name, then departureAirport, then ...)
            
            string_attribute = string_attribute.replaceAll(DataImportation.REGEX_FLIGHTS_AIRPORTS, ""); // Remove useless characters -> Avoid little errors
            
            try {
                switch(currentAttribute) {
                    case 0 :
                        s_name = string_attribute;
                        break;
                    case 1 :
                        s_departure = string_attribute;
                        break;
                    case 2 :
                        s_arrival = string_attribute;
                        break;
                    case 3 :
                        departureTime_h = Integer.parseInt(string_attribute);
                        break;
                    case 4 :
                        departureTime_m = Integer.parseInt(string_attribute);
                        break;
                    case 5 :
                        duration = Integer.parseInt(string_attribute);
                        break;
                    default : 
                        System.err.println("Error at Line " + currentLine + " : More informations than required.");
                        break;
                }
            }catch(NumberFormatException e) {
                scanData.close();
                throw new InvalidFileFormatException(currentLine, "Problem occured when cast String to int");
            }
            
            ++currentAttribute; // Increment to pass to the next attribute
        }
        scanData.close();

        // Check if the line contains all the required informations (if currentAttribute == 5)
        if(currentAttribute < 5) {
            throw new InvalidFileFormatException(currentLine, "Missing informations to correctly create the Flight");
        }

        departureTime = new FlightTime(departureTime_h, departureTime_m);

        // If everything is ok, the Flight is initialized

        // Setting up the FlightFactory to create Flight nodes
        fig.setNodeFactory(new FlightFactory()); // IDK if it's correct but Inch'Allah
        Flight flight = (Flight)fig.addNode(s_name);

        try {
            flight.setFlightAttributes(airportSet.getAirport(s_departure), airportSet.getAirport(s_arrival), departureTime, duration);
        }catch(ObjectNotFoundException onfe) {
            throw new InvalidFileFormatException(currentLine, "Airport does not exist");
        }catch(InvalidEntryException iee) {
            throw new InvalidFileFormatException(currentLine, "Invalid value entered");
        }catch(NullPointerException npe) {
            throw new InvalidFileFormatException(currentLine, "Object does not exist");
        }

        return flight;
    }

    /**
     * This function creates the edges of the FIG by adding an edge between two Flights if they are colliding.
     * The collisions are checked by the isBooming() function.
     * This function takes a Flight in parameter and tests all the collisions with the Flights that are already added to the FIG.
     * 
     * @param fig ({@link graph.FlightsIntersectionGraph}) - The FIG which contains all the Flights
     * @param flight ({@link graph.Flight graph.Flight}) - The Flight from which we are adding the collisions.
     * @param timeSecurity (double) - The value, in MINUTES, of the threshold under which two Flights are in collision.
     * @throws ObjectNotFoundException Threw if the departure Airport or the arrival Airport of one of the two Flights is not found.
     * 
     * @author Luc le Manifik
     */
    private static void createCollisions(FlightsIntersectionGraph fig, Flight flight, double timeSecurity) {
        String idFlight = flight.getId();

        fig.nodes().forEach(e -> {
            if(flight.isBooming((Flight)e, timeSecurity)) {
                fig.addEdge(idFlight + "-" + e.getId(), idFlight, e.getId());
                fig.setNbCollisions(fig.getNbCollisions() + 1); // Increment nbCollisions
            }
        });   
    }
}
