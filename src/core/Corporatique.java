package core;

/**
 * Main launcher of the program, list the plugins, their usage and options
 * Redirects to the plugins main, using a config file to do so, sending command
 * parameters too.
 * 
 * 
 * @version 1.0.0 Executer
 * @author chenf
 */
public class Corporatique {

	public static void main(String[] args) {
		//Test.ClassfromName();
		Test.Install();
		
	}

	public String toString() {
		return "Usage : [plugin, -format, install, remove, update, forceupdate] file-input [-output fileout,-options]";
	}
}