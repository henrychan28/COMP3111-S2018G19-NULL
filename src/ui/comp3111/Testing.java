package ui.comp3111;


import core.comp3111.*;
import javafx.application.Application;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.scene.Scene;





public class Testing extends Application {
	private CoreData coreData = new CoreData();
	private int[] selectedTableIndex = { Constants.EMPTY, Constants.EMPTY };
	
	XYChart<Number, Number > chart;
	@Override
	public void start(Stage stage) throws ChartException{
		int[] a = coreData.addParentTable(SampleDataGenerator.generateSampleLineData()); // 2number, 1string
		int[] b = coreData.addParentTable(SampleDataGenerator.generateSampleLineDataV2()); // 2number
		String [] axis = {"X","Y"};
		linechart line1 = new linechart(coreData.getDataTable(a), axis, "Hello World");
		linechart line2 = new linechart(coreData.getDataTable(b), axis, "Hello kitty");
		chart = line1.getXYChart(); 
		
		Scene s = new Scene(chart, 300,300);
		stage.hide();
		stage.setTitle("hi here~");
		stage.setScene(s);
		stage.setResizable(true);
		chart = line2.getXYChart();
		stage.show();
		

		
	}
	public static void main(String[] args) {
		launch(args);
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
}

