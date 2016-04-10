package dataAccess;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

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

import entities.Book;
import entities.Sale;
import entities.User;

public class SaleXmlOp {
	private static String filePath = "D:\\eclipse\\workspace\\BookStore\\sellings.xml";

	public static boolean addSelling(Sale selling){

		if(!new File(filePath).exists()){
			XmlOp.createXmlFile(filePath, "sellings");
		}
		
		Vector<Book> books = selling.getBooks();
		Vector<Integer> quantities = selling.getQuantities();
		Float total = selling.getTotal();
		Date date = selling.getDateSell();
		User employee = selling.getUser();
		
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filePath);

			Node rootElement = doc.getFirstChild();

			int maxid = XmlOp.getMaxId(rootElement) + 1;
			selling.setIdSelling(maxid);
			
			Element sell = doc.createElement("selling");
			rootElement.appendChild(sell);
			
			Attr attr = doc.createAttribute("id");
			attr.setValue(String.valueOf(maxid));
			sell.setAttributeNode(attr);

			Element booklist = doc.createElement("books");
			sell.appendChild(booklist);
			
			for(Book b : books){
				Element book = doc.createElement("book");
				booklist.appendChild(book);
				
				Element tl = doc.createElement("title");
				tl.appendChild(doc.createTextNode(b.getTitle()));
				book.appendChild(tl);

				Element at = doc.createElement("author");
				at.appendChild(doc.createTextNode(b.getAuthor()));
				book.appendChild(at);

				Element tp = doc.createElement("genre");
				tp.appendChild(doc.createTextNode(b.getGenre()));
				book.appendChild(tp);
				
				Element qt = doc.createElement("quantity");
				qt.appendChild(doc.createTextNode(String.valueOf(quantities.get(books.indexOf(b)))));
				book.appendChild(qt);
				
				Element pr = doc.createElement("unitprice");
				pr.appendChild(doc.createTextNode(String.valueOf(b.getPrice())));
				book.appendChild(pr);
			}
			
			Element tpr = doc.createElement("totalprice");
			tpr.appendChild(doc.createTextNode(String.valueOf(total)));
			sell.appendChild(tpr);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			Element dt = doc.createElement("date");
			dt.appendChild(doc.createTextNode(String.valueOf(dateFormat.format(date))));
			sell.appendChild(dt);
			
			Element em = doc.createElement("employee");
			sell.appendChild(em);
			
			Attr atem = doc.createAttribute("id");
			atem.setValue(String.valueOf(employee.getIdUser()));
			em.setAttributeNode(atem);
			
			Element un = doc.createElement("username");
			un.appendChild(doc.createTextNode(employee.getUsername()));
			em.appendChild(un);
			
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
