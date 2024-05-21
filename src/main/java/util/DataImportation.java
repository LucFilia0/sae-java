package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import exceptions.InvalidCoordinateException;
import exceptions.InvalidEntryException;
import exceptions.InvalidFileFormatException;
import exceptions.InvalidTimeException;
import exceptions.ObjectNotFoundException;
import graph.Flight;
import graph.FlightFactory;
import graph.FlightsIntersectionGraph;

public abstract class DataImportation {

    /*
     * ================================
     * Airports' Importation
     * ================================
     */
    


    /**
     * Imports the Airports from a File passed in parameter. They are automatically added to the AirportSet.
     * 
     * @param airportsFile ({@link java.io.File java.io.File}) - The File read.
     *
     * @throws FileNotFoundException Threw if the File is not found or does not exist.
     * @throws NumberFormatException Threw if the cast from (String) to (int) is not done correctly (spaces, symbols,...)/
     * @throws InvalidCoordinateException Threw if the coordinates ({@link util.Longitude util.Longitude} and {@link util.Latitude util.Latitude}) are not correct in the source file.
     * 
     * @author Luc le Manifik
     */
    public static void importAirportsFromFile(AirportSet airportSet, FlightsIntersectionGraph fig, File airportsFile) throws FileNotFoundException, NumberFormatException, InvalidCoordinateException {
        
        Scanner scanLine = null;

        try {
            scanLine = new Scanner(airportsFile);
        }catch(FileNotFoundException fnfe) {
            throw fnfe;
        }

        String line;
        int currentLine = 0; // The line currently read in the source file. To report errors.

        while(scanLine.hasNextLine()) {
            ++currentLine;
            line = scanLine.nextLine();
            if(line.charAt(0) != '\n') { // Check if the line is not just a blank line
                try {
                    DataImportation.createAirportFrom(airportSet, fig, line, currentLine); // Creates an Airport with the informations of the line.
                }catch(InvalidFileFormatException iffe) {
                    scanLine.close();
                    throw iffe;
                }catch(NumberFormatException nfe) {
                    scanLine.close();
                    throw nfe;
                }catch(InvalidCoordinateException ice) {
                    scanLine.close();
                    throw ice;
                }
            }
        }

        scanLine.close();
    }

    /**
     * Creates and add an Airport to the AirportSet, by reading the line (String) passed in parameter. 
     * 
     * @param waypointSet ({@link java.util.Set}) - The Set which contains all the Airport's Waypoints
     * @param okLine (String) - The (String) that is read and contains the informations about the Airport.
     * @param currentLine (int) - The current line in the source file. Used to report errors.
     * 
     * @throws InvalidFileFormatException Threw if the source file does not match the required format.
     * @throws NumberFormatException Threw if the cast from (String) to (int) is not done correctly (spaces, symbols...).
     * @throws InvalidCoordinateException Threw if the values of the coordinates ({@link util.Longitude util.Longitude} and {@link util.Latitude util.Latitude}) are not correct.
     * 
     * @author Luc le Manifik
     */
    private static void createAirportFrom(AirportSet airportSet, FlightsIntersectionGraph fig, String line, int currentLine) throws InvalidFileFormatException, NumberFormatException, InvalidCoordinateException {

        String okLine = line.replaceAll(" ", "");

        Scanner scanAirport = new Scanner(okLine);
        scanAirport.useDelimiter("[;\0]");

        String string_attribute; // Store the different attributes of the Airport
        int currentAttribute = 0; // Counter, incremented to pass the attributes in the while loop (with the switch).

        // All the Strings that will contain the informations of the Airport, by reading the source file.
        String s_name = "", s_location = "", s_latitudeDegree = "", s_latitudeMinutes = "", s_latitudeSeconds = "", s_latitudeDirection = "", s_longitudeDegree = "", s_longitudeMinutes = "", s_longitudeSeconds = "", s_longitudeDirection = "";
        int latitudeDegree = 0, latitudeMinutes = 0, latitudeSeconds = 0;
        int longitudeDegree = 0, longitudeMinutes = 0, longitudeSeconds = 0;
        
        while(scanAirport.hasNext()) {
            string_attribute = scanAirport.next();
            switch(currentAttribute) {
                case 0 :
                    s_name = string_attribute;
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
                    s_latitudeDirection = string_attribute;
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
                    s_longitudeDirection = string_attribute;
                    break;
                default :
                    System.err.println("Error at Line " + currentLine + " : More informations than required.");
                    break;
            }
            ++currentAttribute;
        }
        scanAirport.close();

        if(currentAttribute < 9) {
            throw new InvalidFileFormatException("Missing informations to correctly create the Airport at line " + currentLine);
        }

        // Cast the coordinates (String) informations to (int)
        try {
            latitudeDegree = Integer.parseInt(s_latitudeDegree);
            latitudeMinutes = Integer.parseInt(s_latitudeMinutes);
            latitudeSeconds = Integer.parseInt(s_latitudeSeconds);

            longitudeDegree = Integer.parseInt(s_longitudeDegree);
            longitudeMinutes = Integer.parseInt(s_longitudeMinutes);
            longitudeSeconds = Integer.parseInt(s_longitudeSeconds);
        }catch(NumberFormatException nfe) {
            throw nfe;
        }

        // Creates the two coordinates, need to be declared here before to be used in the Airport's Constructor

        Longitude longitude = new Longitude(0, 0, 0, 'E');
        Latitude latitude = new Latitude(0, 0, 0, 'N');

        try {
            latitude = new Latitude(latitudeDegree, latitudeMinutes, latitudeSeconds, s_latitudeDirection.charAt(0));
            longitude = new Longitude(longitudeDegree, longitudeMinutes, longitudeSeconds, s_longitudeDirection.charAt(0));
        }catch(InvalidCoordinateException ice) {
            throw ice;
        }
        
        Airport airport = new Airport(s_name, s_location, latitude, longitude);

        // Adding the airport to the AirportSet
        airportSet.add(airport);
    }

    /**
     * This procedure puts the active and inactive Airports in their right Set.
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
     * ================================
     * Flights' Importation
     * ================================
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
     * @throws NumberFormatException Threw if the cast from (String) to (int) is not done correctly.
     * @throws InvalidTimeException Threw if the departureTime values are not correct.
     * @throws ObjectNotFoundException Threw if the searched Airport is not found.
     * @throws InvalidEntryException Threw if the values passed in the Flights's constructor are not correct.
     * 
     * @author Luc le Manifik
     */
    public static void importFlightsFromFile(AirportSet airportSet, FlightsIntersectionGraph fig, File flightsFile, double timeSecurity) throws FileNotFoundException, NumberFormatException, InvalidTimeException, ObjectNotFoundException, InvalidEntryException {
        
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
            if(line.charAt(0) != '\n') { // Pass if the line is just a jump
                try {
                    flight = DataImportation.createFlightFrom(airportSet, fig, line, currentLine);
                    fig.setNbFlights(fig.getNbFlights() + 1); // Increments the number of Flights

                    DataImportation.createCollisions(fig, flight, timeSecurity); // Creates all the collisions from this new Flight
                }catch(InvalidFileFormatException iffe) {
                    scanLine.close();
                    throw iffe;
                }catch(NumberFormatException nfe) {
                    scanLine.close();
                    throw nfe;
                }catch(InvalidTimeException ite) {
                    scanLine.close();
                    throw ite;
                }catch(ObjectNotFoundException onfe) {
                    scanLine.close();
                    throw onfe;
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
     * @throws NumberFormatException Threw if the cast from (String) to (int) goes wrong.
     * @throws InvalidTimeException Threw if the data on the departure time are incorrect.
     * @throws ObjectNotFoundException Threw if the research by the name of the Airport have found nothing (The key is incorrect or the Airport does not exist). 
     * @throws InvalidEntryException Threw if the arguments passed by the Flight's constructor are not correct.
     * 
     * @author Luc le Manifik
     */
    private static Flight createFlightFrom(AirportSet airportSet, FlightsIntersectionGraph fig, String line, int currentLine) throws InvalidFileFormatException, NumberFormatException, InvalidTimeException, ObjectNotFoundException , InvalidEntryException {

        String okLine = line.replaceAll(" ", "");

        Scanner scanData = new Scanner(okLine);
        scanData.useDelimiter(";");

        int currentAttribute = 0;
        String string_attribute = "";

        String s_name = "", s_departure = "", s_arrival = "";
        int departureTime_h = 0, departureTime_m = 0, duration = 0;
        FlightTime departureTime = null;

        while(scanData.hasNext()) {
            string_attribute = scanData.next(); // store the current Flight's data (name, then departureAirport, then ...)
            string_attribute = string_attribute.replaceAll(" ", ""); // Remove useless spaces -> Avoid little errors
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
                    try {
                        departureTime_h = Integer.parseInt(string_attribute);
                    }catch(NumberFormatException nfe) {
                        throw nfe;
                    }
                    break;
                case 4 :
                    try {
                        departureTime_m = Integer.parseInt(string_attribute);
                    }catch(NumberFormatException nfe) {
                        throw nfe;
                    }
                    break;
                case 5 :
                    try {
                        duration = Integer.parseInt(string_attribute);
                    }catch(NumberFormatException nfe) {
                        throw nfe;
                    }
                    break;
                default : 
                    System.err.println("Error at Line " + currentLine + " : More informations than required.");
                    break;
            }
            ++currentAttribute; // Increment to pass to the next attribute
        }
        scanData.close();

        // Check if the line contains all the required informations (if currentAttribute == 5)
        if(currentAttribute < 5) {
            throw new InvalidFileFormatException("Missing informations to correctly create the Flight at line " + currentLine);
        }

        departureTime = new FlightTime(departureTime_h, departureTime_m);

        // If everything is ok, the Flight is initialized

        // Setting up the FlightFactory to create Flight nodes
        fig.setNodeFactory(new FlightFactory()); // IDK if it's correct but Inch'Allah
        Flight flight = (Flight)fig.addNode(s_name);

        try {
            flight.setFlightAttributes(airportSet.getAirport(s_departure), airportSet.getAirport(s_arrival), departureTime, duration);
        }catch(ObjectNotFoundException onfe) {
            throw onfe;
        }catch(InvalidEntryException iee) {
            throw iee;
        }catch(NullPointerException npe) {
            throw npe;
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
