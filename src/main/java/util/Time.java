package util;

import exceptions.InvalidTimeException;

public class Time {
    private int h ;
    private int min ;

    public Time(int h, int min) throws InvalidTimeException {
        this.h = h ;
        this.min = min ;

        if (h < 0 || h > 23 || min < 0 || min > 59) {
            throw new InvalidTimeException(this) ;
        }
    }


    public int getHours() {
        return this.h;
    }

    public void setHours(int h) throws InvalidTimeException {
        this.h = h;
        if (h < 0 || h > 23) {
            throw new InvalidTimeException(this) ;
        }
    }

    public int getMinutes() {
        return this.min;
    }

    public void setMinutes(int min) throws InvalidTimeException {
        this.min = min;
        if (min < 0 || min > 59) {
            throw new InvalidTimeException(this) ;
        }
    }

    public String toString() {
        return this.getHours() + "h" + this.getMinutes() ;
    }

    /**
     * Return the value of the Time in minutes.
     * @return (int) - The value of the Time in minutes.
     * 
     * @author Luc le Manifik
     */
    public int getHourValueInMinutes() {
        return this.h * 60 + this.min;
    }
    
}
