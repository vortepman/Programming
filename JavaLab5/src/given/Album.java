package given;

/**
 * @author Petrov Ilya
 * @version 1.0
 * Class for describing a Album - MusicBand class field
 */
public class Album {

    /** Field name */
    private String name; //Поле не может быть null, Строка не может быть пустой
    /** Field sales */
    private Long sales; //Поле может быть null, Значение поля должно быть больше 0

    /** Constructor for making a album */
    public Album(String name, Long sales) {
        this.name = name;
        this.sales = sales;
    }

    /** Default constructor */
    public Album() {}

    /**
     * Method for get name
     * @return String name
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method for get sales
     * @return Long sales
     */
    public Long getSales() {
        return sales;
    }

    public void setSales(Long sales) {
        this.sales = sales;
    }

    /** Method for printing Album-class object into string representation */
    @Override
    public String toString() {
        return "Album{" +
                "name='" + name + '\'' +
                ", sales=" + sales +
                '}';
    }

}
