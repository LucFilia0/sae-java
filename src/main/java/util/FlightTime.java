package util;

import java.sql.Time;
import java.time.LocalTime;

import exceptions.InvalidTimeException;

/**
 * Class handling hours and minutes only for easier access and manipulation in the {@link graph.Flight Flight class}
 * 
 * @author Nathan LIEGEON
 */
public class FlightTime {

    /**
     * The number of hour - [0; 23]
     */
    private int hour ;

    /**
     * The number of minutes - [0; 59]
     */
    private int min ;

    /**
     * Instantiates a FlightTime object
     * 
     * @param hour Values go from 0 to 23
     * @param min Values go from 0 to 59
     * 
     * @author Nathan LIEGEON
     */
    public FlightTime(int hour, int min) {
        try {
            this.setHour(hour) ;
            this.setMinute(min) ;
        }

        catch (InvalidTimeException ite) {
            System.err.println(ite) ;
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

    /**
     * toString() FlightTime's method.
     */
    public String toString() {
        return this.getHour() + "h" + this.getMinute() ;
    }
    
    /**
     * Return the value of the Time in minutes.
     * @return (int) - The value of the Time in minutes.
     * 
     * @author Luc le Manifik
     */
    public int getHourValueInMinutes() {
        return this.hour * 60 + this.min;
    }

    /**
     * Converts a time object to a FlightTime object
     * @param time (Time) - object to convert
     * @return (FlightTime) - converted time
     * 
     * @author Nathan LIEGEON
     */
    public FlightTime convertTime(Time time) {
        int hour = (int)time.getTime()%(1000000 * 60 * 60)/24 ;
        int minute = (int) time.getTime()%(1000000 * 60 * 60) / (24*60) ;
        FlightTime returnTime = new FlightTime(hour, minute) ;
        return returnTime ;
    }

    /**
     * 
     * @return (FlightTime) - CurrentTime
     * 
     * @author Nathan LIEGEON
     */
    public static FlightTime getCurrentTime() {
        return new FlightTime(LocalTime.now().getHour(), LocalTime.now().getMinute()) ;
    }
}
