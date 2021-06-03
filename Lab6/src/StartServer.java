import java.io.IOException;

/**
 * Class for starting a server
 * @author Petrov Ilya
 * @version 1.0
 */
public class StartServer {

    /** Server entry point */
    public static void main(String[] args) throws IOException {
        System.out.println("The server module started working.");
        System.out.println("Waiting for the client's actions... ... ...");
        ServerLogicalBody serverLogicalBody = new ServerLogicalBody();
        serverLogicalBody.serverRun();
    }
}
