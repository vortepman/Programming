package Commands;

import given.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;

/**
 * Class of command 'show_all'
 * @author Petrov Ilya
 * @version 1.1
 */
public class ShowAll extends CommandSkeleton {

    /**
     * Method for executing this command
     *
     * @param databaseSupervisor collection
     * @return - String description of command
     */
    @Override
    public String move(DatabaseSupervisor databaseSupervisor){
        StringBuilder letter = new StringBuilder();
        ArrayDeque<MusicBand> oneOfMusicBands = new ArrayDeque<>();
        Connection c = DatabaseSupervisor.getConnection();
        //int downloads = 0;
        try {
            PreparedStatement ps = c.prepareStatement("select * from music_bands");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Coordinates coordinates = new Coordinates(
                        rs.getInt("x_coordinate"),
                        (long) rs.getInt("y_coordinate")
                );
                Album album = new Album(
                        rs.getString("best_album_name"),
                        (long) rs.getInt("best_album_sales")
                );
                MusicGenre musicGenre;
                if (rs.getString("genre").equals("ROCK")) {
                    musicGenre = MusicGenre.ROCK;
                } else if (rs.getString("genre").equals("JAZZ")) {
                    musicGenre = MusicGenre.JAZZ;
                } else {
                    musicGenre = MusicGenre.BLUES;
                }
                MusicBand musicBand = new MusicBand(
                        (long) rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("creation_date"),
                        coordinates,
                        rs.getInt("number_of_participants"),
                        //MusicGenre.valueOf(rs.getString("genre")),
                        //MusicGenre.ROCK,
                        musicGenre,
                        album
                );
                oneOfMusicBands.add(musicBand);
                //downloads += 1;
            }
            //System.out.println("Number of successfully loaded element: " + downloads);
            //return "Number of successfully loaded element: " + downloads;
//            for (MusicBand m : musicBands) {
//                System.out.println(m);
//            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            //return e.getMessage();
        }
        //DatabaseSupervisor.downloadElements(DatabaseSupervisor.getConnection(), oneOfMusicBands, "*");
        if (!oneOfMusicBands.isEmpty()) {
            for(MusicBand musicBand : oneOfMusicBands){
                letter.append(musicBand.toString()).append("\n");
            }
        } else {
            letter.append("Database is empty.");
        }
        return letter.toString();
    }
}
