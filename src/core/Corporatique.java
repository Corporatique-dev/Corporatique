package core;

import java.util.Scanner;

/**
 * Main launcher of the program, list the plugins, their usage and options
 * Redirects to the plugins main, using a config file to do so, sending command
 * parameters too.
 *
 * @author Fati CHEN
 * @version 1.0.0
 */
public class Corporatique {

    private static Scanner sc;

    public static void main(String[] args) {

        /*sc = new Scanner(System.in);
        System.out.println("Choose between : \n" +
                "1: install \n" +
                "2: Execute \n" +
                "3: Remove \n" +
                "4: Update");
        int i = sc.nextInt();
        switch (i) {
            case 1:
                Test.Install();
                break;

            case 2:
                Test.Execute();
                break;

            case 3:
                Test.Remove();
                break;

            case 4:
                Test.Update();
                break;

            default:
                System.out.println(OtherActions.listAll());
                System.out.println(OtherActions.pluginDetails("doc", true));
                break;
        }*/
    }

    @Override
    public String toString() {
        return "Usage : [plugin, -format, install, remove, update, forceupdate -d] file-input [-output fileout,-options String[]]";
    }
}