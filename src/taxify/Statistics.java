package taxify;

/**
 * The Statistics class implements the IStatistics interface
 * to keep track of various service metrics including the number of services,
 * ratings, stars, distance traveled, and billing amounts.
 */
public class Statistics implements IStatistics {
    private int services;
    private int ratings;
    private int stars;
    private int distance;
    private int billing;
    
    /**
     * Constructs a Statistics object with all metrics initialized to zero.
     */
    public Statistics() {
        this.services = 0;
        this.ratings = 0;
        this.stars = 0;
        this.distance = 0;
        this.billing = 0;
    }
    
    /**
     * Returns the total number of services provided.
     *
     * @return the number of services
     */
    @Override
    public int getServices() {
        return this.services;
    }
    
    /**
     * Returns the total number of reviews received.
     *
     * @return the number of reviews
     */
    @Override
    public int getReviews() {
        return this.ratings;
    }
    
    /**
     * Returns the average star rating rounded to two decimal places.
     * If there are no ratings, returns NaN.
     *
     * @return the average star rating
     */
    @Override
    public double getStars() {
        double stars = (double) this.stars / (double) this.ratings;
        
        return Math.round(stars*100.0)/100.0;
    }
    
    /**
     * Returns the total distance traveled.
     *
     * @return the total distance
     */
    @Override
    public int getDistance() {
        return this.distance;
    }
    
    /**
     * Returns the total billing amount.
     *
     * @return the total billing amount
     */
    @Override
    public int getBilling() {
        return this.billing;
    }
    
    /**
     * Increments the number of services provided by one.
     */
    @Override
    public void updateServices() {
        this.services++;
    }
    
    /**
     * Increments the number of reviews received by one.
     */
    @Override
    public void updateReviews() {
        this.ratings++;
    }
    
    /**
     * Adds the specified number of stars to the total stars received.
     *
     * @param stars the number of stars to add
     */
    @Override
    public void updateStars(int stars) {
        this.stars = this.stars + stars;
    }
    
    /**
     * Adds the specified distance to the total distance traveled.
     *
     * @param distance the distance to add
     */
    @Override
    public void updateDistance(int distance) {
        this.distance = this.distance + distance;
    }
    
    /**
     * Adds the specified amount to the total billing.
     *
     * @param billing the amount to add
     */
    @Override
    public void updateBilling(int billing) {
        this.billing = this.billing + billing;
    }    
}