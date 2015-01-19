package core;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Has the mission to parse the command line.
 *
 * @author Fati CHEN
 * @version 1.0.0
 */
public class JCommanderCorporatique {

    @Parameter(names = "-options", description = "Will pass the following parameters to the plugin", variableArity = true)
    public List<String> options = new ArrayList<>();

    @Parameter(names = {"-v", "--debug", "-d"}, description = "Debug mode")
    boolean debug = false;

    @Parameter(names = "--help", help = true)
    private boolean help;

}