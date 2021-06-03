package Commands;

import given.*;

import java.io.IOException;
import java.util.ArrayDeque;

/**
 * Class of command 'remove_by_id'
 * @author Petrov Ilya
 * @version 1.1
 */
public class RemoveById extends CommandSkeleton {

    /**
     * Method for executing this command
     *
     * @param supervisor collection
     * @return String phrase for close client
     */
    @Override
    public String move(String element, CollectionSupervisor supervisor) {
        try {
            ArrayDeque<MusicBand> oneOfMusicBands = supervisor.getMusicBands();
            String strId = element.trim();
            long id;
            if (strId.indexOf(" ") > 0) {
                id = Long.parseLong(strId.substring(0, strId.indexOf(" ")));
            } else {
                id = Long.parseLong(strId);
            }
            if (!oneOfMusicBands.isEmpty()) {
                for (MusicBand musicBand : oneOfMusicBands) {
                    if (id == musicBand.getId()) {
                        oneOfMusicBands.remove(musicBand);
                        supervisor.save();
                        return "Element deleted without problems.";
                    } else {
                        return "An element with this id doesn't exist. {" + id + "}";
                    }
                }
            } else {
                return "The collection is empty.";
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException n) {
            return "Id value must be a long-type number. Try again.";
        }
        return "I don't know why it came out";
    }
}
