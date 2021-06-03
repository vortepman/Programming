package Commands;

import given.CollectionSupervisor;
import given.MusicBand;

import java.util.ArrayDeque;

/**
 * Class of command 'remove_head'
 * @author Petrov Ilya
 * @version 1.1
 */
public class RemoveHead extends CommandSkeleton {

    /**
     * Method for executing this command
     *
     * @param supervisor collection
     * @return - String description of command
     */
    @Override
    public String move(CollectionSupervisor supervisor){
        StringBuilder letter = new StringBuilder();
        ArrayDeque<MusicBand> oneOfMusicBands = supervisor.getMusicBands();
        if (!oneOfMusicBands.isEmpty()) {
            letter.append(oneOfMusicBands.removeFirst());
        } else {
            letter.append("Collection is empty");
        }
        return letter.toString();
    }
}
