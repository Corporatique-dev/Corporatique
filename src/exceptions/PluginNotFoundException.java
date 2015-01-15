package exceptions;

import core.Flags;

/**
 * Will be triggered if the given plugin isn't installed.
 *
 * @author Fati CHEN
 * @version 1.0.0
 */
public class PluginNotFoundException extends Exception {
    /**
     * @param message the name of the concerned plugin
     */
    public PluginNotFoundException(String message) {
        super(System.lineSeparator() + Flags.getString("exception.plugin") + message + Flags.getString("exception.plugin.notfound"));
    }
}
