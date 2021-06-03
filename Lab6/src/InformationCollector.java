import given.*;
import java.util.*;

/**
 * Class for receiving values of fields for collections elements
 * @author Petrov Ilya
 * @version 1.0
 */
public class InformationCollector {

    /**
     * Method for receiving x-coordinate of band(element)
     * @return long x
     */
    public long CollectX() {
        for ( ; ; ) {
            try {
                System.out.print("Input x coordinate(this value can't be empty): ");
                Scanner scanner = new Scanner(System.in);
                String x = scanner.nextLine();
                return Long.parseLong(x.trim());
            } catch (NumberFormatException numberFormatException) {
                System.out.println("This value must be a long-type number. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Now the program will stop working.");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving y-coordinate of band(element)
     * @return Long y
     */
    public Long CollectY() {
        for ( ; ; ) {
            try {
                System.out.print("Input y coordinate(this value cannot be empty): ");
                Scanner scanner = new Scanner(System.in);
                String y = scanner.nextLine();
                return Long.parseLong(y.trim());
            } catch (NumberFormatException numberFormatException) {
                System.out.println("This value must be a Long-type number. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Now the program will stop working.");
                System.exit(1);
            }
        }
    }

    /** Method for making coordinates by using methods receiveX() and receiveY() */
    public Coordinates CollectCoordinates() {
        return new Coordinates(CollectX(), CollectY());
    }

    /**
     * Method for receiving best Album name of band(element)
     * @return String albumName
     */
    public String CollectBestAlbumName() {
        for ( ; ; ) {
            try {
                System.out.println("Warning: if album name will be so long, " +
                        "you may lose some part of this information.");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Input a best Album name: ");
                String albumName = scanner.nextLine().trim();
                if (albumName.equals("")) {
                    System.out.println("This value cannot be empty. Try again");
                    continue;
                }
                if (albumName.length() <= 256) {
                    return albumName;
                }
                else {
                    System.out.println("Input a value of 256 characters or less.");
                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be string. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Now the program will stop working.");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving best Album sales of band(element)
     * @return long longSales
     */
    public Long CollectBestAlbumSales() {
        for ( ; ; ) {
            try {
                System.out.print("Enter best album sales(numbers only). Min value is 1 (Value can't be empty): ");
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
                System.out.println("Now the program will stop working.");
                System.exit(1);
            }
        }
    }

    /** Method for making best album by using methods receiveBestAlbumName() and receiveBestAlbumSales() */
    public Album CollectBestAlbum() {
        return new Album(CollectBestAlbumName(), CollectBestAlbumSales());
    }

    public long CollectId() {
        for ( ; ; ) {
            try {
                int randomID;
                Random random = new Random();
                randomID = random.nextInt(Integer.MAX_VALUE);
                return (long) randomID;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be Long-type of number. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Now the program will stop working.");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving name of band(element)
     * @return String name
     */
    public String CollectName() {
        for ( ; ; ) {
            try {
                System.out.println("Warning: if band name will be so long, " +
                        "you may lose some part of this information.");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Input a band name: ");
                String name = scanner.nextLine().trim();
                if (name.equals("")) {
                    System.out.println("This value can't be empty. Try again.");
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
                System.out.println("Now the program will stop working.");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving music genre of band(element)
     * @return MusicGenre musicGenre
     */
    public MusicGenre CollectMusicGenre() {
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
                System.out.println("Now the program will stop working.");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving number of participants of band(element)
     * @return String name
     */
    public long CollectNumberOfParticipants() {
        for (; ; ) {
            try {
                System.out.println("Warning: the number of participants must be greater than 0.");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Input a number of participants: ");
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
                System.out.println("Now the program will stop working.");
                System.exit(1);
            }
        }
    }

}
