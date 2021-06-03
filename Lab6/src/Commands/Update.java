package Commands;

import given.*;

import java.io.IOException;

/**
 * Class of command 'update'
 * @author Petrov Ilya
 * @version 1.1
 */
public class Update extends CommandSkeleton {

    /**
     * Method for executing this command
     *
     * @param supervisor collection
     * @return String phrase for close client
     */
    @Override
    public String move(String element, CollectionSupervisor supervisor) {
        try {
            String[] myElement = element.trim().split("\n", 9);
            Coordinates newCoordinates = new Coordinates(Long.parseLong(myElement[3]), Long.valueOf(myElement[4]));
            Album newAlbum = new Album(myElement[7], Long.valueOf(myElement[8]));
            MusicGenre newMusicGenre = MusicGenre.valueOf(myElement[6]);
            long newId = Long.parseLong(myElement[0]);
            int test = 0;
            for (MusicBand musicBand : supervisor.getMusicBands()) {
                if (newId == musicBand.getId()) {
                    musicBand.setName(myElement[1]);
                    musicBand.setCoordinates(newCoordinates);
                    musicBand.setNumberOfParticipants(Long.parseLong(myElement[5]));
                    musicBand.setGenre(newMusicGenre);
                    musicBand.setBestAlbum(newAlbum);
                    test = 1;
                    supervisor.save();
                }
            }
            if (test == 0) {
                return "An element with this id does not exist.";
            } else {
                return "Element updated without problems";
            }
        } catch (NumberFormatException numberFormatException) {
            return "Id must be of long-type number. Try again.";
        }
    }
}
