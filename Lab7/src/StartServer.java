import given.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for starting a server
 * @author Petrov Ilya
 * @version 1.0
 */
public class StartServer {

    /** Server entry point */
    public static void main(String[] args) throws IOException {
//        List<MusicBand> musicBands = new ArrayList<MusicBand>();
//
//        Connection c = DBConnection.getConnection();
//
//        try {
//            PreparedStatement ps = c.prepareStatement("select * from music_bands");
////            PreparedStatement ps = c.prepareStatement("" +
////                "select b.id, b.name, creation_date, number_of_participants, p.x as x_coordinate, p.y as y_coordinate, ba.name as best_album_name, ba.sales as best_album_sales\n" +
////                "from music_bands b\n" +
////                "join albums ba on b.best_album_id = ba.id\n" +
////                "join places p on b.place_id = p.id\n" +
////                "where b.id = ?;" +
////            "");
////            ps.setInt(1, 1);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                Coordinates coordinates = new Coordinates(
//                    rs.getInt("x_coordinate"),
//                    (long) rs.getInt("y_coordinate")
//                );
//                Album album = new Album(
//                    rs.getString("best_album_name"),
//                    (long) rs.getInt("best_album_sales")
//                );
//                MusicBand musicBand = new MusicBand(
//                    (long) rs.getInt("id"),
//                    rs.getString("name"),
//                    rs.getString("creation_date"),
//                    coordinates,
//                    rs.getInt("number_of_participants"),
//                    MusicGenre.ROCK,
//                    album
//                );
//
//                musicBands.add(musicBand);
//            }
//
//            for (MusicBand m : musicBands) {
//                System.out.println(m);
//            }
//        } catch (SQLException e) {
//            System.err.println(e.getMessage());
//        }

        System.out.println("The server module started working.");
        ServerLogicalBody serverLogicalBody = new ServerLogicalBody();
        serverLogicalBody.serverRun();
    }
}
