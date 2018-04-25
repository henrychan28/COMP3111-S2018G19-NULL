package ui.comp3111;

import java.awt.List;
import core.comp3111.CoreData;
import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataType;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class DataHostingUI extends Application {
		public static final int OUTER = 0;
		public static final int INNER = 1;
		private static ObservableList<DataTable> parentTableList = null;
		private static ObservableList<DataTable> childTableList = null;
		private static int childIndex = 0;
		private static TableView<DataTable> parentTable;
		private static TableView<DataTable> childTable;
		
		/**
		 * GetDataTable takes in the axis and outer index(if needed) and generate an ObservableList along the axis 
		 * (with that outer index if any). If the axis is OUTER, it will retrieve all parent DataTable and append
		 * them to the ObservableList. If the axis is INNER, it will retrieve all child DataTable and append them to the
		 * ObservableList.
		 * 
		 * @param axis 
		 *            - the axis to be scan along (INNER or OUTER)
		 * @param outer
		 * 			  - if scan along OUTER, put it to be -1
		 * 			  - if scan along INNER, provide the parentIndex
		 * @return dataSet
		 * 			  - a ObservableList containing the scanned item in order
		 */
	    public ObservableList<DataTable> GetDataTable(int axis, int parent) {
	    	//To-Do: Once the CoreData is completed, retrieve data from there
	    	CoreData coreData = getCoreData();
	    	ObservableList<DataTable> dataSet = FXCollections.observableArrayList();
	    	if (axis == OUTER && parent == -1) {
		    	int outerSize = coreData.getOuterSize();
		    	for(int outerIndex=0;outerIndex<outerSize;outerIndex++) {
		    		dataSet.add(coreData.getDataTable(new int[] {outerIndex, 0}));
		    	}
	    	}
	    	else if (axis==INNER) {
	    		int innerSize = coreData.getInnerSize(parent);
	    		for(int innerIndex=0;innerIndex<innerSize;innerIndex++) {
		    		dataSet.add(coreData.getDataTable(new int[] {parent, innerIndex}));
	    		}
	    	}
	    	return dataSet;
	    }
	    
	    private void SetDataTable(int axis, int outerIndex) {
	    	if(axis==OUTER) {
	    		parentTableList = GetDataTable(OUTER, -1);
	    	}
	    	else if(axis==INNER) {
	    		childTableList = GetDataTable(INNER, outerIndex);
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
	    	//Initialize the parent table
	    	SetDataTable(OUTER, -1);
	    	SetDataTable(INNER, childIndex);
	        stage.setTitle("Table View Sample");
	        stage.setWidth(650);
	        stage.setHeight(500);
	 
	        parentTable = CreateTableView("Parent Table", "tableName", parentTable, parentTableList);
	        childTable = CreateTableView("Child Table", "tableName", childTable, childTableList);

	        final HBox hbox = new HBox();
	        hbox.getChildren().addAll(parentTable, childTable);
	        Scene scene = new Scene(hbox);
	        stage.setScene(scene);
	        stage.show();
	    }
	    
	    public TableView<DataTable> CreateTableView(String tableName, String propertyName, TableView<DataTable> tableView, 
	    											ObservableList<DataTable> tableList) {
	    	TableView<DataTable> table = new TableView<>();
	        TableColumn Dataset = new TableColumn(tableName);
	        Dataset.setCellValueFactory(new PropertyValueFactory<>(propertyName));
	        Dataset.setCellFactory(getDataTableFactory());
	        table.setItems(tableList);
	        table.getColumns().addAll(Dataset);
	        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	    	return table;	    	
	    }
	    
	    Callback<TableColumn, TableCell> getDataTableFactory(){
	    	Callback<TableColumn, TableCell> dataTableFactory = new Callback<TableColumn, TableCell>(){
	    		@Override
	    		public TableCell call(TableColumn p) {
	    			StringTableCell cell = new StringTableCell();
	    			cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new dataTableFactoryEventHandler());
	    			return cell;
	    		}
	    	};
	    	return dataTableFactory;
	    }
	    
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
	    
	    class dataTableFactoryEventHandler implements EventHandler<MouseEvent> {
	        @Override
	        public void handle(MouseEvent t) {
	            TableCell cell = (TableCell) t.getSource();
	            int index = cell.getIndex();
	            SetDataTable(INNER, index);
	            childTable.setItems(childTableList);
	        }
	    }
	    

	} 
