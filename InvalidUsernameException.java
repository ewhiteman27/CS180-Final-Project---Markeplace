/**
 * Project 4 - InvalidUsernameException
 *
 * Exception to be thrown when a user attempts to create an account
 * with a username that is already taken or is otherwise invalid
 *
 * @author Ethan Whiteman, lab sec L21
 * @version 11/6/2022
 */

public class InvalidUsernameException extends Exception {
    public InvalidUsernameException(String message) {
        super(message);
    }
}
