package core;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import plugins.Corpoplugins;
import plugins.Pluginspecs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
        String path = Corporatique.class.getProtectionDomain().getCodeSource().getLocation().getPath();


        File config = new File(path + File.separator + ".properties");
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

    public void installPlugin(/*TODO: File toinstall*/) {
        //Setting up the Plugin thing
        PluginManager pm = PluginManagerFactory.createPluginManager();
        // adding a plugin from a path
        System.out.println(new File("plugins/doc/").toURI());
        pm.addPluginsFrom(new File("./plugins/doc/").toURI());
        // getting the plugin
        Corpoplugins extension = pm.getPlugin(Corpoplugins.class);
        System.out.println(extension.getClass());
        // if it has the annotations
        Pluginspecs specs = extension.getClass().getAnnotation(Pluginspecs.class);

        extension.toString();

        // TODO: delete Test Output specifications
        System.out.println(specs.name());
        System.out.println(specs.version());
        System.out.println(specs.author());
        System.out.println(specs.description());
        for (String i : specs.extensions()) {
            System.out.println(i + " ");
        }
        for (String j : specs.dependencies()) {
            System.out.print(j + " ");
        }
    }
}
