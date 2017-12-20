package com.cherkashyn.vitalii.markets.tools.mapconverter;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlUtility {
	/**
	 * read file and convert it to XML Document 
	 * @param source
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static Document readDocument(File source) throws SAXException, IOException, ParserConfigurationException{
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		org.w3c.dom.Document doc = documentBuilderFactory.newDocumentBuilder().parse(source);
		return doc;
	}
	
	public static Document readDocument(String source) throws SAXException, IOException, ParserConfigurationException{
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		org.w3c.dom.Document doc = documentBuilderFactory.newDocumentBuilder().parse(new InputSource(new StringReader(source)));
		return doc;
	}
	/** circle element with description */
	private final static String TAG_PATH="path";
	/** child element of circle element */
	private final static String TAG_DESC="desc";

	/** type of path attribute */
	private final static String CIRCLE_ATTR="sodipodi:type";
	/** type - circle */
	private final static String CIRCLE_VALUE="arc";
	/** attribute which determinate color container */
	private final static String STYLE_ATTR="style";
	/** color of the green zone */
	private final static String BACKGROUND_STYLE_VALUE="fill:#B8DB7C";
	
	public static Document clearUnusedData(Document document) throws SAXException, IOException, ParserConfigurationException {
		removeDescriptionsForTag(document);
		return document;
	}
	
	private static void removeDescriptionsForTag(Document document){
		NodeList nodeList=document.getElementsByTagName(TAG_PATH);
		for(int index=0;index<nodeList.getLength();index++){
			Node eachNode=nodeList.item(index);
			if(! (eachNode instanceof Element)){
				continue;
			}
			Element eachElement=(Element)eachNode;
			// actions
			removeFirstChildByTagName(eachElement, TAG_DESC);
			if(isCircleElement(eachElement)){
				setColorOfElement(eachElement, BACKGROUND_STYLE_VALUE);
			}
		}
	}
	
	private static boolean isCircleElement(Element eachElement){
		return CIRCLE_VALUE.equals(eachElement.getAttribute(CIRCLE_ATTR));	
	}

	private static void setColorOfElement(Element eachElement, String colorValue) {
		eachElement.setAttribute(STYLE_ATTR, colorValue);
	}

	private static void removeFirstChildByTagName(Element eachElement, String tagDesc) {
		if(eachElement.hasChildNodes()){
			NodeList nodeList=eachElement.getChildNodes();
			for(int index=0;index<nodeList.getLength();index++){
				Node childNode=nodeList.item(index);
				if(childNode instanceof Element){
					if(tagDesc.equals(((Element)childNode).getTagName())){
						eachElement.removeChild(childNode);
						return;
					}
				}
			}
		}
	}

	public static void saveToFile(Document document, String pathToDestinationFile) throws TransformerException {
		 //for output to file, console
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        //for pretty print
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(document);

        //write to console or file
        // StreamResult console = new StreamResult(System.out);
        StreamResult file = new StreamResult(new File(pathToDestinationFile));

        //write data
        // transformer.transform(source, console);
        transformer.transform(source, file);
        System.out.println("DONE");

	}
}
