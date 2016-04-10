package entities;
import java.util.*;

import dataAccess.BookXmlOp;
import dataAccess.UserXmlOp;

public class BookStore {
	private TreeSet<Book> books;
	private Vector<User> users;
	private Vector<Sale> sales;
	
	private static BookStore instance = new BookStore();
	
	private BookStore() {
		this.books = new TreeSet<>();
		this.users = new Vector<User>();
		this.sales = new Vector<>();
		
		this.readBooks();
		this.readUsers();
	}
	
	public static BookStore getInstance(){
		return instance;
	}
	
	public TreeSet<Book> getBooks() {
		return books;
	}
	public void setBooks(TreeSet<Book> books) {
		this.books = books;
	}
	public Vector<User> getUsers() {
		return users;
	}
	public void setUsers(Vector<User> users) {
		this.users = users;
	}
	
	public Vector<Sale> getSales(){
		return this.sales;
	}
	
	public User searchUser(String username, String password){
		
		for(User user: this.users){
			if(user.login(username, password)){
				return user;
			}
		}
		
		return null;
	}
	
	private void readBooks(){
		this.setBooks(BookXmlOp.readBooks());
	}
	
	private void readUsers(){
		this.setUsers(UserXmlOp.readUsers());
	}
	

	
	
	
	public boolean existsUser(String username){
		if(this.getUsers() == null){
			return false;
		}
		
		for(User user : this.getUsers()){
			if(user.getUsername().equals(username)){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean existsBook(String title, String author){
		if(this.getBooks() == null){
			return false;
		}
		
		for(Book book : this.getBooks()){
			if(book.getTitle().equals(title) && book.getAuthor().equals(author)){
				return true;
			}
		}
		
		return false;
	}
	public TreeSet<Book> getOutOfStockBooks(){
		TreeSet<Book> res = new TreeSet<Book>();
		
		if(this.getBooks() == null){
			return null;
		}
		
		for(Book book : this.getBooks()){
			if(book.getQuantity() == 0){
				res.add(book);
			}
		}
		
		return res;
	}
	
	public TreeSet<Book> searchBooksByTitle(String title){
		TreeSet<Book> res = new TreeSet<Book>();
		
		if(this.getBooks() == null){
			return null;
		}
		
		for(Book book : this.getBooks()){
			if(book.getTitle().toLowerCase().contains(title.toLowerCase())){
				res.add(book);
			}
		}
		
		return res;
	}
	
	public TreeSet<Book> searchBooksByAuthor(String author){
		TreeSet<Book> res = new TreeSet<Book>();
		
		if(this.getBooks() == null){
			return null;
		}
		
		for(Book book : this.getBooks()){
			if(book.getAuthor().toLowerCase().contains(author.toLowerCase())){
				res.add(book);
			}
		}
		
		return res;
	}
	
	public TreeSet<Book> searchBooksByGenre(String genre){
		TreeSet<Book> res = new TreeSet<Book>();
		
		if(this.getBooks() == null){
			return null;
		}
		
		for(Book book : this.getBooks()){
			if(book.getGenre().toLowerCase().contains(genre.toLowerCase())){
				res.add(book);
			}
		}
		
		return res;
	}
	
	public boolean addEmployee(String username, String password){
		if(this.existsUser(username)){
			return false;
		}
			
		Employee em = new Employee(username, password);
		if(!em.save()){
			return false;
		}
		
		this.getUsers().addElement(em);
		return true;
	}
	public boolean addBook(String title, String author, String genre, int quantity, float price){
		if(this.existsBook(title, author)){
			return false;
		}
		
		Book book = new Book(title, author, genre, quantity, price);
		if(!book.save()){
			return false;
		}
		
		this.getBooks().add(book);
		return true;
	}
	
	public boolean deleteEmployee(int idEm){
		User user = this.getUserById(idEm);
		if(user != null){
			this.getUsers().remove(user);
			UserXmlOp.deleteUser(idEm);
			
			return true;
		}
				
		return false;
	}
	
	public boolean deleteBook(int idBook){
		Book book = this.getBookById(idBook);
		
		if(book != null){
			this.getBooks().remove(book);
			BookXmlOp.deleteBook(idBook);
			
			return true;
		}
		
		return false;
	}
	
	public User getUserById(int idUser){
		if(this.getUsers() == null){
			return null;
		}
		
		for(User user : this.getUsers()){
			if(user.getIdUser() == idUser){
				
				return user;
			}
		}
		
		return null;
	}
	
	public Book getBookById(int idBook){
		if(this.getBooks() == null){
			return null;
		}
		
		for(Book book : this.getBooks()){
			if(book.getIdBook() == idBook){
				return book;
			}
		}
		
		return null;
	}
	
	public Book getBookByTitleAndAuthor(String descr){
		if(this.getBooks() == null){
			return null;
		}
		for(Book book : this.getBooks()){
			if((book.getTitle() + ", " + book.getAuthor()).equals(descr)){
				return book;
			}
		}
		
		return null;
	}
}
