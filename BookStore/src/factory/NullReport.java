package factory;

import java.util.TreeSet;

import entities.Book;

public class NullReport implements IReport{

	@Override
	public boolean generateReport(TreeSet<Book> books) throws ReportException{
		throw new ReportException("Invalid report type");
	}

}
