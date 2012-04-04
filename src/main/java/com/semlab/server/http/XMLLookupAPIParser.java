package com.semlab.server.http;

import java.io.IOException;
import java.io.StringReader;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Component;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.semlab.server.resources.Concept;
import com.semlab.server.resources.FType;

@Component
public class XMLLookupAPIParser {

	public List<Concept> updateConcept(String xmlResponse, boolean unknownType){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(xmlResponse));
		Document doc = null;
		try {
			doc = db.parse(is);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		NodeList nodes = doc.getElementsByTagName("Result");
		List<Concept> resultUris = new LinkedList<Concept>();
		// iterate the result
		for (int i = 0; i < nodes.getLength(); i++) {
			Concept concept = new Concept();
			Element element = (Element) nodes.item(i);

			NodeList label = element.getElementsByTagName("Label");
			Element line = (Element) label.item(0);
			concept.setName(getCharacterDataFromElement(line));
			
			NodeList uri = element.getElementsByTagName("URI");
			line = (Element) uri.item(0);
			concept.setUri(getCharacterDataFromElement(line));
			
			if(unknownType){

				NodeList classes = element.getElementsByTagName("Classes");
				Element classesElement = (Element) classes.item(0);
				NodeList subClasses = classesElement.getElementsByTagName("Class");
				
				if(subClasses.getLength() < 1){
					concept.setType(null);
				}
				
				for(int j = 0; j < subClasses.getLength(); j++){
					Element subElement = (Element) subClasses.item(j);
					NodeList classUri = subElement.getElementsByTagName("URI");
					line = (Element) classUri.item(0);
					if(getCharacterDataFromElement(line).equalsIgnoreCase(FType.Musician.getUri()) | getCharacterDataFromElement(line).equalsIgnoreCase(FType.Band.getUri())){
						concept.setType(getCharacterDataFromElement(line));
					} else {
						concept.setType(null);
					}
				}	
			}
			resultUris.add(concept);
			
		}
		return resultUris;
	}
	
	
	private String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "?";
	}
	
}
