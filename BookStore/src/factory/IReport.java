package factory;

import java.util.TreeSet;

import entities.Book;

public interface IReport {
	public boolean generateReport(TreeSet<Book> books) throws ReportException;
}
