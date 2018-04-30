package core.comp3111;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class DataImport {
	
	private File selectedFile = null;
	private ArrayList<ArrayList<Object>> importMatrix = null;
	String[] columnHeaders = null;
	
	public DataImport() {
		
	}
	
	/**
	 * Presents the system file choosing dialog so the user can choose a file for eventual import
	 * 
	 * Defaults to the desktop
	 * 
	 * @return boolean notifying whether a file was chosen successfully
	 */
	public String getFileForImport() {
		String message = null;
		
		// Initialize the file chooser
		FileChooser fileChooser = new FileChooser();
		// We only are looking for CSV, but allow all (*) too
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("CSV Data Files", "*.csv"),
		         new ExtensionFilter("All Files", "*.*"));
		// Default to the user's desktop
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home"), "Desktop"));
		
		// Open the file chooser
		selectedFile = fileChooser.showOpenDialog(null);
		if (selectedFile != null) {
			message = "File selected: " + selectedFile.getAbsolutePath();
		}
		
		return message;
	}
	
	
	/**
	 * Converts the scratchpad into a properly formatted DataTable
	 * 
	 * @param columns
	 * 			HashMap with column names as key to a value pair of {data type, autofill type}
	 * @return
	 * 			A completed DataTable
	 */
	public DataTable buildDataTable(HashMap<String, String[]> columns, String nameOfTable) {
		DataTable table = null;
		
		// We finished reading in the file, now we can put the data into a DataTable
		// If this works we can return that the import succeeded
		try {
			if (CoreData.getInstance().doesTableExist(nameOfTable) == true) {
				CoreData.getInstance();
				table = new DataTable(nameOfTable + CoreData.getTransactID());
			} else {
				table = new DataTable(nameOfTable);
			}
			
			Object[] objArr = null;
			String[] colSettings = null;
			for (int colNum = 0; colNum < columnHeaders.length; colNum++) {
				objArr = importMatrix.get(colNum).toArray(new Object[importMatrix.get(colNum).size()]);
				colSettings = columns.get(columnHeaders[colNum]);
				String type = colSettings[Constants.DATATYPE_INDEX];
				
				DataColumn column = null;
				Number[] numCol = null;
				
				// Process this column's raw data, adding in blanks where needed
				switch (colSettings[Constants.AUTOFILLTYPE_INDEX]) {
				case AutoFillType.TYPE_EMPTY:
					// We're good, move on
					column = new DataColumn(type, objArr);
					break;
				case AutoFillType.TYPE_MEAN:
					// Find any blanks, then insert the average
					try {
						if (findEmpty(objArr)) {
							String f = calcMean(objArr);
							replaceEmpty(objArr,f);
						}
						numCol = castToNumber(objArr);
						column = new DataColumn(type, numCol);
					} catch (NumberFormatException e) {
						type = DataType.TYPE_STRING;
						column = new DataColumn(type, objArr);
					}
					break;
				case AutoFillType.TYPE_MEDIAN:
					// Find any blanks, then insert the mean
					try {
						if (findEmpty(objArr)) {
							String f = calcMedian(objArr);
							replaceEmpty(objArr,f);
						}
						numCol = castToNumber(objArr);
						column = new DataColumn(type, numCol);
					} catch (NumberFormatException e) {
						type = DataType.TYPE_STRING;
						column = new DataColumn(type, objArr);
					}
					break;
				case AutoFillType.TYPE_ZERO:
					// Find any blanks, then insert zeros
					try {
						if (findEmpty(objArr)) {
							replaceEmptyWithZero(objArr);
						}
						numCol = castToNumber(objArr);
						column = new DataColumn(type, numCol);
					} catch (NumberFormatException e) {
						type = DataType.TYPE_STRING;
						column = new DataColumn(type, objArr);
					}
					break;
				default:
					break;
				}						
				
				// Save to the DataTable
				table.addCol(columnHeaders[colNum], column);
			}
			
		} catch (DataTableException e) {
			e.printStackTrace();
		}
		
		return table;
	}
	
	/**
	 * Replaces empty string values in array with the passed string
	 * 
	 * @param vals 
	 * 			The array to increment over
	 * @param f
	 * 			The replacement for empties
	 */
	public void replaceEmpty(Object[] vals, String f) {
		for (int i = 0; i< vals.length; i++) {
			if (((String) vals[i]).equals("")) {
				vals[i] = (Object) f;
			}
		}
	}
	
	/**
	 * Searches for the presence of empty strings in the passed array
	 * 
	 * @param vals
	 * 			the array to increment over
	 * @return
	 * 			The presence of empty elements
	 */
	public boolean findEmpty(Object[] vals) {
		boolean found = false;
		
		for (int i = 0; i< vals.length && found == false; i++) {
			if (((String) vals[i]).equals("")) {
				found = true;
			}
		}
		
		return found;
	}
	
	/**
	 * 
	 * @param nums
	 * 			The array to increment over
	 * 
	 * @return Returns the mean as a string
	 * @throws NumberFormatException
	 */
	public String calcMean(Object[] nums) throws NumberFormatException {
		double f = 0f;
		int count = 0;
		for (int i = 0; i< nums.length; i++) {
			if (!((String) nums[i]).equals("")) {
				f += Double.parseDouble((String) nums[i]);
				count++;
			}
		}
		if (count == 0) {count++;}
		return String.format("%.2f", f/count);
	}
	
	/**
	 * 
	 * @param nums
	 * 			The array to increment over
	 * 
	 * @return Returns the median as a string
	 * @throws NumberFormatException
	 */
	public String calcMedian(Object[] nums) throws NumberFormatException {
		String result;
		
		// Extract all the numbers
		ArrayList<Double> arr = new ArrayList<Double>();
		
		for (int i = 0; i< nums.length; i++) {
			if (!((String) nums[i]).equals("")) {
				arr.add(Double.parseDouble((String) nums[i])); 
			}
		}
		arr.sort(null);
		
		int middle = arr.size()/2;
	    if (arr.size()%2 == 1) {
	        result = String.format("%.2f", arr.get(middle).doubleValue());
	    } else {
	    	double temp = arr.get(middle-1).doubleValue() + arr.get(middle).doubleValue();
	    	result = String.format("%.2f", temp/2.0);
	    }
		
		return result;
	}
	
	/**
	 * Creates a new array that conforms to Number
	 * @param obj
	 * 			Generic object array
	 * @return the Number array
	 */
	public Number[] castToNumber(Object[] obj) {
		Number[] arr = new Number[obj.length];
		
		for (int i = 0; i < obj.length; i++) {
			arr[i] = (Number) Double.parseDouble((String) obj[i]);
		}
		
		return arr;
	}
	
	/**
	 * Run through the array and replace empties with zero
	 * 
	 * @param vals
	 * 			The array to run over
	 * @throws NumberFormatException
	 */
	public void replaceEmptyWithZero(Object[] vals) throws NumberFormatException {
		//Run over the array and blow up if we can't turn into a Number
		for (int i = 0; i< vals.length; i++) {
			if (!((String) vals[i]).equals("")) {
				Double.parseDouble((String) vals[i]);
			}
		}
		
		// Find empties and replace with zero as a string
		for (int i = 0; i< vals.length; i++) {
			if (((String) vals[i]).equals("")) {
				vals[i] = (Object) new String("0");
			} 
		}
	}
	
	/**
	 * Attempts to extract the CSV formatted file contents contained in the file
	 * 
	 * @return boolean notifying whether the file was parsed successfully
	 */
	public String[] parseFile() {
		
		// Variable init
		BufferedReader reader = null;
        String line = "";
        String[] lineArray = null;

        // Try opening and reading the file previously linked
        try {
            reader = new BufferedReader(new FileReader(selectedFile.getAbsoluteFile()));
            
            // Double check there is content before proceeding
            if ((line = reader.readLine()) != null) {
            		
	    		// Get the headers
	    		columnHeaders = line.split(",", -1);
	    		
	    		// Build an arraylist of Strings arraylists to store the CSV with
	    		// This transposes the rows of file data into columns convenient for our application
	    		importMatrix = buildImportScratchpad(columnHeaders.length);
	    		
	    		// Extract the content line by line
	    		while ((line = reader.readLine()) != null) {
	    			
	    			// Use specified separator to parse into same number of columns as we had headers
	    			lineArray = line.split(",", columnHeaders.length);
	    			addRowToScratchpad(importMatrix,lineArray);
            	}	
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
        	e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return columnHeaders;
	}
	
	/**
	 * Creates a 2D ArrayList of Strings
	 * 
	 * @param size
	 * 			The number of columns the scratchpad needs to have
	 * @return the initiated scratchpad
	 */
	private ArrayList<ArrayList<Object>> buildImportScratchpad(int size) {
		ArrayList<ArrayList<Object>> outer = null;
		
		// Only initialize if there is a size
		// We should never call this method for size = 0, but we're being safe
		if (size > 0) {
			outer = new ArrayList<ArrayList<Object>>();
			ArrayList<Object> inner = null;
			int i = 0;
			
			// Continue adding new inner ArrayLists until we have the requisite matrix stub
			do {
				inner = new ArrayList<Object>();
				outer.add(inner);
				i++;
			} while (i < size);
		}
		
		return outer;
	}
	
	/**
	 * 
	 * @param matrix
	 * 			The scratchpad to build onto
	 * @param strArray
	 * 			The content that needs to be added to the scratchpad
	 */
	private void addRowToScratchpad(ArrayList<ArrayList<Object>> matrix, String[] strArray) {
		for (int i = 0; i < strArray.length; i++) {
			matrix.get(i).add(strArray[i]);
		}
	}
	
	/**
	 * Gets the file name and its path on the disk of the previously selected 
	 * 
	 * @return a string describing the path and file name, or null if no file selected
	 */
	public String getAbsolutePath() {
		return (selectedFile != null ) ? selectedFile.getAbsolutePath() : null;
	}
	
	public void setFile(String str) {
		if (str != null) {
			selectedFile = new File(str);
		}
	}
}