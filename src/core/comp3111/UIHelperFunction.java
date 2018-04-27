package core.comp3111;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UIHelperFunction {
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
	    public static ObservableList<DataTable> InjectDataTable(int axis, int parent) {
	    	//To-Do: Once the CoreData is completed, retrieve data from there
	    	CoreData coreData = getCoreData();
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
	    
	    //Temporary function for getting dummy CoreData for demonstration purpose
	    public static CoreData getCoreData() {
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
}
