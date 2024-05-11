package graph;

//-- Import Java

import util.FlightTime ;
import util.Airport;
import java.io.File;

//-- Import GraphStream

import org.graphstream.graph.implementations.*;

//-- Import JxMapViewer

import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;

//-- Import Exceptions

import exceptions.InvalidEntryException;


/**
 * Flight extends SingleNode, from GraphStream, and is a node of a FlightsIntersectionGraph.
 * 
 * @implNote Uses the GraphStream attributes.
 * 
 * @author Luc le Manifik
 */
public class Flight extends SingleNode implements Waypoint {
    
    // Can only be used with the "graph.addNode()" method.
    protected Flight(AbstractGraph graph, String id) {
        super(graph,id) ; // -> the name of the flight is its identifier
    }

    //-- Flight Attributes

    /**
     * The String identifier which represents the departure Airport ({@link util.Airport util.Airport})
     */
    private static final String DEPARTURE_AIRPORT = "departureAirport";

    /**
     * The String identifier which represents the departure Airport ({@link util.Airport util.Airport})
     */
    private static final String ARRIVAL_AIRPORT = "arrivalAirport";

    /**
     * The String identifier which represents the departure Time ({@link util.Time util.Time})
     */
    private static final String DEPARTURE_TIME = "departureTime";

    /**
     * The String identifier which represents the Flight's duration, in MINUTES (int)
     */
    private static final String FLIGHT_DURATION = "flightDuration";

    /**
     * The String identifier which represents the Flight's layer (int)
     */
    private static final String LAYER = "layer";

    /**
     * The file which contains the icon of the flights ({@link java.io.File})
     */
    public static final File FLIGHT_ICON_FILE = new File("sprint/plane.png");

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
     * 
     * @return (String)
     */
    public String toString() {
        return "-- Flight\nName : " + super.getId() + "\nDeparture Airport : " + this.getAttribute("departureAirport") + "\nArrival Airport : " + this.getAttribute("arrivalAirport") + "\nDeparture Time : " + this.getAttribute("departureTime") + "\nDuration Flight : " + this.getAttribute("durationFlight") + "\nLayer : " + this.getAttribute("layer");
    }

    //-- Flight Getters

    /**
     * Get the departure Airport of the Flight.
     * 
     * @return ({@link util.Airport})
     * 
     * @author Luc le Manifik
     */
    public Airport getDepartureAirport() {
        return (Airport)this.getAttribute(Flight.DEPARTURE_AIRPORT);
    }

    /**
     * Get the arrival Airport of the Flight.
     * 
     * @return ({@link util.Airport})
     * 
     * @author le Manifik
     */
    public Airport getArrivalAirport() {
        return (Airport)this.getAttribute(Flight.ARRIVAL_AIRPORT);
    }

    /**
     * Get the departure Time of the Flight.
     * 
     * @return ({@link util.FlightTime})
     * 
     * @author Luc le Manifik
     */
    public FlightTime getDepartureTime() {
        return (FlightTime)this.getAttribute(Flight.DEPARTURE_TIME);
    }

    /**
     * Get the duration of the Flight (in MINUTES).
     * 
     * @return (int)
     * 
     * @author Luc le Manifik
     */
    public int getFlightDuration() {
        return (int)this.getAttribute(Flight.FLIGHT_DURATION);
    }

    /**
     * Get the layer on which is the Flight.
     * 
     * @return (int)
     * 
     * @author Luc le Manifik
     */
    public int getLayer(){
        return (int)this.getAttribute(Flight.LAYER);
    }

    @Override
    public GeoPosition getPosition() {
        return this.getCurrentGeoPosition(); // recalculated each time we ask for the Flight location
    }

    //-- Flight Setters

    /**
     * Set the departure Airport of the Flight.
     * 
     * @param departureAirport ({@link util.Airport}) - The new departure Airport.
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
     * @param arrivalAirport ({@link util.Airport}) - The new arrival Airport.
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
     * @param departureTime ({@link util.Time}) - The new departure Time of the Flight.
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
     * @param layer (int) - The new layer of the Flight
     * 
     * @author Luc le Manifik
     */
    public void setLayer(int layer) throws InvalidEntryException {
        if(layer < 0) {
            throw new InvalidEntryException();
        }
        this.setAttribute(Flight.LAYER, layer);
    }

    //-- Flight Methods

    /**
     * This function returns the current position of the Flight.
     * It considers the departure Airport's coordinates, the arrival Airport's coordinates and the current time to calculate it's real position.
     * 
     * @return currentGeoPosition ({@link org.jxmapviewer.viewer.GeoPosition}) - The current GeoPosition of the Flight if it is currently flying. Else, returns "null".
     * 
     * @author Luc le Manifik
     */
    public GeoPosition getCurrentGeoPosition() {

        GeoPosition currentGeoPosition = null;

        // Get the current time (from milliseconds to minutes)
        long currentTimeInMinutes = System.currentTimeMillis() / 60000;
        //long currentTimeInMinutes = 8*60; // For tests (8h00)

        int flightDepartureTime = this.getDepartureTime().getHourValueInMinutes();
        int flightArrivalTime = flightDepartureTime + this.getFlightDuration();

        /*
         * Precisions :
         * We need t calculate both the x_position, and the y_position of the Flight, so we can put them into a GeoPosition object.
         * We first need to calculate the distance, speed and then the position on the X axis, and then for the Y_AXIS.
         * 
         * 0. Verifying the Flight is currently flying
         * 1. Distance of the Flight (X & Y)
         * 2. Speed of the Flight (X & Y)
         * 3. Position (X & Y)
         */

        /*
         * 0. Verifying the Flight is currently flying
         */
        if(flightDepartureTime <= currentTimeInMinutes && currentTimeInMinutes <= flightArrivalTime) {

            // Departure Airport's coordinates
            double depX = this.getDepartureAirport().getLongitude().getDecimalCoordinate();
            double depY = this.getDepartureAirport().getLatitude().getDecimalCoordinate();

            // Arrival Airport's coordinates
            double arrX = this.getArrivalAirport().getLongitude().getDecimalCoordinate();
            double arrY = this.getArrivalAirport().getLatitude().getDecimalCoordinate();

            int flightDuration = this.getFlightDuration();

            /*
             * 1. Distance of the Flight
             */
            //double flightDistanceX = MyMath.absoluteValue(depX, arrX);
            //double flightDistanceY = MyMath.absoluteValue(depY, arrY);

            double flightDistanceX = arrX - depX;
            double flightDistanceY = arrY - depY;

            /*
             * 2. Speed of the Flight
             */
            double flightSpeedX = flightDistanceX / flightDuration; // v = d / t
            double flightSpeedY = flightDistanceY / flightDuration;

            /*
             * 3. Position
             * 
             * 'flightDuration' in MINUTES and 'currentTimeInMinutes' in minutes -> OK
             * 
             * pos(t) = 1/2 * a * t^2 + v * t + x0
             * 
             * With a = 0 (because it is suppposed that the speed is constant)
             * v = 'flightSpeed[XY]'
             * t = 'durationFlight'
             * x0 = dep[XY]
             */

            double positionX = (currentTimeInMinutes - flightDepartureTime) * flightSpeedX + depX;
            double positionY = (currentTimeInMinutes - flightDepartureTime) * flightSpeedY + depY;

            // /!\ -> latitude = positionY & longitude = positionX
            currentGeoPosition = new GeoPosition(positionY, positionX);
        }

        return currentGeoPosition;
    }

    public boolean isBooming(Flight tangoCharlie, double timeSecurity) {

        boolean explode = false;
        
        double epsilon = 0.00001;

        double zeroNeg = 0 - epsilon;
        double zeroPos = 0 + epsilon;

        /*
         * Procedure :
         * 
         *      - Check if tangoCharlie and the current Flight are BOTH IN THE SKY at some moment
         *      - Check if one/both of the routes are strictly vertical
         *          \- Check if they are parallel
         *              \-YES- Check if there X are the same
         *                  \- Check if the segments are crossing
         *                      \- Calculate the collision time
         *              \-NO- Check if the segments are crossing in X
         *                  \- Calculate the collision time
         *      - Check if the two routes are parallel
         *          \- Check if the two routes are confounded
         *              \- Check if the segments are confounded
         *                   \- Calculate the collision time
         *      - Check if the two segments are crossing
         *          \- Check if the segments are crossing
         *               \- Calculate collision time
         */

        //-- Get the time informations on the two Flights
        int flightDuration_A = this.getFlightDuration();
        int flightDuration_B = tangoCharlie.getFlightDuration();

        int departureTime_A = this.getDepartureTime().getHourValueInMinutes();
        int departureTime_B = tangoCharlie.getDepartureTime().getHourValueInMinutes();

        int arrivalTime_A = departureTime_A + flightDuration_A;
        int arrivalTime_B = departureTime_B + flightDuration_B;

        //-- If they are BOTH in the sky at some moment
        if(!( arrivalTime_A < departureTime_B || arrivalTime_B < departureTime_A )) {
            //-- Get the Flights Airports coordinate

            // Current Flight's departure airport
            double depY_A = this.getDepartureAirport().getLatitude().getDecimalCoordinate();
            double depX_A = this.getDepartureAirport().getLongitude().getDecimalCoordinate();

            // Current Flight's arrival airport
            double arrY_A = this.getArrivalAirport().getLatitude().getDecimalCoordinate();
            double arrX_A = this.getArrivalAirport().getLongitude().getDecimalCoordinate();

            // Tango Charlie's departure airport
            double depY_B = tangoCharlie.getDepartureAirport().getLatitude().getDecimalCoordinate();
            double depX_B = tangoCharlie.getDepartureAirport().getLongitude().getDecimalCoordinate();

            // Tango Charlie's arrival airport
            double arrY_B = tangoCharlie.getArrivalAirport().getLatitude().getDecimalCoordinate();
            double arrX_B = tangoCharlie.getArrivalAirport().getLongitude().getDecimalCoordinate();


            // Calculate some other stuffs (flight distance, flight duration, etc...)

            // Flight's distance
            double flightDistanceX_A = arrX_A - depX_A; // Need to have a negative value if the direction is West or South
            double flightDistanceY_A = arrY_A - depY_A; // The negative value is used for the calculation of the speed

            double flightDistanceX_B = arrX_B - depX_B;
            double flightDistanceY_B = arrY_B - depY_B;

            // Flight's speed
            // v = d / t
            double speedX_A = flightDistanceX_A / flightDuration_A;
            double speedY_A = flightDistanceY_A / flightDuration_A;

            double speedX_B = flightDistanceX_B / flightDuration_B;
            double speedY_B = flightDistanceY_B / flightDuration_B;


            // Booleans store if the routes are vertical
            boolean routeIsVertical_A = (zeroNeg < arrX_A - depX_A && arrX_A - depX_A < zeroPos); // depX_A == arrX_A
            boolean routeIsVertical_B = (zeroNeg < arrX_B - depX_B && arrX_B - depX_B < zeroPos); // depX_A == arrX_A

            // Check if BOTH the routes are strictly vertical
            if(routeIsVertical_A && routeIsVertical_B) {
                // Check if the X is the same -> if the routes are confounded

                // depX_A == depX_B
                if(zeroNeg < depX_A - depX_B && depX_A - depX_B < zeroPos) {

                    if(!( (depY_A > arrY_B && depY_A > depY_B && arrY_A > arrY_B && arrY_A > depY_B) || 
                        (depY_B > arrY_A && depY_B > depY_A && arrY_B > arrY_A && arrY_B > depY_A) )) {

                        /*
                         * Calculate collision :
                         * 
                         * [AB] is regex : means "A" or "B"
                         * 
                         *      posY_[AB](t) = speedY_[AB] * (t - departureTime_[AB]) + depY_[AB]
                         * 
                         * Calculate the speed of the Flights (in degree/minutes) : 
                         * 
                         *      speedY_[AB] = flightDistanceY_[AB] / flightDuration_[AB]
                         * 
                         * So we have : 
                         * 
                         *      posY_A(t) = speedY_A * (t - departureTime_A) + depY_A
                         *      posY_B(t) = speedY_B * (t - departureTime_B) + depY_B
                         * 
                         * And we are searching for the time when they will get to the same position, so "t" when :
                         * 
                         *      posY_A(t) = posY_B(t)
                         *      speedY_A * (t - departureTime_A) + depY_A = speedY_B * (t - departureTime_B) + depY_B
                         *      
                         *      speedY_A * (t - departureTime_A) - speedY_B * (t - departureTime_B) = depY_B - depY_A
                         *      (speedY_A * t) - (speedY_A * departureTime_A) - (speedY_B * t) + (speedY_B * departureTime_B) = depY_B - depY_A
                         *      t * (speedY_A - speedY_B) = depY_B - depY_A + (speedY_A * departureTime_A) - (speedY_B * departureTime_B)
                         *      
                         *      t = ( depY_B - depY_A + (speedY_A * departureTime_A) - (speedY_B - departureTime_B) ) / (speedY_A - speedY_B)
                         * 
                         * We now need to check if the position at the time "t" is between "departureTime_A" and "arrivalTime_A" and between "departureTime_B" and "arrivalTime_B"
                         * If it's not, then it means that the collision will happen when one of the Flight already arrived or is not departed yet.
                         * 
                         * We use the timeSecurity value, of course
                         */

                        double collisionTime = ( depY_B - depY_A + (speedY_A * departureTime_A) - (speedY_B * departureTime_B) ) / (speedY_A - speedY_B);
                        explode = ( (departureTime_A - timeSecurity <= collisionTime && collisionTime <= arrivalTime_A + timeSecurity) && (departureTime_B - timeSecurity <= collisionTime && collisionTime <= arrivalTime_B + timeSecurity) );
                    }
                }
            }else {

                // Slopes of the routes
                double slope_A = (arrY_A - depY_A) / (arrX_A - depX_A);
                double slope_B = (arrY_B - depY_B) / (arrX_B - depX_B);

                // Origin coordinate

                /*
                 * We have :
                 *      route_[AB] : y = slope_[AB] * x + originCoordinate_[AB]
                 * 
                 * So :
                 *      originCoordinate_[AB] = y - (slope_[AB] * x)
                 * 
                 * With "y" and "x" which are points of the route, like dep_[AB] or arr_[AB].
                 */

                double originCoordinate_A = depY_A - (slope_A * depX_A);
                double originCoordinate_B = depY_B - (slope_B * depX_B);

                // Crossing point coordinates
                double crossX = 0, crossY = 0;

                // Crossing point time A & B
                double crossTime_A = 0, crossTime_B = 0;

                // Time Gap
                double timeGap = 0;

                if(routeIsVertical_A || routeIsVertical_B) { // Check if only the route A is vertical
                    // Check if only one is vertical
                    /*
                     * We search if the plain route's segment will cross the vertical route.
                     * We want to know where the plain route cross the X position of the vertical route.
                     * 
                     *      route_[AB] : y = slope_[AB] * x + originCoordinate_[AB]
                     * 
                     * With (from the vertical route):
                     * 
                     *      x = depX_[AB] || arrX_[AB]
                     *
                     * Then we are looking for the time gap between FlightA's passage and FlightB's passage
                     * 
                     *      pos[XY]_[AB](t) = speed[XY]_[AB] * (t - departureTime_[AB]) + dep[XY]_[AB]
                     * 
                     * So :
                     * 
                     *      t = ( (dep[XY]_[AB] - pos[XY]_[AB](t)) / ( speed[XY]_[AB] * (-1)) ) + departureTime_[AB]
                     * 
                     */

                    crossX = (routeIsVertical_A) ? depX_A : depX_B;
                    crossY = (routeIsVertical_A) ? slope_B * crossX + depX_B : slope_A * crossX + depX_A;

                    crossTime_A = ( (depY_A - crossY) / (speedY_A * -1) ) + departureTime_A;
                    crossTime_B = ( (depY_B - crossY) / (speedY_B * -1) ) + departureTime_B;

                    timeGap = (crossTime_A > crossTime_B) ? crossTime_A - crossTime_B : crossTime_B - crossTime_A;
                    if(timeGap <= timeSecurity) explode = true;
                }else {
                    // Check if none of the route is vertical (the slopes are already checked good :thumbsup:)

                    // slope_A == slope_B ?
                    if(zeroNeg < slope_A - slope_B && slope_A - slope_B < zeroPos) {

                        // originCoordinate_A == originCoordinate_B ?
                        if(zeroNeg < originCoordinate_A - originCoordinate_B && originCoordinate_A - originCoordinate_B < zeroPos) {

                            // Then the two routes are confounded
                            if(!( (depY_A > arrY_B && depY_A > depY_B && arrY_A > arrY_B && arrY_A > depY_B) || 
                            (depY_B > arrY_A && depY_B > depY_A && arrY_B > arrY_A && arrY_B > depY_A) )) {
                                // If the segments are confounded
                                /*
                                 * Calculate collision :
                                 * 
                                 * [AB] is regex : means "A" or "B"
                                 * 
                                 *      posY_[AB](t) = speedY_[AB] * (t - departureTime_[AB]) + depY_[AB]
                                 * 
                                 * Calculate the speed of the Flights (in degree/minutes) : 
                                 * 
                                 *      speedY_[AB] = flightDistanceY_[AB] / flightDuration_[AB]
                                 * 
                                 * So we have : 
                                 * 
                                 *      posY_A(t) = speedY_A * (t - departureTime_A) + depY_A
                                 *      posY_B(t) = speedY_B * (t - departureTime_B) + depY_B
                                 * 
                                 * And we are searching for the time when they will get to the same position, so "t" when :
                                 * 
                                 *      posY_A(t) = posY_B(t)
                                 *      speedY_A * (t - departureTime_A) + depY_A = speedY_B * (t - departureTime_B) + depY_B
                                 *      
                                 *      speedY_A * (t - departureTime_A) - speedY_B * (t - departureTime_B) = depY_B - depY_A
                                 *      (speedY_A * t) - (speedY_A * departureTime_A) - (speedY_B * t) + (speedY_B * departureTime_B) = depY_B - depY_A
                                 *      t * (speedY_A - speedY_B) = depY_B - depY_A + (speedY_A * departureTime_A) - (speedY_B * departureTime_B)
                                 *      
                                 *      t = ( depY_B - depY_A + (speedY_A * departureTime_A) - (speedY_B - departureTime_B) ) / (speedY_A - speedY_B)
                                 * 
                                 * We now need to check if the position at the time "t" is between "departureTime_A" and "arrivalTime_A" and between "departureTime_B" and "arrivalTime_B"
                                 * If it's not, then it means that the collision will happen when one of the Flight already arrived or is not departed yet.
                                 * 
                                 * We use the timeSecurity value, of course
                                 */
                                double collisionTime = ( depY_B - depY_A + (speedY_A * departureTime_A) - (speedY_B * departureTime_B) ) / (speedY_A - speedY_B);
                                explode = ( (departureTime_A - timeSecurity <= collisionTime && collisionTime <= arrivalTime_A + timeSecurity) && (departureTime_B - timeSecurity <= collisionTime && collisionTime <= arrivalTime_B + timeSecurity) );
                            }
                        }
                    }else {
                        // The two  routes are not parallel

                        /*
                         * We first need to know where the collision will happen.
                         * Then we verify if the collision happens on the two segments of the route, and not far away on the trajectory
                         * Finally, we calculate the crossing time of FlightA and FlightB, and verify the gap between them.
                         * 
                         * Find the collision point :
                         * 
                         *      route_A : y = slope_A * x + originCoordinate_A
                         *      route_B : y = slope_B * x + originCoordinate_B
                         * 
                         * We are searching for :
                         * 
                         *      route_A.y = route_B.y
                         *      slope_A * x + originCoordinate_A = slope_B * x + originCoordinate_B
                         *      x * slope_A - x * slope_B = originCoordinate_B - originCoordinate_A
                         *      x * (slope_A - slope_B) = originCoordinate_B - originCoordinate_A
                         *      x = (originCoordinate_B - originCoordinate_A) / (slope_A - slope_B)
                         * 
                         * So we found "crossX", which is the abscissa where FlightA encounters FlightB
                         * Now we are searching for the crossingTime : 
                         * 
                         *      posX(t) = speedX_[AB] * (t - departureTime_[AB]) + depX_[AB]
                         * 
                         * So :
                         * 
                         *      (-1) * speedX_[AB] * (t - departureTime_[AB]) = depX_[AB] - posX(t)
                         *      t - departureTime_[AB] = (depX_[AB] - posX(t)) / (-1) * speedX_[AB]
                         *      t = (depX_[AB] - posX(t)) / (-1) * speedX_[AB] + departureTime_[AB]
                         * 
                         * And then, we compare crossTime_A and crossTime_B
                         */
                        crossX = (originCoordinate_B - originCoordinate_A) / (slope_A - slope_B);

                        crossTime_A = ( (depX_A - crossX)  / (-1 * speedX_A) ) + departureTime_A;
                        crossTime_B = ( (depX_B - crossX)  / (-1 * speedX_B) ) + departureTime_B;

                        if(crossTime_A <= arrivalTime_A && crossTime_B <= arrivalTime_B) {
                            timeGap = (crossTime_A > crossTime_B) ? crossTime_A - crossTime_B : crossTime_B - crossTime_A;
                            if(timeGap <= timeSecurity) explode = true;
                        }
                    }
                }
            }
        }
        return explode;
    }
}
