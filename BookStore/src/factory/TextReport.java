package factory;

import java.io.*;
import java.util.*;

import entities.Book;

public class TextReport implements IReport{

	@Override
	public boolean generateReport(TreeSet<Book> books) {
		
		if(books == null){
			return false;
		}
		
		try
		{
			  FileWriter fstream = new FileWriter("out_of_stock_books.txt", false);
			  BufferedWriter out = new BufferedWriter(fstream);
			  
			  out.write("Out of stock books:");
			  out.newLine();
			  
			  for(Book b : books){
				  out.write(b.toString());
				  out.newLine();
				  
			  }
			 
			  out.close();
		}
		catch (Exception e)
		{
			  System.err.println("Error: " + e.getMessage());
			  return false;
		}
		
		return true;
	}

}
