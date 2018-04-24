package core.comp3111;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart;

import javafx.stage.Stage;


public class Chart{
	/**
	 * Constructor of the Chart class
	 * @param DataTable
	 * @param AxisLabels
	 * @param ChartName
	 * @param ChartType
	 */
	
	Chart(DataTable DataTable, String[] AxisLabels, String ChartName, String ChartType){
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
	
	//Attributes
	
	protected DataTable DataTable;
	protected String DataTableName;
	protected String[] AxisLabels;
	protected String ChartName;
	protected String ChartType;
	protected chart <Object, Object> chart;
	
	
}