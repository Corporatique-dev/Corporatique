package plugins.doc;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import plugins.Corpoplugins;
import plugins.Pluginspecs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Pluginspecs(
        name = "Doc",
        version = "1.0.0",
        description = "Extracts text from .doc Files",
        author = "Maxime CHAPUIS",
        dependencies = "none",
        extensions = "none")
@PluginImplementation
public class Doc implements Corpoplugins {

    protected static int BUF_SIZE = 4096;
    private File in;
    private File out;

    @Override
    public void Load(File file_in, File file_out) {
        this.in = new File(in.getAbsolutePath());
        this.out = new File(out.getAbsolutePath());

    }

    @Override
    public void processExtraction(String[] options) throws IOException {
        // TODO Auto-generated method stub
        FileReader fis = new FileReader(this.in);
        BufferedReader br = new BufferedReader(fis, BUF_SIZE);
        FileWriter fw = new FileWriter(this.out);
        BufferedWriter bw = new BufferedWriter(fw);
        int nread;
        char[] chars = new char[BUF_SIZE];
        boolean text = false;
        boolean endOfText = false;
        boolean header = true;
        boolean firstLine = false; //Est ce qu'on a d�j� ajout� la premi�re ligne
        while ((nread = br.read(chars, 0, BUF_SIZE)) > 0) {
            StringBuilder strb = new StringBuilder();
            StringBuilder strb_header = new StringBuilder();
            for (int i = 0; i < nread; i++) {
                if (!text && chars[i] == 13) { //On cherche le premier CR (mais on loupe la premi�re ligne du coup). Mise en place de bytes_header pour retrouver la ligne 1
                    text = true;
                }
                if (text && !endOfText && chars[i] == 3) { //On cherche END OF TEXT pour savoir quand arreter
                    endOfText = true;
                }
                if (text && !endOfText) {
                    //System.out.print(chars[i]);
                    header = false;
                    if (!Character.isISOControl(chars[i]) || Character.isWhitespace(chars[i])) { //On ajoute pas les caract�res de controles
                        strb.append(chars[i]);
                    }
                }
                if (header) {
                    strb_header.append(chars[i]);
                }
            }


            if (!header && !firstLine) {
                String head = strb_header.toString();
                int n = head.lastIndexOf('\0'); // On cherche le dernier nul du header, apr�s cela nous avons la premi�re ligne de texte
                String firstL = head.substring(n + 1); //+1 car on ne veut pas le nul
                bw.write(firstL);
                firstLine = true;
            }
            bw.write(strb.toString());
        }

        br.close();
        bw.close();
    }

    @Override
    public File getFileIn() {
        // TODO Auto-generated method stub
        return this.in;
    }

    @Override
    public File getFileOut() {
        return this.out;
    }

    @Override
    public void setFileIn(File file_in) {
        this.in = file_in;

    }

    @Override
    public void setFileOut(File file_out) {
        this.out = file_out;

    }
}

