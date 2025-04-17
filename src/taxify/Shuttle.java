package taxify;

/**
 * Shuttle class represents a shuttle vehicle in the taxi company system.
 * It extends the Vehicle class and provides methods to manage the vehicle's
 * status, location, and ride details.
 */
public class Shuttle extends Vehicle {
    private static final double RATE = 1.5;

    /**
     * Constructs a new Shuttle with the specified ID, location, and driver.
     *
     * @param id The unique identifier for the shuttle
     * @param location The initial location of the shuttle
     * @param driver The driver assigned to this shuttle
     */
    public Shuttle(int id, ILocation location, IDriver driver) {
        super(id, location, driver);
    }

    /**
     * Calculates the cost of the ride using the shuttle's rate multiplier.
     *
     * @return The total cost of the ride with the shuttle rate applied
     */
    @Override
    public double calculateCost() {
        return super.calculateCost() * RATE;
    }
}