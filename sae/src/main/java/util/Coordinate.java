package util;

import exceptions.InvalidCoordinatesException ;

public abstract class Coordinate {
    protected int degree ;
    protected int minutes ;
    protected int seconds ;

    protected Coordinate(int degree, int minutes, int seconds) {
        // degree doit être entre -180 et 180, mis à 0 sinon + InvalidCoordinateException
        // minutes et secondes vont de 0 à 59, mis à 0 sinon + InvalidCoordinateException

        try {
            this.setDegree(degree) ;
            this.setMinutes(minutes) ;
            this.setSeconds(seconds) ;
        }
        
        catch (InvalidCoordinatesException ICE) {
            System.err.println(ICE) ;
            this.degree = 0 ;
            this.minutes = 0 ;
            this.seconds = 0 ;
        }
    }

    public int getDegree() {
        return this.degree ;
    }

    public void setDegree(int degree) throws InvalidCoordinatesException {
        this.degree = degree ;
        if (degree < -180 || degree > 180) {
            throw new InvalidCoordinatesException(this) ;
        }
    }

    public int getMinutes() {
        return this.minutes ;
    }

    public void setMinutes(int minutes) throws InvalidCoordinatesException {
        this.minutes = minutes ;
        if (minutes < 0 || minutes > 59) {
            throw new InvalidCoordinatesException(this) ;
        }
    }

    public int getSeconds() {
        return this.seconds ;
    }

    public void setSeconds(int seconds) throws InvalidCoordinatesException {
        this.seconds = seconds ;
        if (seconds < 0 || seconds > 59) {
            throw new InvalidCoordinatesException(this) ;
        }
    }

    public String toString() {
        return Integer.toString(this.getDegree()) + ' ' + Integer.toString(this.getMinutes()) + ' ' + Integer.toString(this.getSeconds()) ;
    }

}
