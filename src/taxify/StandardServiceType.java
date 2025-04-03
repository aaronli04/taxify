package taxify;

public class StandardServiceType implements IServiceType {

    public boolean isUserEligible(IUser user) {
        return true;
    }
    
    public boolean isDriverEligible(IDriver driver) {
        return true;
    }
    
    public double getBaseFare() {
        return 1.0;
    }
    
}
