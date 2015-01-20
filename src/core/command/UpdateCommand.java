package core.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.util.ArrayList;
import java.util.List;

/**
 * Has the mission to parse the command line.
 *
 * @author Fati CHEN
 * @version 1.0.0
 */
@Parameters(commandDescription = "Will update the plugin with the newer one in the given path")
public class UpdateCommand {
    @Parameter(description = "path to the plugin to update")
    private List<String> plugin = new ArrayList<>();

    @Parameter(names = {"-v", "--debug", "-d"}, description = "Debug mode")
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