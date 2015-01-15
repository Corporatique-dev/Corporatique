package exceptions;

import core.Flags;

/**
 * Will be triggered if the directory could not be created.
 *
 * @author Fati CHEN
 * @version 1.0.0
 */
public class FolderCreationException extends Exception {
    /**
     * @param message path to the concerned directory
     */
    public FolderCreationException(String message) {
        super(System.lineSeparator() + Flags.getString("exception.creation") + message + Flags.getString("exception.creation2"));
    }
}
