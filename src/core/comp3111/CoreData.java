package core.comp3111;

import java.util.ArrayList;
import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataType;

public class CoreData {
	
	// Class variables
	private ArrayList<ArrayList<DataTable>> tableList;
	
	// Initializer
	public CoreData() {
		tableList = new ArrayList<ArrayList<DataTable>>();
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
			tableList.add(inner);
			outerIndex = tableList.size() - 1;
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
		
		if (tableList.size() >= parentIndex && table != null)
		{
			tableList.get(parentIndex).add(table);
			childIndex = tableList.get(parentIndex).size() - 1;
		}
		
		return childIndex;
	}
}
