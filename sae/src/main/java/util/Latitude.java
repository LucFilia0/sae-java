package util;

import exceptions.InvalidLatitudeDirectionException;

/**
 * Class extending {@link Coordinate}
 * Handles directions which can only be can only be 'N' for Nord (North) or 'S' for Sud (South)
 * 
 * @author Nathan LIEGEON
 */
public class Latitude extends Coordinate {
    protected char direction ;

    /**
     * Creates a Latitude object, see Coordinate for how the first 3 fields are handled
     * 
     * @param degree 
     * @param minutes
     * @param seconds 
     * @param direction can only be 'N' or 'S'
     * 
     * @throws InvalidLatitudeDirectionException
     */
    public Latitude(int degree, int minutes, int seconds, char direction) {

        super(degree, minutes, seconds) ;
        try {
            this.setDirection(direction) ;
        }

        catch (InvalidLatitudeDirectionException ILaDE) {
            System.err.println(ILaDE) ;
            this.direction = ' ' ;
        }
    }

    public char getDirection() {
        return this.direction ;
    }

    public void setDirection(char direction) throws InvalidLatitudeDirectionException {
        this.direction = Character.toUpperCase(direction) ;
        if (direction != 'N' && direction != 'S') {
            throw new InvalidLatitudeDirectionException() ;
        }
    }

    public String toString() {
        return super.toString() + ' ' + this.getDirection() ;
    }

}
