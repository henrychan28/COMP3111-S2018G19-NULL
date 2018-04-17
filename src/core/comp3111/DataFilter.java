package core.comp3111;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DataFilter {
	
	/**
	 * TextFilter function takes a data table as an input, and output a DataColumns array 
	 * 
	 * @param dataTable
	 *            - the input dataTable for processing
	 */
	public DataFilter() {
	}
	public DataTable TextFilter(DataTable dataTable, HashMap<String, Set<Object>> removeValues) {
		HashMap<String, Set<Object>> tableText = new HashMap();
		Object[] columnNames = dataTable.getColumnNames();
		for(Object columnName:columnNames) {
			String columnNameString = String.valueOf(columnName);
			DataColumn dataColumn = dataTable.getCol(columnNameString);
			if(dataColumn.getTypeName()!="String") continue; //ignore non-String column
			Set<Object> columnText = new HashSet<Object>(Arrays.asList(dataColumn.getData()));
			if(removeValues != null && removeValues.containsKey(columnText)) 
				columnText.removeAll(removeValues.get(columnNameString));
			tableText.put(columnNameString, columnText);
		}
		return null;
	}
	
	public DataTable FilterTableByIndex(DataTable dataTable, int[] index) throws DataTableException{
		DataTable newDataTable = new DataTable();
		String[] columnNames = dataTable.getColumnNames();
		for (String columnName:columnNames) {
			DataColumn originalDataColumn = dataTable.getCol(columnName);
			Object[] originalData = originalDataColumn.getData();
			String originalTypeName = originalDataColumn.getTypeName();
			Object[] newData = new Object[index.length];
			for(int i=0;i<index.length;i++) {
				newData[i]=originalData[index[i]];
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
		HashMap<String, Set<Object>> tableText = new HashMap();
		String[] columnNames = dataTable.getColumnNames();
		for(String columnName:columnNames) {
			//System.out.print(columnName);

			DataColumn dataColumn = dataTable.getCol(columnName);
			if(dataColumn.getTypeName()!="String") continue; //ignore non-String column
			Set<Object> columnText = new HashSet<Object>(Arrays.asList(dataColumn.getData()));
			tableText.put(columnName, columnText);
		}
		return tableText;
	}
}

