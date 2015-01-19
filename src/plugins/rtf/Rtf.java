package plugins.rtf;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import org.apache.commons.io.FileUtils;
import plugins.Corpoplugins;
import plugins.Pluginspecs;
import plugins.xml.Messages;

import java.io.File;
import java.io.IOException;

/**
 * @author Loris NORSIC
 *         This plugin enables to convert a .rtf file in .txt file
 */

@Pluginspecs(
        name = "Rtf",
        version = "1.0.0",
        description = "Extracts text from .rtf Files",
        author = "Loris Norsic",
        dependencies = "",
        extensions = "rtf")
@PluginImplementation

/**
 * @author Kaforo
 *This class implements Corpoplugins and uses theses methods
 */
public class Rtf implements Corpoplugins {
    protected static int BUF_SIZE = 4096;
    private File in;
    private File out;


    /**
     * @author Kaforo
     * The strings REGEX contain the regulars expressions and the Strings REPLACE contain the Strings which replace the regex
     */
    private String REGEX = Messages.getString("rtf.0");// //$NON-NLS-1$
    private String REGEX2 = Messages.getString("rtf.1"); //$NON-NLS-1$
    private String REGEX3 = Messages.getString("rtf.2"); //$NON-NLS-1$
    private String REGEX4 = Messages.getString("rtf.3"); //$NON-NLS-1$
    private String REGEX5 = Messages.getString("rtf.4"); //$NON-NLS-1$
    private String REGEX6 = Messages.getString("rtf.5"); //$NON-NLS-1$
    private String REPLACE = Messages.getString("rtf.6"); //$NON-NLS-1$
    private String REPLACE2 = Messages.getString("rtf.7"); //$NON-NLS-1$
    private String REPLACE3 = Messages.getString("rtf.8"); //$NON-NLS-1$

    public void Load(File file_in, File file_out) {
        this.setFileIn(file_in);
        this.setFileOut(file_out);
    }

    /**
     * @author Kaforo
     * This method recover the file in and the text in this file is transformed in String. The String is treated by regex.
     */
    public void processExtraction(String[] options) throws IOException {

        String airlines = FileUtils.readFileToString(in); //File is transformed in String
        String text = airlines.replaceAll(this.REGEX, this.REPLACE2); //head of file is delete
        String tab[] = text.split(Messages.getString("rtf.9")); //$NON-NLS-1$


        FileUtils.deleteQuietly(out);//Delete the file with the same name
        for (int i = 0; i < tab.length; i++) { //REGEX used for each line of String tab[]
            String newline = tab[i].replaceAll(this.REGEX2, this.REPLACE);
            newline = newline.replaceAll(this.REGEX3, this.REPLACE2);
            newline = newline.replaceAll(this.REGEX4, this.REPLACE2);
            newline = newline.replaceAll(this.REGEX5, this.REPLACE3);
            newline = newline.replaceAll(this.REGEX6, this.REPLACE3);
            tab[i] = newline;
            FileUtils.write(out, tab[i], true);
        }
    }

    void setFileIn(File file_in) {
        this.in = file_in;
    }

    void setFileOut(File file_out) {
        this.out = file_out;
    }
}
