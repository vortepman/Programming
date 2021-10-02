package Commands;

import given.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;

/**
 * Class of command 'head'
 * @author Petrov Ilya
 * @version 1.2
 */
public class Head extends CommandSkeleton {

    /**
     * Method for executing this command
     *
     * @param databaseSupervisor element for working with database
     * @return - String description of command
     */
    @Override
    public String move(DatabaseSupervisor databaseSupervisor, String username){
        StringBuilder letter = new StringBuilder();
        ArrayDeque<MusicBand> oneOfMusicBands = new ArrayDeque<>();
        DatabaseSupervisor.downloadElementsForUser(DatabaseSupervisor.getConnection(), oneOfMusicBands, username);
        if (!oneOfMusicBands.isEmpty()) {
            MusicBand firstMusicBand = oneOfMusicBands.getFirst();
            letter.append(firstMusicBand.toString());
        } else {
            letter.append("Collection is empty");
        }
        return letter.toString();
    }
}
