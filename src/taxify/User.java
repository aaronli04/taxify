package taxify;

import java.time.LocalDate;

/**
 * Represents a user of the taxi service.
 * Implements the IUser interface.
 */
public class User implements IUser {
    private int id;
    private String firstName;
    private String lastName;
    private char gender;
    private LocalDate birthDate;
    private ITaxiCompany company;
    private boolean service;
    
    /**
     * Constructs a new User with the specified details.
     *
     * @param id        the unique identifier of the user
     * @param firstName the first name of the user
     * @param lastName  the last name of the user
     * @param gender    the gender of the user
     * @param birthDate the birth date of the user
     */
    public User(int id, String firstName, String lastName, char gender, LocalDate birthDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.service = false;
    }
    
    /**
     * Gets the unique identifier of the user.
     *
     * @return the user ID
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Gets the first name of the user.
     *
     * @return the first name
     */
    @Override
    public String getFirstName() {
        return this.firstName;
    }
    
    /**
     * Gets the last name of the user.
     *
     * @return the last name
     */
    @Override
    public String getLastName() {
        return this.lastName;
    }
    
    /**
     * Gets the gender of the user.
     *
     * @return the gender
     */
    @Override
    public char getGender() {
       return this.gender;
    }

    /**
     * Gets the birth date of the user.
     *
     * @return the birth date
     */
    @Override
    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    /**
     * Checks if the user currently has an active service.
     *
     * @return true if the user has an active service, false otherwise
     */
    @Override
    public boolean getService() {
        return this.service;
    }
    
    /**
     * Sets whether the user has an active service.
     *
     * @param service true to indicate an active service, false otherwise
     */
    @Override
    public void setService(boolean service) {
        this.service = service;
    }
    
    /**
     * Sets the taxi company associated with the user.
     *
     * @param company the taxi company
     */
    @Override
    public void setCompany(ITaxiCompany company) {
        this.company = company;
    }
    
    /**
     * Requests a taxi service from the associated taxi company.
     */
    @Override
    public void requestService(IServiceType serviceType) {
        this.company.provideService(this.id, serviceType);
    }
    
    /**
     * Rates a completed service.
     *
     * Users rate around 50% of the services randomly with a score between 1 and 5 stars.
     *
     * @param service the service to be rated
     */
    @Override
    public void rateService(IService service) {
        // users rate around 50% of the services (1 to 5 stars)
        
        if (ApplicationLibrary.rand() % 2 == 0) {
            service.setStars(ApplicationLibrary.rand(5) + 1);
        }
    }
    
    /**
     * Returns a string representation of the user, including their ID and full name.
     *
     * @return a formatted string containing the user ID and full name
     */
    @Override
    public String toString() {
        return this.getId() + " " + String.format("%-20s",this.getFirstName() + " " + this.getLastName());
    }
}