package core;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Fati CHEN
 * @version 1.0.0
 */
public abstract class ActionBase {
    protected static final String TXT = Messages.getString("file.txt");
    protected static final String FILESPITTER = Messages.getString("file.splitter");
    protected static final String DOUBLEQUOTE = Messages.getString("file.replaceall");
    protected static final String EMPTYSTRING = "";
    protected static final String INTERSEPARATOR = Messages.getString("properties.interseparator");
    protected static final String DEFAULT = Messages.getString("properties.default");
    protected static final String PLUGIN = Messages.getString("properties.name");
    protected static final String SEPARATOR = Messages.getString("properties.separator");
    protected static final String PLUGINDIRECTORY = Messages.getString("plugin.directory");
    protected final File config = new File(Messages.getString("properties.file"));
    protected String[] configString;

    /**
     * Extracts the properties in a [ plugin | format ] structure.
     *
     * @return String[] properties of the configuration file
     * @throws java.io.IOException
     */
    protected String[] getConfig() throws IOException {
        if (config.createNewFile())
            return null;
        String[] configs;

        List<String> airlines = FileUtils.readLines(config);

        configs = new String[airlines.size()];
        airlines.toArray(configs);

        return configs;
    }
}
