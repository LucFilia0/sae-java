package util;

import exceptions.InvalidLongitudeDirectionException;

public class Longitude extends Coordinate {
    protected char direction ;

    public Longitude(int degree, int minutes, int seconds, char direction) {
        // direction doit être 'E' (East) ou 'W' (West), sinon mise à ' ' + InvalidLongitudeDirectionException

        super(degree, minutes, seconds) ;
        try {
            this.setDirection(direction); ;
        }

        catch (InvalidLongitudeDirectionException ILoDE) {
            System.err.println(ILoDE) ;
            this.direction = ' ' ;
        }

    }

    public char getDirection() {
        return this.direction;
    }

    public void setDirection(char direction) throws InvalidLongitudeDirectionException {
        this.direction = direction;
        if (direction != 'E' && direction != 'W') {
            throw new InvalidLongitudeDirectionException() ;
        }
    }

}
