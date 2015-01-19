package plugins.doc;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import plugins.Corpoplugins;
import plugins.Pluginspecs;

import java.io.*;

@Pluginspecs(
        name = "DOC",
        version = "1.0.0",
        description = "Extracts text from .doc Files",
        author = "Maxime CHAPUIS",
        dependencies = "",
        extensions = "doc")
@PluginImplementation

public class DOC implements Corpoplugins {

    private static int BUF_SIZE = 4096;
    private File in;
    private File out;

    public DOC() {
    }

    @Override
    public void Load(File file_in, File file_out) {
        this.setFileIn(file_in);
        this.setFileOut(file_out);
    }

    @Override
    public void processExtraction(String[] options) throws IOException {
        FileReader fis = new FileReader(this.in);
        BufferedReader br = new BufferedReader(fis, BUF_SIZE);   //Le buffer correspondant au fichier d'entr� (le .doc)
        FileWriter fw = new FileWriter(this.out);
        BufferedWriter bw = new BufferedWriter(fw);   //Le buffer correspondant au fichier de sortie (le .txt)
        int nread;
        char[] chars = new char[BUF_SIZE];   //Le tableau de caract�res dans lequel sont rang� apr�s la lecture
        boolean text = false;   //vrai si le d�but du texte a �t� d�t�ct�
        boolean endOfText = false;   //vrai si la fin du texte a �t� d�t�ct�
        boolean header = true;   //faux si la fin du header a �t� d�t�ct�
        boolean firstLine = false;   //faux tant que la premi�re ligne du texte n'a pas �t� �crite.
        while ((nread = br.read(chars, 0, BUF_SIZE)) > 0) {   //Tant qu'il y a des caract�res � lire
            StringBuilder strb = new StringBuilder(); //Le StringBuilder correspondant au texte (les donn�es textuelles qui seront dans le .txt)
            StringBuilder strb_header = new StringBuilder(); //Le StringBuilder correspondant au header, il servira � retrouver la premi�re ligne du texte.
            for (int i = 0; i < nread; i++) { //Parcours du tableau chars
                if (!text && chars[i] == 13) { //Recherche du premier caract�re 'CR' (13 en int) (CR correspont � un retour chariot).
                    //A cause de cela la premi�re ligne est saut�, elle sera gr�ce au strb_header.
                    text = true;
                }
                if (text && !endOfText && chars[i] == 3) { //Recherche du premier END OF TEXT apr�s le d�but du texte (3 en int) pour savoir quand s'arr�te le texte.
                    endOfText = true;
                }
                if (text && !endOfText) { //Si le texte a commenc� et que la fin n'a pas encore �t� trouv�e,
                    header = false;
                    if (!Character.isISOControl(chars[i]) || Character.isWhitespace(chars[i])) {
                        //le caract�re est ajout� � strb si ce n'est pas un caract�re de controle
                        strb.append(chars[i]);
                    }
                }
                if (header) { //Si la fin du header n'a pas �t� trouv�e,
                    //le caract�re est ajout� � strb_header
                    strb_header.append(chars[i]);
                }
            }
            //Fin de la boucle de traitement du tableau chars

            if (!header && !firstLine) { //Si le header est termin� et que la premi�re ligne n'a pas �t� �crite
                String head = strb_header.toString();
                int n = head.lastIndexOf('\0'); //Recherche du dernier nul du header, apr�s celui ci, c'est la premi�re ligne de texte.
                String firstL = head.substring(n + 1); //+1 car on ne veut pas le nul
                bw.write(firstL); //Ecriture la premi�re ligne dans le fichier txt
                firstLine = true;
            }
            bw.write(strb.toString()); //Ecriture des donn�es textuelles dans le fichier txt
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

