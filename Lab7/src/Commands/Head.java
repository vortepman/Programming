package Commands;

import given.CollectionSupervisor;
import given.DatabaseSupervisor;
import given.MusicBand;

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
    public String move(DatabaseSupervisor databaseSupervisor){
        StringBuilder letter = new StringBuilder();
        if (!databaseSupervisor.getMusicBands().isEmpty()) {
            MusicBand firstMusicBand = databaseSupervisor.getMusicBands().getFirst();
            letter.append(firstMusicBand.toString());
        } else {
            letter.append("Collection is empty");
        }
        return letter.toString();
    }
}
