package taxify;

public class Shuttle extends Vehicle {
    private static final double RATE = 1.5;

    public Shuttle(int id, ILocation location) {
        super(id, location);
    }

    @Override
    public double calculateCost() {
        return super.calculateCost() * RATE;
    }
}