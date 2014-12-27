package core;

import exceptions.FormatNotFoundException;
import exceptions.IsNotFileException;
import exceptions.PluginNotFoundException;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import plugins.Corpoplugins;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Will execute the Corpoplugin passed in executePlugin. Will get the plugin
 * name or the format if given, otherwise will get the file extension
 *
 * @author Fati CHEN
 * @version 1.0.0
 */
public class Execute extends ActionBase {
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
     */
    public void executePlugin(String plugin_name, String format, String path_filein, String path_fileout, String[] options) {
        String[] config_strings;
        try {
            PluginManager pm = PluginManagerFactory.createPluginManager();
            // Setting up the PluginManager from jspf
            config_strings = this.getConfig();
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
                for (String config_line : config_strings) {
                    if (config_line.contains(PLUGIN + INTERSEPARATOR
                            + plugin_name.toUpperCase() + INTERSEPARATOR)) {
                        plugin_from_conf = config_line;
                        break;
                    }
                }
                if (plugin_from_conf == null)
                    throw new PluginNotFoundException(plugin_name);
            } else if (format != null) { // if the file format is given
                for (String config_line : config_strings) {
                    if (config_line
                            .contains(SEPARATOR + DEFAULT + INTERSEPARATOR
                                    + format.toLowerCase() + SEPARATOR)) {
                        plugin_from_conf = config_line;
                        break;
                    } else if (config_line.contains(SEPARATOR
                            + format.toLowerCase() + SEPARATOR))
                        plugin_from_conf = config_line;
                }
                if (plugin_from_conf == null)
                    throw new FormatNotFoundException(format);
            } else {
// is the format nor the plugin is given, searching from file extension
                String[] filesplitted = path_filein.split(FILESPITTER);
                if (filesplitted.length <= 1)
                    throw new FormatNotFoundException(filesplitted[0]);
                String file_format = filesplitted[filesplitted.length - 1];
                for (String config_line : config_strings) {
                    if (config_line.contains(SEPARATOR + DEFAULT
                            + INTERSEPARATOR + file_format.toLowerCase())) {
                        plugin_from_conf = config_line;
                        break;
                    } else if (config_line.contains(SEPARATOR
                            + file_format.toLowerCase() + SEPARATOR))
                        plugin_from_conf = config_line;
                }
                if (plugin_from_conf == null)
                    throw new FormatNotFoundException(file_format);
            }

            String plugin = plugin_from_conf.split(INTERSEPARATOR)[1];
            pm.addPluginsFrom(new File(PLUGINDIRECTORY + plugin).toURI());

            Corpoplugins extractor = pm.getPlugin(Corpoplugins.class);

            File file_out, file_in;
            if (path_filein != null)
                path_filein = path_filein.replaceAll(DOUBLEQUOTE, EMPTYSTRING);
            else throw new FileNotFoundException();
            if (path_fileout != null)
                path_fileout = path_fileout.replaceAll(DOUBLEQUOTE, EMPTYSTRING);

            if (new File(path_filein).isFile())
                file_in = new File(path_filein);
            else throw new IsNotFileException(path_filein);

            if (path_fileout == null)
                file_out = new File(path_filein + TXT);
            else if (new File(path_fileout).isDirectory())
                file_out = new File(path_fileout + DEFAULT + TXT);
            else if (!new File(path_fileout).exists()) {
                file_out = new File(path_fileout);
                new File(file_out.getParent()).mkdirs();
            } else
                file_out = new File(path_fileout);

            extractor.Load(file_in, file_out);
            System.out.print(Messages.getString("flag.execute.processing"));
            extractor.processExtraction(options);
            System.out.print(Messages.getString("done"));
        } catch (IsNotFileException | IOException | PluginNotFoundException
                | FormatNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
