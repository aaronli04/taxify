package taxify;

import java.util.ArrayList;
import java.util.List;

public class SharedService implements ISharedService {
    private IService originalService;
    private ArrayList<IUser> users;
    private ILocation pickup;
    private ArrayList<ILocation> dropoffLocations;
    private ArrayList<Integer> stars;
    private RideMode rideMode;

    /**
     * Constructs a new SharedService instance with the specified IService.
     *
     * @param service the existing service to adapt into a shared service
     */
    public SharedService(IService service) {
        this.users = new ArrayList<>();
        this.dropoffLocations = new ArrayList<>();
        this.stars = new ArrayList<>();

        this.users.add(service.getUser());
        this.pickup = service.getPickupLocation();
        this.dropoffLocations.add(service.getDropoffLocation());
        this.stars.add(service.getStars());
        this.rideMode = service.getRideMode();

        this.originalService = service;
    }


    /**
     * Returns the original user of the shared service.
     *
     * @return the user
     */
    @Override
    public IUser getUser() {
        return this.originalService.getUser();
    }

    /**
     * Returns the list of users in the shared service.
     *
     * @return the list of users
     */
    @Override
    public List<IUser> getUsers() {
        return this.users;
    }

    /**
     * Adds a user to the shared service with the specified dropoff location.
     *
     * @param user the user to be added
     * @param dropoffLocation the dropoff location of the user
     */
    @Override
    public void addUser(IUser user, ILocation dropoffLocation) {
        this.users.add(user);
        this.dropoffLocations.add(dropoffLocation);
        this.stars.add(0);
    }

    /**
     * Removes first user from the shared service.
     *
     */
    @Override
    public void removeUser() {
        this.users.remove(0);
    }

    /**
     * Returns the pickup location of the shared service.
     *
     * @return the pickup location
     */
    @Override
    public ILocation getPickupLocation() {
        return this.originalService.getPickupLocation();
    }

    /**
     * Returns the original dropoff location of the shared service.
     *
     * @return the original dropoff location
     */
    @Override
    public ILocation getDropoffLocation() {
        return this.dropoffLocations.get(0);
    }

    /**
     * Returns the list of dropoff locations in the shared service.
     *
     * @return the list of dropoff locations
     */
    @Override
    public List<ILocation> getDropoffLocations() {
        return this.dropoffLocations;
    }

    /**
     * Adds a dropoff location to the shared service.
     *
     * @param location the dropoff location to be added
     */
    @Override
    public void addDropoffLocation(ILocation location) {
        this.dropoffLocations.add(location);
    }

    /**
     * Removes the first dropoff location from the shared service.
     */
    @Override
    public void removeDropoffLocation() {
        this.dropoffLocations.remove(0);
    }

    /**
     * Returns the list of stars for the users in the shared service.
     *
     * @return the list of stars
     */
    @Override
    public List<Integer> getStars() {
        return this.stars;
    }

    /**
     * Sets the stars for a user in the shared service.
     * 
     * @param user
     * @param stars
     */
    @Override
    public void setStars(IUser user, int stars) {
        int index = this.users.indexOf(user);
        if (index != -1) {
            this.stars.set(index, stars);
        }
    }

    /**
     * Calculates the Manhattan distance between the pickup and dropoff locations.
     * This is computed as the sum of the absolute differences between the x and y coordinates.
     *
     * @return the Manhattan distance between pickup and dropoff locations
     */
    @Override
    public int calculateDistance() {
        int totalDistance = Math.abs(this.pickup.getX() - this.originalService.getDropoffLocation().getX()) + Math.abs(this.pickup.getY() - this.originalService.getDropoffLocation().getY());
        for (int i = 0; i < this.dropoffLocations.size() - 1; i++) {
            // Calculate distance between last dropoff and the next dropoff, uses indexing
            totalDistance += Math.abs(this.dropoffLocations.get(i).getX() - this.dropoffLocations.get(i + 1).getX()) + Math.abs(this.dropoffLocations.get(i).getY() - this.dropoffLocations.get(i + 1).getY());
        }

        return totalDistance;
    }

    /**
     * Returns the service type of the shared service.
     *
     * @return the service type
     */
    @Override
    public IServiceType getServiceType() {
        return this.originalService.getServiceType();
    }

    /**
     * Returns a string representation of the shared service.
     *
     * @return a string representation of the shared service
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.pickup).append(" to ");
        for (ILocation dropoff : this.dropoffLocations) {
            if (dropoff != this.dropoffLocations.get(this.dropoffLocations.size() - 1)) {
                sb.append(dropoff).append(", ");
            } else {
                sb.append("and ").append(dropoff);
            }    
        }
        return sb.toString();
    }

    /**
     * Gets the discount rate for the service.
     *
     * @return the discount rate
     */
    @Override
    public double getDiscountRate() {
        return 0.3;
    }

}
