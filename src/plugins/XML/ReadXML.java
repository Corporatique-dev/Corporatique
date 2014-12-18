package plugins.XML;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import plugins.CorpoPlugins;

public class ReadXML{

	private static int BUF_SIZE = 1024;
	private static String SYS_KEY_SEP = System.getProperty("file.separator");
	private static String REGEX_TAG = "<[^>]+>";
	private static String TAG_REPLACE = "\n";
	//String PS = "[\)][^\(]+\(|\)[^)]+$|^[^(]+\(";
	
	public ReadXML(){}
	
	public void extractTextRegex(String in,String encIn) throws IOException{
		String out = in.substring(0, in.lastIndexOf('.'))+".txt";
		System.out.println(out);
		extractTextRegex(in,out,encIn,"UTF-8");
	}
	
	public void extractTextRegex(String in, String out, String encIn, String encOut) throws IOException{
		
		File pathf = new File(out.substring(0, out.lastIndexOf(SYS_KEY_SEP))); // on prends le parent
		if(!pathf.exists()){
			pathf.mkdirs();
		}
		FileInputStream fis = new FileInputStream(in);
		InputStreamReader isr = new InputStreamReader(fis,encIn);
		BufferedReader br = new BufferedReader(isr,BUF_SIZE);
		FileOutputStream fos = new FileOutputStream(out);
		OutputStreamWriter osw = new OutputStreamWriter(fos,encOut);
		BufferedWriter bw = new BufferedWriter(osw);
		
		String line;
		while((line = br.readLine()) != null){
				String newline = line.replaceAll(this.REGEX_TAG,this.TAG_REPLACE).replaceAll("[\n]+","\n");
				if(!newline.isEmpty()){
					//System.out.print(newline);
					bw.write(newline);
				}
		}
		br.close();
		bw.close();
	}
	
	
	
		
		
}
	
