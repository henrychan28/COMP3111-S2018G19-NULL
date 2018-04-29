package ui.comp3111;

import java.util.ArrayList;
import java.util.HashMap;

import core.comp3111.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;

/**
 * UI for generating chart
 * 
 * @author YuenTing
 *
 */
// extends the DataTable:
// getNumberOfDataColumn(Type)
// getDataColumnsOf(Type)

public class GenerateChartUI extends Application {

	// Data Storage
	private CoreData coreData = new CoreData();
	private int[] selectedTableIndex = { Constants.EMPTY, Constants.EMPTY };

	// View Chart
	private String ChartType; // "Line Chart", "Scatter Chart", "Dynamic Chart"

	// Attributes: Scene and Stage
	public static final int SCENE_CHART_NUM = 6;
	public static final int SCENE_Chart_TYPE_SELECTION = 0;
	public static final int SCENE_VIEW_HISTORY = 1;
	public static final int SCENE_LINE_CHART_SELECTION = 2;
	public static final int SCENE_SCATTER_CHART_SELECTION = 3;
	public static final int SCENE_DYNAMIC_CHART_SELECTION = 4;
	public static final int SCENE_SHOW_CHART = 5;
	public static final String[] SCENE_CHART_TITLES = { "Chart Type Selection", "View History Charts",
			"Generate New Line Chart", "Generate New Scatter Chart", "Generate New Dynamic Chart", "Show Chart" };
	private Stage stage = null;
	private Scene[] scenes = null;

	// To keep this application more structural,
	// The following UI components are used to keep references after invoking
	// createScene()

	// screen 1: paneChartSelection
	private Button btHistory, btGenerateNew;
	private ComboBox cbChartType;
	private XYChart<Number, Number> chartPreviewChart;
	private Label lbSelectType, lbmessage;

	// screen 2: paneViewHistory
	private Label lbViewHistory, lbhistorymsg;
	private Button btshow, btbackto1;
	private ObservableList<xychart> olhistory;
	private TableView<xychart> tvhistory;

	// screen 3: paneLineChartSelection
	private Label lbSelectNewLineChart, lbLineTitle, lbLineXaxis, lbLineYaxis, lblinemsg;
	private Button btLineSave, btLineSaveandPreview, btbackto1_;
	private ComboBox cbLineXaxis, cbLineYaxis;
	private TextField tfLineTitle;

	// screen 4: paneScatterChartSelection
	private Label lbSelectNewScatterChart, lbScatterTitle, lbScatterXaxis, lbScatterYaxis, lbScatterCaxis, lbscattermsg;
	private Button btScatterSave, btScatterSaveandPreview, btbackto1__;
	private ComboBox cbScatterXaxis, cbScatterYaxis, cbScatterCaxis;
	private TextField tfScatterTitle;

	// screen 5: paneDynamicChartSelection
	private Label lbSelectNewDynamicChart, lbDynamicTitile, lbDynamicXaxis, lbDynamicYaxis, lbDynamicCaxis,
			lbdynamicmsg;
	private Button btDynamicSave, btDynamicSaveandPreview, btbackto1___;
	private ComboBox cbDynamicXaxis, cbDynamicYaxis, cbDynamicCaxis;
	private TextField tfDynamicTitle;

	// screen 6: paneShowChart
	private Label lbShowChart;
	private XYChart<Number, Number> chartShowChart = null;
	private Button btbackto2; // back to history

	// Methods
	/* Pane made: */
	/** scene 0: Chart Type Selection, SCENE_Chart_TYPE_SELECTION */
	private Pane paneChartTypeSelection() {
		// 1. title
		lbSelectType = new Label("Select the Chart Type: ");
		lbSelectType.setFont(new Font("Arial", 20));

		// 2. select type

		cbChartType = new ComboBox();
		// testing
		// cbChartType.getItems().addAll("Line Chart", "Scatter Chart");
		// TODO: combine with henry

		DataTable selectedDataTable = coreData.getDataTable(selectedTableIndex);
		// if DataTable contains >= 2 Number Data Columns => show LineChart, Dynamic
		// Chart
		if (selectedDataTable.getNumColOfType(DataType.TYPE_NUMBER) >= 2) {
			cbChartType.getItems().addAll("Line Chart");
		}
		// if DataTable contains >= 2 Number Data Columns && >= 1 String Data Columns =>
		// show ScatterChart
		if (selectedDataTable.getNumColOfType(DataType.TYPE_NUMBER) >= 2
				&& selectedDataTable.getNumColOfType(DataType.TYPE_STRING) >= 1) {
			cbChartType.getItems().add("Scatter Chart");
		}

		// TODO: dynamic chart

		// TODO: preview chart type
		lbmessage = new Label("");

		// 3. buttons
		btHistory = new Button("View History");
		btGenerateNew = new Button("Generate New");
		HBox Buttons = new HBox(20);
		Buttons.getChildren().addAll(btHistory, btGenerateNew);
		Buttons.setAlignment(Pos.CENTER);
		// Container
		VBox container = new VBox(20);
		container.getChildren().addAll(lbSelectType, cbChartType, lbmessage, new Separator(), Buttons);
		container.setAlignment(Pos.CENTER);
		BorderPane pane = new BorderPane();
		pane.setCenter(container);

		return pane;
	}

	/** 1 . View History */
	private Pane paneViewHistory() {
		// 1. heading
		lbViewHistory = new Label("These are the history for DataTable");

		// 2.
		olhistory = FXCollections.observableArrayList();
		// DataTable selecteDataTable = coreData.getDataTable(selectedTableIndex);
		// ArrayList<xychart> charts =
		// coreData.getCharts(selecteDataTable.getTableName());
		// no chart during initialization
		tvhistory = CreateTableView("History", "ChartName", olhistory);
		lbhistorymsg = new Label("");

		// 4.

		btshow = new Button("show");
		btbackto1 = new Button("Back");

		HBox buttons = new HBox(20);
		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(btshow, btbackto1);

		VBox container = new VBox(20);
		container.getChildren().addAll(lbViewHistory, tvhistory, lbhistorymsg, new Separator(), buttons);
		container.setAlignment(Pos.CENTER);

		BorderPane pane = new BorderPane();
		pane.setCenter(container);
		return pane;
	}

	/** 2 SCENE_LINE_CHART_SELECTION */

	private Pane paneLineChartSelection() {

		// 1: heading
		lbSelectNewLineChart = new Label("Select Line Chart Setting");
		lbSelectNewLineChart.setFont(new Font("Arial", 20));

		// 2: Title - default DataTable Name, use editable combobox
		HBox Title = new HBox(10);
		lbLineTitle = new Label("Title");
		tfLineTitle = new TextField();
		tfLineTitle.setPromptText("Type the title");
		Title.getChildren().addAll(lbLineTitle, tfLineTitle);
		Title.setAlignment(Pos.CENTER);

		// 3: x-axis
		HBox Xaxis = new HBox(10);
		lbLineXaxis = new Label("x-axis");
		cbLineXaxis = new ComboBox();
		Xaxis.getChildren().addAll(lbLineXaxis, cbLineXaxis);
		Xaxis.setAlignment(Pos.CENTER);

		// TODO: add the key of all number type data columns of the DataTable to the
		// ComboBox
		DataTable selectedDataTable = coreData.getDataTable(selectedTableIndex);
		String[] keys = selectedDataTable.getColKeysOfType(DataType.TYPE_NUMBER);
		cbLineXaxis.getItems().addAll(keys);

		// 4: y-axis
		HBox Yaxis = new HBox(10);
		lbLineYaxis = new Label("y-axis");
		cbLineYaxis = new ComboBox();
		Yaxis.getChildren().addAll(lbLineYaxis, cbLineYaxis);
		Yaxis.setAlignment(Pos.CENTER);

		// TODO: add the key of all number type data columns of the DataTable to the
		// ComboBox
		cbLineYaxis.getItems().addAll(keys);

		// 5. message for reminding the user
		lblinemsg = new Label("");

		// 6 Buttons
		HBox ButtonsSave = new HBox(20);
		ButtonsSave.setAlignment(Pos.CENTER);
		btLineSave = new Button("Save");
		btLineSaveandPreview = new Button("Save and View");
		btbackto1_ = new Button("Back");

		ButtonsSave.getChildren().addAll(btLineSave, btLineSaveandPreview, btbackto1_);

		VBox container = new VBox(20);
		container.setAlignment(Pos.CENTER);
		container.getChildren().addAll(lbSelectNewLineChart, Title, Xaxis, Yaxis, new Separator(), lblinemsg,
				ButtonsSave);
		BorderPane pane = new BorderPane();
		pane.setCenter(container);

		return pane;
	}

	/** 3 Scatter Chart */
	private Pane paneScatterChartSelection() {
		// screen 4: paneScatterChartSelection
		// private Label , , , , ;
		// private Button btScatterSave, btScatterSaveandPreview;
		// private ComboBox , , , ;
		// 1. heading
		lbSelectNewScatterChart = new Label("Select the Scatter Chart Setting");
		lbSelectNewScatterChart.setFont(new Font("Arial", 20));

		// 2: Title - default DataTable Name, use textfield
		HBox Title = new HBox(10);
		lbScatterTitle = new Label("Title");
		tfScatterTitle = new TextField();
		tfScatterTitle.setPromptText("Type the title");
		Title.getChildren().addAll(lbScatterTitle, tfScatterTitle);
		Title.setAlignment(Pos.CENTER);

		// 3: x-axis
		HBox Xaxis = new HBox(10);
		lbScatterXaxis = new Label("x-axis");
		cbScatterXaxis = new ComboBox();
		Xaxis.getChildren().addAll(lbScatterXaxis, cbScatterXaxis);
		Xaxis.setAlignment(Pos.CENTER);

		// TODO: add the key of all number type data columns of the DataTable to the
		// ComboBox
		DataTable selectedDataTable = coreData.getDataTable(selectedTableIndex);
		String[] keys = selectedDataTable.getColKeysOfType(DataType.TYPE_NUMBER);
		cbScatterXaxis.getItems().addAll(keys);

		// 4: y-axis
		HBox Yaxis = new HBox(10);
		lbScatterYaxis = new Label("y-axis");
		cbScatterYaxis = new ComboBox();
		Yaxis.getChildren().addAll(lbScatterYaxis, cbScatterYaxis);
		Yaxis.setAlignment(Pos.CENTER);

		// TODO: add the key of all number type data columns of the DataTable to the
		// ComboBox
		cbScatterYaxis.getItems().addAll(keys);

		// 5 Category Axis
		HBox Caxis = new HBox(10);
		lbScatterCaxis = new Label("Categories");
		cbScatterCaxis = new ComboBox();
		Caxis.getChildren().addAll(lbScatterCaxis, cbScatterCaxis);
		Caxis.setAlignment(Pos.CENTER);

		// add the key of all String type data columns of the DataTable to the ComboBox
		String[] keys2 = selectedDataTable.getColKeysOfType(DataType.TYPE_STRING);
		cbScatterCaxis.getItems().addAll(keys2);
		// 6
		lbscattermsg = new Label("");
		// 7 Buttons
		HBox ButtonsSave = new HBox(20);
		ButtonsSave.setAlignment(Pos.CENTER);
		btScatterSave = new Button("Save");
		btScatterSaveandPreview = new Button("Save and View");
		btbackto1__ = new Button("Back");

		ButtonsSave.getChildren().addAll(btScatterSave, btScatterSaveandPreview, btbackto1__);

		VBox container = new VBox(20);
		container.setAlignment(Pos.CENTER);
		container.getChildren().addAll(lbSelectNewScatterChart, Title, Xaxis, Yaxis, Caxis, new Separator(),
				lbscattermsg, ButtonsSave);

		BorderPane pane = new BorderPane();
		pane.setCenter(container);
		return pane;
	}

	private Pane paneDynamicChartSelection() {
		BorderPane pane = new BorderPane();
		return pane;
	}

	/** Show Chart pane */
	private Pane paneShowChart() {
		// heading
		lbShowChart = new Label("This is the chart");
		lbShowChart.setFont(new Font("Arial", 20));
		// chart
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		XYChart<Number, Number> emptychart = new LineChart<Number, Number>(xAxis, yAxis);
		xAxis.setLabel("undefined");
		yAxis.setLabel("undefined");
		emptychart.setTitle("An empty line chart");
		chartShowChart = emptychart;

		// button
		btbackto2 = new Button("Back to History.");

		VBox container = new VBox(20);
		container.setAlignment(Pos.CENTER);
		container.getChildren().addAll(lbShowChart, chartShowChart, btbackto2);
		BorderPane pane = new BorderPane();
		pane.setCenter(container);
		return pane;
	}

	/** Handlers */
	/** Chart selection handler */
	private void initChartTypeSelectionHandler() {

		btHistory.setOnAction(e -> {
			if (cbChartType.getValue() == null) {
				lbmessage.setText("Please select a chart type");
			} else {
				// store the selected chart type to variable of the class
				ChartType = cbChartType.getValue().toString();
				// default
				cbChartType.setValue(null);

				// Then add the Charts to the History Pane
				updateHistoryScene();
			}
		});
		btGenerateNew.setOnAction(e -> {
			if (cbChartType.getValue() == null) {
				lbmessage.setText("Please select a chart type");
			} else {
				ChartType = cbChartType.getValue().toString();
				// default
				lbmessage.setText("");
				cbChartType.setValue(null);

				if (ChartType == ChartTypeValue.TYPE_LINE) {
					putSceneOnStage(SCENE_LINE_CHART_SELECTION);
				} else if (ChartType == ChartTypeValue.TYPE_SCATTER) {
					putSceneOnStage(SCENE_SCATTER_CHART_SELECTION);
				} else if (ChartType == ChartTypeValue.TYPE_DYNAMIC) {
					putSceneOnStage(SCENE_DYNAMIC_CHART_SELECTION);
				}

			}
		});

	};

	private void initViewHistoryHandler() {
		// history chart TableView
		tvhistory.setOnMouseClicked(new historyTableFactoryEventHandler());
		// back button
		btbackto1.setOnAction(e -> {
			putSceneOnStage(SCENE_Chart_TYPE_SELECTION);
		});
		// show button
		btshow.setOnAction(e -> {

			// If not selected
			if (tvhistory.getSelectionModel().isEmpty()) {
				lbhistorymsg.setText("Please select a chart");
			} else {
				// chartShowChart updated by tvhistory
				updateShowChartScene();
				// and then show chart
				putSceneOnStage(SCENE_SHOW_CHART);
			}
		});

	};

	/** Line Chart Handler */
	private void initLineChartSelectionHandler() {

		btLineSave.setOnAction(e -> {
			// Check the user enters value properly
			if (tfLineTitle.getText().isEmpty()) {
				lblinemsg.setText("Please enter the title");
			} else if (cbLineXaxis.getValue() == null) {
				System.out.print("Select the x-axis");

				lblinemsg.setText("Please select the x-axis");
			} else if (cbLineYaxis.getValue() == null) {
				System.out.print("Select the y-axis");

				lblinemsg.setText("Please select the y-axis");
			} else {
				System.out.print("Ok making the chart...");

				DataTable selectedDataTable = coreData.getDataTable(selectedTableIndex);
				String[] AxisLabels = { cbLineXaxis.getValue().toString(), cbLineYaxis.getValue().toString() };
				linechart lc;

				try {
					lc = new linechart(selectedDataTable, AxisLabels, tfLineTitle.getText().toString());
					coreData.addChart(selectedDataTable.getTableName(), lc);
					System.out.print("Check it in history!!! :) ");
					// Set back to the default values
					tfLineTitle.clear();
					cbLineXaxis.setValue(null);
					cbLineYaxis.setValue(null);

				} catch (ChartException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.print("Ohohoh...fail...");
				}

			}

		});

		btLineSaveandPreview.setOnAction(e -> {
			// TOOD: cut it or not
			// Check the user enters value properly
			if (tfLineTitle.getText().isEmpty()) {
				lblinemsg.setText("Please enter the title");
			} else if (cbLineXaxis.getValue() == null) {
				System.out.print("Select the x-axis");

				lblinemsg.setText("Please select the x-axis");
			} else if (cbLineYaxis.getValue() == null) {
				System.out.print("Select the y-axis");

				lblinemsg.setText("Please select the y-axis");
			} else {
				System.out.print("Ok making the chart...");

				DataTable selectedDataTable = coreData.getDataTable(selectedTableIndex);
				String[] AxisLabels = { cbLineXaxis.getValue().toString(), cbLineYaxis.getValue().toString() };
				linechart lc;

				try {
					lc = new linechart(selectedDataTable, AxisLabels, tfLineTitle.getText().toString());
					coreData.addChart(selectedDataTable.getTableName(), lc);
					System.out.print("Check it in history!!! :) ");
					chartShowChart = lc.getXYChart();
					// create a new scene for the chart object
					updateShowChartScene();
					// Set back to the default values
					tfLineTitle.clear();
					cbLineXaxis.setValue(null);
					cbLineYaxis.setValue(null);

					putSceneOnStage(SCENE_SHOW_CHART);
				} catch (ChartException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.print("Ohohoh...fail...");
				}
			}

		});
		btbackto1_.setOnAction(e -> {
			putSceneOnStage(SCENE_Chart_TYPE_SELECTION);
		});

	}

	/** Scatter Chart Handler */

	private void initScatterChartSelectionHandler() {
		btScatterSave.setOnAction(e -> {
			// Check the user enters value properly
			if (tfScatterTitle.getText().isEmpty()) {
				lbscattermsg.setText("Please enter the title");
			} else if (cbScatterXaxis.getValue() == null) {
				System.out.print("Select the x-axis");

				lbscattermsg.setText("Please select the x-axis");
			} else if (cbScatterYaxis.getValue() == null) {
				System.out.print("Select the y-axis");

				lbscattermsg.setText("Please select the y-axis");
			} else if (cbScatterCaxis.getValue() == null) {
				lbscattermsg.setText("Select the categories");
			}

			else {
				System.out.print("Ok...Making the Scatter Chart...");
				// TODO: make the scatter chart,

				DataTable selectedDataTable = coreData.getDataTable(selectedTableIndex);
				String[] AxisLabels = { cbScatterXaxis.getValue().toString(), cbScatterYaxis.getValue().toString(),
						cbScatterCaxis.getValue().toString() };
				scatterchart sc;

				try {
					sc = new scatterchart(selectedDataTable, AxisLabels, tfScatterTitle.getText().toString());
					coreData.addChart(selectedDataTable.getTableName(), sc);
					System.out.print("Check it in History!");
					// Set back to the default values
					tfScatterTitle.clear();
					cbScatterXaxis.setValue(null);
					cbScatterYaxis.setValue(null);
					cbScatterCaxis.setValue(null);

				} catch (ChartException e1) {
					// TODO Auto-generated catch block
					System.out.print("Ohohoh...failed...omg~~~");
					e1.printStackTrace();
				}

			}
		});
		btScatterSaveandPreview.setOnAction(e -> {
			// Check the user enters value properly
			if (tfScatterTitle.getText().isEmpty()) {
				lbscattermsg.setText("Please enter the title");
			} else if (cbScatterXaxis.getValue() == null) {
				System.out.print("Select the x-axis");

				lbscattermsg.setText("Please select the x-axis");
			} else if (cbScatterYaxis.getValue() == null) {
				System.out.print("Select the y-axis");

				lbscattermsg.setText("Please select the y-axis");
			} else if (cbScatterCaxis.getValue() == null) {
				lbscattermsg.setText("Select the categories");
			}

			else {
				System.out.print("Ok...Making the Scatter Chart...");
				// TODO: make the scatter chart,

				DataTable selectedDataTable = coreData.getDataTable(selectedTableIndex);
				String[] AxisLabels = { cbScatterXaxis.getValue().toString(), cbScatterYaxis.getValue().toString(),
						cbScatterCaxis.getValue().toString() };
				scatterchart sc;

				try {
					sc = new scatterchart(selectedDataTable, AxisLabels, tfScatterTitle.getText().toString());
					coreData.addChart(selectedDataTable.getTableName(), sc);
					chartShowChart = sc.getXYChart();
					// create a new scene for the chart object
					updateShowChartScene();
					// Set back to the default values
					tfScatterTitle.clear();
					cbScatterXaxis.setValue(null);
					cbScatterYaxis.setValue(null);
					cbScatterCaxis.setValue(null);

					putSceneOnStage(SCENE_SHOW_CHART);

				} catch (ChartException e1) {
					// TODO Auto-generated catch block
					System.out.print("Ohohoh...failed...omg~~~");
					e1.printStackTrace();
				}

			}
		});

		btbackto1__.setOnAction(e -> {
			putSceneOnStage(SCENE_Chart_TYPE_SELECTION);
		});

	}

	private void initDynamicChartSelectionHandler() {
		/*
		 * btbackto1___.setOnAction(e->{ putSceneOnStage(SCENE_Chart_TYPE_SELECTION);
		 * });
		 */

	}

	private void initShowChartHandler() {

		btbackto2.setOnAction(e -> {
			// update the view history scene, since not yet updated after creating chart

			updateHistoryScene();
			
		});

	}

	private void initScenes() {
		scenes = new Scene[SCENE_CHART_NUM];

		scenes[SCENE_Chart_TYPE_SELECTION] = new Scene(paneChartTypeSelection(), 400, 600);
		scenes[SCENE_VIEW_HISTORY] = new Scene(paneViewHistory(), 400, 600);
		scenes[SCENE_LINE_CHART_SELECTION] = new Scene(paneLineChartSelection(), 400, 600);
		scenes[SCENE_SCATTER_CHART_SELECTION] = new Scene(paneScatterChartSelection(), 400, 600);
		scenes[SCENE_DYNAMIC_CHART_SELECTION] = new Scene(paneDynamicChartSelection(), 400, 600);
		scenes[SCENE_SHOW_CHART] = new Scene(paneShowChart(), 500, 300);

		for (Scene s : scenes) {
			if (s != null) {
				s.getStylesheets().add("Main.css");
			}
		}
	}

	public void initEventHandlers() {

		// Common UI

		initChartTypeSelectionHandler();
		initViewHistoryHandler();
		initLineChartSelectionHandler();
		initScatterChartSelectionHandler();
		initDynamicChartSelectionHandler();
		initShowChartHandler();

		return;
	}

	private void putSceneOnStage(int sceneID) {

		// ensure the sceneID is valid
		if (sceneID < 0 || sceneID >= SCENE_CHART_NUM)
			return;

		stage.hide();
		stage.setTitle(SCENE_CHART_TITLES[sceneID]);
		stage.setScene(scenes[sceneID]);
		stage.setResizable(true);
		stage.show();
	}

	private void testingData() {
		int[] a = coreData.addParentTable(SampleDataGenerator.generateSampleLineData()); // 2number, 1string
		int[] b = coreData.addParentTable(SampleDataGenerator.generateSampleLineDataV2()); // 2number
		selectedTableIndex = a;
	}

	/**
	 * A function to create the TableView
	 * 
	 * @author HenryChan
	 * @param tableName
	 *            - Name of the TableView
	 * @param propertyName
	 *            - Name of the TableColumn
	 * @param tableList
	 *            -
	 * @return TableView<xychart> with Chart Name displayed
	 */

	private TableView<xychart> CreateTableView(String tableName, String propertyName,
			ObservableList<xychart> olhistory) {
		TableView<xychart> table = new TableView<>();
		TableColumn<xychart, String> Dataset = new TableColumn<>(tableName);
		Dataset.setCellValueFactory(new PropertyValueFactory<>(propertyName));
		table.setItems(olhistory);
		table.getColumns().add(Dataset);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		return table;
	}

	private class historyTableFactoryEventHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent t) {
			if(tvhistory.getSelectionModel().isEmpty()) {
				return;
			}
			int selected = tvhistory.getSelectionModel().getSelectedIndex();
			
			xychart selectedchart = olhistory.get(selected);
			// updated the selected chart to scene 6
			chartShowChart = selectedchart.getXYChart();
		}
	}

	// create a new scene for the chart object
	private void updateShowChartScene() {
		VBox container = new VBox(20);
		container.getChildren().addAll(lbShowChart, chartShowChart, btbackto2);
		container.setAlignment(Pos.CENTER);
		BorderPane pane = new BorderPane();
		pane.setCenter(container);
		scenes[SCENE_SHOW_CHART] = new Scene(pane, 500, 500);
	}
	/**Update and set the View History Scene */
	private void updateHistoryScene() {
		DataTable selectedDataTable = coreData.getDataTable(selectedTableIndex);
		ArrayList<xychart> charts = coreData.getChartsWithType(selectedDataTable.getTableName(), ChartType);

		if (charts == null) { // if it is empty, then remain in this pane

			lbmessage.setText("No History Available. Create a new one.");
		} else {
			lbmessage.setText("");
			olhistory.clear();

			for (xychart chart : charts) {
				// TODO: add to the TableView
				olhistory.add(chart);

			}
			lbViewHistory.setText(
					String.format("These are the %s for DataTable %s", ChartType, selectedDataTable.getTableName()));

			putSceneOnStage(SCENE_VIEW_HISTORY);

		}
	}

	

	@Override
	public void start(Stage primarystage) {
		testingData();
		stage = primarystage;
		initScenes();
		initEventHandlers();
		putSceneOnStage(0);
		System.out.println(cbChartType.getValue());

	}

	public static void main(String[] args) {
		launch(args);
	}

}