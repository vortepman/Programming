package Commands;

import given.CollectionSupervisor;

/**
 * Class of command 'info'
 * @author Petrov Ilya
 * @version 1.1
 */
public class Info extends CommandSkeleton {

    /**
     * Method for executing this command
     *
     * @param supervisor collection
     * @return String description of command
     */
    @Override
    public String move(CollectionSupervisor supervisor) {
        return "Collection Type: " + supervisor.getMusicBands().getClass() +
                "\nInitialization date: " + supervisor.getInitializationDate() +
                "\nAmount of elements: " + supervisor.getMusicBands().size() +
                "\nCreator of the program: Petrov Ilya" +
                "\nData storage format: CSV";
    }
}

