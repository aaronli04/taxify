package taxify;

import java.util.Random;

/**
 * Utility class providing various helper methods for the Taxify application.
 * This includes generating random numbers, computing distances, and generating random locations.
 */
public class ApplicationLibrary {
    
    /** The minimum distance required for a valid random location generation. */
    public static final int MINIMUM_DISTANCE = 3;
    
    /** The width of the map used for random location generation. */
    private static final int MAP_WIDTH = 10;
    
    /** The height of the map used for random location generation. */
    private static final int MAP_HEIGHT = 10;    

    /**
     * Generates a random integer within the range [0, 9766].
     * 
     * @return A random integer between 0 and 9766.
     */
    public static int rand() {
        Random random = new Random();

        return random.nextInt(9767);
    }
    
    /**
     * Generates a random integer within the range [0, max - 1].
     * 
     * @param max The upper bound (exclusive) for the random number.
     * @return A random integer between 0 and max - 1.
     */
    public static int rand(int max) {
        Random random = new Random();

        return random.nextInt(9767) % max;
    }
    
    /**
     * Computes the Manhattan distance between two locations.
     * 
     * @param a The first location.
     * @param b The second location.
     * @return The Manhattan distance between the two locations.
     */
    public static int distance(ILocation a, ILocation b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
    
    /**
     * Generates a random location within the predefined map boundaries.
     * 
     * @return A randomly generated location within the map.
     */
    public static ILocation randomLocation() {
        return new Location(rand(MAP_WIDTH), rand(MAP_HEIGHT));
    }
    
    /**
     * Generates a random location that is at least MINIMUM_DISTANCE away from the given location.
     * 
     * @param location The reference location from which the distance is measured.
     * @return A randomly generated location that is at least MINIMUM_DISTANCE away from the given location.
     */
    public static ILocation randomLocation(ILocation location) {
        ILocation destination;
        
        do {

            destination = new Location(rand(MAP_WIDTH), rand(MAP_HEIGHT));
            
        } while (distance(location, destination) < MINIMUM_DISTANCE);  
            
        return destination;
    }
}