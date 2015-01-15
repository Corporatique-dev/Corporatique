package exceptions;

/**
 * Will be triggered if the plugin is already installed.
 * 
 * @author Fati CHEN
 * @version 1.0.0
 */
public class PluginIsInstalledException extends Exception {
    /**
     * @param message the plugin concerned
     */
    public PluginIsInstalledException(String message) {
        super(System.lineSeparator() + "The plugin " + message + " is already installed");
    }
}
