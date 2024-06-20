package planeair.util;

//#region IMPORTS
import java.sql.Time;
import java.time.LocalTime;

import planeair.exceptions.InvalidTimeException;
//#endregion

/**
 * Class handling hours and minutes only for easier access and manipulation in the {@link graph.Flight Flight class}
 * 
 * @author Nathan LIEGEON
 */
public class NTime implements Comparable<NTime> {

    /**
     * The number of hour - [0; 23]
     */
    private int hour ;

    /**
     * The number of minutes - [0; 59]
     */
    private int min ;

    /**
     * Instantiates a NTime object
     * 
     * @param hour In the range [0 ; 23]
     * @param min In the range [0 ; 23]
     * 
     * @throws InvalidTimeException Thrown if the value of the hour 
     * or minute field is outside of the allowed range
     * 
     * @author Nathan LIEGEON
     */
    public NTime(int hour, int min) throws InvalidTimeException {
        try {
            this.setHour(hour) ;
            this.setMinute(min) ;
        }

        catch (InvalidTimeException ite) {
            throw ite ;
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
     * @param hour In the range [0 ; 23]
     * @throws InvalidTimeException Thrown if not in the valid range
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
     * @param min In the range [0 ; 59]
     * @throws InvalidTimeException Thrown if not in the valid range
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
        String between = null;
        if(this.min < 10)
            between = "h0";
        else
            between = "h";
        return this.getHour() + between + this.getMinute() ;
    }
    
    /**
     * Return the value of the Time in minutes.
     * @return (int) - The value of the Time in minutes.
     * 
     * @author Luc le Manifik
     */
    public int getValueInMinutes() {
        return this.hour * 60 + this.min;
    }

    /**
     * Converts a time object to a FlightTime object
     * @param time (Time) - object to convert
     * @return (NTime) - converted time
     * 
     * @throws InvalidTimeException Thrown if the converted time ever has
     * invalid values (should basically never happen)
     * 
     * @author Nathan LIEGEON
     */
    public NTime convertTime(Time time) throws InvalidTimeException {
        int hour = (int)time.getTime()%(1000000 * 60 * 60)/24 ;
        int minute = (int) time.getTime()%(1000000 * 60 * 60) / (24*60) ;
        try {
            NTime returnTime  = new NTime(hour, minute) ;
            return returnTime ;
        } catch (InvalidTimeException e) {
            throw e ;
        }
    }

    /**
     * 
     * @return (FlightTime) - CurrentTime
     * 
     * @author Nathan LIEGEON
     */
    public static NTime getCurrentTime() {
        try {
            return new NTime(LocalTime.now().getHour(), 
                LocalTime.now().getMinute()) ;
        } catch (InvalidTimeException e) {
            System.err.println(
                "if this ever happens then the world is fuck f-ed") ;
            return null ;
        }
    }
    
    @Override
    public int compareTo(NTime o) {
        return Integer.compare(this.getValueInMinutes(), o.getValueInMinutes()) ;
    }
}
