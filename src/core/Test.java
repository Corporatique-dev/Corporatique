package core;

import java.io.File;
import java.util.Scanner;

import plugins.Plugins;

public class Test {

	static Scanner sc = new Scanner(System.in);

	// Test du PDF
	public static void ClassfromName() {
		System.out.println("-========-Test Classe from String");
		try {
			Class<?> cl = Class.forName("plugins.PDF.PDF");
			Plugins p = (Plugins) cl.newInstance();
			System.out.println("--resultat : " + p.toString());

		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void Install() {
		System.out.println("-========-Test Installation");
		File fl = new File(
				"C:\\Users\\Fati\\Documents\\Eclipse\\workspace\\Corporatique\\src\\plugins\\PDF\\PDF.java");
		Install.install(fl);

	}
}
