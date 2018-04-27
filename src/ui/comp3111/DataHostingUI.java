package ui.comp3111;

import core.comp3111.Constants;
import core.comp3111.CoreData;
import core.comp3111.DataTable;
import core.comp3111.UIHelperFunction;
import javafx.application.Application;
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

public class DataHostingUI extends Application {
		private static ObservableList<DataTable> parentTableList = null;
		private static ObservableList<DataTable> childTableList = null;
		private static ObservableList<DataTable> graphTableList = null;
		private static int childIndex = 0;
		private static TableView<DataTable> parentTable;
		private static TableView<DataTable> childTable;
		private static TableView<DataTable> graphTable;
		private enum EventHandlerType {
		    PARENT, CHILD, GRAPH
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
	    		parentTableList = UIHelperFunction.InjectDataTable(Constants.OUTER, -1);
	    	}
	    	else if(axis==Constants.INNER) {
	    		childTableList = UIHelperFunction.InjectDataTable(Constants.INNER, outerIndex);
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
	    	
	        stage.setTitle("Table View Sample");
	        stage.setWidth(650);
	        stage.setHeight(500);
	        parentTable = CreateTableView("Parent Table", "tableName", parentTableList, EventHandlerType.PARENT);
			parentTable.setOnMouseClicked(new parentTableFactoryEventHandler());
	        childTable = CreateTableView("Child Table", "tableName", childTableList, EventHandlerType.CHILD);
			childTable.setOnMouseClicked(new childTableFactoryEventHandler());
	        graphTable = CreateTableView("Graph Table", "graphName", graphTableList, EventHandlerType.GRAPH);
			graphTable.setOnMouseClicked(new graphFactoryEventHandler());

	        final HBox hbox = new HBox();
	        hbox.getChildren().addAll(parentTable, childTable, graphTable);
	        Scene scene = new Scene(hbox);
	        stage.setScene(scene);
	        stage.show();
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

	    
	    private class parentTableFactoryEventHandler implements EventHandler<MouseEvent> {
	        @Override
	        public void handle(MouseEvent t) {
	             int index = parentTable.getSelectionModel().getSelectedIndex();
	            SetTable(Constants.INNER, index);
	            childTable.setItems(childTableList);
	        }
	    }
	    private class childTableFactoryEventHandler implements EventHandler<MouseEvent> {
	        @Override
	        public void handle(MouseEvent t) {
	        	System.out.println("This is the childTableFactoryEventHandler.");
	        }
	    }  
	    private class graphFactoryEventHandler implements EventHandler<MouseEvent> {
	        @Override
	        public void handle(MouseEvent t) {
	        	System.out.println("This is the graphFactoryEventHandler.");
	        }
	    }   

} 
