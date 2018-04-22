package ui.comp3111;

import java.util.HashMap;

import core.comp3111.DataType;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ColumnTypeUI {
	
	private static final int SIZE_H = 400;
	private static final int SIZE_W = 600;
	
	public HashMap<String, String[]> start(Stage parent) {
		
		
		/*
		 * Column list selection
		 * 
		 */
		
		String[] columnNames = {"Column One", "Column Two", "Column Three"};
		ObservableList<String> columnsList = FXCollections.observableArrayList(columnNames);
		ListView<String> list = new ListView<String>(columnsList);
		list.setPrefWidth(150);
		list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		Label columnSelectedLabel = new Label("No column selected");
		
		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				columnSelectedLabel.setText((String) newValue);
			}
		});
		
		
		HBox colNameHBox = new HBox(20);
		colNameHBox.setAlignment(Pos.CENTER);
		colNameHBox.setPrefWidth(SIZE_W);
		colNameHBox.getChildren().addAll(list, columnSelectedLabel);
		
		
		/*
		 * Method to fill in blanks
		 * 
		*/
		String[] stringFill = {"Blank"};
		String[] numberFill = {"Zero", "Mean", "Average"};
		
		Label fillLabel = new Label("No fill method selected"); 
		
		ChoiceBox<String> fillChoiceBox = new ChoiceBox<String>();
		fillChoiceBox.setItems(FXCollections.observableArrayList(numberFill));
		fillChoiceBox.disableProperty().set(true);
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
		 * Data Type selection
		 * 
		*/
		String[] types = {DataType.TYPE_NUMBER, DataType.TYPE_OBJECT, DataType.TYPE_STRING};
		String[] typeLabels = {"Number", "Object", "String"};
		
		Label typeLabel = new Label("No type selected");

		ChoiceBox<String> typeChoiceBox = new ChoiceBox<String>();
		typeChoiceBox.setItems(FXCollections.observableArrayList(typeLabels));
		typeChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				typeLabel.setText(types[newValue.intValue()]);
				
				switch (newValue.intValue()) {
	            case 0:
	            	fillChoiceBox.disableProperty().set(false);
	                break;
	            default: 	
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
		 * UI Buttons to cancel or confirm
		 * 
		 */
		
		
		
		/*
		 * Inner container
		 * 
		 */
		
		VBox container = new VBox(20);
		container.setAlignment(Pos.CENTER);
		container.getChildren().addAll(colNameHBox, typeHBox, fillHBox);
		
		
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
		return null;
	}

}