package util;

import exceptions.InvalidCoordinatesException;
import exceptions.InvalidLongitudeDirectionException;

public class Longitude extends Coordinate {
    protected char direction ;

    public Longitude(int degree, int minutes, int seconds, char direction) throws InvalidLongitudeDirectionException, InvalidCoordinatesException {
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
