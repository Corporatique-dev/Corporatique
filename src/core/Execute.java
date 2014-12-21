package core;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;
import plugins.Corpoplugins;

import java.io.File;
import java.util.List;

import static net.jcores.CoreKeeper.$;

/**
 * Created by stardisblue on 21/12/2014.
 */
public class Execute {

    public void executePlugin() {
        PluginManager pm = PluginManagerFactory.createPluginManager(); //Setting up the PluginManger from jspf
        PluginManagerUtil pmu = new PluginManagerUtil(pm);
        List<File> list = $("plugins/").file().dir().filter(".*jar$").print().list();
        System.out.println(list);
        for (File file : list) {
            pmu.addPluginsFrom(file.toURI());
        }
        for (Corpoplugins cp : pmu.getPlugins(Corpoplugins.class)) {
            System.out.println(cp.getClass());
        }
    }
}
