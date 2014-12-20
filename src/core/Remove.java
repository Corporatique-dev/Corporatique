package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Removes the plugin.	Allow you to remove a plugin whet it's not needed anymore
 *
 * @author Fati CHEN
 * @version 1.0
 */
public class Remove {

    public void remove() {
    }

    public boolean remove(String toremove) {

        String configPath = "config";
        Properties properties = new Properties();
        try {
            FileInputStream in = new FileInputStream(configPath);
            properties.load(in);
            in.close();
        } catch (IOException e) {
            System.out.println("Unable to load config file.");
        }

        String fromconf = properties.getProperty(toremove, "notfound");
        if (fromconf != "notfound") {

            String plugins = "plugins";
            String name = (fromconf.split("."))[1];
            String directorypath = (this.getClass().getResource("Remove.class")
                    .getPath().split(Remove.class.getName()))[0]
                    + plugins + "/" + name + "/";
            File directory = new File(directorypath);

            return removeaux(directory);
        }
        return false;
    }

    public boolean removeaux(File directory) {
        boolean resultat = true;
        if (directory.exists()) {
            File[] files = directory.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    resultat &= removeaux(files[i]);
                } else {
                    resultat &= files[i].delete();
                }
            }
        }
        resultat &= directory.delete();
        return (resultat);

    }
}
