package taxify;

public class MicroVehicle implements IMicroVehicle {
    private int id;
    private ITaxiCompany company;
    private MicroVehicleStatus status;
    private ILocation location;
    private int distance;
    private IStatistics statistics;
    private IUser user;

    public MicroVehicle(int id, ILocation location) {
        this.id = id;
        this.status = MicroVehicleStatus.FREE;
        this.location = location;
        this.statistics = new Statistics();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public ILocation getLocation() {
        return this.location;
    }

    @Override
    public void book(IUser user) {
        this.status = MicroVehicleStatus.BOOKED;
        this.user = user;
    }

    @Override
    public void startRide() {
        this.status = MicroVehicleStatus.IN_RIDE;
        this.distance = 0;
    }

    @Override
    public void endRide() {
        // update vehicle statistics

        this.statistics.updateBilling(this.calculateCost());
        this.statistics.updateDistance(this.distance);
        this.statistics.updateServices();

        // set vehicle status to free and user to null

        this.status = MicroVehicleStatus.FREE;
        this.user = null;
    }

    @Override
    public boolean isFree() {
        return this.status == MicroVehicleStatus.FREE;
    }

    @Override
    public void move() {
        // move the vehicle in a random direction
        int x1 = this.location.getX();
        int y1 = this.location.getY();

        // Randomly decide whether to move in x or y direction
        boolean moveInX = Math.random() < 0.5;

        int x_move = 0;
        int y_move = 0;

        if (moveInX) {
            x_move = (Math.random() < 0.5) ? -1 : 1;
        } else {
            y_move = (Math.random() < 0.5) ? -1 : 1;
        }

        ILocation newLocation = new Location(x1 + x_move, y1 + y_move);
        this.location = newLocation;

        // update the distance traveled
        this.distance += Math.abs(x_move) + Math.abs(y_move);
    }

    @Override
    public double calculateCost() {
        return this.distance;
    }

    @Override
    public String toString() {
        return this.id + ((this.status == MicroVehicleStatus.FREE) ? " is free at " + this.location
                        : ((this.status == MicroVehicleStatus.BOOKED) ? " is booked at " + this.location
                                : " in service "));
    }

}
