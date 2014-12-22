package Exceptions;

/**
 * Created by stardisblue on 22/12/2014.
 */
public class IsNotFileException extends Exception {
    public IsNotFileException(String message) {
        super(System.lineSeparator() + "The given path of the plugin " + message + " is not a file");
    }
}
