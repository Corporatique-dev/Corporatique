package core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import plugins.*;

public class Install {
	public void install(File ainstaller) {
		String plugins = "plugins";
		String name = (ainstaller.getName().split(".class"))[0];
		String directory = (this.getClass().getResource("Install.class")
				.getPath().split(Install.class.getName()))[0]
				+ plugins + "/" + name + "/";
		
		// Cr�e le chemin 
		if ((new File(
				(this.getClass().getResource("Install.class").getPath()
						.split(Install.class.getName()))[0]
						+ plugins
						+ "/"
						+ name + "/")).mkdirs())
			System.out.println( "chemin cr�e :" + directory);

		String fileout = directory + ainstaller.getName();
		// Affiche le fichier de destination
		System.out.println(fileout);
		File out = new File(fileout);

		try {
			// Copy le plugin a installer dans son dossier
			Files.copy(ainstaller.toPath(), out.toPath());

			Class<?> cl = Class.forName(plugins + "." + name + "." + name);
			Plugins p = (Plugins) cl.newInstance();
			System.out.println("--resultat : " + p.toString());
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

		} catch (IOException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
