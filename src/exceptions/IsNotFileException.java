package exceptions;

/**
 * Will be triggered if the path of the given file to process is not a file.
 *
 * @author Fati CHEN
 * @version 1.0.0
 */
public class IsNotFileException extends Exception {
    /**
     * @param message path which is not a file
     */
    public IsNotFileException(String message) {
        super(System.lineSeparator() + "The given path " + message + " is not a file");
    }
}
