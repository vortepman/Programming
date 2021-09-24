package Commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import given.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class of command 'update'
 * @author Petrov Ilya
 * @version 1.1
 */
public class Update extends CommandSkeleton {

    /**
     * Method for executing this command
     *
     * @param databaseSupervisor collection
     * @return String phrase for close client
     */
    @Override
    public String move(String element, DatabaseSupervisor databaseSupervisor, String username) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MusicBand newMusicBand = objectMapper.readValue(element, MusicBand.class);
            Connection c = DatabaseSupervisor.getConnection();
            PreparedStatement ps = c.prepareStatement("update music_bands set creation_date = ?, name = ?, " +
                    "x_coordinate = ?, y_coordinate = ?, number_of_participants = ?, genre = ?, best_album_name = ?," +
                    "best_album_sales = ? where (id = ?) and (user_name = ?)");
            ps.setString(1, newMusicBand.getCreationDate());
            ps.setString(2, newMusicBand.getName());
            ps.setInt(3, (int) newMusicBand.getCoordinates().getX());
            ps.setInt(4, (int) newMusicBand.getCoordinates().getY().longValue());
            ps.setInt(5, (int) newMusicBand.getNumberOfParticipants());
            ps.setString(6, newMusicBand.getGenre().toString());
            ps.setString(7, newMusicBand.getBestAlbum().getName());
            ps.setInt(8, (int) newMusicBand.getBestAlbum().getSales().longValue());
            ps.setInt(9, (int) newMusicBand.getId().longValue());
            ps.setString(10, username);

            ps.executeUpdate();

            int test = 0;
            long newId = newMusicBand.getId();
            for (MusicBand musicBand : databaseSupervisor.getMusicBands()) {
                if (newId == musicBand.getId()) {
                    test = 1;
                    databaseSupervisor.getMusicBands().clear();
                    DatabaseSupervisor.downloadElements(c, databaseSupervisor.getMusicBands(), username);
                    break;
                }
            }
            if (test == 0) {
                return "An element with this id doesn't exist.";
            } else {
                return "Element updated without problems";
            }
        } catch (NumberFormatException numberFormatException) {
            return "Id must be of long-type number. Try again.";
        } catch (JsonProcessingException jsonMappingException) {
            return "Json syntax error. Please, try again.";
        } catch (SQLException sqlException) {
            return  "An item with this id does not exist in your collection." + "\nPerhaps, an item with this id exists" +
                    " in the collection of another user, but you don't have edit rights.";
        }
    }
}
