package core;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import core.command.*;

/**
 * Main launcher of the program, list the plugins, their usage and options
 * Redirects to the plugins main, using a config file to do so, sending command
 * parameters too.
 *
 * @author Fati CHEN
 * @version 1.0.0
 */
public class Corporatique {
    public static void main(String[] args) {

        MainCommand mcmd = new MainCommand();
        JCommander cmd = new JCommander(mcmd);
        cmd.setProgramName("Corporatique");

        DeleteCommand dcmd = new DeleteCommand();
        cmd.addCommand("delete", dcmd);
        InstallCommand icmd = new InstallCommand();
        cmd.addCommand("install", icmd);
        UpdateCommand ucmd = new UpdateCommand();
        cmd.addCommand("update", ucmd);
        ExecuteCommand ecmd = new ExecuteCommand();
        cmd.addCommand("execute", ecmd);

        cmd.parse(args);

        if ("delete".equals(cmd.getParsedCommand())) {
            if (dcmd.isHelp()) {
                cmd.usage();
            } else if (dcmd.getPlugin().size() != 1)
                throw new ParameterException("Excepted one path for delete ( has " + dcmd.getPlugin().size() + ")");
            Delete.deletePlugin(dcmd.getPlugin().get(0), dcmd.isDebug());

        } else if ("install".equals(cmd.getParsedCommand())) {
            if (icmd.isHelp()) {
                cmd.usage();
            } else if (icmd.getPlugin().size() != 1)
                throw new ParameterException("Excepted one path for delete ( has " + icmd.getPlugin().size() + ")");
            Install.installPlugin(icmd.getPlugin().get(0), icmd.isDebug());

        } else if ("update".equals(cmd.getParsedCommand())) {
            if (ucmd.isHelp()) {
                cmd.usage();
            } else if (ucmd.getPlugin().size() != 1)
                throw new ParameterException("Excepted one path for update ( has " + ucmd.getPlugin().size() + ")");
            Update.updatePlugin(ucmd.getPlugin().get(0), ucmd.isDebug());
        } else if ("execute".equals(cmd.getParsedCommand())) {
            if (ecmd.isHelp() || args.length == 0) {
                cmd.usage();
            } else {
                int size = ecmd.getPluginorformat().size();
                String plugin_name;
                String filein;
                if (size == 2) {
                    plugin_name = ecmd.getPluginorformat().get(0);
                    filein = ecmd.getPluginorformat().get(1);
                } else if (size == 1) {
                    plugin_name = null;
                    filein = ecmd.getPluginorformat().get(0);
                } else {
                    throw new ParameterException("Excepted plugin name and/or filein (1 or 2 values) got :" + size);
                }
                String table[] = ecmd.getOptions().toArray(new String[ecmd.getOptions().size()]);

                Execute e = new Execute();
                e.executePlugin(plugin_name, ecmd.getFormat(), filein, ecmd.getFileout(), table, ecmd.isDebug());
            }
        } else {
            if (mcmd.isListall())
                System.out.println(OtherActions.listAll());
            else if (mcmd.getDetails() != null)
                System.out.println(OtherActions.pluginDetails(mcmd.getDetails(), ecmd.isDebug()));
            else if (mcmd.getChangeDefault().size() != 0) {
                int result = OtherActions.setDefault(mcmd.getChangeDefault().get(0), mcmd.getChangeDefault().get(1));
                if (result == 0) {
                    System.out.println(Flags.getString("done"));
                } else if (result == 1) {
                    System.out.println(Flags.getString("set.default.already"));
                } else {
                    System.out.println(Flags.getString("set.default.fail"));
                }
            } else {
                cmd.usage();
            }
        }
    }
}