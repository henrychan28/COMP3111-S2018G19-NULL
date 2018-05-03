package ui.comp3111;

import java.util.Arrays;

import core.comp3111.Constants;
import core.comp3111.CoreData;
import core.comp3111.DataFilter;
import core.comp3111.DataTable;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * DataRandomFilterUI is the UI for user to randomly split a dataTable into 
 * two, enabling user to replace/add the split dataTable into the database. 
 * 
 * @author Henry Chan

 */
public class DataRandomFilterUI extends Application{

	private DataTable currentTable;
	private TextField tableNameTextField1;
	private TextField tableNameTextField2;
	private Stage stageRandomFilterUI;
	private Text randomText;
	private ObservableList<String> replacementOptions = 
		    FXCollections.observableArrayList(
		        "Yes", "No"
		    );
	private ComboBox<String> replacementOptionsComboBox;
	private Slider splitSlider;
	
	/** 
	 * Creates a DataRandomFilterUI with the specified dataTable.
	 * @param dataTable
	 * 			the input dataTable
	 */
    public DataRandomFilterUI(DataTable dataTable) {
    	currentTable = dataTable;
    }
    
	public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage stage) {
    	//Initialization of stage
    	stageRandomFilterUI = stage;
		stageRandomFilterUI.setTitle("Data Filter Interface");
		stageRandomFilterUI.setWidth(350);
		stageRandomFilterUI.setHeight(500);
        
        Text tableNamesTextBox = new Text();
        tableNamesTextBox.setText("Enter Table Names:");

        tableNameTextField1 = new TextField ();
        tableNameTextField2 = new TextField ();
        
        splitSlider = new Slider();
        splitSlider.setMin(0);
        splitSlider.setMax(100);
        splitSlider.setValue(50);
        splitSlider.setShowTickLabels(true);
        splitSlider.setShowTickMarks(true);
        splitSlider.setMajorTickUnit(50);
        splitSlider.setMinorTickCount(5);
        splitSlider.setBlockIncrement(10);
        
        Text splitRatio = new Text();
        splitRatio.setText("50%");
        
        splitSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
            	splitRatio.setText(String.format("%.2f", new_val)+"%");
            }
        });
        
        Text replaceOriginalTextFilter2 = new Text();
        replaceOriginalTextFilter2.setText("Replace original table?");
        
        replacementOptionsComboBox = new ComboBox<>(replacementOptions);
        replacementOptionsComboBox.getSelectionModel().select(0);

        
        Button generateButton = new Button("Generate Table");
        generateButton.setOnMouseClicked(new GenerateButtonEventHandler());
        
        Button backButton = new Button("Back");
        backButton.setOnMouseClicked(new BackButtonEventHandler());
        
        randomText = new Text();
        
        HBox randomBox = new HBox();
        randomBox.getChildren().addAll(generateButton, backButton);
        randomBox.setAlignment(Pos.CENTER);

        
        VBox randomFilterVbox = new VBox(10);
        randomFilterVbox.setPadding(new Insets(10, 10, 10, 10));
        randomFilterVbox.setAlignment(Pos.CENTER);
        randomFilterVbox.getChildren().addAll(tableNamesTextBox, tableNameTextField1, tableNameTextField2, 
        									  splitSlider, splitRatio, replaceOriginalTextFilter2, replacementOptionsComboBox, 
        									  randomBox, randomText);
        Scene scene = new Scene(randomFilterVbox);
        stageRandomFilterUI.setScene(scene);
        stageRandomFilterUI.show();
    }
    
	
	/**
	 * GenerateButtonEventHandler fires upon a click on the "generate button.
	 * It will retrieve the table names from text fields and check whether is there any 
	 * name clash with dataTables in CoreData. Upon confirmation of correct name 
	 * and available name, if split tables are not empty, they will be added/replaced 
	 * to the CoreData according to user's choice.
	 */  
    private class GenerateButtonEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent t) 
        {	
        	CoreData coreData = CoreData.getInstance();
        	String tableName1 = tableNameTextField1.getText();
        	String tableName2 = tableNameTextField2.getText();

        	//Case: there is no user input
        	if(tableName1=="" || tableName1.trim().isEmpty() || 
        	   tableName2=="" || tableName2.trim().isEmpty()) {
        		randomText.setText("Table Name cannot be empty");
        		return;
        	}
        	//Case: the table name already exist
        	else if(!Arrays.equals(coreData.searchForDataTable(tableName1),new int[] {-1,-1})||
        			!Arrays.equals(coreData.searchForDataTable(tableName2),new int[] {-1,-1})) {
        		randomText.setText("Table Name already exist");
        		return;
        	}
        	double splitRatio = splitSlider.getValue()/100.0;
        	
        	//This is the dataTable that will be added to CoreData
        	DataTable[] randomDataTables = DataFilter.RandomSplitTable(currentTable, splitRatio);
        	randomDataTables[0].setName(tableName1);
        	randomDataTables[1].setName(tableName2);
        	
        	//Case: there is a dataTable with no entry after split
        	if(randomDataTables[0].getNumRow()==0||randomDataTables[1].getNumRow()==0) {
            	randomText.setText("Error: One of the split table has no row");
            	return;
        	}

        	int[] tableIndex = coreData.searchForDataTable(currentTable.getTableName());
        	//Case: the user selected to replace the original dataTable
        	if(replacementOptionsComboBox.getSelectionModel().getSelectedItem()=="Yes") {
            	coreData.setDataTable(tableIndex, randomDataTables[0]);
            	coreData.addChildTable(randomDataTables[1], tableIndex[Constants.OUTER]);

            	randomText.setText("Table \""+ tableName1 + "\" and \"" + tableName2 + "\" successfully replaced");
        	} 
        	//Case: the user selected not to replace the original dataTable
        	else {
            	coreData.addChildTable(randomDataTables[0], tableIndex[Constants.OUTER]);
            	coreData.addChildTable(randomDataTables[1], tableIndex[Constants.OUTER]);
            	randomText.setText("Table \""+ tableName1 + "\" and \"" + tableName2 + "\" successfully added");

        	}
		}
    }
    
	/**
	 * Event handler for transition to the DataHostingUI
	 */  
    private class BackButtonEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent t) {
			 //Return to the dataHostingUI  
        	DataHostingUI dataHostingUI = new DataHostingUI();
        	dataHostingUI.start(stageRandomFilterUI);
        }
    }
}