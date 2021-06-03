package Commands;

import given.*;

import java.io.IOException;

/**
 * Class of command 'add'
 * @author Petrov Ilya
 * @version 1.1
 */
public class Add extends CommandSkeleton {

    /**
     * Method for executing this command
     *
     * @param supervisor collection
     * @return String phrase for close client
     */
    @Override
    public String move(String element, CollectionSupervisor supervisor) {
        String[] myElement = element.trim().split("\n", 9);
        Coordinates newCoordinates = new Coordinates(Long.parseLong(myElement[3]), Long.valueOf(myElement[4]));
        Album newAlbum = new Album(myElement[7], Long.valueOf(myElement[8]));
        MusicGenre newMusicGenre = MusicGenre.valueOf(myElement[6]);
        MusicBand newMusicBand = new MusicBand(Long.valueOf(myElement[0]), myElement[1], myElement[2], newCoordinates
                , Long.parseLong(myElement[5]), newMusicGenre, newAlbum);
        supervisor.getMusicBands().addFirst(newMusicBand);
        supervisor.save();
        return "Element added successfully.";
    }
}