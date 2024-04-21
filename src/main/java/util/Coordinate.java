package util;

import exceptions.InvalidCoordinateException ;

/**
 * The Coordinate class stores the different informations about the geographical localisation of the different Airports.
 * This class is defined as abstract, because the verification of the direction is made by the inherited classes (Longitude and Latitude).
 * 
 * @see {@link util.Latitude}
 * @see {@link util.Longitude}
 * 
 * @author Nathan LIEGEON - Modified : Luc le Manifik
 */
public abstract class Coordinate {

    //-- Coordinate parameters

    /**
     * The degree of the Coordinate (int) | [-180; 180] for Longitude or [-90; 90] for Latitude
     */
    protected int degree ;

    /**
     * The minutes of the Coordinate (int) | [0; 59]
     */
    protected int minutes ;

    /**
     * The seconds of the Coordinate (int) | [0; 59]
     */
    protected int seconds ;

    /**
     * The direction of the Coordinate (char) | [EO] for Longitude or [NS] for Latitude (Regex)
     */
    protected char direction; // -> Depends if the Coordinate is a Longitude ('N' || 'S') or a Latitude ('E' || 'O')

    //-- Coordinate Constructor 

    /**
     * Constructor of the Coordinate class. Creates a new Coordinate, with it's direction initialized to 'X', juste in case.
     * The verification of the parameters is made by the inherited classes (Longitude and Latitude), and the exception is also throwed by its children.
     * 
     * @param degree (int)
     * @param minutes (int)
     * @param seconds (int)
     */
    protected Coordinate(int degree, int minutes, int seconds) {
        this.degree = degree ;
        this.minutes = minutes ;
        this.seconds = seconds ;
        this.direction = 'X'; // Declared just in case
    }

    //-- Coordinate toString()

    /**
     * toString() Coordinate's method.
     */
    public String toString() {
        return this.degree + "° " + this.minutes + "' " + this.seconds + "'' " + this.direction;
    }

    //-- Coordinate Getters

    /**
     * Get the degree of the Coordinate.
     * 
     * @return degree (int)
     */
    public int getDegree() {
        return this.degree;
    }

    /**
     * Get the minutes of the Coordinate.
     * 
     * @return minutes (int)
     */
    public int getMinutes() {
        return this.minutes;
    }

    /**
     * Get the seconds of the Coordinate.
     * 
     * @return seconds (int)
     */
    public int getSeconds() {
        return this.seconds;
    }

    //-- Coordinate Setters

    /**
     * Set the value of the degree of the Coordinate.
     * Checks if the value is correct.
     * 
     * @param degree (int) - The new value of degree.
     * @throws InvalidCoordinateException Throwed if the value is not in [-180; 180].
     * 
     * @author Luc le Manifik
     */
    public void setDegree(int degree) throws InvalidCoordinateException {

        this.degree = degree;

        if(degree < -180 || degree > 180) {
            throw new InvalidCoordinateException(this);
        }
    }

    /**
     * Set the value of the minutes of the Coordinate.
     * Checks if the value id correct.
     * 
     * @param minutes (int) - The new value of minutes.
     * @throws InvalidCoordinateException Throwed if the value is not in [0; 59].
     * 
     * @author Luc le Manifik
     */
    public void setMinutes(int minutes) throws InvalidCoordinateException {

        this.minutes = minutes;

        if(minutes < 0 || minutes > 59) {
            throw new InvalidCoordinateException(this);
        }
    }

    /**
     * Set the value of the seconds of the Coordinate.
     * Checks if the value id correct.
     * 
     * @param seconds (int) - The new value of seconds.
     * @throws InvalidCoordinateException Throwed if the value is not in [0; 59].
     * 
     * @author Luc le Manifik
     */
    public void setSeconds(int seconds) throws InvalidCoordinateException {

        this.seconds = seconds;

        if(seconds < 0 || seconds > 59) {
            throw new InvalidCoordinateException(this);
        }
    }

    //-- Coordinate Abstract Methods

    // These methods are abstract, so that it is not possible 
    // to access the direction of coordinate if it's not defined as a Longitude or a Latitude

    /**
     * Get the value of the direction of the Coordinate.
     * 
     * @return direction (char)
     * 
     * @author Luc le Manifik
     */
    public abstract char getDirection();

    /**
     * Set the value of the direction of the Coordinate.
     * The verification is made in the inherited classes (Longitude and Latitude).
     * 
     * @param direction (char) - The new value of direction.
     * @throws InvalidCoordinateException Throwed if the value of direction is not accepted.
     * 
     * @author Luc le Manifik
     */
    public abstract void setDirection(char direction) throws InvalidCoordinateException;

    /**
     * Returns the decimal value of the D° M' S''' D format.
     * 
     * @return decimalCoordinate (double)
     * 
     * @author Luc le Manifik
     */
    public abstract double getDecimalCoordinate();
}
