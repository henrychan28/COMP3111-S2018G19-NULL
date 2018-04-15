package core.comp3111;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class DataImport {
	
	private File selectedFile = null;
	private ArrayList<ArrayList<String>> importMatrix = null;
	private DataTable table = null;
	String[] columnHeaders = null;
	String[] columnDataType = null;
	
	public DataImport() {
		
	}
	
	/**
	 * Presents the system file choosing dialog so the user can choose a file for eventual import
	 * 
	 * Defaults to the desktop
	 * 
	 * @return boolean notifying whether a file was chosen successfully
	 */
	public boolean showSingleFileChooser() {
		boolean success = false;
		
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
			System.out.println("File selected: " + selectedFile.getAbsolutePath());
			success = true;
		}
		else {
			System.out.println("File selection cancelled.");
		}
		
		return success;
	}
	
	/**
	 * Attempts to extract the CSV formatted file contents contained in the file
	 * 
	 * @return boolean notifying whether the file was parsed successfully
	 */
	public boolean parseFileToDataTable() {
		// Variable init
		boolean success = false;
		BufferedReader reader = null;
        String line = "";
        int columnCount = -1;
        String[] lineArray = null;

        // Try opening and reading the file previously linked
        try {
            reader = new BufferedReader(new FileReader(selectedFile.getAbsoluteFile()));
            
            // Double check there is content before proceeding
            if ((line = reader.readLine()) != null) {
            		
            		// Get the headers
            		columnHeaders = line.split(",", -1);
            		columnCount = columnHeaders.length;
            		
            		// Prompt the user for the type of data for each column
            		// HERE
            		
            		// Build an arraylist of Strings arraylists to store the CSV with
            		// This transposes the rows of file data into columns convenient for our application
            		importMatrix = buildImportScratchpad(columnCount);
            		
            		// Extract the content line by line
            		while ((line = reader.readLine()) != null) {
            			
            			// Use specified separator to parse into same number of columns as we had headers
            			lineArray = line.split(",", columnCount);
            			addRowToScratchpad(importMatrix,lineArray);
            		}
            		
            		// We finished reading in the file, now we can put the data into a DataTable
            		// If this works we can return that the import succeeded
            		success = createDataTable(selectedFile.getName());
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
		
		return success;
	}
	
	/**
	 * Creates a 2D ArrayList of Strings
	 * 
	 * @param size
	 * 			The number of columns the scratchpad needs to have
	 * @return the initiated scratchpad
	 */
	private ArrayList<ArrayList<String>> buildImportScratchpad(int size) {
		ArrayList<ArrayList<String>> outer = null;
		
		// Only initialize if there is a size
		// We should never call this method for size = 0, but we're being safe
		if (size > 0) {
			outer = new ArrayList<ArrayList<String>>();
			ArrayList<String> inner = null;
			int i = 0;
			
			// Continue adding new inner ArrayLists until we have the requisite matrix stub
			do {
				inner = new ArrayList<String>();
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
	private void addRowToScratchpad(ArrayList<ArrayList<String>> matrix, String[] strArray) {
		for (int i = 0; i < strArray.length; i++) {
			matrix.get(i).add(strArray[i]);
		}
	}
	
	/**
	 * Converts the scratchpad into a properly formatted DataTable
	 * 
	 * @param name
	 * 			The name to initialize the DataTable with
	 * @return boolean 
	 * 				Notifies whether the DataTable was created successfully
	 */
	private boolean createDataTable(String name) {
		boolean success = false;
		
		if (importMatrix != null) {
			table = new DataTable(name);
			
			for (int colNum = 0; colNum < columnHeaders.length; colNum++) {
				String[] strArr = (String[]) importMatrix.get(colNum).toArray();
				DataColumn column = new DataColumn(columnDataType[colNum], strArr);
			}
			
			success = true;
		}
		
		return success;
	}
	
	/**
	 * Gets the file name and its path on the disk of the previously selected 
	 * 
	 * @return a string describing the path and file name, or null if no file selected
	 */
	public String getAbsolutePath() {
		return (selectedFile != null ) ? selectedFile.getAbsolutePath() : null;
	}
}