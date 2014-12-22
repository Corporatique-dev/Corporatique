package core;

import plugins.Corpoplugins;

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

        /*Execute e = new Execute();
        e.executePlugin("Doc");*/

    }
}
