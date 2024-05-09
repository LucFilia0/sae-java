package util;

import exceptions.InvalidCoordinateException;

/**
 * This class stores the geographical coordinates of the airports.
 * 
 * @author Nathan LIEGEON - Modified : Luc le Manifik
 */
public class Latitude extends Coordinate {
    protected char direction ;

    //-- Latitude Constructor

    /**
     * Constructor of the Latitude class. Creates a new Latitude.
     * 
     * @param degree (int) - [-90; 90]
     * @param minutes (int) - [0; 59]
     * @param seconds (int) - [0; 59]
     * @param direction (char) - [NS] (Regex)
     * @throws InvalidCoordinateException Throwed if the values are not correct.
     * 
     * @author Luc le Manifik
     */
    public Latitude(int degree, int minutes, int seconds, char direction) throws InvalidCoordinateException {
        super(degree, minutes, seconds) ;
        super.direction = direction ;

        if (degree < -90 || degree > 90 || minutes < 0 || minutes > 59 || seconds < 0 || seconds > 59 || (direction != 'N' && direction != 'S')) {
            throw new InvalidCoordinateException(this) ;
        }
    }

    //-- Latitude's implemented abstract methods from Coordinate

    // JavaDoc made in Coordinate
    public char getDirection() {
        return this.direction;
    }

    // JavaDoc made in Coordinate
    public void setDirection(char direction) throws InvalidCoordinateException {
        this.direction = direction;
        if(direction != 'N' && direction != 'S') {
            throw new InvalidCoordinateException(this);
        }
    }

    // JavaDoc made in Coordinate
    public double getDecimalCoordinate() {
        double decimalCoordinate;
        decimalCoordinate = this.minutes + this.seconds/(double)60;
        decimalCoordinate = this.degree + decimalCoordinate/60;

        // If the direction is South, then is turns negative.
        if(this.direction == 'S') {
            decimalCoordinate = decimalCoordinate * -1;
        }
        return decimalCoordinate;
    }

}
