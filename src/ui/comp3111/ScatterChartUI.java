package ui.comp3111;
import core.comp3111.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.Group;



public class ScatterChartUI extends Application {
	
	
	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(new Group(), 400, 350);
		//Create the Label, TextField, ComboBox, Button
		Label HeadingL = new Label ("Select the scatter chart setting");
		Label TitleL = new Label ("Title");
		Label xL = new Label ("x-axis");
		Label yL = new Label ("y-axis");
		Label cL = new Label ("Categories");
		
		TextField TitleTF = new TextField();
		TitleTF.setPromptText("Enter the chart name.");
		
		ComboBox<String> xComboBox = new ComboBox<String>();
		ComboBox<String> yComboBox = new ComboBox<String>();
		ComboBox<String> cComboBox = new ComboBox<String>();

		//add the key of all number type data columns of the DataTable to xComboBox, yComboBox
		//TODO
		
		//add the key of all String type data columns of the DataTable to cComboBox
		//TODO

		Button BackButton = new Button ("Back");
		Button NextButton = new Button ("Next");
		
		//Create grid
		GridPane grid = new GridPane();
		grid.setVgap(4);
		grid.setHgap(10);
		grid.add(HeadingL, 0, 0);
		grid.add(TitleL,  0, 1);
		grid.add(xL, 0, 2);
		grid.add(yL, 0, 3);
		grid.add(cL, 0, 3);

		grid.add(TitleTF, 1, 1);
		grid.add(xComboBox, 1, 2);
		grid.add(yComboBox, 1, 3);
		grid.add(cComboBox, 1, 3);

		grid.add(BackButton, 2, 4);
		grid.add(NextButton, 3, 4);
		
		//Add the grid to the scene		
		((Group)scene.getRoot()).getChildren().add(grid);
		stage.setScene(scene);
		stage.show();
		
	}
	
	
}