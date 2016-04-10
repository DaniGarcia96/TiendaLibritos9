package view;

import java.util.List;
import controller.Main;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class Window extends Application{

	private Main main;
	
	private Scene scene;
	
	private Stage stage;
	
	private TextField usernameField;
	private TextField usernameEmField;
	private TextField searchField;
	private TextField quantityField;
	private TextField totalField;
	private TextField titleField;
	private TextField authorField;
	private TextField genreField;
	private TextField qField;
	private TextField priceField;
	
	private PasswordField passwordField;
	private PasswordField passwordEmField;
	
	private Button loginButton;
	private Button searchButton;
	private Button addButton;
	private Button sellButton;
	private Button addEmButton;
	private Button addBookButton;
	private Button deleteEmButton;
	private Button deleteBookCartButton;
	private Button deleteBookButton;
	private Button generateButton;
	private Button logoutEmButton;
	private Button logoutAdminButton;
	
	
	private TableView bookEmTable;
	private TableView cartTable;
	private TableView emTable;
	private TableView bookTable;
	private TableView outOfStockTable;
	
	private TableColumn<List<String>, String> quantityCartCol;
	
	private TableColumn<List<String>, String> titleCol;
	private TableColumn<List<String>, String> authorCol;
	private TableColumn<List<String>, String> genreCol;
	private TableColumn<List<String>, String> quantityCol;
	private TableColumn<List<String>, String> priceCol;
	
	private TableColumn<List<String>, String> usernameCol;
	private TableColumn<List<String>, String> typeCol;
	
	private ComboBox<String> categories;
	private ComboBox<String> comboBook;
	
	
	private ToggleGroup group;
	
	public Window(Main main){
		this.main = main;
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;
		
		usernameEmField = new TextField();
		passwordEmField = new PasswordField();
		
		loginButton = new Button("Log in");
		addButton = new Button("Add");
		searchButton = new Button("Search");
		sellButton = new Button("Sell");
		addEmButton = new Button("Add employee");
		addBookButton = new Button("Add book");
		generateButton = new Button("Generate report");
		deleteBookCartButton = new Button("Remove from cart");
		logoutEmButton = new Button("Log out");
		logoutAdminButton = new Button("Log out");
		
		deleteEmButton = new Button("Delete");
		deleteBookButton = new Button("Delete");
		
		quantityCartCol = new TableColumn<List<String>, String>("Quantity");
		usernameCol = new TableColumn<List<String>, String>("Username");
		typeCol = new TableColumn<List<String>, String>("Type user");
		
		titleCol = new TableColumn<List<String>, String>("Title");
		authorCol = new TableColumn<List<String>, String>("Author");
		genreCol = new TableColumn<List<String>, String>("Genre");
		quantityCol = new TableColumn<List<String>, String>("Quantity");
		priceCol = new TableColumn<List<String>, String>("Price");
		
		bookEmTable = new TableView();
		cartTable = new TableView();
		emTable = new TableView();
		bookTable = new TableView();
		outOfStockTable = new TableView();
		
		comboBook = new ComboBox<>();
		
		group = new ToggleGroup();
		
		loginWindow();
		
		stage.setTitle("Book Store");
//		stage.setScene(scene);
//		stage.show();
	}
	
	public void loginWindow(){
		VBox vboxLog = new VBox(20);
		HBox hboxUsername = new HBox(10);
		HBox hboxPassword = new HBox(10);
		
		vboxLog.setAlignment(Pos.CENTER);
		hboxUsername.setAlignment(Pos.CENTER);
		hboxPassword.setAlignment(Pos.CENTER);
		
		Label usernameLabel = new Label("Username");
		Label passwordLabel = new Label("Password");
		
		usernameField = new TextField();
		passwordField = new PasswordField();
		
		//loginButton = new Button("Log in");
		//addListenerButtonLogin(new ButtonLoginListener());
		
		hboxUsername.getChildren().addAll(usernameLabel, usernameField);
		hboxPassword.getChildren().addAll(passwordLabel, passwordField);
		
		vboxLog.getChildren().addAll(hboxUsername, hboxPassword, loginButton);
		
		String image = Window.class.getResource("image.jpg").toExternalForm();
		vboxLog.setStyle("-fx-background-image: url('" + image + "'); -fx-background-position: center center; -fx-background-repeat: stretch;");
		
		
		Scene loginScene = new Scene(vboxLog, 480, 360);
		
		setScene(loginScene);
	}

	public void employeeWindow(){
		TabPane tabEm = new TabPane();
		
		Tab bookEm = new Tab("Books");
		Tab sellEm = new Tab("Sell books");
		
		VBox vBook = new VBox(20);
		VBox vsell = new VBox(20);
		
		//bookEmTable = new TableView();
		
		bookEmTable.setMinWidth(600);
		bookEmTable.setMaxHeight(600);
		
		TableColumn<List<String>, String> idBookCol = new TableColumn<List<String>, String>("Id book");
		TableColumn<List<String>, String> titleCol = new TableColumn<List<String>, String>("Title");
		TableColumn<List<String>, String> authorCol = new TableColumn<List<String>, String>("Author");
		TableColumn<List<String>, String> genreCol = new TableColumn<List<String>, String>("Genre");
		TableColumn<List<String>, String> quantityCol = new TableColumn<List<String>, String>("Quantity");
		TableColumn<List<String>, String> priceCol = new TableColumn<List<String>, String>("Price");
		
		
		idBookCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(0));
			}
		});
		
		titleCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(1));
			}
		});
		authorCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(2));
			}
		});
		genreCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(3));
			}
		});
		quantityCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(4));
			}
		});
		priceCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(5));
			}
		});
		
		bookEmTable.getColumns().addAll(idBookCol, titleCol, authorCol, genreCol, quantityCol, priceCol);
		bookEmTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		Label searchLabel = new Label("Search book");
		Label resultLabel = new Label("Results");
		
		searchField = new TextField();
		searchField.setPromptText("Search");
		
		categories = new ComboBox(FXCollections.observableArrayList("title", "author", "genre"));
		categories.setPromptText("Select category");
		
		//searchButton = new Button("Search");
		
		HBox searchBox = new HBox(20);
		
		searchBox.getChildren().addAll(searchField, categories, searchButton);
		vBook.getChildren().addAll(searchLabel, searchBox, resultLabel, bookEmTable, logoutEmButton);
		vBook.setPadding(new Insets(50));
		bookEm.setContent(vBook);
		
		
		Label sellLabel = new Label("Sell books");
		Label selectLabel = new Label("Select book:");
		Label quantityLabel = new Label("Insert quantity:");
		Label totalLabel = new Label("Total:");
		
		
		comboBook.setPromptText("Select book");
		
		quantityField = new TextField();
		quantityField.setPromptText("Quantity");
		
		totalField = new TextField();
		totalField.setEditable(false);
		
		//addButton = new Button("Add book");
		//sellButton = new Button("Sell");
		
		//cartTable = new TableView();
		
		cartTable.setEditable(true);
		
		HBox hselect = new HBox(20);
		HBox hinsert = new HBox(20);
		HBox htotal = new HBox(20);
		
		hselect.getChildren().addAll(selectLabel, comboBook);
		hinsert.getChildren().addAll(quantityLabel, quantityField);
		htotal.getChildren().addAll(totalLabel, totalField);
		
		TableColumn<List<String>, String> idCartBookCol = new TableColumn<List<String>, String>("Id book");
		TableColumn<List<String>, String> titleCartCol = new TableColumn<List<String>, String>("Title");
		TableColumn<List<String>, String> authorCartCol = new TableColumn<List<String>, String>("Author");
		TableColumn<List<String>, String> genreCartCol = new TableColumn<List<String>, String>("Genre");
		TableColumn<List<String>, String> unitPriceCartCol = new TableColumn<List<String>, String>("Unit price");
		TableColumn<List<String>, String> priceCartCol = new TableColumn<List<String>, String>("Total price");
		

		
		
		idCartBookCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(0));
			}
		});
		
		idCartBookCol.setMinWidth(100);
		
		titleCartCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(1));
			}
		});
		
		titleCartCol.setMinWidth(100);
		
		authorCartCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(2));
			}
		});
		
		authorCartCol.setMinWidth(100);
		
		genreCartCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(3));
			}
		});
		
		genreCartCol.setMinWidth(100);
		
		unitPriceCartCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(4));
			}
		});
		
		unitPriceCartCol.setMinWidth(100);
		
		quantityCartCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(5));
			}
		});
		quantityCartCol.setMinWidth(100);
		quantityCartCol.setCellFactory(TextFieldTableCell.forTableColumn());
		
		priceCartCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(6));
			}
		});
		
		priceCartCol.setMinWidth(100);
		
		

		
		cartTable.getColumns().addAll(idCartBookCol, titleCartCol, authorCartCol, genreCartCol, unitPriceCartCol, quantityCartCol, priceCartCol);
		
		vsell.getChildren().addAll(searchLabel, hselect, hinsert, addButton, cartTable, deleteBookCartButton, htotal, sellButton);
		vsell.setPadding(new Insets(50));
		sellEm.setContent(vsell);
		
		tabEm.getTabs().addAll(bookEm, sellEm);
		
		Group root = new Group();
		String image = Window.class.getResource("image.jpg").toExternalForm();
		tabEm.setStyle("-fx-background-image: url('" + image + "'); -fx-background-position: center center; -fx-background-repeat: stretch;");
		
		Scene scene = new Scene(root, 800, 600);
		
		
		BorderPane borderPane = new BorderPane();
		borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        
        borderPane.setCenter(tabEm);
        root.getChildren().add(borderPane);
        
        setScene(scene);
	}
	
	public void adminWindow(){
		TabPane tabPaneAdmin = new TabPane();
		
		Tab tabEm = new Tab("Employees");
		Tab tabBook = new Tab("Books");
		Tab tabReport = new Tab("Generate reports");
		
		VBox vboxEm = new VBox(20);
		VBox vboxBook = new VBox(20);
		VBox vboxReport = new VBox(20);
		
		/***********Out of stock table****************/
		outOfStockTable.setMinWidth(600);
		outOfStockTable.setMaxHeight(600);
		
		Label labelStock = new Label("Out of stock books");
		
		TableColumn<List<String>, String> idOosBookCol = new TableColumn<List<String>, String>("Id book");
		TableColumn<List<String>, String> titleOosBookCol = new TableColumn<List<String>, String>("Title");
		TableColumn<List<String>, String> authorOosBookCol = new TableColumn<List<String>, String>("Author");
		TableColumn<List<String>, String> genreOosBookCol = new TableColumn<List<String>, String>("Genre");
		TableColumn<List<String>, String> quantityOosBookCol = new TableColumn<List<String>, String>("Quantity");
		TableColumn<List<String>, String> priceOosBookCol = new TableColumn<List<String>, String>("Price");
		
		idOosBookCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(0));
			}
		});
		
		titleOosBookCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(1));
			}
		});
		
		
		authorOosBookCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(2));
			}
		});
		
		
		
		genreOosBookCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(3));
			}
		});
		
		
		quantityOosBookCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(4));
			}
		});
		
		
		priceOosBookCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(5));
			}
		});
		
		
		
		outOfStockTable.getColumns().addAll(idOosBookCol, titleOosBookCol, authorOosBookCol, genreOosBookCol, quantityOosBookCol, priceOosBookCol);
		outOfStockTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		RadioButton txtButton = new RadioButton("Text file");
		txtButton.setToggleGroup(group);
		txtButton.setSelected(true);
		txtButton.setUserData("textReport");
		
		RadioButton xmlButton = new RadioButton("Xml file");
		xmlButton.setToggleGroup(group);
		xmlButton.setUserData("xmlReport");
		
		vboxReport.getChildren().addAll(labelStock, outOfStockTable, new Label("Generate report with the books out of stock"), txtButton, xmlButton, generateButton);
		vboxReport.setAlignment(Pos.CENTER);
		vboxReport.setPrefSize(300, 300);
		vboxReport.setPadding(new Insets(50));
		
		/***********Employees' table******************/
		//emTable = new TableView();
		
		emTable.setEditable(true);
		emTable.setMinWidth(600);
		emTable.setMaxHeight(600);
		
		Label label = new Label("Employees");
		
		TableColumn<List<String>, String> idCol = 
				new TableColumn<List<String>, String>("Id employee");
		idCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(0));
			}
		});
		
		idCol.setMinWidth(50);
		
		
		usernameCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(1));
			}
		});
		
		usernameCol.setMinWidth(100);
		usernameCol.setCellFactory(TextFieldTableCell.forTableColumn());

		
		typeCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(2));
			}
		});
		
		typeCol.setMinWidth(100);
		typeCol.setCellFactory(TextFieldTableCell.forTableColumn());		
        
		emTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		emTable.getColumns().addAll(idCol, usernameCol, typeCol);
		
		HBox addEmBox = new HBox(20);
		
		
		usernameEmField.setPromptText("Username");
		passwordEmField.setPromptText("Password");
		
		
		//addEmButton = new Button("Add employee");
		//addListenerAddEmployee(new AddEmployeeListener());
		
		addEmBox.getChildren().addAll(usernameEmField, passwordEmField, addEmButton);
		
		vboxEm.getChildren().addAll(label, emTable, addEmBox, new Label("Select an employee in order to delete him:"), deleteEmButton, logoutAdminButton);
		
		vboxEm.setAlignment(Pos.CENTER);
		vboxEm.setPrefSize(300, 300);
		vboxEm.setPadding(new Insets(50));

		//bookTable = new TableView();
		
		bookTable.setMinWidth(600);
		bookTable.setMaxHeight(600);
		bookTable.setEditable(true);
		
		TableColumn<List<String>, String> idBookCol = new TableColumn<List<String>, String>("Id book");
		
		
		idBookCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(0));
			}
		});
		
		titleCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(1));
			}
		});
		
		titleCol.setCellFactory(TextFieldTableCell.forTableColumn());
		
		authorCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(2));
			}
		});
		
		authorCol.setCellFactory(TextFieldTableCell.forTableColumn());
		
		genreCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(3));
			}
		});
		
		genreCol.setCellFactory(TextFieldTableCell.forTableColumn());
		
		quantityCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(4));
			}
		});
		
		quantityCol.setCellFactory(TextFieldTableCell.forTableColumn());
		
		priceCol.setCellValueFactory(new Callback<CellDataFeatures<List<String>, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<List<String>, String> data){
				return new ReadOnlyStringWrapper(data.getValue().get(5));
			}
		});
		
		priceCol.setCellFactory(TextFieldTableCell.forTableColumn());
		
		
		bookTable.getColumns().addAll(idBookCol, titleCol, authorCol, genreCol, quantityCol, priceCol);
		bookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		Label bLabel = new Label("Books");
		HBox addBookBox = new HBox(20);
		
		titleField = new TextField();
		titleField.setPromptText("Title");
		
		authorField = new TextField();
		authorField.setPromptText("Author");
		
		genreField = new TextField();
		genreField.setPromptText("Genre");
		
		qField = new TextField();
		qField.setPromptText("Quantity");
		
		priceField = new TextField();
		priceField.setPromptText("Price");
		
		addBookBox.getChildren().addAll(titleField, authorField, genreField, qField, priceField, addBookButton);
		vboxBook.getChildren().addAll(bLabel, bookTable, addBookBox, new Label("Select a book in order to delete it:"), deleteBookButton);
		
		vboxBook.setAlignment(Pos.CENTER);
		vboxBook.setPrefSize(300, 300);
		vboxBook.setPadding(new Insets(50));
		
		tabEm.setContent(vboxEm);
		tabBook.setContent(vboxBook);
		tabReport.setContent(vboxReport);
		
		tabPaneAdmin.getTabs().addAll(tabEm, tabBook, tabReport);
		
		Group root = new Group();
		String image = Window.class.getResource("image.jpg").toExternalForm();
		tabPaneAdmin.setStyle("-fx-background-image: url('" + image + "'); -fx-background-position: center center; -fx-background-repeat: stretch;");
		
		Scene scene = new Scene(root, 800, 600);
		
		BorderPane borderPane = new BorderPane();
		borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        
        borderPane.setCenter(tabPaneAdmin);
        root.getChildren().add(borderPane);
        
        setScene(scene);
	}
	

	
	/**********************Listeners*********************************/
	
	public void addListenerLoginButton(EventHandler<ActionEvent> act){
		this.loginButton.setOnAction(act);
	}
	
	public void addListenerLogoutButton(EventHandler<ActionEvent> act){
		this.logoutEmButton.setOnAction(act);
		this.logoutAdminButton.setOnAction(act);
	}
	
	public void addListenerSearchButton(EventHandler<ActionEvent> act){
		this.searchButton.setOnAction(act);
	}
	
	public void addListenerAddButton(EventHandler<ActionEvent> act){
		this.addButton.setOnAction(act);
	}
	
	public void addListenerSellButton(EventHandler<ActionEvent> act){
		this.sellButton.setOnAction(act);
	}
	
	public void addListenerQuantityCart(EventHandler<CellEditEvent<List<String>, String>> act){
		quantityCartCol.setOnEditCommit(act);
	}
	
	public void addListenerTitleBook(EventHandler<CellEditEvent<List<String>, String>> act){
		titleCol.setOnEditCommit(act);
	}
	
	public void addListenerAuthorBook(EventHandler<CellEditEvent<List<String>, String>> act){
		authorCol.setOnEditCommit(act);
	}
	
	public void addListenerGenreBook(EventHandler<CellEditEvent<List<String>, String>> act){
		genreCol.setOnEditCommit(act);
	}
	
	public void addListenerQuantityBook(EventHandler<CellEditEvent<List<String>, String>> act){
		quantityCol.setOnEditCommit(act);
	}
	
	public void addListenerPriceBook(EventHandler<CellEditEvent<List<String>, String>> act){
		priceCol.setOnEditCommit(act);
	}
	
	
	public void addListenerEmUsername(EventHandler<CellEditEvent<List<String>, String>> act){
		usernameCol.setOnEditCommit(act);
	}
	
	public void addListenerEmType(EventHandler<CellEditEvent<List<String>, String>> act){
		typeCol.setOnEditCommit(act);
	}
	
	public void addListenerAddBookButton(EventHandler<ActionEvent> act){
		this.addBookButton.setOnAction(act);
	}
	
	public void addListenerAddEmployeeButton(EventHandler<ActionEvent> act){
		this.addEmButton.setOnAction(act);
	}
	
	public void addListenerGenerateButton(EventHandler<ActionEvent> act){
		this.generateButton.setOnAction(act);
	}
	
	public void addListenerDeleteBookCartButton(EventHandler<ActionEvent> act){
		this.deleteBookCartButton.setOnAction(act);
	}
	
//	public void addListenerUpdateBookButton(EventHandler<ActionEvent> act){
//		this.updateBookButton.setOnAction(act);
//	}
//	
//	public void addListenerUpdateEmployeeButton(EventHandler<ActionEvent> act){
//		this.updateEmButton.setOnAction(act);
//	}
	
	public void addListenerDeleteBookButton(EventHandler<ActionEvent> act){
		this.deleteBookButton.setOnAction(act);
	}
	
	public void addListenerDeleteEmployeeButton(EventHandler<ActionEvent> act){
		this.deleteEmButton.setOnAction(act);
	}
	
	/**********************End listeners****************************/
	
	
	/**********************Getters*********************************/
	public String getUsername(){
		return this.usernameField.getText();
	}
	
	public String getPassword(){
		return this.passwordField.getText();
	}
	
	public String getQuantity(){
		return this.quantityField.getText();
	}
	
	public String getEmUsername(){
		return this.usernameEmField.getText();
	}
	
	public String getEmPassword(){
		return this.passwordEmField.getText();
	}
	
	public String getTitle(){
		return this.titleField.getText();
	}
	
	public String getAuthor(){
		return this.authorField.getText();
	}
	
	public String getGenre(){
		return this.genreField.getText();
	}
	
	public String getBookQuantity(){
		return this.qField.getText();
	}
	
	public String getPrice(){
		return this.priceField.getText();
	}
	
	public String getSearchedItem(){
		return this.searchField.getText();
	}
	
	public String getCategory(){
		return this.categories.getValue();
	}
	
	public List<String> getSelectedUser(){
		return (List<String>) this.emTable.getSelectionModel().getSelectedItem();
	}
	
	public List<String> getSelectedBook(){
		return (List<String>) this.bookTable.getSelectionModel().getSelectedItem();
	}
	
	public String getReportOption(){
		if (group.getSelectedToggle() != null){
			return group.getSelectedToggle().getUserData().toString();
		}
		return null;
	}
	
	public List<String> getSelectedCartBook(){
		return (List<String>) this.cartTable.getSelectionModel().getSelectedItem();
	}
	
	public String getBookToAdd(){
		return this.comboBook.getValue();
	}
	
//	public String getBookId(){
//		
//	}
//	
//	public String getEmployeeId(){
//		
//	}
	/**********************End getters****************************/
	
	
	/**********************Setters*********************************/
	public void setTotal(String total){
		this.totalField.setText(total);
	}
	
	public void setScene(Scene newScene){
		this.scene = newScene;
		this.stage.setScene(this.scene);
		this.stage.show();
	}
	
	public void clearTables(){
		bookEmTable.getColumns().clear();
		bookTable.getColumns().clear();
		emTable.getColumns().clear();
		cartTable.getColumns().clear();
		outOfStockTable.getColumns().clear();
	}
	public void setBookEmTable(List<List<String>> table){
		ObservableList<List<String>> data = FXCollections.observableArrayList(table);
		bookEmTable.setItems(data);
	}
	
	public void setBookAdminTable(List<List<String>> table){
		ObservableList<List<String>> data = FXCollections.observableArrayList(table);
		bookTable.setItems(data);
	}
	
	public void setEmployeesTable(List<List<String>> table){
		ObservableList<List<String>> data = FXCollections.observableArrayList(table);
		emTable.setItems(data);
	}
	
	public void setCartTable(List<List<String>> table){
		ObservableList<List<String>> data = FXCollections.observableArrayList(table);
		cartTable.setItems(data);
	}
	
	public void setOutOfStockTable(List<List<String>> table){
		ObservableList<List<String>> data = FXCollections.observableArrayList(table);
		outOfStockTable.setItems(data);
	}
	
	public void setComboBook(List<String> list){
		ObservableList<String> olist = FXCollections.observableArrayList(list);
		comboBook.setItems(olist);
	}
	/**********************End setters****************************/
	
	/**********************Messages******************************/
	public void ErrorMessage(String title, String header, String content){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		
		alert.showAndWait();
	}
	
	public void SuccesMessage(String title, String header, String content){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		
		alert.showAndWait();
	}
	/*********************End messages***************************/
}
