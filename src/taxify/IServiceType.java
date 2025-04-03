package taxify;

public interface IServiceType {

    public boolean isUserEligible(IUser user);
    public boolean isDriverEligible(IDriver driver);
    public double getBaseFare();
    
}
