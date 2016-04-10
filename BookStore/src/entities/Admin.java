package entities;

import java.util.TreeSet;

import factory.IReport;
import factory.ReportException;
import factory.ReportFactory;

public class Admin extends User{

	public Admin(String username, String password) {
		super(username, password);
	}
	
	public String getType(){
		return "administrator";
	}
	
	public boolean generateOutOfStockBooksReport(String type) throws ReportException{
		TreeSet<Book> books = BookStore.getInstance().getOutOfStockBooks();
		
		ReportFactory rf = new ReportFactory();
		IReport ir = rf.getReport(type);
		
		return ir.generateReport(books);
	}

}
