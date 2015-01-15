package exceptions;

/**
 * Will be triggered if the plugin specifications aren't given.
 * 
 * @author Fati CHEN
 * @version 1.0.0
 */
public class PluginSpecsNotFoundException extends Exception {
    /**
     * @param message name of the concerned plugin
     */
	public PluginSpecsNotFoundException(String message) {
        super(System.lineSeparator() + "The Pluginspecs annotation not found in" + message);
    }
}
