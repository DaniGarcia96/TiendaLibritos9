package factory;
import java.io.IOException;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import dataAccess.XmlOp;
import entities.Book;

public class XmlReport implements IReport{

	@Override
	public boolean generateReport(TreeSet<Book> books) {
		if(books == null){
			return false;
		}
		
		String filePath = "D:\\eclipse\\workspace\\BookStore\\out_of_stock_books.xml";
		
		XmlOp.createXmlFile(filePath, "booklist");
			
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filePath);

			
			Node rootElement = doc.getFirstChild();
			
			for(Book b : books){
				int id = b.getIdBook();
				String title = b.getTitle();
				String author = b.getAuthor();
				String genre = b.getGenre();
				int quantity = b.getQuantity();
				float price = b.getPrice();
				
				Element book = doc.createElement("book");
				rootElement.appendChild(book);

				
				Attr attr = doc.createAttribute("id");
				attr.setValue(String.valueOf(id));
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
			
			}
			
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

}
