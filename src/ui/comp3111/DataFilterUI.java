package ui.comp3111;

import java.util.HashMap;
import java.util.Set;

import core.comp3111.CoreData;
import core.comp3111.DataColumn;
import core.comp3111.DataFilter;
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

public class DataFilterUI extends Application {
	private static String[] currentText;
	private static ObservableList<DataColumn> columnList;
	private static TableView<DataColumn> columnTableView;
	private static TableView<String> textTableView;
	
	private enum EventHandlerType {
	    COLUMN, TEXT
	}
    
    private void InjectCurrentText(DataTable dataTable, String columnName) {
    	DataFilter filter = DataFilter.getFilter();
    	currentText = (String[]) filter.GetTableTextLabels(dataTable).get(columnName).toArray();
    }
    
    private void InjectColumnList(DataTable dataTable) {
    	for(DataColumn dataColumn: dataTable.getCol()) {
    		columnList.add(dataColumn);
    	}
    }
    
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage stage) {
        stage.setTitle("Table View Sample");
        stage.setWidth(650);
        stage.setHeight(500);
 

        final HBox hbox = new HBox();
        hbox.getChildren().addAll();
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
    public TableView<HashMap<String, Set<Object>>> CreateTableView(String tableName, String propertyName, HashMap<String, Set<Object>> tableList,
    											EventHandlerType eventType) {
    	TableView<HashMap<String, Set<Object>>> table = new TableView<>();
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
    


}
