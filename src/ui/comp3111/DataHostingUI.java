package ui.comp3111;

import java.awt.List;
import core.comp3111.CoreData;
import core.comp3111.DataColumn;
import core.comp3111.DataTable;
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
		 
	    public ObservableList<DataTable> GetDataTable() {
	    	//To-Do: Once the CoreData is completed, retrieve data from there.
	    	ObservableList<DataTable> datasetName = FXCollections.observableArrayList();
	    	datasetName.add(new DataTable("table1"));
	    	datasetName.add(new DataTable("table2"));
	    	datasetName.add(new DataTable("table3"));
	    	datasetName.add(new DataTable("table4"));
	    	datasetName.add(new DataTable("table5"));
	    	datasetName.add(new DataTable("table6"));
	    	return datasetName;
	    }
	    public static void main(String[] args) {
	        launch(args);
	    }
	 
	    @Override
	    public void start(Stage stage) {
	        //Scene scene = new Scene(new Group());
	        stage.setTitle("Table View Sample");
	        stage.setWidth(650);
	        stage.setHeight(500);
	 
	        TableView<DataTable> table = CreateDatasetTableView();
	        //TableView<String> table2 = CreateChartTableView();

	        final HBox hbox = new HBox();
	        //hbox.setSpacing(5);
	        //hbox.setPadding(new Insets(10, 0, 0, 10));
	        hbox.getChildren().addAll(table);
	 
	        //((Group) scene.getRoot()).getChildren().addAll(hbox);
	        Scene scene = new Scene(hbox);
	        stage.setScene(scene);
	        stage.show();
	    }
	    

	    public TableView<DataTable> CreateDatasetTableView(){
	    	TableView<DataTable> table = new TableView<>();
	        TableColumn<DataTable, String> Dataset = new TableColumn<>("Dataset");
	        Dataset.setCellValueFactory(new PropertyValueFactory<>("tableName"));
	        ObservableList<DataTable> dataTables = GetDataTable();
	        table.setItems(dataTables);
	        table.getColumns().addAll(Dataset);
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
