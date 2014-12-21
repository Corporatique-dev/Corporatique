package Exceptions;

/**
 * Created by stardisblue on 21/12/2014.
 */
public class PluginSpecsNotFoundException extends Exception {
    public PluginSpecsNotFoundException() {
        System.err.println("The Pluginspecs annotation not found");
    }

}
