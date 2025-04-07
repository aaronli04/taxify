package taxify;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a route between two locations in a grid-based system.
 * The route is determined using a simple Manhattan distance approach,
 * moving first in the x-direction and then in the y-direction.
 */
public class Route implements IRoute {
    private List<ILocation> route;
    
    /**
     * Constructs a Route object between a given start and destination location.
     * The route is initialized by calculating the path using setRoute.
     *
     * @param location The starting location of the route.
     * @param destination The destination location of the route.
     */
    public Route(ILocation location, ILocation destination) {
        this.route = setRoute(location, destination);
    }
    
    /**
     * Checks if the route has any remaining locations to visit.
     *
     * @return true if the route contains locations, false otherwise.
     */
    @Override
    public boolean hasLocations() {
        return !this.route.isEmpty();
    }
            
    /**
     * Retrieves and removes the next location in the route.
     *
     * @return The next ILocation in the route.
     */
    @Override
    public ILocation getNextLocation() {
        ILocation location = this.route.get(0);
        
        this.route.remove(0);
        
        return location;
    }
    
    /**
     * Adds a new destination to the route dynamically.
     * The route to the new destination is calculated and appended to the existing route.
     *
     * @param newDestination The new destination to add to the route.
     */
    public void addDestination(ILocation start, ILocation newDestination)
    {
        this.route.addAll(setRoute(start, newDestination));
    }
    
    /**
     * Returns a string representation of the route, where each location
     * is represented as a string and separated by spaces.
     *
     * @return A string representation of the route.
     */
    @Override
    public String toString() {
        String route = "";
           
           for (ILocation location : this.route) {
               route = route + location.toString() + " ";
           }
       
           return route;        
    }
    
    /**
     * Generates a route between two locations using a Manhattan distance approach.
     * Moves first in the x-direction and then in the y-direction.
     *
     * @param location The starting location.
     * @param destination The destination location.
     * @return A list of ILocation objects representing the route.
     */
    private static List<ILocation> setRoute(ILocation location, ILocation destination) {
        List<ILocation> route = new ArrayList<ILocation>();
        
        int x1 = location.getX();
        int y1 = location.getY();
        
        int x2 = destination.getX();
        int y2 = destination.getY();
        
        int dx = Math.abs(x1 - x2);
        int dy = Math.abs(y1 - y2);
       
        for (int i=1; i<=dx; i++) {
            x1 = (x1 < x2) ? x1 + 1 : x1 - 1;

            route.add(new Location(x1, y1));
        }
        
        for (int i=1; i<=dy; i++) {
            y1 = (y1 < y2) ? y1 + 1 : y1 - 1;
            
            route.add(new Location(x1, y1));
        }
        
        return route;
    }       
}