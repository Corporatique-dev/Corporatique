package exceptions;

/**
 * Will be triggered if the format given by the user isn't present in the properties.
 *
 * @author Fati CHEN
 * @version 1.0.0
 */
public class FormatNotFoundException extends Exception {
    /**
     * @param message the format which is not supported
     */
    public FormatNotFoundException(String message) {
        super("The format is not supported " + message);
    }
}
