package taxify;

public class Statistics implements IStatistics {
    private int services;
    private int ratings;
    private int stars;
    private int distance;
    private int billing;
    
    public Statistics() {
        this.services = 0;
        this.ratings = 0;
        this.stars = 0;
        this.distance = 0;
        this.billing = 0;
    }
    
    @Override
    public int getServices() {
        return this.services;
    }
    
    @Override
    public int getReviews() {
        return this.ratings;
    }
    
    @Override
    public double getStars() {
        double stars = (double) this.stars / (double) this.ratings;
        
        return Math.round(stars*100.0)/100.0;
    }
    
    @Override
    public int getDistance() {
        return this.distance;
    }
    
    @Override
    public int getBilling() {
        return this.billing;
    }
    
    @Override
    public void updateServices() {
        this.services++;
    }
    
    @Override
    public void updateReviews() {
        this.ratings++;
    }
    
    @Override
    public void updateStars(int stars) {
        this.stars = this.stars + stars;
    }
    
    @Override
    public void updateDistance(int distance) {
        this.distance = this.distance + distance;
    }
    
    @Override
    public void updateBilling(int billing) {
        this.billing = this.billing + billing;
    }    
}