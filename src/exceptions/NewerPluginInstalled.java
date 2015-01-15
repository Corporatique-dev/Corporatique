package exceptions;

/**
 * Will be triggered if installed plugin is newer or the same as the proposed one.
 *
 * @author Fati CHEN
 * @version 1.0.0
 */
public class NewerPluginInstalled Exception {
    /**
     * @param message the plugin concerned
     */
    public NewerPluginInstalled(String message) {
        super(System.lineSeparator() + "The plugin" + message + " has a newer version installed");
    }
}
