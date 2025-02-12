package taxify;

/**
 * Represents a service request for a ride, containing information about the user, pickup location,
 * dropoff location, and rating (stars) given to the service.
 */
public class Service implements IService {
    private IUser user;
    private ILocation pickup;
    private ILocation dropoff;
    private int stars;
    
    /**
     * Constructs a new Service instance with the specified user, pickup location, and dropoff location.
     * The default star rating is set to 0.
     *
     * @param user   the user requesting the service
     * @param pickup the pickup location of the service
     * @param dropoff the dropoff location of the service
     */
    public Service(IUser user, ILocation pickup, ILocation dropoff) {
        this.user = user;
        this.pickup = pickup;
        this.dropoff = dropoff; 
        this.stars = 0;
    }
    
    /**
     * Gets the user associated with this service.
     *
     * @return the user who requested the service
     */
    @Override
    public IUser getUser() {
        return this.user;
    }
    
    /**
     * Gets the pickup location of the service.
     *
     * @return the pickup location
     */
    @Override
    public ILocation getPickupLocation() {
        return this.pickup;
    }
    
    /**
     * Gets the dropoff location of the service.
     *
     * @return the dropoff location
     */
    @Override
    public ILocation getDropoffLocation() {
        return this.dropoff;
    }
    
    /**
     * Gets the star rating given to the service.
     *
     * @return the star rating of the service
     */
    @Override
    public int getStars() {
        return this.stars;
    }
    
    /**
     * Sets the star rating for the service.
     *
     * @param stars the new star rating to be set
     */
    @Override
    public void setStars(int stars) {
        this.stars = stars;
    }
    
    /**
     * Calculates the Manhattan distance between the pickup and dropoff locations.
     * This is computed as the sum of the absolute differences between the x and y coordinates.
     *
     * @return the Manhattan distance between pickup and dropoff locations
     */
    @Override
    public int calculateDistance() {
        return Math.abs(this.pickup.getX() - this.dropoff.getX()) + Math.abs(this.pickup.getY() - this.dropoff.getY());
    }
    
    /**
     * Returns a string representation of the service, indicating the pickup and dropoff locations.
     *
     * @return a string representation of the service in the format "pickup to dropoff"
     */
    @Override
    public String toString() {
        return this.getPickupLocation().toString() + " to " + this.getDropoffLocation().toString();
    }
}