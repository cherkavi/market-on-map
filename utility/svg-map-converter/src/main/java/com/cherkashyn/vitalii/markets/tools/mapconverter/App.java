package com.cherkashyn.vitalii.markets.tools.mapconverter;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * main class for execute main logic of program: <br />
 * 
 */
public class App 
{
    public static void main( String[] args )
    {
    	InputArguments inputData=parseInputParameters(args);
    	File tempFile=null;
    	System.out.println(" - BEGIN -");
    	try{
    		tempFile=File.createTempFile("zurich", "svg");
    		tempFile.deleteOnExit();
    		
    		// stub transformation ( replace parts of file - head and tail )
    		FileUtility.INSTANCE.replaceHeadTail(new File(inputData.pathToSourceFile), tempFile);
    		
    		// xml transformation ( replace some attributes inside elements ) 
    		Document documentForSave=XmlUtility.readDocument(tempFile);
    		documentForSave=XmlUtility.clearUnusedData(documentForSave);
    		
    		// save to output 
			XmlUtility.saveToFile(documentForSave, inputData.pathToDestinationFile);
				
			System.out.println(" check new file: "+inputData.pathToDestinationFile);
    	}catch(IOException ex){
    		System.err.println("IO Exception: "+ex.getMessage());
    	} catch( ParserConfigurationException  te){
    		System.err.println("ParserConfiguration XML: "+te.getMessage());
    	}catch(TransformerException te){
    		System.err.println("Transformer Exception XML: "+te.getMessage());
    	}catch(SAXException te){
    		System.err.println("SAX Exception ( check your XML - for inkscape only !!! ): "+te.getMessage());
    	} finally{
    		if(tempFile!=null){
    			tempFile.delete();
    		}
    	}
		System.out.println("- END - ");
    }
    
    
    private static InputArguments parseInputParameters(String[] args){
        if(args.length<2){
        	System.err.println("Arguments should be: ");
        	System.err.println(" <input file>  <output file> ");
        	System.exit(1);
        }
        return new InputArguments(args[0], args[1]);
    }
    
}

class InputArguments{
	String pathToSourceFile;
	String pathToDestinationFile;
	
	public InputArguments(String pathToSourceFile, String pathToDestinationFile) {
		super();
		this.pathToSourceFile = pathToSourceFile;
		this.pathToDestinationFile = pathToDestinationFile;
	}
	

}