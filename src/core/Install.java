package core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import plugins.*;

public class Install {
	public void install(File ainstaller) {
		//
		PluginManager pm = PluginManagerFactory.createPluginManager();

		// and then one or more of these ..
		pm.addPluginsFrom(ainstaller.toURI());

		Corpoplugins extention = pm.getPlugin(Corpoplugins.class);

		Pluginspecs specs = p.getClass().getAnnotation(Pluginspecs.class);

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
		// String name = (ainstaller.getName().split(".class"))[0];
		// String directory = (this.getClass().getResource("Install.class")
		// .getPath().split(Install.class.getName()))[0]
		// + plugins + "/" + name + "/";
		//
		// // Crée le chemin
		// if ((new File(
		// (this.getClass().getResource("Install.class").getPath()
		// .split(Install.class.getName()))[0]
		// + plugins
		// + "/"
		// + name + "/")).mkdirs())
		// System.out.println("chemin crée :" + directory);
		//
		// String fileout = directory + ainstaller.getName();
		// // Affiche le fichier de destination
		// System.out.println(fileout);
		// File out = new File(fileout);
		//
		// try {
		// // Copy le plugin a installer dans son dossier
		// Files.copy(ainstaller.toPath(), out.toPath());
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
