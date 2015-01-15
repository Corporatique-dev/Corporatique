package core;

import exceptions.*;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import plugins.Corpoplugins;
import plugins.Pluginspecs;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

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
     * @param path_to_update String The plugin which will be updated.
     * @param debug          boolean Will display the progression of the action if true.
     * @see core.Install
     * @see core.Remove
     */
    public static void updatePlugin(String path_to_update, boolean debug) {
        File plugin_path = new File(path_to_update);

        try {
            if (!plugin_path.exists() || !plugin_path.isFile())
                throw new IsNotFileException(path_to_update);

            // Setting up the PluginManger from jspf
            PluginManager pm = PluginManagerFactory.createPluginManager();
            // adding the path to verify
            pm.addPluginsFrom(plugin_path.toURI());
            // getting the class which implements our plugin interface
            Corpoplugins extension = pm.getPlugin(Corpoplugins.class);
            Pluginspecs new_plugin_specs;

            configString = ActionBase.getConfig(); // Get the configuration file
            new_plugin_specs = extension.getClass()
                    .getAnnotation(Pluginspecs.class); // Get the Pluginspecs from jar

            if (debug) System.out.print(Flags.getString("install.plugin-specs")
                    + extension.getClass());
            if (new_plugin_specs == null)
                throw new PluginSpecsNotFoundException(extension.getClass()
                        .toString()); // if there is no annotations
            if (debug) {
                System.out.println(Flags.getString("done"));
                System.out.print(Flags.getString("update.verification"));
            }
            if (!Install.isInstalled(new_plugin_specs.name()))
                throw new PluginNotFoundException(new_plugin_specs.name()); // if the plugin is NOT installed
            if (debug) {
                System.out.println(Flags.getString("done"));
                System.out.print(Flags.getString("install.dependencies"));
            }
            Install.hasDependencies(new_plugin_specs); // if all the dependencies are present
            if (debug) {
                System.out.println(Flags.getString("done"));
                System.out.print(Flags.getString("update.version"));
            }
            if (!compareVersion(new_plugin_specs))//if the plugin installed is newer
                throw new PluginIsNewer(new_plugin_specs.name());
            if (debug) {
                System.out.println(Flags.getString("done"));
            }
            Remove.removePlugin(new_plugin_specs.name(), debug);
            Install.installPlugin(path_to_update, debug);

        } catch (IOException | PluginNotFoundException | PluginSpecsNotFoundException |
                PluginDependenciesNotPresentException | IsNotFileException | PluginIsNewer e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Will compare the versions between the one installed and the one which will replace it.
     * If the installed one is older, the newer will be installed, else,  the installed will be replaced
     *
     * @param new_plugin_specs Pluginspecs the specification of the plugin which will replace the one installed
     * @return true if the installed is older, false if they are the same or if the installed one is newer.
     */
    private static boolean compareVersion(Pluginspecs new_plugin_specs) {
        boolean canupdate = false;
        int length;
        try {
            String config_line;

            String[] splitted = Remove.findPluginLine(new_plugin_specs.name()).split(Pattern.quote(INTERSEPARATOR));
            config_line = splitted[splitted.length - 1];

            String[] version = config_line.split(Pattern.quote(SEPARATOR))[0].split(FILESPLITTER);
            String[] newversion = new_plugin_specs.version().split(FILESPLITTER);


            if (version.length >= newversion.length) length = newversion.length;
            else {
                length = version.length;
                canupdate = true;
            }

            for (int i = 0; i < length; i++) {
                if (Integer.parseInt(newversion[i]) > Integer.parseInt(version[i])) return true;
                else if (Integer.parseInt(newversion[i]) < Integer.parseInt(version[i])) return false;
            }
        } catch (IOException | PluginNotFoundException e) {
            e.getMessage();
        }
        return canupdate;
    }
}
