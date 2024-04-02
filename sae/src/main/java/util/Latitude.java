package util;

import exceptions.InvalidLatitudeDirectionException;

public class Latitude extends Coordinate {
    protected char direction ;

    public Latitude(int degree, int minutes, int seconds, char direction) {
        // direction doit être 'N' (North) ou 'S' (South), sinon mise à ' ' + InvalidLatitudeDirectionException

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
        this.direction = direction;
        if (direction != 'N' && direction != 'S') {
            throw new InvalidLatitudeDirectionException() ;
        }
    }

    public String toString() {
        return super.toString() + ' ' + this.getDirection() ;
    }

}
