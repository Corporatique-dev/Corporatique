package core.command;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fati CHEN
 */
public class MainCommand {
    @Parameter(names = {"-l", "--listall"}, description = "List all the plugins installed")
    private boolean listall = false;

    @Parameter(names = {"-m", "--details"}, description = "Details of the plugin")
    private String details = null;

    @Parameter(names = {"-c", "--change-default"}, description = "Change the default Plugin for a format usage : -c plugin format", arity = 2)
    private List<String> changeDefault = new ArrayList<>();

    public boolean isListall() {
        return listall;
    }

    public String getDetails() {
        return details;
    }

    public List<String> getChangeDefault() {
        return changeDefault;
    }
}
