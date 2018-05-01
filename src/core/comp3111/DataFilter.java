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
	
	private DataFilter() {
		System.out.println("In the DataFilter Constructor");
	}
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
	public DataTable TextFilter(DataTable dataTable, HashMap<String, Set<String>> retainValues) {
		
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
	 *  which the exact number of rows for one of the splitted dataTable is (lowerbound)splitRatio*numRows.
	 * 
	 * @param dataTable 
	 *            - the input dataTable for processing
	 * @param splitRatio
	 * 			  - the splitRatio
	 * @return splitedTables
	 * 			  - a DataTable array with splitted dataTable with the first element has the one specific by the splitRatio
	 */
	public DataTable[] RandomSplitTable(DataTable dataTable, double splitRatio) {
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
	public Integer[] NonRepRandomIntegerGenerator(int start, int end) {
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
	 * 			   - Unique text entries for each column in the dataTable
	 */
	public HashMap<String, Set<String>> GetTableTextLabels(DataTable dataTable) {
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

