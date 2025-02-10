package taxify;

public interface ITaxiCompany {

    public String getName();    
    public int getTotalServices();
    public boolean provideService(int user);
    
}