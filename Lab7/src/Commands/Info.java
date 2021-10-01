package Commands;

import given.CollectionSupervisor;
import given.DatabaseSupervisor;

/**
 * Class of command 'info'
 * @author Petrov Ilya
 * @version 1.2
 */
public class Info extends CommandSkeleton {

    /**
     * Method for executing this command
     *
     * @param databaseSupervisor element for working with database
     * @return String description of command
     */
    @Override
    public String move(DatabaseSupervisor databaseSupervisor) {
        return "Collection Type: " + databaseSupervisor.getMusicBands().getClass() +
                "\nInitialization date: " + databaseSupervisor.getInitializationDate() +
                "\nCreator of the program: Petrov Ilya" +
                "\nData storage format: Database psql";
    }
}

