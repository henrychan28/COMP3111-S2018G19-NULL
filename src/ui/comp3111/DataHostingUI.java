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
			//System.out.println("Inside start():before CreateTableView()");
	        parentTable = CreateTableView("Parent Table", "tableName", parentTableList, EventHandlerType.PARENT);
			//System.out.println("Inside start():after CreateTableView()");
			//parentTable.setOnMouseClicked(new parentTableFactoryEventHandler());
	        childTable = CreateTableView("Child Table", "tableName", childTableList, EventHandlerType.CHILD);
	        graphTable = CreateTableView("Graph Table", "graphName", graphTableList, EventHandlerType.GRAPH);
	        //parentTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

	        final HBox hbox = new HBox();
			//System.out.println("Inside start():before addAll(parentTable)");
	        //hbox.getChildren().addAll(parentTable);
			//System.out.println("Inside start():after addAll(parentTable)");

	        hbox.getChildren().addAll(parentTable, childTable, graphTable);
	        Scene scene = new Scene(hbox);
	        stage.setScene(scene);
			//System.out.println("Inside start():before stage.show()");
	        stage.show();
			//System.out.println("Inside start():after stage.show()");
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
	        TableColumn Dataset = new TableColumn(tableName);
	        Dataset.setCellValueFactory(new PropertyValueFactory<>(propertyName));
	        Dataset.setCellFactory(GetDataTableFactory(eventType));
	        table.setItems(tableList);
	        table.getColumns().addAll(Dataset);
	        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    	return table;	    	
	    }
	    
		/**
		 * GetDataTableFactory returns Callback which includes corresponding event handler
		 * which the behavior of cell is defined by StringTableCell().
		 * 
		 * @param eventType
		 *            - the type of event handler 
		 * @return dataTableFactory
		 * 			  - callback with desired event handler 
		 */
	    private Callback<TableColumn, TableCell> GetDataTableFactory(EventHandlerType eventType){
	    	Callback<TableColumn, TableCell> dataTableFactory = new Callback<TableColumn, TableCell>(){
	    		@Override
	    		public TableCell call(TableColumn p) {
	    			//System.out.println("Inside GetDataTableFactory: calling Callback.call");
	    			StringTableCell cell = new StringTableCell();
	    			//System.out.println("Inside GetDataTableFactory: calling Callback.call: Finished constructing StringTableCell");
	    			switch(eventType) {
	    				case PARENT:
	    	    			cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new parentTableFactoryEventHandler());
	    	    			break;
	    				case CHILD:
	    	    			cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new childTableFactoryEventHandler());
	    					break;
	    				case GRAPH:
	    	    			cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new graphFactoryEventHandler());
	    				default:
	    					throw new Error();
	    			}
	    			//System.out.println("Inside GetDataTableFactory: calling Callback.call: End of Callback.call");
	    			return cell;
	    		}
	    	};
			//System.out.println("Inside GetDataTableFactory: End of 'new Callback'");
	    	return dataTableFactory;
	    }
	    
	    //Define the behavior of cells in table
	    private class StringTableCell extends TableCell<DataTable, String> {
	    	public StringTableCell() {
	    		super();
	    		//System.out.println("Calling StringTableCell constructor...");
	    	}
	        @Override
	        public void updateItem(String item, boolean empty) {
	        	System.out.println(item);
	            super.updateItem(item, empty);
	            setText(empty ? null : getString());
	            setGraphic(null);
	        }
	 
	        private String getString() {
	            return getItem() == null ? "" : getItem().toString();
	        }
	    }
	    
	    private class parentTableFactoryEventHandler implements EventHandler<MouseEvent> {
	        @Override
	        public void handle(MouseEvent t) {
	            TableCell cell = (TableCell) t.getSource();
	            int index = cell.getIndex();
	            SetTable(Constants.INNER, index);
	            childTable.setItems(childTableList);
	            
	            /*
	             int temp = parentTable.getSelectionModel().getSelectedIndex();
	             System.out.println(temp);
	            */
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
