package Commands;

import given.CollectionSupervisor;

import java.util.*;

/**
 * Class of command 'help'
 * @author Petrov Ilya
 * @version 1.1
 */
public class Help extends CommandSkeleton {

    /**
     * Method for executing this command
     *
     * @param supervisor collection
     */
    @Override
    public String move(CollectionSupervisor supervisor) {
        StringBuilder letter = new StringBuilder();
        for (Map.Entry<String, String> entry : supervisor.getInformationAboutCommands().entrySet()) {
            letter.append(entry.getKey()).append(entry.getValue()).append("\n");
        }
        return letter.toString();
    }
}

