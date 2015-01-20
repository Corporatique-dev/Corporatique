package core.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.util.ArrayList;
import java.util.List;

/**
 * Will install the plugin in the given path
 *
 * @author Fati CHEN
 * @version 1.0.0
 */
@Parameters(commandDescription = "Will install the plugin in the given path")
public class InstallCommand {
    @Parameter(description = "Path to the corpoplugin File")
    private List<String> plugin = new ArrayList<>();

    @Parameter(names = {"-v", "-d", "--debug"}, description = "Debug mode")
    private boolean debug = false;

    @Parameter(names = {"-h", "--help"}, help = true)
    private boolean help;

    public List<String> getPlugin() {
        return plugin;
    }

    public boolean isDebug() {
        return debug;
    }

    public boolean isHelp() {
        return help;
    }
}