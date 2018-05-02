package core.comp3111;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import core.comp3111.DataTable;

/**
 * Data storage class for the application.  
 * 
 * It is a 2D matrix of DataTable objects, Hashtable of Charts and a singular instance of CoreData.
 * 
 * @author michaelfrost
 *
 */
public class CoreData implements Serializable {
	
	// Defines
	public static final long serialVersionUID = Constants.SERIALIZABLE_VER;
	private static long serialChartUID = 1;
	private static long transactID = 0;
	
	// Class variables
	private ArrayList<ArrayList<DataTable>> masterTableList;
	private HashMap<String, ArrayList<xychart>> masterChartList;
	private static CoreData instance;

	// Initializers
	
	/**
	 * Instantiates a CoreData object with new empty chart and DataTable lists
	 * 
	 */
	public CoreData() {
		masterTableList = new ArrayList<ArrayList<DataTable>>();
		masterChartList = new HashMap<String, ArrayList<xychart>>();
	}
	
	/**
	 * Used to fetch the singular instance of CoreData
	 * 
	 * @return the CoreData instance
	 */
	public static CoreData getInstance(){
        if(instance == null){
            instance = new CoreData();
        }
        return instance;
    }
	
	/**
	 * Set the singular instance to the passed CoreData object
	 * 
	 * @param data
	 * 			the core data object
	 */
	public static void setInstance(CoreData data) {
		instance = data;
	}
	
	/**
	 * Gets the number of table adds performed, this is to allow automatic creation of unique table names
	 * 
	 * @return the number (long)
	 */
	public static long getTransactID() {
		return transactID;
	}
	
	/**
	 * Add a new inner list of data tables and a parent data table
	 * 
	 * @param table
	 * 			The DataTable
	 * @return the index in which the table was added
	 */
	public int[] addParentTable(DataTable table) {
		int[] newParentIndex = {Constants.EMPTY, Constants.EMPTY};
		
		if (table != null)
		{
			ArrayList<DataTable> inner = new ArrayList<DataTable>();
			inner.add(table);
			masterTableList.add(inner);
			newParentIndex[Constants.OUTER] = masterTableList.size() - 1;
			newParentIndex[Constants.INNER] = 0;
		}
		transactID++;
		
		return newParentIndex;
	}
	
	/**
	 * Add a new child table to the specified outer list.
	 * Null tables are not added
	 * 
	 * @param table
	 *            The DataTable
	 * @param parentIndex
	 * 			Index of the parent datatable on outer Arraylist
	 * @return child table index
	 */
	public int[] addChildTable(DataTable table, int parentIndex) {
		int[] newChildIndex = {Constants.EMPTY,Constants.EMPTY};
		
		if (masterTableList.size() > parentIndex && table != null)
		{
			masterTableList.get(parentIndex).add(table);
			newChildIndex[Constants.INNER] = masterTableList.get(parentIndex).size() - 1;
			newChildIndex[Constants.OUTER] = parentIndex;
		}
		transactID++;
		
		return newChildIndex;
	}
	
	/**
	 * Fetch the ArrayList of DataTables by outer index 
	 * 
	 * @param outerIndex
	 * 			The index of the data table ArrayList in outer Arraylist
	 * @return the inner ArrayList
	 */
	public ArrayList<DataTable> getInnerList(int outerIndex) {
		ArrayList<DataTable> tableList = null;
		
		// Return the ArrayList of the index so long as the index can have contents
		if (masterTableList.size() > outerIndex) {
			tableList = masterTableList.get(outerIndex);
		}
		
		return tableList;
	}
	
	/**
	 * Fetch the DataTable at a certain [outer,inner] index
	 * 
	 * @param index 
	 * 				int[] that represents outer 2D ArrayList index to access 
	 * @return the DataTable
	 */
	public DataTable getDataTable(int[] index) {
		DataTable table = null;
		
		// Chain of if statements to protect against nulls
		if (masterTableList.size() > index[Constants.OUTER] && 
				masterTableList.get(index[Constants.OUTER]) != null &&
				masterTableList.get(index[Constants.OUTER]).size() > index[Constants.INNER] &&
				masterTableList.get(index[Constants.OUTER]).get(index[Constants.INNER]) != null) {
			
			table = masterTableList.get(index[Constants.OUTER]).get(index[Constants.INNER]);
		}
		
		return table;
	}
	
	/**
	 * Return the size of the inner ArrayList, meaning the number of related parent and children DataTables
	 * 
	 * @param innerIndex
	 * 			The array index on the outer ArrayList
	 * @return size of inner ArrayList
	 */
	public int getInnerSize(int innerIndex) {
		int size = Constants.EMPTY;
		
		// Make sure there is a ArrayList at the desired outer index
		if (innerIndex < masterTableList.size()) {
			size = masterTableList.get(innerIndex).size();
		}
		
		return size;
	}
	
	/**
	 * Returns the number of top level (parent) DataTables being stored
	 * 
	 * DataTables can be null and thus store no usable information
	 * 
	 * @return Number of parent tables
	 */
	public int getOuterSize() {
		return masterTableList.size();
	}
	
	/**
	 * Sets the DataTable referenced by index to the passed DataTable
	 * 
	 * @param index
	 * 			Outer and Inner index of the DataTable in question
	 * @param table
	 * 			The DataTable that will be stored
	 * @return true if the table could be set, false if it could not 
	 */
	public boolean setDataTable(int[] index, DataTable table) {
		boolean success = false;
		if (index[Constants.OUTER] >= 0 && 
				index[Constants.INNER] >= 0 && 
				masterTableList.size() > index[Constants.OUTER] &&
				masterTableList.get(index[Constants.OUTER]).size() > index[Constants.INNER] ) {
			
			masterTableList.get(index[Constants.OUTER]).set(index[Constants.INNER], table);
			success = true;
		}
		transactID++;
		return success;
	}
	
	/**
	 * Searches through the contained DataTables for a table of the supplied name, not case sensitive. 
	 * 
	 * @param name
	 * 			DataTable name that is being searched for
	 * @return [outer, inner] indices of the desired DataTable, [-1,-1] if not found
	 */
	public int[] searchForDataTable(String name) { 
		int[] indices = {Constants.EMPTY,Constants.EMPTY};
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
							masterTableList.get(outerIndex).get(innerIndex).getTableName().equalsIgnoreCase(name)) {
						
						indices[Constants.OUTER] = outerIndex;
						indices[Constants.INNER] = innerIndex;
						found = true;
					}
				}
			}
		}
		
		return indices;
	}
	
	
	/**
	 * Checks whether the file 'name' exists in the CoreData, not case sensitive
	 * 
	 * @param name
	 * 			TableName to search for
	 * @return boolean
	 * 			true if it exists, false if not
	 */
	public boolean doesTableExist(String name) {
		boolean exists = false;
		int[] indices = {Constants.EMPTY,Constants.EMPTY};
		
		indices = searchForDataTable(name);
		
		if (indices[Constants.INNER] == Constants.EMPTY && indices[Constants.OUTER] == Constants.EMPTY) {
			exists = false;
		} else {
			exists = true;
		}
			
		return exists;
	}
	
	
	/**
	 * Add the Chart to the masterChartList for a specific DataTable.
	 * 
	 * @param DataTableName
	 * 			- String, Corresponding DataTable Name
	 * @param xychart
	 * 			- the xychart object
	 * @return boolean
	 * 			- true if success, false otherwise
	 */
	public boolean addChart(String DataTableName, xychart xychart) {
		boolean success = false;
		if(this.masterChartList.containsKey(DataTableName)) {
			masterChartList.get(DataTableName).add(xychart);
			success = true;
		}
		else {
			ArrayList<xychart> arraylist = new ArrayList<xychart>();
			arraylist.add(xychart);
			masterChartList.put(DataTableName, arraylist);
			success = true;
		}
		return success;
	}
	
	
	/**
	 * get the ArrayList of xychart for the specific DataTable.
	 * 
	 * 
	 * @param DataTable
	 * 			- String, DataTable Name
	 * @return ArrayList<xychart> 
	 * 			- it is the ArrayList of the xychart(s) if there exists charts for DataTable. 
	 * 			- null otherwise.
	 */
	public ArrayList<xychart> getCharts(String DataTable){
		return masterChartList.get(DataTable);
	}
	
	
	/**
	 * Get the Number of Charts of the input DataTable with input ChartType.
	 * 
	 * @param DataTableName 
	 * 		- String, Name of the DataTable that the charts belong to
	 * @param ChartType 
	 * 		- String, Type of the Chart intends to get. Defined in ChartTypeValue.java.
	 * @return int
	 * 		- Number of Charts with inputed ChartType and inputed DataTable (with Name: DataTableName)
	 */
	public int getNumChartsWithType(String DataTableName, String ChartType){
		int num = 0;

		ArrayList<xychart> charts = masterChartList.get(DataTableName);
		if (charts == null) {return num;}
		for (xychart chart: charts) {
			if (chart.getChartType().equals(ChartType)) {
				num +=1; 
			}
		}
		return num;
		
		
	}
	/**
	 * Get the list of charts of the inputed chart type of a specific DataTable.
	 * 
	 * @param DataTableName
	 * 			- String, Name of the DataTable
	 * @param ChartType
	 * 			- String, Type of the chart. Define in ChartTypeValue.java.
	 * @return ArrayList<xychart>
	 * 			- an ArrayList of xychart(s) of specific ChartType of the specific DataTable if exists
	 *			- null if not exists
	 */	
	
	public ArrayList<xychart> getChartsWithType(String DataTableName, String ChartType){
		ArrayList<xychart> charts = masterChartList.get(DataTableName);
		if (charts == null) {
			return null;
		}
		ArrayList<xychart> chart_ChartType = new ArrayList<xychart>();
		for (xychart chart: charts) {
			if (chart.getChartType().equals(ChartType)) {
				 chart_ChartType.add(chart);
			}
		}
		if (chart_ChartType.size() == 0) {
			return null;
		}
		else {
		return chart_ChartType;
		}
		
	}
	
	/**
	 * get the chart with ChartID known and DataTable known 
	 * 
	 * @param DataTableName
	 * 			- String, The Name of the DataTable
	 * @param ChartID
	 * 			- String, ChartID of the chart intented to get
	 * @return xychart
	 * 			- the xychart with ChartID of the DataTable if existed. 
	 * 			- null otherwise.
	 */
	public xychart getChart(String DataTableName, String ChartID) {
		if (masterChartList.containsKey(DataTableName)) {
			ArrayList<xychart> Charts = masterChartList.get(DataTableName);
			for (int i = 0; i < Charts.size(); i++) {
				if (Charts.get(i).getChartID() == ChartID) {
					return Charts.get(i);
				}
			}

		}
		return null;
	}
	
	/**
	 * Get and update the chartid in CoreData class. 
	 * 
	 * @return long 
	 * 			- current chartid in the CoreData class
	 */
	public static long getchartid() {
		CoreData.serialChartUID +=1;	
		return CoreData.serialChartUID -1;
	}
	
	
	/**
	 * Check the chartid in the CoreData class. No updating. 
	 * 
	 * @return long
	 * 			- current chartid in the CoreData class.
	 */
	public static long checkchartid() {
		return CoreData.serialChartUID;
	}
	
	/**
	 * Deletes all data stored in CoreData, sets transaction count to zero
	 * 
	 */
	public void destroyData() {
		masterTableList = new ArrayList<ArrayList<DataTable>>();
		masterChartList = new HashMap<String, ArrayList<xychart>>();
		transactID = 0;
	}
	
	
}
