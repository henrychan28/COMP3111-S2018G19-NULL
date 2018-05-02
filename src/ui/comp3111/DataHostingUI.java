package ui.comp3111;

import java.util.HashSet;
import java.util.Set;

import core.comp3111.Constants;
import core.comp3111.CoreData;
import core.comp3111.DataTable;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DataHostingUI extends Application {
		private ObservableList<DataTable> parentTableList = null;
		private ObservableList<DataTable> childTableList = null;
		//private static ObservableList<DataTable> graphTableList = null;
		private int childIndex = 0;
		private TableView<DataTable> parentTable;
		private TableView<DataTable> childTable;
		//private static TableView<DataTable> graphTable;
		private enum EventHandlerType {
		    PARENT, CHILD, GRAPH
		}
		private Stage stageDataHostingUI;

	    public static ObservableList<DataTable> InjectDataTable(int axis, int parent) {
	    	//To-Do: Once the CoreData is completed, retrieve data from there
	    	CoreData coreData = CoreData.getInstance();
	    	ObservableList<DataTable> dataSet = FXCollections.observableArrayList();
	    	if (axis == Constants.OUTER && parent == -1) {
		    	int outerSize = coreData.getOuterSize();
		    	for(int outerIndex=0;outerIndex<outerSize;outerIndex++) {
		    		dataSet.add(coreData.getDataTable(new int[] {outerIndex, 0}));
		    	}
	    	}
	    	else if (axis==Constants.INNER) {
	    		int innerSize = coreData.getInnerSize(parent);
	    		for(int innerIndex=0;innerIndex<innerSize;innerIndex++) {
		    		dataSet.add(coreData.getDataTable(new int[] {parent, innerIndex}));
	    		}
	    	}
	    	return dataSet;
	    }
		
		/**
		 * SetTable helps to set the parentTableList and childTableList in the class with the aid 
		 * of GetDataTable function. 
		 * 
		 * @param axis 
		 *            - the axis to be scan along (INNER or OUTER)
		 * @param outer
		 * 			  - if scan along OUTER, put it to be -1
		 * 			  - if scan along INNER, provide the parentIndex
		 */
	    private void SetTable(int axis, int outerIndex) {
	    	if(axis==Constants.OUTER) {
	    		parentTableList = InjectDataTable(Constants.OUTER, -1);
	    	}
	    	else if(axis==Constants.INNER) {
	    		childTableList = InjectDataTable(Constants.INNER, outerIndex);
	    	}
	    }
	    
	    public static void main(String[] args) {
	        launch(args);
	    }
	    
	    @Override
	    public void start(Stage stage) {
	    	//Initialize the parent table
	    	SetTable(Constants.OUTER, -1);
	    	SetTable(Constants.INNER, childIndex);
	    	
	        stageDataHostingUI = stage;
	    	stageDataHostingUI.setTitle("Table View Sample");
	    	stageDataHostingUI.setWidth(650);
	    	stageDataHostingUI.setHeight(500);
	    	
	        parentTable = CreateTableView("Parent Table", "tableName", parentTableList, EventHandlerType.PARENT);
			parentTable.setOnMouseClicked(new ParentTableFactoryEventHandler());
	        childTable = CreateTableView("Child Table", "tableName", childTableList, EventHandlerType.CHILD);

	        HBox hbox = new HBox();
	        
	        VBox vbox = new VBox(10);
	        vbox.setAlignment(Pos.CENTER);
	        vbox.setPadding(new Insets(10, 10, 10, 10));

	        
	        Button textFilterButton = new Button("Text Filter");
	        textFilterButton.setOnMouseClicked(new TextFilterButtonEventHandler());

	        Button randomFilterButton = new Button("Random Filter");
	        randomFilterButton.setOnMouseClicked(new RandomFilterButtonEventHandler());
	        
	        Button chartButton = new Button("Chart");
	        chartButton.setOnMouseClicked(new ChartButtonEventHandler());
	        
	        Button backButton = new Button("Back");
	        backButton.setOnMouseClicked(new BackButtonEventHandler());
	        
	        vbox.getChildren().addAll(textFilterButton, randomFilterButton, chartButton, backButton);
	        
	        hbox.getChildren().addAll(parentTable, childTable, vbox);
	        Scene scene = new Scene(hbox);
	        stageDataHostingUI.setScene(scene);
	        stageDataHostingUI.show();
	    }
	    
		/**
		 * CreateTableView function returns a single column table view 
		 * 
		 * @param tableName
		 *            - a string for setting the table name
		 * @param propertyName
		 * 			  - the propertyName on the tableList wanted to be shown on the column
		 * @param tableList
		 * 			  - the tableList containing desired data for display
		 * @return table
		 * 			  - the table created 
		 */
	    private TableView<DataTable> CreateTableView(String tableName, String propertyName, ObservableList<DataTable> tableList,
	    											EventHandlerType eventType) {
	    	TableView<DataTable> table = new TableView<>();
	        TableColumn<DataTable, String> Dataset = new TableColumn<>(tableName);
	        Dataset.setCellValueFactory(new PropertyValueFactory<>(propertyName));
	        table.setItems(tableList);
	        table.getColumns().add(Dataset);
	        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    	return table;	    	
	    }

	    
	    private class ParentTableFactoryEventHandler implements EventHandler<MouseEvent> {
	        @Override
	        public void handle(MouseEvent t) {
	            childIndex = parentTable.getSelectionModel().getSelectedIndex();
	            SetTable(Constants.INNER, childIndex);
	            childTable.setItems(childTableList);
	            childTable.refresh();
	        }
	    }
	    
	    private class ChartButtonEventHandler implements EventHandler<MouseEvent> {
	        @Override
	        public void handle(MouseEvent t) {
	        	
            	DataTable selectedTable = childTable.getSelectionModel().getSelectedItem();
            	if(selectedTable==null) return;
            	CoreData coreData = CoreData.getInstance();
            	int[] dataTableIndex = coreData.searchForDataTable(selectedTable.getTableName());   
                GenerateChartUI generateChartUI = new GenerateChartUI(dataTableIndex);
                generateChartUI.start(stageDataHostingUI);
	        }
	    } 
	    
	    private class TextFilterButtonEventHandler implements EventHandler<MouseEvent> {
	        @Override
	        public void handle(MouseEvent t) {
            	DataTable selectedTable = childTable.getSelectionModel().getSelectedItem();
            	if(selectedTable==null) return;
            	DataTextFilterUI dataTextFilterUI = new DataTextFilterUI(selectedTable);
            	dataTextFilterUI.start(stageDataHostingUI);
            }
        }
	    
	    private class RandomFilterButtonEventHandler implements EventHandler<MouseEvent> {
	        @Override
	        public void handle(MouseEvent t) {
            	DataTable selectedTable = childTable.getSelectionModel().getSelectedItem();
            	if(selectedTable==null) return;
            	DataRandomFilterUI dataTextFilterUI = new DataRandomFilterUI(selectedTable);
            	dataTextFilterUI.start(stageDataHostingUI);
            }
        }
	    
	    private class BackButtonEventHandler implements EventHandler<MouseEvent> {
	        @Override
	        public void handle(MouseEvent t) {
            	Main main = new Main();
            	main.start(stageDataHostingUI);
            }
        }
} 

