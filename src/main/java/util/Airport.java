package util;

/**
 * Airport represents the different airports of France.
 * 
 * @author Luc le Manifik
 */
public class Airport {
    
    //-- Airport Attributes

    private String name; 
    private String location;
    private Longitude longitude;
    private Latitude latitude;

    //-- Airport Constructor

    /**
     * Constructor of the Airport class. Creates a new Airport.
     * 
     * @param name (String) - The name of the Airport.
     * @param location (String) - The location/city of the Airport.
     * @param longitude ({@link util.Longitude util.Longitude}) - The Longitude (extends {@link util.Coordinate util.Coordinate}) of the Airport.
     * @param latitude ({@link util.Latitude util.Latitude}) - The Latitude (extends {@link util.Coordinate util.Coordinate}) of the Airport.
     * 
     * @author Luc le Manifik
     */
    Airport(String name, String location, Latitude latitude, Longitude longitude) {
        this.setName(name);
        this.setLocation(location);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }

    //-- Airport toString()

    /**
     * Returns the informations of the Airport in a (String).
     * 
     * @author Luc le Manifik
     */
    public String toString() {
        return "-- Airport\nName : " + this.name + "\nLocation : " + this.location + "\nLatitude : " + this.latitude + "\nLongitude : " + this.longitude;
    }

    //-- Airport Getters

    /**
     * Get the name of the Airport.
     * @return name (String)
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the location of the Airport.
     * @return location (String)
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Get the longitude of the Airport.
     * @return longitude ({@link util.Longitude util.Longitude})
     */
    public Longitude getLongitude() {
        return this.longitude;
    }

    /**
     * Get the latitude of the Airport.
     * @return latitude ({@link util.Latitude util.Latitude})
     */
    public Latitude getLatitude() {
        return this.latitude;
    }

    //-- Airport Setters

    /**
     * Set the name of the Airport.
     * 
     * @param name (String) - The new name of the Airport.
     * @throws NullPointerException Throwed if the (String) passed  in parameter is null.
     * 
     * @author Luc le Manifik
     */
    public void setName(String name) throws NullPointerException {
        if(name == null) {
            throw new NullPointerException();
        }
        this.name = name;
    }

    /**
     * Set the location of the Airport.
     * 
     * @param location (String) - The new location of the Airport.
     * @throws NullPointerException Throwed if the (String) passed  in parameter is null.
     * 
     * @author Luc le Manifik
     */
    public void setLocation(String location) throws NullPointerException {
        if(location == null) {
            throw new NullPointerException();
        }
        this.location = location;
    }

    /**
     * Set the longitude of the Airport.
     * 
     * @param longitude ({@link util.Longitude util.Longitude}) - The new Longitude of the Airport.
     * @throws NullPointerException Throwed if the ({@link util.Longitude util.Longitude}) passed  in parameter is null.
     * 
     * @author Luc le Manifik
     */
    public void setLongitude(Longitude longitude) throws NullPointerException {
        if(longitude == null) {
            throw new NullPointerException();
        }
        this.longitude = longitude;
    }

    /**
     * Set the latitude of the Airport.
     * 
     * @param latitude ({@link util.Latitude util.Latitude}) - The new Latitude of the Airport.
     * @throws NullPointerException Throwed if the ({@link util.Latitude util.Latitude}) passed  in parameter is null.
     * 
     * @author Luc le Manifik
     */
    public void setLatitude(Latitude latitude) throws NullPointerException {
        if(latitude == null) {
            throw new NullPointerException();
        }
        this.latitude = latitude;
    }
}
