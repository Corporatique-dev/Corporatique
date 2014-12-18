package plugins;

import net.xeoh.plugins.base.Plugin;

import java.io.File;

@Pluginspecs(
        name = "Plugin",
        version = "1.0.0",
        description = "Interface, every plugin should implement it",
        author = "Fati CHEN",
        dependencies = "none",
        extensions = "none")
public interface Corpoplugins extends Plugin {
    File file_in;
    File file_out;

    /**
     * Loading the selected file in the plugin and sets the output file
     * (security measures)
     *
     * @param file_in File to load
     * @return void
     */
    public void Load(File file_in, File file_out);

    /**
     * Process the text extraction to file_out with options if necessary
     *
     * @param options eventuals options of the plugin
     * @return void
     */
    public void processExtraction(String[] options);

    /*-------------------
     * Getters - Setters - toString
     */
    public File getFileIn();

    public File getFileOut();

    void setFileIn(File file_in);

    void setFileOut(File file_out);

    public String toString();
}
