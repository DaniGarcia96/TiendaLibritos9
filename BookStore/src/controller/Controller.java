package controller;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

import entities.Admin;
import entities.Book;
import entities.BookStore;
import entities.Employee;
import entities.ShoppingCart;
import entities.User;
import factory.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn.CellEditEvent;
import view.*;

public class Controller {
	private Window window;
	private BookStore bookstore;
	private User currentUser;
	private ShoppingCart cart;
	
	public Controller(Window window, BookStore bookstore){
		this.window = window;
		this.bookstore = bookstore;
		this.cart = new ShoppingCart();
		
		this.window.addListenerAddButton(new AddButtonListener());
		this.window.addListenerLoginButton(new LoginButtonListener());
		this.window.addListenerLogoutButton(new LogoutButtonListener());
		this.window.addListenerQuantityCart(new QuantityCartListener());
		this.window.addListenerSearchButton(new SearchButtonListener());
		this.window.addListenerSellButton(new SellButtonListener());
		this.window.addListenerAddBookButton(new AddBookButtonListener());
		this.window.addListenerAddEmployeeButton(new AddEmployeeButtonListener());
		this.window.addListenerGenerateButton(new GenerateButtonListener());
		
		this.window.addListenerTitleBook(new TitleBookListener());
		this.window.addListenerAuthorBook(new AuthorBookListener());
		this.window.addListenerGenreBook(new GenreBookListener());
		this.window.addListenerQuantityBook(new QuantityBookListener());
		this.window.addListenerPriceBook(new PriceBookListener());
		
		this.window.addListenerDeleteEmployeeButton(new DeleteEmployeeButtonListener());
		this.window.addListenerDeleteBookButton(new DeleteBookButtonListener());
		this.window.addListenerDeleteBookCartButton(new DeleteBookCartButtonListener());
		
		this.window.addListenerEmUsername(new UsernameEmListener());
		this.window.addListenerEmType(new TypeEmListener());
	}
	
	public void loadCart(){
		Vector<Book> books = this.cart.getBooks();
		Vector<Integer> q = this.cart.getQuantities();
		
		List<List<String>> bookTable = new ArrayList<>();
		
		if(books != null){
			for(Book b : books){
				
				int index = books.indexOf(b);
				
				List<String> bookRow = new ArrayList<>();
				
				float price = b.getPrice();
				int quantity = q.get(index);
				
				bookRow.add(String.valueOf(b.getIdBook()));
				bookRow.add(b.getTitle());
				bookRow.add(b.getAuthor());
				bookRow.add(b.getGenre());
				bookRow.add(String.valueOf(price));
				bookRow.add(String.valueOf(quantity));
				bookRow.add(String.valueOf(quantity*price));
				
				bookTable.add(bookRow);
			}
		}
		
		window.setCartTable(bookTable);
		window.setTotal(String.valueOf(cart.getTotal()));
	}
	public void loadOutOfStockBooks(TreeSet<Book> books){
		List<List<String>> bookTable = new ArrayList<>();
		
		if(books != null){
			for(Book b : books){
				List<String> bookRow = new ArrayList<>();
				
				bookRow.add(String.valueOf(b.getIdBook()));
				bookRow.add(b.getTitle());
				bookRow.add(b.getAuthor());
				bookRow.add(b.getGenre());
				bookRow.add(String.valueOf(b.getQuantity()));
				bookRow.add(String.valueOf(b.getPrice()));
				
				bookTable.add(bookRow);
			}
			window.setOutOfStockTable(bookTable);
		}
	}
	public void loadBooks(TreeSet<Book> books){
		List<List<String>> bookTable = new ArrayList<>();
		List<String> bookList = new ArrayList<>();
		
		if(books != null){
			for(Book b : books){
				List<String> bookRow = new ArrayList<>();
				
				bookList.add(b.getTitle() + ", " + b.getAuthor());
				
				
				bookRow.add(String.valueOf(b.getIdBook()));
				bookRow.add(b.getTitle());
				bookRow.add(b.getAuthor());
				bookRow.add(b.getGenre());
				bookRow.add(String.valueOf(b.getQuantity()));
				bookRow.add(String.valueOf(b.getPrice()));
				
				bookTable.add(bookRow);
			}
			if(this.currentUser.getType().equals("employee")){
				window.setBookEmTable(bookTable);
				window.setComboBook(bookList);
			}
			else if(this.currentUser.getType().equals("administrator")){
				window.setBookAdminTable(bookTable);
			}
		}
	}
	
	public void loadUsers(){
		List<List<String>> userTable = new ArrayList<>();
		Vector<User> users = bookstore.getUsers();
		
		if(users!=null){
			for(User u : users){
				List<String> userRow = new ArrayList<>();
				
				userRow.add(String.valueOf(u.getIdUser()));
				userRow.add(u.getUsername());
				userRow.add(u.getType());
				
				userTable.add(userRow);
			}
			
			window.setEmployeesTable(userTable);
		}
	}
	class AddButtonListener implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent arg0) {
			try{
				String book = window.getBookToAdd();
				int quantity = Integer.valueOf(window.getQuantity());
				
				if(quantity < 0){
					throw new NumberFormatException();
				}
				
				Book b = bookstore.getBookByTitleAndAuthor(book);
				
				if(quantity > b.getQuantity()){
					window.ErrorMessage("Error", "Error adding book to cart", "Quantity too large.");
				}
				else{
					cart.addBook(b, quantity);
					loadCart();
				}
			}
			catch(NumberFormatException e){
				window.ErrorMessage("Error", "Error adding book to cart", "Invalid quantity.");
			}
		}
		
	}
	
	class LoginButtonListener implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent arg0) {
			String username = window.getUsername();
			String password = window.getPassword();
			
			if(bookstore == null){
				System.out.println("ull ");
			}
			User user = bookstore.searchUser(username, password);
			
			if(user == null){
				window.ErrorMessage("Error", "Failed loging in", "Username or password incorrect.");
			}
			else{
				if(user.getType().equals("employee")){
					window.employeeWindow();
				}
				else if(user.getType().equals("administrator")){
					window.adminWindow();
					loadUsers();
					loadOutOfStockBooks(bookstore.getOutOfStockBooks());
				}
				
				currentUser = user;
				
				loadBooks(bookstore.getBooks());
			}
		}
		
		
	}
	
	class LogoutButtonListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			currentUser = null;
			window.clearTables();
			window.loginWindow();
		}
		
	}
	class TitleBookListener implements EventHandler<CellEditEvent<List<String>, String>>{

		@Override
		public void handle(CellEditEvent<List<String>, String> event) {
			List<String> book = window.getSelectedBook();
			String newTitle = event.getNewValue();
			
			if(bookstore.existsBook(newTitle, book.get(1))){
				window.ErrorMessage("Error", "Error updating book", "Book already exists.");
			}
			else{
				Book b = bookstore.getBookById(Integer.valueOf(book.get(0)));
				b.setTitle(newTitle);
				
				loadBooks(bookstore.getBooks());
			}
		}
		
		
	}
	
	class AuthorBookListener implements EventHandler<CellEditEvent<List<String>, String>>{

		@Override
		public void handle(CellEditEvent<List<String>, String> event) {
			List<String> book = window.getSelectedBook();
			String newAuthor = event.getNewValue();
			
			if(bookstore.existsBook(book.get(0), newAuthor)){
				window.ErrorMessage("Error", "Error updating book", "Book already exists.");
			}
			else{
				Book b = bookstore.getBookById(Integer.valueOf(book.get(0)));
				b.setAuthor(newAuthor);
				
				loadBooks(bookstore.getBooks());
			}
		}
		
		
	}
	
	class GenreBookListener implements EventHandler<CellEditEvent<List<String>, String>>{

		@Override
		public void handle(CellEditEvent<List<String>, String> event) {
			List<String> book = window.getSelectedBook();
			String newGenre = event.getNewValue();
			
			
			Book b = bookstore.getBookById(Integer.valueOf(book.get(0)));
			b.setGenre(newGenre);
			
			loadBooks(bookstore.getBooks());
			
		}
		
		
	}
	
	class QuantityBookListener implements EventHandler<CellEditEvent<List<String>, String>>{

		@Override
		public void handle(CellEditEvent<List<String>, String> event) {
			List<String> book = window.getSelectedBook();
			
			try{
				int newQuantity = Integer.valueOf(event.getNewValue());
				
				if(newQuantity < 0){
					throw new NumberFormatException();
				}
				
				Book b = bookstore.getBookById(Integer.valueOf(book.get(0)));
				b.setQuantity(newQuantity);
				
				loadBooks(bookstore.getBooks());
				loadOutOfStockBooks(bookstore.getOutOfStockBooks());
			}
			catch(NumberFormatException e){
				window.ErrorMessage("Error", "Error updating book", "Invalid quantity.");
			}
		}
		
		
	}
	
	class PriceBookListener implements EventHandler<CellEditEvent<List<String>, String>>{

		@Override
		public void handle(CellEditEvent<List<String>, String> event) {
			List<String> book = window.getSelectedBook();
			
			try{
				float newPrice = Float.valueOf(event.getNewValue());
				
				if(newPrice < 0.0){
					throw new NumberFormatException();
				}
				
				Book b = bookstore.getBookById(Integer.valueOf(book.get(0)));
				b.setPrice(newPrice);
				
				loadBooks(bookstore.getBooks());
			}
			catch(NumberFormatException e){
				window.ErrorMessage("Error", "Error updating book", "Invalid price.");
			}
		}
		
		
	}
	class QuantityCartListener implements EventHandler<CellEditEvent<List<String>, String>>{

		@Override
		public void handle(CellEditEvent<List<String>, String> event) {
			try{
				int q = Integer.valueOf(event.getNewValue());
				if(q <= 0){
					throw new NumberFormatException();
				}
				
				List<String> cartBook = window.getSelectedCartBook();
				Book book = bookstore.getBookById(Integer.valueOf(cartBook.get(0)));
				
				if(q > book.getQuantity()){
					window.ErrorMessage("Error", "Error adding book to cart", "Quantity too large.");
				}
				else{
					cart.setQuantity(book, q);
					loadCart();
				}
			}
			catch(NumberFormatException e){
				window.ErrorMessage("Error", "Invalid quantity.", "");
			}
		}
		
		
	}
	
	class UsernameEmListener implements EventHandler<CellEditEvent<List<String>, String>>{

		@Override
		public void handle(CellEditEvent<List<String>, String> event) {
			List<String> user = window.getSelectedUser();
			String newUsername = event.getNewValue();
			
			if(bookstore.existsUser(newUsername)){
				window.ErrorMessage("Error", "Error updating employee", "Username already exists.");
			}
			else{
				User u = bookstore.getUserById(Integer.valueOf(user.get(0)));
				u.setUsername(newUsername);
				
				loadUsers();
			}
		}
		
		
	}
	
	class TypeEmListener implements EventHandler<CellEditEvent<List<String>, String>>{

		@Override
		public void handle(CellEditEvent<List<String>, String> event) {
			List<String> user = window.getSelectedUser();
			String newType = event.getNewValue();
			
			if(!("employee".equals(newType) || "administrator".equals(newType))){
				window.ErrorMessage("Error", "Error updating employee", "Invalid type.");
			}
			else{
				User u = bookstore.getUserById(Integer.valueOf(user.get(0)));
				u.setType(newType);
				
				loadUsers();
			}
		}
		
		
	}
	
	class SearchButtonListener implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent arg0) {
			String item = window.getSearchedItem();
			String category = window.getCategory();
			
			if(category == null){
				window.ErrorMessage("Error", "Failed searching", "Please select a category.");
			}
			else if("title".equals(category)){
				loadBooks(bookstore.searchBooksByTitle(item));	
			}
			else if("author".equals(category)){
				loadBooks(bookstore.searchBooksByAuthor(item));
			}
			else if("genre".equals(category)){
				loadBooks(bookstore.searchBooksByGenre(item));
			}
		}
		
		
		
	}
	
	class SellButtonListener implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent arg0) {
			if(cart.isEmpty()){
				window.ErrorMessage("Error", "Shopping cart is empty", "");
			}
			else{
				if(!((Employee) currentUser).sellBooks(cart.getBooks(), cart.getQuantities(), cart.getTotal())){
					window.ErrorMessage("Error", "The sale could not be completed", "");
				}
				else{
					window.SuccesMessage("Success", "Sale completed successfully", "");
					cart.empty();
					loadCart();
					loadBooks(bookstore.getBooks());
				}
			}
		}
		
	}
	
	class AddBookButtonListener implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent arg0) {
			try{
				String title = window.getTitle();
				String author = window.getAuthor();
				String genre = window.getGenre();
				int quantity = Integer.valueOf(window.getBookQuantity());
				float price = Float.valueOf(window.getPrice());
				
				boolean added = bookstore.addBook(title, author, genre, quantity, price);
				
				if(!added){
					window.ErrorMessage("Error", "Error adding book", "Book already exists.");
				}
				else{
					window.SuccesMessage("Success", "Book added", "Book has been added successfully");
					loadBooks(bookstore.getBooks());
					loadOutOfStockBooks(bookstore.getOutOfStockBooks());
				}
			}
			catch(NumberFormatException e){
				window.ErrorMessage("Error", "Error adding book", "Invalid quantity or price.");
			}
			
		}
		
	}
	
	class AddEmployeeButtonListener implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent arg0) {
			String username = window.getEmUsername();
			String password = window.getEmPassword();
			
			boolean added = bookstore.addEmployee(username, password);
			
			if(!added){
				window.ErrorMessage("Error", "Error adding employee", "Username already exists.");
			}
			else{
				window.SuccesMessage("Success", "Employee added", "Employee has been added successfully");
				loadUsers();
			}
		}
		
	}
	
	class DeleteBookButtonListener implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent arg0) {
			List<String> book = window.getSelectedBook();
			
			if(book == null){
				window.ErrorMessage("Error", "Error deleting book", "You must select a book.");
			}
			else{
				if(!bookstore.deleteBook(Integer.valueOf(book.get(0)))){
					window.ErrorMessage("Error", "Error deleting book", "Cannot delete book.");
				}
				else{
					window.SuccesMessage("Success", "Book deleted", "Book has been deleted successfully");
					loadBooks(bookstore.getBooks());
					loadOutOfStockBooks(bookstore.getOutOfStockBooks());
				}
			}
		}
		
	}
	
	class DeleteEmployeeButtonListener implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent arg0) {
			
			List<String> user = window.getSelectedUser();
			
			if(user == null){
				window.ErrorMessage("Error", "Error deleting employee", "You must select an employee.");
			}
			else{
				if(!bookstore.deleteEmployee(Integer.valueOf(user.get(0)))){
					window.ErrorMessage("Error", "Error deleting employee", "Cannot delete employee.");
				}
				else{
					window.SuccesMessage("Success", "Employee deleted", "Employee has been deleted successfully");
					loadUsers();
				}
			}
		}
		
	}

	class DeleteBookCartButtonListener implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent arg0) {
			
			List<String> cartBook = window.getSelectedCartBook();
			
			if(cartBook == null){
				window.ErrorMessage("Error", "Error removing book", "You must select a book.");
			}
			else{
				Book b = bookstore.getBookById(Integer.valueOf(cartBook.get(0)));
				
				if(!cart.removeBook(b)){
					window.ErrorMessage("Error", "Error removing book", "");
				}
				else{
					window.SuccesMessage("Success", "Item removed", "");
					loadCart();
				}
			}
		}
		
	}
	
	class GenerateButtonListener implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent arg0) {
			String option = window.getReportOption();
			
			try {
				if(!((Admin) currentUser).generateOutOfStockBooksReport(option)){
					window.ErrorMessage("Error", "Error generating report", "Error generating report");
				}
				else{
					window.SuccesMessage("Success", "Report generated", "");
				}
			} catch (ReportException e) {
				window.ErrorMessage("Error", "Error generating report", e.getMessage());
			}
		}
	}
			
}
