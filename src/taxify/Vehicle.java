package taxify;

import java.util.ArrayList;

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

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public ILocation getLocation() {
        return this.location;
    }

    @Override
    public ILocation getDestination() {
        return this.destination;
    }

    @Override
    public void addDestination(ILocation location) {
        this.nextDestinations.add(location);
    }

    @Override
    public IBaseService getService() {
        return this.service;
    }

    @Override
    public IStatistics getStatistics() {
        return this.statistics;
    }

    @Override
    public void setCompany(ITaxiCompany company) {
        this.company = company;
    }

    @Override
    public void pickService(IBaseService service) {
        // pick a service, set destination to the service pickup location, and status to
        // "pickup"

        this.service = service;
        this.destination = service.getPickupLocation();
        this.route = new Route(this.location, this.destination);
        this.status = VehicleStatus.PICKUP;
    }

    @Override
    public void setService(IBaseService service) {
        this.service = service;
    }

    @Override
    public void startService() {
        // set destination to the service drop-off location, and status to "service"
        this.destination = this.service.getDropoffLocation();
        this.route = new Route(this.location, this.destination);
        this.status = VehicleStatus.SERVICE;
    }

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

    @Override
    public void notifyArrivalAtPickupLocation() {
        // notify the company that the vehicle is at the pickup location and start the
        // service
        if (this.company != null) {
            this.company.arrivedAtPickupLocation(this);
        }

        startService();

    }

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

    @Override
    public boolean isFree() {
        // returns true if the status of the vehicle is "free" and false otherwise
        return this.status == VehicleStatus.FREE;
    }

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


    @Override
    public double calculateCost() {
        return this.service.calculateDistance() * this.service.getServiceType().getBaseFare()
                * (1.0 - this.service.getDiscountRate());
    }

    @Override
    public String toString() {
        return this.id + " at " + this.location + " driving to " + this.destination +
                ((this.status == VehicleStatus.FREE) ? " is free with path " + this.route.toString()
                        : ((this.status == VehicleStatus.PICKUP) ? " to pickup user " + this.service.getUser().getId()
                                : " in service "));
    }

    @Override
    public IDriver getDriver() {
        return this.driver;
    }
}