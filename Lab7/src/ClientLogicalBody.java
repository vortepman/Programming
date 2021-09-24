import com.fasterxml.jackson.databind.ObjectMapper;
import given.*;
import org.apache.commons.lang3.time.StopWatch;

import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.*;
import java.time.LocalDateTime;


/**
 * Class for implementing the work of the client application
 * @author Petrov Ilya
 * @version 1.0
 */
public class ClientLogicalBody {

    /**
     * Field for storing the IP-address
     */
    private static InetAddress inetAddress;
    /**
     * Server port to which the client socket is going to connect
     */
    public final static int serverPort = 5462;
    /**
     * Socket for communication
     */
    private static DatagramSocket datagramSocket;

    //public static String userChecker = "something";

    public static int firstRequestRate = 0;

    /** method for launching the client application */
    public void clientRun() {
        try {
            while (true) {
                System.out.println("Welcome to the client app, buddy.");

                //WorkWithUser workWithUser = new WorkWithUser();
                String username = WorkWithUser.logging();
                inetAddress = InetAddress.getByName("localhost");
                while (true) {
                    datagramSocket = new DatagramSocket();
                    String letter = creatingAMessage(username);

                    if (letter.equals("exit")) {
                        System.out.println("Now the program will stop working.");
                        System.exit(0);
                        datagramSocket.close();
                        break;
                    }
                    if (letter.equals("back")) {
                        System.out.println("Thank you for working in our program! Now you will be direct to the login field.");
                        firstRequestRate = 0;
                        datagramSocket.close();
                        break;
                    }
                    if (letter.equals("tryAgain")) {
                        datagramSocket.close();
                        continue;
                    }
                    String answerFromServer = readingResponse();
                    datagramSocket.close();
                }
            }
        } catch (UnknownHostException | SocketException unknownHostException) {
            System.out.println("Host error!");
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Now the program will stop working.");
            System.exit(0);
        } catch (IOException ioException) {
            System.out.println("IOException, It's not clear how it came about.");
        } catch (SQLException sqlException) {
            System.out.println("Database problem. Sorry, please restart the program, it is possible that your username" +
                    " already exists, but the problem may be different.");
        }
    }

    /**
     * Module for creating and sending requests to the server
     *
     * @return String - entered clientCommand
     * @throws IOException receiving exception
     */
    public static String creatingAMessage(String username) throws IOException {
        String clientCommand = "";
        byte[] byteLetter;
        if (firstRequestRate == 0) {
            String letter = "downloadingCollection secondLetter " + username;
            byteLetter = letter.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(byteLetter, byteLetter.length, inetAddress, serverPort);
            datagramSocket.send(datagramPacket);
            firstRequestRate += 1;
            return clientCommand;
        }
        Scanner inputValue = new Scanner(System.in);
        while (clientCommand.equals("")) {
            System.out.print("Input a command: ");
            clientCommand = inputValue.nextLine();
            if (clientCommand.equals("")) {
                System.out.println("This value can't be empty.");
            }
        }
        String auxExpression = "\\s+";
        String semiCommand = clientCommand.replaceAll(auxExpression, " ").trim();

        String lower = semiCommand.toLowerCase();
        char[] c = lower.toCharArray();
        int freq = 0;
        for (char value : c) {
            if (value == ' ') {
                freq += 1;
            }
        }
        if (freq > 1) {
            System.out.println("Enter at most one argument. Try again.");
            clientCommand = "tryAgain";
            return clientCommand;
        }
        String[] fullCommand = semiCommand.trim().split(" ", 2);
        //byte[] byteLetter;
        if (fullCommand[0].equals("add")) {
            InformationCollector collector = new InformationCollector();
            MusicBand musicBand = new MusicBand(Long.parseLong(String.valueOf(collector.CollectId()))
                    , collector.CollectName(), LocalDateTime.now().toString(), collector.CollectCoordinates()
                    , collector.CollectNumberOfParticipants(), collector.CollectMusicGenre()
                    , collector.CollectBestAlbum());
            String jsonMusicBand = musicBand.toJson();
            String letter = fullCommand[0] + " \n" + jsonMusicBand + " " + username;
            byteLetter = letter.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(byteLetter, byteLetter.length, inetAddress, serverPort);
            datagramSocket.send(datagramPacket);
        } else if (fullCommand[0].equals("update")) {
            if (fullCommand.length == 2) {
                try {
                    long verifiedId;
                    if (fullCommand[1].indexOf(" ") > 0) {
                        verifiedId = Long.parseLong(fullCommand[1].substring(0, fullCommand[1].indexOf(" ")));
                    } else {
                        verifiedId = Long.parseLong(fullCommand[1]);
                    }
                    fullCommand[1] = String.valueOf(verifiedId);
                    InformationCollector collector = new InformationCollector();
                    MusicBand musicBand = new MusicBand(Long.valueOf(fullCommand[1])
                            , collector.CollectName(), LocalDateTime.now().toString(), collector.CollectCoordinates()
                            , collector.CollectNumberOfParticipants(), collector.CollectMusicGenre()
                            , collector.CollectBestAlbum());
                    String newJsonMusicBand = musicBand.toJson();
                    String letter = fullCommand[0] + " \n" + newJsonMusicBand + " " + username;
                    byteLetter = letter.getBytes();
                    DatagramPacket datagramPacket = new DatagramPacket(byteLetter, byteLetter.length, inetAddress, serverPort);
                    datagramSocket.send(datagramPacket);
                } catch (NumberFormatException numberFormatException) {
                    System.out.println("Argument must be a Long-type number. Try again.");
                    clientCommand = "tryAgain";
                }
            } else {
                System.out.println("Please enter a value for the field id. Try again.");
                clientCommand = "tryAgain";
            }
        } else if (fullCommand.length == 2 && !fullCommand[0].equals("exit")) {
            //clientCommand = clientCommand + "\n" + username;
//            long verifiedId;
//            if (fullCommand[1].indexOf(" ") > 0) {
//                verifiedId = Long.parseLong(fullCommand[1].substring(0, fullCommand[1].indexOf(" ")));
//            } else {
//                verifiedId = Long.parseLong(fullCommand[1]);
//            }
//            fullCommand[1] = String.valueOf(verifiedId);
            byteLetter = (fullCommand[0] + " " + fullCommand[1] + " " + username).getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(byteLetter, byteLetter.length, inetAddress, serverPort);
            datagramSocket.send(datagramPacket);
        } else if (fullCommand[0].equals("exit")) {
            clientCommand = "exit";
        } else if (fullCommand[0].equals("back")) {
            clientCommand = "back";
        } else {
            byteLetter = (clientCommand + " secondLetter " + username).getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(byteLetter, byteLetter.length, inetAddress, serverPort);
            datagramSocket.send(datagramPacket);
        }
        return clientCommand;
    }

    /**
     * Module for receiving letters from the server
     *
     * @return String - answer from server
     * @throws IOException - receiving exception
     */
    public static String readingResponse() throws IOException {
        try {
            byte[] byteLetterOut = new byte[65535];
            DatagramPacket packFromServer = new DatagramPacket(byteLetterOut, byteLetterOut.length);
            datagramSocket.setSoTimeout(3232);
            datagramSocket.receive(packFromServer);
            String answerFromServer = new String(packFromServer.getData(), 0, packFromServer.getLength());
            System.out.println(answerFromServer);
            return answerFromServer;
        } catch (SocketTimeoutException socketTimeoutException) {
            System.out.println("Server isn't available.");
            return "Server is unavailable. Try later.";
        }
    }
}

