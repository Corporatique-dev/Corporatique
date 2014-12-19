package core;

import Exceptions.PluginIsInstalledException;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import plugins.Corpoplugins;
import plugins.Pluginspecs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Install {
    public void install(File toinstall) {
        //Setting up the Plugin thing
        PluginManager pm = PluginManagerFactory.createPluginManager();
        // adding a plugin from a path
        pm.addPluginsFrom(toinstall.toURI());
        // getting the plugin
        Corpoplugins extension = pm.getPlugin(Corpoplugins.class);
        // if it has the annotations
        Pluginspecs specs = extension.getClass().getAnnotation(Pluginspecs.class);

        // initialisation of properties
        Properties prop = new Properties();
        InputStream input;

        try {
            input = new FileInputStream("config.properties".replace);
            prop.load(input);

            if (prop.get(specs.name()) != null) {
                throw new PluginIsInstalledException();
            }
            prop.propertyNames()

        } catch (IOException | PluginIsInstalledException e) {
            e.printStackTrace();
        }

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

        // String plugins = "plugins";
        // String name = (toinstall.getName().split(".class"))[0];
        // String directory = (this.getClass().getResource("Install.class")
        // .getPath().split(Install.class.getName()))[0]
        // + plugins + "/" + name + "/";
        //
        // // Cr�e le chemin
        // if ((new File(
        // (this.getClass().getResource("Install.class").getPath()
        // .split(Install.class.getName()))[0]
        // + plugins
        // + "/"
        // + name + "/")).mkdirs())
        // System.out.println("chemin crée :" + directory);
        //
        // String fileout = directory + toinstall.getName();
        // // Affiche le fichier de destination
        // System.out.println(fileout);
        // File out = new File(fileout);
        //
        // try {
        // // Copy le plugin a installer dans son dossier
        // Files.copy(toinstall.toPath(), out.toPath());
        //
        // Class<?> cl = Class.forName(plugins + "." + name + "." + name);
        // Corpoplugins p = (Corpoplugins) cl.newInstance();
        // System.out.println("--resultat : " + p.toString());

        //
        // } catch (IOException | ClassNotFoundException |
        // InstantiationException
        // | IllegalAccessException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }

    }
}
