/**
 * Project 4 - InvalidUsernameException
 *
 * Exception to be thrown when a user attempts to log in to an account that does not exist
 *
 * @author Ethan Whiteman, lab sec L21
 * @version 11/6/2022
 */

public class InvalidLogInException extends Exception {
    public InvalidLogInException(String message) {
        super(message);
    }
}
