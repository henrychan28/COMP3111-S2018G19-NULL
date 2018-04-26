package ui.comp3111;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import core.comp3111.AutoFillType;
import core.comp3111.Constants;
import core.comp3111.DataType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ColumnTypeUI {
	
	private static final int SIZE_H = 400;
	private static final int SIZE_W = 600;
	
	private HashMap<String, String[]> columnPrefs;
	
	public ColumnTypeUI(String[] columns) {
		columnPrefs = new HashMap<String, String[]>();
		String[] defaultVals = {DataType.TYPE_STRING, AutoFillType.TYPE_EMPTY};
		
		for (int i = 0; i < columns.length; i++)
		{
			columnPrefs.put(columns[i], defaultVals);
		}
	}
	
	public HashMap<String, String[]> presentUI(Stage parent) {
		
		// Defines
		Button saveColumn = new Button("Save Column");
		
		/*
		 * Column list selection
		 * 
		 */
		
		// Pull column names from the preferences map
		Set<String> keys = columnPrefs.keySet();
		String[] columnNames = keys.toArray(new String[keys.size()]);
		
		// Add the column selections list
		ObservableList<String> columnsList = FXCollections.observableArrayList(columnNames);
		ListView<String> list = new ListView<String>(columnsList);
		list.setPrefWidth(150);
		list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		Label columnSelectedLabel = new Label("No column selected");
		
		/*
		 * Method to fill in blanks
		 * 
		*/
		
		String[] numberFill = {AutoFillType.TYPE_EMPTY, AutoFillType.TYPE_ZERO, AutoFillType.TYPE_MEDIAN, AutoFillType.TYPE_MEAN};
		
		Label fillLabel = new Label("No fill method selected"); 
		
		ChoiceBox<String> fillChoiceBox = new ChoiceBox<String>();
		fillChoiceBox.setItems(FXCollections.observableArrayList(numberFill));
		fillChoiceBox.disableProperty().set(true);
		
		/*
		 * Data Type selection
		 * 
		*/
		String[] types = {DataType.TYPE_NUMBER, DataType.TYPE_OBJECT, DataType.TYPE_STRING};
		String[] typeLabels = {"Number", "Object", "String"};
		
		Label typeLabel = new Label("No type selected");

		ChoiceBox<String> typeChoiceBox = new ChoiceBox<String>();
		typeChoiceBox.setItems(FXCollections.observableArrayList(typeLabels));
		
		
		// Methods		
		
		/*
		 * LIST
		 * 
		 */
		
		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
								
				String[] vals = columnPrefs.get(newValue.toString());
				
				int i = 0;
				for (i = 0; i < types.length; i++) {
					if (types[i].equals(vals[Constants.DATATYPE_INDEX])) {
						System.out.println(i);
						typeChoiceBox.getSelectionModel().select(i);
					}
				}
				
				for (i = 0; i < numberFill.length; i++) {
					if (numberFill[i].equals(vals[Constants.AUTOFILLTYPE_INDEX])) {
						System.out.println(i);
						fillChoiceBox.getSelectionModel().select(i);
					}
				}			
				
				columnSelectedLabel.setText((String) newValue);
			}
		});
		
		HBox colNameHBox = new HBox(20);
		colNameHBox.setAlignment(Pos.CENTER);
		colNameHBox.setPrefWidth(SIZE_W);
		colNameHBox.getChildren().addAll(list, columnSelectedLabel);
		
		
		/*
		 * Type Selection
		 * 
		 */

		typeChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				typeLabel.setText(types[newValue.intValue()]);

				switch (newValue.intValue()) {
	            case 0: 	// Is Number
	            	fillChoiceBox.disableProperty().set(false);
	            	fillChoiceBox.getSelectionModel().select(numberFill[1]);
	                break;
	            default: 	// Not Number
	            	fillChoiceBox.getSelectionModel().select(numberFill[0]);
	            	fillChoiceBox.disableProperty().set(true);
	                break;
				}
			}
		});
		
		HBox typeHBox = new HBox(20);
		typeHBox.setAlignment(Pos.CENTER);
		typeHBox.setPrefWidth(SIZE_W);
		typeHBox.getChildren().addAll(typeChoiceBox, typeLabel);
		
		
		/*
		 * Autofill Choices
		 * 
		 */
		
		fillChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				fillLabel.setText(numberFill[newValue.intValue()]);
			}
		});
		
		HBox fillHBox = new HBox(20);
		fillHBox.setAlignment(Pos.CENTER);
		fillHBox.setPrefWidth(SIZE_W);
		fillHBox.getChildren().addAll(fillChoiceBox, fillLabel);
		
		
		/*
		 * UI Buttons to save column prefs
		 * 
		 */
		
		saveColumn.setOnAction(e -> {
			// Make sure this is even valid
			if (typeChoiceBox.getSelectionModel().getSelectedIndex() == 0 &&
					fillChoiceBox.getSelectionModel().getSelectedItem().equals(AutoFillType.TYPE_EMPTY)) {
				fillChoiceBox.getSelectionModel().select(numberFill[1]);
				fillLabel.setText("Cannot have blank chosen, set to zero");
			}
			
			// Save it
			String column = list.getSelectionModel().getSelectedItem();
			String[] vals = {types[typeChoiceBox.getSelectionModel().getSelectedIndex()], fillChoiceBox.getSelectionModel().getSelectedItem()};
			columnPrefs.replace(column, vals);
			System.out.println(Arrays.toString(columnPrefs.get(column)));
		});
		
		HBox saveHBox = new HBox(20);
		saveHBox.setAlignment(Pos.CENTER);
		saveHBox.setPrefWidth(SIZE_W);
		saveHBox.getChildren().addAll(saveColumn);
		
		
		
		/*
		 * UI Buttons to cancel or confirm
		 * 
		 */
		
		
		
		/*
		 * Inner container
		 * 
		 */
		
		VBox container = new VBox(20);
		container.setAlignment(Pos.CENTER);
		container.getChildren().addAll(colNameHBox, typeHBox, fillHBox, saveHBox);
		
		
		/*
		 * 
		 * 
		 */
		
		Scene scene = new Scene(container,SIZE_W,SIZE_H);
		
		Stage newWindow = new Stage();
		newWindow.setTitle("Column Type Selector");
		newWindow.setWidth(SIZE_W);
		newWindow.setHeight(SIZE_H);
		newWindow.setScene(scene);
		
		newWindow.initOwner(parent);
		newWindow.initModality(Modality.APPLICATION_MODAL); 
		newWindow.showAndWait();
		

		System.out.println("SHIT");
		return columnPrefs;
	}

}