/**
 * Class for starting a client app
 * @author Petrov Ilya
 * @version 1.0
 */
public class StartClient{

    /** Client app entry point */
    public static void main(String[] args) {
        System.out.println("The client module started working");
        System.out.println("Connecting to the server... ... ...");
        ClientLogicalBody clientLogicalBody = new ClientLogicalBody();
        clientLogicalBody.clientRun();
    }
}


