package util;

//-- Import Java

import java.util.HashSet;
import java.io.File;
import java.util.Scanner;

//-- Import Exceptions

import exceptions.InvalidFileFormatException;
import exceptions.ObjectNotFoundException;

import java.io.FileNotFoundException;
import exceptions.InvalidCoordinateException;

/**
 * The AirportSet class is a container for the Airport class.
 * It stores the Airports in a HashSet<Airport>.
 * It also has the importations functions, to read a file and create a Set of Airport from it.
 * 
 * @author Luc le Manifik
 */
public class AirportSet {
    
    //-- AirportSet Attributes

    private HashSet<Airport> airportSet; // -> The HashSet that contains all the Airports

    //-- AirportSet Constructor

    /**
     * Creates a new AirportSet.
     * 
     * @author Luc le Manifik
     */
    public AirportSet() {
        this.airportSet = new HashSet<Airport>();
    }

    //-- AirportSet Methods

    /**
     * Get the size of the AirportSet / the number of Aiports in the AiportSet.
     * @return size (int)
     */
    public int getSize() {
        return this.airportSet.size();
    }

    /**
     * Add an Airport to the AirportSet.
     * 
     * @param airport ({@link util.Airport util.Airport}) - The new Airport to add.
     * @return (boolean) - True if the Airport was correctly added, else false.
     * 
     * @author Luc le Manifik
     */
    public boolean addAirport(Airport airport) {
        return this.airportSet.add(airport);
    }

    /**
     * Remove an Airport from the AirportSet.
     * 
     * @param airport ({@link util.Airport util.Airport}) - The Airport to remove.
     * @return (boolean) - True if the Airport was correctly removed, else false.
     * 
     * @author Luc le Manifik
     */
    public boolean removeAirport(Airport airport) {
        return this.airportSet.remove(airport);
    }


    /**
     * Get an Aiport by his name.
     * 
     * @param name (String) - The name searched.
     * @return airport ({@link util.Airport util.Airport})
     * @throws ObjectNotFoundException Throwed if none airport in AiportSet has the searched name.
     */
    public Airport getAirport(String name) throws ObjectNotFoundException {
        for(Airport airport : this.airportSet) {
            if(airport.getName().equals(name)) {
                return (Airport)airport;
            }
        }
        throw new ObjectNotFoundException();
    } 

    /**
     * Prompt all the informations of all the Flights in the console
     * 
     * @author Luc le Manifik
     */
    public void showAllAirports() {
        for(Airport airport : this.airportSet) {
            System.out.println(airport);
        }
    }

    //-- AirportSet Data Importation

    /**
     * Imports the Airports from a File passed in parameter. They are automatically added to the AirportSet.
     * 
     * @param airportsFile ({@link java.io.File java.io.File}) - The File read.
     * @throws FileNotFoundException Throwed if the File is not found or does not exist.
     * @throws NumberFormatException Throwed if the cast from (String) to (int) is not done correctly (spaces, symbols,...)/
     * @throws InvalidCoordinateException Throwed if the coordinates ({@link util.Longitude util.Longitude} and {@link util.Latitude util.Latitude}) are not correct in the source file.
     * 
     * @author Luc le Manifik
     */
    public void importAirportsFromFile(File airportsFile) throws FileNotFoundException, NumberFormatException, InvalidCoordinateException {
        
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
                    this.createAirportFrom(line, currentLine); // Creates an Airport with the informations of the line.
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
     * @param okLine (String) - The (String) that is read and contains the informations about the Airport.
     * @param currentLine (int) - The current line in the source file. Used to report errors.
     * @throws InvalidFileFormatException Throwed if the source file does not match the required format.
     * @throws NumberFormatException Throwed if the cast from (String) to (int) is not done correctly (spaces, symbols...).
     * @throws InvalidCoordinateException Throwed if the values of the coordinates ({@link util.Longitude util.Longitude} and {@link util.Latitude util.Latitude}) are not correct.
     * 
     * @author Luc le Manifik
     */
    private void createAirportFrom(String line, int currentLine) throws InvalidFileFormatException, NumberFormatException, InvalidCoordinateException {

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

        Longitude longitude = null;
        Latitude latitude = null;

        try {
            latitude = new Latitude(latitudeDegree, latitudeMinutes, latitudeSeconds, s_latitudeDirection.charAt(0));
            longitude = new Longitude(longitudeDegree, longitudeMinutes, longitudeSeconds, s_longitudeDirection.charAt(0));
        }catch(InvalidCoordinateException ice) {
            throw ice;
        }
        

        this.addAirport(new Airport(s_name, s_location, latitude, longitude));
    }
}