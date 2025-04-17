package taxify;

import java.util.List;

/**
 * TaxiCompany class implements the ITaxiCompany interface and represents a taxi
 * company in the system. It manages users, vehicles, and services provided by
 * the company.
 */
public class TaxiCompany implements ITaxiCompany, ISubject {
    private String name;
    private List<IUser> users;
    private List<IVehicle> vehicles;
    private int totalServices;
    private IObserver observer;

    /**
     * Constructs a new TaxiCompany with the specified parameters.
     * Sets up the company by linking users and vehicles to this company.
     *
     * @param name     The name of the taxi company
     * @param users    List of users associated with the company
     * @param vehicles List of vehicles operated by the company
     */
    public TaxiCompany(String name, List<IUser> users, List<IVehicle> vehicles) {
        this.name = name;
        this.users = users;
        this.vehicles = vehicles;
        this.totalServices = 0;

        for (IUser user : this.users) {
            user.setCompany(this);
        }

        for (IVehicle vehicle : this.vehicles) {
            vehicle.setCompany(this);
        }
    }

    /**
     * Gets the name of the taxi company.
     *
     * @return The name of the taxi company as a String
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Gets the total number of active services currently being provided by the company.
     *
     * @return The total number of active services
     */
    @Override
    public int getTotalServices() {
        return this.totalServices;
    }

    /**
     * Provides a service to a user by assigning a vehicle to the service.
     * The method checks for available vehicles and assigns a random pickup and
     * drop-off location.
     *
     * @param user       The ID of the user requesting the service
     * @param serviceType The type of service requested (e.g., taxi, shared)
     * @param rideMode   The mode of the ride (e.g., standard, premium)
     * @return true if the service is successfully provided, false otherwise
     */
    @Override
    public boolean provideService(int user, IServiceType serviceType, RideMode rideMode) {
        int userIndex = findUserIndex(user);
        int vehicleIndex = findFreeVehicle(userIndex, serviceType);

        // if there is a free vehicle, assign a random pickup and drop-off location to
        // the new service
        // the distance between the pickup and the drop-off location should be at least
        // 3 blocks

        if (vehicleIndex != -1) {
            ILocation origin, destination;

            do {

                origin = ApplicationLibrary.randomLocation();
                destination = ApplicationLibrary.randomLocation(origin);

            } while (ApplicationLibrary.distance(origin,
                    this.vehicles.get(vehicleIndex).getLocation()) < ApplicationLibrary.MINIMUM_DISTANCE);

            // update the user status

            this.users.get(userIndex).setService(true);

            // create a service with the user, the pickup and the drop-off location

            IService service = new Service(this.users.get(userIndex), origin, destination, serviceType, rideMode);

            // assign the new service to the vehicle

            this.vehicles.get(vehicleIndex).pickService(service);

            notifyObserver("User " + this.users.get(userIndex).getId() + " requests a service from "
                    + service.toString() + ", the ride is assigned to " +
                    this.vehicles.get(vehicleIndex).getClass().getSimpleName() + " "
                    + this.vehicles.get(vehicleIndex).getId() + " at location " +
                    this.vehicles.get(vehicleIndex).getLocation().toString());

            // update the counter of services

            this.totalServices++;

            return true;
        }

        return false;
    }

    /**
     * Handles the event when a vehicle arrives at the pickup location.
     * Notifies the observer about the pickup event.
     *
     * @param vehicle The vehicle that has arrived at the pickup location
     */
    @Override
    public void arrivedAtPickupLocation(IVehicle vehicle) {
        // notify the observer a vehicle arrived at the pickup location

        IBaseService service = vehicle.getService();
        int user = service.getUser().getId();

        notifyObserver(
                String.format("%-8s", vehicle.getClass().getSimpleName()) + vehicle.getId() + " picks up user " + user);
    }

    /**
     * Handles the event when a vehicle arrives at the dropoff location.
     * Updates service status, handles user ratings, and manages shared services if applicable.
     *
     * @param vehicle The vehicle that has arrived at the dropoff location
     */
    @Override
    public void arrivedAtDropoffLocation(IVehicle vehicle) {
        // a vehicle arrives at the drop-off location
        IBaseService service = vehicle.getService();

        int user = service.getUser().getId();
        int userIndex = findUserIndex(user);

        if (service instanceof IService) {
    
            // the taxi company requests the user to rate the service, and updates its
            // status
    
            this.users.get(userIndex).rateService(service);
            this.users.get(userIndex).setService(false);
    
            // update the counter of services
    
            this.totalServices--;
        } else if (service instanceof ISharedService) {
            ISharedService sharedService = (ISharedService) service;

            // the taxi company requests the user to rate the service, and updates its status
            this.users.get(userIndex).rateService(service);
            this.users.get(userIndex).setService(false);

            sharedService.removeUser();
            sharedService.removeDropoffLocation();

            // no more users and drop-off locations, end the service
            if (sharedService.getUsers().size() == 0 && sharedService.getDropoffLocations().size() == 0) {
                this.totalServices--;
            }
        }

        notifyObserver(String.format("%-8s", vehicle.getClass().getSimpleName()) + vehicle.getId() + " drops off user "
                + user);
    }

    /**
     * Adds an observer to the taxi company for notification purposes.
     *
     * @param observer The observer to be added
     */
    @Override
    public void addObserver(IObserver observer) {
        this.observer = observer;
    }

    /**
     * Notifies the observer with a message about company events.
     *
     * @param message The message to be sent to the observer
     */
    @Override
    public void notifyObserver(String message) {
        this.observer.updateObserver(message);
    }

    /**
     * Finds a free vehicle that matches the service requirements for a given user.
     *
     * @param userIndex   The index of the user requesting the service
     * @param serviceType The type of service requested
     * @return The index of an available vehicle, or -1 if no suitable vehicle is found
     */
    private int findFreeVehicle(int userIndex, IServiceType serviceType) {
        for (int i = 0; i < vehicles.size(); i++) {
            IUser user = this.users.get(userIndex);
            IVehicle vehicle = vehicles.get(i);
            IDriver driver = vehicle.getDriver();

            if (vehicle.isFree() && serviceType.isDriverEligible(driver) && serviceType.isUserEligible(user)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Finds the index of a user in the users list by their ID.
     *
     * @param id The ID of the user to find
     * @return The index of the user in the list, or -1 if not found
     */
    private int findUserIndex(int id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }
}