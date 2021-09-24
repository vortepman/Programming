package given;

import java.time.*;

/**
 * @author Petrov Ilya
 * @version 1.0
 * Class for describing a music band - element of collection
 */
public class MusicBand {
    /** Field ID */
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0,
    // Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    /** Field name */
    private String name; //Поле не может быть null, Строка не может быть пустой
    /** Field coordinates */
    private Coordinates coordinates; //Поле не может быть null
    /** Field creation date */
    private String creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    /** Field number of participants */
    private long numberOfParticipants; //Значение поля должно быть больше 0
    /** Field genre */
    private MusicGenre genre; //Поле не может быть null
    /** Field best album */
    private Album bestAlbum; //Поле не может быть null\

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String CREATION_DATE = "creationDate";
    private static final String COORDINATES = "coordinates";
    private static final String XCOORDINATE = "x";
    private static final String YCOORDINATE = "y";
    private static final String NUMBER_OF_PARTICIPANTS = "numberOfParticipants";
    private static final String GENRE = "genre";
    private static final String BEST_ALBUM = "bestAlbum";
    private static final String BEST_ALBUM_NAME = "name";
    private static final String BEST_ALBUM_SALES = "sales";

    /** Constructor for making a music band */
    public MusicBand(Long id, String name, String creationDate, Coordinates coordinates, long numberOfParticipants,
                     MusicGenre genre, Album bestAlbum) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.numberOfParticipants = numberOfParticipants;
        this.genre = genre;
        this.bestAlbum = bestAlbum;
    }

    /** Default constructor */
    public MusicBand() {}

    /** Method for printing MusicBand-class object into string representation */
    @Override
    public String toString() {
        return "MusicBand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", numberOfParticipants=" + numberOfParticipants +
                ", genre=" + genre +
                ", bestAlbum=" + bestAlbum +
                '}';
    }

    /**
     * Method for get ID
     * @return Long id
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
     * Method for get coordinates
     * @return Coordinates coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Method for get number of participants
     * @return long numberOfParticipants
     */
    public long getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(long numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    /**
     * Method for get genre
     * @return MusicGenre musicGenre
     */
    public MusicGenre getGenre() {
        return genre;
    }

    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }

    /**
     * Method for get best Album
     * @return Album album
     */
    public Album getBestAlbum() {
        return bestAlbum;
    }

    public void setBestAlbum(Album bestAlbum) {
        this.bestAlbum = bestAlbum;
    }

    /**
     * Method for get current date into string representation
     * @return String date
     */
    public String getCreationDate() {
        creationDate = LocalDateTime.now().toString();
        return creationDate;
    }

    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"" + ID + "\"").append(':').append(this.getId()).append(",");
        json.append("\"" + NAME + "\"").append(':').append("\"").append(this.getName()).append("\"").append(",");
        json.append("\"" + CREATION_DATE + "\"").append(':').append("\"").append(this.getCreationDate()).append("\"")
                .append(",");
        json.append("\"" + COORDINATES + "\"").append(':').append("{").append("\"").append(XCOORDINATE)
                .append("\"").append(':').append(this.getCoordinates().getX()).append(",").append("\"")
                .append(YCOORDINATE).append("\"").append(':').append(this.getCoordinates().getY()).append("}")
                .append(",");
        json.append("\"" + NUMBER_OF_PARTICIPANTS + "\"").append(':').append(this.getNumberOfParticipants())
                .append(",");
        json.append("\"" + GENRE + "\"").append(':').append("\"").append(this.getGenre()).append("\"").append(",");
        json.append("\"" + BEST_ALBUM + "\"").append(':').append("{").append("\"").append(BEST_ALBUM_NAME).append("\"")
                .append(':').append("\"").append(this.getBestAlbum().getName()).append("\"").append(",").append("\"")
                .append(BEST_ALBUM_SALES).append("\"").append(':').append(this.getBestAlbum().getSales()).append("}");
        json.append("}");
        return json.toString();
    }

}