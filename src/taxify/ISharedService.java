package taxify;

import java.util.List;

public interface ISharedService extends IBaseService {

    public List<IUser> getUsers();
    public void addUser(IUser user, ILocation dropoffLocation);
    public void removeUser();
    public List<ILocation> getDropoffLocations();
    public void addDropoffLocation(ILocation location);
    public void removeDropoffLocation();
    public List<Integer> getStars();
    public void setStars(IUser user, int stars);

}
