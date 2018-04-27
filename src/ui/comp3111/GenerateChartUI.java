package ui.comp3111;

import core.comp3111.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
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
import javafx.scene.layout.Pane;



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

	//Daata Storage
	private CoreData coreData = new CoreData();
	private int[] selectedTableIndex = {Constants.EMPTY, Constants.EMPTY};
	
	//Attributes: Scene and Stage
	public static final int SCENE_CHART_NUM = 6;
	public static final int SCENE_Chart_TYPE_SELECTION = 0;
	public static final int SCENE_VIEW_HISTORY = 1;
	public static final int SCENE_LINE_CHART_SELECTION = 2;
	public static final int SCENE_SCATTER_CHART_SELECTION = 3;
	public static final int SCENE_DYNAMIC_CHART_SELECTION = 4;
	public static final int SCENE_SHOW_CHART = 5;
	public static final String[] SCENE_CHART_TITLES = {
			"Chart Type Selection", "View History Charts", "Generate New Line Chart", "Generate New Scatter Chart",
			"Generate New Dynamic Chart", "Show Chart"
	};
	private Stage stage = null;
	private Scene[] scenes = null;
	
	// To keep this application more structural,
	// The following UI components are used to keep references after invoking
	// createScene()
	
	//screen 1: paneChartSelection
	private Button btHistory, btGenerateNew;
	private ComboBox cbChartType;
	private XYChart<Number, Number> chartPreviewChart;
	private Label lbSelectType;
	
	//screen 2, 3, 4, 5: the back button
	private Button btbackto1;
	
	//screen 2: paneViewHistory
	private TableColumn  colChartType, colChartID;
	private Button btshow;


	//screen 3: paneLineChartSelection
	private Label lbSelectNewLineChart, lbLineTitle, lbLineXaxis, lbLineYaxis;
	private Button btLineSave, btLineSaveandPreview;
	private ComboBox cbLineTitle, cbLineXaxis, cbLineYaxis;
	
	//screen 4: paneScatterChartSelection
	private Label lbSelectNewScatterChart, lbScatterTitle, lbScatterXaxis, lbScatterYaxis, lbScatterCaxis;
	private Button btScatterSave, btScatterSaveandPreview;
	private ComboBox cbScatterTitle, cbScatterXaxis, cbScatterYaxis, cbScatterCaxis;
	
	//screen 5: paneDynamicChartSelection
	
	
	
	//screen 6: paneShowChart
	private Label lbShowChart;
	private XYChart<Number, Number> chartShowChart;
	private Button btbackto2; //back to history

	/*
	public static VBox ChartTypeSelection;
	public static TableView<xychart> ViewHistory;
	public static VBox LineChartSelection;
	public static VBox ScatterChartSelection;
	public static VBox DyLineChartSelection;
	
	*/	
	//Methods
	//1. Pane made
	private Pane paneChartTypeSelection () {
		//1. title
		lbSelectType = new Label("Select the Chart Type: ");
		lbSelectType.setFont(new Font("Arial", 20));
		

		//2. select type

		cbChartType = new ComboBox();
		
		/*
		DataTable selectedDataTable = coreData.getDataTable(selectedTableIndex);
		//if DataTable contains >= 2 Number Data Columns => show LineChart, Dynamic LineChart
		if (selectedDataTable.getNumColOfType(DataType.TYPE_NUMBER) >= 2) {
			cbChartType.getItems().addAll("Line Chart");
		}
		//if DataTable contains >= 2 Number Data Columns && >= 1 String Data Columns => show ScatterChart
		if (selectedDataTable.getNumColOfType(DataType.TYPE_NUMBER) >= 2 && selectedDataTable.getNumColOfType(DataType.TYPE_STRING) >=1) {
			cbChartType.getItems().add("Scatter Chart");
		}*/
		
		
		//TODO: dynamic chart
			
		//TODO: preview chart type
		//3. buttons
		btHistory = new Button("View History");
		btGenerateNew = new Button("Generate New");
		HBox Buttons = new HBox(20);
		Buttons.getChildren().addAll(btHistory, btGenerateNew);
		Buttons.setAlignment(Pos.CENTER);
		//Container
		VBox container = new VBox(20);
		container.getChildren().addAll(lbSelectType, cbChartType, new Separator(),  Buttons);
		container.setAlignment(Pos.CENTER);
		BorderPane pane = new BorderPane();
		pane.setCenter(container);

		return pane;
	}
	private Pane paneViewHistory() {

		//1. 	
		TableColumn colHistory = new TableColumn("Chart History");
		colChartType = new TableColumn ("Chart Type");
		colChartID = new TableColumn("Chart ID");
		colHistory.getColumns().addAll(colChartType, colChartID);
		
		TableView tvhistory = new TableView();
		tvhistory.getColumns().add(colHistory);
		
		//2. 
		btbackto1 = new Button("Back");
		btshow = new Button("show");
		HBox buttons = new HBox(20);
		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(btbackto1, btshow);
		
		VBox container = new VBox(20);
		container.getChildren().addAll(tvhistory, buttons);		
		container.setAlignment(Pos.CENTER);

		
		BorderPane pane = new BorderPane();
		pane.setCenter(container);
		return pane;
	}
	private Pane paneLineChartSelection() {

		
		//1: heading 
		lbSelectNewLineChart = new Label("Select Line Chart Setting");
		lbSelectNewLineChart.setFont(new Font("Arial", 20));
		
		//2: Title - default DataTable Name, use editable combobox
		HBox Title = new HBox(10);
		lbLineTitle = new Label("Title"); 
		cbLineTitle = new ComboBox();
		Title.getChildren().addAll(lbLineTitle, cbLineTitle);
		Title.setAlignment(Pos.CENTER);
		//TODO: editable combobox
		
		
		//3: x-axis 
		HBox Xaxis = new HBox(10);
		lbLineXaxis = new Label("x-axis");
		cbLineXaxis = new ComboBox();
		Xaxis.getChildren().addAll(lbLineXaxis, cbLineXaxis);
		Xaxis.setAlignment(Pos.CENTER);

		//TODO: add the key of all number type data columns of the DataTable to the ComboBox
	
		//4: y-axis
		HBox Yaxis = new HBox(10);
		lbLineYaxis = new Label("y-axis");
		cbLineYaxis= new ComboBox();
		Yaxis.getChildren().addAll(lbLineYaxis, cbLineYaxis);
		Yaxis.setAlignment(Pos.CENTER);

		//TODO: add the key of all number type data columns of the DataTable to the ComboBox

		//5 Buttons
		HBox ButtonsSave = new HBox(20);
		ButtonsSave.setAlignment(Pos.CENTER);
		btLineSave = new Button("Save");
		btLineSaveandPreview= new Button("Save and View");
		ButtonsSave.getChildren().addAll(btLineSave, btLineSaveandPreview, btbackto1);
		
		
		VBox container = new VBox(20);
		container.getChildren().addAll(lbSelectNewLineChart, Title, Xaxis, Yaxis, new Separator(), ButtonsSave);
		container.setAlignment(Pos.CENTER);
		BorderPane pane = new BorderPane();
		pane.setCenter(container);
		
		return pane;
	}
	private Pane paneScatterChartSelection() {
		//screen 4: paneScatterChartSelection
		//private Label , , , , ;
		//private Button btScatterSave, btScatterSaveandPreview;
		//private ComboBox , , , ;
		//1. heading
		lbSelectNewScatterChart = new Label("Select the Scatter Chart Setting");
		lbSelectNewScatterChart.setFont(new Font("Arial", 20));
		
		//2: Title - default DataTable Name, use editable combobox
		HBox Title = new HBox(10);
		lbScatterTitle = new Label("Title"); 
		cbScatterTitle = new ComboBox();
		Title.getChildren().addAll(lbScatterTitle, cbScatterTitle);
		Title.setAlignment(Pos.CENTER);
		//TODO: editable combobox
		
		
		//3: x-axis 
		HBox Xaxis = new HBox(10);
		lbScatterXaxis = new Label("x-axis");
		cbScatterXaxis = new ComboBox();
		Xaxis.getChildren().addAll(lbScatterXaxis, cbScatterXaxis);
		Xaxis.setAlignment(Pos.CENTER);

		//TODO: add the key of all number type data columns of the DataTable to the ComboBox
	
		//4: y-axis
		HBox Yaxis = new HBox(10);
		lbScatterYaxis = new Label("y-axis");
		cbScatterYaxis= new ComboBox();
		Yaxis.getChildren().addAll(lbScatterYaxis, cbScatterYaxis);
		Yaxis.setAlignment(Pos.CENTER);

		//TODO: add the key of all number type data columns of the DataTable to the ComboBox

		//5 Category Axis
		HBox Caxis = new HBox(10);
		lbScatterCaxis = new Label("Categories");
		cbScatterCaxis= new ComboBox();
		Caxis.getChildren().addAll(lbScatterCaxis, cbScatterCaxis);
		Caxis.setAlignment(Pos.CENTER);
		
		//6 Buttons
		HBox  ButtonsSave = new HBox(20);
		ButtonsSave.setAlignment(Pos.CENTER);
		btScatterSave = new Button("Save");
		btScatterSaveandPreview= new Button("Save and View");
		ButtonsSave.getChildren().addAll(btScatterSave, btScatterSaveandPreview, btbackto1);
		
		
		VBox container = new VBox(20);
		container.setAlignment(Pos.CENTER);
		container.getChildren().addAll(lbSelectNewScatterChart, Title,  Xaxis, Yaxis,Caxis, new Separator(), ButtonsSave);
		
		BorderPane pane = new BorderPane();
		pane.setCenter(container);
		return pane;
	}
	private Pane paneDynamicChartSelection() {
		BorderPane pane = new BorderPane();
		return pane;
	}
	private Pane paneShowChart() {
		//heading
		lbShowChart = new Label("This is the chart");
		lbShowChart.setFont(new Font("Arial", 20));
		//chart
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		chartShowChart = new LineChart<Number, Number>(xAxis, yAxis);
		xAxis.setLabel("undefined");
		yAxis.setLabel("undefined");
		chartShowChart.setTitle("An empty line chart");
		//button
		btbackto2 = new Button("Back");
		
		VBox container = new VBox(20);
		container.setAlignment(Pos.CENTER);
		container.getChildren().addAll(lbShowChart, chartShowChart, btbackto2);
		BorderPane pane = new BorderPane();
		pane.setCenter(container);
		return pane;
	}
	
	
	
	
	
	
	private void initScenes() {
		scenes = new Scene[SCENE_CHART_NUM];
		scenes[SCENE_Chart_TYPE_SELECTION] = new Scene(paneChartTypeSelection(), 400, 600);
		scenes[SCENE_VIEW_HISTORY] = new Scene(paneViewHistory(), 400, 600);
		scenes[SCENE_LINE_CHART_SELECTION] = new Scene( paneLineChartSelection() , 400, 600);
		scenes[SCENE_SCATTER_CHART_SELECTION] = new Scene(paneScatterChartSelection(), 400, 600) ;
		scenes[SCENE_DYNAMIC_CHART_SELECTION] = new Scene(paneDynamicChartSelection() , 400, 600);
		scenes[SCENE_SHOW_CHART] = new Scene(paneShowChart() , 400, 600);	
		
		for(Scene s: scenes) {
			if(s!= null) {s.getStylesheets().add("Main.css");}
		}
	}
	
	public void initEventHandlers() {
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
	
	@Override
	public void start(Stage primarystage) {
		 stage = primarystage;
		 initScenes();
		 initEventHandlers();
		 putSceneOnStage(SCENE_SHOW_CHART);
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}