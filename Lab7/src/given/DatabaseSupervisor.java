package given;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.time.LocalDateTime;
import java.sql.*;

/**
 * @author Petrov Ilya
 * @version 1.0
 * Class for working with database
 */
public class DatabaseSupervisor {
    /**
     * ArrayDeque for keeping a collection as java-object
     */
    private final ArrayDeque<given.MusicBand> musicBands = new ArrayDeque<>();
    /**
     * HashMap collection for making a manual
     */
    private final HashMap<String, String> informationAboutCommands;
    /** Set for storing paths to files for command 'execute_script' */
    private static Set<String> paths = new HashSet<>();
    /**
     * Field for saving date of initialization the collection
     */
    private LocalDateTime initializationDate;
    private static final String url = "jdbc:postgresql://hi:********/buddy";
    private static final String user = "********";
    private static final String pass =  "********";
    private static final String schema =  "********";
    private static Connection connection;

    // Constructor for establishing a connection to the database and fixing the start time
    public DatabaseSupervisor() {
        Connection c = DatabaseSupervisor.getConnection();
        initializationDate = LocalDateTime.now();
        downloadElements(DatabaseSupervisor.getConnection(), getMusicBands());
    }

    /**
     * Method for downloading elements to the all users collection from database
     */
    public static void downloadElements(Connection c, ArrayDeque<MusicBand> musicBands) {
        int downloads = 0;
        try {
            PreparedStatement ps = c.prepareStatement("select * from music_bands");
            //ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Coordinates coordinates = new Coordinates(
                        rs.getInt("x_coordinate"),
                        (long) rs.getInt("y_coordinate")
                );
                Album album = new Album(
                        rs.getString("best_album_name"),
                        (long) rs.getInt("best_album_sales")
                );
                MusicGenre musicGenre;
                if (rs.getString("genre").equals("ROCK")) {
                    musicGenre = MusicGenre.ROCK;
                } else if (rs.getString("genre").equals("JAZZ")) {
                    musicGenre = MusicGenre.JAZZ;
                } else {
                    musicGenre = MusicGenre.BLUES;
                }
                MusicBand musicBand = new MusicBand(
                        (long) rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("creation_date"),
                        coordinates,
                        rs.getInt("number_of_participants"),
                        //MusicGenre.valueOf(rs.getString("genre")),
                        //MusicGenre.ROCK,
                        musicGenre,
                        album
                );
                musicBands.add(musicBand);
                downloads += 1;
            }
            System.out.println("Number of successfully loaded element: " + downloads);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Method for downloading elements of one user from database
     */
    public static void downloadElementsForUser(Connection c, ArrayDeque<MusicBand> musicBands, String username) {
        try {
            PreparedStatement ps = c.prepareStatement("select * from music_bands where user_name = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Coordinates coordinates = new Coordinates(
                        rs.getInt("x_coordinate"),
                        (long) rs.getInt("y_coordinate")
                );
                Album album = new Album(
                        rs.getString("best_album_name"),
                        (long) rs.getInt("best_album_sales")
                );
                MusicGenre musicGenre;
                if (rs.getString("genre").equals("ROCK")) {
                    musicGenre = MusicGenre.ROCK;
                } else if (rs.getString("genre").equals("JAZZ")) {
                    musicGenre = MusicGenre.JAZZ;
                } else {
                    musicGenre = MusicGenre.BLUES;
                }
                MusicBand musicBand = new MusicBand(
                        (long) rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("creation_date"),
                        coordinates,
                        rs.getInt("number_of_participants"),
                        //MusicGenre.valueOf(rs.getString("genre")),
                        //MusicGenre.ROCK,
                        musicGenre,
                        album
                );
                musicBands.add(musicBand);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }



    /**
     * Method for getting the connection with database
     */
    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        if (connection == null) {
            String connUrl = url + "?user=" + user + "&password=" + pass + "&ssl=false" + "&currentSchema=" + schema;
            while(true) {
                try {
                    connection = DriverManager.getConnection(connUrl);
                    System.out.println("Database connection successful.");
                    break;
                } catch (SQLException sqlException) {
                    System.out.println("Database is not available. Reconnecting...");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException interruptedException) {
                        System.out.println("Connection problems, please, wait...");
                    }
                }
            }
        }
        return connection;
    }

    /**
     * Method for closing the connection with database
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }

            connection = null;
        }
    }

    {

        // Making a manual
        informationAboutCommands = new HashMap<>();
        informationAboutCommands.put("[help]", " - display help for available commands");
        informationAboutCommands.put("[info]", " - display information about the server app and the database collection" +
                " (type, date of server initialization, number of elements, etc.) to the standard output stream");
        informationAboutCommands.put("[show]", " - print to standard output all elements from database of your collection in string representation");
        informationAboutCommands.put("[show_all]", " - print to standard output all elements from database in string representation");
        informationAboutCommands.put("[back]", " - this command returns you to the registration field.");
        informationAboutCommands.put("[add {element}]", " - add new element to the database");
        informationAboutCommands.put("[update id {element}]", " - update the value of the collection element" +
                " whose id is equal to the given. After entering the command," +
                " you must enter the ID separated by a space. For example: update 1");
        informationAboutCommands.put("[remove id {element}]", " - remove an element from the collection by its ID." +
                " After entering the command, you must enter the ID separated by a space. For example: remove_by_id 1");
        informationAboutCommands.put("[clear]", " - clear the your collection");
        informationAboutCommands.put("[execute_script file_name]", " - read and execute a script from specified file." +
                " You should to enter path to file after entering a command.");
        informationAboutCommands.put("[exit]", " - end the program");
        informationAboutCommands.put("[head]", " - display the first element of the your collection");
        informationAboutCommands.put("[remove_head]", " - display the first element of the your collection and remove it");
        informationAboutCommands.put("[average_of_number_of_participants]", " - display the average value of the field" +
                " numberOfParticipants for all elements of the database");
        informationAboutCommands.put("[count_greater_than_number_of_participants numberOfParticipants]", " - display the number of" +
                " elements, the value of the field numberOfParticipants of which is greater than the specified");
    }

    public ArrayDeque<MusicBand> getMusicBands() {
        return musicBands;
    }

    public HashMap<String, String> getInformationAboutCommands() {
        return informationAboutCommands;
    }

    public static Set<String> getPaths() {
        return paths;
    }

    public static void setPaths(Set<String> paths) {
        DatabaseSupervisor.paths = paths;
    }

    public LocalDateTime getInitializationDate() {
        return initializationDate;
    }

}
