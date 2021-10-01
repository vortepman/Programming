package Commands;

import given.CollectionSupervisor;
import given.DatabaseSupervisor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class of command 'execute_script'
 * @author Petrov Ilya
 * @version 1.2
 */
public class ExecuteScript extends CommandSkeleton {

    /** Method for executing script from external file */
    public String move(String pathToFile, DatabaseSupervisor databaseSupervisor, String username) {
        try {
            String[] newPathOfFile = pathToFile.trim().split(" ", 2);
            BufferedReader reader = new BufferedReader(new FileReader(newPathOfFile[0]));
            StringBuilder letter = new StringBuilder();
            String[] okUserCommand;
            String command;
                letter.append("!!! To avoid recursion, your file can't contain execute script commands.\n");
                while ((command = reader.readLine()) != null) {
                    okUserCommand = command.trim().toLowerCase().split(" ", 2);
                    switch (okUserCommand[0]) {
                        case "":
                            letter.append("Command cannot be empty.\n");
                            break;
                        case "help":
                            letter.append(new Help().move(databaseSupervisor)).append("\n")
                                    .append("Command 'help' is ended.\n");
                            break;
                        case "info":
                            letter.append(new Info().move(databaseSupervisor)).append("\n")
                                    .append("Command 'info' is ended.\n");
                            break;
                        case "show":
                            letter.append(new Show().move(databaseSupervisor)).append("\n")
                                    .append("Command 'show' is ended.\n");
                            break;
                        case "remove":
                            if (okUserCommand.length == 2) {
                                letter.append(new RemoveById().move(okUserCommand[1], databaseSupervisor, username)).append("\n")
                                        .append("Command 'remove' ").append(okUserCommand[1]).append("' is ended.\n");
                            } else letter.append("This command needs an id-argument.\n");
                            break;
                        case "clear":
                            letter.append(new Clear().move(databaseSupervisor, username)).append("\n")
                                    .append("Command 'clear' is ended.\n");
                            break;
                        case "execute_script":
                            if (okUserCommand.length == 2) {
                                System.out.println(DatabaseSupervisor.getPaths());
                                if (!(DatabaseSupervisor.getPaths().contains(okUserCommand[1].toLowerCase()))) {
                                    DatabaseSupervisor.getPaths().add(okUserCommand[1]);
                                    letter.append(new ExecuteScript().move(okUserCommand[1], databaseSupervisor, username)).append("\n")
                                            .append("Command 'execute_script ").append(okUserCommand[1]).append("' is ended.\n");
                                } else {
                                    letter.append("Recursion has been found in file: ")
                                            .append(okUserCommand[1]).append("\nPlease correct your script!\n");
                                }
                            } else letter.append("This command needs an argument.\n");
                            break;
                        default:
                            letter.append("Unknown command.\n");
                            break;
                    }
                }
                reader.close();
                letter.append("Commands are ended.");
            return letter.toString();
        } catch(FileNotFoundException fileNotFoundException){
            return "File not found. Try again.";
        } catch(IOException ioException){
            return "File reading exception. Try again.";
        }
    }
}
