package taxify;

public interface IMicroVehicle extends IMovable {

    public int getId();
    public ILocation getLocation();
    public void book(IUser user);
    public void startRide();
    public void endRide();
    public double calculateCost();
    public boolean isFree();
    public String toString();

}
