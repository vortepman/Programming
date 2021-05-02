import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import com.opencsv.CSVWriter;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import given.*;

/**
 * @author Petrov Ilya
 * @version 1.0
 * Class which realised user`s commands
 */
public class CollectionManager {

    /** Environment variable initialization */
    String env = "CM1";
    /** ArrayDeque for keeping a collection as java-object */
    private final ArrayDeque<given.MusicBand> musicBands;
    /** HashMap collection for making a manual */
    private final HashMap<String, String> commandsInfo;
    /** Field for saving date of initialization the collection */
    private LocalDateTime initializationDate;

    {
        musicBands = new ArrayDeque<>();

        // Making a manual
        commandsInfo = new HashMap<>();
        commandsInfo.put("help", " - display help for available commands");
        commandsInfo.put("info", " - display information about the collection" +
                " (type, date of initialization, number of elements, etc.) to the standard output stream");
        commandsInfo.put("show", " - print to standard output all elements of the collection in string representation");
        commandsInfo.put("add {element}", " - add new element to the collection");
        commandsInfo.put("update id {element}", " - update the value of the collection element" +
                " whose id is equal to the given. After entering the command," +
                " you must enter the ID separated by a space. For example: update 1");
        commandsInfo.put("remove id {element}", " - remove an element from the collection by its ID." +
                " After entering the command, you must enter the ID separated by a space. For example: remove_by_id 1");
        commandsInfo.put("clear", " - clear the collection");
        commandsInfo.put("save", " - save the collection to file");
        commandsInfo.put("execute_script file_name", " - read and execute a script from specified file." +
                " You should to enter path to file after entering a command.");
        commandsInfo.put("exit", " - end the program (without saving to file)");
        commandsInfo.put("head", " - display the first element of the collection");
        commandsInfo.put("remove_head", " - display the first element of the collection and remove it");
        commandsInfo.put("add_if_max_sales", " - add new element to the collection, if it`s best album sales more, " +
                " than the sales of the largest element in this collection. You should to enter characteristics of" +
                " comparing element after entering a command.");
        commandsInfo.put("average_of_number_of_participants", " - display the average value of the field" +
                " numberOfParticipants for all elements of the collection");
        commandsInfo.put("group_counting_by_id", " - group the elements of the collection by the value of the id" +
                " field, display the number of elements in each group");
        commandsInfo.put("count_greater_than_number_of_participants numberOfParticipants", " - display the number of" +
                " elements, the value of the field numberOfParticipants of which is greater than the specified");
    }

    // Constructor for checking a path to file existence and file readiness to work
    public CollectionManager() {
        try {
            if(checkFile(env)) {
                try (
                        Reader reader = Files.newBufferedReader(Paths.get(System.getenv(env)));
                        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()
                ) {
                    String[] nextRecord;
                    int successfulDownloads = 0;
                    int nonSuccessfulDownloads = 0;

                    while ((nextRecord = csvReader.readNext()) != null) {
                        try {
                            long fileId = Long.parseLong(nextRecord[0]);
                            String fileName = nextRecord[1];
                            String fileCreationDate = nextRecord[4];
                            long xFileCoordinate = Long.parseLong(nextRecord[2]);
                            Long yFileCoordinate = Long.valueOf(nextRecord[3]);
                            long fileNumberOfParticipants = Long.parseLong(nextRecord[5]);
                            MusicGenre fileMusicGenre = MusicGenre.valueOf(nextRecord[6]);
                            String fileAlbumName = nextRecord[7];
                            long fileAlbumSales = Long.parseLong(nextRecord[8]);
                            Album fileAlbum = new Album(fileAlbumName, fileAlbumSales);
                            Coordinates fileCoordinates = new Coordinates(xFileCoordinate, yFileCoordinate);

                            if (fileId > 0 && !fileName.equals("") && fileNumberOfParticipants > 0
                                    && !fileAlbumName.equals("") && fileAlbumSales > 0) {
                                MusicBand fileMusicBand = new MusicBand(fileId, fileName, fileCreationDate
                                        , fileCoordinates, fileNumberOfParticipants, fileMusicGenre, fileAlbum);
                                musicBands.addFirst(fileMusicBand);
                                successfulDownloads += 1;
                            }
                            else {
                                nonSuccessfulDownloads += 1;
                            }
                        } catch (NumberFormatException numberFormatException) {
                            nonSuccessfulDownloads += 1;
                        }
                    }
                    System.out.println("Collection was loaded successfully. Elements loaded: " + successfulDownloads);
                    System.out.println("Amount of elements which contains invalid values and has not been loaded: "
                            + nonSuccessfulDownloads);
                    initializationDate = LocalDateTime.now();
                } catch (IOException | CsvValidationException ioException) {
                    System.out.println("CSV syntax error. Try again.");
                }
            }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Program will be finished now.");
            System.exit(0);
        }
    }

    /**
     * Class which check file is existed, and can be readable and writeable.
     * @return readiness status
     */
    public boolean checkFile(String env) {
        try {
            File checkingFile = new File(System.getenv(env));
            if (!checkingFile.exists()) {
                System.out.println("Path to file is not correct, try again." +
                        " I advise you to check the data entered into the CM1 environment variable.");
                System.exit(1);
                return false;
            }
            if (!checkingFile.canRead()) {
                System.out.println("File cannot be readable.");
                System.out.println("Keep this in mind, we advise you to exit the program using the command exit.");
                return false;
            }
            if (!checkingFile.canWrite()) {
                System.out.println("File cannot be writeable.");
                System.out.println("Keep this in mind, we advise you to exit the program using the command exit.");
                return false;
            }
            return true;
        } catch (NullPointerException nullPointerException) {
            System.out.println("The path to the file does not exist," +
                    " please enter it using the environment variable CM1.");
            System.out.println("Program was stopped successfully.");
            System.exit(1);
            return false;
        }
    }

    /** Method for adding element by using all receive-fields methods */
    public void add() {
        MusicBand newMusicBand = new MusicBand(receiveId(), receiveName(), returnDate(), receiveCoordinates(),
                receiveNumberOfParticipants(), receiveMusicGenre(), receiveBestAlbum());
        musicBands.addFirst(newMusicBand);
    }

    /**
     * Method for receiving x-coordinate of band(element)
     * @return long x
     */
    public long receiveX() {
        for ( ; ; ) {
            try {
                System.out.print("Enter x coordinate(this value cannot be empty): ");
                Scanner scanner = new Scanner(System.in);
                String x = scanner.nextLine();
                return Long.parseLong(x.trim());
            } catch (NumberFormatException numberFormatException) {
                System.out.println("This value must be a long-type number. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving y-coordinate of band(element)
     * @return Long y
     */
    public Long receiveY() {
        for ( ; ; ) {
            try {
                System.out.print("Enter y coordinate(this value cannot be empty): ");
                Scanner scanner = new Scanner(System.in);
                String y = scanner.nextLine();
                return Long.parseLong(y.trim());
            } catch (NumberFormatException numberFormatException) {
                System.out.println("This value must be a Long-type number. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    //a good option so that the code does not repeat itself
    /*public long receiveX() {
        return this.receiveCoordinate('x');
    }

    public Long receiveY() {
        return this.receiveCoordinate('y');
    }

    private long receiveCoordinate(char axis) {
        for ( ; ; ) {
            try {
                System.out.print("Enter " + axis + " coordinate(this value cannot be empty): ");
                Scanner scanner = new Scanner(System.in);
                String value = scanner.nextLine();
                return Long.parseLong(value);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("This value must be a float-type number. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }*/

    /** Method for making coordinates by using methods receiveX() and receiveY() */
    public Coordinates receiveCoordinates() {
        return new Coordinates(receiveX(), receiveY());
    }

    /**
     * Method for receiving best Album name of band(element)
     * @return String albumName
     */
    public String receiveBestAlbumName() {
        for ( ; ; ) {
            try {
                System.out.println("Warning: if album name will be so long, " +
                        "you may lose some part of this information.");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter a best Album name: ");
                String albumName = scanner.nextLine().trim();
                if (albumName.equals("")) {
                    System.out.println("This value cannot be empty. Try again");
                    continue;
                }
                if (albumName.length() <= 256) {
                    return albumName;
                }
                else {
                    System.out.println("Enter a value of 256 characters or less.");
                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be string. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving best Album sales of band(element)
     * @return long longSales
     */
    public Long receiveBestAlbumSales() {
        for ( ; ; ) {
            try {
                System.out.print("Enter best album sales(numbers only). Min value is 1 (Value cannot be empty): ");
                Scanner scanner = new Scanner(System.in);
                String sales = scanner.nextLine();
                long longSales = Long.parseLong(sales.trim());
                if (longSales < 1) {
                    System.out.println("Min value is 1. Try again.");
                    continue;
                }
                return longSales;
            } catch (NumberFormatException numberFormatException) {
                System.out.println("This value must be a long-type number. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    /** Method for making best album by using methods receiveBestAlbumName() and receiveBestAlbumSales() */
    public Album receiveBestAlbum() {
        return new Album(receiveBestAlbumName(), receiveBestAlbumSales());
    }

    /** Method for printing manual for user */
    public void help() {
        for (Map.Entry<String, String> entry : commandsInfo.entrySet()) {
            System.out.println(entry.getKey() + entry.getValue());
        }
    }

    /** Method for removing all elements from collection */
    public void clear() {
        musicBands.clear();
        System.out.println("Collection was cleaned successfully.");
    }

    /**
     * Method for receiving ID of band(element)
     * @return Long ID
     */
    public Long receiveId() {
        Long maxId = 0L;
        for (MusicBand musicBand : musicBands) {
            if (musicBand.getId() > maxId) {
                maxId = musicBand.getId();
            }
        }
        return maxId + 1;
    }

    /**
     * Method for receiving name of band(element)
     * @return String name
     */
    public String receiveName() {
        for ( ; ; ) {
            try {
                System.out.println("Warning: if band name will be so long, " +
                        "you may lose some part of this information.");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter a band name: ");
                String name = scanner.nextLine().trim();
                if (name.equals("")) {
                    System.out.println("This value cannot be empty. Try again.");
                    continue;
                }
                if (name.length() <= 256) {
                    return name;
                }
                else {
                    System.out.println("Enter a value of 256 characters or less.");
                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be string. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving number of participants of band(element)
     * @return String name
     */
    public long receiveNumberOfParticipants() {
        for (; ; ) {
            try {
                System.out.println("Warning: the number of participants must be greater than 0.");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter a number of participants: ");
                String numberOfParticipants = scanner.nextLine();
                long longNumberOfParticipants = Long.parseLong(numberOfParticipants.trim());
                if (longNumberOfParticipants < 1) {
                    System.out.println("This value must be greater than 0. Try again.");
                    continue;
                }
                return longNumberOfParticipants;
            } catch (NumberFormatException numberFormatException) {
                System.out.println("This value must be a long-type number. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    /** Method for printing information about the collection */
    public void info() {
        System.out.println("Type of collection: java.util.ArrayDeque");
        System.out.println("Initialization date: " + initializationDate);
        System.out.println("Amount of elements in the collection: " + musicBands.size());
    }

    /** Method for printing collection elements into the string representation */
    public void show() {
        if (musicBands.size() != 0) {
            for (MusicBand musicBand : musicBands) {
                System.out.println(musicBand.toString());
            }
        } else {
            System.out.println("There are no elements in the collection yet.");
        }
    }

    /**
     * Method for receiving music genre of band(element)
     * @return MusicGenre musicGenre
     */
    public MusicGenre receiveMusicGenre() {
        for ( ; ; ) {
            try {
                System.out.println("Select the genre of the group. " +
                        "Enter the number corresponding to the genre of the group.");
                System.out.print("Variants: 1 - ROCK, 2 - JAZZ, 3 - BLUES. Enter 1, 2 or 3: ");
                Scanner scanner = new Scanner(System.in);
                String genreNumber = scanner.nextLine();
                int intGenreNumber = Integer.parseInt(genreNumber.trim());
                //int genreNumber = scanner.nextInt();
                switch (intGenreNumber) {
                    case 1:
                        return MusicGenre.ROCK;
                    case 2:
                        return MusicGenre.JAZZ;
                    case 3:
                        return MusicGenre.BLUES;
                    default:
                        break;
                }
                System.out.println("You should to enter 1, 2 or 3. Try again.");
            } catch (NumberFormatException numberFormatException) {
                System.out.println("This value must be a number (1, 2 or 3). Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    /** Method for switching off the program */
    public void exit() {
        try {
            System.out.println("Program will be finished now.");
            TimeUnit.SECONDS.sleep(3);
            System.exit(0);
        } catch (InterruptedException interruptedException) {
            System.out.println("Program will be finished now.");
            System.exit(0);
        }
    }

    /** Method for printing current date in string representation */
    public String returnDate() {
        return LocalDateTime.now().toString();
    }

    /** Method for updating the band(element) by it`s ID */
    public void update(String id) {
        if (musicBands.size() != 0) {
            for (MusicBand musicBand : musicBands) {
                Long longId = musicBand.getId();
                String strId = String.valueOf(longId);
                if (strId.equals(id.trim())) {
                    musicBands.remove(musicBand);
                    MusicBand updatedPerson = new MusicBand(longId, receiveName(), musicBand.returnCreationDate(),
                            receiveCoordinates(),
                            receiveNumberOfParticipants(), receiveMusicGenre(), receiveBestAlbum());
                    musicBands.add(updatedPerson);
                    System.out.println("Element was updated successfully.");
                    return;
                }
            }
            System.out.println("Element with this ID is not defined. Try again.");
        }
        else {
            System.out.println("There are no elements in the collection yet.");
        }
    }

    /** Method for removing the band(element) by it`s ID */
    public void remove(String id) {
        if (musicBands.size() != 0) {
            for (MusicBand musicBand : musicBands) {
                Long longId = musicBand.getId();
                String strId = String.valueOf(longId);
                if (strId.equals(id.trim())) {
                    musicBands.remove(musicBand);
                    System.out.println("Element was deleted successfully.");
                    return;
                }
            }
            System.out.println("Element with this ID is not defined. Try again.");
        }
        else {
            System.out.println("There are no elements in the collection yet.");
        }
    }

    /** Method for adding band(element) to collection if it`s best album sales more than entered sales */
    public void add_if_max_sales(MusicBand contender) {
        if (musicBands.size() != 0) {
            long maxSales = Long.MIN_VALUE;
            for (MusicBand musicBand : musicBands) {
                if (musicBand.getBestAlbum().getSales() > maxSales) {
                    maxSales = musicBand.getBestAlbum().getSales();
                }
            }
            if (contender.getBestAlbum().getSales() > maxSales) {
                musicBands.add(contender);
                System.out.println("Element was added successfully. It's has the largest sales in this collection.");
            } else {
                System.out.println("Element wan not added to collection because its sales" +
                        " less than max element`s sales in the collection.");
            }
        }
        else {
            System.out.println("There are no elements in the collection yet." +
                    " Create something and then use this command.");
        }
    }

    /** Method for printing the first band(element) in the collection in string representation */
    public void head() {
        if (musicBands.size() != 0) {
            System.out.println(musicBands.getFirst());
        }
        else {
            System.out.println("There are no elements in the collection yet.");
        }
    }

    /** Method for printing the first band(element) in the collection in string representation and remove it */
    public void remove_head() {
        if (musicBands.size() != 0) {
            System.out.println(musicBands.removeFirst());
            System.out.println("First element deleted successfully.");
        }
        else {
            System.out.println("There are no elements in the collection yet.");
        }
    }

    /** Method for printing average of number of participants of bands(elements) */
    public void average_of_number_of_participants() {
        if (musicBands.size() != 0) {
            double sum_of_number_of_participants = 0;
            for (MusicBand musicBand : musicBands) {
                sum_of_number_of_participants += musicBand.getNumberOfParticipants();
            }
            System.out.println("Average value of number of participants = "
                    + sum_of_number_of_participants / musicBands.size());
        }
        else {
            System.out.println("There are no elements in the collection yet.");
        }
    }

    /** Method for counting amount of elements, which number of participants greater than entered number */
    public void count_greater_than_number_of_participants(String MyNumberOfParticipants) {
        if (musicBands.size() != 0) {
            int sum_of_bands = 0;
            long LongMyNumberOfParticipants = Long.parseLong(MyNumberOfParticipants.trim());
            for (MusicBand musicBand : musicBands) {
                if (musicBand.getNumberOfParticipants() > LongMyNumberOfParticipants) {
                    sum_of_bands += 1;
                }
            }
            System.out.println("The number of groups whose numberOfParticipants field value is greater than" +
                    " the received value = " + sum_of_bands);
        }
        else {
            System.out.println("There are no elements in the collection yet.");
        }
    }

    /** Roflan-method))) */
    public void group_counting_by_id(String id) {
        System.out.println("This command with a trap), because all id in the collection are unique," +
                " but thanks for using it, she got bored(");
    }

    /** Method for saving java collection to CSV-file */
    public void save() {
        if (checkFile(env)) {
            try (
                    Writer writer = Files.newBufferedWriter(Paths.get(System.getenv(env)));

                    CSVWriter csvWriter = new CSVWriter(writer,
                            CSVWriter.DEFAULT_SEPARATOR,
                            CSVWriter.NO_QUOTE_CHARACTER,
                            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                            CSVWriter.DEFAULT_LINE_END)
            ) {
                String[] headerRecord = {"id", "name", "x coordinate", "y coordinate", "creation date",
                        "number Of Participants", "genre", "best album name", "best album sales"};
                csvWriter.writeNext(headerRecord);
                for (MusicBand musicBand : musicBands) {
                    csvWriter.writeNext(new String[]{musicBand.getId().toString(), musicBand.getName()
                            , String.valueOf(musicBand.getCoordinates().getX())
                            , String.valueOf(musicBand.getCoordinates().getY()), musicBand.returnCreationDate()
                            , String.valueOf(musicBand.getNumberOfParticipants()), String.valueOf(musicBand.getGenre())
                            , musicBand.getBestAlbum().getName(), musicBand.getBestAlbum().getSales().toString()});
                }
                System.out.println("Collection saved successful.");
            } catch (IOException ioException) {
                System.out.println("CSV syntax error. Try again.");
            }
        }
        else {
            System.out.println("File error, you may not have permission to work with this file.");
        }
    }

    /** Method for executing script from external file */
    public void execute_script(String pathToFile) {
        try {
            System.out.println("Tip. To avoid recursion, your file cannot contain execute script commands.");
            BufferedReader reader = new BufferedReader(new FileReader(new File(pathToFile)));
            String[] finalUserCommand;
            String command;
            while((command = reader.readLine()) != null) {
                finalUserCommand = command.trim().toLowerCase().split(" ", 2);
                switch (finalUserCommand[0]) {
                    case "help":
                        help();
                        break;
                    case "info":
                        info();
                        break;
                    case "show":
                        show();
                        break;
                    case "add":
                        add();
                        break;
                    case "clear":
                        clear();
                        break;
                    case "exit":
                        exit();
                        break;
                    case "update":
                        update(finalUserCommand[1]);
                        break;
                    case "remove":
                        remove(finalUserCommand[1]);
                        break;
                    case "head":
                        head();
                        break;
                    case "remove_head":
                        remove_head();
                        break;
                    case "add_if_max_sales":
                        System.out.println("Enter characteristics of element, " +
                                "which will be compared with elements in collection.");
                        add_if_max_sales(new MusicBand(receiveId(), receiveName(), returnDate(), receiveCoordinates(),
                                receiveNumberOfParticipants(), receiveMusicGenre(), receiveBestAlbum()));
                        break;
                    case "average_of_number_of_participants":
                        average_of_number_of_participants();
                        break;
                    case "group_counting_by_id":
                        group_counting_by_id(finalUserCommand[1]);
                        break;
                    case "save":
                        save();
                        break;
                    case "count_greater_than_number_of_participants":
                        count_greater_than_number_of_participants(finalUserCommand[1]);
                        break;
                    default:
                        reader.readLine();
                        break;
                }
                System.out.println("Command is ended.");
            }
            System.out.println("Commands are ended.");
            reader.close();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found. Try again.");
        } catch (IOException ioException) {
            System.out.println("File reading exception. Try again.");
        }
    }

}
