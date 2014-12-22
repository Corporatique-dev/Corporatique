package exceptions;

/**
 * @author Fati CHEN
 * @version 1.0.0
 */
public class PluginIsInstalledException extends Exception {
    public PluginIsInstalledException(String message) {
        super(System.lineSeparator() + "The plugin " + message + " is already installed");
    }
}
