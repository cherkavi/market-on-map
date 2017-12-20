package com.cherkashyn.vitalii.markets.tools.mapconverter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlUtilityTest {
	
	@Test
	public void checkXmlRead() throws SAXException, IOException, ParserConfigurationException{
		// given
		String value=getXmlString();
		
		// when
		Document document=XmlUtility.readDocument(value);
		
		// then
		Assert.assertNotNull(document);
		NodeList nodeList=document.getChildNodes();
		Assert.assertEquals(1, nodeList.getLength());
		Assert.assertNotNull(document.getElementsByTagName("root"));
		Assert.assertNotNull(document.getElementsByTagName("child1"));
		Assert.assertEquals(2, document.getElementsByTagName("child1").getLength());
	}
	
	@Test
	public void readXmlFromFile() throws IOException, SAXException, ParserConfigurationException{
		// given 
		File tempFile=File.createTempFile("test", "bin");
		FileOutputStream output=null;
		try{
			// fill file 
			output=new FileOutputStream(tempFile);
			IOUtils.write(getXmlString(), output);
			output.flush();
			
			// when
			Document document=XmlUtility.readDocument(tempFile);
			
			// then 
			Assert.assertNotNull(document);
			Assert.assertTrue(document.getChildNodes().getLength()>0);
		}finally{
			IOUtils.closeQuietly(output);
			tempFile.delete();
		}
	}
	
	/** test xml file */
	private String getXmlString(){
		StringBuilder s=new StringBuilder();
		s.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
		s.append("<root>");
		s.append("   <child1>data of child</child1>");
		s.append("   <child1>data of child 2</child1>");
		s.append("</root>");
		return s.toString(); 
	}
	
}
