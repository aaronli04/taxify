package taxify;

/**
 * Represents a location in a 2D coordinate system.
 * Implements the ILocation interface to provide X and Y coordinates.
 */
public class Location implements ILocation {
    private int x;
    private int y;

    /**
     * Constructs a new Location with the specified X and Y coordinates.
     *
     * @param x the X-coordinate of the location.
     * @param y the Y-coordinate of the location.
     */
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the X-coordinate of the location.
     *
     * @return the X-coordinate.
     */
    @Override
    public int getX() {
        return this.x;
    }

    /**
     * Returns the Y-coordinate of the location.
     *
     * @return the Y-coordinate.
     */
    @Override
    public int getY() {
        return this.y;
    }

    /**
     * Returns a string representation of the location in the format (X,Y).
     *
     * @return a string representation of the location.
     */
    @Override
    public String toString() {
        return "(" + this.getX() + "," + this.getY() + ")";
    }
}