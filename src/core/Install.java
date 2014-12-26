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
import java.util.List;
import java.util.Objects;

/**
 * Manage the installation of a Corpoplugin.
 * Will also check the existing plugins to avoid any collisions between plugins.
 *
 * @author Fati CHEN
 */
public class Install {
    private static final String INTERSEPARATOR = ">";
    private static final String DEFAULT = "default";
    private static final String PLUGIN = "plugin";
    private static final String SEPARATOR = "|";
    private static final String PLUGINDIRECTORY = "plugins/";
    private final File config = new File("Corporatique.properties");
    private String[] configString;

    /**
     * Will install the specified plugin.
     *
     * @param path_to_install String    Is the path to the plugin to install
     */
    public void installPlugin(String path_to_install) {
        File plugin_path = new File(path_to_install.replaceAll("\"", ""));

        PluginManager pm = PluginManagerFactory.createPluginManager(); //Setting up the PluginManger from jspf
        pm.addPluginsFrom(plugin_path.toURI()); // adding the path to verify
        Corpoplugins extension = pm.getPlugin(Corpoplugins.class); // getting the class which implements our plugin interface
        Pluginspecs plugin_specs;

        try {
            configString = this.getConfig(); // Get the configuration file
            plugin_specs = extension.getClass().getAnnotation(Pluginspecs.class); // Get the Pluginspecs from jar

            System.out.print("Getting the plugin Specifications from " + extension.getClass());
            if (plugin_specs == null)
                throw new PluginSpecsNotFoundException(extension.getClass().toString()); // if there is no annotations
            System.out.println("...OK");

            System.out.print("Verifying if the plugin is installed");
            this.isInstalled(plugin_specs); // if the plugin is already installed
            System.out.println("...OK");

            System.out.print("Verifying if the dependencies of the plugin are installed");
            this.hasDependencies(plugin_specs); //if all the dependencies are present
            System.out.println("...OK");

            System.out.print("Copying the plugin in the plugins/" + plugin_specs.name().toLowerCase() + "/ folder");
            this.copyPlugintoDirectory(plugin_specs.name(), plugin_path);
            System.out.println("...OK");

            System.out.print("Adding the plugin to the configuration file");
            this.addPlugintoConfig(plugin_specs);
            System.out.println("...OK");
        } catch (IOException | PluginDependenciesNotPresentException | PluginIsInstalledException | PluginSpecsNotFoundException | IsNotJarException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Extracts the properties in a [ plugin | format ] structure.
     *
     * @return String[] properties of the configuration file
     * @throws IOException
     */
    private String[] getConfig() throws IOException {
        if (config.createNewFile()) return null;
        String[] configs;

        List<String> airlines = FileUtils.readLines(config);

        configs = new String[airlines.size()];
        airlines.toArray(configs);

        return configs;
    }

    /**
     * Will check if the plugin is already installed
     *
     * @param plugin_specs Pluginspecs contains the specifications of the given plugin
     * @throws PluginIsInstalledException
     */
    private void isInstalled(Pluginspecs plugin_specs) throws PluginIsInstalledException {
        Boolean verification = false;

        for (String line : configString) {
            verification = line.contains(PLUGIN + INTERSEPARATOR + plugin_specs.name().toUpperCase());
            if (verification) break;
        }
        if (verification) throw new PluginIsInstalledException(plugin_specs.name());

    }

    /**
     * Verify if the dependencies of a plugin are present
     * Will throw an PluginDependenciesNotPresentException
     * if the dependencies of the given plugin are not present in the config file
     *
     * @param plugin_specs Pluginspecs   Contains the plugin specifications
     * @throws PluginDependenciesNotPresentException If the wanted dependencies are not present in Corporatique
     */
    private void hasDependencies(Pluginspecs plugin_specs) throws PluginDependenciesNotPresentException {
        String[] pluginspecstab = plugin_specs.dependencies();
        boolean dependencies = true;

        for (String dep : pluginspecstab) {
            if (Objects.equals(dep, "")) break;
            dependencies = false; // initialisation of dependencies to false for each dependence

            for (String line : configString) {
                // if there is no match found yet, continue, else break
                if (!dependencies) dependencies = line.contains(PLUGIN + INTERSEPARATOR + dep.toUpperCase());
                else break;
            }
            // If at the end of the config file, there wasn't any match to the wanted dependence
            if (!dependencies) break;
        }
        if (!dependencies) throw new PluginDependenciesNotPresentException(plugin_specs.name());
    }

    /**
     * Will add the given pluginspecs to the configuration file
     *
     * @param plugin_specs Pluginspecs contains the plugin specifications
     * @throws IOException
     */
    private void addPlugintoConfig(Pluginspecs plugin_specs) throws IOException {
        String formats = "";
        for (String ext : plugin_specs.extensions()) {
            if (Objects.equals(ext, "")) break;

            if (configString.length != 0) {
                for (String config_line : configString) {
                    formats += SEPARATOR;
                    if (config_line.contains(DEFAULT + INTERSEPARATOR + ext.toLowerCase()))
                        formats += "";
                    else if (config_line.contains(SEPARATOR + ext.toLowerCase()))
                        formats += DEFAULT + INTERSEPARATOR;
                    formats += ext;
                }
            } else formats += SEPARATOR + ext.toLowerCase();
        }

        FileUtils.writeStringToFile(config,
                PLUGIN + INTERSEPARATOR + plugin_specs.name().toUpperCase() + INTERSEPARATOR + plugin_specs.version()
                        + formats + System.lineSeparator(), true);
    }

    /**
     * Will copy the given plugin File under plugins/plugin_name
     *
     * @param plugin_name String The name of the plugin
     * @param plugin_path File The path to the plugin
     * @throws IOException
     */
    private void copyPlugintoDirectory(String plugin_name, File plugin_path) throws IOException, IsNotJarException {
        if (plugin_path.isFile())
            FileUtils.copyFileToDirectory(plugin_path, new File(PLUGINDIRECTORY + plugin_name.toLowerCase()));
        else throw new IsNotJarException(plugin_name);
    }
}

