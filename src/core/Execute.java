package core;

import exceptions.FormatNotFoundException;
import exceptions.IsNotFileException;
import exceptions.PluginNotFoundException;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import org.apache.commons.io.FileUtils;
import plugins.Corpoplugins;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Fati CHEN
 * @version 1.0.0
 */
public class Execute {

    private static final String TXT = ".txt";
    private static final String INTERSEPARATOR = ">";
    private static final String DEFAULT = "default";
    private static final String PLUGIN = "plugin";
    private static final String SEPARATOR = "|";
    private static final String PLUGINDIRECTORY = "plugins/";
    private final File config = new File("Corporatique.properties");

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
     * Will process the Text extraction depending on the given parameters.
     * <p>Will get the appropriate Corpoplugin by analysing the user input</p>
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
            PluginManager pm = PluginManagerFactory.createPluginManager(); // Setting up the PluginManager from jspf
            config_strings = this.getConfig();
            String plugin_from_conf = null;

            /* NO USE FOUND YET
            if (plugin_name != null && format != null) {    // if the plugin name AND the file format is given
                for (String config_line : config_strings) {
                    if (config_line.matches(PLUGIN + INTERSEPARATOR + plugin_name.toUpperCase() + INTERSEPARATOR) && (config_line.matches(SEPARATOR + DEFAULT + INTERSEPARATOR + format.toLowerCase() + SEPARATOR) || config_line.matches(SEPARATOR + format.toLowerCase() + SEPARATOR))) {
                        plugin_from_conf = config_line;
                        break;
                    }
                }
                if (plugin_from_conf == null) throw new PluginNotFoundException(plugin_name);
            } else*/
            if (plugin_name != null) {               // if the plugin name is given
                for (String config_line : config_strings) {
                    if (config_line.contains(PLUGIN + INTERSEPARATOR + plugin_name.toUpperCase() + INTERSEPARATOR)) {
                        plugin_from_conf = config_line;
                        break;
                    }
                }
                if (plugin_from_conf == null) throw new PluginNotFoundException(plugin_name);
            } else if (format != null) {                    // if the file format is given
                for (String config_line : config_strings) {
                    if (config_line.contains(SEPARATOR + DEFAULT + INTERSEPARATOR + format.toLowerCase() + SEPARATOR)) {
                        plugin_from_conf = config_line;
                        break;
                    } else if (config_line.contains(SEPARATOR + format.toLowerCase() + SEPARATOR))
                        plugin_from_conf = config_line;
                }
                if (plugin_from_conf == null) throw new FormatNotFoundException(format);
            } else {                                        // is the format nor the plugin is given, searching from file extension
                String[] filesplitter = path_filein.split("\\.");
                if (filesplitter.length <= 1)
                    throw new FormatNotFoundException(filesplitter[0]);
                String file_format = filesplitter[filesplitter.length - 1];
                for (String config_line : config_strings) {
                    if (config_line.contains(SEPARATOR + DEFAULT + INTERSEPARATOR + file_format.toLowerCase())) {
                        plugin_from_conf = config_line;
                        break;
                    } else if (config_line.contains(SEPARATOR + file_format.toLowerCase() + SEPARATOR))
                        plugin_from_conf = config_line;
                }
                if (plugin_from_conf == null) throw new FormatNotFoundException(file_format);
            }

            String plugin = plugin_from_conf.split(INTERSEPARATOR)[1];
            pm.addPluginsFrom(new File(PLUGINDIRECTORY + plugin).toURI());

            Corpoplugins extractor = pm.getPlugin(Corpoplugins.class);

            File file_out, file_in;

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

            extractor.setFileIn(file_in);
            extractor.setFileOut(file_out);

            System.out.print("Beginning the Text extraction");
            extractor.processExtraction(options);
            System.out.print("...OK");
        } catch (IsNotFileException | IOException | PluginNotFoundException | FormatNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
