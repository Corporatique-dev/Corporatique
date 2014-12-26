package exceptions;

/**
 * Will be triggered if the plugin dependencies are not present.
 * 
 * @author Fati CHEN
 * @version 1.0.0
 */
public class PluginDependenciesNotPresentException extends Exception {
    public PluginDependenciesNotPresentException(String message) {
        super(System.lineSeparator() + "The dependencies of the plugin " + message + " are not present");
    }
}
