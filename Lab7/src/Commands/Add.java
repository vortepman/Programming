package Commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import given.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Class of command 'add'
 * @author Petrov Ilya
 * @version 1.2
 */
public class Add extends CommandSkeleton {

    /**
     * Method for executing this command
     *
     * @param databaseSupervisor element for working with database
     * @return String phrase for close client
     */
    @Override
    public String move(String element, DatabaseSupervisor databaseSupervisor, String username) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MusicBand musicBand = objectMapper.readValue(element, MusicBand.class);
            Connection c = DatabaseSupervisor.getConnection();
            PreparedStatement ps = c.prepareStatement("insert into music_bands (creation_date, name, " +
                    "x_coordinate, y_coordinate, number_of_participants, genre, best_album_name, " +
                    "best_album_sales, user_name) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            //ps.setInt(1, (int) musicBand.getId().longValue());
            ps.setString(1, musicBand.getCreationDate());
            ps.setString(2, musicBand.getName());
            ps.setInt(3, (int) musicBand.getCoordinates().getX());
            ps.setInt(4, (int) musicBand.getCoordinates().getY().longValue());
            ps.setInt(5, (int) musicBand.getNumberOfParticipants());
            ps.setString(6, musicBand.getGenre().toString());
            ps.setString(7, musicBand.getBestAlbum().getName());
            ps.setInt(8, (int) musicBand.getBestAlbum().getSales().longValue());
            ps.setString(9, username);

            ps.executeUpdate();
            databaseSupervisor.getMusicBands().clear();
            DatabaseSupervisor.downloadElements(c, databaseSupervisor.getMusicBands());

            return "Element added to database successfully.";
        } catch (Throwable throwable) {
            return "Oh shit, I don't know how it happened)";
        }
    }
}