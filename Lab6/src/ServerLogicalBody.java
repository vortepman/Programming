import Commands.*;
import given.CollectionSupervisor;
import given.MusicBand;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.BindException;
import java.util.ArrayDeque;
import java.util.NoSuchElementException;

/**
 * Class for implementing the work of the server
 * @author Petrov Ilya
 * @version 1.0
 */
public class ServerLogicalBody {

    /**
     * UDP Server socket run on this port
     */
    public final static int serverPort = 4444;
    /**
     * Socket for communication
     */
    private static DatagramSocket datagramSocket;
    /**
     * Array of bytes for organizing a packet for receiving information from client
     */
    static byte[] byteLetter = new byte[65535];
    /**
     * Array of bytes for organizing a packet for sending answer-message to the client
     */
    static byte[] secondByteLetter = new byte[65535];
    /**
     * Field for saved the IP-address
     */
    private static InetAddress inetAddress;
    /**
     * Client UDP socket running on this port
     */
    public static int port;
    /**
     * Field for saved client commands
     */
    private static String[] commandFromUser;
    /**
     * Collection for collecting data
     */
    static ArrayDeque<MusicBand> loadedCollection = new ArrayDeque<>();

    /** method for launching the server application */
    public void serverRun() throws IOException {
        try {
            CollectionSupervisor yeapSupervisor = new CollectionSupervisor();
            while (true) {
                datagramSocket = new DatagramSocket(serverPort);
                String message = receivingLetter();
                creatingAResponse(yeapSupervisor);
                loadedCollection = yeapSupervisor.getMusicBands();
                datagramSocket.close();
            }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Now the program will stop working.");
            System.exit(0);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            System.out.println("Please, input a true path to CSV file.");
        } catch (BindException bindException) {
            System.out.println("The server application is already running!");
        }

    }

    /**
     * Module for receiving messages from the client
     *
     * @return - String client letter
     * @throws IOException - receiving exception
     */
    public static String receivingLetter() throws IOException {
        DatagramPacket packFromClient = new DatagramPacket(byteLetter, byteLetter.length);
        datagramSocket.receive(packFromClient);
        inetAddress = packFromClient.getAddress();
        port = packFromClient.getPort();
        String letter = new String(packFromClient.getData(), 0, packFromClient.getLength());
        System.out.println("Letter received from client: {" + letter + "})");
        System.out.println("Message received successfully.");
        commandFromUser = letter.trim().split(" ", 2);
        return letter;
    }

//    public static String commandExecutorWith1Arg(String argument, CollectionSupervisor collectionSupervisor) {
//        String answer;
//        switch (argument) {
//            case "":
//                answer = "";
//                break;
//            case "help":
//                answer = new Help().move(collectionSupervisor);
//                break;
//            case "info":
//                answer = new Info().move(collectionSupervisor);
//                break;
//            default:
//                answer = "Incomprehensible command. Write 'help' for reference.";
//                break;
//        }
//        return answer;
//    }

    /**
     * Module for execution of commands
     *
     * @return - String description of command
     */
    public static String commandExecutorWith2Args(String firstValue, String secondValue, CollectionSupervisor collectionSupervisor) {
        String executableCommand;
        switch (firstValue) {
            case "add":
                executableCommand = new Add().move(secondValue, collectionSupervisor);
                break;
            case "help":
                executableCommand = new Help().move(collectionSupervisor);
                break;
            case "info":
                executableCommand = new Info().move(collectionSupervisor);
                break;
            case "clear":
                executableCommand = new Clear().move(collectionSupervisor);
                break;
            case "show":
                executableCommand = new Show().move(collectionSupervisor);
                break;
            case "head":
                executableCommand = new Head().move(collectionSupervisor);
                break;
            case "remove_head":
                executableCommand = new RemoveHead().move(collectionSupervisor);
                break;
            case "average_of_number_of_participants":
                executableCommand = new AverageOfNumberOfParticipants().move(collectionSupervisor);
                break;
            case "update":
                executableCommand = new Update().move(secondValue, collectionSupervisor);
                break;
            case "remove":
                executableCommand = new RemoveById().move(secondValue, collectionSupervisor);
                break;
            case "count_greater_than_number_of_participants":
                executableCommand = new CountGreaterThanNumberOfParticipants().move(secondValue, collectionSupervisor);
                break;
            case "execute_script":
                CollectionSupervisor.getPaths().add(secondValue.toLowerCase());
                executableCommand = new ExecuteScript().action(secondValue, collectionSupervisor);
                CollectionSupervisor.getPaths().clear();
                break;
            default:
                executableCommand = "Incomprehensible command. Write 'help' for reference.";
                break;
        }
        return executableCommand;
    }

//    public static void creatingAResponse(CollectionSupervisor supervisor) throws IOException {
//        String answerForClient;
//        if (commandFromUser.length == 1) {
//            answerForClient = commandExecutorWith1Arg(commandFromUser[0], supervisor);
//        } else if (commandFromUser.length == 2) {
//            answerForClient = commandExecutorWith2Args(commandFromUser[0], commandFromUser[1], supervisor);
//        } else answerForClient = "Unknown command. Write 'help' for reference.";
//        secondByteLetter = answerForClient.getBytes();
//        DatagramPacket toClientPacket = new DatagramPacket(secondByteLetter, secondByteLetter.length, inetAddress, port);
//        datagramSocket.send(toClientPacket);
//    }

    /**
     * Module for sending answers to the client
     *
     * @throws IOException - receiving exception
     */
    public static void creatingAResponse(CollectionSupervisor supervisor) throws IOException {
        String answerForClient;
        if (commandFromUser.length == 2) {
            answerForClient = commandExecutorWith2Args(commandFromUser[0], commandFromUser[1], supervisor);
        } else {
            answerForClient = "Unknown command. Write 'help' for reference.";
        }
        secondByteLetter = answerForClient.getBytes();
        DatagramPacket toClientPacket = new DatagramPacket(secondByteLetter, secondByteLetter.length, inetAddress, port);
        datagramSocket.send(toClientPacket);
    }

}
