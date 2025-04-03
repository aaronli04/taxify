package taxify;

import java.time.LocalDate;

public class PinkServiceType implements IServiceType {
    
    @Override
    public boolean isUserEligible(IUser user) {
        return user.getGender() == 'F' || LocalDate.now().getYear() - user.getBirthDate().getYear() < 18;
    }

    @Override
    public boolean isDriverEligible(IDriver driver) {
        return driver.getGender() == 'F';
    }

    @Override
    public double getBaseFare() {
        return 2.0;
    }
}
