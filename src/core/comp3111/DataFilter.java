package core.comp3111;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DataFilter {
	
	private DataFilter() {}
	private static DataFilter filter = new DataFilter();
	public static DataFilter getFilter() {return filter;}
	
	
	/**
	 * TextFilter takes the target dataTable and retainValues which contains columns with strings willing to retain
	 * the function will return a dataTable with rows contain retainValues
	 * @param dataTable
	 *            - the input dataTable for processing
	 * @param index
	 * 			  - a hashmap contains 
	 * @return newDataTable
	 * 			   - table filtered with given retainValues hashmap
	 */
	public DataTable TextFilter(DataTable dataTable, HashMap<String, Set<Object>> retainValues) {
		DataTable filteredTable = null;
		ArrayList<Integer> index = new ArrayList<Integer>();
		for(int i=0;i<dataTable.getNumRow();i++) index.add(i);
		for(String columnName:retainValues.keySet()) {
			if(!dataTable.containsColumn(columnName)) continue;
			DataColumn currentColumn = dataTable.getCol(columnName);
			Set<Object> retainValue = retainValues.get(columnName);
			Object[] currentData = currentColumn.getData();
			for(int i=0;i<currentData.length;i++) {
				if(!retainValue.contains(currentData[i])) {
					if(index.contains(i)) index.remove((Integer)i);
				}
			}
		}
		try{
			filteredTable = FilterTableByIndex(dataTable, index);
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
		return filteredTable;
	}
	
	
	
	public DataTable[] RandomSplitTable(DataTable dataTable, double splitRatio) {
		DataTable[] splitedTables = new DataTable[2];
		int numRows = dataTable.getNumRow();
		Integer[] randomIntegerArray = NonRepRandomIntegerGenerator(0,numRows);
		int cutOff = (int)(((double)numRows)*splitRatio);
		ArrayList<Integer> indexA = new ArrayList();
		ArrayList<Integer> indexB = new ArrayList();
		for(int i=0;i<cutOff;i++) indexA.add(randomIntegerArray[i]);
		for(int i=cutOff;i<numRows;i++) indexB.add(randomIntegerArray[i]);
		try {
			splitedTables[0] = FilterTableByIndex(dataTable, indexA);
			splitedTables[1] = FilterTableByIndex(dataTable, indexB);
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
		return splitedTables;
	}
	
	/**
	 * NonRepRandomIntegerGenerator get the start and end(exclusive) and generate an randomly shuffled array of integer in the 
	 * specified range
	 * 
	 * @param start
	 *            - start integer (inclusive)
	 * @param end
	 * 			  - end integer (exclusive)
	 * @return randomIntegerArray
	 * 			   - randomly shuffled integer array with specified range
	 */
	public Integer[] NonRepRandomIntegerGenerator(int start, int end) {
	    Integer[] randomIntegerArray = new Integer[start-end];
	    for (int i = start; i < end; i++) {
	    	randomIntegerArray[i] = i;
	    }
	    Collections.shuffle(Arrays.asList(randomIntegerArray));
		return randomIntegerArray;
	}
	/**
	 * FilterTableByIndex takes a dataTable and desired index to retain and return a dataTable with selected index
	 * 
	 * @param dataTable
	 *            - the input dataTable for processing
	 * @param index
	 * 			  - the index array with index wanted to retain
	 * @return newDataTable
	 * 			   - table filtered with given index
	 */
	public DataTable FilterTableByIndex(DataTable dataTable, ArrayList<Integer> index) throws DataTableException{
		DataTable newDataTable = new DataTable();
		String[] columnNames = dataTable.getColumnNames();
		for (String columnName:columnNames) {
			DataColumn originalDataColumn = dataTable.getCol(columnName);
			Object[] originalData = originalDataColumn.getData();
			String originalTypeName = originalDataColumn.getTypeName();
			Object[] newData = new Object[index.size()];
			for(int i=0;i<index.size();i++) {
				newData[i]=originalData[index.get(i)];
			}
			newDataTable.addCol(columnName, new DataColumn(originalTypeName,newData));
			
		}
		return newDataTable;
	}
	/**
	 * GetTableTextLabels takes a dataTable and return DataColumns with data type "String"
	 * 
	 * @param dataTable
	 *            - the input dataTable for processing
	 * @return textLabelTable
	 * 			   - Columns with 
	 */
	public HashMap<String,Set<Object>> GetTableTextLabels(DataTable dataTable) {
		HashMap<String, Set<Object>> tableTextSet = new HashMap();
		String[] columnNames = dataTable.getColumnNames();
		for(String columnName:columnNames) {
			DataColumn dataColumn = dataTable.getCol(columnName);
			if(dataColumn.getTypeName()!="String") continue; //ignore non-String column
			Set<Object> columnTextSet = new HashSet<Object>(Arrays.asList(dataColumn.getData()));
			tableTextSet.put(columnName, columnTextSet);
		}
		return tableTextSet;
	}
}

