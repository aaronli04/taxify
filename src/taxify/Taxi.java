package taxify;

public class Taxi extends Vehicle {
    private static final double RATE = 2;

    public Taxi(int id, ILocation location, IDriver driver) {
        super(id, location, driver);
    }

    @Override
    public double calculateCost() {
        return super.calculateCost() * RATE;
    }
}