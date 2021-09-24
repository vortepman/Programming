import java.io.*;

public class Main {

    /**
     * Main class for starting a program
     * @author Petrov Ilya
     * @version 1.0
     * @param args - args for program successfully working
     * @throws IOException - may be throw in the method
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Collection manager v0.1 by Petrov Ilya is starting -__-");
        System.out.println("Here you can make a list of your favorite bands.");
        Helper helper = new Helper(new CollectionSupervisor());
        helper.autoMode();
    }
}
