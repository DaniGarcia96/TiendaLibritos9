package entities;

import java.util.Vector;

public class ShoppingCart {
	private Vector<Book> books;
	private Vector<Integer> quantities;
	
	public ShoppingCart(){
		this.books = new Vector<>();
		this.quantities = new Vector<>();
	}
	
	public boolean removeBook(Book b){
		
		int index = this.books.indexOf(b);
		
		if(index == -1){
			return false;
		}
		this.books.remove(index);
		this.quantities.remove(index);
		
		return true;
	}
	
	public void addBook(Book b, int quantity){
		
		if(this.books.contains(b)){
			int index = books.indexOf(b);
			this.quantities.set(index, this.quantities.get(index) + quantity);
		}
		else{
			this.books.add(b);
			this.quantities.add(quantity);
		}
	}
	
	public Vector<Book> getBooks(){
		return this.books;
	}
	
	public Vector<Integer> getQuantities(){
		return this.quantities;
	}
	
	public float getTotal(){
		float total = (float) 0.0;
		
		for(Book b : this.books){
			int index = this.books.indexOf(b);
			int q = this.quantities.get(index);
			
			total += q*b.getPrice();
		}
		
		return total;
	}
	
	public void setQuantity(Book b, int newQuantity){
		int index = this.books.indexOf(b);
		this.quantities.set(index, newQuantity);
	}
	
	public void empty(){
		this.books.removeAllElements();
		this.quantities.removeAllElements();
	}
	
	public boolean isEmpty(){
		return this.books.isEmpty() && this.quantities.isEmpty();
	}
}
