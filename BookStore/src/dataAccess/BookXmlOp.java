package dataAccess;

import java.io.File;
import java.io.IOException;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import entities.Book;

public class BookXmlOp {
	private static String filePath = "D:\\eclipse\\workspace\\BookStore\\books.xml";


	public static TreeSet<Book> readBooks(){
		TreeSet<Book> result = new TreeSet<Book>();
		
		if(!new File(filePath).exists()){
			return result;
		}
		
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filePath);
	
	
			NodeList books = doc.getElementsByTagName("book");
			int length = books.getLength();

			
			for(int i=0;i<length;i++){
				Node book = books.item(i);
				
				int idBook = Integer.valueOf(book.getAttributes().getNamedItem("id").getNodeValue());
				String title = book.getChildNodes().item(0).getTextContent();
				String author = book.getChildNodes().item(1).getTextContent();
				String genre = book.getChildNodes().item(2).getTextContent();
				int quantity = Integer.valueOf(book.getChildNodes().item(3).getTextContent());
				float price = Float.valueOf(book.getChildNodes().item(4).getTextContent());
				
				Book b = new Book(title, author, genre, quantity, price);
				b.setIdBook(idBook);
				
				result.add(b);
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
	
	public static boolean addBook(Book b){
		String title = b.getTitle();
		String author = b.getAuthor();
		String genre = b.getGenre();
		int quantity = b.getQuantity();
		float price = b.getPrice();
		
		try{
			
			if(!new File(filePath).exists()){
				XmlOp.createXmlFile(filePath, "bookstore");
			}
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filePath);

			
			Node rootElement = doc.getFirstChild();

			int maxid = XmlOp.getMaxId(rootElement) + 1;
			b.setIdBook(maxid);
			
			
			Element book = doc.createElement("book");
			rootElement.appendChild(book);

			
			
			Attr attr = doc.createAttribute("id");
			attr.setValue(String.valueOf(maxid));
			book.setAttributeNode(attr);


			Element un = doc.createElement("title");
			un.appendChild(doc.createTextNode(title));
			book.appendChild(un);

			Element pw = doc.createElement("author");
			pw.appendChild(doc.createTextNode(author));
			book.appendChild(pw);

			Element tp = doc.createElement("genre");
			tp.appendChild(doc.createTextNode(genre));
			book.appendChild(tp);
			
			Element qt = doc.createElement("quantity");
			qt.appendChild(doc.createTextNode(String.valueOf(quantity)));
			book.appendChild(qt);
			
			Element pr = doc.createElement("price");
			pr.appendChild(doc.createTextNode(String.valueOf(price)));
			book.appendChild(pr);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(filePath);
			transformer.transform(source, result);
			
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

	public static boolean updateBook(Book b){
		int idBook = b.getIdBook();
		String title = b.getTitle();
		String author = b.getAuthor();
		String genre = b.getGenre();
		int quantity = b.getQuantity();
		float price = b.getPrice();
		
		try{
			if(!new File(filePath).exists()){
				XmlOp.createXmlFile(filePath, "bookstore");
			}
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filePath);


			
			boolean found = false;
			
			XPathFactory xFactory = XPathFactory.newInstance();
			XPath xPath = xFactory.newXPath();
			XPathExpression exp = null;
			
			exp = xPath.compile("//book[@id = '" + idBook + "']");
			
			
			
			Object res = exp.evaluate(doc, XPathConstants.NODESET);
	        NodeList nodes = (NodeList) res;
	        
	        for (int i = 0; i < nodes.getLength(); i++) {
	        	found = true;
	           
	        	nodes.item(i).getChildNodes().item(0).setTextContent(title);
	        	nodes.item(i).getChildNodes().item(1).setTextContent(author);
	        	nodes.item(i).getChildNodes().item(2).setTextContent(genre);
	        	nodes.item(i).getChildNodes().item(3).setTextContent(String.valueOf(quantity));
	        	nodes.item(i).getChildNodes().item(4).setTextContent(String.valueOf(price));
	        }
	        
//			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(filePath);
			transformer.transform(source, result);
			
			if(found){
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
	
	public static boolean deleteBook(int idBook){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filePath);

			boolean found = false;
			
			XPathFactory xFactory = XPathFactory.newInstance();
			XPath xPath = xFactory.newXPath();
			XPathExpression exp = null;
			
			exp = xPath.compile("//book[@id = '" + idBook + "']");
			
			
			
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

