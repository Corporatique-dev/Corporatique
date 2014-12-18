package core;

import java.io.File;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import plugins.*;

public class Install {
    public void install(File toinstall) {
        //
        PluginManager pm = PluginManagerFactory.createPluginManager();

        // and then one or more of these ..
        pm.addPluginsFrom(toinstall.toURI());

        Corpoplugins extension = pm.getPlugin(Corpoplugins.class);

        Pluginspecs specs = p.getClass().getAnnotation(extension.getClass());

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
        // System.out.println("chemin cr�e :" + directory);
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
