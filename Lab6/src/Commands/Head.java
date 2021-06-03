package Commands;

import given.CollectionSupervisor;
import given.MusicBand;

/**
 * Class of command 'head'
 * @author Petrov Ilya
 * @version 1.1
 */
public class Head extends CommandSkeleton {

    /**
     * Method for executing this command
     *
     * @param supervisor collection
     * @return - String description of command
     */
    @Override
    public String move(CollectionSupervisor supervisor){
        StringBuilder letter = new StringBuilder();
        if (!supervisor.getMusicBands().isEmpty()) {
            MusicBand firstMusicBand = supervisor.getMusicBands().getFirst();
            letter.append(firstMusicBand.toString());
        } else {
            letter.append("Collection is empty");
        }
        return letter.toString();
    }
}
