package core;

import java.io.IOException;
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

                allplugins += description[1].toLowerCase() + "\t" + "v" + description[2];

                String bydefault = EMPTYSTRING;
                String supported = EMPTYSTRING;
                for (int i = 1; i < format.length; i++) {
                    if (format[i].contains(DEFAULT + INTERSEPARATOR))
                        bydefault = " ." + format[i].split(Pattern.quote(INTERSEPARATOR))[1] + "," + bydefault;
                    else supported += " ." + format[i] + ",";
                }

                if (!Objects.equals(bydefault, "")) {
                    bydefault = "\t" + DEFAULT + ":" + bydefault;
                }
                if (!Objects.equals(supported, "")) {
                    supported = "\t" + "supported:" + supported;
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
     * -1 if the format isn't supported by the plugin, or the plugin isn't installed
     */

    public static int setDefault(String plugin_to_set, String format_to_set) {
        try {
            configString = ActionBase.getConfig();


            for (String configline : configString) {
                if (configline.contains(PLUGIN + INTERSEPARATOR + plugin_to_set.toUpperCase() + INTERSEPARATOR)
                        && configline.contains(DEFAULT + INTERSEPARATOR + plugin_to_set.toLowerCase() + SEPARATOR)) {
                    return 1;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * Gives the plugin specifications, used in --help
     *
     * @return The plugin specifications.
     */
    public static String pluginDetails() {
        return "";
    }

}
