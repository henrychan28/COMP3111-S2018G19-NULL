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
import javafx.scene.control.Button;
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

public class DataFilterUI extends Application {
	public static final int OUTER = 0;
	private String[] currentText;
	private String[] columnNames;
	private ObservableList<DataColumn> columnList;
	private TableView<String> columnTableView;
	private TableView<String> textTableView;
	private DataTable currentTable;
	private HashMap<String, Set<Object>> selectedRetainText = new HashMap<>();
	private TextField tableNameTextField;
	private TextField randomTableNameTextField1;
	private TextField randomTableNameTextField2;
	private Stage stageDataFilterUI;

    public DataFilterUI(DataTable dataTable) {
    	currentTable = dataTable;
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
    
 
    @Override
    public void start(Stage stage) {
    	try {
			SetCurrentTable(currentTable);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	stageDataFilterUI = stage;
		stageDataFilterUI.setTitle("Data Filter Interface");
		stageDataFilterUI.setWidth(900);
		stageDataFilterUI.setHeight(500);
        
        columnTableView = createTableView("Column Name", columnNames);
        columnTableView.setOnMouseClicked(new ColumnTableEventHandler());
        columnTableView.getSelectionModel().select(0);;
        
        textTableView = createTableView("Text", currentText);
        textTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        Text tableNameTextBox = new Text();
        tableNameTextBox.setText("Enter Table Name:");
        
        Button selectButton = new Button("Select Text");
        selectButton.setOnMouseClicked(new SelectButtonEventHandler());
        
        Button generateButton = new Button("Generate Table");
        generateButton.setOnMouseClicked(new GenerateButtonEventHandler());
        
        tableNameTextField = new TextField ();
        
        Button backButton = new Button("Back");
        backButton.setOnMouseClicked(new BackButtonEventHandler());

        HBox textFilterBox = new HBox();
        textFilterBox.getChildren().addAll(selectButton, generateButton);
        textFilterBox.setAlignment(Pos.CENTER);

        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        
        Text tableNamesTextBox = new Text();
        tableNamesTextBox.setText("Enter Table Names:");
        
        randomTableNameTextField1 = new TextField ();
        randomTableNameTextField2 = new TextField ();
        
        Slider splitSlider = new Slider();
        splitSlider.setMin(0);
        splitSlider.setMax(100);
        splitSlider.setValue(50);
        splitSlider.setShowTickLabels(true);
        splitSlider.setShowTickMarks(true);
        splitSlider.setMajorTickUnit(50);
        splitSlider.setMinorTickCount(5);
        splitSlider.setBlockIncrement(10);
        
        Text splitRatio = new Text();
        splitRatio.setText("50%");
        
        splitSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
            	splitRatio.setText(String.format("%.2f", new_val)+"%");
            }
        });
        
        
        VBox randomFilterVbox = new VBox(10);
        randomFilterVbox.setPadding(new Insets(10, 10, 10, 10));
        randomFilterVbox.setAlignment(Pos.CENTER);
        randomFilterVbox.getChildren().addAll(tableNamesTextBox, randomTableNameTextField1, randomTableNameTextField2, splitSlider, splitRatio);

        VBox textFilterVbox = new VBox(10);
        textFilterVbox.setPadding(new Insets(10, 10, 10, 10));
        textFilterVbox.setAlignment(Pos.CENTER);
        textFilterVbox.getChildren().addAll(tableNameTextBox, tableNameTextField, textFilterBox, backButton);
        
        HBox hbox = new HBox();
        hbox.getChildren().addAll(columnTableView, textTableView, textFilterVbox, separator, randomFilterVbox);
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
        	CoreData coreData = CoreData.getInstance();
        	int[] tableIndex = coreData.searchForDataTable(currentTable.getTableName());
        	coreData.addChildTable(filteredDataTable, tableIndex[OUTER]);
        	
        	filteredDataTable.printDataTable();
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
