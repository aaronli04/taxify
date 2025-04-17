package taxify;

/**
 * Scooter class represents a scooter in the taxi company system.
 * It extends the MicroVehicle class and implements specific behavior for scooters.
 */
public class Scooter extends MicroVehicle {
    private static final double RATE = 0.15;
    private static final double UNLOCK_FEE = 1.0;
    private int batteryLevel;
    private int rideCount;

    public Scooter(int id, ILocation location) {
        super(id, location);
        this.batteryLevel = 100;
        this.rideCount = 0;
    }

    /**
     * Checks if the scooter is available for use.
     * A scooter is considered free if it's not in use and has battery power.
     *
     * @return true if the scooter is available and has battery, false otherwise
     */
    @Override
    public boolean isFree() {
        return super.isFree() && batteryLevel > 0;
    }

    /**
     * Ends the current ride, updates the ride count and battery level.
     * Automatically recharges the scooter if battery is low or after three rides.
     */
    @Override
    public void endRide() {
        super.endRide();
        this.rideCount++;
        this.batteryLevel -= 25;

        if (batteryLevel <= 25 || rideCount >= 3) {
            rechargeScooter();
        }
    }

    /**
     * Recharges the scooter by resetting battery level to 100% and ride count to 0.
     */
    private void rechargeScooter() {
        batteryLevel = 100;
        rideCount = 0;
    }

    /**
     * Calculates the total cost of the ride including the unlock fee.
     * 
     * @return The total cost of the ride based on distance, rate and unlock fee
     */
    @Override
    public double calculateCost() {
        return super.calculateCost() * RATE + UNLOCK_FEE;
    }
    
}
