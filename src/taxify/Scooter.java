package taxify;

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

    @Override
    public boolean isFree() {
        return super.isFree() && batteryLevel > 0;
    }

    @Override
    public void endRide() {
        super.endRide();
        this.rideCount++;
        this.batteryLevel -= 25;

        if (batteryLevel <= 25 || rideCount >= 3) {
            rechargeScooter();
        }
    }

    private void rechargeScooter() {
        batteryLevel = 100;
        rideCount = 0;
    }

    @Override
    public double calculateCost() {
        return super.calculateCost() * RATE + UNLOCK_FEE;
    }
    
}
