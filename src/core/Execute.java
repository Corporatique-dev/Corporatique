package core;

import exceptions.FolderCreationException;
import exceptions.FormatNotFoundException;
import exceptions.IsNotFileException;
import exceptions.PluginNotFoundException;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import plugins.Corpoplugins;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Will execute the Corpoplugin passed in executePlugin. Will get the plugin
 * name or the format if given, otherwise will get the file extension
 *
 * @author Fati CHEN
 * @version 1.0.0
 */
public class Execute extends ActionBase {
    /**
     * Allows you to get the plugin you want if it's installed. Advise : put it in the dependencies
     *
     * @param plugin_name String The name of the wanted plugin
     * @return Corpoplugins The plugin with the given name
     */
    public static Corpoplugins thisPlugin(String plugin_name) {
        // Setting up the PluginManager from jspf
        PluginManager pm = PluginManagerFactory.createPluginManager();
        try {
            configString = getConfig();
            if (Install.isInstalled(plugin_name))
                pm.addPluginsFrom(new File(PLUGINDIRECTORY + plugin_name.toLowerCase()).toURI());
            return pm.getPlugin(Corpoplugins.class);
        } catch (IOException e) {
            e.getMessage();
        }
        return null;
    }

    /**
     * Will process the Text extraction depending on the given parameters.
     * <p>
     * Will get the appropriate Corpoplugin by analysing the user input
     * </p>
     *
     * @param plugin_name  String the name of the plugin being used for the extraction
     * @param format       String Format of the file which you are going to process
     * @param path_filein  String path to the original file
     * @param path_fileout String [optional] Path to the processed file
     * @param options      String[] [optional] Eventual options of a given plugin;
     * @param debug        true to display debug information
     */
    public void executePlugin(String plugin_name, String format, String path_filein, String path_fileout, String[] options, boolean debug) {
        try {
            PluginManager pm = PluginManagerFactory.createPluginManager();
            // Setting up the PluginManager from jspf
            configString = getConfig();
            String plugin_from_conf = null;

			/*
             * ====================== NO USE FOUND YET ======================
             * if (plugin_name != null && format != null) { // if the plugin name
			 * AND the file format is given for (String config_line :
			 * config_strings) { if (config_line.matches(PLUGIN + INTERSEPARATOR
			 * + plugin_name.toUpperCase() + INTERSEPARATOR) &&
			 * (config_line.matches(SEPARATOR + DEFAULT + INTERSEPARATOR +
			 * format.toLowerCase() + SEPARATOR) ||
			 * config_line.matches(SEPARATOR + format.toLowerCase() +
			 * SEPARATOR))) { plugin_from_conf = config_line; break; } } if
			 * (plugin_from_conf == null) throw new
			 * PluginNotFoundException(plugin_name); } else
			 * ==============================================================
			 */

            if (plugin_name != null) { // if the plugin name is given
                if (debug) System.out.print(Flags.getString("update.verification"));
                for (String config_line : configString) {
                    if (config_line.contains(PLUGIN + INTERSEPARATOR
                            + plugin_name.toUpperCase() + INTERSEPARATOR)) {
                        plugin_from_conf = config_line;
                        break;
                    }
                }
            } else if (format != null) { // if the file format is given
                if (debug) System.out.print(Flags.getString("execute.format"));

                for (String config_line : configString) {
                    if (config_line
                            .contains(SEPARATOR + DEFAULT + INTERSEPARATOR
                                    + format.toLowerCase() + SEPARATOR)) {
                        plugin_from_conf = config_line;
                        break;
                    } else if (config_line.contains(SEPARATOR
                            + format.toLowerCase() + SEPARATOR))
                        plugin_from_conf = config_line;
                }
            } else {
// is the format nor the plugin is given, searching from file extension
                if (debug) System.out.print(Flags.getString("execute.format"));

                String[] filesplitted = path_filein.split(FILESPLITTER);
                if (filesplitted.length <= 1)
                    throw new FormatNotFoundException(filesplitted[0]);
                String file_format = filesplitted[filesplitted.length - 1];
                for (String config_line : configString) {
                    if (config_line.contains(SEPARATOR + DEFAULT
                            + INTERSEPARATOR + file_format.toLowerCase())) {
                        plugin_from_conf = config_line;
                        break;
                    } else if (config_line.contains(SEPARATOR
                            + file_format.toLowerCase() + SEPARATOR))
                        plugin_from_conf = config_line;
                }
            }
            if (plugin_from_conf == null)
                throw new PluginNotFoundException(plugin_name);
            if (debug) System.out.println(Flags.getString("done"));
            String plugin = plugin_from_conf.split(Pattern.quote(INTERSEPARATOR))[1];
            File pluginlocation = new File(PLUGINDIRECTORY + plugin.toLowerCase() + System.getProperty("file.separator"));
            pm.addPluginsFrom(pluginlocation.toURI());

            Corpoplugins extractor = pm.getPlugin(Corpoplugins.class);

            File file_out, file_in;
            if (path_filein == null)
                throw new FileNotFoundException();

            if (debug) System.out.print(Flags.getString("execute.verification"));
            if (new File(path_filein).isFile())
                file_in = new File(path_filein);
            else throw new IsNotFileException(path_filein);
            if (debug) System.out.println(Flags.getString("done"));

            if (path_fileout == null)
                file_out = new File(path_filein + TXT);
            else if (new File(path_fileout).isDirectory())
                file_out = new File(path_fileout + DEFAULT + TXT);
            else if (!new File(path_fileout).exists()) {
                file_out = new File(path_fileout);
                if (!new File(file_out.getParent()).mkdirs())
                    throw new FolderCreationException(file_out.getParent());
            } else
                file_out = new File(path_fileout);

            extractor.Load(file_in, file_out);
            System.out.print(Flags.getString("execute.processing"));
            extractor.processExtraction(options);
            System.out.println(Flags.getString("done"));
        } catch (FormatNotFoundException | FolderCreationException | IsNotFileException | IOException | PluginNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
