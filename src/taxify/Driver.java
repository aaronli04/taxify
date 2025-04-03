package taxify;

import java.time.LocalDate;

public class Driver implements IDriver {
    private int id;
    private String firstName;
    private String lastName;
    private char gender;
    private LocalDate birthDate;
    private IVehicle vehicle;
    private LocalDate dateJoined;

    /**
     * Constructs a new Driver with the specified details.
     *
     * @param id        the unique identifier of the driver
     * @param firstName the first name of the driver
     * @param lastName  the last name of the driver
     * @param gender    the gender of the driver
     * @param birthDate the birth date of the driver
     * @param vehicle   the vehicle associated with the driver
     */
    public Driver(int id, String firstName, String lastName, char gender, LocalDate birthDate, IVehicle vehicle) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.vehicle = vehicle;
        this.dateJoined = LocalDate.now();
    }

    /**
     * Gets the unique identifier of the driver.
     *
     * @return the driver ID
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Gets the first name of the driver.
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
     * Gets the gender of the driver.
     *
     * @return the gender
     */
    @Override
    public char getGender() {
       return this.gender;
    }

    /**
     * Gets the birth date of the driver.
     *
     * @return the birth date
     */
    @Override
    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    /**
     * Gets the experience of the driver.
     *
     * @return the experience in years
     */
    @Override
    public int getExperience() {
        return LocalDate.now().getYear() - this.dateJoined.getYear();
    }

    /**
     * Gets the rating of the driver.
     *
     * @return the rating
     */
    @Override
    public double getRating() {
        return this.vehicle.getStatistics().getStars();
    }

    /**
     * Returns a string representation of the driver, including their ID and full name.
     *
     * @return a formatted string containing the driver ID and full name
     */
    @Override
    public String toString() {
        return this.getId() + " " + String.format("%-20s",this.getFirstName() + " " + this.getLastName());
    }
}
