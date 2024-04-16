package util;

import exceptions.InvalidTimeException;

/**
 * Class handling hours and minutes only for easier access and manipulation
 * 
 * @author Nathan LIEGEON
 */
public class FlightTime {
    private int hour ;
    private int min ;

    /**
     * Instantiates a FlightTime object
     * 
     * @param hour Values go from 0 to 23
     * @param min Values go from 0 to 59
     * 
     * @throws InvalidTimeException
     * 
     * @author Nathan LIEGEON
     */
    public FlightTime(int hour, int min) {
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

    /**
     * Getter for the hour field
     * 
     * @return the hour of the object
     * 
     */
    public int getHour() {
        return this.hour ;
    }

    /**
     * Setter for the hour field
     * 
     * @param hour Values go from 0 to 23
     * @throws InvalidTimeException
     */
    public void setHour(int hour) throws InvalidTimeException {
        this.hour = hour ;
        if (hour < 0 || hour > 23) {
            throw new InvalidTimeException(this) ;
        }
    }

    /**
     * Getter for the min field
     * 
     * @return the min of the object
     */
    public int getMinute() {
        return this.min;
    }

    /**
     * Setter for the min field
     * 
     * @param min Values go from 0 to 59
     * @throws InvalidTimeException
     */
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
