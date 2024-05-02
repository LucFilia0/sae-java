package graph;

//-- Import Java

import util.FlightTime ;
import util.Airport;
import util.Math;

//-- Import GraphStream

import org.graphstream.graph.implementations.*;

//-- Import Exceptions

import exceptions.InvalidEntryException;


/**
 * Flight extends SingleNode, from GraphStream, and is a node of a FlightsIntersectionGraph.
 * 
 * @implNote Uses the GraphStream attributes.
 * 
 * @author Luc le Manifik
 */
public class Flight extends SingleNode {
    
    // Can only be used with the "graph.addNode()" method.
    protected Flight(AbstractGraph graph, String id) {
        super(graph,id) ; // -> the name of the flight is its identifier
    }

    //-- Flight Attributes

    /**
     * The String identifier that represents the departure Airport ({@link util.Airport util.Airport})
     */
    public static final String DEPARTURE_AIRPORT = "departureAirport";

    /**
     * The String identifier that represents the departure Airport ({@link util.Airport util.Airport})
     */
    public static final String ARRIVAL_AIRPORT = "arrivalAirport";

    /**
     * The String identifier that represents the departure Time ({@link util.Time util.Time})
     */
    public static final String DEPARTURE_TIME = "departureTime";

    /**
     * The String identifier that represents the Flight's duration, in MINUTES (int)
     */
    public static final String FLIGHT_DURATION = "flightDuration";

    /**
     * The String identifier that represents the Flight's layer (int)
     */
    public static final String LAYER = "layer";

    /**
     * Initialize the attributes of a Flight.
     * 
     * @param departureAirport ({@link util.Airport util.Airport}) - The name of the Airport it came from.
     * @param arrivalAirport ({@link util.Airport util.Airport}) - The name of the Airport it goes.
     * @param departureTime (FlightTime) - The hour of the departure.
     * @param flightDuration (int) - The duration of the flight, in MINUTES
     * 
     * @author Luc le Manifik
     */
    public void setFlightAttributes(Airport departureAirport, Airport arrivalAirport, FlightTime departureTime, int flightDuration) throws NullPointerException, InvalidEntryException {
        try {
            this.setDepartureAirport(departureAirport); // -> The airport where it comes
            this.setArrivalAirport(arrivalAirport); // -> The airport where it goes
            this.setDepartureTime(departureTime); // -> The time of the departure
            this.setFlightDuration(flightDuration); // -> The duration of the flight, in MINUTES
    
            this.setLayer(0); // -> The coloration : The layer on which the Flight is placed (0 means no layer attributed)
        }catch(NullPointerException npe) {
            throw npe;
        }catch(InvalidEntryException iee) {
            throw iee;
        }
    }

    //-- Flight toString()

    /**
     * Returns the informations of Flight in (String).
     * @return (String)
     */
    public String toString() {
        return "-- Flight\nName : " + super.getId() + "\nDeparture Airport : " + this.getAttribute("departureAirport") + "\nArrival Airport : " + this.getAttribute("arrivalAirport") + "\nDeparture Time : " + this.getAttribute("departureTime") + "\nDuration Flight : " + this.getAttribute("durationFlight") + "\nLayer : " + this.getAttribute("layer");
    }

    //-- Flight Getters

    /**
     * Get the departure Airport of the Flight.
     * 
     * @return obj ({@link util.Airport util.Airport})
     * 
     * @author Luc le Manifik
     */
    public Airport getDepartureAirport() {
        return (Airport)this.getAttribute(Flight.DEPARTURE_AIRPORT);
    }

    /**
     * Get the arrival Airport of the Flight.
     * 
     * @return obj ({@link util.Airport util.Airport})
     * 
     * @author Luc le Manifik
     */
    public Airport getArrivalAirport() {
        return (Airport)this.getAttribute(Flight.ARRIVAL_AIRPORT);
    }

    /**
     * Get the departure Time of the Flight.
     * 
     * @return obj ({@link util.Time util.Time})
     * 
     * @author Luc le Manifik
     */
    public FlightTime getDepartureTime() {
        return (FlightTime)this.getAttribute(Flight.DEPARTURE_TIME);
    }

    /**
     * Get the duration of the Flight (in MINUTES).
     * 
     * @return obj (int)
     * 
     * @author Luc le Manifik
     */
    public int getFlightDuration() {
        return (int)this.getAttribute(Flight.FLIGHT_DURATION);
    }

    /**
     * Get the layer on which is the Flight.
     * 
     * @return obj (int)
     * 
     * @author Luc le Manifik
     */
    public int getLayer(){
        return (int)this.getAttribute(Flight.LAYER);
    }

    //-- Flight Setters

    /**
     * Set the departure Airport of the Flight.
     * 
     * @param departureAirport ({@link util.Airport util.Airport}) - The new departure Airport.
     * @throws NullPointerException Throwed if the Object passed in paramter is null.
     * 
     * @author Luc le Manifik
     */
    public void setDepartureAirport(Airport departureAirport) throws NullPointerException {
        if(departureAirport == null) {
            throw new NullPointerException();
        }
        this.setAttribute(Flight.DEPARTURE_AIRPORT, departureAirport);
    }

    /**
     * Set the arrival Airport of the Flight.
     * 
     * @param arrivalAirport ({@link util.Airport util.Airport}) - The new arrival Airport.
     * @throws NullPointerException Throwed if the Object passed in paramter is null.
     * 
     * @author Luc le Manifik
     */
    public void setArrivalAirport(Airport arrivalAirport) throws NullPointerException {
        if(arrivalAirport == null) {
            throw new NullPointerException();
        }
        this.setAttribute(Flight.ARRIVAL_AIRPORT, arrivalAirport);
    }

    /**
     * Set the departure Time of the Flight.
     * 
     * @param departureTime ({@link util.Time util.Time}) - The new departure Time of the Flight.
     * @throws NullPointerException Throwed if the Object passed in paramter is null.
     * 
     * @author Luc le Manifik
     */
    public void setDepartureTime(FlightTime departureTime) throws NullPointerException {
        if(departureTime == null) {
            throw new NullPointerException();
        }
        this.setAttribute(Flight.DEPARTURE_TIME, departureTime);
    }

    /**
     * Set the duration flight of the Flight.
     * 
     * @param flightDuration (int) - The new duration flight of the Flight.
     * @throws InvalidEntryException Throwed if the flight duration is inferior or equals to 0.
     * 
     * @author Luc le Manifik
     */
    public void setFlightDuration(int flightDuration) throws InvalidEntryException {
        if(flightDuration <= 0) {
            throw new InvalidEntryException();
        }
        this.setAttribute(Flight.FLIGHT_DURATION, flightDuration);
    }

    /**
     * Set the new layer on which the Flight is.
     * 
     * @param layer (int) - The new layer of the Flight.
     * 
     * @author Luc le Manifik
     */
    public void setLayer(int layer) {
        if(layer >= 0) {
            this.setAttribute(Flight.LAYER, layer);
        }
    }

    //-- Flight Methods

    /**
     * Check if the Flight passed in parameter is exploding with the current Flight. It does not consider the different layers
     *  
     * @param tangoCharlie ({@link graph.Flight graph.Flight}) - The Flight with wich we check the collision.
     * @param timeSecurity (double) - The value below which we consider that two Flights are in collision.
     * 
     * @return explode (boolean) - Returns "true" if the two Flights collide, else "false".
     * 
     * @author Luc le Manifik
     */
    public boolean isBooming(Flight tangoCharlie, double timeSecurity) {

        /*
        * Steps :
        *      Step 0 : Check if "this" is not "tangoCharlie"
        *      Step 1 : Get the coordinates
        *      Step 2 : Check if the routes of the two Flights are crossing
        *      Step 3 : If they are crossing, then check WHEN they cross, and what is the time gap between them. If it's inferior to "timeSecurity"
        *              then the function returns "true".
        */

        boolean explode = false;
        
        if(!this.equals(tangoCharlie)) {
            /*
            * ====== STEP 1 : Getting the coordinates
            * 
            * We get the different coordinates
            */
    
            // Coordinates defintion
            double depX_A, depY_A; // Coordinates departure Airport of FlightA : "this"
            double arrX_A, arrY_A; // Coordinates arrival Airport of FlightA : "this"
    
            double depX_B, depY_B; // Coordinates departure Airport of FlightB : "tangoCharlie"
            double arrX_B, arrY_B; // Coordinates arrival Airport of FlightB : "tangoCharlie"
    
            double slope_A, slope_B; // The slope/directing coefficient of the two lines/flight's route
            double originCoordinate_A, originCoordinate_B; // The coordinate at the origin
    
            // Crossing cooordinate (Where the Flights are supposed to EXPLODE)
            double crossX;
            // double crossY; -> never used
    
            depX_A = this.getDepartureAirport().getLongitude().getDecimalCoordinate();
            depY_A = this.getDepartureAirport().getLatitude().getDecimalCoordinate();

            arrX_A = this.getArrivalAirport().getLongitude().getDecimalCoordinate();
            arrY_A = this.getArrivalAirport().getLatitude().getDecimalCoordinate();

            depX_B = tangoCharlie.getDepartureAirport().getLongitude().getDecimalCoordinate();
            depY_B = tangoCharlie.getDepartureAirport().getLatitude().getDecimalCoordinate();

            arrX_B = tangoCharlie.getArrivalAirport().getLongitude().getDecimalCoordinate();
            arrY_B = tangoCharlie.getArrivalAirport().getLatitude().getDecimalCoordinate();
    
    
            /*
            * ===== STEP 2 :
            * 
            * We try to find if the Flight's routes are crossing between there departure and arrival point.
            * 
            * //-- First route equation (Flight A : "this")
            * 
            * slope_A = deltaY / deltaX
            *         = (arrY_A - depY_A) / (arrX_A - depX_A)
            * Getting originCoordinate_A :
            *       y = slope_A * x + originCoordinate_A
            *       originCoordinate_A = depY_A - (slope_A * depX_A)
            * 
            *    => RA : y = slope_A * x + originCoordinate_A
            *  
            * //-- Second route equation (Flight B : "tangoCharlie")
            * 
            * slope_B = deltaY / deltaX
            *         = (arrY_B - depY_B) / (arrX_B - depX_B)
            * Getting originCoordinate_B :
            *       y = slope_B * x + originCoordinate_B
            *       originCoordinate_B = depY_B - (slope_B * depX_B)
            * 
            *    => RB : y = slope_B * x + originCoordinate_B
            * 
            * We are searching "x" when "y" is the same into RA and RB, so when :  
            * 
            *     slope_B * x + originCoordinate_B = slope_A * x + originCoordinate_A
            * <=> slope_B * x - slope_A * x = originCoordinate_A - originCoordinate_B
            * <=> x(slope_B - slope_A) = originCoordinate_A - originCoordinate_B
            * 
            *    =>  x = (originCoordinate_A - originCoordinate_B) / (slope_B - slope_A)
            * 
            * "x" is now abscissa of the crossing point. We now need to check if "x" 
            * is between the coordinates of [depX_A; arrX_A] and [depX_B; arrX_B].
            * If it's the case, then it means that the crossing point is on the  two segments that represents the routes
            * of the two Flights.
            */
    
            // Route A (Flight A : "this")
            slope_A = (arrY_A - depY_A) / (arrX_A - depX_A);
            originCoordinate_A = depY_A - (slope_A * depX_A);
            // => y = slope_A * x + originCoordinate_A
    
            // Route B (Flight B : "tangoCharlie")
            slope_B = (arrY_B - depY_B) / (arrX_B - depX_B);
            originCoordinate_B = depY_B - (slope_B * depX_B);
            // => y = slope_B * x + originCoordinate_B
    
            // Crossing coordinate
            if(slope_A != slope_B) { // If both slopes are the same, then the Flight's routes are parallel, and they never cross.
                crossX = (originCoordinate_A - originCoordinate_B) / (slope_B - slope_A); // Mathematic resoltion, don't ask
                // crossY = slope_A * crossX + originCoordinate_A; -> never used
    
                // Check if crossX is on the route of A AND on the route of B
                if(((depX_A <= crossX && crossX <= arrX_A) || (arrX_A <= crossX && crossX <= depX_A)) && ((depX_B <= crossX && crossX <= arrX_B) || (arrX_B <= crossX && crossX <= depX_B))) {
                    /*
                    * ===== STEP 3 :
                    * 
                    * Searching the time gap between A and B.
                    * 
                    * /!\ Here, regex are used : [AB] means "A" OR "B".
                    * 
                    * We consider the position of the two Flights on the X_AXIS only. We know the "x" position where the are supposed to collide, so
                    * we just check the time gap between their time at crossX.
                    * 
                    * Position equation, with a = 0 (constant speed, so null acceleration) and x0 = depX_[AB] :
                    *      x(t) = 1/2*aX*t² + vX*t + x0
                    * Traduction with the variables :
                    *      crossX = speedX_[AB]*t + depX_[AB]
                    * 
                    *      => t = (crossX - depX_[AB]) / speedX_[AB]
                    * 
                    * Speed of the Flights (on the X_AXIS) :
                    * vX = dX/dt
                    * with : dX (distance on X_AXIS) = (arrX_[AB] - depX_[AB])
                    *        dt (delta time) = [AB].getFlightDuration()
                    * So :
                    *      speedX_[AB] = (arrX_[AB] - depX_[AB]) / [AB].getFlightDuration()
                    * 
                    * Then we do :
                    *      t = (crossX - depX_[AB]) / speedX_[AB]
                    * It will gave the time gap between the departureTime of the Flight and the crossX coordinate.
                    * So we need to add the departureTime, and THEN compare the time gap.
                    */
    
                    // Declaration of the required variables to calcul Flight's speed
                    double speedX_A, speedX_B; // The speeds of the Flights ("this" and "tangoCharlie").
                    int flightDuration_A, flightDuration_B; // The durations of the Flights (in MINUTES).
                    double flightDistanceX_A, flightDistanceX_B; // The distance travelled by the Flights
                    
                    // The time when they get to the crossing point.
                    double crossTime_A, crossTime_B;
                    double timeGap;
                    
                    // Flight duration (in MINUTES)
                    flightDuration_A = this.getFlightDuration();
                    flightDuration_B = tangoCharlie.getFlightDuration();
    
                    // Flight distance (on X_AXIS)
                    flightDistanceX_A = Math.absoluteValue(depX_A, arrX_A);
                    flightDistanceX_B = Math.absoluteValue(depX_B, arrX_B);
    
                    // Flight speed
                    speedX_A = flightDistanceX_A / flightDuration_A; // v = d/dt
                    speedX_B = flightDistanceX_B / flightDuration_B; // v = d/dt
                    // Unit of the speed is something like "degree per minute"
    
                    // Time when the Flights get to the crossing point
                    // We are adding there departure time to the time they pass by crossX to get the time where they pass by crossX (your head is exploding right now)
                    
                    double deltaX_A, deltaX_B;
    
                    deltaX_A = Math.absoluteValue(crossX, depX_A);
                    deltaX_B = Math.absoluteValue(crossX, depX_B);
    
                    crossTime_A = (deltaX_A / speedX_A) + this.getDepartureTime().getHourValueInMinutes(); // We add the departure Time, to get the real time/hour when the Flight will get to the crossing point.
                    crossTime_B = (deltaX_B / speedX_B) + tangoCharlie.getDepartureTime().getHourValueInMinutes();    
                    
                    timeGap = Math.absoluteValue(crossTime_A, crossTime_B); // The time difference between the two crossTime
    
                    // Check if the timeGap is between the timeSecurity -> If the Flights are getting to the crossing point at the same time.
                    if(timeGap < timeSecurity) {
                        explode = true;
                    }
    
                }
            }
        }
        return explode;
    }
}
