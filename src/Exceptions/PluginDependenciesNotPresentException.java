package Exceptions;

/**
 * Created by stardisblue on 21/12/2014.
 */
public class PluginDependenciesNotPresentException extends Exception {
    public PluginDependenciesNotPresentException() {
        System.err.println("The dependencies of this plugin are not present");
    }
}
