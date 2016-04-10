package controller;
import entities.BookStore;
import javafx.application.Application;
import javafx.stage.Stage;
import view.*;

public class Main extends Application{

	private Window window;
	private Controller controller;
	private BookStore bookstore;
	
	private Stage primaryStage;
	
	public Main(){
		window = new Window(this);
	}
	
	public static void main(String[] args){
		launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
		primaryStage = stage;
		window.start(primaryStage);
		controller = new Controller(window, BookStore.getInstance());
	}
	
}
