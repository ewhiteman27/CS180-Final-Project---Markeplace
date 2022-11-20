/**
 * Project 4 - InvalidCardException
 *
 * Exception to be thrown when a user attempts to input
 * a card number incorrectly
 *
 * @author Saujin Park
 * @version 1.0
 */
public class InvalidCardException extends Exception {
    public InvalidCardException(String message) {
        super(message);
    }
}
