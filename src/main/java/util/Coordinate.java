package util;

import exceptions.InvalidCoordinatesException ;

public class Coordinate {
    protected int degree ;
    protected int minutes ;
    protected int seconds ;

    Coordinate(int degree, int minutes, int seconds) throws InvalidCoordinatesException {
        this.degree = degree ;
        this.minutes = minutes ;
        this.seconds = seconds ;

        if (degree < -180 || degree > 180 || minutes < 0 || minutes > 59 || seconds < 0 || seconds > 59) {
            throw new InvalidCoordinatesException(this) ;
        } 
    }

    public int getDegree() {
        return this.degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return this.seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

}
