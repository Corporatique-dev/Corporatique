package core;

import exceptions.PluginNotFoundException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Removes a plugin. Will remove the given plugin if he's present in the Coproplugins.properties file
 *
 * @author Fati CHEN
 * @version 1.0.0
 */
public class Remove extends ActionBase {

    /**
     * Will remove the given plugin, if the plugin is registered in the configuration file
     *
     * @param plugin_to_remove String The name of the plugin which needs to be removed
     * @param debug            boolean Will display the progression of the action if true.
     */
    public static void removePlugin(String plugin_to_remove, boolean debug) {
        try {
            configString = getConfig();

            if (debug) System.out.print(Messages.getString("flag.remove.pluginconfig"));
            removeFromProperties(findPluginLine(plugin_to_remove));
            if (debug) System.out.println(Messages.getString("flag.done"));

            if (debug) System.out.print(Messages.getString("flag.remove.pluginfolder") + PLUGINDIRECTORY);
            FileUtils.deleteDirectory(new File(PLUGINDIRECTORY + plugin_to_remove.toLowerCase()));
            if (debug) System.out.println(Messages.getString("flag.done"));

            System.out.println(Messages.getString("flag.remove.success"));
        } catch (IOException | PluginNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    protected static void removeFromProperties(String line) throws IOException {
        String fileString;
        fileString = FileUtils.readFileToString(config);
        String finalString = fileString.replaceAll(Pattern.quote(line) + System.lineSeparator(), EMPTYSTRING);
        FileUtils.writeStringToFile(config, finalString);
    }

    protected static String findPluginLine(String plugin) throws IOException, PluginNotFoundException {

        for (String line : configString) {
            if (line.contains(PLUGIN + INTERSEPARATOR + plugin.toUpperCase() + INTERSEPARATOR))
                return line;
        }
        throw new PluginNotFoundException(plugin);
    }
}
