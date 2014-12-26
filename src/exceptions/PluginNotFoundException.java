package exceptions;

/**
 * Created by stardisblue on 24/12/2014.
 */
public class PluginNotFoundException extends Exception {
    public PluginNotFoundException(String message) {
        super("The Plugin " + message + " was not found, make sure to install it using install");
    }
}
