package Commands;

import given.CollectionSupervisor;
import given.DatabaseSupervisor;
import given.MusicBand;

import java.util.ArrayDeque;

/**
 * Class of command 'average_of_number_of_participants'
 * @author Petrov Ilya
 * @version 1.1
 */
public class AverageOfNumberOfParticipants extends CommandSkeleton {

    /**
     * Method for executing this command
     *
     * @param databaseSupervisor collection
     * @return - String description of command
     */
    @Override
    public String move(DatabaseSupervisor databaseSupervisor, String username){
        StringBuilder letter = new StringBuilder();
        ArrayDeque<MusicBand> oneOfMusicBands = databaseSupervisor.getMusicBands();
        double sum_of_number_of_participants = 0;
        if (!oneOfMusicBands.isEmpty()) {
            for(MusicBand musicBand : oneOfMusicBands){
                sum_of_number_of_participants += musicBand.getNumberOfParticipants();
            }
            letter.append("Average value of number of participants = ")
                    .append(sum_of_number_of_participants / oneOfMusicBands.size())
                    .append(".For " + username + " collection.");
        } else {
            letter.append("Collection is empty.");
        }
        return letter.toString();
    }
}
