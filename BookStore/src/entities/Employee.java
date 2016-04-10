package entities;

import java.util.Vector;

public class Employee extends User{

	public Employee(String username, String password) {
		super(username, password);
	}

	public String getType(){
		return "employee";
	}
	
	public boolean sellBooks(Vector<Book> books, Vector<Integer> q, float total){
		Sale s = new Sale(books, q, total, this);
		
		BookStore bs = BookStore.getInstance();
		
		bs.getSales().add(s);
		
		for(Book b : books){
			int index = books.indexOf(b);
			int quantity = q.get(index);
			
			Book myBook = bs.getBookById(b.getIdBook());
			myBook.setQuantity(myBook.getQuantity() - quantity);
			
		}
		
		return s.save();
	}
	
	
}
