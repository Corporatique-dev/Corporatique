package core;

import exceptions.PluginNotFoundException;
import exceptions.PluginSpecsNotFoundException;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import org.apache.commons.io.FileUtils;
import plugins.Corpoplugins;
import plugins.Pluginspecs;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author Fati CHEN
 */
public class OtherActions extends ActionBase {

    /**
     * Will return a Array list with the list of all the plugins installed in the application [present in the configuration file]
     *
     * @return String containing all the plugins with de supported format and the formats wich are set to default.
     */
    public static String listAll() {
        String allplugins = EMPTYSTRING;

        try {
            configString = ActionBase.getConfig();

            for (String line : configString) {
                String format[] = line.split(Pattern.quote(SEPARATOR));
                // format contains all the format from 1 to format.lenght-1
                String description[] = format[0].split(INTERSEPARATOR);

                allplugins += PLUGIN + INTERSEPARATOR + description[1] + "\t" + "v" + description[2];

                String bydefault = EMPTYSTRING;
                String supported = EMPTYSTRING;
                for (int i = 1; i < format.length; i++) {
                    if (format[i].contains(DEFAULT + INTERSEPARATOR))
                        bydefault = " ." + format[i].split(Pattern.quote(INTERSEPARATOR))[1] + bydefault;
                    else supported += " ." + format[i];
                }

                if (!Objects.equals(bydefault, EMPTYSTRING)) {
                    bydefault = "\t" + INTERSEPARATOR + DEFAULT + bydefault;
                }
                if (!Objects.equals(supported, EMPTYSTRING)) {
                    supported = "\t" + INTERSEPARATOR + "supported" + supported;
                }

                allplugins += bydefault + supported + System.lineSeparator();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return allplugins;
    }

    /**
     * Will set a plugin to default for the specified format.
     *
     * @param plugin_to_set String plugin to set to default
     * @param format_to_set String format to set to default
     * @return 1 if the plugin is already set to default for this format<br/>
     * 0 if the plugin was successfully set to default for this format<br/>
     * -1 if the format isn't supported by the plugin, or the format/plugin doesn't exist
     */

    public static int setDefault(String plugin_to_set, String format_to_set) {
        try {
            configString = ActionBase.getConfig();
            boolean replaced = false;

            for (int i = 0; i < configString.length; i++) {
                if (configString[i].contains(PLUGIN + INTERSEPARATOR + plugin_to_set.toUpperCase() + INTERSEPARATOR)
                        && configString[i].contains(DEFAULT + INTERSEPARATOR + format_to_set.toLowerCase() + SEPARATOR)) {
                    return 1;
                }

                if (configString[i].contains(PLUGIN + INTERSEPARATOR + plugin_to_set.toUpperCase() + INTERSEPARATOR)
                        && configString[i].contains(SEPARATOR + format_to_set.toLowerCase() + SEPARATOR)) {
                    configString[i] = configString[i].replaceAll(
                            Pattern.quote(SEPARATOR + format_to_set.toLowerCase() + SEPARATOR)
                            , SEPARATOR + DEFAULT + INTERSEPARATOR + format_to_set.toLowerCase() + SEPARATOR);
                    replaced = true;
                }
            }

            if (replaced) {
                for (int i = 0; i < configString.length; i++) {
                    if (!configString[i].contains(PLUGIN + INTERSEPARATOR + plugin_to_set.toUpperCase() + INTERSEPARATOR)
                            && configString[i].contains(DEFAULT + INTERSEPARATOR + format_to_set.toLowerCase() + SEPARATOR)) {
                        configString[i] = configString[i].replaceAll(
                                Pattern.quote(SEPARATOR + DEFAULT + INTERSEPARATOR + format_to_set.toLowerCase() + SEPARATOR)
                                , SEPARATOR + format_to_set.toLowerCase() + SEPARATOR);
                    }
                }

                FileUtils.writeLines(config, Arrays.asList(configString));
                return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * Gives the specifications of the given plugin, used in --help
     *
     * @param plugin_name String the name of the plugin.
     * @return The plugin specifications.
     */
    public static String pluginDetails(String plugin_name, boolean debug) {

        String plugin = EMPTYSTRING;
        // Setting up the PluginManger from jspf
        PluginManager pm = PluginManagerFactory.createPluginManager();
// adding the path to verify
        pm.addPluginsFrom(new File(PLUGINDIRECTORY + plugin_name).toURI());
// getting the class which implements our plugin interface
        Corpoplugins extension = pm.getPlugin(Corpoplugins.class);

        try {
            if (Remove.findPluginLine(plugin_name) != null) ;

            if (debug) System.out.print(Flags.getString("install.plugin-specs")
                    + extension.getClass());
            Pluginspecs plugin_specs = extension.getClass()
                    .getAnnotation(Pluginspecs.class);
            if (plugin_specs == null)
                throw new PluginSpecsNotFoundException(extension.getClass()
                        .toString()); // if there is no annotations
            if (debug) System.out.println(Flags.getString("done"));

            plugin += "Plugin name: " + plugin_specs.name() + System.lineSeparator();
            plugin += "Description: " + plugin_specs.description() + System.lineSeparator();
            plugin += "Version: " + plugin_specs.version() + System.lineSeparator();
            plugin += "Author: " + plugin_specs.author() + System.lineSeparator();
            plugin += "Supported formats: ";
            for (String format : plugin_specs.extensions()) {
                plugin += " ." + format;
            }
            plugin += System.lineSeparator() + "Dependencies: ";
            for (String dependency : plugin_specs.dependencies()) {
                plugin += dependency + ", ";
            }

        } catch (PluginSpecsNotFoundException | IOException | PluginNotFoundException e) {
            e.printStackTrace();
        }
        return plugin;
    }

}
