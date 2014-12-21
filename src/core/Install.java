package core;

import Exceptions.PluginDependenciesNotPresentException;
import Exceptions.PluginIsInstalledException;
import Exceptions.PluginSpecsNotFoundException;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import plugins.Corpoplugins;
import plugins.Pluginspecs;

import java.io.*;
import java.util.ArrayList;

/**
 * Manage the installation of a Corpoplugin.
 * Will also check the existing plugins to avoid any collisions between plugins.
 *
 * @author Fati CHEN
 */
public class Install {

    /**
     * Extracts the properties in a [ plugin, format, package ] structure.
     *
     * @return String[] properties of the configuration file
     * @throws IOException
     */
    private String[] getConfig() throws IOException {
        File config = new File(".properties");
        if (config.createNewFile())
            return null;

        FileReader configFR = new FileReader(config);
        BufferedReader configBR = new BufferedReader(configFR);

        String line;
        ArrayList<String> airlines = new ArrayList<>();

        while ((line = configBR.readLine()) != null) {
            airlines.add(line);
        }
        configBR.close();

        String[] configs = new String[airlines.size()];
        airlines.toArray(configs);
        return configs;
    }

    private void addPlugintoConfig(String namep, String version, String[] extensions) throws IOException {
        File config = new File(".properties");

        FileWriter configFW = new FileWriter(config);
        BufferedWriter configBW = new BufferedWriter(configFW);

        String formats = "";

        for (String ext : extensions) {
            formats += "|" + ext;
        }

        String str = "plugin>" + namep + ">" + version + formats;


        configBW.newLine();
        configBW.write(str);

        configBW.close();
    }

    private void isPlugin(Pluginspecs pluginspecs, String[] config) throws PluginIsInstalledException {
        Boolean verification = false;
        for (String line : config) {
            verification = line.matches("plugin>" + pluginspecs.name());
            if (verification)
                break;
        }
        if (verification) throw new PluginIsInstalledException();
    }

    private void hasDependencies(Pluginspecs pluginspecs, String[] config) throws PluginDependenciesNotPresentException {

        String[] pluginspecstab = pluginspecs.dependencies();
        boolean dependencies = false;

        if (pluginspecstab.length > 0) {
            for (String dep : pluginspecstab) {
                dependencies = false; // initialisation of dependencies to false for each dependence
                for (String line : config) {
                    if (!dependencies) // if there is no match yet
                        dependencies = line.matches("plugin>" + dep);
                    else // if there is a match
                        break;
                }
                if (!dependencies) // If at the end of the config file, there wasn't any match to the wanted dependence
                    break;
            }
            if (dependencies) throw new PluginDependenciesNotPresentException();
        }
    }

    /**
     * @param path_to_install String    Is the path to the plugin to install
     */
    public void installPlugin(String path_to_install) {
        PluginManager pm = PluginManagerFactory.createPluginManager(); //Setting up the PluginManger from jspf
        pm.addPluginsFrom(new File(path_to_install.replaceAll("\"", "")).toURI()); // adding the path to verify
        Corpoplugins extension = pm.getPlugin(Corpoplugins.class); // getting the class which implements our plugin interface

        // TODO : delete this class test
        System.out.println(extension.getClass());
        Pluginspecs pluginspecs;
        String[] config;


        try {
            config = this.getConfig(); // Get the configuration file
            pluginspecs = extension.getClass().getAnnotation(Pluginspecs.class); // Get the Pluginspecs from jar
            if (pluginspecs == null) throw new PluginSpecsNotFoundException(); // if there is no annotations

            this.isPlugin(pluginspecs, config); // if the plugin is already installed
            this.hasDependencies(pluginspecs, config);

        } catch (IOException | PluginDependenciesNotPresentException | PluginIsInstalledException | PluginSpecsNotFoundException e) {
            e.printStackTrace();
        }
    }
}

