package exceptions;

import core.Flags;

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
        super(System.lineSeparator() + Flags.getString("exception.plugin") + message + Flags.getString("exception.plugin.installed"));
    }
}
