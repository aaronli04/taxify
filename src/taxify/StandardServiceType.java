package taxify;

/**
 * StandardServiceType class implements the IServiceType interface and represents a
 * standard service type in the taxi company system. It defines the eligibility
 * criteria for users and drivers, as well as the base fare for the service.
 */
public class StandardServiceType implements IServiceType {

    /**
     * Checks if a user is eligible for the Standard service.
     * All users are eligible for this service type.
     *
     * @param user The user to check for eligibility
     * @return true if the user is eligible, false otherwise
     */
    public boolean isUserEligible(IUser user) {
        return true;
    }
    
    /**
     * Checks if a driver is eligible to provide Standard service.
     * All drivers are eligible for this service type.
     *
     * @param driver The driver to check for eligibility
     * @return true if the driver is eligible, false otherwise
     */
    public boolean isDriverEligible(IDriver driver) {
        return true;
    }
    
    /**
     * Gets the base fare for the Standard service.
     *
     * @return The base fare amount of 1.0
     */
    public double getBaseFare() {
        return 1.0;
    }
    
}
