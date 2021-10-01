package Commands;

import given.CollectionSupervisor;
import given.DatabaseSupervisor;

/**
 * @author Petrov Ilya
 * @version 1.2
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
    public Object move(DatabaseSupervisor databaseSupervisor, String s) {
        return null;
    }
    /** Method for commands execution */
    public Object move(String s, DatabaseSupervisor databaseSupervisor, String ss) {
        return null;
    }
    /** Method for commands execution */
    public Object move(DatabaseSupervisor databaseSupervisor) {
        return null;
    }
}