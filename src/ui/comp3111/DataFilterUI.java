package ui.comp3111;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import core.comp3111.CoreData;
import core.comp3111.DataColumn;
import core.comp3111.DataFilter;
import core.comp3111.DataTable;
import core.comp3111.SampleDataGenerator;
import core.comp3111.UIHelperFunction;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class DataFilterUI extends Application {
	private static String[] currentText;
	private static String[] columnNames;
	private static ObservableList<DataColumn> columnList;
	private static TableView<String> columnTableView;
	private static TableView<String> textTableView;
	private static DataTable currentTable;
	
	private enum EventHandlerType {
	    COLUMN, TEXT
	}
    
    private void InjectCurrentText(String columnName) throws Exception {
    	if(currentTable.getNumCol()==0 || (columnName!=null && currentTable.getCol(columnName)==null)) {
    		throw new Exception("Column name not found or there is no column in current table...");
    	}
    	//Handle initialization case
    	if(columnName==null) columnName =currentTable.getColumnNames()[0];
    	Object[] currentTextObject = currentTable.getCol(columnName).getData();
    	currentText = Arrays.copyOf(currentTextObject, currentTextObject.length, String[].class);
    }
    
    private void InjectColumnList() {
    	columnList = FXCollections.observableArrayList();
    	for(DataColumn dataColumn: currentTable.getCol()) {
    		columnList.add(dataColumn);
    	}
    }
    private void InjectColumnName() {
    	columnNames = currentTable.getColumnNames();
	}

    	
    private void SetCurrentTable(DataTable dataTable) throws Exception {
    	DataFilter filter = DataFilter.getFilter();
    	currentTable = filter.GetTableTextLabels(dataTable);
    	InjectColumnName();
    	InjectColumnList();
    	InjectCurrentText(null);
    }
    

	public static void main(String[] args) {
        launch(args);
    }
    
    private void TestInitialize() {
    	DataTable testTable = SampleDataGenerator.generateSampleLineData();
    	try{
    		SetCurrentTable(testTable);
    	} catch (Exception e) {
    		e.getMessage();
    	}
    }
 
    @Override
    public void start(Stage stage) {
    	//Initialize the UI test data
    	TestInitialize();
        System.out.println("Finished initialization...");
    	stage.setTitle("Table View Sample");
        stage.setWidth(650);
        stage.setHeight(500);
        columnTableView = createTableView("hello", columnNames);
        textTableView = createTableView("hello", currentText);
        textTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        final HBox hbox = new HBox();
        hbox.getChildren().addAll(columnTableView, textTableView);
        Scene scene = new Scene(hbox);
        stage.setScene(scene);
        stage.show();
    }
    
	private TableView<String> createTableView(String tableName, String[] data) {
		TableView<String> table = new TableView<>();
		TableColumn<String, String> Dataset = new TableColumn(tableName);
		Dataset.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String, String>, ObservableValue<String>>(){
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<String, String> p){
				return new SimpleStringProperty(p.getValue());
			}
		});
		table.getItems().addAll(Arrays.asList(data));
		table.getColumns().add(Dataset);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		return table;
	}

}
