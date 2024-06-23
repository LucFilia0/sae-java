package planeair.graph.coloring;

import java.lang.reflect.Method;

//#region IMPORTS

import java.util.Arrays;

import planeair.graph.graphtype.GraphSAE;

//#endregion

/**
 * <html>
 * Enum storing constants linked to algorithms, 
 * it defines for each algorithm
 * <dl>
 * <dt>an <strong>ID</strong> : </dt>
 *      <dd> - to identify it or index it </dd>
 * </dt>
 * <dt>a <strong>String</strong> : </dt> 
 *      <dd> - for its toString method </dd>
 * </dt>
 * <dt>a <strong>Method</strong> (can be null) : </dt> 
 *      <dd> - the method that implements the algorithm</dd>
 * </dt>
 * </dl>
 * 
 * </html>
 * @author Nathan LIEGEON
 */
public enum ColoringAlgorithms {
    //#region CONSTANTS
    INDISPONIBLE(-1, "INDISPONIBLE", null),
    WELSH_POWELL(0, "Welsh & Powell", setMethod(
        ColoringWelshPowell.class, "coloringWelshPowell", 
        new Class<?>[]{GraphSAE.class})),

    DSATUR(1, "DSATUR",setMethod(ColoringDSATUR.class, 
    "coloringDsatur", new Class<?>[]{GraphSAE.class})),

    RLF(2, "RLF", setMethod(ColoringRLF.class, 
        "coloringRLF", new Class<?>[]{GraphSAE.class})) ;

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

    /**
     * The method of the algorithm
     */
    private Method method ;

    //#endregion

    //#region CONSTRUCTOR

    /**
     * Defines an algorithm constant by its {@code id} and {@code name}
     * If its id is a {@code positive Integer}, then it is an Algorithm
     * else (so if it's a {@code negative Integer}), it means that it is
     * some other constant related to Algorithms
     * 
     * @param id the id of the constant
     * @param name the name of the constant
     * @param method The method linked to the constant (the coloring algorithm
     * or something else if this is not an algorithm)
     */
    private ColoringAlgorithms(int id, String name, Method method) {
        this.id = id ;
        this.name = name ;
        this.method = method ;
    }

    //#endregion

    //#region GETTERS
 
    /**
     * Getter for this constants's id
     * @return The constants's id
     */   
    public int getId() {
        return this.id ;
    }

    /**
     * Getter for this constant's name
     * @return The constants's name
     */
    public String getName() {
        return this.name ;
    }

    /**
     * Getter for this constant's method
     * @return The method (can be null)
     */
    public Method getMethod() {
        return this.method ;
    }

    /**
     * Returns only the algorithms
     * 
     * @return The array containing the algorithms (all constants
     * with a {@code positive id} and a {@code non-null method})
     */
    public static ColoringAlgorithms[] algorithmList() {
        return Arrays.stream(values())
            .filter(algo -> (algo.getId() >= 0 && algo.getMethod() != null))
            .toArray(ColoringAlgorithms[]::new) ;
    } 
    
    /**
     * Returns the algorithm with this {@code id}, 
     * if the id is invalid, {@code returns null}
     * 
     * @param id The id of the algorithm
     * @return The algorithm with that id or null
     */
    public static ColoringAlgorithms get(int id) {
        return Arrays.stream(values())
            .filter(algo -> algo.getId() == id)
            .findFirst().orElse(null) ;
    }

    //#endregion

    //#region SETTERS

    /**
     * Returns the method if it exists
     * 
     * @param methodClass The Class of the method
     * @param methodName The method's name
     * @param parameterTypes Array containing all parameter types in order
     * @return The method or null if a {@link NoSuchMethodException} is
     * thrown
     */
    private static Method setMethod(Class<? extends Object> methodClass, 
        String methodName, Class<? extends Object>[] parameterTypes) {
       
        Method method ;
        try {
            method = methodClass.getMethod(methodName, parameterTypes) ;
        } catch (NoSuchMethodException e) {
            method = null ;
        }

        return method ;
    }

    //#endregion

    //#region TOSTRING

    public String toString() {
        return this.name ;
    } 

    //#endregion
}
