package taxify;

import java.time.LocalDate;

/**
 * PinkServiceType class implements the IServiceType interface and represents a
 * specific service type in the taxi company system. It defines the eligibility
 * criteria for users and drivers, as well as the base fare for the service.
 */
public class PinkServiceType implements IServiceType {
    
    /**
     * Checks if a user is eligible for the Pink service.
     * Users are eligible if they are female or under 18 years old.
     *
     * @param user The user to check for eligibility
     * @return true if the user is female or under 18, false otherwise
     */
    @Override
    public boolean isUserEligible(IUser user) {
        return user.getGender() == 'F' || LocalDate.now().getYear() - user.getBirthDate().getYear() < 18;
    }

    /**
     * Checks if a driver is eligible to provide Pink service.
     * Only female drivers are eligible for this service type.
     *
     * @param driver The driver to check for eligibility
     * @return true if the driver is female, false otherwise
     */
    @Override
    public boolean isDriverEligible(IDriver driver) {
        return driver.getGender() == 'F';
    }

    /**
     * Gets the base fare for the Pink service.
     *
     * @return The base fare amount of 2.0
     */
    @Override
    public double getBaseFare() {
        return 2.0;
    }
}
