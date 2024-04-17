package graph;

//-- Import Java

import util.Time ;
import util.Airport;
import java.lang.Math;

//-- Import GraphStream

import org.graphstream.graph.implementations.*;

//-- Import Exceptions

import exceptions.ObjectNotFoundException;


/**
 * Flight extends SingleNode, from GraphStream, and is a node of a FlightsIntersectionGraph. 
 * 
 * @author Luc le Manifik
 */
public class Flight extends SingleNode {
    
    protected Flight(AbstractGraph graph, String id) {
        super(graph,id) ; // -> the name of the flight is its identifier
    }

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
    public void setFlightAttributes(Airport departureAirport, Airport arrivalAirport, Time departureTime, int flightDuration) {
        this.setAttribute("departureAirport", departureAirport) ; // -> The airport where it comes
        this.setAttribute("arrivalAirport", arrivalAirport) ; // -> The airport where it goes
        this.setAttribute("departureTime", departureTime) ; // -> The time of the departure
        this.setAttribute("flightDuration", flightDuration) ; // -> The duration of the flight, in MINUTES

        this.setAttribute("layer", 0); // -> The coloration : The layer on which the Flight is placed (0 means no layer attributed)
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
     * @throws ObjectNotFoundException Throwed when the key does not match any object.
     * 
     * @author Luc le Manifik
     */
    public Airport getDepartureAirport() throws ObjectNotFoundException {
        Object obj = this.getAttribute("departureAirport");
        if(obj == null) {
            throw new ObjectNotFoundException();
        }
        return (Airport)obj;
    }

    /**
     * Get the arrival Airport of the Flight.
     * 
     * @return obj ({@link util.Airport util.Airport})
     * @throws ObjectNotFoundException Throwed when the key does not match any object.
     * 
     * @author Luc le Manifik
     */
    public Airport getArrivalAirport() throws ObjectNotFoundException {
        Object obj = this.getAttribute("arrivalAirport");
        if(obj == null) {
            throw new ObjectNotFoundException();
        }
        return (Airport)obj;
    }

    /**
     * Get the departure Time of the Flight.
     * 
     * @return obj ({@link util.Time util.Time})
     * @throws ObjectNotFoundException Throwed when the key does not match any object.
     * 
     * @author Luc le Manifik
     */
    public Time getDepartureTime() throws ObjectNotFoundException {
        Object obj = this.getAttribute("departureTime");
        if(obj == null) {
            throw new ObjectNotFoundException();
        }
        return (Time)obj;
    }

    /**
     * Get the duration of the Flight (in MINUTES).
     * 
     * @return obj (int)
     * @throws ObjectNotFoundException Throwed when the key does not match any object.
     * 
     * @author Luc le Manifik
     */
    public int getFlightDuration() throws ObjectNotFoundException {
        Object obj = this.getAttribute("flightDuration");
        if(obj == null) {
            throw new ObjectNotFoundException();
        }
        return (int)obj;
    }

    /**
     * Get the layer on which is the Flight.
     * 
     * @return obj (int)
     * @throws ObjectNotFoundException Throwed when the key does not match any object.
     * 
     * @author Luc le Manifik
     */
    public int getLayer() throws ObjectNotFoundException {
        Object obj = this.getAttribute("layer");
        if(obj == null) {
            throw new ObjectNotFoundException();
        }
        return (int)obj;
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
        this.setAttribute("departureAirport", departureAirport);
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
        this.setAttribute("arrivalAirport", arrivalAirport);
    }

    /**
     * Set the departure Time of the Flight.
     * 
     * @param departureTime ({@link util.Time util.Time}) - The new departure Time of the Flight.
     * @throws NullPointerException Throwed if the Object passed in paramter is null.
     * 
     * @author Luc le Manifik
     */
    public void setDepartureTime(Time departureTime) throws NullPointerException {
        if(departureTime == null) {
            throw new NullPointerException();
        }
        this.setAttribute("departureTime", departureTime);
    }

    /**
     * Set the duration flight of the Flight.
     * 
     * @param flightDuration (int) - The new duration flight of the Flight.
     * 
     * @author Luc le Manifik
     */
    public void setFlightDuration(int flightDuration) {
        if(flightDuration >= 0) {
            this.setAttribute("flightDuration", flightDuration);
        }
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
            this.setAttribute("layer", layer);
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
     * @throws ObjectNotFoundException Throwed if the attributes of the Airports (departure or arrival) are empty or if the attribute's keys are incorrect.
     * 
     * @author Luc le Manifik
     */
    public boolean isBooming(Flight tangoCharlie, double timeSecurity) throws ObjectNotFoundException {
        
        /*
         * Steps :
         *      Step 1 : Get the coordinates
         *      Step 2 : Check if the routes of the two Flights are crossing
         *      Step 3 : If they are crossing, then check WHEN they cross, and what is the time gap between them. If it's inferior to "timeSecurity"
         *              then the function returns "true".
         */

        boolean explode = false;

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

        try {
            depX_A = this.getDepartureAirport().getLongitude().getDecimalCoordinate();
            depY_A = this.getDepartureAirport().getLatitude().getDecimalCoordinate();

            arrX_A = this.getArrivalAirport().getLongitude().getDecimalCoordinate();
            arrY_A = this.getArrivalAirport().getLatitude().getDecimalCoordinate();

            depX_B = tangoCharlie.getDepartureAirport().getLongitude().getDecimalCoordinate();
            depY_B = tangoCharlie.getDepartureAirport().getLatitude().getDecimalCoordinate();

            arrX_B = tangoCharlie.getArrivalAirport().getLongitude().getDecimalCoordinate();
            arrY_B = tangoCharlie.getArrivalAirport().getLatitude().getDecimalCoordinate();
        }catch(ObjectNotFoundException onfe) {
            throw onfe;
        }


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
                 * Position equation, with a = 0 (constant speed, so null acceleration) and x0 = depX_[AB] :
                 *      x(t) = 1/2*a*t² + v0*t + x0
                 * Traduction with the variables :
                 *      crossX = v_[AB]*t + depX_[AB]
                 * 
                 *      => t = (crossX - depX_[AB]) / speed_[AB]
                 * 
                 * Speed of the Flights :
                 * v = d/dt
                 * with : d (distance) = sqrt((arrX_A - depX_A)**2 + (arrY_A - depY_A)**2)
                 *        dt (delta time) = Flight[AB].getFlightDuration()
                 * So :
                 *      speed_[AB] = (crossX - depX_[AB]) / speed_[AB]
                 */

                // Declaration of the required variables to calcul Flight's speed
                double speed_A, speed_B; // The speeds of the Flights ("this" and "tangoCharlie").
                int flightDuration_A, flightDuration_B; // The durations of the Flights (in MINUTES).
                double flightDistance_A, flightDistance_B; // The distance travelled by the Flights
                
                // The time when they get to the crossing point.
                double crossTime_A, crossTime_B;
                double timeGap;
                
                // Flight duration
                try {
                    flightDuration_A = this.getFlightDuration();
                    flightDuration_B = tangoCharlie.getFlightDuration();
                }catch(ObjectNotFoundException onfe) {
                    throw onfe;
                }

                // Flight distance
                double deltaX, deltaY;
                
                deltaX = (arrX_A - depX_A);
                deltaY = (arrY_A - depY_A);
                flightDistance_A = Math.sqrt(deltaX*deltaX + deltaY*deltaY); // Pythagore

                deltaX = (arrX_B - depX_B);
                deltaY = (arrY_B - depY_B);
                flightDistance_B = Math.sqrt(deltaX*deltaX + deltaY*deltaY); // Pythagore

                // Flight speed
                speed_A = flightDistance_A / flightDuration_A; // v = d/dt
                speed_B = flightDistance_B / flightDuration_B; // v = d/dt
                // Unit of the speed is something like "degree per minute"

                // Time when the Flights get to the crossing point
                crossTime_A = (crossX - depX_A) / speed_A;
                crossTime_B = (crossX - depX_B) / speed_B;

                timeGap = crossTime_A - crossTime_B; // The time difference between the two crossTime

                if(timeGap < 0)
                    timeGap = timeGap * -1;

                // Check if the timeGap is between the timeSecurity -> If the Flights are getting to the crossing point at the same time.
                if(timeGap < timeSecurity) {
                    explode = true;
                }

            }
        }

        return explode;
    }

}
