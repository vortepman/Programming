package Commands;

import given.CollectionSupervisor;
import given.DatabaseSupervisor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Class of command 'clear'
 * @author Petrov Ilya
 * @version 1.2
 */
public class Clear extends CommandSkeleton {

    /**
     * Method for executing this command
     *
     * @param databaseSupervisor element for working with database
     * @return String description of command
     */
    @Override
    public String move(DatabaseSupervisor databaseSupervisor, String username){
        Connection c = DatabaseSupervisor.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement("delete from music_bands where user_name = ?");
            ps.setString(1, username);
            ps.executeUpdate();
            databaseSupervisor.getMusicBands().clear();
            return "Database successfully cleared.";
        } catch (SQLException sqlException) {
            return "Oh shit, I don't know how it happened)";
        }
    }
}
