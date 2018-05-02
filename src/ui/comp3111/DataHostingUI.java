package ui.comp3111;

import core.comp3111.Constants;
import core.comp3111.CoreData;
import core.comp3111.DataTable;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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


/**
 * DataHostingUI is responsible for creating, launching and maintaining the window
 * of displaying dataTable available in the database. It provides an interface for 
 * users to select dataTable for manipulations.
 * 
 * @author Henry Chan

 */
public class DataHostingUI extends Application {
		private ObservableList<DataTable> parentTableList = null;
		private ObservableList<DataTable> childTableList = null;
		private int childIndex = 0;
		private TableView<DataTable> parentTable;
		private TableView<DataTable> childTable;
		private enum EventHandlerType {
		    PARENT, CHILD, GRAPH
		}
		private Stage stageDataHostingUI;
		
		/**
		 * InjectDataTable is used to inject value to the  
		 * 
		 * @param axis 
		 *            - the axis to be scan along (INNER or OUTER)
		 * @param outerIndex
		 * 			  - if scan along OUTER, put it to be -1
		 * 			  - if scan along INNER, provide the parentIndex
		 */
	    private ObservableList<DataTable> datatTableToObservableList(int axis, int parent) {
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
		 * SetTable is used to set the parentTableList or childTableList 
		 * 
		 * @param axis 
		 *            - the axis to be scan along (INNER or OUTER)
		 * @param outerIndex
		 * 			  - if scan along OUTER, put it to be -1
		 * 			  - if scan along INNER, provide the parentIndex
		 */
	    private void setTable(int axis, int outerIndex) {
	    	if(axis==Constants.OUTER) {
	    		parentTableList = datatTableToObservableList(Constants.OUTER, -1);
	    	}
	    	else if(axis==Constants.INNER) {
	    		childTableList = datatTableToObservableList(Constants.INNER, outerIndex);
	    	}
	    }
	    
	    public static void main(String[] args) {
	        launch(args);
	    }
	    
	    @Override
	    public void start(Stage stage) {
	    	//Initialize the parent table
	    	setTable(Constants.OUTER, -1);
	    	setTable(Constants.INNER, childIndex);
	    	
	        stageDataHostingUI = stage;
	    	stageDataHostingUI.setTitle("Table View Sample");
	    	stageDataHostingUI.setWidth(650);
	    	stageDataHostingUI.setHeight(500);
	    	
	        parentTable = createTableView("Parent Table", "tableName", parentTableList, EventHandlerType.PARENT);
			parentTable.setOnMouseClicked(new ParentTableFactoryEventHandler());
	        childTable = createTableView("Child Table", "tableName", childTableList, EventHandlerType.CHILD);

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
		 * 			  - the tableView created 
		 */
	    private TableView<DataTable> createTableView(String tableName, String propertyName, ObservableList<DataTable> tableList,
	    											EventHandlerType eventType) {
	    	TableView<DataTable> table = new TableView<>();
	        TableColumn<DataTable, String> Dataset = new TableColumn<>(tableName);
	        Dataset.setCellValueFactory(new PropertyValueFactory<>(propertyName));
	        table.setItems(tableList);
	        table.getColumns().add(Dataset);
	        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    	return table;	    	
	    }

    	/**
    	 * Event handler for updating the childTableView after selected a parent 
    	 * table on the parentTableView
    	 */
	    private class ParentTableFactoryEventHandler implements EventHandler<MouseEvent> {
	        @Override
	        public void handle(MouseEvent t) {
	            childIndex = parentTable.getSelectionModel().getSelectedIndex();
	            setTable(Constants.INNER, childIndex);
	            childTable.setItems(childTableList);
	            childTable.refresh();
	        }
	    }
	    
    	/**
    	 * Event handler for transition to the GenerateChartUI module
    	 */
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
	    
    	/**
    	 * Event handler for transition to the DataTextFilterUI module
    	 */
	    private class TextFilterButtonEventHandler implements EventHandler<MouseEvent> {
	        @Override
	        public void handle(MouseEvent t) {
	        	//Go to the DataTextFilterUI
            	DataTable selectedTable = childTable.getSelectionModel().getSelectedItem();
            	if(selectedTable==null) return;
            	DataTextFilterUI dataTextFilterUI = new DataTextFilterUI(selectedTable);
            	dataTextFilterUI.start(stageDataHostingUI);
            }
        }
	    
    	/**
    	 * Event handler for transition to the DataRandomFilterUI module
    	 */    
	    private class RandomFilterButtonEventHandler implements EventHandler<MouseEvent> {
	        @Override
	        public void handle(MouseEvent t) {
	        	//Go to the DataRandomFilterUI
            	DataTable selectedTable = childTable.getSelectionModel().getSelectedItem();
            	if(selectedTable==null) return;
            	DataRandomFilterUI dataTextFilterUI = new DataRandomFilterUI(selectedTable);
            	dataTextFilterUI.start(stageDataHostingUI);
            }
        }
	    
    	/**
    	 * Event handler for transition to the main page
    	 */  
	    private class BackButtonEventHandler implements EventHandler<MouseEvent> {
	        @Override
	        public void handle(MouseEvent t) {
	        	//Return to the main menu
            	Main main = new Main();
            	main.start(stageDataHostingUI);
            }
        }
} 

