import given.DatabaseSupervisor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class for working with user data
 * @author Petrov Ilya
 * @version 1.0
 */
public class WorkWithUser {
    /**
     * Variable for storing the user's decision: register or log in
     */
    private static String userDecision = "";
    /**
     * Variable for storing the user's name
     */
    private static String username = "";
    /**
     * Establishing a connection to the database
     */
    private static Connection c = DatabaseSupervisor.getConnection();

    /** Default constructor */
    public WorkWithUser() {}

    /** method for the client to log into his account */
    public static String logging() throws SQLException {
        ArrayDeque<String> users = new ArrayDeque<>();
        Scanner inputValue = new Scanner(System.in);
        //Connection c = DatabaseSupervisor.getConnection();
        while (true) {
            try {
                System.out.println("Are you already registered? If yes, then enter 'sign_in', otherwise enter 'sign_up'.");
                System.out.println("If you want to exit from the program, then enter 'exit'.");
                System.out.print("Enter a decision: ");
                userDecision = inputValue.nextLine().trim();
                if (userDecision.equals("sign_in")) {
                    while (true) {
                        System.out.println("If you want to go back, enter 'back'.");
                        System.out.print("Enter your username: ");
                        username = inputValue.nextLine().trim();

                        if (username.equals("")) {
                            while (username.equals("")) {
                                System.out.println("This value can't be empty. Try again.");
                                System.out.print("Enter your username: ");
                                username = inputValue.nextLine().trim();
                            }
                        }

                        PreparedStatement ps = c.prepareStatement("select * from users");
                        ResultSet rs = ps.executeQuery();

                        users.clear();
                        while (rs.next()) {
                            users.add(rs.getString("user_name"));
                        }
                        int check = 0;
                        if (!users.isEmpty()) {
                            for (String s : users) {
                                if (s.equals(username)) {
                                    check = 1;
                                    break;
                                }
                            }
                        }
                        if (check == 1) {
                            String password = WorkWithUser.workingWithPassword();
                            if (password.equals("back")) {
                                continue;
                            }
                            return username;
                        } else if (!username.equals("back")) {
                            System.out.println("This username doesn't exist. Please, try again.");
                        } else {
                            break;
                        }
                    }
                } else if (userDecision.equals("sign_up")) {
                    while (true) {
                        System.out.print("Welcome to the app for creating your own music collection!" + "\n" + "If you want to go back, enter 'back'." + "\n" + "Create and enter " +
                                "your username: ");
                        username = inputValue.nextLine().trim();

                        if (username.equals("")) {
                            while (username.equals("")) {
                                System.out.println("This value can't be empty. Try again.");
                                System.out.print("Create and enter your username: ");
                                username = inputValue.nextLine().trim();
                            }
                        }

                        PreparedStatement ps = c.prepareStatement("select * from users");
                        ResultSet rs = ps.executeQuery();
                        users.clear();
                        while (rs.next()) {
                            users.add(rs.getString("user_name"));
                        }
                        int check = 0;
                        if (!users.isEmpty()) {
                            for (String s : users) {
                                if (!s.equals(username)) {
                                    check = 1;
                                } else {
                                    check = 0;
                                    break;
                                }
                            }
                        } else {
                            check = 1;
                        }

                        if (check == 1 && !username.equals("back")) {
                            String password = WorkWithUser.workingWithPassword();
                            if (password.equals("back")) {
                                continue;
                            }
                            PreparedStatement pS = c.prepareStatement("insert into users (user_name, password) values (?, ?)");
                            pS.setString(1, username);
                            pS.setString(2, password);
                            pS.executeUpdate();

                            System.out.println("Great, now you are registered as " + "[" + username + "]");
                            //return username;
                            break;
                        } else if (!username.equals("back") && check == 0) {
                            System.out.println("This name already exists. Please, try again.");
                        } else {
                            break;
                        }
                    }
                } else if (userDecision.equals("exit")) {
                    String exit = "Now the program will stop working.";
                    System.exit(0);
                    return exit;
                    //break;
                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be string. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Now the program will stop working.");
                System.exit(1);
            }
        }
    }

    /** method for hashing the input value */
    public static String hashing(String input) {
        try {
            // getInstance() method is called with algorithm SHA-384
            MessageDigest md = MessageDigest.getInstance("SHA-384");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            // return the HashText
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /** method for working with user password */
    public static String workingWithPassword() throws SQLException {
        Scanner inputValue2 = new Scanner(System.in);
        String usersPassword = "";
        String verifiablePassword = "";
        while (!usersPassword.equals("back")) {
            if (userDecision.equals("sign_in")) {
                while (true) {
                    System.out.println("If you want to go back, enter 'back'.");
                    System.out.print("Enter your password: ");
                    usersPassword = inputValue2.nextLine().trim();

                    if (usersPassword.equals("")) {
                        while (usersPassword.equals("")) {
                            System.out.println("This value can't be empty. Try again.");
                            System.out.print("Enter your password: ");
                            usersPassword = inputValue2.nextLine().trim();
                        }
                    }


                    PreparedStatement preparedStatement = c.prepareStatement("select password from users where user_name = ?");
                    preparedStatement.setString(1, username);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        verifiablePassword = resultSet.getString("password");
                    }
                    int check2 = 0;
                    if (WorkWithUser.hashing(usersPassword).equals(verifiablePassword)) {
                        check2 = 1;
                    }
                    if (check2 == 1) {
                        return verifiablePassword;
                    } else if (!usersPassword.equals("back")) {
                        System.out.println("Incorrect password entered. Please, try again.");
                    } else {
                        break;
                    }
                }
            } else if (userDecision.equals("sign_up")) {
                while (true) {
                    System.out.println("If you want to go back, enter 'back'.");
                    System.out.print("Create your password(Attention! All spaces will be removed and password must contain at least 8 characters)): ");
                    usersPassword = inputValue2.nextLine().trim();

                    if (usersPassword.length() < 8 && !usersPassword.equals("back")) {
                        while (usersPassword.length() < 8) {
                            System.out.println("Password must contain at least a 8 characters. Try again.");
                            System.out.print("Create your password(Attention! All spaces will be removed and password must contain at least 8 characters): ");
                            usersPassword = inputValue2.nextLine().trim().replaceAll(" ", "");
                            if (usersPassword.equals("back")) {
                                return usersPassword;
                            }
                        }
                    } else if (usersPassword.equals("back")) {
                        return usersPassword;
                    }

                    System.out.print("Please duplicate your password: ");
                    String usersPassword2 = "";
                    usersPassword2 = inputValue2.nextLine().trim();

                    if (usersPassword2.equals("")) {
                        while (usersPassword2.equals("")) {
                            System.out.println("This value can't be empty. Try again.");
                            System.out.print("Duplicate your password: ");
                            usersPassword2 = inputValue2.nextLine().trim().replaceAll(" ", "");
                        }
                    }

                    if (usersPassword.equals(usersPassword2)) {
                        return WorkWithUser.hashing(usersPassword);
                    } else {
                        System.out.println("The entered password doesn't match the previously entered password! Please, try again.");
                    }
                }

            }
        }
        return usersPassword;
    }

}
