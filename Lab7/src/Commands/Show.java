package Commands;

import given.CollectionSupervisor;
import given.DatabaseSupervisor;
import given.MusicBand;

import java.util.ArrayDeque;

/**
 * Class of command 'show'
 * @author Petrov Ilya
 * @version 1.1
 */
public class Show extends CommandSkeleton {

    /**
     * Method for executing this command
     *
     * @param databaseSupervisor collection
     * @return - String description of command
     */
    @Override
    public String move(DatabaseSupervisor databaseSupervisor){
        StringBuilder letter = new StringBuilder();
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
