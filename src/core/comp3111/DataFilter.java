package core.comp3111;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;



/**
 * DataFilter class provides static methods for manipulating dataTable 
 * 
 * @author Henry Chan

 */
public class DataFilter {	
	
	/**
	 * TextFilter takes the target dataTable and retainValues which is a map of column name and text values
	 * for column to retain. The function will return a dataTable with rows containing columns to be retain.
	 * @param dataTable
	 *            - the dataTable for processing
	 * @param retainValues
	 * 			  - the HashMap contains column name and for each column name, the set of text to be retain 
	 * 
	 * @return newDataTable
	 * 			   - a dataTable filtered with given retainValues
	 */
	public static DataTable TextFilter(DataTable dataTable, HashMap<String, Set<String>> retainValues) {
		
		//TO-DO: bug of selecting only one entry of the unique text
		DataTable filteredTable = null;
		ArrayList<Integer> index = new ArrayList<Integer>();
		if (retainValues.keySet().size() != 0) for(int i=0;i<dataTable.getNumRow();i++) index.add(i);
		for(String columnName:retainValues.keySet()) {
			if(!dataTable.containsColumn(columnName)) continue;
			else {
				DataColumn currentColumn = dataTable.getCol(columnName);
				Set<String> retainValue = retainValues.get(columnName);
				Object[] currentData = currentColumn.getData();
				for(int i=0;i<currentData.length;i++) {
					if(!retainValue.contains(currentData[i])) {
						if(index.contains(i)) index.remove((Integer)i);
					}
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
	
	
	/**
	 * RandomSplitTable takes in a dataTable and randomly split it into two according to the splitRatio
	 * which the exact number of rows for one of the split dataTable is (lowerbound)splitRatio*numRows.
	 * 
	 * @param dataTable 
	 *            - the dataTable for processing
	 * @param splitRatio
	 * 			  - the splitRatio
	 * @return splitedTables
	 * 			  - a DataTable array of size 2 containing dataTables which are split with ratio specified in 
	 *              in splitRatio
	 */
	public static DataTable[] RandomSplitTable(DataTable dataTable, double splitRatio) {
		DataTable[] splitedTables = new DataTable[2];
		int numRows = dataTable.getNumRow();
		System.out.println("numRows: " + numRows);
		Integer[] randomIntegerArray = NonRepRandomIntegerGenerator(0,numRows);
		int cutOff = (int)(((double)numRows)*splitRatio);
		System.out.println("curOff: " + cutOff);

		ArrayList<Integer> indexA = new ArrayList<>();
		ArrayList<Integer> indexB = new ArrayList<>();
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
	public static Integer[] NonRepRandomIntegerGenerator(int start, int end) {
	    Integer[] randomIntegerArray = new Integer[end-start];
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
	 *            - the input dataTable
	 * @param index
	 * 			  - the index array with indexes of rows wanted to retain
	 * @return newDataTable
	 * 			   - table filtered with given index
	 */
	public static DataTable FilterTableByIndex(DataTable dataTable, ArrayList<Integer> index) throws DataTableException{
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
	 * GetTableTextLabels takes a dataTable and return a HashMap with key to be the column name
	 * and value to be a set of unique text for each column
	 * 
	 * @param dataTable
	 *            - the input dataTable
	 * @return textLabelTable
	 * 			   - HashMap with column name as key and sets of unique text for each column
	 */
	public static HashMap<String, Set<String>> GetTableTextLabels(DataTable dataTable) {
		HashMap<String, Set<String>> tableTextSet = new HashMap<>();
		String[] columnNames = dataTable.getColumnNames();
		for(String columnName:columnNames) {
			DataColumn dataColumn = dataTable.getCol(columnName);
			if(!dataColumn.getTypeName().equals(DataType.TYPE_STRING)) continue; //ignore non-String column
			Object[] columnTextObject = dataColumn.getData();
			String[] columnTextString = Arrays.copyOf(columnTextObject, columnTextObject.length, String[].class);
			Set<String> columnTextSet = new HashSet<String>(Arrays.asList(columnTextString));
			tableTextSet.put(columnName, columnTextSet);
		}
		return tableTextSet;
	}
}

