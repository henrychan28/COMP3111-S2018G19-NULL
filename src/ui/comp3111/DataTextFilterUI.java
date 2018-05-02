package ui.comp3111;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import core.comp3111.CoreData;
import core.comp3111.DataColumn;
import core.comp3111.DataFilter;
import core.comp3111.DataTable;
import core.comp3111.SampleDataGenerator;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

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
	private TextField randomTableNameTextField1;
	private TextField randomTableNameTextField2;
	private Stage stageDataFilterUI;
	private Text textFilterText;
	private Text randomText;
	private ObservableList<String> replacementOptions = 
		    FXCollections.observableArrayList(
		        "Yes", "No"
		    );
	private ComboBox replacementOptionsComboBox;
	private ComboBox replacementOptionsComboBox2;
	private Slider splitSlider;

    public DataTextFilterUI(DataTable dataTable) {
    	currentTable = dataTable;
    }
    
    private void InjectCurrentText(String columnName) throws Exception {
    	if(currentTable.getNumCol()==0 || (columnName!=null && currentTable.getCol(columnName)==null)) {
    		throw new Exception("Column name not found or there is no column in current table...");
    	}
    	
    	//Handle initialization case
    	if(columnName==null) columnName =textMap.keySet().toArray(new String[textMap.keySet().size()])[0];
    	System.out.println("First column name: " + columnName);
    	Set<String> currentTextSet = textMap.get(columnName);
    	currentText = currentTextSet.toArray(new String[currentTextSet.size()]);
    }
    
    private void InjectColumnName() {
    	Set<String> columnNameSet = textMap.keySet();
    	columnNames = columnNameSet.toArray(new String[columnNameSet.size()]);
	}

    	
    private void SetCurrentTable(DataTable dataTable) throws Exception {
    	currentTable = dataTable;
    	DataFilter filter = DataFilter.getFilter();
    	textMap = filter.GetTableTextLabels(dataTable);
    	if(textMap==null||textMap.size()==0) {
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Filter Error");
    		alert.setHeaderText("Filter Error");
    		alert.setContentText("Selected table does not have any string entry.");
    		alert.showAndWait();
        	throw (new Exception("No String entry in the table"));
    	}
    	InjectColumnName();
    	InjectCurrentText(null);
    	
    }
    

	public static void main(String[] args) {
        launch(args);
    }
    
 
    @Override
    public void start(Stage stage) {
    	stageDataFilterUI = stage;
    	try {
			SetCurrentTable(currentTable);
		} catch (Exception e) {
			return;
		}
		stageDataFilterUI.setTitle("Data Filter Interface");
		stageDataFilterUI.setWidth(700);
		stageDataFilterUI.setHeight(500);
        
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
	
    private class ColumnTableEventHandler implements EventHandler<MouseEvent> {
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
        		Set<String> selectedTextSet = new HashSet<String>();
        		for(String selectedText: selectedTextList) {
        			selectedTextSet.add(selectedText);
        		}
        		selectedRetainText.put(selectedColumn, selectedTextSet);
        		PrintSelectedRetainText();
		}
    }
    
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
        	DataFilter filter = DataFilter.getFilter();
        	//This is the dataTable that will be added to CoreData
        	DataTable filteredDataTable = filter.TextFilter(currentTable, selectedRetainText);
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
    
    private class GenerateButtonEventHandler2 implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent t) 
        {
        	CoreData coreData = CoreData.getInstance();
        	String tableName1 = randomTableNameTextField1.getText();
        	String tableName2 = randomTableNameTextField2.getText();

        	//Case: there is no user input
        	if(tableName1=="" || tableName1.trim().isEmpty() || 
        	   tableName2=="" || tableName2.trim().isEmpty()) {
        		randomText.setText("Table Name cannot be empty");
        		return;
        	}
        	//Case: the table name already exist
        	else if(!Arrays.equals(coreData.searchForDataTable(tableName1),new int[] {-1,-1})||
        			!Arrays.equals(coreData.searchForDataTable(tableName2),new int[] {-1,-1})) {
        		randomText.setText("Table Name already exist");
        		return;
        	}
        	double splitRatio = splitSlider.getValue()/100.0;
        	DataFilter filter = DataFilter.getFilter();
        	
        	//This is the dataTable that will be added to CoreData
        	DataTable[] randomDataTables = filter.RandomSplitTable(currentTable, splitRatio);
        	randomDataTables[0].setName(tableName1);
        	randomDataTables[1].setName(tableName2);
        	
        	if(randomDataTables[0].getNumRow()==0||randomDataTables[1].getNumRow()==0) {
            	randomText.setText("Error: One of the split table has no row");
            	return;
        	}


        	int[] tableIndex = coreData.searchForDataTable(currentTable.getTableName());
        	if(replacementOptionsComboBox2.getSelectionModel().getSelectedItem()=="Yes") {
            	coreData.setDataTable(tableIndex, randomDataTables[0]);
            	coreData.addChildTable(randomDataTables[1], tableIndex[OUTER]);

            	randomText.setText("Table \""+ tableName1 + "\" and \"" + tableName2 + "\" successfully replaced");
        	} else {
            	coreData.addChildTable(randomDataTables[0], tableIndex[OUTER]);
            	coreData.addChildTable(randomDataTables[1], tableIndex[OUTER]);
            	randomText.setText("Table \""+ tableName1 + "\" and \"" + tableName2 + "\" successfully added");

        	}
		}
    }
    
    private class BackButtonEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent t) {
        	DataHostingUI dataHostingUI = new DataHostingUI();
        	dataHostingUI.start(stageDataFilterUI);
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
