package taxify;

public interface IBaseService {
    public IUser getUser();
    public ILocation getPickupLocation();
    public ILocation getDropoffLocation();
    public int calculateDistance();
    public String toString();
    public IServiceType getServiceType();
    public double getDiscountRate();
}
