package core;

import exceptions.IsNotJarException;
import exceptions.PluginDependenciesNotPresentException;
import exceptions.PluginIsInstalledException;
import exceptions.PluginSpecsNotFoundException;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import org.apache.commons.io.FileUtils;
import plugins.Corpoplugins;
import plugins.Pluginspecs;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Manage the installation of a Corpoplugin. Will also check the existing
 * plugins to avoid any collisions between plugins.
 *
 * @author Fati CHEN
 */
public class Install extends ActionBase {

    /**
     * Will install the specified plugin. Will if the plugin is extended from the Corpoplugin interface,
     * check if it's aready installed, then will copy it under : installation_folder/plugin_name/
     *
     * @param path_to_install String Is the path to the plugin to install
     * @param debug           boolean Will display the progression of the action if true.
     * @see core.Remove
     */
    public static void installPlugin(String path_to_install, boolean debug) {
        File plugin_path = new File(path_to_install.replaceAll(DOUBLEQUOTE, EMPTYSTRING));

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
            if (debug) System.out.println(Messages.getString("flag.done"));

            if (debug) System.out.print(Messages.getString("flag.install.verification"));
            if (isInstalled(plugin_specs.name()))
                throw new PluginIsInstalledException(plugin_specs.name()); // if the plugin is already installed
            if (debug) System.out.println(Messages.getString("flag.done"));

            if (debug) System.out.print(Messages.getString("flag.install.dependencies"));
            hasDependencies(plugin_specs); // if all the dependencies are present
            if (debug) System.out.println(Messages.getString("flag.done"));

            if (debug) System.out.print(Messages.getString("flag.install.copyjar")
                    + plugin_specs.name().toLowerCase()
                    + Messages.getString("flag.install.copyjar2"));
            copyPlugintoDirectory(plugin_specs.name(), plugin_path);
            if (debug) System.out.println(Messages.getString("flag.done"));

            if (debug) System.out.print(Messages.getString("flag.install.adding-properties"));
            addPlugintoConfig(plugin_specs);
            if (debug) System.out.println(Messages.getString("flag.done"));

            System.out.println(Messages.getString("flag.install.success"));
        } catch (IOException | PluginDependenciesNotPresentException
                | PluginIsInstalledException | PluginSpecsNotFoundException
                | IsNotJarException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Will check if the plugin is present in the configuration file.
     *
     * @param name String the name of the plugin, will be converted to UPPERCASE
     * @return true if the plugin is installed.
     */
    protected static boolean isInstalled(String name) {
        Boolean verification = false;

        for (String line : configString) {
            verification = line.contains(PLUGIN + INTERSEPARATOR
                    + name.toUpperCase());
            if (verification)
                break;
        }

        return verification;
    }

    /**
     * Verify if the dependencies of a plugin are present Will throw an
     * PluginDependenciesNotPresentException if the dependencies of the given
     * plugin are not present in the config file
     *
     * @param plugin_specs Pluginspecs Contains the plugin specifications
     * @throws PluginDependenciesNotPresentException If the wanted dependencies are not present in Corporatique
     */
    protected static void hasDependencies(Pluginspecs plugin_specs)
            throws PluginDependenciesNotPresentException {
        String[] pluginspecstab = plugin_specs.dependencies();
        boolean dependencies = true;

        for (String dep : pluginspecstab) {
            if (Objects.equals(dep, EMPTYSTRING))
                break;
            dependencies = false; // initialisation of dependencies to false for
            // each dependence

            for (String line : configString) {
                // if there is no match found yet, continue, else break
                if (!dependencies)
                    dependencies = line.contains(PLUGIN + INTERSEPARATOR
                            + dep.toUpperCase());
                else
                    break;
            }
            // If at the end of the config file, there wasn't any match to the
            // wanted dependence
            if (!dependencies)
                break;
        }
        if (!dependencies)
            throw new PluginDependenciesNotPresentException(plugin_specs.name());
    }

    /**
     * Will add the given pluginspecs to the configuration file
     *
     * @param plugin_specs Pluginspecs contains the plugin specifications
     * @throws IOException
     */
    private static void addPlugintoConfig(Pluginspecs plugin_specs) throws IOException {
        String formats = EMPTYSTRING;
        for (String ext : plugin_specs.extensions()) {
            if (Objects.equals(ext, EMPTYSTRING))
                break;

            if (configString.length != 0) {
                for (String config_line : configString) {
                    formats += SEPARATOR;
                    if (config_line.contains(SEPARATOR + ext.toLowerCase()))
                        formats += DEFAULT + INTERSEPARATOR;
                    formats += ext;
                }
            } else
                formats += SEPARATOR + ext.toLowerCase();
        }
        FileUtils.writeStringToFile(config, PLUGIN + INTERSEPARATOR
                        + plugin_specs.name().toUpperCase() + INTERSEPARATOR
                        + plugin_specs.version() + formats + System.lineSeparator(),
                true);
    }

    /**
     * Will copy the given plugin File under plugins/plugin_name
     *
     * @param plugin_name String The name of the plugin
     * @param plugin_path File The path to the plugin
     * @throws IOException
     */
    private static void copyPlugintoDirectory(String plugin_name, File plugin_path)
            throws IOException, IsNotJarException {
        if (plugin_path.isFile())
            FileUtils.copyFileToDirectory(plugin_path, new File(PLUGINDIRECTORY
                    + plugin_name.toLowerCase()));
        else
            throw new IsNotJarException(plugin_name);
    }
}
