package core.comp3111;

/**
 * The implementation of the xychart class 
 * Which is the parent class of Line Chart and Scatter Chart
 * 
 * @author YuenTing
 */
import javafx.scene.chart.XYChart;


public class xychart{
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
	}
	
	/**
	 * A method to show the chart
	 * 
	 * @return true if the graph is sucessfully shown
	 */
	public boolean showGraph() {
		return true;
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

	
	//Attributes
	
	protected DataTable DataTable;
	protected String DataTableName;
	protected String[] AxisLabels;
	protected String ChartName;
	protected String ChartType;
	protected XYChart <Number, Number> xychart;
	
	
}