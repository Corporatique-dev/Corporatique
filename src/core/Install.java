package core;

import java.io.File;
import java.lang.annotation.Annotation;

import plugins.Pluginspecs;
import plugins.PDF.PDF;

public class Install {
	public static void install(File plugin) {
		PDF test = new PDF();
		Pluginspecs specs = test.getClass().getAnnotation(Pluginspecs.class);

		System.out.println(specs.name());
		System.out.println(specs.version());
		System.out.println(specs.author());
		System.out.println(specs.description());
		for (String i : specs.extensions()) {
			System.out.println(i+" ");
		}
		for (String j : specs.dependencies()) {
			System.out.print(j+" ");
		}
	}
}
