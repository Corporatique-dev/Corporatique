package core;

/**
 * Main launcher of the program, list the plugins, their usage and options
 * Redirects to the plugins main, using a config file to do so, sending command
 * parameters too.
 *
 * @author Fati CHEN
 * @version 1.0
 */
public class Corporatique {

    public static void main(String[] args) {
        //Test.Install();
        Test.Execute();
    }

    @Override
    public String toString() {
        return "Usage : [plugin, -format, install, remove, update, forceupdate] file-input [-output fileout,-options]";
    }
}