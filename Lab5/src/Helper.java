import given.MusicBand;

import java.util.*;

/**
 * @author Petrov Ilya
 * @version 1.0
 * Method for handling user`s command
 */
public class Helper {

    /** Collection manager for realising user`s commands */
    private final CollectionSupervisor collectionSupervisor;
    /** Field for receiving user`s command */
    private String userCommand;

    {
        userCommand = "";
    }

    /**
     * Constructor for making a commander
     * @param manager - CollectionManager class object which will realise user`s commands
     */
    public Helper(CollectionSupervisor manager) {
        this.collectionSupervisor = manager;
    }

    /**
     * Method for starting auto mode
     */
    public void autoMode() {
        try {
            try (Scanner commandReader = new Scanner(System.in)) {
                System.out.println("Tip: I recommend writing a help " +
                        "to get acquainted with the list of all available commands.");
                while (!userCommand.equals("exit")) {
                    System.out.print("Enter a command: ");
                    userCommand = commandReader.nextLine();
                    String regex = "\\s+";
                    String wrongUserCommand = userCommand.replaceAll(regex, " ");
                    String[] finalUserCommand = wrongUserCommand.trim().toLowerCase().split(" ", 2);
                    try {
                        switch (finalUserCommand[0]) {
                            case "":
                                break;
                            case "help":
                                collectionSupervisor.help();
                                break;
                            case "info":
                                collectionSupervisor.info();
                                break;
                            case "show":
                                collectionSupervisor.show();
                                break;
                            case "add":
                                collectionSupervisor.add();
                                break;
                            case "clear":
                                collectionSupervisor.clear();
                                break;
                            case "exit":
                                collectionSupervisor.exit();
                                break;
                            case "update":
                                collectionSupervisor.update(finalUserCommand[1]);
                                break;
                            case "remove":
                                collectionSupervisor.remove(finalUserCommand[1]);
                                break;
                            case "head":
                                collectionSupervisor.head();
                                break;
                            case "remove_head":
                                collectionSupervisor.remove_head();
                                break;
                            case "execute_script":
                                collectionSupervisor.execute_script(finalUserCommand[1]);
                                break;
                            case "add_if_max_sales":
                                System.out.println("Enter characteristics of element, " +
                                        "which will be compared with elements in collection.");
                                collectionSupervisor.add_if_max_sales(new MusicBand(collectionSupervisor.receiveId(),
                                        collectionSupervisor.receiveName(), collectionSupervisor.returnDate(),
                                        collectionSupervisor.receiveCoordinates(),
                                        collectionSupervisor.receiveNumberOfParticipants(),
                                        collectionSupervisor.receiveMusicGenre(), collectionSupervisor.receiveBestAlbum()));
                                break;
                            case "average_of_number_of_participants":
                                collectionSupervisor.average_of_number_of_participants();
                                break;
                            case "group_counting_by_id":
                                collectionSupervisor.group_counting_by_id(finalUserCommand[1]);
                                break;
                            case "save":
                                collectionSupervisor.save();
                                break;
                            case "count_greater_than_number_of_participants":
                                collectionSupervisor.count_greater_than_number_of_participants(finalUserCommand[1]);
                                break;
                            default:
                                System.out.println("Unknown command. Write help for help. Sorry for the tautology.");
                                break;
                        }
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        System.out.println("Argument of command is absent.");
                    }
                }
            }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Program will be finished now.");
            System.exit(1);
        }
    }
}

