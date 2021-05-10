package au.com.vincentbai;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class JavaPdfBoxReadText {
	
	public static void main(String[] args) throws IOException{
		
		File myFile = new File("src/main/resources/2.pdf");
		
		try {
			
			PDDocument doc =  PDDocument.load(myFile);
			
            String text = new PDFTextStripper().getText(doc);
            
            System.out.println("Text size: " + text.length() + " characters:");

            System.out.println(text);

		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
}
