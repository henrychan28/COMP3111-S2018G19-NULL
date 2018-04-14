package core.comp3111;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class DataImport {
	
	private File selectedFile = null;
	
	public DataImport() {
		
	}
	
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
	
	public boolean parseFile() {
		
		// Variable init
		BufferedReader reader = null;
        String line = "";
        int columnCount = -1;
        String[] columnHeaders = null;
        String[] lineArray = null;

        // Try opening and reading the file previously linked
        try {
            reader = new BufferedReader(new FileReader(selectedFile.getAbsoluteFile()));
            
            // Double check there is content before proceeding
            if ((line = reader.readLine()) != null) {
            		
            		// Get the headers
            		columnHeaders = line.split(",", -1);
            		columnCount = columnHeaders.length;
            		
            		// Build an arraylist of Strings arraylists to store the CSV with
            		// This transposes the rows of file data into columns convenient for our application
            		ArrayList<ArrayList<String>> importMatrix = buildImportScratchpad(columnCount);
            		
            		// Extract the content
            		while ((line = reader.readLine()) != null) {
            			
            			// Use specified separator to parse into same number of columns as we had headers
            			lineArray = line.split(",", columnCount);
            			addRowToScratchpad(importMatrix,lineArray);
            		}
            	
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

		
		return false;
	}
	
	private ArrayList<ArrayList<String>> buildImportScratchpad(int size) {
		ArrayList<ArrayList<String>> outer = null;
		
		// Only initialize if there is a size
		// We should never call this method for size = 0, but we're being safe
		if (size > 0) {
			outer = new ArrayList<ArrayList<String>>();
			ArrayList<String> inner = null;
			int i = 0;
			
			// Continue adding inner ArrayLists until we have the requisite matrix stub
			do {
				inner = new ArrayList<String>();
				outer.add(inner);
				i++;
			} while (i < size);
		}
		
		return outer;
	}
	
	private void addRowToScratchpad(ArrayList<ArrayList<String>> matrix, String[] strArray) {
		for (int i = 0; i < strArray.length; i++) {
			matrix.get(i).add(strArray[i]);
		}
	}
	
	public String getAbsolutePath() {
		return selectedFile.getAbsolutePath();
	}
}