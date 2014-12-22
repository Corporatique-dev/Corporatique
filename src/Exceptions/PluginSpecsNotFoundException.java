package exceptions;

/**
 * Created by stardisblue on 21/12/2014.
 */
public class PluginSpecsNotFoundException extends Exception {
    public PluginSpecsNotFoundException(String message) {
        super(System.lineSeparator() + "The Pluginspecs annotation not found in" + message);
    }
}
