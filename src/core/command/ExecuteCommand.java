package core.command;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Will execute process the extraction with the given options
 *
 * @author Fati CHEN
 * @version 1.0.0
 */
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

    @Parameter(names = {"-l", "--listall"}, description = "List all the plugins installed")
    private boolean listall = false;

    @Parameter(names = {"-m", "--details"}, description = "Details of the plugin")
    private String details = null;

    @Parameter(names = {"-c", "--change-default"}, description = "Change the default Plugin for a format usage : -c plugin format", arity = 2)
    private List<String> setDefault = new ArrayList<>();

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

    public boolean isListall() {
        return listall;
    }

    public String getDetails() {
        return details;
    }

    public List<String> setDefault() {
        return setDefault;
    }
}