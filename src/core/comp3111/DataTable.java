package core.comp3111;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 2D array of data values with the following requirements: (1) There are 0 to
 * many columns (2) The number of row for each column is the same (3) 2 columns
 * may have different type (e.g. String and Number). (4) A column can be
 * uniquely identified by its column name (5) add/remove a column is supported
 * (6) Suitable exception handling is implemented
 * 
 * @author cspeter
 *
 */
public class DataTable implements Serializable{

	private static final long serialVersionUID = Constants.SERIALIZABLE_VER;
	
	/**
	 * Construct - Create an empty DataTable
	 */
	public DataTable() {

		// In this application, we use HashMap data structure defined in
		// java.util.HashMap
		dc = new HashMap<String, DataColumn>();
		tableName = "";
	}
	
	/**
	 * Construct - Create an empty DataTable
	 * 
	 * @param name
	 * 			The name of the data table
	 */
	public DataTable(String name) {
		this();
		tableName = name;
	}
	
	/**
	 * Add a data column to the table.
	 * 
	 * @param colName
	 *            - name of the column. It should be a unique identifier
	 * @param newCol
	 *            - the data column
	 * @throws DataTableException
	 *             - It throws DataTableException if a column is already exist, or
	 *             the row size does not match.
	 */
	public void addCol(String colName, DataColumn newCol) throws DataTableException {
		if (containsColumn(colName)) {
			throw new DataTableException("addCol: The column already exists");
		}

		int curNumCol = getNumCol();
		if (curNumCol == 0) {
			dc.put(colName, newCol); // add the column
		} else {
			// If there is more than one column,
			// we need to ensure that all columns having the same size

			int curNumRow = getNumRow();
			if (newCol.getSize() != curNumRow) {
				throw new DataTableException(String.format(
						"addCol: The row size does not match: newCol(%d) and curNumRow(%d)", newCol.getSize(), curNumRow));
			}

			dc.put(colName, newCol); // add the mapping
		}
	}

	
	/**
	 * Remove a column from the data table
	 * 
	 * @param colName
	 * 			The column name. It should be a unique identifier
	 * @throws DataTableException
	 * 			It throws DataTableException if the column does not exist
	 */
	public void removeCol(String colName) throws DataTableException {
		if (containsColumn(colName)) {
			dc.remove(colName);
		} else {
			throw new DataTableException("removeCol: The column does not exist");
		}
	}

	/**
	 * Get the DataColumn object based on the give colName. Return null if the
	 * column does not exist
	 * 
	 * @param colName
	 *            The column name
	 * @return DataColumn reference or null
	 */
	public DataColumn getCol(String colName) {
		if (containsColumn(colName)) {
			return dc.get(colName);
		}
		return null;
	}
	
	/**
	 * Returns all data columns
	 * @return DataColumn lists
	 */
	public DataColumn[] getCol() {
		DataColumn[] dataColumns = new DataColumn[dc.size()];
		int index = 0;
		for(String key:dc.keySet()) {
			dataColumns[index] = dc.get(key);
			index++;
		}
		return dataColumns;
	}
	
	/**
	 * Check whether the column exists by the given column name
	 * 
	 * @param colName
	 * 			String that represents the column name
	 * @return true if the column exists, false otherwise
	 */
	public boolean containsColumn(String colName) {
		return dc.containsKey(colName);
	}
	
	public void setName(String string) {
		tableName = string;
	}
	
	/**
	 * Return the number of column in the data table
	 * 
	 * @return the number of column in the data table
	 */
	public int getNumCol() {
		return dc.size();
	}
	
	/**
	 * Return the keys in the data table
	 * 
	 * @return the keys in the data table
	 */	
	public String[] getColumnNames() {
		Object[] columnNames = dc.keySet().toArray();
		String[] columnString = Arrays.copyOf(columnNames, columnNames.length, String[].class);
		return columnString;
	}
	
	/**
	 * Returns the name of the DataTable
	 * 
	 * @return String representing the DataTable name
	 */
	public String getTableName() {
		return this.tableName;
	}
	
	/**
	 * Return the number of row of the data table. This data structure ensures that
	 * all columns having the same number of row
	 * 
	 * @return the number of row of the data table
	 */
	public int getNumRow() {
		if (dc.size() <= 0)
			return dc.size();

		// Pick the first entry and get its size
		// assumption: For DataTable, all columns should have the same size
		Map.Entry<String, DataColumn> entry = dc.entrySet().iterator().next();
		return dc.get(entry.getKey()).getSize();
	}
	
	@Override
	public boolean equals(Object obj) {
		DataTable otherDataTable = (DataTable) obj;
		return dc.equals(otherDataTable.dc);
	}
	
	/**
	 * Get number of columns of integer type.
	 * @return - integer 
	 */
	public int getNumColOfInteger() {
		
		int Num = 0;
		
		//Iterate every element in dc
		for (Map.Entry<String, DataColumn> entry: dc.entrySet()) {
			DataColumn temdc = entry.getValue();
			
			if (temdc.isInteger()) {
				Num +=1;
			}	
		}
		
		return Num;
		
	}

  
  /**
	 * Return the number of columns with the input column type.
	 * 
	 * @param colType
	 * 			- Type of the column: Defined in DataType class
	 * @return 
	 * 			- integer
	 */	
	public int getNumColOfType(String colType) {
		int Num = 0;
		
		//Iterate every element in dc
		for (Map.Entry<String, DataColumn> entry: dc.entrySet()) {
			String type = entry.getValue().getTypeName();

			if (type.equals(colType) ) {
				Num +=1;
			}	
		}
		return Num;
	}
	
	/**
	 * Return the keys of the columns of the input column type
	 * 
	 * @param colType
	 * 			- Type of the column: Defined in DataType class
	 * @return
	 * 			- null if not exists
	 * 			- String[] keys
	 */
	public String[] getColKeysOfType(String colType) {
		int size = getNumColOfType(colType);
		if (size == 0) {
			return null;
		}
		String [] keys = new String[size];

		//iterate the HashMap dc
		int i = 0;
		for (Map.Entry<String, DataColumn> entry: dc.entrySet()) {
			String type = entry.getValue().getTypeName();
			if (type.equals(colType)) {

				keys[i] = entry.getKey();
				i++;

			}	
		}
		
		return keys;
	}
	
	public void printDataTable() {
		System.out.println("---------printDataTable()---------");
		for(String key: dc.keySet()) {
			DataColumn currentColumn = dc.get(key);
			Object[] currentData = currentColumn.getData();
			System.out.println(key + " " + currentColumn.getTypeName());
			for(Object datum:currentData) {
				System.out.print(datum + " ");
			}
			System.out.println();
		}
		System.out.println("-------------------------------");
	}
	// attribute: A java.util.Map interface
	// KeyType: String
	// ValueType: DataColumn
	private Map<String, DataColumn> dc;
	private String tableName;


}