package core;

import exceptions.PluginNotFoundException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Removes a plugin. Will remove the given plugin if he's present in the Coproplugins.properties file
 *
 * @author Fati CHEN
 * @version 1.0.0
 */
public class Delete extends ActionBase {

    /**
     * Will remove the given plugin, if the plugin is registered in the configuration file
     *
     * @param plugin_to_remove String The name of the plugin which needs to be removed, will be Converted to UPPERCASE
     * @param debug            boolean Will display the progression of the action if true.
     */
    public static void deletePlugin(String plugin_to_remove, boolean debug) {
        try {
            configString = getConfig();

            if (debug) System.out.print(Flags.getString("remove.pluginconfig"));
            removeFromProperties(findPluginLine(plugin_to_remove));
            if (debug) System.out.println(Flags.getString("done"));

            if (debug) System.out.print(Flags.getString("remove.pluginfolder") + PLUGINDIRECTORY);
            FileUtils.deleteDirectory(new File(PLUGINDIRECTORY + plugin_to_remove.toLowerCase()));
            if (debug) System.out.println(Flags.getString("done"));

            System.out.println(Flags.getString("remove.success"));
        } catch (IOException | PluginNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Will delete the given line from the configuration file.
     *
     * @param line String the line which need to be removed
     * @throws IOException If the configuration file is not found
     */
    protected static void removeFromProperties(String line) throws IOException {
        String fileString;
        fileString = FileUtils.readFileToString(config);
        String finalString = fileString.replace(line + System.lineSeparator(), EMPTYSTRING);
        FileUtils.writeStringToFile(config, finalString);
    }

    /**
     * Will find the line from the configuration file for the given plugin name,
     *
     * @param plugin String the name of the plugin which line needs to be found, will be converted to UPPERCASE
     * @return The line which contains the plugin name.
     * @throws IOException             If the configuration file is not found
     * @throws PluginNotFoundException if the plugin name is not present in the configuration file
     */
    protected static String findPluginLine(String plugin) throws IOException, PluginNotFoundException {
        for (String line : configString) {
            if (line.contains(PLUGIN + INTERSEPARATOR + plugin.toUpperCase() + INTERSEPARATOR))
                return line;
        }
        throw new PluginNotFoundException(plugin);
    }
}
