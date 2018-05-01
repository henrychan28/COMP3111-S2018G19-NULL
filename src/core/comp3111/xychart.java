package core.comp3111;

/**
 * The implementation of the xychart class 
 * Which is the parent class of Line Chart and Scatter Chart
 * 
 * @author YuenTing
 */
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.Serializable;

import javafx.scene.Scene;



public class xychart implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2;
	/**
	 * Constructor of the Chart class
	 * @param DataTable
	 * @param AxisLabels
	 * @param ChartName
	 * @param ChartType
	 */
	
	public xychart(DataTable DataTable, String[] AxisLabels, String ChartName, String ChartType){
		this.DataTable = DataTable;
		//this.DataTableName = DataTable.getName();
		this.AxisLabels = AxisLabels;
		this.ChartName = ChartName;
		this.ChartType = ChartType;
		long serialID = CoreData.getchartid();
		this.ChartID = ChartName + '_' +ChartName +Long.toString(serialID);
	}
	

	/**
	 * Set the ChartName. 
	 * 
	 * @param ChartName
	 * 		- the name of the line chart
	 * @return void
	 */
	public void SetChartName(String ChartName) {
		this.ChartName = ChartName;
		this.xychart.setTitle(ChartName);
		return;
		
	}
	/**
	 * Show the chart on the screen
	 * 
	 * @param stage - pass the Stage stage
	 * 
	 */
	
	public void showChart(Stage stage) {
		
		
        Scene scene  = new Scene(xychart, 500, 400);
        stage.setScene(scene);
        stage.show();	
        
	}
	
	/**
	 * 
	 * get the xychart for UI
	 * @return
	 */
	public XYChart<Number, Number> getXYChart(){
		return xychart;
	}
	/**
	 * get the ChartID
	 * 
	 * @return String ChartID
	 */
	public String getChartID() {
		return this.ChartID;
	}
	/**
	 * get the Chart Name
	 * 
	 * @return String ChartName
	 */
	public String getChartName() {
		return this.ChartName;
	}
	/**
	 * get the chart type
	 * 
	 * @return String ChartType 
	 */
	public String getChartType() {
		return this.ChartType;
	}
	/**
	 * get the data columns name
	 * 
	 * @return String[] AxisLabels
	 */
	
	public String[] getAxisLabels() {
		return this.AxisLabels;
	}
	/**
	 * get the name of the DataTable referred to
	 * 
	 * @ return String DataTableName
	 */
	public String getDataTableName() {
		return DataTableName;
	}

	
	//Attributes
	
	protected DataTable DataTable;
	protected String DataTableName;
	protected String[] AxisLabels;
	protected String ChartName;
	protected String ChartID;  //unique
	protected String ChartType;
	protected XYChart <Number, Number> xychart;
	
	
}