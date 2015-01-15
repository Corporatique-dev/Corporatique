package core;

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

    public static void Install() {
        System.out.print("-=========-Test Installation\n" +
                "path to plugin to install :");
        String dir = sc.next();
        System.out.print("debug ?");
        Install.installPlugin(dir, sc.nextBoolean());
    }

    public static void Execute() {
        System.out.print("-=========-Test Extraction\n" +
                "name of the plugin to use :");
        String plugin_name = sc.next();
        if (Objects.equals(plugin_name, "null")) plugin_name = null;

        System.out.print("name of format:");
        String format = sc.next();
        if (Objects.equals(format, "null")) format = null;

        System.out.print("path to filein:");
        String filein = sc.next();
        if (Objects.equals(filein, "null")) filein = null;

        System.out.print("path to filein:");
        String fileout = sc.next();
        if (Objects.equals(fileout, "null")) fileout = null;

        System.out.print("debug");
        boolean debug = sc.nextBoolean();
        sc.close();

        if (filein !=null) filein = filein.replaceAll("\"", "");
        if (fileout !=null) fileout = fileout.replaceAll("\"", "");

        if (Objects.equals(plugin_name, "null"))
            plugin_name = null;
        Execute e = new Execute();
        e.executePlugin(plugin_name, format, filein, fileout, null, debug);

    }

    public static void Remove() {
        System.out.print("-=========-Test deletion\n" +
                "path to plugin to remove :");
        String plugin_to_remove = sc.next();
        System.out.print("debug ?");
        Remove.removePlugin(plugin_to_remove, sc.nextBoolean());
    }

    public static void Update() {
        System.out.println("-=========-Test Update\n" +
                "path to plugin to update :");
        String plugin_to_update = sc.next();
        System.out.print("debug ?");
        Update.updatePlugin(plugin_to_update, sc.nextBoolean());
    }
}
