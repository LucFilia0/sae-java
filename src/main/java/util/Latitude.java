package util;

import exceptions.InvalidCoordinatesException;
import exceptions.InvalidLatitudeDirectionException;

public class Latitude extends Coordinate {
    protected char direction ;

    public Latitude(int degree, int minutes, int seconds, char direction) throws InvalidLatitudeDirectionException, InvalidCoordinatesException {
        super(degree, minutes, seconds) ;
        this.direction = direction ;
    }

    public char getDirection() {
        return this.direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

}
