package ui.comp3111;

import java.awt.List;
import core.comp3111.CoreData;
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
		 
	    private final ObservableList<String> datasetName =
	        FXCollections.observableArrayList(
	            new String("Henry")
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
	 
	        TableView<String> table = CreateDatasetTableView();
	        TableView<String> table2 = CreateChartTableView();

	        final HBox hbox = new HBox();
	        hbox.setSpacing(5);
	        hbox.setPadding(new Insets(10, 0, 0, 10));
	        hbox.getChildren().addAll(table, table2);
	 
	        ((Group) scene.getRoot()).getChildren().addAll(hbox);
	 
	        stage.setScene(scene);
	        stage.show();
	    }
	    

	    public TableView<String> CreateDatasetTableView(){
	    	TableView<String> table = new TableView<String>();
	    	table.setEditable(true);
	        TableColumn<Void, String> Dataset = new TableColumn("Dataset");
	        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    	return table;
	    }
	    
	    public TableView<String> CreateChartTableView(){
	    	TableView<String> table = new TableView<String>();
	    	table.setEditable(true);
	        TableColumn Chart = new TableColumn("Chart");
	        table.getColumns().addAll(Chart);
	        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    	return table;
	    }   

	} 
