package core.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.util.ArrayList;
import java.util.List;

/**
 * Will execute process the extraction with the given options
 *
 * @author Fati CHEN
 * @version 1.0.0
 */
@Parameters(commandDescription = "Allows you to extract the text")
public class ExecuteCommand {

    @Parameter(description = "plugin_to_use and File to extract")
    private List<String> pluginorformat;

    @Parameter(names = {"-f", "--format"}, description = "The output file")
    private String format = null;

    @Parameter(names = {"-o", "--output"}, description = "The output file")
    private String fileout = null;

    @Parameter(names = "-options", description = "Will pass the following parameters to the plugin", variableArity = true)
    private List<String> options = new ArrayList<>();

    @Parameter(names = {"-v", "--debug", "-d"}, description = "Debug mode")
    private boolean debug = false;

    @Parameter(names = {"-h", "--help"}, help = true)
    private boolean help;

    public List<String> getPluginorformat() {
        return pluginorformat;
    }

    public String getFormat() {
        return format;
    }

    public String getFileout() {
        return fileout;
    }

    public List<String> getOptions() {
        return options;
    }

    public boolean isDebug() {
        return debug;
    }

    public boolean isHelp() {
        return help;
    }
}