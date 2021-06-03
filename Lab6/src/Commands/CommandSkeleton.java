package Commands;

import given.CollectionSupervisor;

/**
 * @author Petrov Ilya
 * @version 1.1
 * Class from which all commands are inherited
 */
abstract public class CommandSkeleton {

    /** Method for commands execution */
    public Object move(String s) {
        return null;
    }
    /** Method for commands execution */
    public String move(){
        return null;
    }
    /** Method for commands execution */
    public Object move(CollectionSupervisor supervisor) {
        return null;
    }
    /** Method for commands execution */
    public Object move(String s, CollectionSupervisor supervisor) {
        return null;
    }
}