package com.cherkashyn.vitalii.markets.tools.mapconverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class FileUtility {
	public static FileUtility INSTANCE=new FileUtility();
	/** path to classpath file, which contains HEAD information for SVG file */
	final static String FILE_HEAD="head.svg";
	/** path to classpath file, which contains TAIL information for SVG file */
	final static String FILE_TAIL="tail.svg";
	
	private FileUtility(){
	}
	
	public void replaceHeadTail(File inputData, File outputData) throws IOException{
		InputStream inputStream=new FileInputStream(inputData);
		OutputStream outputStream=new FileOutputStream(outputData);
		try{
			InputStream headInputStream=this.getClass().getClassLoader().getResource(FILE_HEAD).openStream();
			InputStream tailInputStream=this.getClass().getClassLoader().getResource(FILE_TAIL).openStream();
			
			replaceHeadTail(inputStream, headInputStream, tailInputStream, outputStream);
			outputStream.flush();
		}finally{
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(outputStream);
		}
	}
	
	/**
	 * @param rawFile - file from external GraphicEditor ( like INKSCAPE )
	 * @param headFile - file with XML header 
	 * @param tailFile - file with XML tailer
	 * @param destinationFile - destination point for print output data
	 * @throws IOException 
	 */
	public void replaceHeadTail(InputStream rawFile, InputStream headFile, InputStream tailFile, OutputStream destinationFile) throws IOException{
		// head
		copyHead(headFile, destinationFile);
		
		// read file, try to find first "<path>" element
		copyBody(rawFile, destinationFile);

		// tail
		copyTail(tailFile, destinationFile);
		
		IOUtils.closeQuietly(destinationFile);
	}

	private final static String TAG_PATH="<path";
	private final static String TAG_SVG="</svg>";
	private final static byte[] BREAK_LINE="\n".getBytes();
	
	private void copyBody(InputStream rawFile, OutputStream destinationFile) throws IOException {
		BufferedReader reader=new BufferedReader(new InputStreamReader(rawFile));

		boolean findPreambula=true;
		String currentLine=null;
		while((currentLine=reader.readLine())!=null){
			if(findPreambula){
				// find first tag "<path"
				StringPair splitString=splitString(currentLine, TAG_PATH);
				if(splitString.second!=null){
					findPreambula=false;
					destinationFile.write(splitString.second.getBytes());
					destinationFile.flush();
				}
			}else{
				// find first tag "</svg>"
				StringPair splitString=splitString(currentLine, TAG_SVG);
				if(splitString.first!=null){
					destinationFile.write(splitString.first.getBytes());
				}
				if(splitString.second!=null){
					IOUtils.closeQuietly(rawFile);
					break;
				}
			}
			destinationFile.write(BREAK_LINE);
		}
	}

	private StringPair splitString(String currentLine, String tagPath) {
		StringPair returnValue=new StringPair();
		returnValue.first=StringUtils.substringBefore(currentLine, tagPath);
		if(returnValue.first.length()==currentLine.length()){
			returnValue.second=null;
		}else{
			returnValue.second=StringUtils.substring(currentLine, returnValue.first.length());
		}
		return returnValue;
	}

	void copyHead(InputStream headFile, OutputStream destinationFile) throws IOException {
		copy(headFile, destinationFile);
	}
	void copyTail(InputStream headFile, OutputStream destinationFile) throws IOException {
		copy(headFile, destinationFile);
	}
	
	void copy(InputStream source, OutputStream destination) throws IOException {
		IOUtils.copy(source, destination);
		destination.flush();
		IOUtils.closeQuietly(source);
	}
}


class StringPair extends Pair<String, String>{
}

class Pair<T, Z>{
	T first;
	Z second;
}