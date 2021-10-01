package Commands;

import given.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;

/**
 * Class of command 'remove_by_id'
 * @author Petrov Ilya
 * @version 1.2
 */
public class RemoveById extends CommandSkeleton {

    /**
     * Method for executing this command
     *
     * @param databaseSupervisor element for working with database
     * @return String phrase for close client
     */
    @Override
    public String move(String element, DatabaseSupervisor databaseSupervisor, String username) {
        System.out.println("{" + element.trim() + "}");
        try {
            Connection c = DatabaseSupervisor.getConnection();
            PreparedStatement ps = c.prepareStatement("delete from music_bands where (id = ?) and (user_name = ?)");
            ps.setInt(1, Integer.parseInt(element.trim()));
            ps.setString(2, username);
            ps.executeUpdate();

            ArrayDeque<MusicBand> oneOfMusicBands = databaseSupervisor.getMusicBands();

//            String strId = element.trim();
//            long id;
//            if (strId.indexOf(" ") > 0) {
//                id = Long.parseLong(strId.substring(0, strId.indexOf(" ")));
//            } else {
//                id = Long.parseLong(strId);
//            }
            long id = Long.parseLong(element.trim());

            if (oneOfMusicBands.size() != 0) {
                for (MusicBand musicBand : oneOfMusicBands) {
                    //System.out.println(musicBand.getId().toString());
                    if (id == musicBand.getId()) {
                        oneOfMusicBands.remove(musicBand);
                        //databaseSupervisor.save();
                        return "Element deleted without problems.";
                    }
                }
                return "An element with this id doesn't exist. {" + id + "}";
            } else {
                return "The collection is empty.";
            }
        } catch (NumberFormatException n) {
            return "Id value must be a long-type number. Try again.";
        } catch (SQLException sqlException) {
            return  "An item with this id does not exist in your collection." + "\nPerhaps, an item with this id exists" +
                    " in the collection of another user, but you don't have edit rights.";
        }
        //return "I don't know why it came out";
    }
}
