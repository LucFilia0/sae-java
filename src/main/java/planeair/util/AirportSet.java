package planeair.util;

//-- Import Java

import java.util.HashSet;

//-- Import Exceptions

import planeair.exceptions.ObjectNotFoundException;
import planeair.graph.FlightsIntersectionGraph;

/**
 * The AirportSet class is a container for the Airport class.
 * It stores the Airports in a HashSet<Airport>.
 * It also has the importations functions, to read a file and create a Set of Airport from it.
 * 
 * @author Luc le Manifik
 */
public class AirportSet extends HashSet<Airport> {

    //-- AirportSet Attributes

    /**
     * Set which contains all the active Airports. The red ones.
     */
    private HashSet<Airport> activeAirports;

    /**
     * Set which contains all the inactiveAirports. The gray ones
     */
    private HashSet<Airport> inactiveAirports;

    //-- AirportSet Constructor

    /**
     * Creates a new AirportSet.
     * 
     * @author Luc le Manifik
     */
    public AirportSet() {
        this.activeAirports = new HashSet<Airport>();
        this.inactiveAirports = new HashSet<Airport>();
    }

    //-- AirportSet Getters

    /**
     * Returns the Set of the active Airports, which means the ones that are crossed by Flights.
     * They are in red on the Map.
     * 
     * @return ({@link java.util.HashSet HashSet<Airport>}) - The Set which contains all the active Airports
     * 
     * @author Luc le Manifik
     */
    public HashSet<Airport> getActiveAirports() {
        return this.activeAirports;
    }

    /**
     * Returns the Set of the inactive Airports, which means the ones that ARE NOT used by the Flights.
     * They are in red on the Map.
     * 
     * @return ({@link java.util.HashSet HashSet<Airport>}) - The Set which contains all the unused Airports
     * 
     * @author Luc le Manifik
     */
    public HashSet<Airport> getInactiveAirports() {
        return this.inactiveAirports;
    }

    //-- AirportSet Methods

    /**
     * Get an Aiport by his name.
     * 
     * @param name (String) - The name searched.
     * @return airport ({@link util.Airport util.Airport})
     * @throws ObjectNotFoundException Throwed if none airport in AiportSet has the searched name.
     */
    public Airport getAirport(String name) throws ObjectNotFoundException {
        for(Airport airport : this) {
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
        for(Airport airport : this) {
            System.out.println(airport);
        }
    }

    /**
     * This procedure puts the active and inactive Airports in their right Set.
     * It must be called after the Flights' importation, because it needs the Flights to know
     * if it's used or not.
     * 
     * @param fig ({@link graph.FlightsIntersectionGraph}) - The FIG which contains all the Airports
     * 
     * @author Luc le Manifik
     */
    public void setActiveAirportsFrom(FlightsIntersectionGraph fig) {
        
        for(Airport airport : this) {

            // Adding the airport in the right Set, used later to print Waypoints on the Map (the red ones and the grey ones)
            if(airport.mustBeActive(fig)) {
                this.activeAirports.add(airport);
            }else {
                this.inactiveAirports.add(airport);
            }
        }
    }
}
