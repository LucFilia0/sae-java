package util;

import exceptions.InvalidTimeException;

public class Time {
    private int hour ;
    private int min ;

    public Time(int hour, int min) {
        // hour et min vont de 0 à 59, mis à 0 sinon + InvalidTimeException
        try {
            this.setHour(hour) ;
            this.setMinute(min) ;
        }

        catch (InvalidTimeException ITE) {
            System.err.println(ITE) ;
            this.hour = 0 ;
            this.min = 0 ;
        }

    }


    public int getHour() {
        return this.hour ;
    }

    public void setHour(int hour) throws InvalidTimeException {
        this.hour = hour ;
        if (hour < 0 || hour > 23) {
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
