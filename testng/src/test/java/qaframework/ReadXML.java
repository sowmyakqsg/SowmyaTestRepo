package qaframework;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadXML {

	public File xml;
	
	public ReadXML(File xmlFile){
		this.xml = xmlFile;
	}

	public Document getDocument() throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(this.xml);
		doc.getDocumentElement().normalize();
		return doc;
	}

	public void validateXML() throws Exception {
		Document doc = this.getDocument();
		NodeList element = doc.getElementsByTagName("element");
		String[] name = new String[element.getLength()];
		for (int index = 0; index < element.getLength(); index++) {
			Node nNode = element.item(index);
			Element eElement = (Element) nNode;
			name[index] = eElement.getAttribute("name");
		}

		// Validate XML for duplicate elements
		for (int i = 0; i < name.length; i++)
			for (int j = i + 1; j < name.length; j++)
				if (name[i].equals(name[j])) {
					throw new Exception("Duplicate elements found in XML: "+name[i]);
				}
		
	}

	public String getElementXML(String locatorName, String locatorType)
			throws Exception {
		String result = "";
		Document doc = this.getDocument();
		NodeList element = doc.getElementsByTagName("element");
		for (int index = 0; index < element.getLength(); index++) {
			Node nNode = element.item(index);
			Element childElement = (Element) nNode;

			if (locatorName.equals(childElement.getAttribute("name"))) {
				NodeList locatorElement = childElement
						.getElementsByTagName("locator");
				for (int indexLocator = 0; indexLocator < locatorElement
						.getLength(); indexLocator++) {
					Node nNodeLocator = locatorElement.item(indexLocator);
					Element childElements = (Element) nNodeLocator;

					if (childElements.getAttribute("type").equals(locatorType)) {
						result = childElements.getTextContent();
					}
				}
			}
		}
		if(result.equals("")){
			throw new Exception("No locators of name '"+locatorName+"'");
		}
		return result;
	}
}