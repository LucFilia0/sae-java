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

import exceptions.InvalidFileFormatException;
import exceptions.InvalidTimeException;
import exceptions.ObjectNotFoundException;


/**
 * The FlightIntersectionGraph represents the collisions between Flights.
 * The nodes of the Graph are the Flights.
 * 
 * @author Luc le Manifik
 */
public class FlightsIntersectionGraph extends SingleGraph {

    //-- FIG Constructor

    public FlightsIntersectionGraph(String id) {
        super(id); // -> The identifier of the FIG, in the parent class (SingleGraph)
        this.setAttribute("nbFlights", 0); // -> The number of flights in the FIG
        this.setAttribute("nbCollisions", 0); // -> The number of collisions in the FIG
    }

    //-- FIG toString()

    public String toString() {
        return "-- Flights Intersection Graph\nIdentifier : " + super.id;
    }

    //-- FIG Importations

    /**
     * Imports and creates Flights from the source file passed in parameter. The Airports which are needed to create the Flights
     * are in the AirportSet, passed in parameter. It is made this way so that we can create multiple FIG without having to re-import the Airports each time.
     * 
     * @param flightsFile ({@link java.io.File java.io.File}) - The source file where the informations  on the Flights are stored.
     * @param airportSet ({@link util.AirportSet util.AirportSet}) - The Set that contains all the Airports.
     * @param timeSecurity (double) - The time Gap below which the Flights are considered like in collision (in MINUTES).
     * @throws FileNotFoundException Throwed if the file is not found or does not exist.
     * @throws NumberFormatException Throwed if the cast from (String) to (int) is not done correctly.
     * @throws InvalidTimeException Throwed if the departureTime values are not correct.
     * 
     * @author Luc le Manifik
     */
    public void importFlightsFromFile(File flightsFile, AirportSet airportSet, double timeSecurity) throws FileNotFoundException, NumberFormatException, InvalidTimeException, ObjectNotFoundException {
        
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
                    this.createCollisions(flight, timeSecurity);
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
     * 
     * @author Luc le Manifik
     */
    private Flight createFlightFrom(String line, AirportSet airportSet, int currentLine) throws InvalidFileFormatException, NumberFormatException, InvalidTimeException, ObjectNotFoundException {

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
        }

        return flight;
    }

    private void createCollisions(Flight flight, double timeSecurity) throws ObjectNotFoundException {
        String idFlight = flight.getId();

        this.nodes().forEach(e -> {
            /* try {
                if(flight.isBooming((Flight)e, timeSecurity)) {
                    this.addEdge(idFlight + "-" + e.getId(), idFlight, e.getId());
                    System.out.println("BOOM");
                }
            }catch(ObjectNotFoundException onfe) {
                onfe.printStackTrace();
            } */
            try {
                if(flight.isBooming((Flight)e, timeSecurity)) {
                    this.addEdge(idFlight + "-" + e.getId(), idFlight, e.getId());
                }
                
            }catch(ObjectNotFoundException onfe) {
                try {
                    throw onfe;
                } catch (ObjectNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });
        
    }

    //-- FIG Coloration

}
