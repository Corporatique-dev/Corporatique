package Exceptions;

/**
 *
 *
 *@author Fati CHEN
 *@version 1.0.0
 */
public class PluginIsInstalledException extends Exception {
    public PluginIsInstalledException() {
        System.err.println("The plugin is already installed");
    }
}
