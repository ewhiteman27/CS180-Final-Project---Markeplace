import java.io.*;
import java.util.ArrayList;

/**
 * Project 4 - User
 *
 * Class for user account storage and creation
 * Includes methods used for facilitating log in and log out, account
 * creation, and reading and saving account information to and from a file
 *
 * @author Ethan Whiteman, lab sec L21
 * @version 11/8/2022
 */

public class User {
    private String username; // the unique username associated with the user's account. not case-sensitive
    private String email; // the unique email associated with the user's account. not case-sensitive
    private String password; // the password associated with the user's account. case-sensitive
    private int accountType; // an int indicating if the account is a buyer or a seller, 1 = buyer, 2 = seller

    // constructors
    private User(String username , String email , String password , int accountType) {
        /**
         * initializes each field to its corresponding given parameter.
         * this constructor can only be called from within createAccount
         * or logIn method. use blank constructor before using createAccount
         * or logIn method in order to access this constructor
         */

        this.username = username;
        this.email = email;
        this.password = password;
        this.accountType = accountType;
    }

    public User() {
        /**
         * intializes each field to null or 0. USE THIS BEFORE USING createNewAccount or logIn
         */
        this.username = null;
        this.email = null;
        this.password = null;
        this.accountType = 0;
    }

    public User createAccount(String username, String email, String password, int accountType)
            throws InvalidUsernameException, InvalidEmailException, InvalidPasswordException, IOException {
        /**
         * First confirms that the parameters given for the account details do not match
         * an already existing account. If the account details do not match an already
         * existing account, calls User constructor and returns the new User object and
         * writes the new account to the file. Otherwise, throws the corresponding exception.
         */

        ArrayList<String> accounts = getAccounts();
        for (int i = 0; i < accounts.size(); i++) { // goes through each account to see if the username or password
            String[] account = accounts.get(i).split(";"); // matches that of an already existing account.
            if (username.equalsIgnoreCase(account[0])) { // throws corresponding exception if necessary.
                throw new InvalidUsernameException("This username is already taken. " +
                        "Please enter a different username.");
            } else if (username.equals(account[1])) {
                throw new InvalidEmailException("There is already an account associated with this email. " +
                        "Please enter a different email.");
            }
        }

        if (!(password.length() >= 8)) { // checks if given password is at least eight characters
            throw new InvalidPasswordException("The account password must be at least 8 characters " +
                    "long. Please enter a different password.");
        }

        writeNewAccount(accounts , username , email , password , accountType);
        return new User(username , email , password , accountType);
    }

    public User logIn(String username , String password) throws InvalidLogInException, IOException {
        /**
         * looks through list of accounts and, if any account has a matching username
         * and password to those provided, returns a User object with the attributes
         * of the one matching the username and password given as parameters
         */

        ArrayList<String> accounts = getAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            String[] account = accounts.get(i).split(";");
            if (account[0].equalsIgnoreCase(username) && account[2].equals(password)) {
                return new User(account[0] , account[1] , account[2] , Integer.parseInt(account[3]));
            }
        }
        throw new InvalidLogInException("The username and password entered is invalid.");
    }

    private ArrayList<String> getAccounts() throws IOException {
        /**
         * reads the accounts.txt file and returns an arraylist
         * with the information of every user account in the file.
         */

        File f = new File("accounts.txt");
        ArrayList<String> accounts = new ArrayList<String>();
            f.createNewFile();
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                if (line.split(";").length == 4) { // checks if the current line is an account with a valid format
                    accounts.add(line);
                }
                line = bfr.readLine();
            }
            bfr.close();
            fr.close();
        return accounts;
    }

    private void writeNewAccount(ArrayList<String> accounts , String username
            , String email , String password , int accountType) {
        /**
         * writes the account given into the accounts.txt file.
         * used in createAccount method
         */

        File f = new File("accounts.txt");
        try {
            FileWriter fw = new FileWriter(f , false);
            PrintWriter pw = new PrintWriter(fw);
            for (int i = 0; i < accounts.size(); i++) { // writes all previously existing accounts
                pw.println(accounts.get(i));
            }
            pw.printf("%s;%s;%s;%d\n" , username , email , password , accountType); // writes new account
            pw.close();
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeAccounts(ArrayList<String> accounts) {
        /**
         * updates the accounts.txt file to reflect the most recent account changes
         */

        File f = new File("accounts.txt");
        try {
            FileWriter fw = new FileWriter(f , false);
            PrintWriter pw = new PrintWriter(fw);
            for (int i = 0; i < accounts.size(); i++) { // writes all previously existing accounts
                pw.println(accounts.get(i));
            }
            pw.close();
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean changeUsername(String newUsername , String password , String email) throws IOException {
        /**
         * changes the username associated with the account to a new username.
         * the given new username must not be already taken and the user must
         * verify their email and password in order to change the username.
         * returns true if the username is successfully updated and false otherwise.
         */

        ArrayList<String> accounts = getAccounts();
        if (this.password.equals(password) && this.email.equalsIgnoreCase(email)) {
            for (int i = 0; i < accounts.size(); i++) {
                String[] account = accounts.get(i).split(";");
                if (newUsername.equalsIgnoreCase(account[0])) {
                    return false;
                }
            }
            this.username = newUsername;
            for (int i = 0; i < accounts.size(); i++) {
                String[] account = accounts.get(i).split(";");
                if (this.email.equalsIgnoreCase(account[1]) && this.password.equals(account[2])) {
                    accounts.set(i , String.format("%s;%s;%s;%d" , newUsername , email , password , accountType));
                }
            }
            writeAccounts(accounts);
            return true;
        }
        return false;
    }

    public boolean changeEmail(String newEmail , String username , String password) throws IOException {
        /**
         * changes the email associated with the account to a new email address.
         * the given new email must not be already in use and the user must
         * verify their username and password in order to change the email.
         * returns true if the email is successfully updated and false otherwise.
         */

        ArrayList<String> accounts = getAccounts();
        if (this.password.equals(password) && this.username.equalsIgnoreCase(username)) {
            for (int i = 0; i < accounts.size(); i++) {
                String[] account = accounts.get(i).split(";");
                if (newEmail.equalsIgnoreCase(account[1])) {
                    return false;
                }
            }
            this.email = newEmail;
            for (int i = 0; i < accounts.size(); i++) {
                String[] account = accounts.get(i).split(";");
                if (this.username.equalsIgnoreCase(account[0]) && this.password.equals(account[2])) {
                    accounts.set(i , String.format("%s;%s;%s;%d" , username , newEmail , password , accountType));
                }
            }
            writeAccounts(accounts);
            return true;
        }
        return false;
    }

    public boolean changePassword(String newPassword , String username , String email)
            throws InvalidPasswordException, IOException {
        /**
         * changes the password associated with the account to a new password.
         * the given new email must not be already in use and the user must
         * verify their username and email in order to change the password.
         * returns true if the password is successfully updated and false otherwise.
         */

        ArrayList<String> accounts = getAccounts();
        if (!this.password.equals(newPassword) && this.username.equalsIgnoreCase(username)
                && this.email.equalsIgnoreCase(email)) {
            if (newPassword.length() < 8) {
                throw new InvalidPasswordException("Password must be more than 8 characters");
            }
            this.password = newPassword;
            for (int i = 0; i < accounts.size(); i++) {
                String[] account = accounts.get(i).split(";");
                if (this.username.equalsIgnoreCase(account[0]) && this.email.equalsIgnoreCase(account[1])) {
                    accounts.set(i , String.format("%s;%s;%s;%d" , username , email , newPassword , accountType));
                }
            }
            writeAccounts(accounts);
            return true;
        }
        return false;
    }

    public boolean deleteAccount(String username , String password) throws IOException {
        /**
         * deletes the account associated with this User object by setting
         * all fields to null and removing the account from the accounts.txt file.
         * requires username and password verification in order for account to be deleted
         * returns true if the account is successfully deleted and false otherwise
         */

        ArrayList<String> accounts = getAccounts();
        if (this.username.equalsIgnoreCase(username) && this.password.equals(password)) {
            for (int i = 0; i < accounts.size(); i++) {
                String[] account = accounts.get(i).split(";");
                if (username.equalsIgnoreCase(account[0]) && password.equalsIgnoreCase(account[2])) {
                    accounts.remove(i);
                    this.username = null;
                    this.email = null;
                    this.password = null;
                    this.accountType = 0;
                    writeAccounts(accounts);
                    return true;
                }
            }
        }
        return false;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getAccountType() {
        return accountType;
    }
}
