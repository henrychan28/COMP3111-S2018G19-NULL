package ui.comp3111;

import core.comp3111.*;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.Group;


import javafx.stage.Stage;
/**
 * UI for generating chart
 * 
 * @author YuenTing
 *
 */
//extends the DataTable: 
//getNumberOfDataColumn(Type)
//getDataColumnsOf(Type)

public class GenerateChartUI extends Application {

		
	@Override
	public void start(Stage stage) {
		
		//Create Labels, ComboBoxes, buttons
		Label SelectTypeL = new Label("Select the Chart Type: ");
		ComboBox<String> ChartTypeCB = new ComboBox<String>();
		//TODO
		//if DataTable contains >= 2 Number Data Columns => show LineChart, Dynamic LineChart
		if (true) {
			ChartTypeCB.getItems().addAll("Line Chart", "Dynamic Line Chart");
		}
		//TODO:
		//if DataTable contains >= 2 Number Data Columns && >= 1 String Data Columns => show ScatterChart
		if (true) {
			ChartTypeCB.getItems().add("Scatter Chart");
			
		}
		
		Button BackButton = new Button("Back");
		Button NextButton = new Button("Next");
		
		//Create grid
		GridPane grid = new GridPane();
		grid.setVgap(4);
		grid.setHgap(10);
		grid.add(SelectTypeL, 0, 0);
		grid.add(ChartTypeCB, 0, 1);
		grid.add(BackButton, 1, 2);
		grid.add(NextButton, 2, 2);
		
		Scene scene = new Scene(new Group(), 400, 350);
		
		((Group)scene.getRoot()).getChildren().add(grid);
		stage.setScene(scene);
		stage.show();
			
		
		
	}
	
}