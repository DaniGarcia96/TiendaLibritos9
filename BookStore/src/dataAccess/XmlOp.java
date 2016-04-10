package dataAccess;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XmlOp {
	public static void createXmlFile(String filePath, String root){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(root);
			doc.appendChild(rootElement);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(filePath);

			transformer.transform(source, result);
		}
		catch(ParserConfigurationException pce){
			pce.printStackTrace();
		}
		catch(TransformerException tfe){
			tfe.printStackTrace();
		}

	}

	public static int getMaxId(Node root){ 
		
		Node lastUser = root.getLastChild();
		
		
		if(lastUser == null){
			return 0;
		}
		
		int maxid = Integer.valueOf(lastUser.getAttributes().getNamedItem("id").getNodeValue()); 
		
		return maxid;
	}
}
