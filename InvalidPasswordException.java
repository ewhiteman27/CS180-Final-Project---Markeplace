/**
 * Project 4 - InvalidPasswordException
 *
 * Exception to be thrown when a user attempts to create an account with
 * a password that is less than 8 characters long or is otherwise invalid
 *
 * @author Ethan Whiteman, lab sec L21
 * @version 11/6/2022
 */

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String message) {
        super(message);
    }
}