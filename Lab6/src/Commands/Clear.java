package Commands;

import given.CollectionSupervisor;

/**
 * Class of command 'clear'
 * @author Petrov Ilya
 * @version 1.1
 */
public class Clear extends CommandSkeleton {

    /**
     * Method for executing this command
     *
     * @param supervisor collection
     * @return String description of command
     */
    @Override
    public String move(CollectionSupervisor supervisor){
        String letter;
        if (supervisor.getMusicBands().isEmpty()){
            letter = "The collection is already empty";
        } else {
            supervisor.getMusicBands().clear();
            letter = "Collection successfully cleared.";
        }
        supervisor.save();
        return letter;
    }
}
