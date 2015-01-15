package exceptions;

import core.Flags;

/**
 * Will be triggered if the given path of the plugin during the installation isn't a jar file.
 *
 * @author Fati CHEN
 * @version 1.0.0
 */
public class IsNotJarException extends Exception {
    /**
     * @param message the path to the file which isn't a .jar.
     */
    public IsNotJarException(String message) {
        super(System.lineSeparator() + Flags.getString("exception.path") + message + Flags.getString("exception.path.jar"));
    }
}
