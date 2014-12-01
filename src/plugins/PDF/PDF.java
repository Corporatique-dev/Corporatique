package plugins.PDF;

import java.io.File;

import plugins.*;

@Pluginspecs(name = "PDF", version = "1.0.0", description = "Extrait le texte d'un PDF", author = "Fati CHEN", dependencies = { "" }, extensions = { "" })
public class PDF extends Plugins {

	public PDF() {

	}

	public PDF(File fichier_in, File fichier_out) {
		this.setFileIn(fichier_in);
		this.setFileOut(fichier_out);
	}

	@Override
	public void processExtraction() {
		/*
		 * PDDocument pdf; pdf = PDDocument.load(this.getFichier_in());
		 * 
		 * PDFTextStripper stripper = new PDFTextStripper();
		 * 
		 * ExtractText extr = new ExtractText();
		 * 
		 * String[] args = new String[4]; args[0] = "-sort"; args[1] = "-debug";
		 * args[2] = "-html"; args[3] = getFichier_in().getAbsolutePath(); //
		 * args[2] = getFichier_out().getAbsolutePath();
		 * extr.startExtraction(args);
		 * 
		 * System.out.println(stripper.getPageSeparator());
		 * stripper.setPageEnd("ENDPAGE*");
		 * stripper.setPageStart("*$STARTPAGE* ");
		 * stripper.setArticleStart("*$ARTICLEDEBUT*");
		 * stripper.setArticleEnd("ARTICLEFIN*");
		 * stripper.setAddMoreFormatting(true);
		 * 
		 * try (PrintStream out = new PrintStream(new FileOutputStream(
		 * this.getFichier_out()))) { out.println(stripper.getText(pdf));
		 * out.close(); }
		 */
	}

	@Override
	public String toString() {
		return "salut je suis la classe " + this.getClass().getName();
	}
}
