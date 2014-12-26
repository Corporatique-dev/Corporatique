package core;

import plugins.Corpoplugins;

import java.util.Objects;
import java.util.Scanner;

/**
 * Provisory class which allows to test differents components of corporatique
 *
 * @author Fati CHEN
 * @version 1.0
 */
public class Test {

	static Scanner sc = new Scanner(System.in);

	// Test du PDF
	public static void ClassfromName() {
		System.out.println("-========-Test Classe from String");
		try {
			Class<?> cl = Class.forName("plugins.PDF.PDF");
			Corpoplugins p = (Corpoplugins) cl.newInstance();
			System.out.println("--resultat : " + p.toString());

		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void Install() {
		System.out.println("-=========-Test Installation");
		Scanner sc = new Scanner(System.in);
		String dir = sc.next();
		Install j = new Install();
		j.installPlugin(dir);
		sc.close();
	}

	public static void Execute() {
		System.out.println("-=========-Test Extraction");
		Scanner sc = new Scanner(System.in);
		String filein = sc.next();
		String file_out = sc.next();
		sc.close();
		filein = filein.replaceAll("\"", "");
		file_out = file_out.replaceAll("\"", "");
		if (Objects.equals(file_out, "null"))
			file_out = null;
		Execute e = new Execute();
		e.executePlugin(null, null, filein, file_out, null);

	}
}
