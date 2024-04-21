package graph;

//-- Import Java

import java.io.File;
import java.util.Scanner;
import util.Time;
import util.AirportSet;

//-- Import GraphStream

import org.graphstream.graph.implementations.SingleGraph;

//-- Import Exceptions

import java.io.FileNotFoundException;

import exceptions.InvalidEntryException;
import exceptions.InvalidFileFormatException;
import exceptions.InvalidTimeException;
import exceptions.ObjectNotFoundException;


/**
 * The FlightIntersectionGraph represents the collisions between Flights.
 * The nodes of the Graph are the Flights.
 * 
 * @implNote Uses the GraphStream attributes.
 * 
 * @author Luc le Manifik
 */
public class FlightsIntersectionGraph extends SingleGraph {

    //-- FIG Attributes

    /**
     * The String identifier that represents the number of Flights presents in the FIG (int)
     */
    private final String NB_FLIGHTS = "nbFlights";

    /**
     * The String identifier that represents the number of collisions in the FIG (int)
     */
    private final String NB_COLLISIONS = "nbCollisions";

    //-- FIG Constructor

    /**
     * Constructor of the FlightsIntersectionGraph (FIG) class.
     * Creates a new FIG.
     * 
     * @param id (String) - The identifier of the FIG.
     * 
     * @author Luc le Manifik
     */
    public FlightsIntersectionGraph(String id) {
        super(id); // -> The identifier of the FIG, in the parent class (SingleGraph)
        this.setAttribute(this.NB_FLIGHTS, 0); // -> The number of flights in the FIG
        this.setAttribute(this.NB_COLLISIONS, 0); // -> The number of collisions in the FIG
    }

    //-- FIG toString()

    /**
     * toString() FIG's method.
     */
    public String toString() {
        return "-- Flights Intersection Graph\nIdentifier : " + super.id + "\nNumber of Flights : " + this.getNbFlights() + "\nNumber of collisions : " + this.getNbCollisions();
    }

    //-- FIG Getters

    /**
     * Get the number of Flights in the FIG.
     * 
     * @return (int) - The number of Flights in the FIG.
     * 
     * @author Luc le Manifik
     */
    public int getNbFlights() {
        return (int)this.getAttribute(this.NB_FLIGHTS);
    }

    /**
     * Get the number of collisions in the FIG.
     * 
     * @return (int) - The number of collisions in the FIG.
     * 
     * @author Luc le Manifik
     */
    public int getNbCollisions()  {
        return (int)this.getAttribute(this.NB_COLLISIONS);
    }

    //-- FIG Setters

    /**
     * Set the number of Flights in the FIG.
     * 
     * @param nbFlights (int) - The new number of Flights in the FIG.
     * 
     * @author Luc le Manifik
     */
    private void setNbFlights(int nbFlights) {
        this.setAttribute(this.NB_FLIGHTS, nbFlights);
    }

    /**
     * Set the number of collisions in the FIG.
     * 
     * @param nbCollisions (int) - The new number of collisions in the FIG.
     * 
     * @author Luc le Manifik
     */
    private void setNbCollisions(int nbCollisions) {
        this.setAttribute(this.NB_COLLISIONS, nbCollisions);
    }

    //-- FIG Importations

    /**
     * Imports and creates Flights from the source file passed in parameter. The Airports which are needed to create the Flights
     * are in the AirportSet, passed in parameter. It is made this way so that we can create multiple FIG without having to re-import the Airports each time.
     * 
     * @param flightsFile ({@link java.io.File java.io.File}) - The source file where the informations  on the Flights are stored.
     * @param airportSet ({@link util.AirportSet util.AirportSet}) - The Set that contains all the Airports.
     * @param timeSecurity (double) - The time Gap below which the Flights are considered like in collision (in MINUTES).
     * 
     * @throws FileNotFoundException Throwed if the file is not found or does not exist.
     * @throws NumberFormatException Throwed if the cast from (String) to (int) is not done correctly.
     * @throws InvalidTimeException Throwed if the departureTime values are not correct.
     * @throws ObjectNotFoundException Throwed if the searched Airport is not found.
     * @throws InvalidEntryException Throwed if the values passed in the Flights's constructor are not correct.
     * 
     * @author Luc le Manifik
     */
    public void importFlightsFromFile(File flightsFile, AirportSet airportSet, double timeSecurity) throws FileNotFoundException, NumberFormatException, InvalidTimeException, ObjectNotFoundException, InvalidEntryException {
        
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
                    flight = this.createFlightFrom(line, airportSet, currentLine);
                    this.setNbFlights(this.getNbFlights() + 1); // Increments the number of Flights

                    this.createCollisions(flight, timeSecurity); // Creates all the collisions from this new Flight
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
     * @param okLine (String) - The line on which the relatives informations of the Flight are registered.
     * @param airportSet ({@link util.AirportSet util.AirportSet}) - The AirportSet on which are contains all the airports.
     * @param currentLine (int) - The current line of the source file, used to report the errors.
     * @throws InvalidFileFormatException Throwed if the line does not meet the requirement. Like missing informations, etc...
     * @throws NumberFormatException Throwed if the cast from (String) to (int) goes wrong.
     * @throws InvalidTimeException Throwed if the data on the departure time are incorrect.
     * @throws ObjectNotFoundException Throwed if the research by the name of the Airport have found nothing (The key is incorrect or the Airport does not exist). 
     * @throws InvalidEntryException Throwed if the arguments passed by the Flight's constructor are not correct.
     * 
     * @author Luc le Manifik
     */
    private Flight createFlightFrom(String line, AirportSet airportSet, int currentLine) throws InvalidFileFormatException, NumberFormatException, InvalidTimeException, ObjectNotFoundException , InvalidEntryException {

        String okLine = line.replaceAll(" ", "");

        Scanner scanData = new Scanner(okLine);
        scanData.useDelimiter(";");

        int currentAttribute = 0;
        String string_attribute = "";

        String s_name = "", s_departure = "", s_arrival = "";
        int departureTime_h = 0, departureTime_m = 0, duration = 0;
        Time departureTime = null;

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

        try {
            departureTime = new Time(departureTime_h, departureTime_m);
        }catch(InvalidTimeException ite) {
            throw ite;
        }

        // If everything is ok, the Flight is initialized

        // Setting up the FlightFactory to create Flight nodes
        this.setNodeFactory(new FlightFactory()); // IDK if it's correct but Inch'Allah
        Flight flight = (Flight)this.addNode(s_name);

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
     * @param flight ({@link graph.Flight graph.Flight}) - The Flight from which we are adding the collisions.
     * @param timeSecurity (double) - The value, in MINUTES, of the threshold under which two Flights are in collision.
     * @throws ObjectNotFoundException Throwed if the departure Airport or the arrival Airport of one of the two Flights is not found.
     * 
     * @author Luc le Manifik
     */
    private void createCollisions(Flight flight, double timeSecurity) {
        String idFlight = flight.getId();

        this.nodes().forEach(e -> {
            if(flight.isBooming((Flight)e, timeSecurity)) {
                this.addEdge(idFlight + "-" + e.getId(), idFlight, e.getId());
                this.setNbCollisions(this.getNbCollisions() + 1); // Increment nbCollisions
            }
        });
        
    }

    //-- FIG Coloration

}
