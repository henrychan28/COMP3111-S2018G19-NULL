package core.comp3111;

import java.util.Arrays;

public class DataFilter {
	
	/**
	 * TextFilter function takes a data table as an input, and output a DataColumns array 
	 * 
	 * @param dataTable
	 *            - the input dataTable for processing
	 */
	public DataColumn[] TextFilter(DataTable dataTable, String[] columnNames) {
		//To-Do
		DataColumn[] dataColumns = new DataColumn[columnNames.length];
		for(String columnName: columnNames) {
			
		}
		return null;
	}
	public String[] GetColumnTextLabels(DataColumn dataColumn) {
		String type = dataColumn.getTypeName();
		if(type!="String") return null;
		Object[] columnData = dataColumn.getData();
		String[] columnText = Arrays.copyOf(columnData, columnData.length, String[].class);
		return columnText;
	}
	
	public String[] GetTableTextLabels(DataTable dataTable) {
		String[] columnNames = Arrays.copyOf(dataTable.getColumnNames(), dataTable.getColumnNames().length, String[].class);
		for(String columnName: columnNames) {
			dataTable.getCol(columnName);
		}
		return null;
	}
}

