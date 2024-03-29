package Commands;

import given.CollectionSupervisor;
import given.DatabaseSupervisor;

import java.util.*;

/**
 * Class of command 'help'
 * @author Petrov Ilya
 * @version 1.2
 */
public class Help extends CommandSkeleton {

    /**
     * Method for executing this command
     *
     * @param databaseSupervisor element for working with database
     */
    @Override
    public String move(DatabaseSupervisor databaseSupervisor) {
        StringBuilder letter = new StringBuilder();
        for (Map.Entry<String, String> entry : databaseSupervisor.getInformationAboutCommands().entrySet()) {
            letter.append(entry.getKey()).append(entry.getValue()).append("\n");
        }
        return letter.toString();
    }
}

