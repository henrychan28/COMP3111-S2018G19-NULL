package ui.comp3111;

import core.comp3111.Constants;
import core.comp3111.DataImport;

import java.io.File;
import java.util.HashMap;
import java.util.Optional;

import core.comp3111.CoreData;
import core.comp3111.CoreDataIO;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * The Main class of this GUI application
 * 
 * @author cspeter
 *
 */
public class Main extends Application {

	// Attribute: DataTable
	// In this sample application, a single data table is provided
	// You need to extend it to handle multiple data tables
	// Hint: Use java.util.List interface and its implementation classes (e.g.
	// java.util.ArrayList)
	
	// Data Storage
	private CoreData coreData = CoreData.getInstance();
	private int[] selectedTableIndex = {Constants.EMPTY, Constants.EMPTY};

	// Attributes: Scene and Stage
	private static final int SCENE_NUM = 4;
	private static final int SCENE_MAIN_SCREEN = 0;
	private static final String SCENE_TITLE = "COMP3111 Chart - NULL";
	private Stage stage = null;
	private Scene[] scenes = null;

	// To keep this application more structural,
	// The following UI components are used to keep references after invoking
	// createScene()

	// Screen 1: paneMainScreen
	private Button btViewTables, btImportDataLineChart, btExport, btLoad;
	private Label lbStatusLabel, lbMainScreenTitle;
	private TextInputDialog dialog;

	/**
	 * create all scenes in this application
	 */
	private void initScenes() {
		scenes = new Scene[SCENE_NUM];
		scenes[SCENE_MAIN_SCREEN] = new Scene(paneMainScreen(), 600, 300);
		for (Scene s : scenes) {
			if (s != null)
				// Assumption: all scenes share the same stylesheet
				s.getStylesheets().add("Main.css");
		}
	}

	/**
	 * This method will be invoked after createScenes(). In this stage, all UI
	 * components will be created with a non-NULL references for the UI components
	 * that requires interaction (e.g. button click, or others).
	 */
	private void initEventHandlers() {
		initMainScreenHandlers();
	}


	/**
	 * Initialize event handlers of the main screen
	 */
	private void initMainScreenHandlers() {
		
		// click handler for import
		btImportDataLineChart.setOnAction(e -> {
						
			// Present file chooser to the user and store result
			DataImport fileToImport = new DataImport();
			String importFilePath = null;
			
			// If a file is chosen process it
			importFilePath = getFileForImport();
			if (importFilePath != null) 
			{
				fileToImport.setFile(importFilePath);
				lbStatusLabel.setText("File Selected: " + importFilePath);
				
				// Ask for the file name
				dialog = new TextInputDialog("Name Here");
				dialog.setTitle("Import CSV");
				dialog.setHeaderText("Before importing, please enter a unique name for the table");
				dialog.setContentText("Please enter the name:");
				dialog.initOwner(stage);
				dialog.initModality(Modality.APPLICATION_MODAL); 

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
					lbStatusLabel.setText("Table being called: " + result.get());
				
					// Parse the selected file to temporary table, returning column headers
					String[] columnHeaders = null;
					columnHeaders = fileToImport.parseFile();
					
					// Ask the user about the method to handle the various columns
					ColumnTypeUI columnWindow = new ColumnTypeUI(columnHeaders);
					HashMap<String, String[]> columnData = columnWindow.presentUI(stage);
					
					if (columnData != null) {
						selectedTableIndex = coreData.addParentTable(fileToImport.buildDataTable(columnData,result.get()));
						lbStatusLabel.setText(String.format("DataTable Added: %d rows, %d columns", coreData.getDataTable(selectedTableIndex).getNumRow(),
								coreData.getDataTable(selectedTableIndex).getNumCol()));
					}
				}
			} 		
		});
		
		btExport.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			File dest = fileChooser.showSaveDialog(null);
			if (dest != null) {
				CoreDataIO io = new CoreDataIO();
				io.saveCoreData(coreData, dest.getAbsolutePath(),Constants.FILE_EX);
				lbStatusLabel.setText("Environment saved to location: " + dest.getAbsolutePath() + Constants.FILE_EX);
			}
		});
		
		btLoad.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			File src = fileChooser.showOpenDialog(null);
			if (src != null) {
				CoreDataIO io = new CoreDataIO();
				CoreData.setInstance(io.openCoreData(src.getAbsolutePath()));
				lbStatusLabel.setText("Environment loaded from: " + src.getAbsolutePath());
			}
		});
	}

	/**
	 * Creates the main screen and layout its UI components
	 * 
	 * @return a Pane component to be displayed on a scene
	 */
	private Pane paneMainScreen() {
		
		lbMainScreenTitle = new Label("COMP3111 Chart - G19 NULL");
		lbStatusLabel = new Label("Program Opened");
		btImportDataLineChart = new Button("Import CSV");
		btExport = new Button("Save Environment");
		btLoad = new Button("Load Environment");
		btViewTables = new Button("Select Table");
		btViewTables.setOnMouseClicked(new btViewTablesEventHandler());
		// Layout the UI components
		
		HBox hc = new HBox(20);
		hc.setAlignment(Pos.CENTER);
		hc.getChildren().addAll(btExport, btLoad);

		VBox container = new VBox(20);
		container.getChildren().addAll(lbMainScreenTitle, btImportDataLineChart, hc, lbStatusLabel, new Separator(), btViewTables);
		container.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setCenter(container);

		// Apply style to the GUI components
		lbMainScreenTitle.getStyleClass().add("menu-title");
		pane.getStyleClass().add("screen-background");

		return pane;
	}
	

	/**
	 * This method is used to pick anyone of the scene on the stage. It handles the
	 * hide and show order. In this application, only one active scene should be
	 * displayed on stage.
	 * 
	 * @param sceneID
	 *            - The sceneID defined above (see SCENE_XXX)
	 */
	private void putSceneOnStage(int sceneID) {

		// ensure the sceneID is valid
		if (sceneID < 0 || sceneID >= SCENE_NUM)
			return;

		stage.hide();
		stage.setTitle(SCENE_TITLE);
		stage.setScene(scenes[sceneID]);
		stage.setResizable(true);
		stage.show();
	}

	/**
	 * All JavaFx GUI application needs to override the start method You can treat
	 * it as the main method (i.e. the entry point) of the GUI application
	 */
	@Override
	public void start(Stage primaryStage) {
		try {

			stage = primaryStage; // keep a stage reference as an attribute
			initScenes(); // initialize the scenes
			initEventHandlers(); // link up the event handlers
			putSceneOnStage(SCENE_MAIN_SCREEN); // show the main screen

		} catch (Exception e) {

			e.printStackTrace(); // exception handling: print the error message on the console
		}
	}

    private class btViewTablesEventHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent t) {
        	DataHostingUI dataHostingUI = new DataHostingUI();
        	dataHostingUI.start(stage);
        }
    }
    
    /**
	 * Presents the system file choosing dialog so the user can choose a file for eventual import
	 * Link to the file is contained in this class
	 * 
	 * Defaults to the desktop
	 * 
	 * @return the absolute path of the selected file, or null if none was selected
	 */
	public String getFileForImport() {
		String filePath = null;
		
		// Initialize the file chooser
		FileChooser fileChooser = new FileChooser();
		File selectedFile = null;
		// We only are looking for CSV, but allow all (*) too
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("CSV Data Files", "*.csv"),
		         new ExtensionFilter("All Files", "*.*"));
		// Default to the user's desktop
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home"), "Desktop"));
		
		// Open the file chooser
		selectedFile = fileChooser.showOpenDialog(null);
		if (selectedFile != null) {
			filePath = selectedFile.getAbsolutePath();
		}
		
		return filePath;
	}
    
	/**
	 * main method - only use if running via command line
	 * 
	 * @param args
	 **/
	public static void main(String[] args) {
		launch(args);
	}
	
}
