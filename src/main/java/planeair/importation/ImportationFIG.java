package planeair.importation;

// Import Java
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Import PlaneAIR
import org.graphstream.graph.IdAlreadyInUseException;

import planeair.exceptions.InvalidCoordinateException;
import planeair.exceptions.InvalidEntryException;
import planeair.exceptions.InvalidFileFormatException;
import planeair.exceptions.ObjectNotFoundException;

import planeair.graph.graphutil.Flight;
import planeair.graph.graphutil.FlightFactory;
import planeair.graph.graphtype.FlightsIntersectionGraph;

import planeair.util.Airport;
import planeair.util.AirportSet;
import planeair.util.Coordinate;
import planeair.util.NTime;

/**
 * <html>
 * This class contains all the functions/procedures linked to FIG importation.
 * <hr>
 * In this project, two types of files are mainly used : 
 *  <ol>
 *      <li>
 *          "aeroports.csv" contains all data of the Airports, which will be stored in an AirportSet.
 *      </li>
 *      <li>                 
 *          "vols-testX.csv" contains all data of the Flights we wish to simulate.
 *          <strong>The Flights NEEDS the Airports</strong>, and need to be imported after them.
 *      </li>
 *  </ol>
 * The files are formated due to certain rules, in order to find the right informations at the right place.
 * So, if a file can't be read because of some errors (mistakes or not), it may endanger the entire application.
 * <hr>
 * In order to prevent such a mess to happen, data will be checked before to be fully imported in the application's objects.
 * Messages will be prompt for the user, even if these errors are not blocking the programm to execute.
 * With that, the user will be informed of all the errors that are present in the files.
 * </html>
 * 
 * @author Luc le grand, que dis-je, le <strong>Manifik</strong>
 */
public abstract class ImportationFIG {

    /**
     * The RegEx which is used to clean the lines of the files. It only accepts ";", spaces, numbers and letters.
     * All the other caracters are ignored.
     * 
     * @author Luc le Manifik
     */
    public static final String REGEX_FIG = "[^; 0-9A-Za-z]";

    /**
     * Only accept numbers
     */
    public static final String REGEX_NUMBERS = "[^0-9]";

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
    public static void importAirportsFromFile(AirportSet airportSet, File airportsFile) throws FileNotFoundException, InvalidFileFormatException {
        
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
                    ImportationFIG.createAirportFrom(airportSet, line, currentLine); // Creates an Airport with the informations of the line.
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
    private static void createAirportFrom(AirportSet airportSet, String line, int currentLine) throws InvalidFileFormatException {

        String okLine = line.replaceAll(ImportationFIG.REGEX_FIG, ""); // Suppress all the useless spaces

        Scanner scanAirport = new Scanner(okLine);
        scanAirport.useDelimiter("[;\0]");

        String string_attribute; // Store the different attributes of the Airport
        int currentAttribute = 0; // Counter, incremented to pass the attributes in the while loop (with the switch).

        // All the Strings that will contain the informations of the Airport, by reading the source file.
        String s_name = "", s_location = "", s_latitudeDegree = "", s_latitudeMinutes = "", s_latitudeSeconds = "", s_longitudeDegree = "", s_longitudeMinutes = "", s_longitudeSeconds = "";
        char latitudeDirection = ' ', longitudeDirection = ' ';

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
                    s_latitudeDegree = string_attribute.replaceAll(ImportationFIG.REGEX_NUMBERS, "");
                    break;
                case 3 :
                    s_latitudeMinutes = string_attribute.replaceAll(ImportationFIG.REGEX_NUMBERS, "");
                    break;
                case 4 :
                    s_latitudeSeconds = string_attribute.replaceAll(ImportationFIG.REGEX_NUMBERS, "");
                    break;
                case 5 :
                    latitudeDirection = string_attribute.toUpperCase().charAt(0);
                    break;
                case 6 :
                    s_longitudeDegree = string_attribute.replaceAll(ImportationFIG.REGEX_NUMBERS, "");
                    break;
                case 7 :
                    s_longitudeMinutes = string_attribute.replaceAll(ImportationFIG.REGEX_NUMBERS, "");
                    break;
                case 8 :
                    s_longitudeSeconds = string_attribute.replaceAll(ImportationFIG.REGEX_NUMBERS, "");
                    break;
                case 9 :
                    longitudeDirection = string_attribute.toUpperCase().charAt(0);
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

        Airport airport = null;
        Coordinate airportCoordinate = null;

        try {
            airportCoordinate = new Coordinate(latitudeDegree, latitudeMinutes, latitudeSeconds, latitudeDirection, longitudeDegree, longitudeMinutes, longitudeSeconds, longitudeDirection);
        }catch(InvalidCoordinateException e) {
            throw new InvalidFileFormatException(currentLine, e.getMessage());
        }
        
        try {
            airport = new Airport(s_name, s_location, airportCoordinate);
        }catch(InvalidFileFormatException iffe) {
            throw iffe;
        }

        // Adding the Airport to the AirportSet        
        airportSet.add(airport);
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

        Flight flight = null;

        while(scanLine.hasNext()) {
            ++currentLine;
            line = scanLine.nextLine();
            if(line.charAt(0) != '\n') { // Pass if the line is just a blank line
                try {
                    flight = ImportationFIG.createFlightFrom(airportSet, fig, line, currentLine);

                    ImportationFIG.createCollisions(fig, flight, timeSecurity); // Creates all the collisions from this new Flight
                }catch(InvalidFileFormatException iffe) {
                    scanLine.close();
                    throw iffe;
                }catch(InvalidEntryException iee) {
                    scanLine.close();
                    throw iee;
                }
            }
        }

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

        String okLine = line.replaceAll(ImportationFIG.REGEX_FIG, "");

        Scanner scanData = new Scanner(okLine);
        scanData.useDelimiter("[;\0]");

        int currentAttribute = 0;
        String string_attribute = "";

        // Variables that will store the Flight's informations
        String s_name = "", s_departure = "", s_arrival = "";
        int departureTime_h = 0, departureTime_m = 0, duration = 0;
        NTime departureTime = null;

        while(scanData.hasNext()) {
            
            string_attribute = scanData.next(); // store the current Flight's data (name, then departureAirport, then ...)
                        
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
                        departureTime_h = Integer.parseInt(string_attribute.replaceAll(ImportationFIG.REGEX_NUMBERS, ""));
                        break;
                    case 4 :
                        departureTime_m = Integer.parseInt(string_attribute.replaceAll(ImportationFIG.REGEX_NUMBERS, ""));
                        break;
                    case 5 :
                        duration = Integer.parseInt(string_attribute.replaceAll(ImportationFIG.REGEX_NUMBERS, ""));
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

        departureTime = new NTime(departureTime_h, departureTime_m);

        // If everything is ok, the Flight is initialized

        // Setting up the FlightFactory to create Flight nodes
        fig.setNodeFactory(new FlightFactory()); // IDK if it's correct but Inch'Allah
        
        Flight flight = null;
        try {
            flight = (Flight)fig.addNode(s_name);
        }catch(IdAlreadyInUseException e) {
            throw new InvalidFileFormatException(currentLine, "Id deja utilise plus haut.");
        }

        try {
            Airport start = airportSet.getAirport(s_departure) ;
            Airport end = airportSet.getAirport(s_arrival) ;
            start.getFlightList().add(flight) ;
            end.getFlightList().add(flight) ;
            
            flight.setFlightAttributes(start, end, departureTime, duration);
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
            }
        });   
    }
}
