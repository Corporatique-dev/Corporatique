package exceptions;

import core.Flags;

/**
 * Will be triggered if installed plugin is newer or the same as the proposed one.
 *
 * @author Fati CHEN
 * @version 1.0.0
 */
public class PluginIsNewer extends Exception {
    /**
     * @param message the plugin concerned
     */
    public PluginIsNewer(String message) {
        super(System.lineSeparator() + Flags.getString("exception.plugin") + message + Flags.getString("exception.plugin.version"));
    }
}
