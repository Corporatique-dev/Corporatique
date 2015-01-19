
package plugins.xml;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import plugins.Corpoplugins;
import plugins.Pluginspecs;

import java.io.*;

@Pluginspecs(
        name = "XML",
        version = "1.0.0",
        description = "Extracts text from .xml Files",
        author = "Maxime CHAPUIS",
        dependencies = "",
        extensions = "xml")
@PluginImplementation

public class XML implements Corpoplugins {

    private File in;
    private File out;

    private static int BUF_SIZE = 1024;
    private static String SYS_KEY_SEP = System.getProperty(Messages.getString("XML.0")); //$NON-NLS-1$
    private static String REGEX_TAG = Messages.getString("XML.1"); //$NON-NLS-1$
    private static String SYS_LINE_SEP = System.getProperty(Messages.getString("XML.2")); //$NON-NLS-1$

    public XML() {

    }

    @Override
    public void Load(File file_in, File file_out) {
        this.setFileIn(file_in);
        this.setFileOut(file_out);
    }

    @Override
    public void processExtraction(String[] options) throws IOException {

        FileReader fis = new FileReader(this.in);
        BufferedReader br = new BufferedReader(fis, BUF_SIZE);
        FileWriter fw = new FileWriter(this.out);
        BufferedWriter bw = new BufferedWriter(fw);

        String line;
        while ((line = br.readLine()) != null) {
            String newline = line.replaceAll(XML.REGEX_TAG, XML.SYS_LINE_SEP).replaceAll(Messages.getString("XML.3"), Messages.getString(Messages.getString("XML.4"))); //$NON-NLS-1$ //$NON-NLS-2$
            if (!newline.isEmpty()) {
                bw.write(newline);
            }
        }
        br.close();
        bw.close();
    }

    public void setFileIn(File file_in) {
        this.in = file_in;

    }

    public void setFileOut(File file_out) {
        this.out = file_out;

    }
}

