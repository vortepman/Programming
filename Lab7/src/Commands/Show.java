package Commands;

import given.DatabaseSupervisor;
import given.MusicBand;

import java.util.ArrayDeque;

/**
 * Class of command 'show'
 * @author Petrov Ilya
 * @version 1.2
 */
public class Show extends CommandSkeleton {

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
            for(MusicBand musicBand : oneOfMusicBands){
                letter.append(musicBand.toString()).append("\n");
            }
        } else {
            letter.append("Collection is empty.");
        }
        return letter.toString();
    }
}
