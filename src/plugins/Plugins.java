package plugins;

import java.io.File;
@Pluginspecs(
	name ="Plugin",
	version="1.0.0",
	description="Parent class, every plugin should be extended from it",
	author="Fati CHEN",
	dependencies="none",
	extensions="none")
public abstract class Plugins {
	protected File file_in;
	protected File file_out;

	public void Load(File file_in) {
		setFileIn(file_in);
	}

	public void processExtraction() {
	}

	// Getters Setters
	public File getFileIn() {
		return file_in;
	}

	public File getFileOut() {
		return file_out;
	}

	protected void setFileIn(File file_in) {
		this.file_in = file_in;
	}
	
	protected void setFileOut(File file_out){
		this.file_out = file_out;
	}
	
	public String toString(){
		return "salut je suis "+this.getClass().getName();
	}
}
