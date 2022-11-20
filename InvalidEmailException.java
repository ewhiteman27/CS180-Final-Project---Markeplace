/**
 * Project 4 - InvalidEmailException
 *
 * Exception to be thrown when a user attempts to create an account
 * with an email address that is already taken or is otherwise invalid
 *
 * @author Ethan Whiteman, lab sec L21
 * @version 11/6/2022
 */

public class InvalidEmailException extends Exception {
    public InvalidEmailException(String message) {
        super(message);
    }
}
