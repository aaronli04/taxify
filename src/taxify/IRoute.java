package taxify;

public interface IRoute {
    public boolean hasLocations();
    public ILocation getNextLocation();
    public void addDestination(ILocation start, ILocation destination);
    public String toString();
}
