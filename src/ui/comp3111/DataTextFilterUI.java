package ui.comp3111;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import core.comp3111.CoreData;
import core.comp3111.DataFilter;
import core.comp3111.DataTable;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * DataTextFilterUI is the UI for user to select text in columns to be retained,
 * enabling user to replace/add the filtered dataTable into the database. 
 * 
 * @author Henry Chan

 */
public class DataTextFilterUI extends Application {
	public static final int OUTER = 0;
	private String[] currentText;
	private String[] columnNames;
	private TableView<String> columnTableView;
	private TableView<String> textTableView;
	private DataTable currentTable;
	private HashMap<String, Set<String>> textMap;
	private HashMap<String, Set<String>> selectedRetainText = new HashMap<>();
	private TextField tableNameTextField;
	private Stage stageDataFilterUI;
	private Text textFilterText;
	private ObservableList<String> replacementOptions = 
		    FXCollections.observableArrayList(
		        "Yes", "No"
		    );
	private ComboBox<String> replacementOptionsComboBox;
	
	/** 
	 * Creates a DataTextFilterUI with the specified dataTable.
	 * @param dataTable
	 * 			the input dataTable
	 */
    public DataTextFilterUI(DataTable dataTable) {
    	currentTable = dataTable;
    }
    
	/** 
	 * Provided with a columnName of interest, injectCurrentText will find 
	 * the column with columnName in the dataTable and inject it to the 
	 * currentText variable, which will determine the text to be displayed on 
	 * the textTableView
	 * @param columnName
	 * 			name of column in the dataTable targeted to be inject 
	*/
    private void injectCurrentText(String columnName) throws Exception {
    	if(currentTable.getNumCol()==0 || (columnName!=null && currentTable.getCol(columnName)==null)) {
    		throw new Exception("Column name not found or there is no column in current table...");
    	}
    	
    	//Handle initialization case
    	if(columnName==null) columnName =textMap.keySet().toArray(new String[textMap.keySet().size()])[0];
    	System.out.println("First column name: " + columnName);
    	Set<String> currentTextSet = textMap.get(columnName);
    	currentText = currentTextSet.toArray(new String[currentTextSet.size()]);
    }
    
	/**
	 * injectColumnName is called after the dataTable and textMap is set
	 * for injecting column names of given textMap to the variable columnNames 
	 */
    private void injectColumnName() {
    	Set<String> columnNameSet = textMap.keySet();
    	columnNames = columnNameSet.toArray(new String[columnNameSet.size()]);
	}

	/** 
	 * setCurrentTable is used to set the dataTable and inject corresponding 
	 * value to attributes in the DataTextFilterUI for initializing the UI
	 * @param dataTable
	 * 			dataTable for displaying in the UI
	 */	
    private void setCurrentTable(DataTable dataTable) throws Exception {
    	currentTable = dataTable;
    	textMap = DataFilter.GetTableTextLabels(dataTable);
    	if(textMap==null||textMap.size()==0) {
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Filter Error");
    		alert.setHeaderText("Filter Error");
    		alert.setContentText("Selected table does not have any string entry.");
    		alert.showAndWait();
        	throw (new Exception("No String entry in the table"));
    	}
    	injectColumnName();
    	injectCurrentText(null);
    	
    }
    

	public static void main(String[] args) {
        launch(args);
    }
    
 
    @Override
    public void start(Stage stage) {
    	stageDataFilterUI = stage;
		stageDataFilterUI.setTitle("Data Filter Interface");
		stageDataFilterUI.setWidth(750);
		stageDataFilterUI.setHeight(500);
		
    	try {
			setCurrentTable(currentTable);
		} catch (Exception e) {
			return;
		}
    	
        columnTableView = createTableView("Column Name", columnNames);
        columnTableView.setOnMouseClicked(new ColumnTableEventHandler());
        columnTableView.getSelectionModel().select(0);;
        
        textTableView = createTableView("Text", currentText);
        textTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        Text tableNameTextBox = new Text();
        tableNameTextBox.setText("Enter Table Name:");
        
        tableNameTextField = new TextField ();

        Text replaceOriginalTextFilter = new Text();
        replaceOriginalTextFilter.setText("Replace original table?");
        
        replacementOptionsComboBox = new ComboBox<>(replacementOptions);
        replacementOptionsComboBox.getSelectionModel().select(0);
        
        Button selectButton = new Button("Select Text");
        selectButton.setOnMouseClicked(new SelectButtonEventHandler());
        
        Button generateButton = new Button("Generate Table");
        generateButton.setOnMouseClicked(new GenerateButtonEventHandler());
        
        Button backButton = new Button("Back");
        backButton.setOnMouseClicked(new BackButtonEventHandler());
                
        textFilterText = new Text();

        HBox textFilterBox = new HBox();
        textFilterBox.getChildren().addAll(selectButton, generateButton);
        textFilterBox.setAlignment(Pos.CENTER);

        VBox textFilterVbox = new VBox(10);
        textFilterVbox.setPadding(new Insets(10, 10, 10, 10));
        textFilterVbox.setAlignment(Pos.CENTER);
        textFilterVbox.getChildren().addAll(tableNameTextBox, tableNameTextField, replaceOriginalTextFilter, 
        		                            replacementOptionsComboBox, textFilterBox, backButton, textFilterText);
        
        HBox hbox = new HBox();
        hbox.getChildren().addAll(columnTableView, textTableView, textFilterVbox);
        Scene scene = new Scene(hbox);
        stageDataFilterUI.setScene(scene);
        stageDataFilterUI.show();
    }
    
	/** 
	 * createTableView is used to create a tableView from a list of string 
	 * and set the tableView name with a given tableName
	 * @param tableName
	 * 			name of the table on the tableView
	 * @param data
	 * 			string to be displayed on the tableView
	 * @return table
	 * 			- tableView with given name and data
	*/	
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
	
	/**
	 * Event handler for updating textTableView after selecting a column 
	 */  
    private class ColumnTableEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent t) {
             String selectedColumn = columnTableView.getSelectionModel().getSelectedItem();
             try {
			injectCurrentText(selectedColumn);
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
    
	/**
	 * Event handler for updating texts being selected to be retain for filtering
	 */  
    private class SelectButtonEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent t) {
    		String selectedColumn = columnTableView.getSelectionModel().getSelectedItem();
    		ObservableList<String> selectedTextList = textTableView.getSelectionModel().getSelectedItems();
    		Set<String> selectedTextSet = new HashSet<String>();
    		for(String selectedText: selectedTextList) {
    			selectedTextSet.add(selectedText);
    		}
    		selectedRetainText.put(selectedColumn, selectedTextSet);
	}
    }
    
	/**
	 * GenerateButtonEventHandler fires upon a click on the "generate" button.
	 * It will retrieve the table names from text fields and check whether is there any 
	 * name clash with dataTables in CoreData. Upon confirmation of correct name 
	 * and available name, if filtered tables are not empty, they will be added/replaced 
	 * to the CoreData according to user's choice.
	 */  
    private class GenerateButtonEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent t) 
        {
        	CoreData coreData = CoreData.getInstance();
        	String tableName = tableNameTextField.getText();
        	//Case: there is no user input
        	if(tableName=="" || tableName.trim().isEmpty()) {
        		textFilterText.setText("Table Name cannot be empty");
        		return;
        	}
        	//Case: the table name already exist
        	else if(!Arrays.equals(coreData.searchForDataTable(tableName),new int[] {-1,-1})) {
        		textFilterText.setText("Table Name already exist");
        		return;
        	}
        	//This is the dataTable that will be added to CoreData
        	DataTable filteredDataTable = DataFilter.TextFilter(currentTable, selectedRetainText);
        	filteredDataTable.setName(tableName);
        	
        	if(filteredDataTable.getNumRow()==0) {
        		textFilterText.setText("Please select entry to retain");
        		return;
        	}
        	int[] tableIndex = coreData.searchForDataTable(currentTable.getTableName());
        	if(replacementOptionsComboBox.getSelectionModel().getSelectedItem()=="Yes") {
            	coreData.setDataTable(tableIndex, filteredDataTable);
            	textFilterText.setText("Table \""+ tableName +"\" successfully replaced ");
        	} else {
            	coreData.addChildTable(filteredDataTable, tableIndex[OUTER]);
            	textFilterText.setText("Table \""+ tableName +"\" successfully created");

        	}
	}
    }
    

	/**
	 * Event handler for transition to the DataHostingUI
	 */  
    private class BackButtonEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent t) {
        	//Return to dataHostingUI
        	DataHostingUI dataHostingUI = new DataHostingUI();
        	dataHostingUI.start(stageDataFilterUI);
        }
    }
}
