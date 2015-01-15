package exceptions;

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
        super(System.lineSeparator() + "The given path to the plugin " + message + " is not a jar");
    }
}
