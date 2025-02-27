package taxify;

public class Taxi extends Vehicle {
    private static final int RATE = 2;

    public Taxi(int id, ILocation location) {
        super(id, location);
    }

    @Override
    public int calculateCost() {
        return super.calculateCost() * RATE;
    }
}