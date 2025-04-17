package taxify;

/**
 * Taxi class represents a taxi vehicle in the taxi company system.
 * It extends the Vehicle class and provides methods to manage the vehicle's
 * status, location, and ride details.
 */
public class Taxi extends Vehicle {
    /**
     * The rate multiplier for taxi rides.
     * This value is used to calculate the total cost of the ride.
     */
    private static final double RATE = 2;

    /**
     * Constructs a new Taxi with the specified ID, location, and driver.
     *
     * @param id The unique identifier for the taxi
     * @param location The initial location of the taxi
     * @param driver The driver assigned to this taxi
     */
    public Taxi(int id, ILocation location, IDriver driver) {
        super(id, location, driver);
    }

    /**
     * Calculates the cost of the ride using the taxi's rate multiplier.
     *
     * @return The total cost of the ride with the taxi rate applied
     */
    @Override
    public double calculateCost() {
        return super.calculateCost() * RATE;
    }
}