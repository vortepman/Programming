import Commands.*;

import given.DatabaseSupervisor;
import given.MusicBand;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.BindException;
import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Class for implementing the work of the server
 * @author Petrov Ilya
 * @version 1.1
 */
public class ServerLogicalBody {

    /**
     * UDP Server socket run on this port
     */
    public final static int serverPort = 5462;
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
    //private ForkJoinPool forkJoinPool = new ForkJoinPool();

    /** method for launching the server application */
    public void serverRun() throws IOException {
        try {
            DatabaseSupervisor databaseSupervisor = new DatabaseSupervisor();
            serverCommand();
            ExecutorService readingPool = Executors.newFixedThreadPool(10);
            while (true) {
                datagramSocket = new DatagramSocket(serverPort);
                Future<String[]> readerResult = readingPool.submit(ServerLogicalBody::receivingLetter);
                //String[] message = receivingLetter();
                String[] message = readerResult.get();
                if (message[0].equals("downloadingCollection")) {
                    databaseSupervisor.getMusicBands().clear();
                    DatabaseSupervisor.downloadElements(DatabaseSupervisor.getConnection(), databaseSupervisor.getMusicBands(), message[2]);
                    //userChecker = message[2];
                    String answerForTheClient = "Your collection has been loaded successfully!";
                    secondByteLetter = answerForTheClient.getBytes();
                    DatagramPacket toClientPacket = new DatagramPacket(secondByteLetter, secondByteLetter.length, inetAddress, port);
                    datagramSocket.send(toClientPacket);
                    datagramSocket.close();
                    continue;
                }

//                Runnable dispatch = () -> {
//                    try {
//                        creatingAResponse(databaseSupervisor);
//                        loadedCollection = databaseSupervisor.getMusicBands();
//                        //datagramSocket.close();
//                    } catch (IOException ioException) {
//                        System.out.println("Error while sending a response!");
//                    }
//                    return null;
//                };
//                forkJoinPool.execute(ForkJoinTask.adapt(dispatch));
                creatingAResponse(databaseSupervisor);
                loadedCollection = databaseSupervisor.getMusicBands();
                datagramSocket.close();
            }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Now the program will stop working.");
            System.exit(0);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            System.out.println("Please, shut up)");
        } catch (BindException bindException) {
            System.out.println("The server application is already running!");
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    /**
     * Module for receiving messages from the client
     *
     * @return - String client letter
     * @throws IOException - receiving exception
     */
    public static String[] receivingLetter() throws IOException {
        DatagramPacket packFromClient = new DatagramPacket(byteLetter, byteLetter.length);
        datagramSocket.receive(packFromClient);
        inetAddress = packFromClient.getAddress();
        port = packFromClient.getPort();
        String letter = new String(packFromClient.getData(), 0, packFromClient.getLength());
        System.out.println("Letter received from client: {" + letter + "}");
        System.out.println("Message received successfully.");
        commandFromUser = letter.trim().split(" ", 3);
        return commandFromUser;
    }

    /**
     * Module for execution of commands
     *
     * @return - String description of command
     */
    public static String commandExecutor(String firstValue, String secondValue, String thirdValue, DatabaseSupervisor databaseSupervisor) {
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Object> out = service.submit(() -> {
            String executableCommand;
            synchronized (ServerLogicalBody.class) {
                switch (firstValue) {
                    case "add":
                        executableCommand = new Add().move(secondValue, databaseSupervisor, thirdValue);
                        break;
                    case "help":
                        executableCommand = new Help().move(databaseSupervisor);
                        break;
                    case "info":
                        executableCommand = new Info().move(databaseSupervisor);
                        break;
                    case "clear":
                        executableCommand = new Clear().move(databaseSupervisor, thirdValue);
                        break;
                    case "show":
                        executableCommand = new Show().move(databaseSupervisor);
                        break;
                    case "show_all":
                        executableCommand = new ShowAll().move(databaseSupervisor);
                        break;
                    case "head":
                        executableCommand = new Head().move(databaseSupervisor);
                        break;
                    case "remove_head":
                        executableCommand = new RemoveHead().move(databaseSupervisor, thirdValue);
                        break;
                    case "average_of_number_of_participants":
                        executableCommand = new AverageOfNumberOfParticipants().move(databaseSupervisor, thirdValue);
                        break;
                    case "update":
                        executableCommand = new Update().move(secondValue, databaseSupervisor, thirdValue);
                        break;
                    case "remove":
                        executableCommand = new RemoveById().move(secondValue, databaseSupervisor, thirdValue);
                        break;
                    case "count_greater_than_number_of_participants":
                        executableCommand = new CountGreaterThanNumberOfParticipants().move(secondValue, databaseSupervisor, thirdValue);
                        break;
                    case "execute_script":
                        DatabaseSupervisor.getPaths().add(secondValue.toLowerCase());
                        executableCommand = new ExecuteScript().move(secondValue, databaseSupervisor, thirdValue);
                        DatabaseSupervisor.getPaths().clear();
                        break;
                    default:
                        executableCommand = "Incomprehensible command. Write 'help' for reference.";
                        break;
                }
            }
            return executableCommand;
        });
        try {
            return (String) out.get();
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return firstValue;
    }

    /**
     * Module for sending answers to the client
     *
     * @throws IOException - receiving exception
     */
    public static void creatingAResponse(DatabaseSupervisor databaseSupervisor) throws IOException {
        final String[] answerForClient = new String[1];
        Runnable runnable = () -> {
            if (commandFromUser.length == 3) {
                answerForClient[0] = commandExecutor(commandFromUser[0], commandFromUser[1], commandFromUser[2], databaseSupervisor);
            } else {
                answerForClient[0] = "Unknown command. Write 'help' for reference.";
            }
            secondByteLetter = answerForClient[0].getBytes();
            DatagramPacket toClientPacket = new DatagramPacket(secondByteLetter, secondByteLetter.length, inetAddress, port);
            try {
                datagramSocket.send(toClientPacket);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    /**
     * Module for 'exit' command on server
     *
     */
    private void serverCommand() {
        Scanner scanner = new Scanner(System.in);
        Runnable serverInput = () -> {
            try {
                while (true) {
                    String[] serverCommand = {"", ""};
                    try {
                        serverCommand = (scanner.nextLine().trim() + " ").split(" ", 2);
                    } catch (NoSuchElementException e) {
                        System.out.println("Now the server will stop working.");
                        System.exit(0);
                    }
                    serverCommand[1] = serverCommand[1].trim();
                    if (!serverCommand[0].equals("")) {
                        if (serverCommand[0].equals("exit")) {
                            System.out.println("Now the server will stop working.");
                            System.exit(0);
                        } else {
                            System.out.println("Incomprehensible command.");
                        }
                    } else {
                        System.out.println("This value can not be empty.");
                    }

                }
            } catch (Exception ignored){}
        };
        Thread thread = new Thread(serverInput);
        thread.start();
    }

}
