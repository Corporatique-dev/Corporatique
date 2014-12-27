package core;

import java.util.Scanner;

/**
 * Main launcher of the program, list the plugins, their usage and options
 * Redirects to the plugins main, using a config file to do so, sending command
 * parameters too.
 *
 * @author Fati CHEN
 * @version 1.0
 */
public class Corporatique {

    private static Scanner sc;

	public static void main(String[] args) {
        sc = new Scanner(System.in);
        System.out.println("Choose between 1 and 3");
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

            default:
                System.out.println("Number between 1 and 3");
                break;
        }
    }

    @Override
    public String toString() {
        return "Usage : [plugin, -format, install, remove, update, forceupdate] file-input [-output fileout,-options]";
    }
}