package util;

import exceptions.InvalidCoordinateException;

/**
 * This class stores the geographical coordinates of the airports.
 * 
 * @author Nathan LIEEON - Modified : Luc le Manifik
 */
public class Longitude extends Coordinate {

    //-- Longitude Constructor

    /**
     * Constructor of the Longitude class. Creates a new Longitude.
     * 
     * @param degree (int)
     * @param minutes (int)
     * @param seconds (int)
     * @param direction (char)
     * @throws InvalidCoordinateException Throwed if the values are not correct.
     * 
     * @author Luc le Manifik
     */
    Longitude(int degree, int minutes, int seconds, char direction) throws InvalidCoordinateException {
        super(degree, minutes, seconds) ;
        super.direction = direction ;

        if (degree < -180 || degree > 180 || minutes < 0 || minutes > 59 || seconds < 0 || seconds > 59 || (direction != 'E' && direction != 'O')) {
            throw new InvalidCoordinateException(this) ;
        }
    }

    //-- Latitude's implemented abstract methods from Coordinate

    // JavaDoc made in Coordinate
    public char getDirection() {
        return super.direction;
    }

    // JavaDoc made in Coordinate
    public void setDirection(char direction) throws InvalidCoordinateException {
        super.direction = direction;
        if(direction != 'E' && direction != 'O') {
            throw new InvalidCoordinateException(this);
        }
    }

    // JavaDoc made in Coordinate
    public double getDecimalCoordinate() {
        double decimalCoordinate; 
        decimalCoordinate = this.minutes + this.seconds/60;
        decimalCoordinate = this.degree + decimalCoordinate/60;

        // If the direction is West, then it turns negative
        if(this.direction == 'O') {
            decimalCoordinate = decimalCoordinate * -1;
        }
        return decimalCoordinate;
    }

}
