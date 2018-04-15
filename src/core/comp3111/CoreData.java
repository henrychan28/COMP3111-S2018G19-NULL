package core.comp3111;

import java.util.ArrayList;
import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataType;

public class CoreData {
	
	// Class variables
	private ArrayList<ArrayList<DataTable>> masterTableList;
	
	// Initializer
	public CoreData() {
		masterTableList = new ArrayList<ArrayList<DataTable>>();
	}
	
	/**
	 * Add a new inner list of data tables and a parent data table
	 * 
	 * @param table
	 * 			The DataTable
	 * @return the index on the outer list in which the table was added
	 */
	public int addParentTable(DataTable table) {
		int outerIndex = -1;
		
		if (table != null)
		{
			ArrayList<DataTable> inner = new ArrayList<DataTable>();
			inner.add(table);
			masterTableList.add(inner);
			outerIndex = masterTableList.size() - 1;
		}
		
		return outerIndex;
	}
	
	/**
	 * Add a new child table to the specified outer list
	 * 
	 * @param table
	 *            The DataTable
	 * @return child table index on the inner list
	 */
	public int addChildTable(DataTable table, int parentIndex) {
		int childIndex = -1;
		
		if (masterTableList.size() > parentIndex && table != null)
		{
			masterTableList.get(parentIndex).add(table);
			childIndex = masterTableList.get(parentIndex).size() - 1;
		}
		
		return childIndex;
	}
	
	/**
	 * Fetch the ArrayList of DataTables by outer index 
	 * 
	 * @param index
	 * 			The index of the data table ArrayList in outer Arraylist
	 * @return the inner ArrayList
	 */
	public ArrayList<DataTable> getList(int index) {
		ArrayList<DataTable> tableList = null;
		
		// Return the ArrayList of the index so long as the index can have contents
		if (masterTableList.size() > index) {
			tableList = masterTableList.get(index);
		}
		
		return tableList;
	}
	
	/**
	 * Fetch the DataTable by inner and outer index
	 * 
	 * @param outerIndex 
	 * 				int that represents outer ArrayList index to access 
	 * @param innerIndex 
	 * 				int that represents inner ArrayList index to access
	 * @return the DataTable
	 */
	public DataTable getDataTable(int outerIndex, int innerIndex) {
		DataTable table = null;
		
		// Chain of if statements to protect against nulls
		if (masterTableList.size() > outerIndex && 
				masterTableList.get(outerIndex) != null &&
				masterTableList.get(outerIndex).size() > innerIndex &&
				masterTableList.get(outerIndex).get(innerIndex) != null) {
			
			table = masterTableList.get(outerIndex).get(innerIndex);
		}
		
		return table;
	}
	
	/**
	 * Searches through the contained DataTables for a table of the supplied name. 
	 * 
	 * @param name
	 * 			DataTable name that is being searched for
	 * @return [outer, inner] indices of the desired DataTable, [-1,-1] if not found
	 */
	public int[] searchForDataTable(String name) { 
		int[] indices = {-1,-1};
		boolean found = false;
		
		// Iterate through the ArrayList searching for the desired DataTable
		// Set bounds to outer ArrayList
		for (int outerIndex = 0; outerIndex < masterTableList.size() && !found; outerIndex++) {
			// Look deeper if there is content here
			if (masterTableList.get(outerIndex) != null) {
				// We now need to look at parent and children DataTables
				for (int innerIndex = 0; innerIndex < masterTableList.get(outerIndex).size() && !found; innerIndex++) {
					// Look deeper if there is content and finally evaluate comparison
					if (masterTableList.get(outerIndex).get(innerIndex) != null && 
							masterTableList.get(outerIndex).get(innerIndex).getName().equalsIgnoreCase(name)) {
						
						indices[0] = outerIndex;
						indices[1] = innerIndex;
						found = true;
					}
				}
			}
		}
		
		return indices;
	}
}
