package Commands;

import given.CollectionSupervisor;
import given.DatabaseSupervisor;
import given.MusicBand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayDeque;

/**
 * Class of command 'remove_head'
 * @author Petrov Ilya
 * @version 1.2
 */
public class RemoveHead extends CommandSkeleton {

    /**
     * Method for executing this command
     *
     * @param databaseSupervisor element for working with database
     * @return - String description of command
     */
    @Override
    public String move(DatabaseSupervisor databaseSupervisor, String username){
        StringBuilder letter = new StringBuilder();
        ArrayDeque<MusicBand> oneOfMusicBands = databaseSupervisor.getMusicBands();
        if (!oneOfMusicBands.isEmpty()) {
            Connection c = DatabaseSupervisor.getConnection();
            try {
                PreparedStatement ps = c.prepareStatement("delete from music_bands where (id = ?) and (user_name = ?)");
                ps.setInt(1, Math.toIntExact(oneOfMusicBands.getFirst().getId()));
                ps.setString(2, username);
                ps.executeUpdate();
            } catch (SQLException sqlException) {
                return "Problems with deleting first element. Try again later.";
            }
            letter.append(oneOfMusicBands.removeFirst());
        } else {
            letter.append("Collection is empty");
        }
        return letter.toString();
    }
}
