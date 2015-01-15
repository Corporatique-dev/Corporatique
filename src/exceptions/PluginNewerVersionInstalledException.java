package exceptions;

/**
 * Will be triggered if the plugin is already installed.
 * 
 * @author Fati CHEN
 * @version 1.0.0
 */
public class PluginNewerVersionInstalledException extends Exception {
    /**
     * @param message the plugin concerned
     */
    public PluginNewerVersionInstalledException(String message) {
        super(System.lineSeparator() + "The plugin" + message + " has a newer version installed");
    }
}
