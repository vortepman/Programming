import given.*;
import java.io.*;
import java.net.*;
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
    public final static int serverPort = 4444;
    /**
     * Socket for communication
     */
    private static DatagramSocket datagramSocket;

    /** method for launching the client application */
    public void clientRun() {
        try {
            System.out.println("Welcome to the client app, buddy.");
            inetAddress = InetAddress.getByName("localhost");
            while (true) {
                datagramSocket = new DatagramSocket();
                String letter = creatingAMessage();
                if (letter.equals("exit")) {
                    System.out.println("Now the program will stop working.");
                    System.exit(0);
                    datagramSocket.close();
                    break;
                }
                if (letter.equals("tryAgain")) {
                    continue;
                }
                String answerFromServer = readingResponse();
                datagramSocket.close();
            }
        } catch (UnknownHostException | SocketException unknownHostException) {
            System.out.println("Host error!");
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Now the program will stop working.");
            System.exit(0);
        } catch (IOException ioException) {
            System.out.println("IOException, It's not clear how it came about.");
        }
    }

    /**
     * Module for creating and sending requests to the server
     *
     * @return String - entered clientCommand
     * @throws IOException receiving exception
     */
    public static String creatingAMessage() throws IOException {
        Scanner inputValue = new Scanner(System.in);
        String clientCommand = "";
        while (clientCommand.equals("")) {
            System.out.print("Input a command: ");
            clientCommand = inputValue.nextLine();
            if (clientCommand.equals("")) {
                System.out.println("This value can't be empty.");
            }
        }
        String auxExpression = "\\s+";
        String semiCommand = clientCommand.replaceAll(auxExpression, " ");
        String[] fullCommand = semiCommand.trim().split(" ", 2);
        byte[] byteLetter;
        if (fullCommand[0].equals("add")) {
            InformationCollector collector = new InformationCollector();
            MusicBand musicBand = new MusicBand(Long.parseLong(String.valueOf(collector.CollectId()))
                    , collector.CollectName(), LocalDateTime.now().toString(), collector.CollectCoordinates()
                    , collector.CollectNumberOfParticipants(), collector.CollectMusicGenre()
                    , collector.CollectBestAlbum());
            String letter = fullCommand[0] + " \n" + musicBand.getId() + "\n" +
                    musicBand.getName() + "\n" + musicBand.returnCreationDate() + "\n" +
                    musicBand.getCoordinates().getX() + "\n" +
                    musicBand.getCoordinates().getY() + "\n" +
                    musicBand.getNumberOfParticipants() + "\n" +
                    musicBand.getGenre() + "\n" +
                    musicBand.getBestAlbum().getName() + "\n" +
                    musicBand.getBestAlbum().getSales();
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
                    StringBuilder letter = new StringBuilder();
                    letter.append(fullCommand[0]).append(" \n").append(musicBand.getId()).append("\n")
                            .append(musicBand.getName()).append("\n").append(musicBand.returnCreationDate()).append("\n")
                            .append(musicBand.getCoordinates().getX()).append("\n")
                            .append(musicBand.getCoordinates().getY()).append("\n")
                            .append(musicBand.getNumberOfParticipants()).append("\n")
                            .append(musicBand.getGenre()).append("\n")
                            .append(musicBand.getBestAlbum().getName()).append("\n")
                            .append(musicBand.getBestAlbum().getSales());
                    byteLetter = letter.toString().getBytes();
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
            byteLetter = clientCommand.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(byteLetter, byteLetter.length, inetAddress, serverPort);
            datagramSocket.send(datagramPacket);
        } else if (fullCommand[0].equals("exit")) {
            clientCommand = "exit";
        }
        else {
            byteLetter = (clientCommand + " secondLetter").getBytes();
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

