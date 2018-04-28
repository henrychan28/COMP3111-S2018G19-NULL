package ui.comp3111;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import core.comp3111.DataColumn;
import core.comp3111.DataFilter;
import core.comp3111.DataTable;
import core.comp3111.SampleDataGenerator;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
	private static HashMap<String, Set<Object>> selectedRetainText;
	private static TextField tableNameTextField;
    
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
    	selectedRetainText = new HashMap<>();
    	DataTable testTable = SampleDataGenerator.generateSampleDataForDataFilter();
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

    	stage.setTitle("Table View Sample");
        stage.setWidth(800);
        stage.setHeight(500);
        columnTableView = createTableView("Column Name", columnNames);
        columnTableView.setOnMouseClicked(new columnTableEventHandler());
        textTableView = createTableView("Text", currentText);
        textTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Button selectButton = new Button("Select");
        selectButton.setOnMouseClicked(new SelectButtonEventHandler());
        Button generateButton = new Button("Generate");
        generateButton.setOnMouseClicked(new GenerateButtonEventHandler());
        tableNameTextField = new TextField ();
        final HBox hbox = new HBox();
        hbox.getChildren().addAll(columnTableView, textTableView, selectButton, tableNameTextField, generateButton);
        Scene scene = new Scene(hbox);
        stage.setScene(scene);
        stage.show();
    }
    
	private TableView<String> createTableView(String tableName, String[] data) {
		TableView<String> table = new TableView<>();
		TableColumn<String, String> Dataset = new TableColumn<>(tableName);
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
	
    private class columnTableEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent t) {
             String selectedColumn = columnTableView.getSelectionModel().getSelectedItem();
             try {
				InjectCurrentText(selectedColumn);
				textTableView.getItems().setAll(Arrays.asList(currentText));
				if(selectedRetainText.get(selectedColumn)!=null) {
					for(Object previousSelectedText: selectedRetainText.get(selectedColumn)) {
				        textTableView.getSelectionModel().select((String)previousSelectedText);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }
    
    private class SelectButtonEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent t) {
        		String selectedColumn = columnTableView.getSelectionModel().getSelectedItem();
        		ObservableList<String> selectedTextList = textTableView.getSelectionModel().getSelectedItems();
        		Set<Object> selectedTextSet = new HashSet<Object>();
        		for(String selectedText: selectedTextList) {
        			selectedTextSet.add(selectedText);
        		}
        		selectedRetainText.put(selectedColumn, selectedTextSet);
        		PrintSelectedRetainText();
		}
    }
    
    private class GenerateButtonEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent t) {
        	DataFilter filter = DataFilter.getFilter();
        	//This is the dataTable that will be added to CoreData
        	DataTable filteredDataTable = filter.TextFilter(currentTable, selectedRetainText);
        	String userInput = tableNameTextField.getText();
        	filteredDataTable.setName(userInput);
        	filteredDataTable.printDataTable();
		}
    }
    
    private void PrintSelectedRetainText() {
		System.out.println("---------PrintSelectedRetainText()---------");

    	for(String column: selectedRetainText.keySet()) {
    		System.out.println(column);
    		for(Object entry: selectedRetainText.get(column)) {
    			System.out.print(entry.toString() + " ");
    		}
    		System.out.println();
    	}
		System.out.println("-----------------------------------------");

    }

}
