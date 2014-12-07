package plugins;

import java.io.File;

@Pluginspecs(name = "Plugin", 
	version = "1.0.0", 
	description = "Parent class, every plugin should be extended from it", 
	author = "Fati CHEN", 
	dependencies = "none", 
	extensions = "none")
public interface Plugins {
	File file_in;
	File file_out;

	public void Load(File file_in);
	public void processExtraction();
	public File getFileIn();
	public File getFileOut();
	void setFileIn(File file_in);
	void setFileOut(File file_out);
	
	public String toString();
}
