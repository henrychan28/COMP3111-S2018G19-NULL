package ui.comp3111;

import core.comp3111.DataColumn;
import core.comp3111.DataTable;
import core.comp3111.DataType;
import core.comp3111.Constants;
import core.comp3111.SampleDataGenerator;
import core.comp3111.DataImport;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import core.comp3111.CoreData;
import core.comp3111.CoreDataIO;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
	private CoreData coreData = new CoreData();
	private int[] selectedTableIndex = {Constants.EMPTY, Constants.EMPTY};

	// Attributes: Scene and Stage
	private static final int SCENE_NUM = 4;
	private static final int SCENE_MAIN_SCREEN = 0;
	private static final int SCENE_LINE_CHART = 1;
	private static final int SCENE_IMPORT = 2;
	private static final int SCENE_EXPORT = 3;
	private static final String[] SCENE_TITLES = { "COMP3111 Chart - NULL", "Sample Line Chart Screen" };
	private Stage stage = null;
	private Scene[] scenes = null;

	// To keep this application more structural,
	// The following UI components are used to keep references after invoking
	// createScene()

	// Screen 1: paneMainScreen
	private Button btSampleLineChartData, btSampleLineChartDataV2, btSampleLineChart, btImportDataLineChart, btExport, btLoad;
	private Label lbSampleDataTable, lbMainScreenTitle;

	// Screen 2: paneSampleLineChartScreen
	private LineChart<Number, Number> lineChart = null;
	private NumberAxis xAxis = null;
	private NumberAxis yAxis = null;
	private Button btLineChartBackMain = null;

	/**
	 * create all scenes in this application
	 */
	private void initScenes() {
		scenes = new Scene[SCENE_NUM];
		scenes[SCENE_MAIN_SCREEN] = new Scene(paneMainScreen(), 400, 500);
		scenes[SCENE_LINE_CHART] = new Scene(paneLineChartScreen(), 800, 600);
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
		initLineChartScreenHandlers();
	}

	/**
	 * Initialize event handlers of the line chart screen
	 */
	private void initLineChartScreenHandlers() {

		// click handler
		btLineChartBackMain.setOnAction(e -> {
			putSceneOnStage(SCENE_MAIN_SCREEN);
		});
	}

	/**
	 * Populate sample data table values to the chart view
	 */
	private void populateSampleDataTableValuesToChart(String seriesName) {

		// Get 2 columns
		DataColumn xCol = coreData.getDataTable(selectedTableIndex).getCol("X");
		DataColumn yCol = coreData.getDataTable(selectedTableIndex).getCol("Y");

		// Ensure both columns exist and the type is number
		if (xCol != null && yCol != null && xCol.getTypeName().equals(DataType.TYPE_NUMBER)
				&& yCol.getTypeName().equals(DataType.TYPE_NUMBER)) {

			lineChart.setTitle("Sample Line Chart");
			xAxis.setLabel("X");
			yAxis.setLabel("Y");

			// defining a series
			XYChart.Series series = new XYChart.Series();

			series.setName(seriesName);

			// populating the series with data
			// As we have checked the type, it is safe to downcast to Number[]
			Number[] xValues = (Number[]) xCol.getData();
			Number[] yValues = (Number[]) yCol.getData();

			// In DataTable structure, both length must be the same
			int len = xValues.length;

			for (int i = 0; i < len; i++) {
				series.getData().add(new XYChart.Data(xValues[i], yValues[i]));
			}

			// clear all previous series
			lineChart.getData().clear();

			// add the new series as the only one series for this line chart
			lineChart.getData().add(series);

		}

	}

	/**
	 * Initialize event handlers of the main screen
	 */
	private void initMainScreenHandlers() {

		// click handler
		btSampleLineChartData.setOnAction(e -> {
			// In this example, we invoke SampleDataGenerator to generate sample data
			selectedTableIndex = coreData.addParentTable(SampleDataGenerator.generateSampleLineData());
			lbSampleDataTable.setText(String.format("SampleDataTable: %d rows, %d columns", coreData.getDataTable(selectedTableIndex).getNumRow(),
					coreData.getDataTable(selectedTableIndex).getNumCol()));

			populateSampleDataTableValuesToChart("Sample 1");

		});

		// click handler
		btSampleLineChartDataV2.setOnAction(e -> {
			// In this example, we invoke SampleDataGenerator to generate sample data
			selectedTableIndex = coreData.addParentTable(SampleDataGenerator.generateSampleLineDataV2());
			lbSampleDataTable.setText(String.format("SampleDataTable: %d rows, %d columns", coreData.getDataTable(selectedTableIndex).getNumRow(),
					coreData.getDataTable(selectedTableIndex).getNumCol()));

			populateSampleDataTableValuesToChart("Sample 2");

		});
		
		// click handler for import test
		btImportDataLineChart.setOnAction(e -> {
						
			// Present file chooser to the user and store result
			DataImport fileToImport = new DataImport();
			
			
			// If a file is chosen process it
			if (fileToImport.getFileForImport()) 
			{
				// Parse the selected file to temporary table, returning column headers
				String[] columnHeaders = null;
				columnHeaders = fileToImport.parseFile();
				
				// Ask the user about the method to handle the various columns
				ColumnTypeUI columnWindow = new ColumnTypeUI(columnHeaders);
				HashMap<String, String[]> columnData = columnWindow.presentUI(stage);
				
				if (columnData != null) {
					selectedTableIndex = coreData.addParentTable(fileToImport.buildDataTable(columnData));
					lbSampleDataTable.setText(String.format("SampleDataTable: %d rows, %d columns", coreData.getDataTable(selectedTableIndex).getNumRow(),
							coreData.getDataTable(selectedTableIndex).getNumCol()));

					populateSampleDataTableValuesToChart("Import Test");
				}
			} 		
		});

		// click handler
		btSampleLineChart.setOnAction(e -> {
			putSceneOnStage(SCENE_LINE_CHART);
		});
		
		btExport.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			File dest = fileChooser.showSaveDialog(null);
			if (dest != null) {
				CoreDataIO io = new CoreDataIO();
				io.saveCoreData(coreData, dest.getAbsolutePath(),Constants.FILE_EX);
			}
		});
		
		btLoad.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			File dest = fileChooser.showOpenDialog(null);
			if (dest != null) {
				CoreDataIO io = new CoreDataIO();
				coreData = io.openCoreData(dest.getAbsolutePath());
			}
		});

	}
	

	/**
	 * Create the line chart screen and layout its UI components
	 * 
	 * @return a Pane component to be displayed on a scene
	 */
	private Pane paneLineChartScreen() {

		xAxis = new NumberAxis();
		yAxis = new NumberAxis();
		lineChart = new LineChart<Number, Number>(xAxis, yAxis);

		btLineChartBackMain = new Button("Back");

		xAxis.setLabel("undefined");
		yAxis.setLabel("undefined");
		lineChart.setTitle("An empty line chart");

		// Layout the UI components
		VBox container = new VBox(20);
		container.getChildren().addAll(lineChart, btLineChartBackMain);
		container.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setCenter(container);

		// Apply CSS to style the GUI components
		pane.getStyleClass().add("screen-background");

		return pane;
	}

	/**
	 * Creates the main screen and layout its UI components
	 * 
	 * @return a Pane component to be displayed on a scene
	 */
	private Pane paneMainScreen() {

		lbMainScreenTitle = new Label("COMP3111 Chart");
		btSampleLineChartData = new Button("Sample 1");
		btSampleLineChartDataV2 = new Button("Sample 2");
		btImportDataLineChart = new Button("Import Test");
		btSampleLineChart = new Button("Sample Line Chart");
		lbSampleDataTable = new Label("DataTable: empty");
		btExport = new Button("Save Environment");
		btLoad = new Button("Load Environment");

		// Layout the UI components

		HBox hc = new HBox(20);
		hc.setAlignment(Pos.CENTER);
		hc.getChildren().addAll(btSampleLineChartData, btSampleLineChartDataV2, btImportDataLineChart, btExport);
		
		HBox hc2 = new HBox(20);
		hc2.setAlignment(Pos.CENTER);
		hc2.getChildren().addAll(btExport, btLoad);

		VBox container = new VBox(20);
		container.getChildren().addAll(lbMainScreenTitle, hc, hc2, lbSampleDataTable, new Separator(), btSampleLineChart);
		container.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setCenter(container);

		// Apply style to the GUI components
		btSampleLineChart.getStyleClass().add("menu-button");
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
		stage.setTitle(SCENE_TITLES[sceneID]);
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

	/**
	 * main method - only use if running via command line
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
