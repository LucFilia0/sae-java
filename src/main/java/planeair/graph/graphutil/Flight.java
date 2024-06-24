package planeair.graph.graphutil;

//#region IMPORTS

//#region PLANEAIR

import planeair.util.NTime;
import planeair.util.Airport;
import planeair.components.mapview.mapwp.flightwp.FlightWaypoint;

//#endregion

//#region GRAPHSTREAM

import org.graphstream.graph.implementations.*;

//#endregion

//#region JXMAPVIEWER

import org.jxmapviewer.viewer.GeoPosition;

//#endregion

//#region EXCEPTION

import planeair.exceptions.InvalidEntryException;
import planeair.graph.coloring.ColoringUtilities;

//#endregion

//#endregion

/**
 * Flight extends SingleNode, from GraphStream, and is a node of a
 * FlightsIntersectionGraph.
 * 
 * @implNote Uses the GraphStream attributes.
 * 
 * @author Luc le Manifik
 */
public class Flight extends SingleNode {

    // #region BLACK MAGIC -> ASK LIEGEON

    // Can only be used with the "graph.addNode()" method.
    protected Flight(AbstractGraph graph, String id) {
        super(graph, id); // -> the name of the flight is its identifier
    }

    // #endregion

    // #region STATIC VARIABLES

    /**
     * The String identifier which represents the departure Airport
     * ({@link planeair.util.Airport Airport})
     */
    public static final String DEPARTURE_AIRPORT = "departureAirport";

    /**
     * The String identifier which represents the departure Airport
     * ({@link planeair.util.Airport Airport})
     */
    public static final String ARRIVAL_AIRPORT = "arrivalAirport";

    /**
     * The String identifier which represents the departure Time
     * ({@link planeair.util.NTime NTime})
     */
    public static final String DEPARTURE_TIME = "departureTime";

    /**
     * The String identifier which represents the Flight's duration, in MINUTES
     * (int)
     */
    public static final String FLIGHT_DURATION = "flightDuration";

    /**
     * The String identifier which represents the Flight's Waypoint on the map (FlightWaypoint)
     */
    private static final String FLIGHT_WAYPOINT = "flightWaypoint" ;

    // #endregion

    // #region CONSTRUCTORS

    /**
     * Initialize the attributes of a Flight.
     * 
     * @param departureAirport The name of the {@link planeair.util.Airport Airport}
     *                         it came from
     * @param arrivalAirport   The name of the {@link planeair.util.Airport Airport}
     *                         it goes
     * @param departureTime    The hour of the departure
     * @param flightDuration   The duration of the flight, in MINUTES
     * 
     * @author Luc le Manifik
     */
    public void setFlightAttributes(Airport departureAirport, Airport arrivalAirport, NTime departureTime,
            int flightDuration) throws NullPointerException, InvalidEntryException {

        try {
            this.setDepartureAirport(departureAirport); // -> The airport where it comes
            this.setArrivalAirport(arrivalAirport); // -> The airport where it goes
            this.setDepartureTime(departureTime); // -> The time of the departure
            this.setFlightDuration(flightDuration); // -> The duration of the flight, in MINUTES
            
            this.setAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE, 0); // -> The coloring : The layer on which the Flight is placed (0 means no layer attributed)

        } catch (NullPointerException | InvalidEntryException e) {
            throw e;
        }
    }

    // #endregion

    // #region TOSTRING

    /**
     * Returns the informations of Flight in String
     * 
     * @return The informations of the Flight
     */
    public String toString() {
        return "<html><h1>Vol</h1><strong>Nom :</strong> " + 
                super.getId() +
                "<br><strong>Aéroport de départ :</strong> " +
                this.getDepartureAirport().getName() +
                "<br><strong>Aéroport d'arrivée :</strong> " +
                this.getArrivalAirport().getName() +
                "<br><strong>Heure de décollage :</strong> " +
                this.getDepartureTime() +
                "<br><strong>Durée du vol :</strong> " +
                this.getFlightDuration() +
                "<br><strong>Altitude :</strong> " +
                this.getAttribute(ColoringUtilities.NODE_COLOR_ATTRIBUTE) +
                "</html>";
    }

    // #endregion

    // #region GETTERS

    /**
     * Gets the departure Airport of the Flight.
     * 
     * @return The departure {@link planeair.util.Airport Airport} of the Flight
     */
    public Airport getDepartureAirport() {
        return (Airport) this.getAttribute(Flight.DEPARTURE_AIRPORT);
    }

    /**
     * Gets the arrival Airport of the Flight.
     * 
     * @return The arrival {@link planeair.util.Airport Airport} of the Flight
     */
    public Airport getArrivalAirport() {
        return (Airport) this.getAttribute(Flight.ARRIVAL_AIRPORT);
    }

    /**
     * Gets the departure Time of the Flight.
     * 
     * @return The {@link planeair.util.NTime Time} which represents the departure
     *         time of the Flight
     */
    public NTime getDepartureTime() {
        return (NTime) this.getAttribute(Flight.DEPARTURE_TIME);
    }

    /**
     * Gets the duration of the Flight (in MINUTES).
     * 
     * @return The flight duration, in minutes (int)
     */
    public int getFlightDuration() {
        return (int) this.getAttribute(Flight.FLIGHT_DURATION);
    }

    /**
     * Get the waypoint object linked to this flight
     * 
     * @return (FlightWaypoint)
     */
    public FlightWaypoint getFlightWaypoint() {
        return (FlightWaypoint)this.getAttribute(Flight.FLIGHT_WAYPOINT) ;
    }

    // #endregion

    // #region SETTERS

    /**
     * Sets the departure Airport of the Flight
     * 
     * @param departureAirport The new departure {@link planeair.util.Airport
     *                         Airport}
     * 
     * @throws NullPointerException Threw if the Object passed in parameter is null
     */
    public void setDepartureAirport(Airport departureAirport) throws NullPointerException {
        if (departureAirport == null) {
            throw new NullPointerException();
        }
        this.setAttribute(Flight.DEPARTURE_AIRPORT, departureAirport);
    }

    /**
     * Sets the arrival Airport of the Flight
     * 
     * @param arrivalAirport The new arrival {@link planeair.util.Airport Airport}
     * 
     * @throws NullPointerException Threw if the Object passed in paramter is null
     */
    public void setArrivalAirport(Airport arrivalAirport) throws NullPointerException {
        if (arrivalAirport == null) {
            throw new NullPointerException();
        }
        this.setAttribute(Flight.ARRIVAL_AIRPORT, arrivalAirport);
    }

    /**
     * Set the departure Time of the Flight
     * 
     * @param departureTime The new departure time ({@link planeair.util.NTime
     *                      NTime}) of the {@link planeair.graph.graphutil.Flight
     *                      Flight}
     * 
     * @throws NullPointerException Threw if the Object passed in paramter is null
     */
    public void setDepartureTime(NTime departureTime) throws NullPointerException {
        if (departureTime == null) {
            throw new NullPointerException();
        }
        this.setAttribute(Flight.DEPARTURE_TIME, departureTime);
    }

    /**
     * Set the duration flight of the {@link planeair.graph.graphutil.Flight Flight}
     * 
     * @param flightDuration The new duration flight of the Flight
     * 
     * @throws InvalidEntryException Threw if the flight duration is inferior or
     *                               equals to 0
     */
    public void setFlightDuration(int flightDuration) throws InvalidEntryException {
        if (flightDuration <= 0) {
            throw new InvalidEntryException();
        }
        this.setAttribute(Flight.FLIGHT_DURATION, flightDuration);
    }

    /**
     * Set the Flight Waypoint related to this flight
     * 
     * @param flightWaypoint
     */
    public void setFlightWaypoint(FlightWaypoint flightWaypoint) {
        this.setAttribute(Flight.FLIGHT_WAYPOINT, flightWaypoint) ;
    }

    // #endregion

    // #region PUBLIC METHODS

    /**
     * Check if the {@link planeair.graph.graphutil.Flight Flight} passed in
     * parameter is exploding with the current Flight. It does not consider the
     * different layers
     * 
     * @param tangoCharlie The {@link planeair.graph.graphutil.Flight Flight} with
     *                     wich we check the collision
     * @param timeSecurity The value below which we consider that two Flights are in
     *                     collision
     * 
     * @return Returns "true" if the two Flights are EXPLODING with each other, else
     *         "false"
     * 
     * @author Luc le Manifik
     */
    public boolean isBooming(Flight tangoCharlie, double timeSecurity) {

        /*
         * Steps :
         * Step 0 : Check if "this" is not "tangoCharlie"
         * Step 1 : Get the coordinates
         * Step 2 : Check if the routes of the two Flights are crossing
         * Step 3 : If they are crossing, then check WHEN they cross, and what is the
         * time gap between them. If it's inferior to "timeSecurity"
         * then the function returns "true".
         */

        boolean explode = false;

        double epsilon = 0.0001;

        if (!this.equals(tangoCharlie)) {

            boolean flyingTogether = !(this.getDepartureTime().getValueInMinutes() > tangoCharlie.getDepartureTime()
                    .getValueInMinutes() + tangoCharlie.getFlightDuration() ||
                    this.getDepartureTime().getValueInMinutes() + this.getFlightDuration() < tangoCharlie
                            .getDepartureTime().getValueInMinutes());

            if (flyingTogether) {

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

                depX_A = this.getDepartureAirport().getCoordinate().getLongitude();
                depY_A = this.getDepartureAirport().getCoordinate().getLatitude();

                arrX_A = this.getArrivalAirport().getCoordinate().getLongitude();
                arrY_A = this.getArrivalAirport().getCoordinate().getLatitude();

                depX_B = tangoCharlie.getDepartureAirport().getCoordinate().getLongitude();
                depY_B = tangoCharlie.getDepartureAirport().getCoordinate().getLatitude();

                arrX_B = tangoCharlie.getArrivalAirport().getCoordinate().getLongitude();
                arrY_B = tangoCharlie.getArrivalAirport().getCoordinate().getLatitude();

                /*
                 * ===== STEP 2 :
                 * 
                 * We try to find if the Flight's routes are crossing between there departure
                 * and arrival point.
                 * 
                 * //-- First route equation (Flight A : "this")
                 * 
                 * slope_A = deltaY / deltaX
                 * = (arrY_A - depY_A) / (arrX_A - depX_A)
                 * Getting originCoordinate_A :
                 * y = slope_A * x + originCoordinate_A
                 * originCoordinate_A = depY_A - (slope_A * depX_A)
                 * 
                 * => RA : y = slope_A * x + originCoordinate_A
                 * 
                 * //-- Second route equation (Flight B : "tangoCharlie")
                 * 
                 * slope_B = deltaY / deltaX
                 * = (arrY_B - depY_B) / (arrX_B - depX_B)
                 * Getting originCoordinate_B :
                 * y = slope_B * x + originCoordinate_B
                 * originCoordinate_B = depY_B - (slope_B * depX_B)
                 * 
                 * => RB : y = slope_B * x + originCoordinate_B
                 * 
                 * We are searching "x" when "y" is the same into RA and RB, so when :
                 * 
                 * slope_B * x + originCoordinate_B = slope_A * x + originCoordinate_A
                 * <=> slope_B * x - slope_A * x = originCoordinate_A - originCoordinate_B
                 * <=> x(slope_B - slope_A) = originCoordinate_A - originCoordinate_B
                 * 
                 * => x = (originCoordinate_A - originCoordinate_B) / (slope_B - slope_A)
                 * 
                 * "x" is now abscissa of the crossing point. We now need to check if "x"
                 * is between the coordinates of [depX_A; arrX_A] and [depX_B; arrX_B].
                 * If it's the case, then it means that the crossing point is on the two
                 * segments that represents the routes
                 * of the two Flights.
                 */

                // Route A (Flight A : "this")
                slope_A = (arrY_A - depY_A) / (arrX_A - depX_A); // If arrX_A == depX_A then PROBLEM. Case treated above
                originCoordinate_A = depY_A - (slope_A * depX_A);
                // because => y = slope_A * x + originCoordinate_A

                // Route B (Flight B : "tangoCharlie")
                slope_B = (arrY_B - depY_B) / (arrX_B - depX_B);
                originCoordinate_B = depY_B - (slope_B * depX_B);
                // because => y = slope_B * x + originCoordinate_B

                // Crossing coordinate
                if (slope_A != slope_B && originCoordinate_A != originCoordinate_B) { // If both slopes are the same, then the Flight's routes are parallel, and they never cross.
                    crossX = (originCoordinate_A - originCoordinate_B) / (slope_B - slope_A); // Mathematic resoltion, don't ask

                    // Check if crossX is on the route of A AND on the route of B
                    // Using epsilon, because double are shit, and equals is never reached
                    if (((depX_A - epsilon <= crossX && crossX <= arrX_A + epsilon) || (arrX_A - epsilon <= crossX && crossX <= depX_A + epsilon))
                            && ((depX_B - epsilon <= crossX && crossX <= arrX_B + epsilon) || (arrX_B - epsilon <= crossX && crossX <= depX_B + epsilon))) {

                        /*
                         * ===== STEP 3 :
                         * 
                         * Searching the time gap between A and B.
                         * 
                         * /!\ Here, regex are used : [AB] means "A" OR "B".
                         * 
                         * We consider the position of the two Flights on the X_AXIS only. We know the
                         * "x" position where the are supposed to collide, so
                         * we just check the time gap between their time at crossX.
                         * 
                         * Position equation, with a = 0 (constant speed, so null acceleration) and x0 =
                         * depX_[AB] :
                         * x(t) = 1/2*aX*t² + vX*t + x0
                         * Traduction with the variables :
                         * crossX = speedX_[AB]*t + depX_[AB]
                         * 
                         * => t = (crossX - depX_[AB]) / speedX_[AB]
                         * 
                         * Speed of the Flights (on the X_AXIS) :
                         * vX = dX/dt
                         * with : dX (distance on X_AXIS) = (arrX_[AB] - depX_[AB])
                         * dt (delta time) = [AB].getFlightDuration()
                         * So :
                         * speedX_[AB] = (arrX_[AB] - depX_[AB]) / [AB].getFlightDuration()
                         * 
                         * Then we do :
                         * t = (crossX - depX_[AB]) / speedX_[AB]
                         * It will gave the time gap between the departureTime of the Flight and the
                         * crossX coordinate.
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
                        flightDistanceX_A = arrX_A - depX_A;
                        flightDistanceX_B = arrX_B - depX_B;

                        // Flight speed
                        speedX_A = flightDistanceX_A / flightDuration_A; // v = d/dt
                        speedX_B = flightDistanceX_B / flightDuration_B; // v = d/dt
                        // Unit of the speed is something like "degree per minute"

                        // Time when the Flights get to the crossing point
                        // We are adding there departure time to the time they pass by crossX to get the
                        // time where they pass by crossX (your head is exploding right now)

                        double deltaX_A, deltaX_B;

                        deltaX_A = crossX - depX_A;
                        deltaX_B = crossX - depX_B;

                        // We add the departure Time, to get the real time/hour when the Flight will get to the crossing point.
                        crossTime_A = (deltaX_A / speedX_A) + this.getDepartureTime().getValueInMinutes(); 
                        crossTime_B = (deltaX_B / speedX_B) + tangoCharlie.getDepartureTime().getValueInMinutes();

                        // The time difference between the two crossTime
                        timeGap = (crossTime_A > crossTime_B) ? crossTime_A - crossTime_B : crossTime_B - crossTime_A;

                        // Check if the timeGap is between the timeSecurity -> If the Flights are
                        // getting to the crossing point at the same time.
                        if (timeGap < timeSecurity) {
                            explode = true;
                        }
                    }
                } else if (slope_A == slope_B && originCoordinate_A == originCoordinate_B) {
                    // We enter here if the slopes of the routes are the same AND they have the same
                    // coordinate origin
                    // So -> They are confounded
                    if (!( (depX_A < depX_B && depX_A < arrX_B && arrX_A < depX_B && arrX_A < arrX_B)
                        ||
                        (depX_A > depX_B && depX_A > arrX_B && arrX_A > depX_B && arrX_A > arrX_B) )) {
                        
                        explode = true;
                    }
                }
            }
        }

        return explode;
    }

    /**
     * This function position of the Flight at the specified NTime.
     * It considers the departure Airport's coordinates, the arrival Airport's
     * coordinates and the current time to calculate it's real position.
     * 
     * @param time The {@link planeair.util.NTime NTime} which indicates when we want to
     *          paint the Flight
     * 
     * @return currentGeoPosition ({@link org.jxmapviewer.viewer.GeoPosition}) - The
     *         current GeoPosition of the Flight if it is currently flying. Else,
     *         returns "null".
     * 
     * @author Luc le Manifik
     */
    public GeoPosition getGeoPositionAtTime(NTime time) {

        GeoPosition currentGeoPosition = null;

        // Get the current time (from milliseconds to minutes)
        // long currentTimeInMinutes = System.currentTimeMillis() / 60000;
        int currentTimeInMinutes = time.getValueInMinutes();

        int flightDepartureTime = this.getDepartureTime().getValueInMinutes();
        int flightArrivalTime = flightDepartureTime + this.getFlightDuration();

        /*
         * Precisions :
         * We need t calculate both the x_position, and the y_position of the Flight, so
         * we can put them into a GeoPosition object.
         * We first need to calculate the distance, speed and then the position on the X
         * axis, and then for the Y_AXIS.
         * 
         * 0. Verifying the Flight is currently flying
         * 1. Distance of the Flight (X & Y)
         * 2. Speed of the Flight (X & Y)
         * 3. Position (X & Y)
         */

        /*
         * 0. Verifying the Flight is currently flying
         */
        if (flightDepartureTime <= currentTimeInMinutes && currentTimeInMinutes <= flightArrivalTime) {

            // Departure Airport's coordinates
            double depX = this.getDepartureAirport().getCoordinate().getLongitude();
            double depY = this.getDepartureAirport().getCoordinate().getLatitude();

            // Arrival Airport's coordinates
            double arrX = this.getArrivalAirport().getCoordinate().getLongitude();
            double arrY = this.getArrivalAirport().getCoordinate().getLatitude();

            int flightDuration = this.getFlightDuration();

            /*
             * 1. Distance of the Flight
             */
            // double flightDistanceX = MyMath.absoluteValue(depX, arrX);
            // double flightDistanceY = MyMath.absoluteValue(depY, arrY);

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

    /**
     * Tells this flight's waypoint that its selection has to 
     * be updated if it exists
     * Also fire goofy aah word 🔥
     * 
     * @return true if the waypoint has been update, false if not
     * (so true if the waypoint exists, false if it doesn't)
     * 
     * @author Nathan LIEGEON
     */
    public void fireSelectionUpdated(boolean isSelected) {
        FlightWaypoint fwp = this.getFlightWaypoint() ;
        if (fwp != null) {
            fwp.getWaypointButton().setSelected(isSelected) ;
            fwp.getWaypointButton().changeSelection(true) ;
        }
    }

    // #endregion
}
