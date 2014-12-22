package Exceptions;

/**
 * Created by stardisblue on 21/12/2014.
 */
public class PluginDependenciesNotPresentException extends Exception {
    public PluginDependenciesNotPresentException(String message) {
        super(System.lineSeparator() + "The dependencies of the plugin " + message + " are not present");
    }
}
