package taxify;

import java.util.List;
import java.time.LocalDate;

/**
 * ApplicationSimulator class simulates the application by managing the taxi
 * company, users, and vehicles.
 */
public class ApplicationSimulator implements IApplicationSimulator, IObserver {
    private ITaxiCompany company;
    private List<IUser> users;
    private List<IVehicle> vehicles;
    
    /**
     * Constructor for ApplicationSimulator.
     * 
     * @param company The taxi company to be managed
     * @param users List of users in the system
     * @param vehicles List of vehicles in the system
     */
    public ApplicationSimulator(ITaxiCompany company, List<IUser> users, List<IVehicle> vehicles) {
        this.company = company;
        this.users = users;
        this.vehicles = vehicles;
    }
    
    /**
     * Displays the current status of all vehicles in the system.
     */
    @Override
    public void show() {
        // show the status of the vehicles
        
        System.out.println("\n" + this.company.getName() + " status \n");

        for (IVehicle vehicle : this.vehicles) {
            System.out.println(vehicle.toString());
        }   
    }
    
    /**
     * Displays statistics for all vehicles in the system including services completed,
     * distance traveled, billing amount, reviews, and star ratings.
     */
    @Override
    public void showStatistics() {
        // show the statistics of the company
        
        String s = "\n" + this.company.getName() + " statistics \n";
        
        for (IVehicle vehicle : this.vehicles) {            
            s = s + "\n" +
            
            String.format("%-8s", vehicle.getClass().getSimpleName()) +
            String.format("%2s", vehicle.getId()) + " " +
            String.format("%2s", vehicle.getStatistics().getServices()) + " services " + 
            String.format("%3s", vehicle.getStatistics().getDistance()) + " km. " +
            String.format("%3s", vehicle.getStatistics().getBilling()) + " eur. " +
            String.format("%2s", vehicle.getStatistics().getReviews()) + " reviews " +
            String.format("%-4s", vehicle.getStatistics().getStars()) + " stars";
        }
                
        System.out.println(s);        
    }    

    /**
     * Updates the position of all vehicles by moving them to their next location.
     */
    @Override
    public void update() {
        // move vehicles to their next location
        
        for (IVehicle vehicle : this.vehicles) {
               vehicle.move();
        }
    }

    /**
     * Finds an available user and requests a service from the Taxi Company.
     * Attempts to offer shared rides when possible and handles special service types
     * like PinkService for female users and minors.
     */
    @Override
    public void requestService() {        
        // finds an available user and requests a service to the Taxi Company
        for (IUser user : users) {
            if (!user.getService()) {
                // Check if a shared ride can be offered
                if (offerSharedRide(user)) {
                    return;
                }

                RideMode rideMode = Math.random() < 0.5 ? RideMode.STANDARD : RideMode.SILENT;
                if (user.getGender() == 'F' || LocalDate.now().getYear() - user.getBirthDate().getYear() < 18) {
                    // 50% chance request PinkServiceType
                    if (Math.random() < 0.5) {
                        user.requestService(new PinkServiceType(), rideMode);
                    } else {
                        user.requestService(new StandardServiceType(), rideMode);
                    }
                } else {
                    user.requestService(new StandardServiceType(), rideMode);
                }
                return;
            }
        }
    }
    
    /**
     * Gets the total number of services completed by the company.
     * 
     * @return The total number of services
     */
    @Override
    public int getTotalServices() {
        return this.company.getTotalServices();
    }
    
    /**
     * Updates the observer with a message about system events.
     * 
     * @param message The message to be displayed
     */
    @Override
    public void updateObserver(String message) {
        System.out.println(message);
    }

    /**
     * Attempts to create a shared ride for a user with an existing service.
     * 
     * @param user The user requesting to join a shared ride
     * @return true if a shared ride was successfully created, false otherwise
     */
    private boolean offerSharedRide(IUser user) {
        for (IVehicle vehicle : vehicles) {
            if (vehicle.getService() instanceof IService && !vehicle.isFree() && vehicle instanceof Taxi && user.getService() == false) {
                IService existingService = (IService) vehicle.getService();
                ILocation userLocation = ApplicationLibrary.randomLocation();
                ILocation userDestination = ApplicationLibrary.randomLocation(userLocation);
                if (ApplicationLibrary.distance(vehicle.getLocation(), userLocation) < ApplicationLibrary.MINIMUM_DISTANCE) {
                    if (Math.random() < 0.5) { // Randomize acceptance for existing user
                        if (Math.random() < 0.5) { // Randomize acceptance for new user
                            ISharedService sharedService = new SharedService(existingService);
                            sharedService.addUser(user, userLocation);
                            sharedService.addDropoffLocation(userDestination);
                            vehicle.addDestination(userDestination);
                            vehicle.setService(sharedService);
                            updateObserver("Shared ride accepted for user " + user.getId() + " with vehicle " + vehicle.getId());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}