package planeair.graph.coloring;

//#region IMPORTS

import java.util.Arrays;

//#endregion

/**
 * Enum storing constants linked to algorithms
 */
public enum ColoringAlgorithm {
    //#region CONSTANTS

    INDISPONIBLE(-1, "INDISPONIBLE"),
    WELSH_POWELL(0, "Welsh & Powell"),
    DSATUR(1, "DSATUR"),
    RLF(2, "RLF") ;

    //#endregion

    //#region ATTRIBUTES

    /**
     * The id of the algorithm (used for array indexing)
     */
    private int id ;
    
    /**
     * Its name (displayed on screen)
     */
    private String name ;

    //#endregion

    //#region CONSTRUCTOR

    /**
     * Defines an algorithm constant by its id and name
     * If its id is a positive Integer, then it is an algorithm
     * else (so if it's a negative Integer), it means that it is
     * some other constant related to Algorithms
     * 
     * @param id the id of the Algorithm
     * @param name the name of the Algorithm 
     */
    private ColoringAlgorithm(int id, String name) {
        this.id = id ;
        this.name = name ;
    }

    //#endregion

    //#region GETTERS
 
    /**
     * Getter for this algorithm's id
     * @return The algorithm's id
     */   
    public int getId() {
        return this.id ;
    }

    /**
     * Getter for this algorithm's name
     * @return The algorithm's name
     */
    public String getName() {
        return this.name ;
    }

    /**
     * Returns only the algorithms
     * @return The array containing the algorithms (all constants
     * with a positive id)
     */
    public static ColoringAlgorithm[] algorithmList() {
        return Arrays.stream(values())
            .filter(algo -> algo.getId() >= 0)
            .toArray(ColoringAlgorithm[]::new) ;
    }

    //#endregion

    //#region TOSTRING

    public String toString() {
        return this.name ;
    }

    //#endregion
}
