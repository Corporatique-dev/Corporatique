package core.command;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.util.List;

/**
 * Will delete the plugin with the given name
 *
 * @author Fati CHEN
 * @version 1.0.0
 */
@Parameters(commandDescription = "Will delete the plugin with the give name")
public class DeleteCommand {
    @Parameter(description = "Name of the Corpoplugin to delete")
    private List<String> plugin;

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