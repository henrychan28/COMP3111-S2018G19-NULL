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
		this.AxisLabels = AxisLabels;
		this.ChartName = ChartName;
		this.ChartType = ChartType;
		long serialID = CoreData.getchartid();
		this.ChartID = ChartName + '_' +Long.toString(serialID);
	}

	/**
	 * 
	 * get the XYChart <Number, Number> xychart. 
	 * 
	 * 
	 * @return XYChart <Number, Number> xychart
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
		return DataTable.getTableName();
	}

	
	//Attributes
	
	protected DataTable DataTable;
	protected String[] AxisLabels;
	protected String ChartName;
	protected String ChartID;  //unique
	protected String ChartType;
	protected XYChart <Number, Number> xychart;
	
	
}