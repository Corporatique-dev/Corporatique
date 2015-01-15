package exceptions;

import core.Flags;

/**
 * Will be triggered if the plugin dependencies are not present.
 *
 * @author Fati CHEN
 * @version 1.0.0
 */
public class PluginDependenciesNotPresentException extends Exception {
    /**
     * @param message the plugin concerned
     */
    public PluginDependenciesNotPresentException(String message) {
        super(System.lineSeparator() + Flags.getString("exception.plugin") + message + Flags.getString("exception.plugin.dependency"));
    }
}
