package au.com.vincentbai;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TesseractDemo {
	
	public static void main(String[] args) {
		
		File myFile = new File("src/main/resources/1.pdf");
		ITesseract instance = new Tesseract();  // JNA Interface Mapping
		// ITesseract instance = new Tesseract1(); // JNA Direct Mapping
		instance.setDatapath("D:/Program Files/Java/OCR/tessdata"); // path to tessdata directory
		
		String result = null;
		
		try {
			
		    result = instance.doOCR(myFile);
		    System.out.println(result);
		    
		} catch (TesseractException e) {
			    System.err.println(e.getMessage());
		}
		
		String regexString = "Job ID: \\d+|Job Id: \\d+|\\$\\d+.\\d+";
		
		ArrayList<String> al = retrieveDateFromString(result, regexString);
		
		
		saveAsCSV(al, "D:\\Users\\korio\\Desktop\\bigPost.csv");
		
	}
	
	public static ArrayList<String> retrieveDateFromString(String orginalString, String regexString){
		
		Pattern p = Pattern.compile(regexString);
		
		Matcher m = p.matcher(orginalString);
		
		System.out.println(m);
		
		ArrayList<String> al = new ArrayList<String>();
		
		Boolean isJobIdAdded = false;
				
		String temp = new String();
		
	    while(m.find()) {
	    	
	    	if(m.group().length()!=0) {
	    		
	    		if(m.group().trim().startsWith("Job Id:") || m.group().trim().startsWith("Job ID:")) {
	    			
	    			temp = m.group().trim().toString();
	    			
	    			temp = temp.substring(temp.indexOf(":") + 1);
	    			
	    			isJobIdAdded = true;
	    			
	    		}else {
	    			
	    			if(isJobIdAdded) {
	    				
	    				// ArrayList<String[]> 结果为 null
	    				
//	    				temp[1] = m.group().trim().toString();
//	    				
//	    				al.add(temp);
//	    				
//	    				isJobIdAdded = false;
//	    				
//	    				temp[0] = null;
//	    				temp[1] = null;
	    				
	    				temp = temp + "," + m.group().trim().toString().substring(1);
	    				
	    				isJobIdAdded = false;
	    				
	    				al.add(temp);
	    				
	    				temp = "";
	    				
	    			} else {
	    				
	    				break;
	    				
	    			}
	    		}
	    	}
	    }
		
		return al;
		
	}
	
	
	
	public static void saveAsCSV(ArrayList<String> al, String filePath) {
		
		try {
			
			FileWriter fw = new FileWriter(filePath, true);
			
			BufferedWriter bw = new BufferedWriter(fw);
			
			PrintWriter pw = new PrintWriter(bw);
				
			for(String temp: al) {
				
				pw.println(temp);
				
			}
			
			pw.flush();
			pw.close();
			
			System.out.println("Done");
			
		}catch(Exception E) {
			
			System.out.println("Error");
		}
		
	}
}
