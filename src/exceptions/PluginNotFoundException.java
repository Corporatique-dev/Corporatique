package exceptions;

/**
 * Will be triggered if the given plugin isn't installed.
 * 
 * @author Fati CHEN
 * @version 1.0.0
 */
public class PluginNotFoundException extends Exception {
    public PluginNotFoundException(String message) {
        super("The Plugin " + message + " was not found, make sure to install it using install");
    }
}
