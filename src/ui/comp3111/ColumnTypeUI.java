package ui.comp3111;

import java.util.HashMap;
import java.util.Set;

import core.comp3111.AutoFillType;
import core.comp3111.Constants;
import core.comp3111.DataType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Class that presents the column property selection menu
 * 
 * @author michaelfrost
 *
 */
public class ColumnTypeUI {
	
	private static final int WINDOW_H = 500;
	private static final int WINDOW_W = 650;
	
	private static final int LIST_W = 220;
	private static final int LIST_H = WINDOW_H - 120;
	private static final int BUTTONS_W = WINDOW_W - LIST_W - 120;
	private static final int BUTTONS_H = WINDOW_H - 120;
	
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
		Stage newWindow = new Stage();
		Button saveColumn = new Button("Save Column");
		Button closeDialog = new Button("Confirm Import");
		Button cancelDialog = new Button("Cancel Import");
		
		/*
		 * Column list selection
		 * 
		 * 
		 */
		
		// Pull column names from the preferences map
		Set<String> keys = columnPrefs.keySet();
		String[] columnNames = keys.toArray(new String[keys.size()]);
		
		// Add the column selections list
		ObservableList<String> columnsList = FXCollections.observableArrayList(columnNames);
		ListView<String> list = new ListView<String>(columnsList);
		list.setPrefWidth(LIST_W);
		list.setPrefHeight(LIST_H);
		
		list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		Label columnSelectedLabel = new Label("No column selected");
		
		/*
		 * Method to fill in blanks
		 * 
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
		
		
		
		//
		//
		// Methods	
		//
		//
		
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
				
				columnSelectedLabel.setText("Column: '" + (String) newValue + "' Selected");
			}
		});
		
		
		/*
		 * Type Selection
		 * 
		 */

		typeChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				typeLabel.setText("Java Type: " + types[newValue.intValue()]);

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
		
		
		/*
		 * Autofill Choices
		 * 
		 */
		
		fillChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				fillLabel.setText("Fill Algorithm: " + numberFill[newValue.intValue()]);
			}
		});
		
		/*
		 * UI Buttons to save column prefs
		 * 
		 */
		
		saveColumn.setOnAction(e -> {
			// Make sure this is even valid
			if (typeChoiceBox.getSelectionModel().getSelectedIndex() == 0 &&
					fillChoiceBox.getSelectionModel().getSelectedItem().equals(AutoFillType.TYPE_EMPTY)) {
				fillChoiceBox.getSelectionModel().select(numberFill[1]);
				fillLabel.setText("A Number column must be filled.\n Replacing blank with zero.");
				fillLabel.setTextAlignment(TextAlignment.CENTER);;
			}
			
			// Save it
			String column = list.getSelectionModel().getSelectedItem();
			String[] vals = {types[typeChoiceBox.getSelectionModel().getSelectedIndex()], fillChoiceBox.getSelectionModel().getSelectedItem()};
			columnPrefs.replace(column, vals);
		});
		
		/*
		 * UI Buttons to confirm and cancel
		 * 
		 */
		
		closeDialog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	System.out.print("Confirm...");
            	newWindow.close();
            }
        });
		
		cancelDialog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	columnPrefs = null;
            	System.out.print("Cancel...");
            	newWindow.close();
            }
        });
		
		// a spacer to push the visible elements up a little
		Region spacer = new Region();
		spacer.setPrefHeight(10);
		
		
		/*
		 * Column selection container
		 * 
		 */
		
		VBox colNameVBox = new VBox(20);
		colNameVBox.setAlignment(Pos.CENTER);
		colNameVBox.setPrefWidth(LIST_W);
		colNameVBox.getChildren().addAll(columnSelectedLabel, list);
				
		
		/*
		 * Buttons and Choicebox container
		 * 
		 */
		
		VBox buttonsVBox = new VBox(20);
		buttonsVBox.setAlignment(Pos.CENTER);
		buttonsVBox.setPrefWidth(BUTTONS_W);
		buttonsVBox.setPrefHeight(BUTTONS_H);
		buttonsVBox.getChildren().addAll(spacer, typeChoiceBox, typeLabel, fillChoiceBox, fillLabel, saveColumn, new Separator(), closeDialog, new Separator(), cancelDialog);
		
		/*
		 * Outer Container
		 * 
		 */
		
		HBox container = new HBox(20);
		container.setAlignment(Pos.CENTER);
		container.getChildren().addAll(colNameVBox, buttonsVBox);
		
		/*
		 * Show the window we generated
		 * 
		 */
		
		Scene scene = new Scene(container,WINDOW_W,WINDOW_H);
		
		newWindow.setTitle("Column Type Selector");
		newWindow.setWidth(WINDOW_W);
		newWindow.setHeight(WINDOW_H);
		newWindow.setScene(scene);
		
		newWindow.initOwner(parent);
		newWindow.initModality(Modality.APPLICATION_MODAL); 
		newWindow.showAndWait();

		System.out.println("Done");
		return columnPrefs;
	}

}