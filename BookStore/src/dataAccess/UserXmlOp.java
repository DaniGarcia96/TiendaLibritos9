package dataAccess;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import entities.Admin;
import entities.Employee;
import entities.User;

public class UserXmlOp {

	private static String filePath  = "D:\\eclipse\\workspace\\BookStore\\users.xml";



	public static Vector<User> readUsers(){
		Vector<User> result = new Vector<>();
		
		if(!new File(filePath).exists()){
			return result;
		}
		
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filePath);
	
	
			NodeList users = doc.getElementsByTagName("user");
			int length = users.getLength();
			
			
			for(int i=0;i<length;i++){
				Node user = users.item(i);
				
				String username = user.getChildNodes().item(0).getTextContent();
				String password = user.getChildNodes().item(1).getTextContent();
				String type = user.getChildNodes().item(2).getTextContent();
				int id = Integer.valueOf(user.getAttributes().getNamedItem("id").getTextContent());
				
				if(type.equals("employee")){
					Employee em = new Employee(username, password);
					em.setIdUser(id);
					
					result.add(em);
					
				}
				else if(type.equals("administrator")){
					Admin ad = new Admin(username, password);
					ad.setIdUser(id);
					
					result.add(ad);
				}
			}
			
		}
		catch(ParserConfigurationException pce){
			pce.printStackTrace();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		} 
		catch (SAXException sae) {
			sae.printStackTrace();
		}
		
		return result;
		
	}
	
	public static boolean addUser(User u){
		String username = u.getUsername();
		String password = u.getPassword();
		String type = u.getType();
		
		try{
			if(!new File(filePath).exists()){
				XmlOp.createXmlFile(filePath, "userlist");
			}
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filePath);


			Node rootElement = doc.getFirstChild();
			
			int maxid = XmlOp.getMaxId(rootElement) + 1;
			u.setIdUser(maxid);
			
			Element user = doc.createElement("user");
			rootElement.appendChild(user);
			
			Attr attr = doc.createAttribute("id");
			attr.setValue(String.valueOf(maxid));
			user.setAttributeNode(attr);


			Element un = doc.createElement("username");
			un.appendChild(doc.createTextNode(username));
			user.appendChild(un);

			Element pw = doc.createElement("password");
			pw.appendChild(doc.createTextNode(password));
			user.appendChild(pw);

			Element tp = doc.createElement("type");
			tp.appendChild(doc.createTextNode(type));
			user.appendChild(tp);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(filePath);
			transformer.transform(source, result);

			System.out.println("User added");
			
			return true;
		}
		catch(ParserConfigurationException pce){
			pce.printStackTrace();
		}
		catch(TransformerException tfe){
			tfe.printStackTrace();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		} 
		catch (SAXException sae) {
			sae.printStackTrace();
		}
		return false;
	}

	public static boolean updateUser(User u){
		int idUser = u.getIdUser();
		String username = u.getUsername();
		String password = u.getPassword();
		String type = u.getType();
		
		try{
			
			if(!new File(filePath).exists()){
				XmlOp.createXmlFile(filePath, "userlist");
			}
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filePath);


			boolean found = false;
			
			XPathFactory xFactory = XPathFactory.newInstance();
			XPath xPath = xFactory.newXPath();
			XPathExpression exp = null;
			
			exp = xPath.compile("//user[@id = '" + idUser + "']");
			
			
			
			Object res = exp.evaluate(doc, XPathConstants.NODESET);
	        NodeList nodes = (NodeList) res;
	        
	        for (int i = 0; i < nodes.getLength(); i++) {
	        	found = true;
	           
	        	nodes.item(i).getChildNodes().item(0).setTextContent(username);
	        	nodes.item(i).getChildNodes().item(1).setTextContent(password);
	        	nodes.item(i).getChildNodes().item(2).setTextContent(type);
	        }
	        
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(filePath);
			transformer.transform(source, result);
			
			if(found){
				System.out.println("User updated");
				return true;
			}
		}
		catch(ParserConfigurationException pce){
			pce.printStackTrace();
		}
		catch(TransformerException tfe){
			tfe.printStackTrace();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		} 
		catch (SAXException sae) {
			sae.printStackTrace();
		}
		catch(XPathExpressionException xpee){
			xpee.printStackTrace();
		}
		return false;
	}
	
	public static boolean deleteUser(int idUser){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filePath);

			boolean found = false;
			
			XPathFactory xFactory = XPathFactory.newInstance();
			XPath xPath = xFactory.newXPath();
			XPathExpression exp = null;
			
			exp = xPath.compile("//user[@id = '" + idUser + "']");
			
			Object res = exp.evaluate(doc, XPathConstants.NODESET);
	        NodeList nodes = (NodeList) res;
	        
	        for (int i = 0; i < nodes.getLength(); i++) {
	        	found = true;
	           
	        	nodes.item(i).getParentNode().removeChild(nodes.item(i));
	        }
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(filePath);
			transformer.transform(source, result);
			
			if(found){
				System.out.println("User deleted");
				return true;
			}
		}
		catch(ParserConfigurationException pce){
			pce.printStackTrace();
		}
		catch(TransformerException tfe){
			tfe.printStackTrace();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		} 
		catch (SAXException sae) {
			sae.printStackTrace();
		}
		catch(XPathExpressionException xpee){
			xpee.printStackTrace();
		}
		return false;
	}
}
