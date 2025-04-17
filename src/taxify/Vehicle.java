package taxify;

import java.util.ArrayList;

/**
 * An abstract class representing a vehicle in the taxi service system.
 * Implements the IVehicle interface and provides base functionality for different types of vehicles.
 */
public abstract class Vehicle implements IVehicle {
    private int id;
    private ITaxiCompany company;
    private IBaseService service;
    private VehicleStatus status;
    private ILocation location;
    private ILocation destination;
    private ArrayList<ILocation> nextDestinations = new ArrayList<>();
    private IStatistics statistics;
    private IRoute route;
    private IDriver driver;

    /**
     * Constructs a new Vehicle with specified parameters.
     * 
     * @param id       The unique identifier for the vehicle
     * @param location The initial location of the vehicle
     * @param driver   The driver assigned to this vehicle
     */
    public Vehicle(int id, ILocation location, IDriver driver) {
        this.id = id;
        this.service = null;
        this.status = VehicleStatus.FREE;
        this.location = location;
        this.destination = ApplicationLibrary.randomLocation(this.location);
        this.statistics = new Statistics();
        this.route = new Route(this.location, this.destination);
        this.driver = driver;
    }

    /**
     * @return The unique identifier of the vehicle
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * @return The current location of the vehicle
     */
    @Override
    public ILocation getLocation() {
        return this.location;
    }

    /**
     * @return The current destination of the vehicle
     */
    @Override
    public ILocation getDestination() {
        return this.destination;
    }

    /**
     * Adds a new destination to the vehicle's queue of destinations.
     * 
     * @param location The location to add as a destination
     */
    @Override
    public void addDestination(ILocation location) {
        this.nextDestinations.add(location);
    }

    /**
     * @return The current service being performed by the vehicle
     */
    @Override
    public IBaseService getService() {
        return this.service;
    }

    /**
     * @return The statistics for this vehicle
     */
    @Override
    public IStatistics getStatistics() {
        return this.statistics;
    }

    /**
     * Sets the taxi company that this vehicle belongs to.
     * 
     * @param company The taxi company to assign
     */
    @Override
    public void setCompany(ITaxiCompany company) {
        this.company = company;
    }

    /**
     * Assigns a service to this vehicle and prepares for pickup.
     * 
     * @param service The service to be picked up
     */
    @Override
    public void pickService(IBaseService service) {
        // pick a service, set destination to the service pickup location, and status to
        // "pickup"

        this.service = service;
        this.destination = service.getPickupLocation();
        this.route = new Route(this.location, this.destination);
        this.status = VehicleStatus.PICKUP;
    }

    /**
     * Sets the current service for this vehicle.
     * 
     * @param service The service to be set
     */
    @Override
    public void setService(IBaseService service) {
        this.service = service;
    }

    /**
     * Starts the current service after picking up the passenger(s).
     */
    @Override
    public void startService() {
        // set destination to the service drop-off location, and status to "service"
        this.destination = this.service.getDropoffLocation();
        this.route = new Route(this.location, this.destination);
        this.status = VehicleStatus.SERVICE;
    }

    /**
     * Ends the current service and updates vehicle statistics.
     */
    @Override
    public void endService() {
        // update vehicle statistics

        this.statistics.updateBilling(this.calculateCost());
        this.statistics.updateDistance(this.service.calculateDistance());
        this.statistics.updateServices();

        // if the service is rated by the user, update statistics
        if (this.service instanceof IService) {
            IService service = (IService) this.service;
            if (service.getStars() != 0) {
                this.statistics.updateStars(service.getStars());
                this.statistics.updateReviews();
            }
        } else if (this.service instanceof ISharedService) {
            ISharedService service = (ISharedService) this.service;
            for (int stars : service.getStars()) {
                this.statistics.updateStars(stars);
                this.statistics.updateReviews();
            }
        }

        // set service to null, and status to "free"

        this.service = null;
        this.destination = ApplicationLibrary.randomLocation(this.location);
        this.route = new Route(this.location, this.destination);
        this.status = VehicleStatus.FREE;
    }

    /**
     * Notifies the company when the vehicle arrives at the pickup location.
     */
    @Override
    public void notifyArrivalAtPickupLocation() {
        // notify the company that the vehicle is at the pickup location and start the
        // service
        if (this.company != null) {
            this.company.arrivedAtPickupLocation(this);
        }

        startService();

    }

    /**
     * Notifies the company when the vehicle arrives at the dropoff location.
     * For shared services, handles multiple dropoff locations.
     */
    @Override
    public void notifyArrivalAtDropoffLocation() {
        // notify the company that the vehicle is at the drop off location and end the
        // service
        if (this.company != null) {
            this.company.arrivedAtDropoffLocation(this);
        }

        if (this.service instanceof ISharedService) {
            if (this.nextDestinations.size() > 0) {
                this.destination = this.nextDestinations.get(0);
                this.route.addDestination(this.location, destination);
                this.nextDestinations.remove(0);
                
                ISharedService sharedService = (ISharedService) this.service;
                sharedService.removeDropoffLocation();
            } else {
                endService();
            }
        } else {
            endService();
        }

    }

    /**
     * Checks if the vehicle is available for new services.
     * 
     * @return true if the vehicle is free, false otherwise
     */
    @Override
    public boolean isFree() {
        // returns true if the status of the vehicle is "free" and false otherwise
        return this.status == VehicleStatus.FREE;
    }

    /**
     * Moves the vehicle to the next location along its route.
     * Handles arrival at pickup and dropoff locations.
     */
    @Override
    public void move() {
        this.location = this.route.getNextLocation();

        // if the route has more locations the vehicle continues its route, otherwise
        // the vehicle has arrived to a pickup or drop off location

        if (!this.route.hasLocations()) {
            if (this.service == null) {
                // the vehicle continues its random route

                this.destination = ApplicationLibrary.randomLocation(this.location);
                this.route = new Route(this.location, this.destination);
            } else {
                // check if the vehicle has arrived to a pickup or drop off location

                ILocation origin = this.service.getPickupLocation();
                ILocation destination = this.service.getDropoffLocation();

                if (this.location.getX() == origin.getX() && this.location.getY() == origin.getY()) {

                    notifyArrivalAtPickupLocation();

                } else if (this.location.getX() == destination.getX()
                        && this.location.getY() == destination.getY()) {

                    notifyArrivalAtDropoffLocation();

                }
            }
        }
    }

    /**
     * Calculates the cost of the current service.
     * 
     * @return The calculated cost based on distance, base fare, and discount rate
     */
    @Override
    public double calculateCost() {
        return this.service.calculateDistance() * this.service.getServiceType().getBaseFare()
                * (1.0 - this.service.getDiscountRate());
    }

    /**
     * @return A string representation of the vehicle's current state
     */
    @Override
    public String toString() {
        return this.id + " at " + this.location + " driving to " + this.destination +
                ((this.status == VehicleStatus.FREE) ? " is free with path " + this.route.toString()
                        : ((this.status == VehicleStatus.PICKUP) ? " to pickup user " + this.service.getUser().getId()
                                : " in service "));
    }

    /**
     * @return The driver assigned to this vehicle
     */
    @Override
    public IDriver getDriver() {
        return this.driver;
    }
}