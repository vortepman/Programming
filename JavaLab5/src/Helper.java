import given.MusicBand;

import java.util.*;

/**
 * @author Petrov Ilya
 * @version 1.0
 * Method for handling user`s command
 */
public class Helper {

    /** Collection manager for realising user`s commands */
    private final CollectionManager collectionManager;
    /** Field for receiving user`s command */
    private String userCommand;

    {
        userCommand = "";
    }

    /**
     * Constructor for making a commander
     * @param manager - CollectionManager class object which will realise user`s commands
     */
    public Helper(CollectionManager manager) {
        this.collectionManager = manager;
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
                    String[] finalUserCommand = userCommand.trim().toLowerCase().split(" ", 2);
                    try {
                        switch (finalUserCommand[0]) {
                            case "":
                                break;
                            case "help":
                                collectionManager.help();
                                break;
                            case "info":
                                collectionManager.info();
                                break;
                            case "show":
                                collectionManager.show();
                                break;
                            case "add":
                                collectionManager.add();
                                break;
                            case "clear":
                                collectionManager.clear();
                                break;
                            case "exit":
                                collectionManager.exit();
                                break;
                            case "update":
                                collectionManager.update(finalUserCommand[1]);
                                break;
                            case "remove":
                                collectionManager.remove(finalUserCommand[1]);
                                break;
                            case "head":
                                collectionManager.head();
                                break;
                            case "remove_head":
                                collectionManager.remove_head();
                                break;
                            case "execute_script":
                                collectionManager.execute_script(finalUserCommand[1]);
                                break;
                            case "add_if_max_sales":
                                System.out.println("Enter characteristics of element, " +
                                        "which will be compared with elements in collection.");
                                collectionManager.add_if_max_sales(new MusicBand(collectionManager.receiveId(),
                                        collectionManager.receiveName(), collectionManager.returnDate(),
                                        collectionManager.receiveCoordinates(),
                                        collectionManager.receiveNumberOfParticipants(),
                                        collectionManager.receiveMusicGenre(), collectionManager.receiveBestAlbum()));
                                break;
                            case "average_of_number_of_participants":
                                collectionManager.average_of_number_of_participants();
                                break;
                            case "group_counting_by_id":
                                collectionManager.group_counting_by_id(finalUserCommand[1]);
                                break;
                            case "save":
                                collectionManager.save();
                                break;
                            case "count_greater_than_number_of_participants":
                                collectionManager.count_greater_than_number_of_participants(finalUserCommand[1]);
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

