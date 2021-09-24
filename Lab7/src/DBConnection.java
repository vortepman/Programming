////import java.sql.Connection;
////import java.sql.DriverManager;
////import java.sql.SQLException;
//import java.util.Properties;
//import java.sql.*;
//
//public class DBConnection {
//    private static final String url = "jdbc:postgresql://localhost:9370/studs";
////    private static final String url = "jdbc:postgresql://localhost:5432/library";
//    private static final String user = "s312642";
////    private static final String user = "ipetrov";
//    private static final String pass =  "pkj919";
////    private static final String pass =  "";
//    private static final String schema =  "s312642";
////    private static final String schema =  "public";
//
//    private static Connection connection;
//
//    public static Connection getConnection() {
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            System.err.println(e.getMessage());
//            e.printStackTrace();
//        }
//
//        if (connection == null) {
////            Properties props = new Properties();
////            props.setProperty("user", user);
////            props.setProperty("password", pass);
////            props.setProperty("ssl", "false");
//
//            String connUrl = url + "?user=" + user + "&password=" + pass + "&ssl=false" + "&currentSchema=" + schema;
//
//            try {
//                connection = DriverManager.getConnection(connUrl);
////                connection = DriverManager.getConnection(url, props);
//            } catch (SQLException e) {
//                System.err.println(e.getMessage());
//                e.printStackTrace();
//            }
//        }
//
//        return connection;
//    }
//
//    public static void closeConnection() {
//        if (connection != null) {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                System.err.println(e.getMessage());
//            }
//
//            connection = null;
//        }
//    }
//}
