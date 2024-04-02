package util;

import exceptions.InvalidTimeException;

public class Time {
    private int h ;
    private int min ;

    public Time(int h, int min) {
        try {
            this.setHour(h);
            this.setMinutes(min);
        }

        catch (InvalidTimeException ITE) {

        }

    }


    public int getHour() {
        return this.h;
    }

    public void setHour(int h) throws InvalidTimeException {
        this.h = h;
        if (h < 0 || h > 23) {
            throw new InvalidTimeException(this) ;
        }
    }

    public int getMinute() {
        return this.min;
    }

    public void setMinute(int min) throws InvalidTimeException {
        this.min = min;
        if (min < 0 || min > 59) {
            throw new InvalidTimeException(this) ;
        }
    }

    public String toString() {
        return this.getHour() + "h" + this.getMinute() ;
    }
    
}
