package ui.comp3111;

import java.awt.List;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DataHostingUI extends Application {
		 
	    private TableView<Person> table2 = new TableView<Person>();
	    private final ObservableList<Person> data =
	        FXCollections.observableArrayList(
	            new Person("Jacob", "Smith", "jacob.smith@example.com"),
	            new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
	            new Person("Ethan", "Williams", "ethan.williams@example.com"),
	            new Person("Emma", "Jones", "emma.jones@example.com"),
	            new Person("Michael", "Brown", "michael.brown@example.com")
	        );
	   
	    public static void main(String[] args) {
	        launch(args);
	    }
	 
	    @Override
	    public void start(Stage stage) {
	        Scene scene = new Scene(new Group());
	        stage.setTitle("Table View Sample");
	        stage.setWidth(650);
	        stage.setHeight(500);
	 
	        final Label label = new Label("Address Book");
	        label.setFont(new Font("Arial", 20));
	 
	        TableView<Person> table = CreateDatasetTableView();
	        TableView<Person> table2 = CreateChartTableView();

	        final HBox hbox = new HBox();
	        hbox.setSpacing(5);
	        hbox.setPadding(new Insets(10, 0, 0, 10));
	        hbox.getChildren().addAll(table, table2);
	 
	        ((Group) scene.getRoot()).getChildren().addAll(hbox);
	 
	        stage.setScene(scene);
	        stage.show();
	    }
	 
	    public TableView<Person> CreateDatasetTableView(){
	    	TableView<Person> table = new TableView<Person>();
	    	table.setEditable(true);
	        TableColumn Dataset = new TableColumn("Dataset");
	        table.getColumns().addAll(Dataset);
	        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    	return table;
	    }
	    
	    public TableView<Person> CreateChartTableView(){
	    	TableView<Person> table = new TableView<Person>();
	    	table.setEditable(true);
	        TableColumn Chart = new TableColumn("Chart");
	        table.getColumns().addAll(Chart);
	        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    	return table;
	    }   
	    
	    public static class Person {
	 
	        private final SimpleStringProperty firstName;
	        private final SimpleStringProperty lastName;
	        private final SimpleStringProperty email;
	 
	        private Person(String fName, String lName, String email) {
	            this.firstName = new SimpleStringProperty(fName);
	            this.lastName = new SimpleStringProperty(lName);
	            this.email = new SimpleStringProperty(email);
	        }
	 
	        public String getFirstName() {
	            return firstName.get();
	        }
	 
	        public void setFirstName(String fName) {
	            firstName.set(fName);
	        }
	 
	        public String getLastName() {
	            return lastName.get();
	        }
	 
	        public void setLastName(String fName) {
	            lastName.set(fName);
	        }
	 
	        public String getEmail() {
	            return email.get();
	        }
	 
	        public void setEmail(String fName) {
	            email.set(fName);
	        }
	    }
	} 
