package taxify;

/**
 * MicroVehicle class represents a micro vehicle in the taxi company system.
 * It implements the IMicroVehicle interface and provides methods to manage
 * the vehicle's status, location, and ride details.
 */
public class MicroVehicle implements IMicroVehicle {
    private int id;
    private ITaxiCompany company;
    private MicroVehicleStatus status;
    private ILocation location;
    private int distance;
    private IStatistics statistics;
    private IUser user;

    /**
     * Constructs a new MicroVehicle with the specified ID and initial location.
     * 
     * @param id The unique identifier for the vehicle
     * @param location The initial location of the vehicle
     */
    public MicroVehicle(int id, ILocation location) {
        this.id = id;
        this.status = MicroVehicleStatus.FREE;
        this.location = location;
        this.statistics = new Statistics();
    }

    /**
     * Gets the unique identifier of the vehicle.
     * 
     * @return The vehicle's ID
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Gets the current location of the vehicle.
     * 
     * @return The current location
     */
    @Override
    public ILocation getLocation() {
        return this.location;
    }

    /**
     * Books the vehicle for a specific user.
     * Changes the vehicle's status to BOOKED and associates it with the user.
     * 
     * @param user The user booking the vehicle
     */
    @Override
    public void book(IUser user) {
        this.status = MicroVehicleStatus.BOOKED;
        this.user = user;
    }

    /**
     * Starts the ride by changing the vehicle's status to IN_RIDE
     * and initializing the distance counter.
     */
    @Override
    public void startRide() {
        this.status = MicroVehicleStatus.IN_RIDE;
        this.distance = 0;
    }

    /**
     * Ends the current ride, updates statistics, and frees the vehicle.
     * Updates billing, distance, and service count in statistics.
     */
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

    /**
     * Checks if the vehicle is available for booking.
     * 
     * @return true if the vehicle's status is FREE, false otherwise
     */
    @Override
    public boolean isFree() {
        return this.status == MicroVehicleStatus.FREE;
    }

    /**
     * Moves the vehicle in a random direction on the grid.
     * Updates the vehicle's location and distance traveled.
     */
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

    /**
     * Calculates the cost of the current ride based on distance traveled.
     * 
     * @return The cost of the ride
     */
    @Override
    public double calculateCost() {
        return this.distance;
    }

    /**
     * Returns a string representation of the vehicle's current state.
     * 
     * @return A string describing the vehicle's ID, status, and location
     */
    @Override
    public String toString() {
        return this.id + ((this.status == MicroVehicleStatus.FREE) ? " is free at " + this.location
                        : ((this.status == MicroVehicleStatus.BOOKED) ? " is booked at " + this.location
                                : " in service "));
    }

}
