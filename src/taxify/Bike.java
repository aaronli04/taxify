package taxify;

/**
 * Represents a bike in the micro-mobility service system.
 * Extends the MicroVehicle class with bike-specific pricing.
 */
public class Bike extends MicroVehicle {
    /** The rate per unit distance for bike rides */
    private static final double RATE = 0.25;
    
    /** The one-time unlock fee charged at the start of each ride */
    private static final double UNLOCK_FEE = 1.0;

    /**
     * Constructs a new Bike instance.
     * 
     * @param id       The unique identifier for the bike
     * @param location The initial location of the bike
     */
    public Bike(int id, ILocation location) {
        super(id, location);
    }

    /**
     * Calculates the total cost of a bike ride.
     * The cost includes the base distance cost multiplied by the bike rate,
     * plus the unlock fee.
     * 
     * @return The total cost of the ride in euros
     */
    @Override
    public double calculateCost() {
        return super.calculateCost() * RATE + UNLOCK_FEE;
    }
}
