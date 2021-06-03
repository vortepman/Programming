package given;

import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import com.opencsv.CSVWriter;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

/**
 * @author Petrov Ilya
 * @version 1.0
 * Class for parsing CSV file and save it
 */
public class CollectionSupervisor {

    /**
     * Environment variable initialization
     */
    String env = "CM1";
    /**
     * ArrayDeque for keeping a collection as java-object
     */
    private final ArrayDeque<given.MusicBand> musicBands = new ArrayDeque<>();
    /**
     * HashMap collection for making a manual
     */
    private final HashMap<String, String> informationAboutCommands;
    /** Set for storing paths to files for command 'execute_script' */
    private static Set<String> paths = new HashSet<>();
    /**
     * Field for saving date of initialization the collection
     */
    private LocalDateTime initializationDate;

    String pathToFile;

    // Constructor for checking a path to file existence and file readiness to work
    public CollectionSupervisor() {
        try {
//            Scanner scanner = new Scanner(System.in);
//            System.out.print("Enter path to file: ");
//            pathToFile = scanner.nextLine();
            if (FileCheck(env)) {
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
                            } else {
                                nonSuccessfulDownloads += 1;
                            }
                        } catch (IllegalArgumentException numberFormatException) {
                            nonSuccessfulDownloads += 1;
                        }
                    }
                    System.out.println("Your collection has been loaded without problems");
                    System.out.println("Elements loaded: " + successfulDownloads);
                    System.out.println("Amount of elements which contains invalid values and has not been loaded: "
                            + nonSuccessfulDownloads);
                    initializationDate = LocalDateTime.now();
                } catch (IOException | CsvValidationException ioException) {
                    System.out.println("CSV syntax error. Try again.");
                }
            }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Now the program will stop working.");
            System.exit(0);
        }
    }

    /** Method for saving java collection to CSV-file */
    public void save() {
        if (FileCheck(env)) {
            try (
                    FileWriter writer = new FileWriter(System.getenv(env));

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

    /**
     * Class which check file is existed, and can be readable and writeable.
     * @return readiness status
     */
    public boolean FileCheck(String env) {
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
                System.out.println("Keep this in mind, we advise you to exit the program using the command exit. " +
                        "Don't forget about CM1 environment variable");
                return false;
            }
            if (!checkingFile.canWrite()) {
                System.out.println("File cannot be writeable.");
                System.out.println("Keep this in mind, we advise you to exit the program using the command exit. " +
                        "Don't forget about CM1 environment variable");
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

    {

        // Making a manual
        informationAboutCommands = new HashMap<>();
        informationAboutCommands.put("[help]", " - display help for available commands");
        informationAboutCommands.put("[info]", " - display information about the collection" +
                " (type, date of initialization, number of elements, etc.) to the standard output stream");
        informationAboutCommands.put("[show]", " - print to standard output all elements of the collection in string representation");
        informationAboutCommands.put("[add {element}]", " - add new element to the collection");
        informationAboutCommands.put("[update id {element}]", " - update the value of the collection element" +
                " whose id is equal to the given. After entering the command," +
                " you must enter the ID separated by a space. For example: update 1");
        informationAboutCommands.put("[remove id {element}]", " - remove an element from the collection by its ID." +
                " After entering the command, you must enter the ID separated by a space. For example: remove_by_id 1");
        informationAboutCommands.put("[clear]", " - clear the collection");
        informationAboutCommands.put("[execute_script file_name]", " - read and execute a script from specified file." +
                " You should to enter path to file after entering a command.");
        informationAboutCommands.put("[exit]", " - end the program (without saving to file)");
        informationAboutCommands.put("[head]", " - display the first element of the collection");
        informationAboutCommands.put("[remove_head]", " - display the first element of the collection and remove it");
        informationAboutCommands.put("[add_if_max_sales]", " - add new element to the collection, if it`s best album sales more, " +
                " than the sales of the largest element in this collection. You should to enter characteristics of" +
                " comparing element after entering a command.");
        informationAboutCommands.put("[average_of_number_of_participants]", " - display the average value of the field" +
                " numberOfParticipants for all elements of the collection");
        informationAboutCommands.put("[count_greater_than_number_of_participants numberOfParticipants]", " - display the number of" +
                " elements, the value of the field numberOfParticipants of which is greater than the specified");
    }

    public ArrayDeque<MusicBand> getMusicBands() {
        return musicBands;
    }

    public HashMap<String, String> getInformationAboutCommands() {
        return informationAboutCommands;
    }

    public LocalDateTime getInitializationDate() {
        return initializationDate;
    }

    public static Set<String> getPaths() {
        return paths;
    }

    public static void setPaths(Set<String> paths) {
        CollectionSupervisor.paths = paths;
    }

}