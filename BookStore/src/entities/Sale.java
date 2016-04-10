package entities;
import java.util.*;

import dataAccess.SaleXmlOp;

public class Sale {
	private int idSelling;
	private Vector<Book> books;
	private Vector<Integer> quantities;
	private Date dateSell;
	private Float total;
	private User user;
	
	public Sale(Vector<Book> books, Vector<Integer> quantities, Float total, User user){
		this.books = books;
		this.quantities = quantities;
		this.total = total;
		this.dateSell = new Date();
		this.user = user;
	}

	public int getIdSelling() {
		return idSelling;
	}

	public void setIdSelling(int idSelling) {
		this.idSelling = idSelling;
	}

	public Vector<Book> getBooks() {
		return books;
	}

	public void setBooks(Vector<Book> books) {
		this.books = books;
	}

	public Vector<Integer> getQuantities() {
		return quantities;
	}

	public void setQuantities(Vector<Integer> quantities) {
		this.quantities = quantities;
	}

	public Date getDateSell() {
		return dateSell;
	}

	public void setDateSell(Date dateSell) {
		this.dateSell = dateSell;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public boolean save(){
		return SaleXmlOp.addSelling(this);
	}
//	public void addBook(Book b, Integer quantity){
//		int index = books.indexOf(b);
//		if(index > 0){
//			quantities.set(index, quantities.get(index) + quantity);
//			addTotal(books.get(index).getPrice() * quantity);
//		}
//		else{
//			books.add(b);
//			quantities.add(quantity);
//		}
//	}
//	
//	public void addTotal(Float sum){
//		this.total += sum;
//	}
	
}
