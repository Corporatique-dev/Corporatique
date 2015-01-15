package core;

import exceptions.PluginDependenciesNotPresentException;
import exceptions.PluginNotFoundException;
import exceptions.PluginSpecsNotFoundException;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import plugins.Corpoplugins;
import plugins.Pluginspecs;

import java.io.File;
import java.io.IOException;

/**
 * @author Fati CHEN
 * @version 1.0.0
 */
public class Update extends ActionBase {


    /**
     * Will update the given plugin. Will check if the plugin is already installed, compare its version,
     * if its dependencies are present (will execute a dry run of install for it).
     * And then use Remove and Install to reinstall the plugin.
     *
     * @param plugin_to_update String The plugin which will be updated.
     * @param debug            boolean Will display the progression of the action if true.
     * @see core.Install
     * @see core.Remove
     */
    public static void updatePlugin(String path_to_update, boolean debug) {
        File plugin_path = new File(path_to_update);

// Setting up the PluginManger from jspf
        PluginManager pm = PluginManagerFactory.createPluginManager();
// adding the path to verify
        pm.addPluginsFrom(plugin_path.toURI());
// getting the class which implements our plugin interface
        Corpoplugins extension = pm.getPlugin(Corpoplugins.class);
        Pluginspecs plugin_specs;

        try {
            configString = ActionBase.getConfig(); // Get the configuration file
            plugin_specs = extension.getClass()
                    .getAnnotation(Pluginspecs.class); // Get the Pluginspecs from jar

            if (debug) System.out.print(Messages.getString("flag.install.plugin-specs")
                    + extension.getClass());
            if (plugin_specs == null)
                throw new PluginSpecsNotFoundException(extension.getClass()
                        .toString()); // if there is no annotations
            if (debug) {
                System.out.println(Messages.getString("flag.done"));
                System.out.print(Messages.getString("flag.install.verification"));
            }
            if (!Install.isInstalled(plugin_specs.name()))
                throw new PluginNotFoundException(plugin_specs.name()); // if the plugin is NOT installed
            if (debug) {
                System.out.println(Messages.getString("flag.done"));
                System.out.print(Messages.getString("flag.install.dependencies"));
            }
            Install.hasDependencies(plugin_specs); // if all the dependencies are present
            if (debug) {
                System.out.println(Messages.getString("flag.done"));
                System.out.println(Messages.getString("flag.update.version"));
            }
            compareVersion(plugin_specs);


        } catch (IOException | PluginNotFoundException | PluginSpecsNotFoundException | PluginDependenciesNotPresentException e) {
            //TODO: change to getMessage()
            e.printStackTrace();
        }
    }

    private static boolean compareVersion(Pluginspecs plugin_specs) {
        boolean canupdate=false;
        try {
            String config_line;

            String[] splitted = Remove.findPluginLine(plugin_specs.name()).split(INTERSEPARATOR);
            config_line = splitted[splitted.length - 1];
            config_line = config_line.split(SEPARATOR)[0];
        } catch (IOException | PluginNotFoundException e) {
            // todo : change to getMessage()
            e.printStackTrace();
        }


        return canupdate;
    }
}
