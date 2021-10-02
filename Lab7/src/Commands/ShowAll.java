package Commands;

import given.DatabaseSupervisor;
import given.MusicBand;

import java.util.ArrayDeque;

/**
 * Class of command 'show'
 * @author Petrov Ilya
 * @version 1.2
 */
public class ShowAll extends CommandSkeleton {

    /**
     * Method for executing this command
     *
     * @param databaseSupervisor element for working with database
     * @return - String description of command
     */
    @Override
    public String move(DatabaseSupervisor databaseSupervisor){
        StringBuilder letter = new StringBuilder();
        databaseSupervisor.getMusicBands().clear();
        DatabaseSupervisor.downloadElements(DatabaseSupervisor.getConnection(), databaseSupervisor.getMusicBands());
        ArrayDeque<MusicBand> oneOfMusicBands = databaseSupervisor.getMusicBands();
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
