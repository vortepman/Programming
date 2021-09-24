package Commands;

import given.*;

import java.io.IOException;
import java.util.ArrayDeque;

/**
 * Class of command 'count_greater_than_number_of_participants'
 * @author Petrov Ilya
 * @version 1.1
 */
public class CountGreaterThanNumberOfParticipants extends CommandSkeleton {

    /**
     * Method for executing this command
     *
     * @param databaseSupervisor collection
     * @return String phrase for close client
     */
    @Override
    public String move(String element, DatabaseSupervisor databaseSupervisor, String username) {
        try {
            ArrayDeque<MusicBand> oneOfMusicBands = databaseSupervisor.getMusicBands();
            String strNumberOfParticipants = element.trim();
            StringBuilder letter = new StringBuilder();
            long numberOfParticipants;
            int bandCounter = 0;
            if (strNumberOfParticipants.indexOf(" ") > 0) {
                numberOfParticipants = Long.parseLong(strNumberOfParticipants
                        .substring(0, strNumberOfParticipants.indexOf(" ")));
            } else {
                numberOfParticipants = Long.parseLong(strNumberOfParticipants);
            }
            if (!oneOfMusicBands.isEmpty()) {
                for (MusicBand musicBand : oneOfMusicBands) {
                    if (numberOfParticipants < musicBand.getNumberOfParticipants()) {
                        bandCounter += 1;
                    }
                }
            } else {
                return "The collection is empty.";
            }
            letter.append("The number of groups whose numberOfParticipants field value is greater than" +
                    " the received value = ").append(bandCounter).append(".For ").append(username).append(" collection.");
            return letter.toString();
        } catch (NumberFormatException | StringIndexOutOfBoundsException n) {
            return "Number of participants field must be a long-type number. Try again.";
        }
    }
}
