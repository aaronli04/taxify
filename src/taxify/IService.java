package taxify;

public interface IService extends IBaseService {
    public ILocation getDropoffLocation();
    public int getStars();
    public void setStars(int stars);
    public IServiceType getServiceType();
    public RideMode getRideMode();
}
