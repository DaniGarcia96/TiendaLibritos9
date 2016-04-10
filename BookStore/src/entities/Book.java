package entities;

import dataAccess.BookXmlOp;

public class Book implements Comparable<Book>{
	private int idBook;
	private String title;
	private String author;
	private String genre;
	private Integer quantity;
	private Float price;
	
	public Book(String title, String author, String genre, Integer quantity, Float price){
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.quantity = quantity;
		this.price = price;
	}

	public int getIdBook() {
		return idBook;
	}

	public void setIdBook(int idBook) {
		this.idBook = idBook;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		BookXmlOp.updateBook(this);
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
		BookXmlOp.updateBook(this);
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
		BookXmlOp.updateBook(this);
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
		BookXmlOp.updateBook(this);
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
		BookXmlOp.updateBook(this);
	}

	@Override
	public int compareTo(Book book) {
		return this.title.compareTo(book.getTitle());
	}

	@Override
	public String toString() {
		return "Book [idBook=" + idBook + ", title=" + title + ", author=" + author + ", genre=" + genre + ", quantity="
				+ quantity + ", price=" + price + "]";
	}
	
	public boolean save(){
		return BookXmlOp.addBook(this);
	}
	
}
