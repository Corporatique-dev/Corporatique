package exceptions;

/**
 * Created by stardisblue on 22/12/2014.
 */
public class IsNotJarException extends Exception {
    public IsNotJarException(String message) {
        super(System.lineSeparator() + "The given path to the plugin " + message + " is not a jar");
    }
}
