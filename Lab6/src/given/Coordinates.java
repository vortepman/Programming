package given;

/**
 * @author Petrov Ilya
 * @version 1.0
 * Class for describing field Coordinates of element
 */
public class Coordinates {

    /** field y */
    private Long y; //Поле не может быть null
    /** field x */
    private long x;

    /**
     * Constructor for making Coordinates field
     * @param x - x-coordinate
     * @param y - y-coordinate
     */
    public Coordinates(long x, Long y) {
        this.x = x;
        this.y = y;
    }

    /** Default constructor */
    public Coordinates() {}

    /** Method for printing this field into a string representation */
    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public long getX() {
        return x;
    }

    public Long getY() {
        return y;
    }
}
