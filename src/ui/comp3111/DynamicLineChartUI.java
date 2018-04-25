package ui.comp3111;
import core.comp3111.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.Group;



public class DynamicLineChartUI extends Application {
	
	
	@Override
	public void start(Stage stage) {
		//Show the UI for line chart first
		//then show the following in a new window
		Label HeadingL = new Label("Select the property for the dynamic chart");
		Label speedL = new Label("Select the speed:");
		Label compactL = new Label("Select how compact the x-axis is:");
		
		ComboBox speedCB = new ComboBox();
		speedCB.getItems().addAll("very slow", "slow", "middle", "fast", "very fast");
		
		Slider compactS = new Slider();
		compactS.setMin(0.1);
		compactS.setMax(0.9);
		compactS.setValue(0.5);
		compactS.setShowTickLabels(true);
		compactS.setShowTickMarks(true);
		compactS.setMajorTickUnit(0.5);
		compactS.setMinorTickCount(8);
		compactS.setBlockIncrement(0.1);
		

		Button BackButton = new Button ("Back");
		Button NextButton = new Button ("Next");
		Button StaticButton = new Button("static line chart");
		
		//Create grid
		GridPane grid = new GridPane();
		
		grid.add(HeadingL,  0,  0);
		grid.add(speedL, 0, 1);
		grid.add(speedCB, 0, 2);
		grid.add(compactL, 0, 3);
		grid.add(compactS, 0, 4);
		grid.add(BackButton, 1, 5);
		grid.add(NextButton, 2, 5);
		//grid.add(StaticButton, 3, 5);
		
		//Add the grid to the scene		
		Scene scene = new Scene(new Group(), 400, 350);
		((Group)scene.getRoot()).getChildren().add(grid);
		stage.setScene(scene);
		stage.show();
		
	}
	
	
}