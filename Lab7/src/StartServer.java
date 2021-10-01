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
        System.out.println("The server module started working.");
        ServerLogicalBody serverLogicalBody = new ServerLogicalBody();
        serverLogicalBody.serverRun();
    }
}
