package util;

import exceptions.InvalidLongitudeDirectionException;


/**
 * Class extending {@link Coordinate}
 * Handles directions which can only be 'E' for Est (East) or 'O' for Ouest (West)
 * 
 * @author Nathan LIEGEON
 */
public class Longitude extends Coordinate {
    protected char direction ;

    /**
     * Instantiated a Longitude Object
     * 
     * @param degree
     * @param minutes
     * @param seconds
     * @param direction Can only be 'E' or 'O', if 'W' is entered it will be converted to 'O'
     * 
     * @author Nathan LIEGEON
     */
    public Longitude(int degree, int minutes, int seconds, char direction) {

        super(degree, minutes, seconds) ;
        try {
            this.setDirection(direction); ;
        }

        catch (InvalidLongitudeDirectionException ILoDE) {
            System.err.println(ILoDE) ;
            this.direction = ' ' ;
        }

    }
    
    /**
     * Getter for the direction of the object
     * 
     * @return the direction of the object
     */
    public char getDirection() {
        return this.direction;
    }

    public void setDirection(char direction) throws InvalidLongitudeDirectionException {
        this.direction = Character.toUpperCase(direction) ;
        if (this.direction == 'W') {
            this.direction = 'O' ;    
        }

        if (direction != 'E' && direction != 'O') {
            throw new InvalidLongitudeDirectionException() ;
        }
    }

    public String toString() {
        return super.toString() + ' ' + this.getDirection() ;
    }

}
