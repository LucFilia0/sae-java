package planeair.util;

//#region IMPORTS
import java.util.HashSet;

import planeair.exceptions.ObjectNotFoundException;
import planeair.graph.graphtype.FlightsIntersectionGraph;
//#endregion

/**
 * The AirportSet class is a container for the Airport class.
 * It stores the Airports in a HashSet<Airport>.
 * It also contains two other Sets : the "activeAirports" Set and the "inactiveAirports" Set,
 * which each contains the ActiveAirports (in red) and the InactiveAirports (in gray) 
 * 
 * @author Luc le Manifik
 */
public class AirportSet extends HashSet<Airport> {

    //#region ATTRIBUTES

    /**
     * Set which contains all the active Airports. The red ones
     */
    private HashSet<Airport> activeAirports;

    /**
     * Set which contains all the inactiveAirports. The gray ones
     */
    private HashSet<Airport> inactiveAirports;

    //#endregion

    //#region CONSTRUCTORS

    /**
     * Creates a new AirportSet.
     * AirportSets Extends {@link java.util.HashSet HashSets} and contains two more Sets : "activeAirports" and "inactiveAirports".
     * These two Sets need to be filled whith the "setActiveAirportsFrom(FlightsIntersectionGraph)" 
     * 
     * @author Luc le Manifik
     */
    public AirportSet() {
        this.activeAirports = new HashSet<Airport>();
        this.inactiveAirports = new HashSet<Airport>();
    }

    //#endregion

    //#region GETTERS

    /**
     * Returns the Set of the active Airports, which means the ones that are crossed by Flights.
     * They are in red on the Map.
     * 
     * @return The {@link java.util.HashSet HashSet} which contains all the active Airports
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
     * @return The {@link java.util.HashSet HashSet} which contains all the unused Airports
     * 
     * @author Luc le Manifik
     */
    public HashSet<Airport> getInactiveAirports() {
        return this.inactiveAirports;
    }

    /**
     * Searches and returns an {@link planeair.util.Aiport Airport}, by giving his name.
     * If the Airport does not exist, then an exception is raised.
     * 
     * @param name The name of the searched Airport
     * 
     * @return The found {@link util.Airport util.Airport}
     * 
     * @throws ObjectNotFoundException Threw if none airport in the AiportSet has the searched name
     * 
     * @author Luc le Manifik
     */
    public Airport getAirport(String name) throws ObjectNotFoundException {

        for(Airport airport : this) {
            if(airport.getName().equals(name)) {
                return (Airport)airport;
            }
        }
        throw new ObjectNotFoundException();
    }

    //#endregion

    //#region PUBLIC METHODS

    /**
     * Prompts all the informations of all the Airports in the console
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
     * @param fig The {@link graph.FlightsIntersectionGraph FIG} which contains all the Airports
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

    //#endregion
}
