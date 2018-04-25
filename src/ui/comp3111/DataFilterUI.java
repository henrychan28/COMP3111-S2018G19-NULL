package ui.comp3111;

import core.comp3111.CoreData;
import core.comp3111.DataColumn;
import core.comp3111.DataTable;
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
import ui.comp3111.DataHostingUI.StringTableCell;
import ui.comp3111.DataHostingUI.childTableFactoryEventHandler;
import ui.comp3111.DataHostingUI.graphFactoryEventHandler;
import ui.comp3111.DataHostingUI.parentTableFactoryEventHandler;

public class DataFilterUI extends Application {
	private static DataTable currentTable = null;
	private static ObservableList<DataColumn> dataColumns = null;
	private static TableView<DataColumn> tableView;
	private enum EventHandlerType {
	    PARENT, CHILD, GRAPH
	}

    private static void SetCurrentTable(DataTable dataTable) {
    	currentTable = dataTable;
    	dataColumns = FXCollections.observableArrayList();
    	for (String columnName:dataTable.getColumnNames()) {
    		DataColumn column = dataTable.getCol(columnName);
    		dataColumns.add(column);
    	}
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    //Temporary function for getting dummy CoreData for demonstration purpose
    public CoreData getCoreData() {
		CoreData coreData = new CoreData();
		DataTable table = new DataTable("Test");
		int OUTER = 0;
		table = new DataTable("Parent");
		int[] res = coreData.addParentTable(table);
		table = new DataTable("Child");
		coreData.addChildTable(table,res[OUTER]);
		
		table = new DataTable("another");
		res = coreData.addParentTable(table);
		table = new DataTable("kid");
		coreData.addChildTable(table,res[OUTER]);
		table = new DataTable("yay");
		coreData.addChildTable(table,res[OUTER]);
		table = new DataTable("cat");
		coreData.addChildTable(table,res[OUTER]);
		table = new DataTable("dog");
		res = coreData.addChildTable(table,res[OUTER]);
    	return coreData;
    }
 
    @Override
    public void start(Stage stage) {
        stage.setTitle("Table View Sample");
        stage.setWidth(650);
        stage.setHeight(500);
 
        tableView = CreateTableView("Parent Table", "tableName", dataColumns, EventHandlerType.PARENT);

        final HBox hbox = new HBox();
        hbox.getChildren().addAll(tableView);
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
    public TableView<DataColumn> CreateTableView(String tableName, String propertyName, ObservableList<DataColumn> tableList,
    											EventHandlerType eventType) {
    	TableView<DataColumn> table = new TableView<>();
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
    Callback<TableColumn, TableCell> GetDataTableFactory(EventHandlerType eventType){
    	Callback<TableColumn, TableCell> dataTableFactory = new Callback<TableColumn, TableCell>(){
    		@Override
    		public TableCell call(TableColumn p) {
    			StringTableCell cell = new StringTableCell();
    			switch(eventType) {
    				default:
    					break;
    			}
    			return cell;
    		}
    	};
    	return dataTableFactory;
    }
    
    //Define the behavior of cells in table
    class StringTableCell extends TableCell<DataTable, String> {
    	 
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(empty ? null : getString());
            //setGraphic(null);
        }
 
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
    /*
    class parentTableFactoryEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent t) {
            TableCell cell = (TableCell) t.getSource();
            int index = cell.getIndex();
            SetTable(INNER, index);
            childTable.setItems(childTableList);
             DataTable temp = parentTable.getSelectionModel().getSelectedItem();

        }
    }
    */


}
