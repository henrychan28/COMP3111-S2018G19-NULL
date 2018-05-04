package core.comp3111;


import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.Serializable;

import javafx.scene.Scene;

/**
 * The implementation of xychart class. 
 * 
 * @author YuenTing
 *
 */

public class xychart implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2;
	
	/**
	 * Constructor of the xychart class
	 * 
	 * @param DataTable
	 * 			- the corresponding DataTable for xychart creation
	 * @param AxisLabels	
	 * 			- String[] names of the columns for the Axises of xychart
	 * @param ChartName
	 * 			- Name of the xychart
	 * @param ChartType
	 * 			- Chart Type of the xychart. define in ChartTypeValue.java. 
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
	 * get the XYChart <Number, Number>. 
	 * 
	 * 
	 * @return XYChart <Number, Number> if chart is created by XYChart's subclass, null otherwise
	 */
	public XYChart<Number, Number> getXYChart(){
		return null;
	}
	
	
	/**
	 * get the Chart ID, which is unique for each chart. 
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
	 * Get the chart type. defined in ChartTypeValue.java.
	 * 
	 * @return String ChartType 
	 */
	public String getChartType() {
		return this.ChartType;
	}
	
	
	/**
	 * Get the data columns name
	 * 
	 * @return String[] AxisLabels
	 */
	public String[] getAxisLabels() {
		return this.AxisLabels;
	}
	
	
	/**
	 * get the name of the DataTable referred to
	 * 
	 * @return String DataTableName
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
	//protected XYChart <Number, Number> xychart;
	
	
}
