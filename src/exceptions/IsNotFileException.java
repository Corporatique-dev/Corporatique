package exceptions;

import core.Flags;

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
        super(System.lineSeparator() + Flags.getString("exception.path") + message + Flags.getString("exception.path.file"));
    }
}
